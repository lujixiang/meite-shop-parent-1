package com.java.pay.service.impl;

import cn.hutool.core.convert.Convert;
import com.java.core.base.Result;
import com.java.core.token.TokenUtil;
import com.java.pay.dao.PaymentTransactionDao;
import com.java.pay.entity.PaymentTransactionEntity;
import com.java.pay.out.dto.PayMentTransacDTO;
import com.java.pay.service.PayMentTransacInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangli
 * @date 2019/7/6 12:33
 */
@RestController
public class PayMentTransacInfoServiceImpl implements PayMentTransacInfoService {
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private PaymentTransactionDao paymentTransactionDao;

    @Override
    public Result<PayMentTransacDTO> tokenByPayMentTransac(String token) {
        // 1.验证token是否为空
        if (StringUtils.isEmpty(token)) {
            return Result.error("token参数不能空!");
        }
        // 2.使用token查询redisPayMentTransacID
        String value = tokenUtil.getToken(token);
        if (StringUtils.isEmpty(value)) {
            return Result.error("该Token可能已经失效或者已经过期");
        }
        Long transactionId = Long.parseLong(value);
        PaymentTransactionEntity paymentTransactionEntity = paymentTransactionDao.selectById(transactionId);
        if (paymentTransactionEntity == null) {
            return Result.error("未查询到该支付信息");
        }
        return Result.ok(Convert.convert(PayMentTransacDTO.class,paymentTransactionEntity));
    }
}
