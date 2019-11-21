package com.geekerstar.activiti2;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;

import java.util.List;

/**
 * @author geekerstar
 * date: 2019-07-18 08:53
 * description: 查询流程定义信息
 */
public class QueryProcessDefinition {
    public static void main(String[] args) {
        //1、得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2、创建RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3、得到ProcessDefinitionQuery对象，可以认为它就是一个查询器
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        //4、设置条件，并查询出当前的所有流程定义 查询条件：流程定义的key=holiday
        //orderByProcessDefinitionVersion() 设置排序方式，根据流程的另一的版本号进行排序
        List<ProcessDefinition> list = processDefinitionQuery.processDefinitionKey("holiday")
                .orderByProcessDefinitionVersion()
                .desc()
                .list();

        //5、输出流程定义信息
        for(ProcessDefinition processDefinition : list){
            System.out.println("流程定义ID："+processDefinition.getId());
            System.out.println("流程定义名称："+processDefinition.getName());
            System.out.println("流程定义的key："+processDefinition.getKey());
            System.out.println("流程定义的版本号："+processDefinition.getVersion());
            System.out.println("流程部署的ID："+processDefinition.getDeploymentId());

        }
    }
}
