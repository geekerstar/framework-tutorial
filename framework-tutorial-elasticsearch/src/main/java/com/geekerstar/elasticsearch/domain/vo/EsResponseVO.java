package com.geekerstar.elasticsearch.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author geekerstar
 * @date 2021/9/2 10:57
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "EsResponseVO", description = "ES出参")
public class EsResponseVO {

    @ApiModelProperty(value = "总记录数", example = "1760")
    private Long total;

    @ApiModelProperty(value = "当前页数", example = "0")
    private String currentPage;

    @ApiModelProperty(value = "总页数", example = "176")
    private Long totalPage;

    @ApiModelProperty(value = "查询结果")
    private Object resultList;
}
