package com.geekerstar.activiti2;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * @author geekerstar
 * date: 2019-07-18 10:15
 * description:
 *
 * 启动流程实例，添加进BusinessKey
 *
 * 本质：act_ru_execution表中的BusinessKey的字段
 */
public class BusinessKeyAdd {


    public static void main(String[] args) {
        //1、得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2、得到RuntimeService对象
        RuntimeService runtimeService = processEngine.getRuntimeService();

        //3、启动流程实例，同时还要指定业务标识Businesskey，它本身就是请假单的id
        //第一个参数：流程定于Key
        //第二个参数：业务标识Businesskey
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday", "1001");

        //4、输出processInstance相关的熟悉
        System.out.println(processInstance.getBusinessKey());

    }
}
