package com.geekerstar.netty.manual.$999.common.protocol.mahjong;

import com.geekerstar.netty.manual.$999.common.protocol.Response;
import com.geekerstar.netty.manual.$999.common.protocol.domain.OperationEnum;

public class ResponseMahjongProtocol extends AbstractMahjongProtocol<Response> {
    @Override
    protected Class<? extends Response> bodyType(int opcode) {
        return OperationEnum.parseResponseType(opcode);
    }
}
