package de.erna.krautmail.collectors.imap.adapters.helpers

import io.netty.channel.{ChannelHandlerContext, ChannelInboundHandlerAdapter}
import io.netty.buffer.ByteBuf

/**
 * User: Eros Candelaresi <eros@candelaresi.de>
 * Project: server
 * Date: 21.10.2013
 * Time: 15:08
 */
class DiscardServerHandler extends ChannelInboundHandlerAdapter {
  override def channelRead(ctx: ChannelHandlerContext, msg: scala.Any) {
    val in = msg.asInstanceOf[ByteBuf]
    try {
      println(in.toString(io.netty.util.CharsetUtil.UTF_8))
    } finally {
      in.release()
      ctx.channel().close()
    }
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
    cause.printStackTrace()
    ctx.close()
  }
}
