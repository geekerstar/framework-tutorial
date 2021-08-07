package com.geekerstar.netty.manual.$999.common.protocol.domain;

import com.geekerstar.netty.manual.$999.common.protocol.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: tangtong
 * @date: 2020/6/5
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse implements Response {
    private boolean success;
    private String message;
}
