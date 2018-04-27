package com.github.binarywang.demo.spring.processor.text;

import java.util.Map;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import com.github.binarywang.demo.spring.entity.message.category.WxMpTextMessage;

public abstract class AbstractTextProcessor {

	public abstract WxMpXmlOutMessage processor(WxMpTextMessage msg, Map<String, Object> context,
			WxMpService wxMpService, WxSessionManager sessionManager);
}
