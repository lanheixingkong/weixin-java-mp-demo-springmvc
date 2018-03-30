package com.github.binarywang.demo.spring.mapper.user;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

public interface WxMpUserMapper {

	// List<WxMpUser> findList();
	int insert(WxMpUser msg);

	int update(WxMpUser msg);
	
	int unsubscribe(String openId);

	int delete(String openId);

	WxMpUser selectByOpenId(String openId);
}
