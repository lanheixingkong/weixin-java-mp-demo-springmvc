package com.github.binarywang.demo.spring.scheduler;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.spring.mapper.jieyouzahuodian.CounselingMapper;

@Service
public class JieYouZaHuoDianJob {
	@Resource
	private CounselingMapper counselingMapper;

	private static final int WAIT_TIMEOUT = 30;//分钟
	@Scheduled(cron = "* 0/30 * * * ? ")
	public void deleteTimeoutWaitState(){
		counselingMapper.deleteTimeoutWaitState(WAIT_TIMEOUT);
	}
}
