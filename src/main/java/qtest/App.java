package qtest;

import java.util.List;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
 
		try {
            StdSchedulerFactory factory = new StdSchedulerFactory();
            Scheduler scheduler =factory.getScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("example", "Hello World job running");
            //Add information for job
            JobDetail jobDetail = JobBuilder.newJob(HelloWorldJob.class).usingJobData(data).withIdentity("HelloWorldJob", "group1").build();
            //Create Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("myTrigger", "group1")
            .startAt(DateBuilder.todayAt(19, 20, 10))
            .withSchedule(SimpleScheduleBuilder.simpleSchedule ()
            .withRepeatCount(5)
            .withIntervalInSeconds(2))
            .build();
            scheduler.scheduleJob(jobDetail, trigger);
            boolean isRunning = isJobPaused(scheduler, "HelloWorldJob");
            System.out.println("---JOB---" + String.valueOf(isRunning));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private static Boolean isJobPaused(Scheduler scheduler, String jobName) throws SchedulerException {

        JobKey jobKey = new JobKey(jobName);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
        for (Trigger trigger : triggers) {
            TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            if (TriggerState.PAUSED.equals(triggerState)) {
                return true;
            }
        }
        return false;
    }
}
