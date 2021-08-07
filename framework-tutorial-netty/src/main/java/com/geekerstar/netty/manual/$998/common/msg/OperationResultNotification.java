package com.geekerstar.netty.manual.$998.common.msg;

import com.geekerstar.netty.manual.$998.common.protocol.MahjongNotification;
import lombok.Data;

/**
 * 操作结果通知
 */
@Data
public class OperationResultNotification implements MahjongNotification {
    private int operation;
    private int operationPos;
    private byte[] cards;
}
