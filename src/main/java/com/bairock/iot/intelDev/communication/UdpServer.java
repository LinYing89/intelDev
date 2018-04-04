package com.bairock.iot.intelDev.communication;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import com.bairock.iot.intelDev.user.User;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UdpServer {

	private static UdpServer ins = new UdpServer();

	public static int MY_PORT = 10000;
	public static int TO_PORT = 10001;
	private EventLoopGroup group;
	private Channel ch;

	private MessageAnalysiser messageAnalysiser;
	private User user;

	public static UdpServer getIns() {
		return ins;
	}

	public void run() {
		try {
			group = new NioEventLoopGroup();
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)
					.handler(new UdpServerHandler());

			ch = b.bind(MY_PORT).channel();
			ch.closeFuture();
		} finally {
			// group.shutdownGracefully();
		}
	}

	public void close() {
		if (null != group) {
			group.shutdownGracefully();
		}
		ch = null;
	}

	public void setMessageAnalysiser(MessageAnalysiser messageAnalysiser) {
		this.messageAnalysiser = messageAnalysiser;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void receivedMsg(String msg) {
		if (null != messageAnalysiser && null != user) {
			messageAnalysiser.putMsg(msg, user);
		}
	}

	public void send(String msg) {
		if (null != ch) {
			ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(msg, Charset.forName("GBK")),
					new InetSocketAddress("255.255.255.255", TO_PORT)));
		}
	}

//	public static void main(String[] args) throws Exception {
//		UdpServer.getIns().run();
//	}
}
