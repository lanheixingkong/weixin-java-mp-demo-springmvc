package com.github.binarywang.demo.spring.mapper.user;

import com.github.binarywang.demo.spring.entity.user.WxMpUserEx;

public interface WxMpUserMapper {

	// List<WxMpUserEx> findList();
	int insert(WxMpUserEx user);

	int update(WxMpUserEx user);

	int unsubscribe(String openId);

	int delete(String openId);

	WxMpUserEx selectByOpenId(String openId);
}
