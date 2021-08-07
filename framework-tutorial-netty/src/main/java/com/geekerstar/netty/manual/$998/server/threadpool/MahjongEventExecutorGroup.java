package com.geekerstar.netty.manual.$998.server.threadpool;

import com.geekerstar.netty.manual.$998.common.msg.CreateTableRequest;
import com.geekerstar.netty.manual.$998.common.msg.EnterTableRequest;
import com.geekerstar.netty.manual.$998.common.protocol.MahjongRequest;
import com.geekerstar.netty.manual.$998.common.protocol.ReceivedMessage;
import com.geekerstar.netty.manual.$998.server.data.DataManager;
import com.geekerstar.netty.manual.$998.server.processor.MahjongProcessor;
import com.geekerstar.netty.manual.$998.util.IdUtils;
import io.netty.channel.Channel;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.MultithreadEventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;

@Slf4j
public class MahjongEventExecutorGroup extends MultithreadEventExecutorGroup {

    private static final MahjongEventExecutorGroup TABLE_EVENT_EXECUTOR_GROUP = new MahjongEventExecutorGroup(NettyRuntime.availableProcessors());

    public MahjongEventExecutorGroup(int nThreads) {
        super(nThreads, null, MahjongEventExecutorChooserFactory.INSTANCE, null);
    }

    @Override
    public void execute(Runnable command) {
        EventExecutor executor = next();
        executor.execute(() -> {
            DataManager.CURRENT_EXECUTOR.set(executor);
            try {
                command.run();
            } finally {
                DataManager.CURRENT_EXECUTOR.remove();
            }
        });
    }

    @Override
    protected EventExecutor newChild(Executor executor, Object... args) throws Exception {
        return new DefaultEventExecutor(this, executor);
    }

    public static void execute(ReceivedMessage receivedMessage) {
        Channel channel = receivedMessage.getChannel();
        MahjongRequest request = receivedMessage.getRequest();
        long receiveTime = receivedMessage.getReceiveTime();

        // 如果是创建桌子，则分配一个桌子id
        Long tableId;
        if (request instanceof CreateTableRequest) {
            tableId = IdUtils.generateId();
        } else if (request instanceof EnterTableRequest) {
            tableId = ((EnterTableRequest) request).getTableId();
        } else {
            // channel已经绑定了table
            tableId = DataManager.getTableIdByChannel(channel);
            if (tableId == null) {
                tableId = IdUtils.randomLong();
            }
        }
        // 用于根据桌子id选择线程
        MahjongEventExecutorChooserFactory.setTableId(tableId);

        final Long finalTableId = tableId;

        TABLE_EVENT_EXECUTOR_GROUP.execute(() -> {
            log.info("process msg, channel={}, table={}, thread={}", channel.id(), finalTableId, Thread.currentThread().getId());
            DataManager.CURRENT_CHANNEL.set(channel);
            DataManager.CURRENT_TABLE_ID.set(finalTableId);

            try {
                MahjongProcessor.processMsg(request);
            } catch (Exception e) {
                log.error("process request error, request={}", request, e);
            } finally {
                DataManager.CURRENT_CHANNEL.remove();
                DataManager.CURRENT_TABLE_ID.remove();

                // 执行时间久的记录下
                long delta = System.currentTimeMillis() - receiveTime;
                if (delta > 5000) {
                    log.error("process request too slow, request={}", request);
                }
            }
        });
    }
}
