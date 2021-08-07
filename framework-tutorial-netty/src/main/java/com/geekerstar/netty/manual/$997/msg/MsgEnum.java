package com.geekerstar.netty.manual.$997.msg;

import com.geekerstar.netty.manual.$997.proto.LoginRequest;
import com.geekerstar.netty.manual.$997.proto.LoginResponse;
import com.google.protobuf.MessageLite;

public enum MsgEnum {
    LOGIN_REQUEST(1, LoginRequest.getDefaultInstance()),
    LOGIN_RESPONSE(2, LoginResponse.getDefaultInstance()),
    ;


    private int cmd;
    private MessageLite msg;

    MsgEnum(int cmd, MessageLite msg) {
        this.cmd = cmd;
        this.msg = msg;
    }

    public static MessageLite parse(int cmd) {
        for (MsgEnum msgEnum : MsgEnum.values()) {
            if (msgEnum.cmd == cmd) {
                return msgEnum.msg;
            }
        }
        return null;
    }
}
