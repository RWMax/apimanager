package com.liuht.ec.quartz.manager;

import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author 刘惠涛
 * @version 1.0
 * @package com.liuht.ec.quartz.manager
 * <p>
 * Title: QuartzManager.java
 * </p>
 * <p>
 * Description: 定时任务管理类
 * </p>
 * @date 2016年9月1日 上午9:36:37
 */
public class QuartzManager {
    private static final String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
    private static final String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzManager.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    /**
     * @return void
     * @throws Exception
     * @package com.liuht.ec.quartz.manager
     * @method addJob 方法
     * @describe <p>
     * 方法说明:添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * </p>
     * @author 刘惠涛
     * @date 2016年9月1日 上午9:37:57
     */
    public void addJob(String jobName, Class<?> cls, String time) throws Exception {
        addJob(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME, cls, time, null);
    }

    /**
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务
     * @param time             时间设置，参考quartz说明文档
     * @return void
     * @throws Exception
     * @package com.liuht.ec.quartz.manager
     * @method addJob 方法
     * @describe <p>
     * 方法说明:添加一个定时任务
     * </p>
     * * @param jobName 任务名
     * @author 刘惠涛
     * @date 2016年9月1日 上午9:42:12
     */
    public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                       Class<?> jobClass, String time) throws Exception {
        addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, time, null);
    }

    /**
     * 添加job（无需自定义trigger名和trigger group名,但能自定义jobGroupName和其他数据Map）
     *
     * @param jobName
     * @param jobGroupName
     * @param jobClass
     * @param cronExpression
     * @param description        数据Map
     * @throws Exception
     */
    public void addJob(String jobName, String jobGroupName, Class<?> jobClass, String cronExpression, String description)
            throws Exception {
        addJob(jobName, jobGroupName, jobName, TRIGGER_GROUP_NAME, jobClass, cronExpression, description);
    }

    /**
     * 添加JOB
     *
     * @param jobName          JOB名称
     * @param jobGroupName     JOB组名称
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @param jobClass         JOB类
     * @param cronExpression   时间规则表达式
     * @param description          数据Map
     * @throws Exception
     */
    public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                       Class<?> jobClass, String cronExpression, String description)
            throws Exception {
        try {
            @SuppressWarnings("unchecked")
			JobBuilder jobBuilder = JobBuilder.newJob((Class<? extends Job>) jobClass);
            if (!StringUtils.isEmpty(description)) {
                jobBuilder.withDescription(description);
            }
            jobBuilder.withIdentity(jobName, StringUtils.isEmpty(jobGroupName) ? JOB_GROUP_NAME : jobGroupName);
            JobDetail jobDetail = jobBuilder.build();
            Trigger trigger = buildTrigger(triggerName, triggerGroupName, cronExpression);
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            LOGGER.error("添加定时任务:" + jobName + ",失败:{}", e);
            throw e;
        }
    }

    /**
     * 构建自定义triggerName,自定义group名的trigger对象
     *
     * @param triggerName
     * @param triggerGroupName
     * @param cronExpression
     * @return
     */
    private Trigger buildTrigger(String triggerName, String triggerGroupName, String cronExpression) {
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
        triggerBuilder.withIdentity(triggerName, triggerGroupName);
        triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression));
        return triggerBuilder.build();
    }

    /**
     * @return void
     * @throws Exception
     * @package com.liuht.ec.quartz.manager
     * @method modifyJobTime 方法
     * @describe <p>
     * 方法说明:修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     * </p>
     * @author 刘惠涛
     * @date 2016年9月1日 上午9:44:51
     */
    public void modifyJobTime(String jobName, String time) throws Exception {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME));
            if (trigger != null) {
                CronTriggerImpl ct = (CronTriggerImpl) trigger;
                ct.setCronExpression(time);
//                scheduler.resumeTrigger(TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME));
                scheduler.rescheduleJob(TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME), ct);
            }
        } catch (Exception e) {
            LOGGER.error("修改定时任务:" + jobName + ",失败:{}", e);
            throw e;
        }
    }

    /**
     * @return void
     * @throws Exception
     * @package com.liuht.ec.quartz.manager
     * @method removeJob 方法
     * @describe 移除一个任务(使用默认的任务组名，触发器名，触发器组名)<p>
     * @author 刘惠涛
     * @date 2016年9月1日 上午9:46:25
     */
    public void removeJob(String jobName) throws Exception {
        removeJob(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME);
    }

    /**
     * 移除一个job
     *
     * @param jobName
     * @param jobGroupName
     * @throws Exception
     */
    public void removeJob(String jobName, String jobGroupName) throws Exception {
        removeJob(jobName, jobGroupName, jobName, TRIGGER_GROUP_NAME);
    }

    /**
     * @return void
     * @throws Exception
     * @package com.liuht.ec.quartz.manager
     * @method removeJob 方法
     * @describe <p>
     * 方法说明:移除一个任务
     * </p>
     * @author 刘惠涛
     * @date 2016年9月1日 上午9:46:46
     */
    public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) throws Exception {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            sched.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));// 停止触发器
            sched.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName));// 移除触发器
            sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
        } catch (Exception e) {
            LOGGER.error("移除定时任务:" + jobName + ",失败:{}", e);
            throw e;
        }
    }

    /**
     * @return void
     * @throws Exception
     * @package com.liuht.ec.quartz.manager
     * @method modifyJobTime 方法
     * @describe <p>
     * 方法说明:修改一个任务的触发时间
     * </p>
     * @author 刘惠涛
     * @date 2016年9月1日 上午9:45:55
     */
    public void modifyJobTime(String triggerName, String triggerGroupName, String time) throws Exception {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            Trigger trigger = sched.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
            if (trigger != null && !((CronTriggerImpl) trigger).getCronExpression().equalsIgnoreCase(time)) {
                CronTriggerImpl ct = (CronTriggerImpl) trigger;
                // 修改时间
                ct.setCronExpression(time);
                // 重启触发器
                sched.rescheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName), ct);
            }
        } catch (Exception e) {
            LOGGER.error("修改一个任务的触发时间失败:{}", e);
            throw e;
        }
    }

    /**
     * 判断是否已添加过该job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名称
     * @return true/false
     * @throws SchedulerException
     * @throws ParseException
     */
    public boolean isJobAdded(String jobName, String jobGroupName)
            throws SchedulerException, ParseException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
        if (jobDetail != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return void
     * @throws Exception
     * @package com.liuht.ec.quartz.manager
     * @method startJobs 方法
     * @describe <p>
     * 方法说明:启动所有定时任务
     * </p>
     * @author 刘惠涛
     * @date 2016年9月1日 上午9:47:18
     */
    public void startJobs() throws Exception {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            sched.start();
        } catch (Exception e) {
            LOGGER.error("启动所有定时任务失败:{}", e);
            throw e;
        }
    }

    /**
     * @return void
     * @throws Exception
     * @package com.liuht.ec.quartz.manager
     * @method shutdownJobs 方法
     * @describe <p>
     * 方法说明:关闭所有定时任务
     * </p>
     * @author 刘惠涛
     * @date 2016年9月1日 上午9:47:36
     */
    public void shutdownJobs() throws Exception {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            LOGGER.error("关闭所有定时任务失败:{}", e);
            throw e;
        }
    }

    /**
     * 停止某个job任务
     *
     * @param jobkey
     * @throws SchedulerException
     */
    public void pauseJob(JobKey jobkey) throws SchedulerException {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            sched.pauseJob(jobkey);
        } catch (Exception e) {
            LOGGER.error("停止任务失败:{}", e);
            throw e;
        }
    }

    /**
     * 恢复某个job任务
     *
     * @param jobkey
     * @throws SchedulerException
     */
    public void resumeJob(JobKey jobkey) throws SchedulerException {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            sched.resumeJob(jobkey);
        } catch (Exception e) {
            LOGGER.error("恢复任务失败:{}", e);
            throw e;
        }
    }

    /**
     * 停止触发器调度Job任务
     *
     * @param triggerkey
     * @return
     * @throws SchedulerException
     */
    public boolean unscheduleJob(TriggerKey triggerkey)
            throws SchedulerException {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            return sched.unscheduleJob(triggerkey);
        } catch (Exception e) {
            LOGGER.error("停止触发器调度失败:{}", e);
            throw e;
        }
    }

    /**
     * 恢复触发器的调度Job任务
     *
     * @param triggerkey
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public Date rescheduleJob(TriggerKey triggerkey, Trigger trigger)
            throws SchedulerException {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            return sched.rescheduleJob(triggerkey, trigger);
        } catch (Exception e) {
            LOGGER.error("恢复触发器的调度失败:{}", e);
            throw e;
        }
    }

    /**
     * 获取触发器
     *
     * @param triggerkey
     * @return
     * @throws SchedulerException
     */
    public Trigger getTrigger(TriggerKey triggerkey)
            throws SchedulerException {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            return sched.getTrigger(triggerkey);
        } catch (Exception e) {
            LOGGER.error("获取触发器失败:{}", e);
            throw e;
        }
    }

    public String getJOB_GROUP_NAME() {
        return JOB_GROUP_NAME;
    }

    public String getTRIGGER_GROUP_NAME() {
        return TRIGGER_GROUP_NAME;
    }

}
