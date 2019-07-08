package com.java.point.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.point.entity.UserPointEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author jiangli
 * @email 31346337@qq.com
 * @date 2019-07-08 21:41:41
 */
@Mapper
public interface UserPointDao extends BaseMapper<UserPointEntity> {
	
}
