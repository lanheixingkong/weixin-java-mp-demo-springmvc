package com.github.binarywang.demo.spring.mapper.message;

import me.chanjar.weixin.mp.bean.message.category.WxMpTextMessage;

public interface WxMpTextMessageMapper {

	// List<WxMpTextMessage> findList();
	int insert(WxMpTextMessage msg);

	int delete(Long id);
}
