package com.geekerstar.netty.manual.$998.common.protocol;

import com.alibaba.fastjson.JSON;
import com.geekerstar.netty.manual.$998.common.msg.MsgTypeEnum;
import com.geekerstar.netty.manual.$998.util.IdUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.nio.charset.StandardCharsets;

@Data
public class MahjongProtocol<T extends MahjongMsg> {

    private MahjongProtocolHeader header;
    private T body;

    public MahjongProtocol() {
    }

    public MahjongProtocol(T body) {
        this.body = body;
        MahjongProtocolHeader header = new MahjongProtocolHeader();
        header.setVersion(1);
        header.setCmd(MsgTypeEnum.parseByBodyType(body.getClass()));
        header.setReqId(IdUtils.generateId());
        this.header = header;
    }

    public void encode(ByteBuf buf) {
        buf.writeInt(header.getVersion());
        buf.writeInt(header.getCmd());
        buf.writeLong(header.getReqId());
        buf.writeBytes(JSON.toJSONString(body).getBytes(StandardCharsets.UTF_8));
    }

    public void decode(ByteBuf msg) {
        MahjongProtocolHeader header = new MahjongProtocolHeader();
        header.setVersion(msg.readInt());
        header.setCmd(msg.readInt());
        header.setReqId(msg.readLong());
        this.header = header;

        Class<?> bodyType = MsgTypeEnum.parseByCmd(header.getCmd());

        this.body = (T) JSON.parseObject(msg.toString(StandardCharsets.UTF_8), bodyType);
    }
}
