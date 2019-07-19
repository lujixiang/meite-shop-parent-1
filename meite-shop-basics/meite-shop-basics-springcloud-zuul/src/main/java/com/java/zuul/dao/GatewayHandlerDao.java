package com.java.zuul.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.zuul.entity.GatewayHandlerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * 
 * @author jiangli
 * @email 31346337@qq.com
 * @date 2019-07-19 21:31:24
 */
@Mapper
public interface GatewayHandlerDao extends BaseMapper<GatewayHandlerEntity> {

	/**
	 * 获取第一个GatewayHandler
	 *
	 * @return
	 */
	@Select("SELECT  handler_name AS handlerName,handler_id AS handlerid ,prev_handler_id AS prevhandlerid ,next_handler_id AS nexthandlerid  ,is_open AS ISOPEN FROM gateway_handler WHERE is_open ='1' and prev_handler_id is null")
	GatewayHandlerEntity getFirstGatewayHandler();

	@Select("SELECT  handler_name AS handlerName,handler_id AS handlerid ,prev_handler_id AS prevhandlerid ,next_handler_id AS nexthandlerid  ,is_open AS ISOPEN FROM gateway_handler WHERE is_open ='1' and handler_id=#{handlerId}")
	GatewayHandlerEntity getByHandler(String handlerId);
	
}
