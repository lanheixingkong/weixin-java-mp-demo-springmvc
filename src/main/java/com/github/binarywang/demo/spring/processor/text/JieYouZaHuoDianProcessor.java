package com.github.binarywang.demo.spring.processor.text;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.spring.builder.TextBuilder;
import com.github.binarywang.demo.spring.constants.text.JieYouZaHuoDianState;
import com.github.binarywang.demo.spring.constants.text.KeyWord;
import com.github.binarywang.demo.spring.constants.text.WxSessionAttributeKey;
import com.github.binarywang.demo.spring.dialog.JieYouZaHuoDianDialog;
import com.github.binarywang.demo.spring.entity.jieyouzahuodian.Counseling;
import com.github.binarywang.demo.spring.entity.message.category.WxMpTextMessage;
import com.github.binarywang.demo.spring.entity.user.WxMpUserEx;
import com.github.binarywang.demo.spring.mapper.jieyouzahuodian.CounselingMapper;
import com.github.binarywang.demo.spring.mapper.user.WxMpUserMapper;
import com.github.binarywang.demo.spring.service.WeixinService;

import me.chanjar.weixin.common.session.WxSession;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

@Service("jieYouZaHuoDianProcessor")
public class JieYouZaHuoDianProcessor extends AbstractTextProcessor {
	@Resource
	private WxMpUserMapper wxMpUserMapper;

	@Resource
	private JieYouZaHuoDianDialog jieYouZaHuoDianDialog;

	@Resource
	private CounselingMapper counselingMapper;

