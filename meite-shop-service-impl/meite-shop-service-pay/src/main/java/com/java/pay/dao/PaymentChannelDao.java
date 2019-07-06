package com.java.pay.dao;

import com.java.pay.entity.PaymentChannelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付渠道 
 * 
 * @author jiangli
 * @email 31346337@qq.com
 * @date 2019-07-04 11:36:14
 */
@Mapper
public interface PaymentChannelDao extends BaseMapper<PaymentChannelEntity> {
	
}
