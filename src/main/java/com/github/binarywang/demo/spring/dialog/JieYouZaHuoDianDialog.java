package com.github.binarywang.demo.spring.dialog;

import org.springframework.stereotype.Component;

@Component
public class JieYouZaHuoDianDialog extends BaseDialog {
	private static final String INTRO = "${1} 你好，欢迎来到解忧杂货店，在这里如果你正有什么困惑或烦恼，你可以写下来并投入到信箱中，"
			+ "我将会来替你分担。或者你也可以从信箱中获取别人的来信，给他（她）回信，替他解忧。\n\n回复【1】，写下你的烦恼，投入信箱中。"
			+ "\n回复【2】，获取别人的来信，为他（她）解忧。\n回复【3】修改自己名字。\n回复【9】退出杂货店。";
	private static final String SIGN_UP = "欢迎来到解忧杂货店，我的名字叫解忧，是这个杂货店的老板，很高兴认识你。请问你叫什么名字呢？\n\n回复你的【名字】，让老板认识认识你吧。";
	private static final String ERROR_NICKNAME = "Oops，你的名字太长了，我可能会记不住，你还有没有简短的昵称呢？\n\n回复一个简短【昵称】，让老板能够记住你吧。";
	private static final String EXIT = "${1} 再见，欢迎下次再来。";
	private static final String CHANGE_NICKNAME = "Oh，你想换个名字吗？那好的。\n\n回复你的【新名字】，让老板重新记住你吧。";

	private static final String WRITE_LETTER = "我猜你现在心里一定有很多烦恼憋着说不出来吧，没关系，将他们统统都写到信里面，让我来替你分担吧。\n\n请写下你的烦恼、焦虑或苦闷吧。";
	private static final String CONFIRM_WRITE_LETTER = "你的信件内容是：\n解忧老板 你好\n${1}\n{2}\n{3}\n\n回复【1】确认，回复【2】修改\n确认之后就不能再修改了哦~";
	private static final String END_WRITE_LETTER = "你的信件已经投递到信箱里面了，24小时之内就会有人给你回信，记得回店里来，回复【查看回信】，看看别人给你回复了什么。";
	private static final String REWRITE_LETTER = "请重新写下你的烦恼、焦虑或苦闷吧。";

	private static final String MAX_REPLY = "你已经给足够多的人分担烦恼了，辛苦你了，等之前这些人的烦恼解决完以后欢迎再来继续替别人分担吧。";
	private static final String READ_LETTER = "这是你分担的第${1}个人的来信：\n解忧老板 你好\n${2}\n{3}\n{4}\n\n请写好回信发给他（她）吧。\n------\n注意：每个人最多只能分担${5}个人的烦恼";
	private static final String REPLY_LETTER = "你的回信内容是：\n${1} 你好\n${2}\n解忧老板\n{3}\n\n回复【1】确认，回复【2】修改\n确认之后就不能再修改了哦~";
	private static final String END_REPLY_LETTER = "你的回信已经投递到信箱里面了，如果烦恼者看见后还有回信给你，记得回店里来，回复【查看追问】，看看别人又追问了什么。";
	private static final String REREPLY_LETTER = "请重新写下你的回信吧。";

	public String intro(String nickname) {
		return getDialog(INTRO, nickname);
	}

	public String signUp() {
		return getDialog(SIGN_UP);
	}

	public String errorNickname() {
		return getDialog(ERROR_NICKNAME);
	}

	public String exit(String nickname) {
		return getDialog(EXIT, nickname);
	}

	public String changeNickname() {
		return getDialog(CHANGE_NICKNAME);
	}

	public String writeLetter() {
		return getDialog(WRITE_LETTER);
	}

	public String confirmWriteLetter(String content, String nickname, String date) {
		return getDialog(CONFIRM_WRITE_LETTER, content, nickname, date);
	}

	public String endWriteLetter() {
		return getDialog(END_WRITE_LETTER);
	}

	public String rewriteLetter() {
		return getDialog(REWRITE_LETTER);
	}

	public String maxReply() {
		return getDialog(MAX_REPLY);
	}

	public String readLetter(int num, String content, String nickname, String date, int maxCount) {
		return getDialog(READ_LETTER, num + "", content, nickname, date, maxCount + "");
	}

	public String replyLetter(String nickname, String content, String date) {
		return getDialog(REPLY_LETTER, nickname, content, date);
	}

	public String rereplyLetter() {
		return getDialog(REREPLY_LETTER);
	}

	public String endReplyLetter() {
		return getDialog(END_REPLY_LETTER);
	}
}
