package com.geekerstar.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

/**
 * @author geekerstar
 * date: 2019-07-17 16:36
 * description: 测试Activiti所需要的25张表的生成
 */
public class ActivitiTest {

    @Test
    public void testGenTable(){
        //1、创建ProcessEngineConfiguration对象
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");

        //2、创建ProcessEngine对象
        ProcessEngine processEngine = configuration.buildProcessEngine();

        //3、输出Process是Engine对象
        System.out.println(processEngine);

    }

    /**
     * 一行代码创建
     */
    @Test
    public void testGenTable2(){
        //条件1、activiti配置文件名称:activiti.cfg.xml
        //条件2、bean的id="processEngineConfiguration"
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);
    }
}
