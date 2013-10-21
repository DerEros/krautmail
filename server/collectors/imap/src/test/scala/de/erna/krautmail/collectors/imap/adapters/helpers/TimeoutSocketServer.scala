package de.erna.krautmail.collectors.imap.adapters.helpers

import scala.concurrent.duration.Duration
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.SocketChannel
import io.netty.channel.{ChannelOption, ChannelInitializer}

/**
 * User: Eros Candelaresi <eros@candelaresi.de>
 * Project: server
 * Date: 21.10.2013
 * Time: 13:37
 *
 * A small server that opens a listening socket and on connection it waits for the given duration before it closes the
 * socket and shuts down. Obviously everything is done non-blocking.
 */
class TimeoutSocketServer(port: Int, timeout: Duration) {
  val bossGroup = new NioEventLoopGroup()
  val workerGroup = new NioEventLoopGroup()

  def run() {
    val bootstrap = new ServerBootstrap()

    try {
      bootstrap.group(bossGroup, workerGroup)
        .channel(classOf[NioServerSocketChannel])
        .childHandler(new ChannelInitializer[SocketChannel] {
        def initChannel(ch: SocketChannel) {
          ch.pipeline().addLast(new DiscardServerHandler())
        }
      })
        .option[Integer](ChannelOption.SO_BACKLOG, 128)
        .childOption[java.lang.Boolean](ChannelOption.SO_KEEPALIVE, true)

      val channelFuture = bootstrap.bind(port).sync()

      //      channelFuture.channel().closeFuture().sync
    } finally {
    }
  }

  def stop() {
    workerGroup.shutdownGracefully()
    bossGroup.shutdownGracefully()
  }
}
