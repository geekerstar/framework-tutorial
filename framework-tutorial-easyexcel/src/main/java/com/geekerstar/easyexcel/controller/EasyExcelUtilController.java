package com.geekerstar.easyexcel.controller;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.geekerstar.easyexcel.model.ExportTestModel;
import com.geekerstar.easyexcel.util.ExcelException;
import com.geekerstar.easyexcel.util.ExcelUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Log4j
@Controller
@RequestMapping("easyExcelUtil")
public class EasyExcelUtilController extends BaseController{



    @GetMapping("getExportData")
    @ResponseBody
    public Object getExportData(HttpServletResponse response){
        List<ExportTestModel> list = new ArrayList<>();
        for(int i = 0 ;i<=1000;i++){
            list.add(new ExportTestModel(i));
        }
        try {
            ExcelUtil.writeExcel(response,list,"导出测试","sheet1", ExcelTypeEnum.XLSX,ExportTestModel.class);
        } catch (ExcelException e) {
            log.info(e);
        }
        return success("导出成功");
    }

    @PostMapping("importExcel")
    @ResponseBody
    public Object importExcel(MultipartHttpServletRequest request){
        Iterator<String> itr = request.getFileNames();
        String uploadedFile = itr.next();
        List<MultipartFile> files = request.getFiles(uploadedFile);
        if (CollectionUtils.isEmpty(files)) {
            return fail("请选择文件！");
        }
        try {
            List<ExportTestModel> list = ExcelUtil.readExcel(files.get(0),ExportTestModel.class);
            return success(JSON.toJSONString(list, SerializerFeature.PrettyFormat));
        } catch (ExcelException e) {
            log.info(e);
            return fail(""+e.getMessage());
        }
    }
}
