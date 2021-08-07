package com.geekerstar.netty.manual.$998.common.domain;

import lombok.Data;

@Data
public class Player {
    /**
     * 玩家id
     */
    private long id;
    /**
     * 玩家昵称
     */
    private String name;
    /**
     * 玩家积分
     */
    private int score;
    /**
     * 玩家钻石
     */
    private int diamond;
    /**
     * 玩家在桌子上的位置
     */
    private int pos = -1;
    /**
     * 玩家拥有的牌
     */
    private byte[] cards;
    /**
     * 玩家拥有的gang
     */
    private byte[] gangList;
    /**
     * 玩家拥有的peng
     */
    private byte[] pengList;

    public int validCardNum() {
        int num = 0;
        for (byte card : cards) {
            if (card != 0) {
                num++;
            }
        }
        return num;
    }

    public byte lastCard() {
        for (int i = cards.length - 1; i >= 0; i--) {
            if (cards[i] != 0) {
                return cards[i];
            }
        }
        return 0;
    }

    public boolean containCards(byte... cards) {
        // 克隆一份，不影响原数据
        byte[] cloneCards = this.cards.clone();
        outer:
        for (byte card : cards) {
            for (int i = 0; i < cloneCards.length; i++) {
                if (cloneCards[i] == card) {
                    // 找到了就归0，不影响多张一样牌的情况
                    cloneCards[i] = 0;
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }

    public void removeCard(byte... removedCards) {
        for (byte removedCard : removedCards) {
            for (int i = 0; i < this.cards.length; i++) {
                if (this.cards[i] == removedCard) {
                    this.cards[i] = 0;
                    break;
                }
            }
        }
    }

    public void addCard(Byte card) {
        for (int i = 0; i < this.cards.length; i++) {
            if (cards[i] == 0) {
                cards[i] = card;
                break;
            }
        }
    }

    public void reset() {
        cards = null;
        pos = -1;
        gangList = null;
        pengList = null;
    }

    public void addPeng(byte card) {
        if (pengList == null) {
            pengList = new byte[0];
        }
        byte[] newPengList = new byte[pengList.length + 1];
        System.arraycopy(pengList, 0, newPengList, 0, pengList.length);
        newPengList[newPengList.length - 1] = card;
        pengList = newPengList;
    }

    public void addGang(byte card) {
        if (gangList == null) {
            gangList = new byte[0];
        }
        byte[] newGangList = new byte[gangList.length + 1];
        System.arraycopy(gangList, 0, newGangList, 0, gangList.length);
        newGangList[newGangList.length - 1] = card;
        gangList = newGangList;
    }

    public boolean isPengContain(byte card) {
        if (pengList != null) {
            for (byte c : pengList) {
                if (c == card) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removePengCard(Byte card) {
        if (pengList != null) {
            for (int i = 0; i < pengList.length; i++) {
                if (pengList[i] == card) {
                    pengList[i] = 0;
                }
            }
        }
    }
}
