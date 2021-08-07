package com.geekerstar.netty.manual.$998.server.data;

import com.geekerstar.netty.manual.$998.common.domain.Player;
import com.geekerstar.netty.manual.$998.common.domain.Table;
import lombok.Data;

/**
 * Channel绑定的东西
 */
@Data
public class ChannelBinding {
    private Player player;
    private Table table;
}
