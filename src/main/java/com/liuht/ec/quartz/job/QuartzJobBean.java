package com.liuht.ec.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;

/**
 * @author:TANQINGPING
 * @version:1.0 2016/9/12
 * package:com.liuht.ec.quartz.job
 * <p>Title: QuartzJobBean.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 *
 */

public abstract class QuartzJobBean implements Job {
    @Override
    public final void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this);
            MutablePropertyValues pvs = new MutablePropertyValues();
            pvs.addPropertyValues(context.getScheduler().getContext());
            pvs.addPropertyValues(context.getMergedJobDataMap());
            bw.setPropertyValues(pvs, true);
        }
        catch (SchedulerException ex) {
            throw new JobExecutionException(ex);
        }
        excuteInternal(context);
    }

    protected abstract void excuteInternal(JobExecutionContext context) throws JobExecutionException;
}
