package com.geekerstar.netty.manual.$999.common.protocol.mahjong;

import com.geekerstar.netty.manual.$999.common.protocol.Request;
import com.geekerstar.netty.manual.$999.common.protocol.domain.OperationEnum;

public class RequestMahjongProtocol extends AbstractMahjongProtocol<Request> {

    @Override
    protected Class<? extends Request> bodyType(int opcode) {
        return OperationEnum.parseRequestType(opcode);
    }
}
