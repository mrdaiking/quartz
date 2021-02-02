package qtest;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloWorldJob implements Job {
    @Override
    public void execute(JobExecutionContext jobContext) throws JobExecutionException {
        System.out.println("Hello World!!");
        System.out.println("HelloWorldJob start: " + jobContext.getFireTime());
        JobDetail jobDetail = jobContext.getJobDetail();
        System.out.println("Example name is: " + jobDetail.getJobDataMap().getString("example"));
        System.out.println("HelloWorldJob end: " + jobContext.getJobRunTime() + ", key: " + jobDetail.getKey());
        System.out.println("HelloWorldJob next scheduled time: " + jobContext.getNextFireTime());
    }
}
