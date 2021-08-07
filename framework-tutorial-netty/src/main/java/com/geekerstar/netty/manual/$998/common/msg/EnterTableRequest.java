package com.geekerstar.netty.manual.$998.common.msg;

import com.geekerstar.netty.manual.$998.common.protocol.MahjongRequest;
import lombok.Data;

@Data
public class EnterTableRequest implements MahjongRequest {
    private long tableId;
}
