package com.java.seckill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.seckill.entity.MeiteSeckillEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 秒杀库存表
 *
 * @author jiangli
 * @email 31346337@qq.com
 * @date 2019-07-17 21:38:21
 */
@Mapper
public interface MeiteSeckillDao extends BaseMapper<MeiteSeckillEntity> {

	//使用mysql数据库的行锁去防止超卖,属于悲观锁
	int decrementStock(Long seckillProductId);

	//使用版本号防止超卖,乐观锁
	int updateStock(@Param("seckillProductId") Long seckillProductId, @Param("version") Long version);
}
