package com.geekerstar.netty.manual.$998.common.msg;

import com.geekerstar.netty.manual.$998.common.domain.Table;
import com.geekerstar.netty.manual.$998.common.protocol.MahjongMsg;
import lombok.Data;

@Data
public class StartGameMsg implements MahjongMsg {
    private Table table;
}
