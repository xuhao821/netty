package com.Ian.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Set;

/**
 * Created by Ian on 2016/6/22.
 */
public class TimeClient {

    public void connect(int port,String host){
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(loopGroup).channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY,true)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                //添加拆包和粘包
                                ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                                ch.pipeline().addLast(new StringDecoder());

                                ch.pipeline().addLast(new TimeClientHandler());
                            }
                        });
            //发起异常请求
            ChannelFuture future = bootstrap.connect(host,port).sync();
            //等待服务器
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args){
        int port = 8080;
        if(null != args && args.length>0){
            try {
                port = Integer.parseInt(args[0]);
            }catch(Exception ex){

            }
        }
        new TimeClient().connect(port,"127.0.0.1");

    }
}
