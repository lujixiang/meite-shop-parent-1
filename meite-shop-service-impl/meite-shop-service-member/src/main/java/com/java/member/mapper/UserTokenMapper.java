package com.java.member.mapper;

import com.java.member.entity.UserTokenDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户登录token表
 * 
 * @author jiangli
 * @email 31346337@qq.com
 * @date 2019-06-10 17:48:05
 */
@Mapper
public interface UserTokenMapper extends BaseMapper<UserTokenDO> {
	
}