	@Override
	public WxMpXmlOutMessage processor(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		String content = msg.getContent();
		WxSession session = sessionManager.getSession(msg.getFromUser());
		String state = session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE) == null ? ""
				: (String) session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE);

		if (KeyWord.JieYouZaHuoDian.JIE_YOU_ZA_HUO_DIAN.equals(content)) {
			return start(msg, context, wxMpService, sessionManager);

		} else if (JieYouZaHuoDianState.SIGN_UP.equals(state)) {
			return signUp(msg, context, wxMpService, sessionManager);

		} else if (JieYouZaHuoDianState.INTRO.equals(state)) {

			return intro(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDianState.WRITE_LETTER.equals(state)) {

			return confirmWriteLetter(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDianState.CONFIRM_WRITE_LETTER.equals(state)) {

			return endWriteLetter(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDianState.REWRITE_LETTER.equals(state)) {

			return rewriteLetter(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDianState.READ_LETTER.equals(state)) {

			return replyLetter(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDianState.REPLY_LETTER.equals(state)) {

			return endReplyLetter(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDianState.REREPLY_LETTER.equals(state)) {

			return rereplyLetter(msg, context, wxMpService, sessionManager);
		}
		return null;
	}

	private WxMpXmlOutMessage rereplyLetter(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {

		// 保存提问
		WxSession session = sessionManager.getSession(msg.getFromUser());
		Long id = (Long) session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
		Counseling counseling = counselingMapper.selectById(id);
		if (JieYouZaHuoDianState.COUNSELING_STATE_WAIT_ANSWER == counseling.getState()
				&& msg.getFromUser().equals(counseling.getToUserId())) {

			WxMpUserEx user = wxMpUserMapper.selectByOpenId(msg.getFromUser());
			counseling.setToUserName(user.getNickname());
			counseling.setAnswer(msg.getContent());
			counselingMapper.updateAnswer(counseling);

			session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE);
			session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
			return new TextBuilder().build(jieYouZaHuoDianDialog.endReplyLetter(), msg, (WeixinService) wxMpService);
		}

		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDianState.INTRO);
		session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
		return new TextBuilder().build(jieYouZaHuoDianDialog.timeout(), msg, (WeixinService) wxMpService);
	}

	private WxMpXmlOutMessage endReplyLetter(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		String content = msg.getContent();
		if (KeyWord.TWO.equals(content)) {
			WxSession session = sessionManager.getSession(msg.getFromUser());
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDianState.REREPLY_LETTER);
			return new TextBuilder().build(jieYouZaHuoDianDialog.rereplyLetter(), msg, (WeixinService) wxMpService);
		}

		return rereplyLetter(msg, context, wxMpService, sessionManager);
	}

	private WxMpXmlOutMessage replyLetter(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		String content = msg.getContent();
		WxSession session = sessionManager.getSession(msg.getFromUser());
		Long id = (Long) session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
		Counseling counseling = counselingMapper.selectById(id);
		if (JieYouZaHuoDianState.COUNSELING_STATE_WAIT_ANSWER == counseling.getState()
				&& msg.getFromUser().equals(counseling.getToUserId())) {
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDianState.REPLY_LETTER);

			return new TextBuilder().build(
					jieYouZaHuoDianDialog.replyLetter(counseling.getFromUserName(), content, getLetterDate()), msg,
					(WeixinService) wxMpService);
		}

		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDianState.INTRO);
		session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
		return new TextBuilder().build(jieYouZaHuoDianDialog.timeout(), msg, (WeixinService) wxMpService);

	}

	private WxMpXmlOutMessage rewriteLetter(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {

		// 保存提问
		String content = msg.getContent();
		WxMpUserEx user = wxMpUserMapper.selectByOpenId(msg.getFromUser());
		Counseling counseling = new Counseling();
		counseling.setQuestion(content);
		counseling.setFromUserId(user.getOpenId());
		counseling.setFromUserName(user.getNickname());
		counselingMapper.insert(counseling);

		WxSession session = sessionManager.getSession(msg.getFromUser());
		session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE);
		return new TextBuilder().build(jieYouZaHuoDianDialog.endWriteLetter(), msg, (WeixinService) wxMpService);
	}

	private WxMpXmlOutMessage confirmWriteLetter(WxMpTextMessage msg, Map<String, Object> context,
			WxMpService wxMpService, WxSessionManager sessionManager) {

		String content = msg.getContent();
		WxMpUserEx user = wxMpUserMapper.selectByOpenId(msg.getFromUser());
		WxSession session = sessionManager.getSession(msg.getFromUser());
		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE,
				JieYouZaHuoDianState.CONFIRM_WRITE_LETTER);

		return new TextBuilder().build(
				jieYouZaHuoDianDialog.confirmWriteLetter(content, user.getNickname(), getLetterDate()), msg,
				(WeixinService) wxMpService);
	}

	private WxMpXmlOutMessage endWriteLetter(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {

		String content = msg.getContent();
		if (KeyWord.TWO.equals(content)) {
			WxSession session = sessionManager.getSession(msg.getFromUser());
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDianState.REWRITE_LETTER);
			return new TextBuilder().build(jieYouZaHuoDianDialog.rewriteLetter(), msg, (WeixinService) wxMpService);
		}

		return rewriteLetter(msg, context, wxMpService, sessionManager);
	}

	private WxMpXmlOutMessage intro(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		String content = msg.getContent();

		if (KeyWord.ONE.equals(content)) {
			// 写信
			return writeLetter(msg, context, wxMpService, sessionManager);
		} else if (KeyWord.TWO.equals(content)) {
			// 回信
			return readLetter(msg, context, wxMpService, sessionManager);
		} else if (KeyWord.THREE.equals(content)) {
			// 修改昵称
			WxSession session = sessionManager.getSession(msg.getFromUser());
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDianState.SIGN_UP);

			return new TextBuilder().build(jieYouZaHuoDianDialog.changeNickname(), msg, (WeixinService) wxMpService);
		} else if (KeyWord.NINE.equals(content)) {
			// 退出
			WxSession session = sessionManager.getSession(msg.getFromUser());
			session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE);
			WxMpUserEx user = wxMpUserMapper.selectByOpenId(msg.getFromUser());
			return new TextBuilder().build(jieYouZaHuoDianDialog.exit(user.getNickname()), msg,
					(WeixinService) wxMpService);
		}

		return null;
	}

	private int maxReplyCount = 5;

	private WxMpXmlOutMessage readLetter(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		// 查询已回信数量
		Integer count = counselingMapper.selectUnfinishedAnswerCountByToUserId(msg.getFromUser());
		count = count == null ? 0 : count;
		if (count >= maxReplyCount) {
			return new TextBuilder().build(jieYouZaHuoDianDialog.maxReply(), msg, (WeixinService) wxMpService);
		} else {

			Counseling counseling = selectOneNotAnswerCounseling(msg.getFromUser());
			if (counseling == null) {
				return new TextBuilder().build(jieYouZaHuoDianDialog.notLetter(), msg, (WeixinService) wxMpService);
			} else {

				WxSession session = sessionManager.getSession(msg.getFromUser());
				session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDianState.READ_LETTER);
				session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER, counseling.getId());
				return new TextBuilder().build(
						jieYouZaHuoDianDialog.readLetter(count + 1, counseling.getQuestion(),
								counseling.getFromUserName(), getLetterDate(counseling.getCreatedate()), maxReplyCount),
						msg, (WeixinService) wxMpService);
			}
		}
	}

	private Counseling selectOneNotAnswerCounseling(String fromUserId) {
		int i = 0;
		Counseling counseling = null;
		do {
			counseling = counselingMapper.selectOneNotAnswerCounseling(fromUserId);
			if (counseling == null) {
				break;
			}

			i = counselingMapper.updateWaitAnswerState(counseling.getId(), fromUserId);

		} while (i == 0);

		return counseling;
	}

	private WxMpXmlOutMessage writeLetter(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		// 检查是否有正在咨询的信件
		Counseling counseling = counselingMapper.selectUnfinishedCounselingByFromUserId(msg.getFromUser());
		if (counseling != null) {
			// TODO 对未结束的询问进行处理
			return new TextBuilder().build("你还有未结束的询问", msg, (WeixinService) wxMpService);
		}
		WxSession session = sessionManager.getSession(msg.getFromUser());
		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDianState.WRITE_LETTER);
		return new TextBuilder().build(jieYouZaHuoDianDialog.writeLetter(), msg, (WeixinService) wxMpService);
	}

	private WxMpXmlOutMessage signUp(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {

		String nickname = msg.getContent();
		if (nickname.length() > 20) {
			return new TextBuilder().build(jieYouZaHuoDianDialog.errorNickname(), msg, (WeixinService) wxMpService);
		}

		WxMpUserEx user = new WxMpUserEx();
		user.setNickname(nickname);
		user.setOpenId(msg.getFromUser());
		wxMpUserMapper.update(user);

		WxSession session = sessionManager.getSession(msg.getFromUser());
		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDianState.INTRO);
		return new TextBuilder().build(jieYouZaHuoDianDialog.intro(nickname), msg, (WeixinService) wxMpService);
	}

	/*
	 * 进店提示，1 登记姓名 状态：SIGN_UP 2 开始咨询(写信 回信 修改姓名) 状态：INTRO
	 */
	private WxMpXmlOutMessage start(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		WxMpUserEx user = wxMpUserMapper.selectByOpenId(msg.getFromUser());
		if (user != null && StringUtils.isBlank(user.getNickname())) {
			WxSession session = sessionManager.getSession(msg.getFromUser());
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDianState.SIGN_UP);

			return new TextBuilder().build(jieYouZaHuoDianDialog.signUp(), msg, (WeixinService) wxMpService);
		}

		WxSession session = sessionManager.getSession(msg.getFromUser());
		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDianState.INTRO);
		return new TextBuilder().build(jieYouZaHuoDianDialog.intro(user.getNickname()), msg,
				(WeixinService) wxMpService);
	}

	private String datePattern = "yyyy年MM月dd日";

	private String getLetterDate() {
		return DateTime.now().toString(datePattern);
	}

	private String getLetterDate(Date date) {
		return new DateTime(date).toString(datePattern);
	}

}
