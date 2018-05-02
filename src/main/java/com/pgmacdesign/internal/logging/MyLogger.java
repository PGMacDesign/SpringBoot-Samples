package com.pgmacdesign.internal.logging;

import com.pgmacdesign.internal.BeanUtils;
import com.pgmacdesign.internal.datamodels.DBLogging;
import com.pgmacdesign.utils.Utilities;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MyLogger {

//    private static MyLogger instance;
//    public static MyLogger getInstance(){
//        if(instance == null){
//            try {
//                instance = AppConfig.getContext().getBean(MyLogger.class);
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        return instance;
//    }

    public static void logEvent(String eventToLog){
        if(Utilities.isNullOrEmpty(eventToLog)){
            return;
        }
        DBLogging logging = new DBLogging();
        logging.setEvent(eventToLog);
        logging.setDateTime(ZonedDateTime.now( ZoneOffset.UTC ).withNano(0).format(DateTimeFormatter.ISO_INSTANT));
        MyLoggingServiceMongo loggingServiceMongo = BeanUtils.getBean(MyLoggingServiceMongo.class);
        loggingServiceMongo.logObject(logging);
    }

    static <T extends Exception> void logException(T exception, String error){
        if(exception == null){
            return;
        }
        DBLogging logging = new DBLogging();
        logging.setEvent(DBLogging.EVENT_EXCEPTION);
        logging.setDescription("Error: " + error + ".\n " + exception.getMessage());
        logging.setStackTrace(exception.toString());
        logging.setDateTime(ZonedDateTime.now( ZoneOffset.UTC ).withNano(0).format(DateTimeFormatter.ISO_INSTANT));
        MyLoggingServiceMongo loggingServiceMongo = BeanUtils.getBean(MyLoggingServiceMongo.class);
        loggingServiceMongo.logObject(logging);
    }

    static <T extends Exception> void logException(T exception){
        if(exception == null){
            return;
        }
        DBLogging logging = new DBLogging();
        logging.setEvent(DBLogging.EVENT_EXCEPTION);
        logging.setDescription(exception.getMessage());
        logging.setStackTrace(exception.toString());
        logging.setDateTime(ZonedDateTime.now( ZoneOffset.UTC ).withNano(0).format(DateTimeFormatter.ISO_INSTANT));
        MyLoggingServiceMongo loggingServiceMongo = BeanUtils.getBean(MyLoggingServiceMongo.class);
        loggingServiceMongo.logObject(logging);
    }

    static void logException(String exception){
        if(Utilities.isNullOrEmpty(exception)){
            return;
        }
        DBLogging logging = new DBLogging();
        logging.setEvent(DBLogging.EVENT_EXCEPTION);
        logging.setDescription(exception);
        logging.setDateTime(ZonedDateTime.now( ZoneOffset.UTC ).withNano(0).format(DateTimeFormatter.ISO_INSTANT));
        MyLoggingServiceMongo loggingServiceMongo = BeanUtils.getBean(MyLoggingServiceMongo.class);
        loggingServiceMongo.logObject(logging);
    }

    public static void logCronJob(String cronJobInfo){
        if(Utilities.isNullOrEmpty(cronJobInfo)){
            return;
        }
        DBLogging logging = new DBLogging();
        logging.setEvent(DBLogging.EVENT_CRON_JOB_RAN);
        logging.setDescription(cronJobInfo);
        logging.setDateTime(ZonedDateTime.now( ZoneOffset.UTC ).withNano(0).format(DateTimeFormatter.ISO_INSTANT));
        MyLoggingServiceMongo loggingServiceMongo = BeanUtils.getBean(MyLoggingServiceMongo.class);
        loggingServiceMongo.logObject(logging);
    }
}
