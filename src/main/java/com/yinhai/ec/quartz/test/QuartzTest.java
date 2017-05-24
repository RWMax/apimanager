package com.yinhai.ec.quartz.test;

import com.yinhai.ec.quartz.job.QuartzDemo;
import com.yinhai.ec.quartz.job.QuartzJobDemo;
import com.yinhai.ec.quartz.manager.QuartzManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring-properties.xml", "classpath:config/quartz/spring-quartz.xml", "classpath:config/datasource/spring-datasource.xml"})
public class QuartzTest {

	@Autowired
	private QuartzManager quartzManager;

	@Test
	public void testQuartz() throws Exception {
		String job_name = "动态任务调度";
		System.out.println("【系统启动】开始(每1秒输出一次)...");
		quartzManager.addJob(job_name, QuartzDemo.class, "0/1 * * * * ?");

		Thread.sleep(5000);
		System.out.println("【修改时间】开始(每2秒输出一次)...");
		quartzManager.modifyJobTime(job_name, "10/2 * * * * ?");
		Thread.sleep(6000);
		System.out.println("【移除定时】开始...");
		quartzManager.removeJob(job_name);
		System.out.println("【移除定时】成功");

		System.out.println("【再次添加定时任务】开始(每10秒输出一次)...");
		quartzManager.addJob(job_name, QuartzJobDemo.class, "*/10 * * * * ?");
		Thread.sleep(60000);
		System.out.println("【移除定时】开始...");
		quartzManager.removeJob(job_name);
		System.out.println("【移除定时】成功");
	}
}
