package com.geekerstar.flink.source;

import com.geekerstar.flink.transformation.Access;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.NumberSequenceIterator;

import java.util.Properties;

/**
 * @author geekerstar
 * @date 2021/9/4 15:08
 * @description
 */
public class SourceApp {

    public static void main(String[] args) throws Exception {
        // 创建上下文
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        test01(env);
//        test02(env);
//        test03(env);
//        test04(env);
//        test05(env);
        env.execute("SourceApp");
    }

    public static void test05(StreamExecutionEnvironment env) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "ruozedata001:9092,ruozedata001:9093,ruozedata001:9094");
        properties.setProperty("group.id", "test");
        DataStream<String> stream = env
                .addSource(new FlinkKafkaConsumer<>("flinktopic", new SimpleStringSchema(), properties));

        System.out.println(stream.getParallelism());
        stream.print();
    }

    public static void test04(StreamExecutionEnvironment env) {
        DataStreamSource<Student> source = env.addSource(new StudentSource()).setParallelism(3);
        System.out.println(source.getParallelism());
        source.print();
    }

    public static void test03(StreamExecutionEnvironment env) {
//        DataStreamSource<Access> source = env.addSource(new AccessSource())
//                .setParallelism(2);
        DataStreamSource<Access> source = env.addSource(new AccessSourceV2()).setParallelism(3);
        System.out.println(source.getParallelism());
        source.print();
    }

    public static void test02(StreamExecutionEnvironment env) {
        env.setParallelism(5); // 对于env设置的并行度 是一个全局的概念
        DataStreamSource<Long> source = env.fromParallelCollection(
                new NumberSequenceIterator(1, 10), Long.class
        );//.setParallelism(4);
        System.out.println("source:" + source.getParallelism());
        SingleOutputStreamOperator<Long> filterStream = source.filter(new FilterFunction<Long>() {
            @Override
            public boolean filter(Long value) {
                return value >= 5;
            }
        }).setParallelism(3); // 对于算子层面的并行度，如果全局设置，以本算子的并行度为准
        System.out.println("filterStream:" + filterStream.getParallelism());
        filterStream.print();
    }

    public static void test01(StreamExecutionEnvironment env) {
        env.setParallelism(5);
//        StreamExecutionEnvironment.createLocalEnvironment();
//        StreamExecutionEnvironment.createLocalEnvironment(3);
//        StreamExecutionEnvironment.createLocalEnvironment(new Configuration());
//        StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
//        StreamExecutionEnvironment.createRemoteEnvironment()
        DataStreamSource<String> source = env.socketTextStream("localhost", 9527);
        System.out.println("source...." + source.getParallelism()); // ?  1
        // 接收socket过来的数据，一行一个单词， 把pk的过滤掉
        SingleOutputStreamOperator<String> filterStream = source.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String value) throws Exception {
                return !"pk".equals(value);
            }
        }).setParallelism(6);
        System.out.println("filter...." + filterStream.getParallelism());
        filterStream.print();
    }
}

