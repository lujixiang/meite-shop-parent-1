package com.java.core.base;

import com.java.core.constants.StatusCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Data;

/**
 * @Auther: jiangli
 * @Date: 2019/6/4 09:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Boolean flag;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 返回
     */
    private T data;

    // 返回错误，可以传code和msg
    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(false,code, msg, null);
    }

    // 返回错误，可以传msg
    public static <T> Result<T> error(String msg) {
        return new Result<>(false, StatusCode.ERROR, msg, null);
    }

    // 返回成功，可以传data值
    public static <T> Result<T> ok(T data) {
        return new Result<>(true,StatusCode.OK, "成功", data);
    }

    // 返回成功，沒有data值
    public static <T> Result<T> ok() {
        return new Result<>(true,StatusCode.OK, "成功", null);
    }

    // 返回成功，可以传msg，沒有data值
    public static <T> Result<T> ok(String msg) {
        return new Result<>(true,StatusCode.OK, msg, null);
    }

}
