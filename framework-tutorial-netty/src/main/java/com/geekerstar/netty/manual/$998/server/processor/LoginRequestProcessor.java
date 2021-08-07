package com.geekerstar.netty.manual.$998.server.processor;

import com.geekerstar.netty.manual.$998.common.domain.Player;
import com.geekerstar.netty.manual.$998.common.msg.LoginRequest;
import com.geekerstar.netty.manual.$998.common.msg.LoginResponse;
import com.geekerstar.netty.manual.$998.server.data.DataManager;
import com.geekerstar.netty.manual.$998.util.IdUtils;
import com.geekerstar.netty.manual.$998.util.MsgUtils;

public class LoginRequestProcessor implements MahjongProcessor<LoginRequest> {

    @Override
    public void process(LoginRequest msg) {
        LoginResponse loginResponse = new LoginResponse();
        if (msg.getUsername() == null || "".equals(msg.getUsername())) {
            loginResponse.setResult(false);
            loginResponse.setMsg("用户名不能为空");
        } else {
            // 任何用户名皆可通过
            loginResponse.setResult(true);
            // 假设从数据库或者redis中读取到的
            Player player = new Player();
            player.setId(IdUtils.randomLong());
            player.setName(msg.getUsername());
            player.setScore(IdUtils.randomInt(100));
            player.setDiamond(IdUtils.randomInt(100));
            loginResponse.setPlayer(player);

            // 登录成功，设置channel与player的关系
            DataManager.bindChannelPlayer(DataManager.CURRENT_CHANNEL.get(), player);
            DataManager.bindPlayerChannel(player, DataManager.CURRENT_CHANNEL.get());
        }
        MsgUtils.send(loginResponse);
    }
}
