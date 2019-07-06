package com.java.weixin.service;

import com.alibaba.fastjson.JSONObject;
import com.java.core.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Api(tags = "微信注册码验证码接口")
public interface VerifyCodeService {

    /**
     * 功能说明:根据手机号码验证码token是否正确
     *
     * @return
     */
    @ApiOperation(value = "根据手机号码验证码token是否正确")
    @PostMapping("/verifyWxCode")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phone", dataType = "String", required = true, value = "用户手机号码"),
            @ApiImplicitParam(paramType = "query", name = "wxCode", dataType = "String", required = true, value = "微信注册码") })
    Result<JSONObject> verifyWxCode(String phone, String wxCode);
}
