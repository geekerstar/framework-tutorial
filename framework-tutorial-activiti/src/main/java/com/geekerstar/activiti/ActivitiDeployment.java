package com.geekerstar.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * @author geekerstar
 * date: 2019-07-17 19:58
 * description: 流程定义的部署
 */
public class ActivitiDeployment {


    /**
     * 改为zip文件部署
     * @param args
     */
    public static void main(String[] args) {
        //1、创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2、得到RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3、转化出ZipInputStream流对象
        InputStream is = ActivitiDeployment.class.getClassLoader().getResourceAsStream("diagram/holidayBPMN.zip");

        //将inputStream流转化为ZipInputStream流
        ZipInputStream zipInputStream = new ZipInputStream(is);

        //3、进行部署
        Deployment deployment = repositoryService.createDeployment()
                //添加资源
                .addZipInputStream(zipInputStream)
                .name("请假申请流程")
                .deploy();
        //4、输出部署的一些信息
        System.out.println(deployment.getName());
        System.out.println(deployment.getId());
    }

//    // 部署影响三张表，act_re_deployment部署信息、act_re_procdef流程定义的一些信息、act_ge_bytearray流程定义的bpmn文件及png文件
//    //流程定义部署
//    public static void main(String[] args) {
//        //1、创建ProcessEngine对象
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        //2、得到RepositoryService实例
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//
//        //3、进行部署
//        Deployment deployment = repositoryService.createDeployment()
//                //添加资源
//                .addClasspathResource("diagram/holiday.bpmn.xml")
//                .addClasspathResource("diagram/holiday.png")
//                .name("请假申请流程")
//                .deploy();
//        //4、输出部署的一些信息
//        System.out.println(deployment.getName());
//        System.out.println(deployment.getId());
//    }

}
