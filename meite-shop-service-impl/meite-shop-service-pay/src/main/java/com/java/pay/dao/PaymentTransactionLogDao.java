package com.java.pay.dao;

import com.java.pay.entity.PaymentTransactionLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付交易日志表 
 * 
 * @author jiangli
 * @email 31346337@qq.com
 * @date 2019-07-04 11:36:14
 */
@Mapper
public interface PaymentTransactionLogDao extends BaseMapper<PaymentTransactionLogEntity> {
	
}
