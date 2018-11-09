package com.objcoding.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author zhangchenghui.dev@gmail.com
 * @since 2018/11/2
 */
public class EchoClient {

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        new Bootstrap()
                // 1. 定义线程模型
                .group(group)
                // 2. 定义IO模型
                .channel(NioSocketChannel.class)
                // 3. IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new EchoClientHandler());
                    }
                })
                .connect("127.0.0.1", 8081)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("连接成功!");
                    } else {
                        System.err.println("连接失败!");
                    }
                });
    }

}
