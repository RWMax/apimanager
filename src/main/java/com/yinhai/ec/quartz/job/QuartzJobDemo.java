package com.yinhai.ec.quartz.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
* @package com.yinhai.ec.quartz.job
* <p>Title: QuartzJobDemo.java</p>
* <p>Description: 任务执行类 </p>
* @author 刘惠涛
* @date 2016年9月1日 上午9:50:49
* @version 1.0
 */
public class QuartzJobDemo implements Job{

	@Override
	public void execute(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		 System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "★★★★★★★★★★★");    
	}

}
