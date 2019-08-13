package com.bairock.iot.intelDev.communication;

import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;

import io.netty.channel.Channel;

/**
 * 由于服务端发送心跳有局限性, 增加服务端压力, 
 * 并且设备越多, 轮询发送的时间间隔越长, 可能导致超过掉线判断时间, 
 * 所以服务端准备关闭发送心跳和客户端多次无响应时踢掉客户端的功能, 改成客户端主动发送心跳,
 * 
 * 服务端的心跳暂时保留一段时间, 可以是两三年, 期间新设备全部加上主动发送心跳, 
 * 一段时间后关闭服务器心跳, 停止服务老设备,
 * 
 * 新协议中设备端最好每隔30s主动发送一次心跳, 间隔时间可以自己定, 不可超过60s, 建议30s
 * 服务器收到心跳后回复一条数据, 可以是当前的h1协议,
 * 当前协议的h2协议保留, 即当服务端不知道设备和用户信息时, 主动发送h2协议, 
 * 协议h2的发送时机是新连接立即发和收到数据不知道用户和设备信息时立即发,
 * 保证尽快并且最终能获取到用户信息
 * 
 * 服务端轮询每个连接, 判断连接最后响应数据的时间, 如果超过90s无响应则关闭连接
 * 轮询放在一个新类中
 * 为了防止客户端频繁发送大量数据, 服务端判断收到多次数据或一次收到大数据无法解析时关闭连接
 * @author 44489
 * @version 2019年8月7日下午9:05:58
 */
public class DeviceChannelBridge extends DevChannelBridge {

    @Override
    public int sendOrder(String order, Device dev, boolean immediately) {
        Channel ch = getChannel();
        if (ch == null) {
            setChannelId(null);
            if (null != dev) {
                dev.setDevStateId(DevStateHelper.DS_YI_CHANG);
            }
            return NO_CHANNEL;
        }
        if (null == getDevice()) {
            noReponsePlus();
            sendOrder(order);
            return OK;
        }
        if (immediately) {
            dev.setLastOrder(order);
            dev.noResponsePlus();
            dev.resetLastCommunicationTime();
            sendOrder(order);
            return OK;
        } else {
            if (dev.canSend()) {
                dev.setLastOrder(order);
                dev.noResponsePlus();
                dev.resetLastCommunicationTime();
                sendOrder(order);
                return OK;
            } else {
                return ONE_ORDER_COMMUNICATION_OFTEN;
            }
        }
    }

    
}
