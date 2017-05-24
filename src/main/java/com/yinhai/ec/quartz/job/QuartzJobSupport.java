package com.yinhai.ec.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

/**
 * @author:TANQINGPING
 * @version:1.0 2016/9/12
 * package:com.yinhai.ec.quartz.job
 * <p>Title: QuartzJobSupport.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: 四川久远银海软件股份有限公司</p>
 */

public abstract class QuartzJobSupport extends QuartzJobBean {
    private ApplicationContext applicationContext;

    public <T> T getBean( String beanName, Class<T> clazz ) {
        return this.applicationContext.getBean(beanName, clazz);
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void excuteInternal(JobExecutionContext context) throws JobExecutionException {
        internalIter(context);
    }
    public abstract void internalIter(JobExecutionContext context);
}
