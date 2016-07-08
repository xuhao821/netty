package com.Ian.httpNetty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;

import static io.netty.handler.codec.http.HttpVersion.*;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpMethod.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;



/**
 * Created by Ian on 2016/6/27.
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest>{
    private String url;

    public HttpFileServerHandler(String url){
        this.url = url;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (!request.getDecoderResult().isSuccess()){
           sendError(ctx,BAD_REQUEST);
            return;
        }
        if(request.getMethod() != GET){
            sendError(ctx,METHOD_NOT_ALLOWED);
            return;
        }
    }



    public static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,status, Unpooled.copiedBuffer("Failure: " + status.toString()
         + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE,"text/plain; charset=" + CharsetUtil.UTF_8 );
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void setContentTypeHeader(HttpResponse response,File file){
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        response.headers().set(CONTENT_TYPE,
                mimetypesFileTypeMap.getContentType(file.getPath()));
    }


}
