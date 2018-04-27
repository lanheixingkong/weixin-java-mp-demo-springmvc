package com.github.binarywang.demo.spring.mapper.message;

import com.github.binarywang.demo.spring.entity.message.category.WxMpTextMessage;

public interface WxMpTextMessageMapper {

	// List<WxMpTextMessage> findList();
	int insert(WxMpTextMessage msg);

	int delete(Long id);
}
