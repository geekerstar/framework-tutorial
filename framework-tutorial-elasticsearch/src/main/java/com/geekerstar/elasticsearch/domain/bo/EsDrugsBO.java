package com.geekerstar.elasticsearch.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author geekerstar
 * @date 2021/9/1 21:23
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EsDrugsBO", description = "平台药品")
public class EsDrugsBO {

    @ApiModelProperty(value = "主键", example = "668ddecb8ff349e4b278670127300aa6")
    private String id;

    @ApiModelProperty(value = "药品名", example = "谷维素片")
    private String name;

    @ApiModelProperty(value = "药品批文", example = "国药准字Z44022406")
    private String approval_num;

    @ApiModelProperty(value = "规格", example = "10mg*100片")
    private String packing_spec;

    @ApiModelProperty(value = "生产厂家", example = "广州白云山制药有限公司")
    private String manufacturer;

    @ApiModelProperty(value = "服用方式", example = "口服")
    private String drugs_route;

    @ApiModelProperty(value = "用药间隔", example = "1日3次")
    private String drugs_inteval;

    @ApiModelProperty(value = "每次剂量", example = "3片")
    private String single_dosage;

}
