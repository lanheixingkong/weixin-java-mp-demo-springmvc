package com.github.binarywang.demo.spring.processor.text.factory;

import java.util.Map;

import me.chanjar.weixin.common.session.WxSession;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;

import com.github.binarywang.demo.spring.constants.text.KeyWord;
import com.github.binarywang.demo.spring.constants.text.WxSessionAttributeKey;
import com.github.binarywang.demo.spring.entity.message.category.WxMpTextMessage;
import com.github.binarywang.demo.spring.processor.text.AbstractTextProcessor;
import com.github.binarywang.demo.spring.processor.text.JieYouZaHuoPuProcessor;

public class TextProcessFactory {

	public static AbstractTextProcessor create(WxMpTextMessage msg, Map<String, Object> context,
			WxMpService wxMpService, WxSessionManager sessionManager) {

		if (isJieYouZaHuoPu(msg, context, wxMpService, sessionManager)) {
			return new JieYouZaHuoPuProcessor();
		}

		return null;
	}

	private static boolean isJieYouZaHuoPu(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		String content = msg.getContent();
		if (KeyWord.JIE_YOU_ZA_HUO_DIAN.equals(content) || KeyWord.SEE_REPLY.equals(content)) {

			return true;
		} else {
			WxSession session = sessionManager.getSession(msg.getFromUser());
			return session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE) != null;
		}
	}
}
