package com.a.Server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component(value = "ChatRoomHandler")
@ChannelHandler.Sharable
public class ChatRoomHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    protected static ConcurrentHashMap<String,ChannelHandlerContext> map = new ConcurrentHashMap<>();
    //广播
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取内容
        String content = msg.text();
        //获取当前的Channel
        Channel incoming = ctx.channel();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Channel channel : channels) {
                channel.writeAndFlush(new TextWebSocketFrame(df.format(new Date())+"\n" + content));
        }

    }


    //进入:,提示所有人,有人进来了
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        channels.add(ctx.channel());

        for (Channel channel : channels){
            channel.writeAndFlush(new TextWebSocketFrame(df.format(new Date())+"\n"+ctx.name() + "已经进入聊天室"
            +" 当前人数："+channels.size()));
        }

    }

    //离开,提示
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        channels.remove(ctx.channel());
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame(df.format(new Date())+"\n"+ctx.channel() + "离开了聊天室"
            +" 当前人数："+channels.size()));
        }

    }


}



