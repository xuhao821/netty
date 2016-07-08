package com.Ian.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by Ian on 2016/6/22.
 * 时间服务
 */
public class TimeServer {

     public void bind(int port){
         //配置服务器的线程组
         EventLoopGroup bossGroup = new NioEventLoopGroup();
         EventLoopGroup workerGroup = new NioEventLoopGroup();
         try {
             ServerBootstrap bootstrap = new ServerBootstrap();
             bootstrap.group(bossGroup,workerGroup)
                     .channel(NioServerSocketChannel.class)
                     .option(ChannelOption.SO_BACKLOG,1024)
                     .childHandler(new ChildChannelHander());
             //绑定接口，同步等待成功
             ChannelFuture cf = bootstrap.bind(port).sync();
             //等待服务器监听端口关闭
                cf.channel().closeFuture().sync();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
         }

     }
}

  class ChildChannelHander extends ChannelInitializer<SocketChannel>{
      @Override
      protected void initChannel(SocketChannel ch) throws Exception {
           //分包、粘包解码器
          ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
          ch.pipeline().addLast(new StringDecoder());

          ch.pipeline().addLast(new TimeServerHandler());
      }

      public static void  main(String[] args){
          int port = 8080;
          if(null != args && args.length>0){
              try {
                  port = Integer.parseInt(args[0]);
              }catch (Exception e){

              }
          }
          System.out.println("启动完成........");

          new TimeServer().bind(port);

      }
  }