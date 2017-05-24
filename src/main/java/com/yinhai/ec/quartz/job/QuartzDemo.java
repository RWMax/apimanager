package com.yinhai.ec.quartz.job;

import org.quartz.JobExecutionContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:TANQINGPING
 * @version:1.0 2016/9/12
 * package:com.yinhai.ec.quartz.job
 * <p>Title: QuartzDemo.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: 四川久远银海软件股份有限公司</p>
 */

public class QuartzDemo extends QuartzJobSupport {
    @Override
    public void internalIter(JobExecutionContext context) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "★★★★★★★★★★★");
    }
}
