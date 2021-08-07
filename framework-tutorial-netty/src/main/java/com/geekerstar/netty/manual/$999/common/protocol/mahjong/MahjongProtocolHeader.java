package com.geekerstar.netty.manual.$999.common.protocol.mahjong;

import com.geekerstar.netty.manual.$999.common.protocol.ProtocolHeader;
import io.netty.buffer.ByteBuf;

import java.util.concurrent.atomic.AtomicLong;

public class MahjongProtocolHeader implements ProtocolHeader {

    private final static AtomicLong REQUEST_ID_GENERATOR = new AtomicLong(0);

    private int version;
    private int opcode;
    private long requestId;

    public MahjongProtocolHeader() {
    }

    public MahjongProtocolHeader(int opcode) {
        this(1, opcode);
    }

    public MahjongProtocolHeader(int version, int opcode) {
        this.version = version;
        this.opcode = opcode;
        this.requestId = REQUEST_ID_GENERATOR.incrementAndGet();
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(version);
        buf.writeInt(opcode);
        buf.writeLong(requestId);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.version = buf.readInt();
        this.opcode = buf.readInt();
        this.requestId = buf.readLong();
    }

    public int getVersion() {
        return version;
    }

    public int getOpcode() {
        return opcode;
    }

    public long getRequestId() {
        return requestId;
    }
}
