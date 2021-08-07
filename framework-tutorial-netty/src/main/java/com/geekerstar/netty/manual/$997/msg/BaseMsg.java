package com.geekerstar.netty.manual.$997.msg;

import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import lombok.Data;

@Data
public class BaseMsg {
    private int cmd;
    private int reqId;
    private int version;
    private MessageLite body;

    public void encode(ByteBuf buf) {
        buf.writeInt(cmd);
        buf.writeInt(reqId);
        buf.writeInt(version);
        buf.writeBytes(body.toByteArray());
    }

    public void decode(ByteBuf msg) throws Exception {
        cmd = msg.readInt();
        reqId = msg.readInt();
        version = msg.readInt();

        MessageLite msgType = MsgEnum.parse(cmd);
        final byte[] array;
        final int offset;
        final int length = msg.readableBytes();
        if (msg.hasArray()) {
            array = msg.array();
            offset = msg.arrayOffset() + msg.readerIndex();
        } else {
            array = ByteBufUtil.getBytes(msg, msg.readerIndex(), length, false);
            offset = 0;
        }

        body = msgType.getParserForType().parseFrom(array, offset, length);
    }
}
