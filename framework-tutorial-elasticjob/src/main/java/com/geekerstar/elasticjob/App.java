package com.geekerstar.elasticjob;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.geekerstar.elasticjob.job.MyDataflowJob;
import com.geekerstar.elasticjob.job.MySimpleJob;


/**
 * @author geekerstar
 * date: 2019/9/20 19:52
 * description: elastic-job基本作业
 */
public class App {
    public static void main( String[] args )    {
        System.out.println( "Hello World!" );
        new JobScheduler(zkCenter(),configurationDataflow()).init();
    }

    /**
     * zookeeper注册中心
     * @return
     */
    public static CoordinatorRegistryCenter zkCenter(){
        ZookeeperConfiguration zc = new ZookeeperConfiguration("localhost:2181",
                "java-simple-job");

        ZookeeperRegistryCenter crc=new ZookeeperRegistryCenter(zc);
        //注册中心初始化
        crc.init();
        return crc;
    }


    /**
     * Simple作业
     *
     * @return
     */
    public static LiteJobConfiguration configuration() {
        //job核心配置
        JobCoreConfiguration jcc = JobCoreConfiguration
                .newBuilder("mySimpleJob","0/5 * * * * ?",2)
                .build();
        //job类型配置
        SimpleJobConfiguration jtc = new SimpleJobConfiguration(jcc,
                MySimpleJob.class.getCanonicalName());

        //job根的配置（LiteJobConfiguration）
        LiteJobConfiguration ljc = LiteJobConfiguration
                .newBuilder(jtc)
                .overwrite(true)
                .build();

        return ljc;
    }

    /**
     * Dataflow作业
     *
     * @return
     */
    public static LiteJobConfiguration configurationDataflow() {
        //job核心配置
        JobCoreConfiguration jcc = JobCoreConfiguration
                .newBuilder("myDataflowJob","0/10 * * * * ?",2)
                .build();

        //job类型配置
        DataflowJobConfiguration jtc = new DataflowJobConfiguration(jcc,
                MyDataflowJob.class.getCanonicalName(),true);

        //job根的配置（LiteJobConfiguration）
        LiteJobConfiguration ljc = LiteJobConfiguration
                .newBuilder(jtc)
                .overwrite(true)
                .build();

        return ljc;
    }

    /**
     * Script作业
     *
     * @return
     */
    public static LiteJobConfiguration configurationScript() {
        //job核心配置
        JobCoreConfiguration jcc = JobCoreConfiguration
                .newBuilder("myScriptJob","0/10 * * * * ?",2)
                .misfire(false)
                .build();

        //job类型配置
        ScriptJobConfiguration jtc = new ScriptJobConfiguration(jcc,"d:/test.cmd");

        //job根的配置（LiteJobConfiguration）
        LiteJobConfiguration ljc = LiteJobConfiguration
                .newBuilder(jtc)
                .overwrite(true)
                .build();

        return ljc;
    }

}
