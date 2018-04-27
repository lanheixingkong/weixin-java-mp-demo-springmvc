package com.github.binarywang.demo.spring.mapper.jieyouzahuodian;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.binarywang.demo.spring.entity.jieyouzahuodian.Counseling;

public interface CounselingMapper {

	int insert(Counseling counseling);

	int updateAnswer(Counseling counseling);

	int updateState(Counseling counseling);

	Counseling selectUnfinishedCounselingByFromUserId(String fromUserId);

	Integer selectUnfinishedAnswerCountByToUserId(String toUserId);

	Counseling selectOneNotAnswerCounseling(@Param(value = "fromUserId") String fromUserId);

	Counseling selectWaitAnswerCounselingByToUserId(@Param(value = "toUserId") String toUserId);

	int updateWaitAnswerState(@Param(value = "id") Long id, @Param(value = "toUserId") String toUserId);

	int revertWaitAnswerState(@Param(value = "id") Long id, @Param(value = "toUserId") String toUserId);

	int finishCounselingByFromUserId(String fromUserId);

	Counseling selectById(Long id);

	List<Counseling> findAddQuestionByToUserId(String toUserId);
	
	Integer selectAddQuestionCountByToUserId(String toUserId);

	void deleteTimeoutWaitState(int timeout);
}
