package com.geekerstar.flink.transformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author geekerstar
 * @date 2021/9/4 15:14
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Access {
    private Long time;
    private String domain;
    private Double traffic;
}

