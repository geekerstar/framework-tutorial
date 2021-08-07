package com.geekerstar.netty.manual.$998.server.processor;

import com.geekerstar.netty.manual.$998.common.domain.Player;
import com.geekerstar.netty.manual.$998.common.domain.Table;
import com.geekerstar.netty.manual.$998.common.msg.CreateTableRequest;
import com.geekerstar.netty.manual.$998.common.msg.CreateTableResponse;
import com.geekerstar.netty.manual.$998.server.data.DataManager;
import com.geekerstar.netty.manual.$998.util.MsgUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateTableRequestProcessor implements MahjongProcessor<CreateTableRequest> {
    @Override
    public void process(CreateTableRequest msg) {
        Player player = DataManager.currentPlayer();
        // 创建桌子
        Table table = new Table();
        table.setId(DataManager.CURRENT_TABLE_ID.get());
        table.setBaseScore(msg.getBaseScore());
        table.setMaxPlayerNum(msg.getPlayerNum());
        Player[] players = new Player[table.getMaxPlayerNum()];
        players[0] = player;
        player.setPos(0);
        player.setDiamond(player.getDiamond() - 1);
        table.setPlayers(players);
        table.setStatus(Table.STATUS_WAITING);

        // 数据缓存
        DataManager.bindChannelTable(DataManager.CURRENT_CHANNEL.get(), table);
        DataManager.putTable(table);

        // 返回响应
        CreateTableResponse response = new CreateTableResponse();
        response.setTable(table);
        MsgUtils.send(response);
    }
}
