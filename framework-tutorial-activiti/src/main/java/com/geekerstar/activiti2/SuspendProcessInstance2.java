package com.geekerstar.activiti2;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * @author geekerstar
 * date: 2019-07-18 10:42
 * description: 单个流程实例挂起与激活
 */
public class SuspendProcessInstance2 {
    public static void main(String[] args) {
        //1、得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2、得到RepositoryService
        RuntimeService runtimeService = processEngine.getRuntimeService();

        //3、查询流程定义的对象
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId("2501")
                .singleResult();

        //4、得到当前流程定义的实例是否都为暂停状态
        boolean suspended = processInstance.isSuspended();

        String processInstanceId = processInstance.getId();

        //5、判断
        if (suspended){
            //说明是暂停，可以激活操作
            runtimeService.activateProcessInstanceById(processInstanceId);
            System.out.println("流程"+processInstanceId+"激活");
        }else {
            runtimeService.suspendProcessInstanceById(processInstanceId);
            System.out.println("流程定义："+processInstanceId+"挂起");

        }

    }
}
