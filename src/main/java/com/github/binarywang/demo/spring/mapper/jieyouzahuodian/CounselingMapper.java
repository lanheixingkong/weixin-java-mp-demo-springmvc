package com.github.binarywang.demo.spring.mapper.jieyouzahuodian;

import org.apache.ibatis.annotations.Param;

import com.github.binarywang.demo.spring.entity.jieyouzahuodian.Counseling;

public interface CounselingMapper {

	int insert(Counseling counseling);

	int updateAnswer(Counseling counseling);

	int updateState(Counseling counseling);

	Counseling selectUnfinishedCounselingByFromUserId(String fromUserId);

	Integer selectUnfinishedAnswerCountByToUserId(String toUserId);

	Counseling selectOneNotAnswerCounseling(@Param(value = "fromUserId") String fromUserId);

	int updateWaitAnswerState(@Param(value = "id") Long id, @Param(value = "toUserId") String toUserId);

	Counseling selectById(Long id);
}
