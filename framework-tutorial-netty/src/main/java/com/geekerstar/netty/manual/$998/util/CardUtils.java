package com.geekerstar.netty.manual.$998.util;

import java.util.Collections;
import java.util.LinkedList;

public class CardUtils {
    /**
     * 一个byte可以表示所有牌，高4位代表牌型，低4位代表牌值
     * 牌型：万(0)、条(1)、筒(2)、字（东南西北中发白）(3)、花（春夏秋冬梅兰竹菊）(4)
     * 牌值：万条筒：123456789，东南西北中发白：1234567，春夏秋冬梅兰竹菊：12345678
     */
    public static final byte WAN_MASK = 0x00;
    public static final byte TIAO_MASK = 0x10;
    public static final byte TONG_MASK = 0x20;
    public static final byte ZI_MASK = 0x30;
    public static final byte HUA_MASK = 0x40;

    public static final byte COLOR_MASK = 0x70;
    public static final byte VALUE_MASK = 0x0f;

    public static byte color(byte card) {
        return (byte) (card & COLOR_MASK);
    }

    public static byte value(byte card) {
        return (byte) (card & VALUE_MASK);
    }

    public static boolean isWan(byte card) {
        return color(card) == WAN_MASK;
    }

    public static boolean isTiao(byte card) {
        return color(card) == TIAO_MASK;
    }

    public static boolean isTong(byte card) {
        return color(card) == TONG_MASK;
    }

    public static LinkedList<Byte> initCards() {
        // 万条筒默认都支持（个别三人麻将除外）
        LinkedList<Byte> cardList = new LinkedList<>();
        // 添加万、条、筒
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j < 4; j++) {
                cardList.add((byte) (WAN_MASK | i));
                cardList.add((byte) (TIAO_MASK | i));
                cardList.add((byte) (TONG_MASK | i));
            }
        }

        // 多洗几次
        Collections.shuffle(cardList);
        Collections.shuffle(cardList);
        Collections.shuffle(cardList);

        return cardList;
    }


}
