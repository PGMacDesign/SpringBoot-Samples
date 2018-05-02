package com.pgmacdesign.internal.logging;

import com.pgmacdesign.utils.Constants;
import com.pgmacdesign.utils.L;
import com.pgmacdesign.utils.Utilities;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class MyErrorLogger {

    public static <T extends Exception> void logError(T t){
        if(t == null){
            return;
        }
        String message = t.getMessage();
        if(Utilities.isNullOrEmpty(message)){
            return;
        }
        MyLogger.logException(t);
        if(Constants.SHOW_ERRORS_IN_LOGCAT) {
            t.printStackTrace();
        }
    }

    public static void logError(String message){
        if(Utilities.isNullOrEmpty(message)){
            return;
        }
        MyLogger.logException(message);
        if(Constants.SHOW_ERRORS_IN_LOGCAT) {
            L.m("error : " + message);
        }
    }

    public static <T extends Exception> void logError(T t, String str){
        if(t == null){
            return;
        }
        String message = t.getMessage();
        if(Utilities.isNullOrEmpty(message)){
            return;
        }
        MyLogger.logException(t, str);
        if(Constants.SHOW_ERRORS_IN_LOGCAT) {
            t.printStackTrace();
            L.m("error : " + str);
        }
    }

}
