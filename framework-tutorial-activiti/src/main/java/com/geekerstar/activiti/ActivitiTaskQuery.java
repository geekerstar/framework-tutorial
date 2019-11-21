package com.geekerstar.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * @author geekerstar
 * date: 2019-07-17 20:31
 * description: 查询当前用户的任务列表
 */
public class ActivitiTaskQuery {
    public static void main(String[] args) {
        //1、得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、得到TaskService
        TaskService taskService = processEngine.getTaskService();
        //3、根据流程定义的Key，负责人assign实现当前用户的任务列表查询
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey("holiday")
                .taskAssignee("zhangsan")
                .list();
        //4、任务列表的展示
        for (Task task : taskList){
            System.out.println("流程实例ID"+task.getProcessInstanceId());
            System.out.println("任务ID"+task.getId());
            System.out.println("任务负责人"+task.getAssignee());
            System.out.println("任务名称"+task.getName());
        }
    }
}
