package com.geekerstar.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * @author geekerstar
 * date: 2019-07-17 20:17
 * description: 流程实例的启动,前提已经完成流程实例的部署
 *
 * 影响的表：
 * act_hi_actinst 已完成的活动信息
 * act_hi_identitylink 参与者信息
 * act_hi_procinst 流程实例
 * act_hi_taskinst 任务实例
 * act_ru_execution 执行表
 * act_ru_identitylink 参与者信息
 * act_ru_task 任务
 */
public class ActivitiStartInstance {
    public static void main(String[] args) {
        //1、得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、得到RunService对象
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //3、创建流程实例，需要知道流程定义的key
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday");
        //4、输出实例的相关信息
        System.out.println("流程部署ID"+processInstance.getDeploymentId());
        System.out.println("流程定义ID"+processInstance.getProcessDefinitionId());
        System.out.println("流程实例ID"+processInstance.getId());
        System.out.println("活动ID"+processInstance.getActivityId());
    }
}
