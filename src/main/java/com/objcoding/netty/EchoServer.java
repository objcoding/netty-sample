package com.objcoding.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zhangchenghui.dev@gmail.com
 * @since 2018/11/2
 */
public class EchoServer {

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        // 1.创建线程模型
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        new ServerBootstrap().group(bossGroup, workerGroup)
                // 2. 定义IO模型
                .channel(NioServerSocketChannel.class)
                // 3. 定义读写处理逻辑
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(serverHandler);
                    }
                })
                .bind(8081)
                .addListener((future) -> {
                    if (future.isSuccess()) {
                        System.out.println("端口绑定成功!");
                    } else {
                        System.err.println("端口绑定失败!");
                    }
                });
    }
}
