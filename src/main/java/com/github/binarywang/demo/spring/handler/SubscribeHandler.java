package com.github.binarywang.demo.spring.handler;

import java.util.Map;

import javax.annotation.Resource;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import org.springframework.stereotype.Component;

import com.github.binarywang.demo.spring.async.SaveDBAsync;
import com.github.binarywang.demo.spring.builder.TextBuilder;
import com.github.binarywang.demo.spring.entity.user.WxMpUserEx;
import com.github.binarywang.demo.spring.service.WeixinService;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class SubscribeHandler extends AbstractHandler {
	@Resource
	private SaveDBAsync saveDBAsync;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {

		this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

		WeixinService weixinService = (WeixinService) wxMpService;

		// 获取微信用户基本信息
//		WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);
		WxMpUserEx userWxInfo = new WxMpUserEx();
		userWxInfo.setOpenId(wxMessage.getFromUser());
		userWxInfo.setSubscribe(true);

		if (userWxInfo != null) {
			saveDBAsync.saveWxMpUser(userWxInfo);
		}

		WxMpXmlOutMessage responseResult = null;
		try {
			responseResult = handleSpecial(wxMessage);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}

		if (responseResult != null) {
			return responseResult;
		}

		try {
			return new TextBuilder().build("感谢关注，回复【解忧杂货店】可以进入店里倾诉自己的烦恼哟", wxMessage, weixinService);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
	 */
	protected WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) throws Exception {
		// TODO
		return null;
	}

}
