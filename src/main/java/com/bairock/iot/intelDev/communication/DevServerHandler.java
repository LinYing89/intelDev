package com.bairock.iot.intelDev.communication;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class DevServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		DevChannelBridge.channelGroup.add(ctx.channel());
		DevChannelBridgeHelper.getIns().setChannelId(ctx.channel().id().asShortText());
//		ctx.writeAndFlush(Unpooled.copiedBuffer(OrderHelper.getOrderMsg("h2").getBytes()));
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf m = (ByteBuf)msg;
		try{
			byte[] req = new byte[m.readableBytes()];
			m.readBytes(req);
			String str = new String(req, "GBK");
//			ctx.channel().writeAndFlush(Unpooled.copiedBuffer(req));
			//System.out.println(str);
			DevChannelBridgeHelper.getIns().channelReceived(ctx.channel().id().asShortText(), str);
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			m.release();
//			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
		DevChannelBridgeHelper.getIns().channelInActive(ctx.channel().id().asShortText());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		ctx.close();
		DevChannelBridgeHelper.getIns().channelInActive(ctx.channel().id().asShortText());
	}

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {  // 2
            IdleStateEvent event = (IdleStateEvent) evt;  
            String type = "";
            if (event.state() == IdleState.READER_IDLE) {
                type = "read idle";
                //读数据超时后关闭连接
                ctx.close();
                DevChannelBridgeHelper.getIns().channelInActive(ctx.channel().id().asShortText());
            } else if (event.state() == IdleState.WRITER_IDLE) {
                type = "write idle";
            } else if (event.state() == IdleState.ALL_IDLE) {
                type = "all idle";
            }
 
            System.out.println( ctx.channel().remoteAddress()+"超时类型：" + type);
        } else {
            super.userEventTriggered(ctx, evt);
        }

    }
	
}
