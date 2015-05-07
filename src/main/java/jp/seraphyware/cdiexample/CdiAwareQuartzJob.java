package jp.seraphyware.cdiexample;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.deltaspike.scheduler.api.Scheduled;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * スケジュール
 *
 * @author seraphy
 */
@ApplicationScoped
@Scheduled(cronExpression = "* * * * * ?", onStartup = true)
public class CdiAwareQuartzJob implements org.quartz.Job {

    @Inject
    private EchoServer echoSrv;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        echoSrv.say("*" + context.getFireTime());
    }
}
