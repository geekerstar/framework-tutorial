package com.geekerstar.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;

/**
 * @author geekerstar
 * date: 2019-07-17 20:31
 * description: 处理当前用户的任务
 *
 * 背后操作的表：
 * act_hi_actinst
 * act_hi_identitylink
 * act_hi_taskinst
 * act_ru_execution
 * act_ru_indentitylink
 * act_ru_task
 */
public class ActivitiCompleteTask {
    public static void main(String[] args) {
        //1、得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、得到TaskService
        TaskService taskService = processEngine.getTaskService();

        //3、处理任务，结合当前用户任务列表的查询操作，任务ID已知2505
        taskService.complete("2505");

    }
}
