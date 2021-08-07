package com.geekerstar.netty.manual.$34.test;

import com.geekerstar.netty.manual.$34.proto.HelloResponse;
import com.geekerstar.netty.manual.$34.proto.Player;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;

public class ProtobufTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        Player player = Player.newBuilder().setName("tt").build();
        HelloResponse response = HelloResponse.newBuilder().setResult(true).setMessage("hello").setPlayer(player).build();

        HelloResponse response2 = clone(response);

        System.out.println(response == response2);
        System.out.println(response.getPlayer() == response2.getPlayer());

    }

    public static <T extends MessageLite> T clone(T msg) throws InvalidProtocolBufferException {
        byte[] bytes = msg.toByteArray();
        return (T) msg.getParserForType().parseFrom(bytes);
    }
}
