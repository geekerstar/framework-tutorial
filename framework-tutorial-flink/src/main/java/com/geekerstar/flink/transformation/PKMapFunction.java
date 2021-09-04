package com.geekerstar.flink.transformation;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.configuration.Configuration;

/**
 * @author geekerstar
 * @date 2021/9/4 20:08
 * @description
 */
public class PKMapFunction extends RichMapFunction<String,Access> {

    /**
     * 初始化操作
     * Connection
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        System.out.println("~~~~open~~~~");
    }

    /**
     * 清理操作
     */
    @Override
    public void close() throws Exception {
        super.close();
    }

    @Override
    public RuntimeContext getRuntimeContext() {
        return super.getRuntimeContext();
    }

    /**
     * 每条数据执行一次
     */
    @Override
    public Access map(String value) throws Exception {
        System.out.println("=====map=====");

        String[] splits = value.split(",");
        Long time = Long.parseLong(splits[0].trim());
        String domain = splits[1].trim();
        Double traffic = Double.parseDouble(splits[2].trim());
        return new Access(time, domain, traffic);
    }
}
