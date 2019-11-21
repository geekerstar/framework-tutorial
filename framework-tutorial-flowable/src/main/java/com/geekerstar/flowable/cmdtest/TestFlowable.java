package com.geekerstar.flowable.cmdtest;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author geekerstar
 * date: 2019/10/22 17:27
 * description:
 */
public class TestFlowable {
    public static void main(String[] args) {

        // 初始化ProcessEngine流程引擎实例
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/flowable_springboot?useUnicode=true&characterEncoding=utf-8&useSSL=false")
                .setJdbcUsername("root")
                .setJdbcPassword("root")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        ProcessEngine processEngine = cfg.buildProcessEngine();

        // 将流程定义部署至Flowable引擎
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/holiday-request.bpmn20.xml")
                .deploy();

        // 通过API查询验证流程定义已经部署在引擎中
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();

        System.out.println("流程定义名："+processDefinition.getName());

        // 启动流程实例,填写表单
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入姓名：");
        String employee = scanner.nextLine();

        System.out.println("你想请几天假：");
        Integer holidays = Integer.valueOf(scanner.nextLine());

        System.out.println("请假理由：");
        String description = scanner.nextLine();

        // 启动流程实例
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String,Object> variables = new HashMap<String, Object>();
        variables.put("employee",employee);
        variables.put("holidays",holidays);
        variables.put("description",description);
        ProcessInstance holidayRequest = runtimeService.startProcessInstanceByKey("holidayRequest", variables);

        // 通过TaskService创建一个TaskQuery获取实际的任务列表
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("您有"+tasks.size()+"个待办任务");
        for (int i = 0; i < tasks.size(); i++){
            System.out.println((i+1)+")"+tasks.get(i).getName());
        }

        // 使用任务Id获取特定流程实例的变量
        System.out.println("您想完成哪个任务：");
        int taskIndex = Integer.parseInt(scanner.nextLine());
        Task task = tasks.get(taskIndex - 1);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        System.out.println(processVariables.get("employee")+"想休"+processVariables.get("holidays")+"天假，是否同意？");

        // 完成任务，在排他网关中选择approved的那条
        boolean approved = scanner.nextLine().toLowerCase().equals("y");
        variables = new HashMap<>();
        variables.put("approved",approved);
        taskService.complete(task.getId(),variables);

        // 获取历史
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processDefinition.getId())
                .finished()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();

        for (HistoricActivityInstance activityInstance : activities){
            System.out.println(activityInstance.getActivityId() + " 花费 "
                    + activityInstance.getDurationInMillis() + " milliseconds");
        }


    }
}
