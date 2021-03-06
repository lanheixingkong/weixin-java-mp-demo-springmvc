package com.github.binarywang.demo.spring.dialog;

import org.springframework.stereotype.Component;

@Component
public class JieYouZaHuoDianDialog extends BaseDialog {
	private static final String INTRO = "${1} 你好，欢迎来到解忧杂货店，在这里如果你正有什么困惑或烦恼，你可以写下来并投入到信箱中，"
			+ "我将会来替你分担。或者你也可以从信箱中获取别人的来信，给他（她）回信，替他解忧。\n\n回复【1】，写下你的烦恼，投入信箱中。"
			+ "\n回复【2】，获取别人的来信，为他（她）解忧。\n回复【3】修改自己名字。\n回复【查看回信】，查看老板给你写的回信\n回复【查看追问】，查看烦恼人给你写的回信\n回复【9】退出杂货店。";
	private static final String SIGN_UP = "欢迎来到解忧杂货店，我的名字叫解忧，是这个杂货店的老板，很高兴认识你。请问你叫什么名字呢？\n\n回复你的【名字】，让老板认识认识你吧。";
	private static final String ERROR_NICKNAME = "Oops，你的名字太长了，我可能会记不住，你还有没有简短的昵称呢？\n\n回复一个简短【昵称】，让老板能够记住你吧。";
	private static final String EXIT = "${1} 再见，欢迎下次再来。";
	private static final String CHANGE_NICKNAME = "Oh，你想换个名字吗？那好的。\n\n回复你的【新名字】，让老板重新记住你吧。";

	private static final String WRITE_LETTER = "我猜你现在心里一定有很多烦恼憋着说不出来，没关系，将他们统统都写到信里面，让我来替你分担吧。\n\n请写下你的烦恼、焦虑或苦闷吧。";
	private static final String CONFIRM_WRITE_LETTER = "你的信件内容是：\n\n解忧老板 你好：\n\n${1}\n\n${2}\n${3}\n\n回复【1】确认\n回复【2】修改\n确认之后就不能再修改了哦~";
	private static final String END_WRITE_LETTER = "你的信件已经投递到信箱里面了，24小时之内就会有人给你回信，记得回店里来，回复【查看回信】，看看别人给你回复了什么。";
	private static final String REWRITE_LETTER = "请重新写下你的烦恼、焦虑或苦闷吧。";

	private static final String MAX_REPLY = "你已经给足够多的人分担烦恼了，辛苦你了，等之前这些人的烦恼解决完以后欢迎再来继续替别人分担吧。";
	private static final String READ_LETTER = "这是你分担的第${1}个人的来信：\n\n解忧老板 你好：\n\n${2}\n\n${3}\n${4}\n\n回复【1】写回信发给他（她）\n回复【2】换一封来信\n------\n注意：每个人最多只能分担${5}个人的烦恼";
	private static final String REPLY_LETTER = "你的回信内容是：\n\n${1} 你好：\n\n${2}\n\n解忧老板\n${3}\n\n回复【1】确认，回复【2】修改\n确认之后就不能再修改了哦~";
	private static final String END_REPLY_LETTER = "你的回信已经投递到信箱里面了，如果烦恼者看见后还有回信给你，记得回店里来，回复【查看追问】，看看别人又追问了什么。";
	private static final String REREPLY_LETTER = "请重新写下你的回信吧。";
	private static final String CONFIRM_ANSWER = "请认真写下你给对方的回信吧。";

	private static final String NOT_LETTER = "现在还没有人写下烦恼，晚点再来试试吧。";
	private static final String TIMEOUT = "Oops，你看的这个烦恼已经解决掉了，重新从信箱中获取别人的来信吧。\n\n回复【1】，写下你的烦恼，投入信箱中。\n"
			+ "\n回复【2】，获取别人的来信，为他（她）解忧。\n回复【3】修改自己名字。\n回复【9】退出杂货店。";

	private static final String SEE_REPLY = "${1} 你好\n\n${2}\n\n解忧老板\n${3}\n\n回复【1】回信给解忧老板\n回复【2】结束询问";
	private static final String FINISH_COUNSELING = "希望我的回信能带给你一点点的帮助，如果以后又遇到什么烦恼，欢迎你随时回来。^_^";
	private static final String ADD_QUESTION = "继续将你的烦恼、焦虑或苦闷写信告诉我吧。";
	private static final String NO_ANSWER = "老板还没有给你回复，请晚点再来看看吧。";
	private static final String NO_QUESTION = "你还没有向老板写过信哟。";

	private static final String NO_ADD_QUESTION = "还没有人继续找你解忧。";
	private static final String SELECT_ADD_QUESTION = "有${1}个人继续回信给你，回复一个【数字】，查看其中一个人的来信吧。";
	private static final String SEE_ADD_QUESTION = "解忧老板 你好：\n\n${1}\n\n${2}\n${3}\n\n回复【1】写回信发给他（她）\n回复【2】放弃回信";

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

	public String confirmAnswer() {
		return getDialog(CONFIRM_ANSWER);
	}

	public String notLetter() {
		return getDialog(NOT_LETTER);
	}

	public String timeout() {
		return getDialog(TIMEOUT);
	}

	public String seeReply(String nickname, String content, String date) {
		return getDialog(SEE_REPLY, nickname, content, date);
	}

	public String finishCounseling() {
		return getDialog(FINISH_COUNSELING);
	}

	public String addQuestion() {
		return getDialog(ADD_QUESTION);
	}

	public String noAnswer() {
		return getDialog(NO_ANSWER);
	}

	public String noQuestion() {
		return getDialog(NO_QUESTION);
	}

	public String noAddQuestion() {
		return getDialog(NO_ADD_QUESTION);
	}

	public String selectAddQuestion(int count) {
		return getDialog(SELECT_ADD_QUESTION, count + "");
	}

	public String selectAddQuestion(String content, String nickname, String date) {
		return getDialog(SEE_ADD_QUESTION, content, nickname, date);
	}
}
