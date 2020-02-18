package com.geekerstar.hutool.core.io;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.io.watch.watchers.DelayWatcher;
import cn.hutool.core.lang.Console;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @author geekerstar
 * date: 2019/11/30 15:26
 * description: https://www.hutool.cn/docs/#/core/IO/%E6%96%87%E4%BB%B6%E7%9B%91%E5%90%AC-WatchMonitor
 */
public class WatchMonitorTest {
    /**
     * 监听指定事件
     */
//    public static void main(String[] args) {
//        File file = FileUtil.file("example.properties");
//        //这里只监听文件或目录的修改事件
//        WatchMonitor watchMonitor = WatchMonitor.create(file, WatchMonitor.ENTRY_MODIFY);
//        watchMonitor.setWatcher(new Watcher(){
//            @Override
//            public void onCreate(WatchEvent<?> event, Path currentPath) {
//                Object obj = event.context();
//                Console.log("创建：{}-> {}", currentPath, obj);
//            }
//            @Override
//            public void onModify(WatchEvent<?> event, Path currentPath) {
//                Object obj = event.context();
//                Console.log("修改：{}-> {}", currentPath, obj);
//            }
//            @Override
//            public void onDelete(WatchEvent<?> event, Path currentPath) {
//                Object obj = event.context();
//                Console.log("删除：{}-> {}", currentPath, obj);
//            }
//            @Override
//            public void onOverflow(WatchEvent<?> event, Path currentPath) {
//                Object obj = event.context();
//                Console.log("Overflow：{}-> {}", currentPath, obj);
//            }
//        });
//        //设置监听目录的最大深入，目录层级大于制定层级的变更将不被监听，默认只监听当前层级目录
//        watchMonitor.setMaxDepth(3);
//        //启动监听
//        watchMonitor.start();
//    }

    /**
     * 监听全部事件
     * @param args
     */
    public static void main(String[] args) {
        File file = FileUtil.file("example.properties");
        WatchMonitor.createAll(file, new SimpleWatcher(){
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Console.log("EVENT modify");
            }
        }).start();

        /**
         * 延迟处理监听事件
         */
//        WatchMonitor monitor = WatchMonitor.createAll("d:/", new DelayWatcher(watcher, 500));
//        monitor.start();

    }




}
