package com.geekerstar.netty.manual.$998.server.processor;

import com.geekerstar.netty.manual.$998.common.protocol.MahjongMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface MahjongProcessor<T extends MahjongMsg> {
    void process(T msg);

    static void processMsg(MahjongMsg msg) {
        Logger log = LoggerFactory.getLogger(MahjongProcessor.class);
        MahjongProcessor processor = ProcessorEnum.getProcessor(msg.getClass());
        if (processor != null) {
            try {
                processor.process(msg);
            } catch (Exception e) {
                log.error("something error when processing", e);
            }
        } else {
            log.error("not found request processor, requestType={}", msg.getClass().getSimpleName());
        }
    }
}
