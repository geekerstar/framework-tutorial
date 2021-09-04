package com.geekerstar.flink.source;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author geekerstar
 * @date 2021/9/4 11:37
 * @description
 */
public class StreamWordCount {public static void main(String[] args) throws Exception {

    // 创建上下文
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    // 对接数据源的数据
    DataStreamSource<String> source = env.socketTextStream("localhost", 9527);

    // 业务逻辑处理： transformation
    source.flatMap(new StreamFlatMapFunction())
            .filter(new StreamFilterFunction())
            .map(new StreamMapFunction())
            .keyBy(new StreamKeySelector())
            .sum(1)
            .print();

    env.execute("StreamingWCApp");
}
}

class StreamFlatMapFunction implements FlatMapFunction<String, String> {

    @Override
    public void flatMap(String value, Collector<String> out) {
        String[] words = value.split(",");
        for (String word : words) {
            out.collect(word.toLowerCase().trim());
        }
    }
}

class StreamMapFunction implements MapFunction<String, Tuple2<String, Integer>> {
    @Override
    public Tuple2<String, Integer> map(String value) {
        return new Tuple2<>(value, 1);
    }
}

class StreamFilterFunction implements FilterFunction<String> {
    @Override
    public boolean filter(String value) {
        return StringUtils.isNotEmpty(value);
    }
}

class StreamKeySelector implements KeySelector<Tuple2<String, Integer>, String> {
    @Override
    public String getKey(Tuple2<String, Integer> value) {
        return value.f0;
    }
}

