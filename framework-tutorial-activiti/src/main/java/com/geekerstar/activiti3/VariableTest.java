package com.geekerstar.activiti3;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程变量的测试
 */
public class VariableTest {


    //完成任务  zhangsan  -----lishi----判断流程变量的请假天数,1天----分支：人事经理存档(zhaoliu)
    public static void main3(String[] args) {
        //1.得到ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到TaskService
        TaskService taskService = processEngine.getTaskService();

        //3.查询当前用户是否有任务
        String key = "myProcess_1";
        Task task = taskService.createTaskQuery().processDefinitionKey(key)
                .taskAssignee("zhaoliu").singleResult();

        //4.判断task!=null,说明当前用户有任务
        if(task!=null){
            taskService.complete(task.getId());
            System.out.println("任务执行完毕");
        }

    }


    //启动流程实例，同时还要设置流程变量的值
    // act_ge_bytearray
    // act_ru_variable
    public static void main(String[] args) {
        //1.得到ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RuntimeService
        RuntimeService runtimeService =  processEngine.getRuntimeService();

        //3.流程定义的key问题   myProcess_1
        String key = "myProcess_1";
        Map<String ,Object> map = new HashMap<>();

        Holiday holiday = new Holiday();
        holiday.setNum(5F);
        map.put("holiday",holiday);

        //4.启动流程实例，并且设置流程变量的值
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, map);

        //5.输出实例信息
        System.out.println(processInstance.getName());
        System.out.println(processInstance.getProcessDefinitionId());

    }





    //新的请假流程定义的部署
    public static void main1(String[] args) {
        //1.得到ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.部署
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("diagram/holiday4.bpmn")
                .addClasspathResource("diagram/holiday4.png")
                .name("请假流程-流程变量")
                .deploy();

        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
    }
}
