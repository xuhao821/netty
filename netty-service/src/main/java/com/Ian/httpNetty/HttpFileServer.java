package com.Ian.httpNetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by Ian on 2016/6/27.
 */
public class HttpFileServer {

    private static final String DEFAULT_URL = "com/netty/file";

    public void run(int port,String url){
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(loopGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                            ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
                            ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
                            ch.pipeline().addLast("fileServerHandler",null);
                        }
                    });
            ChannelFuture cf = bootstrap.bind("192.168.0.126",port).sync();
            System.out.println("启动文件服务：http://192.168.0.126:"+port+"/"+url);
            //等待服务器监听端口关闭
            cf.channel().closeFuture().sync();

        }catch (Exception Ex){

        }finally {
            loopGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
    public static void  main(String[] args){
        int port = 8080;
        if(null != args && args.length>0){
            try {
                port = Integer.parseInt(args[0]);
            }catch (Exception e){

            }
        }
        System.out.println("启动文件服务器..........");
        new HttpFileServer().run(port,HttpFileServer.DEFAULT_URL);

    }

}
