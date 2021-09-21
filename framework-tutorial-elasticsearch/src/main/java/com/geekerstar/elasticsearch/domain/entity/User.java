package com.geekerstar.elasticsearch.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author geekerstar
 * @date 2021/9/20 09:57
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "User", description = "用户对象")
public class User {

    @ApiModelProperty(value = "主键",example = "123")
    private String id;

    @ApiModelProperty(value = "姓名",example = "w")
    private String name;

    @ApiModelProperty(value = "性别",example = "男")
    private String sex;

    @ApiModelProperty(value = "年龄",example = "23")
    private Integer age;
}
