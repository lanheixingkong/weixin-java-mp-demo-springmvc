package com.github.binarywang.demo.spring.processor.text;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.spring.builder.TextBuilder;
import com.github.binarywang.demo.spring.constants.text.JieYouZaHuoDian;
import com.github.binarywang.demo.spring.constants.text.KeyWord;
import com.github.binarywang.demo.spring.constants.text.WxSessionAttributeKey;
import com.github.binarywang.demo.spring.dialog.JieYouZaHuoDianDialog;
import com.github.binarywang.demo.spring.entity.jieyouzahuodian.AddContent;
import com.github.binarywang.demo.spring.entity.jieyouzahuodian.Counseling;
import com.github.binarywang.demo.spring.entity.message.category.WxMpTextMessage;
import com.github.binarywang.demo.spring.entity.user.WxMpUserEx;
import com.github.binarywang.demo.spring.mapper.jieyouzahuodian.AddContentMapper;
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

	@Resource
	private AddContentMapper addContentMapper;

	@Override
	public WxMpXmlOutMessage processor(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		String content = msg.getContent();
		WxSession session = sessionManager.getSession(msg.getFromUser());
		String state = session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE) == null ? ""
				: (String) session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE);

		if (KeyWord.JieYouZaHuoDian.JIE_YOU_ZA_HUO_DIAN.equals(content)) {
			return start(msg, context, wxMpService, sessionManager);

		} else if (KeyWord.JieYouZaHuoDian.SEE_ADD_QUESTION.equals(content)) {
			// 查看追问
			return seeAddQuestion(msg, context, wxMpService, sessionManager);
		} else if (KeyWord.JieYouZaHuoDian.SEE_REPLY.equals(content)) {
			// 查看回信
			return seeReply(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDian.SIGN_UP.equals(state)) {
			return signUp(msg, context, wxMpService, sessionManager);

		} else if (JieYouZaHuoDian.INTRO.equals(state)) {

			return intro(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDian.WRITE_LETTER.equals(state)) {

			return confirmWriteLetter(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDian.CONFIRM_WRITE_LETTER.equals(state)) {

			return endWriteLetter(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDian.REWRITE_LETTER.equals(state)) {

			return rewriteLetter(msg, context, wxMpService, sessionManager, false);
		} else if (JieYouZaHuoDian.READ_LETTER.equals(state)) {

			return confirmAnswer(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDian.CONFIRM_ANSWER.equals(state)) {

			return replyLetter(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDian.REPLY_LETTER.equals(state)) {

			return endReplyLetter(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDian.REREPLY_LETTER.equals(state)) {

			return rereplyLetter(msg, context, wxMpService, sessionManager, false);
		} else if (JieYouZaHuoDian.FROM_USER_REPLY.equals(state)) {

			return fromUserReply(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDian.ADD_QUESTION.equals(state)) {

			return addQuestion(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDian.CONFIRM_ADD_QUESTION.equals(state)) {

			return confirmAddQuestion(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDian.REWRITE_ADD_QUESTION.equals(state)) {

			return saveAddQuestion(msg, context, wxMpService, sessionManager, false);
		} else if (JieYouZaHuoDian.SELECT_ADD_QUESTION.equals(state)) {

			return selectAddQuestion(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDian.SEE_ADD_QUESTION.equals(state)) {

			return confirmReplyAddQuestion(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDian.REPLY_ADD_QUESTION.equals(state)) {

			return replyAddQuestion(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDian.CONFIRM_REPLY_ADD_QUESTION.equals(state)) {

			return endReplyAddQuestion(msg, context, wxMpService, sessionManager);
		} else if (JieYouZaHuoDian.REREPLY_ADD_QUESTION.equals(state)) {

			return rereplyAddQuestion(msg, context, wxMpService, sessionManager, false);
		}
		return null;
	}

	private WxMpXmlOutMessage endReplyAddQuestion(WxMpTextMessage msg, Map<String, Object> context,
			WxMpService wxMpService, WxSessionManager sessionManager) {
		String content = msg.getContent();
		if (KeyWord.TWO.equals(content)) {
			WxSession session = sessionManager.getSession(msg.getFromUser());
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.REREPLY_ADD_QUESTION);
			return new TextBuilder().build(jieYouZaHuoDianDialog.rereplyLetter(), msg, (WeixinService) wxMpService);
		}

		return rereplyAddQuestion(msg, context, wxMpService, sessionManager, true);
	}

	private WxMpXmlOutMessage rereplyAddQuestion(WxMpTextMessage msg, Map<String, Object> context,
			WxMpService wxMpService, WxSessionManager sessionManager, boolean isGetSession) {
		// 保存回答
		WxSession session = sessionManager.getSession(msg.getFromUser());
		Long id = (Long) session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
		String content = isGetSession ? (String) session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_CONTENT)
				: msg.getContent();
		Counseling counseling = counselingMapper.selectById(id);
		if (JieYouZaHuoDian.COUNSELING_STATE_ADD_QUESTION == counseling.getState()) {

			AddContent addContent = new AddContent();
			addContent.setContent(content);
			addContent.setCounselingId(counseling.getId());
			addContent.setUserId(msg.getFromUser());
			addContent.setUserType(JieYouZaHuoDian.USER_TYPE_TO_USER);
			addContentMapper.insert(addContent);

			counseling.setRelId(addContent.getId());
			counseling.setState(JieYouZaHuoDian.COUNSELING_STATE_ADD_ANSWER);
			counselingMapper.updateState(counseling);

			session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE);
			session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
			session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_CONTENT);
			return new TextBuilder().build(jieYouZaHuoDianDialog.endReplyLetter(), msg, (WeixinService) wxMpService);
		}

		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.INTRO);
		session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
		return new TextBuilder().build(jieYouZaHuoDianDialog.timeout(), msg, (WeixinService) wxMpService);
	}

	private WxMpXmlOutMessage replyAddQuestion(WxMpTextMessage msg, Map<String, Object> context,
			WxMpService wxMpService, WxSessionManager sessionManager) {
		String content = msg.getContent();
		WxSession session = sessionManager.getSession(msg.getFromUser());
		Long id = (Long) session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
		Counseling counseling = counselingMapper.selectById(id);
		if (JieYouZaHuoDian.COUNSELING_STATE_ADD_QUESTION == counseling.getState()) {
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE,
					JieYouZaHuoDian.CONFIRM_REPLY_ADD_QUESTION);
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_CONTENT, content);

			return new TextBuilder().build(
					jieYouZaHuoDianDialog.replyLetter(counseling.getFromUserName(), content, getLetterDate()), msg,
					(WeixinService) wxMpService);
		}

		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.INTRO);
		session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
		return new TextBuilder().build(jieYouZaHuoDianDialog.timeout(), msg, (WeixinService) wxMpService);
	}

	private WxMpXmlOutMessage confirmReplyAddQuestion(WxMpTextMessage msg, Map<String, Object> context,
			WxMpService wxMpService, WxSessionManager sessionManager) {
		if (KeyWord.ONE.equals(msg.getContent())) {
			// 回信
			WxSession session = sessionManager.getSession(msg.getFromUser());
			Long id = (Long) session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
			Counseling counseling = counselingMapper.selectById(id);
			if (JieYouZaHuoDian.COUNSELING_STATE_ADD_QUESTION == counseling.getState()) {
				session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE,
						JieYouZaHuoDian.REPLY_ADD_QUESTION);
				return new TextBuilder().build(jieYouZaHuoDianDialog.confirmAnswer(), msg, (WeixinService) wxMpService);
			}

			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.INTRO);
			session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
			return new TextBuilder().build(jieYouZaHuoDianDialog.timeout(), msg, (WeixinService) wxMpService);
		}

		return start(msg, context, wxMpService, sessionManager);
	}

	private WxMpXmlOutMessage selectAddQuestion(WxMpTextMessage msg, Map<String, Object> context,
			WxMpService wxMpService, WxSessionManager sessionManager) {
		String content = msg.getContent();
		int num = 1;
		try {
			num = Integer.parseInt(content);
		} catch (Exception e) {
		}

		List<Counseling> list = counselingMapper.findAddQuestionByToUserId(msg.getFromUser());
		if (num < 1 || num > list.size()) {
			num = 1;
		}

		Counseling counseling = list.get(num - 1);
		AddContent addContent = addContentMapper.selectById(counseling.getRelId());
		WxSession session = sessionManager.getSession(msg.getFromUser());
		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.SEE_ADD_QUESTION);
		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER, counseling.getId());

		return new TextBuilder().build(jieYouZaHuoDianDialog.selectAddQuestion(addContent.getContent(),
				counseling.getFromUserName(), getLetterDate(addContent.getCreatedate())), msg,
				(WeixinService) wxMpService);
	}

	private WxMpXmlOutMessage seeAddQuestion(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {

		Integer count = counselingMapper.selectAddQuestionCountByToUserId(msg.getFromUser());
		if (count == null || count == 0) {
			return new TextBuilder().build(jieYouZaHuoDianDialog.noAddQuestion(), msg, (WeixinService) wxMpService);
		}

		WxSession session = sessionManager.getSession(msg.getFromUser());
		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.SELECT_ADD_QUESTION);
		return new TextBuilder().build(jieYouZaHuoDianDialog.selectAddQuestion(count), msg,
				(WeixinService) wxMpService);
	}

	private WxMpXmlOutMessage confirmAddQuestion(WxMpTextMessage msg, Map<String, Object> context,
			WxMpService wxMpService, WxSessionManager sessionManager) {
		String content = msg.getContent();
		if (KeyWord.TWO.equals(content)) {
			WxSession session = sessionManager.getSession(msg.getFromUser());
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.REWRITE_ADD_QUESTION);
			return new TextBuilder().build(jieYouZaHuoDianDialog.rewriteLetter(), msg, (WeixinService) wxMpService);
		}

		return saveAddQuestion(msg, context, wxMpService, sessionManager, true);
	}

	private WxMpXmlOutMessage saveAddQuestion(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager, boolean isGetSession) {
		// 保存提问
		WxSession session = sessionManager.getSession(msg.getFromUser());
		String content = isGetSession ? (String) session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_CONTENT)
				: msg.getContent();
		Counseling counseling = counselingMapper.selectUnfinishedCounselingByFromUserId(msg.getFromUser());

		AddContent addContent = new AddContent();
		addContent.setContent(content);
		addContent.setCounselingId(counseling.getId());
		addContent.setUserId(msg.getFromUser());
		addContent.setUserType(JieYouZaHuoDian.USER_TYPE_FROM_USER);
		addContentMapper.insert(addContent);

		counseling.setRelId(addContent.getId());
		counseling.setState(JieYouZaHuoDian.COUNSELING_STATE_ADD_QUESTION);
		counselingMapper.updateState(counseling);

		session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE);
		session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_CONTENT);
		return new TextBuilder().build(jieYouZaHuoDianDialog.endWriteLetter(), msg, (WeixinService) wxMpService);
	}

	private WxMpXmlOutMessage addQuestion(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		String content = msg.getContent();
		WxMpUserEx user = wxMpUserMapper.selectByOpenId(msg.getFromUser());
		WxSession session = sessionManager.getSession(msg.getFromUser());
		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.CONFIRM_ADD_QUESTION);
		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_CONTENT, content);

		return new TextBuilder().build(
				jieYouZaHuoDianDialog.confirmWriteLetter(content, user.getNickname(), getLetterDate()), msg,
				(WeixinService) wxMpService);
	}

	private WxMpXmlOutMessage fromUserReply(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {

		String content = msg.getContent();
		if (KeyWord.ONE.equals(content)) {
			// 继续追问
			WxSession session = sessionManager.getSession(msg.getFromUser());
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.ADD_QUESTION);

			return new TextBuilder().build(jieYouZaHuoDianDialog.addQuestion(), msg, (WeixinService) wxMpService);
		} else if (KeyWord.TWO.equals(content)) {
			// 结束询问
			WxSession session = sessionManager.getSession(msg.getFromUser());
			session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE);
			session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);

			counselingMapper.finishCounselingByFromUserId(msg.getFromUser());

			return new TextBuilder().build(jieYouZaHuoDianDialog.finishCounseling(), msg, (WeixinService) wxMpService);
		}
		return null;
	}

	private WxMpXmlOutMessage seeReply(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		Counseling counseling = counselingMapper.selectUnfinishedCounselingByFromUserId(msg.getFromUser());
		if (counseling == null) {
			return new TextBuilder().build(jieYouZaHuoDianDialog.noQuestion(), msg, (WeixinService) wxMpService);
		}
		String answer = null;
		Date answerdate = null;
		int state = counseling.getState();
		if (state == JieYouZaHuoDian.COUNSELING_STATE_ADD_ANSWER) {
			AddContent addContent = addContentMapper.selectById(counseling.getRelId());
			answer = addContent.getContent();
			answerdate = addContent.getCreatedate();
		} else if (state == JieYouZaHuoDian.COUNSELING_STATE_ANSWER) {
			answer = counseling.getAnswer();
			answerdate = counseling.getAnswerdate();
		} else {
			return new TextBuilder().build(jieYouZaHuoDianDialog.noAnswer(), msg, (WeixinService) wxMpService);
		}

		WxSession session = sessionManager.getSession(msg.getFromUser());
		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.FROM_USER_REPLY);
		return new TextBuilder().build(
				jieYouZaHuoDianDialog.seeReply(counseling.getFromUserName(), answer, getLetterDate(answerdate)), msg,
				(WeixinService) wxMpService);
	}

	private WxMpXmlOutMessage confirmAnswer(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		String content = msg.getContent();
		WxSession session = sessionManager.getSession(msg.getFromUser());
		if (KeyWord.TWO.equals(content)) {
			// 换一封信看
			Object obj = session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
			session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
			if (obj != null) {
				counselingMapper.revertWaitAnswerState(Long.parseLong(obj.toString()), msg.getFromUser());
			}

			return readLetter(msg, context, wxMpService, sessionManager);
		}

		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.CONFIRM_ANSWER);
		return new TextBuilder().build(jieYouZaHuoDianDialog.confirmAnswer(), msg, (WeixinService) wxMpService);
	}

	private WxMpXmlOutMessage rereplyLetter(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager, boolean isGetSession) {

		// 保存回答
		WxSession session = sessionManager.getSession(msg.getFromUser());
		Long id = (Long) session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
		String content = isGetSession ? (String) session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_CONTENT)
				: msg.getContent();
		Counseling counseling = counselingMapper.selectById(id);
		if (JieYouZaHuoDian.COUNSELING_STATE_WAIT_ANSWER == counseling.getState()
				&& msg.getFromUser().equals(counseling.getToUserId())) {

			WxMpUserEx user = wxMpUserMapper.selectByOpenId(msg.getFromUser());
			counseling.setToUserName(user.getNickname());
			counseling.setAnswer(content);
			counselingMapper.updateAnswer(counseling);

			session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE);
			session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
			session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_CONTENT);
			return new TextBuilder().build(jieYouZaHuoDianDialog.endReplyLetter(), msg, (WeixinService) wxMpService);
		}

		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.INTRO);
		session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
		session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_CONTENT);
		return new TextBuilder().build(jieYouZaHuoDianDialog.timeout(), msg, (WeixinService) wxMpService);
	}

	private WxMpXmlOutMessage endReplyLetter(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		String content = msg.getContent();
		if (KeyWord.TWO.equals(content)) {
			WxSession session = sessionManager.getSession(msg.getFromUser());
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.REREPLY_LETTER);
			return new TextBuilder().build(jieYouZaHuoDianDialog.rereplyLetter(), msg, (WeixinService) wxMpService);
		}

		return rereplyLetter(msg, context, wxMpService, sessionManager, true);
	}

	private WxMpXmlOutMessage replyLetter(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		String content = msg.getContent();
		WxSession session = sessionManager.getSession(msg.getFromUser());
		Long id = (Long) session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
		Counseling counseling = counselingMapper.selectById(id);
		if (JieYouZaHuoDian.COUNSELING_STATE_WAIT_ANSWER == counseling.getState()
				&& msg.getFromUser().equals(counseling.getToUserId())) {
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.REPLY_LETTER);
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_CONTENT, content);

			return new TextBuilder().build(
					jieYouZaHuoDianDialog.replyLetter(counseling.getFromUserName(), content, getLetterDate()), msg,
					(WeixinService) wxMpService);
		}

		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.INTRO);
		session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER);
		return new TextBuilder().build(jieYouZaHuoDianDialog.timeout(), msg, (WeixinService) wxMpService);

	}

	private WxMpXmlOutMessage rewriteLetter(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager, boolean isGetSession) {

		// 保存提问
		WxSession session = sessionManager.getSession(msg.getFromUser());
		String content = isGetSession ? (String) session.getAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_CONTENT)
				: msg.getContent();
		WxMpUserEx user = wxMpUserMapper.selectByOpenId(msg.getFromUser());
		Counseling counseling = new Counseling();
		counseling.setQuestion(content);
		counseling.setFromUserId(user.getOpenId());
		counseling.setFromUserName(user.getNickname());
		counselingMapper.insert(counseling);

		session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE);
		session.removeAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_CONTENT);
		return new TextBuilder().build(jieYouZaHuoDianDialog.endWriteLetter(), msg, (WeixinService) wxMpService);
	}

	private WxMpXmlOutMessage confirmWriteLetter(WxMpTextMessage msg, Map<String, Object> context,
			WxMpService wxMpService, WxSessionManager sessionManager) {

		String content = msg.getContent();
		WxMpUserEx user = wxMpUserMapper.selectByOpenId(msg.getFromUser());
		WxSession session = sessionManager.getSession(msg.getFromUser());
		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.CONFIRM_WRITE_LETTER);
		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_CONTENT, content);

		return new TextBuilder().build(
				jieYouZaHuoDianDialog.confirmWriteLetter(content, user.getNickname(), getLetterDate()), msg,
				(WeixinService) wxMpService);
	}

	private WxMpXmlOutMessage endWriteLetter(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {

		String content = msg.getContent();
		if (KeyWord.TWO.equals(content)) {
			WxSession session = sessionManager.getSession(msg.getFromUser());
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.REWRITE_LETTER);
			return new TextBuilder().build(jieYouZaHuoDianDialog.rewriteLetter(), msg, (WeixinService) wxMpService);
		}

		return rewriteLetter(msg, context, wxMpService, sessionManager, true);
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
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.SIGN_UP);

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
				session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.READ_LETTER);
				session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_LETTER, counseling.getId());
				return new TextBuilder().build(
						jieYouZaHuoDianDialog.readLetter(count + 1, counseling.getQuestion(),
								counseling.getFromUserName(), getLetterDate(counseling.getCreatedate()), maxReplyCount),
						msg, (WeixinService) wxMpService);
			}
		}
	}

	private Counseling selectOneNotAnswerCounseling(String fromUserId) {
		// 先检查是否有还未解答的问题
		Counseling counseling = counselingMapper.selectWaitAnswerCounselingByToUserId(fromUserId);
		if (counseling == null) {
			int i = 0;
			do {
				// 查询一个随机问题
				counseling = counselingMapper.selectOneNotAnswerCounseling(fromUserId);
				if (counseling == null) {
					break;
				}

				i = counselingMapper.updateWaitAnswerState(counseling.getId(), fromUserId);

			} while (i == 0);
		}

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
		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.WRITE_LETTER);
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
		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.INTRO);
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
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.SIGN_UP);

			return new TextBuilder().build(jieYouZaHuoDianDialog.signUp(), msg, (WeixinService) wxMpService);
		}

		WxSession session = sessionManager.getSession(msg.getFromUser());
		session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDian.INTRO);
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
