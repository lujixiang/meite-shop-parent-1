package com.java.seckill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.seckill.entity.MeiteOrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀成功明细表
 * 
 * @author jiangli
 * @email 31346337@qq.com
 * @date 2019-07-17 21:38:21
 */
@Mapper
public interface MeiteOrderDao extends BaseMapper<MeiteOrderEntity> {
	
}
