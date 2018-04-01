package com.github.binarywang.demo.spring.handler;

import java.util.Map;

import javax.annotation.Resource;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import org.springframework.stereotype.Component;

import com.github.binarywang.demo.spring.async.SaveDBAsync;
import com.github.binarywang.demo.spring.builder.TextBuilder;
import com.github.binarywang.demo.spring.entity.message.category.WxMpTextMessage;
import com.github.binarywang.demo.spring.processor.text.AbstractTextProcessor;
import com.github.binarywang.demo.spring.processor.text.factory.TextProcessFactory;
import com.github.binarywang.demo.spring.service.WeixinService;

/**
 * @author Binary Wang
 */
@Component
public class TextMsgHandler extends AbstractHandler {
	@Resource
	private SaveDBAsync saveDBAsync;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {

		WeixinService weixinService = (WeixinService) wxMpService;

		WxMpTextMessage msg = new WxMpTextMessage(wxMessage);
		saveDBAsync.saveWxMpTextMessage(msg);

		AbstractTextProcessor textProcessor = TextProcessFactory.create(msg, context, wxMpService, sessionManager);
		if (textProcessor != null) {
			return textProcessor.processor(msg, context, wxMpService, sessionManager);
		}

		return new TextBuilder().build(msg.getContent(), wxMessage, weixinService);

	}

}
