package com.java.pay.service;

import cn.hutool.json.JSONObject;
import com.java.core.base.Result;
import com.java.pay.input.dto.PayCratePayTokenDto;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

/**
 * @author jiangli
 * @date 2019/7/4 11:11
 * 支付交易服务
 */
public interface PaymentTransacService {

    /**
     * 创建支付令牌
     */
    @GetMapping("/createPayToken")
    Result<JSONObject> createPayToken(@Valid PayCratePayTokenDto payCratePayTokenDto);
}
