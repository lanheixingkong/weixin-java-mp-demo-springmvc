package com.github.binarywang.demo.spring.mapper.user;

import com.github.binarywang.demo.spring.entity.user.WxMpUserEx;

public interface WxMpUserMapper {

	// List<WxMpUserEx> findList();
	int insert(WxMpUserEx msg);

	int update(WxMpUserEx msg);
	
	int unsubscribe(String openId);

	int delete(String openId);

	WxMpUserEx selectByOpenId(String openId);
}
