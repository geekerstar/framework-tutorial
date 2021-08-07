package com.geekerstar.netty.manual.$998.common.protocol;

import io.netty.channel.Channel;
import lombok.Data;

/**
 * 将接收到的消息封装
 */
@Data
public class ReceivedMessage {
    /**
     * 原始消息
     */
    private MahjongRequest request;
    /**
     * 客户端通道
     */
    private Channel channel;
    /**
     * 接收时的时间
     */
    private long receiveTime;
}
