package com.pgmacdesign.internal.cronjobs;

import com.pgmacdesign.internal.logging.MyLogger;
import com.pgmacdesign.internal.asyncoperations.UserTableAsyncOperations;
import com.pgmacdesign.utils.L;
import com.pgmacdesign.utils.Utilities;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 1) https://stackoverflow.com/questions/46969575/how-to-trigger-a-scheduled-spring-batch-job
 */
@Component
public class UserScheduledJobs {

    public static enum UserCronJobs {
        FixBrokenUsers
    }

    public static List<UserCronJobs> getDefaultCronJobs(){
        List<UserCronJobs> allCronJobs = new ArrayList<>();
        //1st
        UserCronJobs fixUsers1 = UserCronJobs.FixBrokenUsers;
        allCronJobs.add(fixUsers1);

        //2nd
        UserCronJobs fixUsers2 = UserCronJobs.FixBrokenUsers;
        allCronJobs.add(fixUsers2);

        return allCronJobs;
    }


    /**
     * Scheduled job. Time gap (for fixed or fixedDelay) is in milliseconds.
     * Good info at these 2 links:
     *      https://dzone.com/articles/running-on-time-with-springs-scheduled-tasks
     *      http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html
     * @Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
     * Samples:
     *      @Scheduled(cron = "0 0 12 * * ?") --> 12pm every day
     *      @Scheduled(cron = "0 15 10 * * ? 2018") --> 3pm every day only in the year 2018
     *      @Scheduled(cron = "0/20 * * * * ?") --> Fires every 20 seconds
     * Definitions:
     *      * represents all values so * means ALL in that particular column
     *      ? represents no specific value and can only be used in the day of month, day of week fields.
     *      - represents an inclusive range of values
     *      , represents additional values (IE,MON,WED,SUN)
     *      / represents increments (IE, 0/15 in seconds field means every 15 seconds)
     *      L represents the last day of the week or month (Saturday). 6L means last friday of the month,
     *          L-3 means the third from the last day of the month. Must specify ? in other day /week / month / year fields if used
     *      W represents teh nearest weekday of the month. IE, 15W will trigger on the 15th day of the month if it is a weekday
     *      # represents both the day of the week and the week it should trigger. IE 5#2 means the second thursday of the month
     */
    @Scheduled(cron = "0 0 0 * * ?", zone = "UTC")
    public void executeAllCronJobs(){
        L.m("beginning execution of all cron jobs.");
        List<UserCronJobs> lst = UserScheduledJobs.getDefaultCronJobs();
        if(Utilities.isListNullOrEmpty(lst)){
            return;
        }
        for(UserCronJobs cron : lst){
            if(cron == null){
                continue;
            }
            switch (cron){
                case FixBrokenUsers:
                    UserTableAsyncOperations.getInstance().fixAnyBrokenUsers();
                    MyLogger.logCronJob(UserCronJobs.FixBrokenUsers.toString());
                    break;
            }
        }
    }


}
