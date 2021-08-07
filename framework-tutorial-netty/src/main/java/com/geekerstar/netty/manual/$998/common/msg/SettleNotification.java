package com.geekerstar.netty.manual.$998.common.msg;

import com.geekerstar.netty.manual.$998.common.protocol.MahjongNotification;
import lombok.Data;

@Data
public class SettleNotification implements MahjongNotification {
    private int winnerPos;
    private int baseScore;
}
