package com.java.core.constants;

/**
 * 状态码实体类
 */
public class StatusCode {
public static final int OK=10000;//成功
public static final int ERROR =10001;//失败
public static final int LOGINERROR =10002;//用户名或密码错误
public static final int ACCESSERROR =10003;//权限不足
public static final int REMOTEERROR =10004;//远程调用失败
public static final int REPERROR =10005;//重复操作
public static final int NORESULT =10006;//无查询数据
}