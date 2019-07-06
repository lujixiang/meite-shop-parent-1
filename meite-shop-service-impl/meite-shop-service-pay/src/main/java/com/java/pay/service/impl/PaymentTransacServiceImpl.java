package com.java.pay.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.java.core.base.Result;
import com.java.core.token.TokenUtil;
import com.java.pay.dao.PaymentTransactionDao;
import com.java.pay.entity.PaymentTransactionEntity;
import com.java.pay.input.dto.PayCratePayTokenDto;
import com.java.pay.service.PaymentTransacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author jiangli
 * @date 2019/7/4 11:24
 */
@RestController
public class PaymentTransacServiceImpl implements PaymentTransacService {
    @Autowired
    private PaymentTransactionDao paymentTransactionDao;
    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public Result<JSONObject> createPayToken(@Valid PayCratePayTokenDto payCratePayTokenDto) {
        //todo 参数校验
        String orderId = payCratePayTokenDto.getOrderId();
        Long payAmount = payCratePayTokenDto.getPayAmount();
        Long userId = payCratePayTokenDto.getUserId();
        // 2.将输入插入数据库中 待支付记录
        PaymentTransactionEntity paymentTransactionEntity = new PaymentTransactionEntity();
        paymentTransactionEntity.setOrderId(orderId);
        paymentTransactionEntity.setPayAmount(payAmount);
        paymentTransactionEntity.setUserId(userId);
        // 使用雪花算法 生成全局id (在页面展示用,不暴露主键id)
        paymentTransactionEntity.setPaymentId(String.valueOf(IdUtil.createSnowflake(1,1).nextId()));
        int result = paymentTransactionDao.insert(paymentTransactionEntity);
        if (result!=1) {
            return Result.error("系统错误!");
        }
        // 获取主键id
        Long payId = paymentTransactionEntity.getId();
        if (payId == null) {
            return Result.error("系统错误!");
        }

        // 3.生成对应支付令牌
        String keyPrefix = "pay_";
        String token = tokenUtil.createToken(keyPrefix, payId + "");
        JSONObject dataResult = new JSONObject();
        dataResult.put("token", token);

        return Result.ok(dataResult);
    }
}
