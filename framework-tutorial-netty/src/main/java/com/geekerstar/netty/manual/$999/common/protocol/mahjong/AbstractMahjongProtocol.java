package com.geekerstar.netty.manual.$999.common.protocol.mahjong;

import com.alibaba.fastjson.JSON;
import com.geekerstar.netty.manual.$999.common.protocol.Protocol;
import com.geekerstar.netty.manual.$999.common.protocol.ProtocolBody;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public abstract class AbstractMahjongProtocol<B extends ProtocolBody> implements Protocol<MahjongProtocolHeader, B> {
    private MahjongProtocolHeader header;
    private B body;

    @Override
    public void encode(ByteBuf buf) {
        header.encode(buf);
        buf.writeBytes(JSON.toJSONString(body).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void decode(ByteBuf buf) {
        header = new MahjongProtocolHeader();
        header.decode(buf);

        int opcode = header.getOpcode();
        Class<?> bodyType = bodyType(opcode);

        String content = buf.toString(StandardCharsets.UTF_8);
        try {
            body = (B) JSON.parseObject(content, bodyType);
        } catch (Exception e) {
            log.error("json parse error, content={}", content);
        }
    }

    protected abstract Class<? extends B> bodyType(int opcode);

    public MahjongProtocolHeader getHeader() {
        return header;
    }

    public B getBody() {
        return body;
    }

    public void setHeader(MahjongProtocolHeader header) {
        this.header = header;
    }

    public void setBody(B body) {
        this.body = body;
    }
}
