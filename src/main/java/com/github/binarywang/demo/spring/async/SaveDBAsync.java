package com.github.binarywang.demo.spring.async;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.github.binarywang.demo.spring.mapper.message.WxMpTextMessageMapper;
import com.github.binarywang.demo.spring.mapper.user.WxMpUserMapper;

import me.chanjar.weixin.mp.bean.message.category.WxMpTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Component
public class SaveDBAsync {

	@Resource
	private WxMpUserMapper wxMpUserMapper;

	@Resource
	private WxMpTextMessageMapper wxMpTextMessageMapper;

	@Async
	public void saveWxMpUser(WxMpUser user) {
		WxMpUser wmu = wxMpUserMapper.selectByOpenId(user.getOpenId());
		if (wmu != null) {
			wxMpUserMapper.update(user);
		} else {
			wxMpUserMapper.insert(user);
		}
	}

	@Async
	public void saveWxMpTextMessage(WxMpTextMessage msg) {
		wxMpTextMessageMapper.insert(msg);
		String openId = msg.getFromUser();
		WxMpUser wmu = wxMpUserMapper.selectByOpenId(openId);
		if (wmu == null) {
			WxMpUser user = new WxMpUser();
			user.setOpenId(openId);
			user.setSubscribe(true);
			wxMpUserMapper.insert(user);
		}
	}
}
