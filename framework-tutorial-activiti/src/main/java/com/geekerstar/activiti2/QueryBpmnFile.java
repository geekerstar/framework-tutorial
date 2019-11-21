package com.geekerstar.activiti2;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author geekerstar
 * date: 2019-07-18 09:21
 * description:
 *
 * 1、从Activiti的act_ge_bytearray表中读取两个资源文件
 * 2、将两个资源文件保存到路径：xxx
 *
 * 技术方案：
 * 1、使用activiti的API来实现
 * 2、原理层面，可以使用JDBC对blob类型,clob类型数据的读取并保存
 * 3、IO流转换，最好commons_io.jar包轻松解决IO操作
 *
 * 真实应用场景：用户想查看这个请假流程具体有哪些步骤要走？
 */
public class QueryBpmnFile {

    public static void main(String[] args) throws IOException {
        //1、得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2、得到RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3、得到查询器ProcessDefinitionQuery对象
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        //4、设置查询调价
        processDefinitionQuery.processDefinitionKey("holiday");

        //5、执行查询操作，查询出想要的流程定义
        ProcessDefinition processDefinition = processDefinitionQuery.singleResult();

        //6、通过流程定义信息，得到部署ID
        String deploymentId = processDefinition.getDeploymentId();

        //7、通过repositoryService的方法，实现读取图片信息及bpmn文件信息（输入流）
        //getResourceAsStream()参数说明：第一个参数部署ID，第二个代表资源名称
        //processDefinition.getDiagramResourceName()代表获取的png图片资源的名称
        //processDefinition.getResourceName() 代表获取bpmn文件的名称
        InputStream pngIs = repositoryService.getResourceAsStream(deploymentId,processDefinition.getDiagramResourceName());
        InputStream bpmnIs = repositoryService.getResourceAsStream(deploymentId,processDefinition.getResourceName());

        //8、构造出OutputStream流
        OutputStream pngOs = new FileOutputStream("/work/activitiFile"+processDefinition.getDiagramResourceName());
        OutputStream bpmnOs = new FileOutputStream("/work/activitiFile"+processDefinition.getResourceName());

        //9、输入流，输出流的转换 commons-io.jar的方法
        IOUtils.copy(pngIs,pngOs);
        IOUtils.copy(bpmnIs,bpmnOs);

        //10、关闭流
        pngOs.close();
        bpmnOs.close();
        pngIs.close();
        bpmnIs.close();
    }

}
