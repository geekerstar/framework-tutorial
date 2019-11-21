package com.geekerstar.activiti2;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;

/**
 * @author geekerstar
 * date: 2019-07-18 10:42
 * description: 全部流程实例挂起与激活
 */
public class SuspendProcessInstance {
    public static void main(String[] args) {
        //1、得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2、得到RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3、查询流程定义的对象
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("holiday")
                .singleResult();

        //4、得到当前流程定义的实例是否都为暂停状态
        boolean suspended = processDefinition.isSuspended();

        String processDefinitionId = processDefinition.getId();
        //5、判断
        if (suspended){
            //说明是暂停，就可以激活操作
            repositoryService.activateProcessDefinitionById(processDefinitionId,true,null);
            System.out.println("流程定义："+processDefinitionId+"激活");

        }else {
            repositoryService.suspendProcessDefinitionById(processDefinitionId,true,null);
            System.out.println("流程定义："+processDefinitionId+"挂起");
        }
    }
}
