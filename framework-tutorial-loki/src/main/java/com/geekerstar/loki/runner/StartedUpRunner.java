package com.geekerstar.loki.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author geekerstar
 * @date 2021/9/6 22:07
 * @description
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StartedUpRunner implements ApplicationRunner {

    @Value("${server.port:8888}")
    private String port;

    @Override
    public void run(ApplicationArguments args) {
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("info日志：{}",LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            log.warn("warn日志：{}",LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            log.debug("debug日志：{}",LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            log.error("error日志：{}",LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }
}

