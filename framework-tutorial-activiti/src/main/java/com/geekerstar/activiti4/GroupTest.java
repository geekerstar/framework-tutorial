package com.geekerstar.activiti4;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

/**
 *  组任务的测试
 */
public class GroupTest {

    //8.任务交接，前提要保证当前用户是这个任务的负责人，这时候他才可以有权限去将任务交接给其他候选人
    /*public static void main(String[] args) {
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到TaskService对象
        TaskService taskService = processEngine.getTaskService();

        //3.设置一些参数，流程定义的key,用户
        String key = "myProcess_1";
        String assignee="zhangsan";

        //4.执行查询
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(key)
                .taskAssignee(assignee)  //设置任务的负责人
                .singleResult();
        //5.判断是否有这个任务
        if(task!=null){
            taskService.setAssignee(task.getId(),"lisi");//交接任务为lisi  ,交接任务就是一个候选人拾取用户的过程
            System.out.println("交接任务完成~!");
        }
    }*/

    //7.当前用户完成自己的任务
    public static void main(String[] args) {
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到TaskService对象
        TaskService taskService = processEngine.getTaskService();

        //3.设置一些参数，流程定义的key,用户
        String key = "myProcess_1";
        String assignee="lisi";

        //4.执行查询
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(key)
                .taskAssignee(assignee)  //设置任务的负责人
                .singleResult();
        //5.执行当前的任务
        if(task!=null){
            taskService.complete(task.getId());
            System.out.println("任务执行完毕!");
        }

    }

    //6.当前的用户查询自己的任务
    /*public static void main(String[] args) {
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到TaskService对象
        TaskService taskService = processEngine.getTaskService();

        //3.设置一些参数，流程定义的key,用户
        String key = "myProcess_1";
        String assignee="zhangsan";

        //4.执行查询
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey(key)
                .taskAssignee(assignee)  //设置任务的负责人
                .list();
        //5.输出
        for(Task task :list){
            System.out.println(task.getProcessInstanceId());
            System.out.println(task.getId());
            System.out.println(task.getName());
            System.out.println(task.getAssignee());//任务的执行人
        }
    }*/

    //5.测试zhangsan用户，来拾取组任务
    //抽取任务的过程就是将候选用户转化为真正任务的负责人（让任务的assignee有值）
  /* public static void main(String[] args) {
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到TaskService对象
        TaskService taskService = processEngine.getTaskService();

        //3.设置一些参数，流程定义的key,候选用户
        String key = "myProcess_1";
        String candidate_users="zhangsan";

        //4.执行查询
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(key)
                .taskCandidateUser(candidate_users)//设置候选用户
                .singleResult();
        if(task!=null){
            taskService.claim(task.getId(),candidate_users);//第一个参数任务ID,第二个参数为具体的候选用户名
            System.out.println("任务拾取完毕!");
        }
    }
*/
    //4.查询候选用户的组任务

    /*public static void main(String[] args) {
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到TaskService对象
        TaskService taskService = processEngine.getTaskService();

        //3.设置一些参数，流程定义的key,候选用户
        String key = "myProcess_1";
        String candidate_users="zhangsan";

        //4.执行查询
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey(key)
                .taskCandidateUser(candidate_users)//设置候选用户
                .list();
        //5.输出
        for(Task task :list){
            System.out.println(task.getProcessInstanceId());
            System.out.println(task.getId());
            System.out.println(task.getName());
            System.out.println(task.getAssignee());//为null，说明当前的zhangsan只是一个候选人，并不是任务的执行人
        }
    }*/


    //3.填写请假单的任务要执行完成
    /*public static void main(String[] args) {
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到TaskService对象
        TaskService taskService = processEngine.getTaskService();

        //3.查询当前用户的任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myProcess_1")
                .taskAssignee("xiaozhang")
                .singleResult();

        //4.处理任务,结合当前用户任务列表的查询操作的话,任务ID:task.getId()
        if(task!=null){
            taskService.complete(task.getId());
            System.out.println("用户任务执行完毕...");
        }


        //5.输出任务的id
        System.out.println(task.getId());
    }*/

    //2.启动流程实例
   /* public static void main(String[] args) {
            //1.得到ProcessEngine对象
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

            //2.得到RunService对象
            RuntimeService runtimeService = processEngine.getRuntimeService();

            //3.创建流程实例  流程定义的key需要知道 holiday
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_1");


            //4.输出实例的相关信息
            System.out.println("流程定义ID"+processInstance.getProcessDefinitionId());//holiday:1:4
            System.out.println("流程实例ID"+processInstance.getId());//2501
    }*/


    //1.部署流程定义
    /*public static void main(String[] args) {
        //1.创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.进行部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("diagram/holiday5.bpmn")  //添加bpmn资源
                // .addClasspathResource("diagram/holiday5.png")
                .name("请假申请单流程")
                .deploy();

        //4.输出部署的一些信息
        System.out.println(deployment.getName());
        System.out.println(deployment.getId());
    }*/
}
