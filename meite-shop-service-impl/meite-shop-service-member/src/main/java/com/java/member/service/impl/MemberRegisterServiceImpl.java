package com.java.member.service.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.core.base.Result;
import com.java.core.utils.MD5Util;
import com.java.core.utils.RegexUtils;
import com.java.member.entity.UserDO;
import com.java.member.feign.VerifyCodeServiceFeign;
import com.java.member.input.UserInpDTO;
import com.java.member.mapper.UserMapper;
import com.java.member.service.MemberRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Auther: jiangli
 * @Date: 2019/6/4 16:35
 */
@RestController
public class MemberRegisterServiceImpl extends ServiceImpl<UserMapper, UserDO> implements MemberRegisterService {
    @Autowired
    private UserMapper userMapper;
	//SpringCloud服务如何通讯  feign
    @Autowired
    private VerifyCodeServiceFeign verifyCodeServiceFeign;

    /*
      注册
     */
    @Override
    @Transactional//实现类如果对象属性没有封装进来,要加@RequestBody
    public Result<JSONObject> register(@RequestBody UserInpDTO userInpDTO) {
        //判断验证码是否正确,会员调用微信接口
        Result<JSONObject> result = verifyCodeServiceFeign.verifyWxCode(userInpDTO.getMobile(), userInpDTO.getRegisterCode());
        if (!result.getFlag()) {
            return Result.error(result.getMsg());
        }
        String md5Pwd = MD5Util.MD5(userInpDTO.getPassword());
        userInpDTO.setPassword(md5Pwd);
		//将请求的inpDTO转换为DO
        UserDO userDO = Convert.convert(UserDO.class, userInpDTO);
        return userMapper.insert(userDO) > 0 ? Result.ok("注册成功") : Result.error("注册失败");
    }

    /*
      校验手机号码是否存在
     */
    @Override
    public Result existMobile(String mobile) {
        //校验参数
        if (StringUtils.isEmpty(mobile) || !RegexUtils.checkMobile(mobile)) {
            return Result.error("手机号码不正确!");
        }
        UserDO userDO = userMapper.selectOne(new LambdaQueryWrapper<UserDO>().eq(UserDO::getMobile, mobile));
        if (userDO == null) {
            return Result.error("用户不存在");
        }
	    return Result.ok();
    }
}