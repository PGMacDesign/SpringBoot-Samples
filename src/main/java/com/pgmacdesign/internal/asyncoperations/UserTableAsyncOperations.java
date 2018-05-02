package com.pgmacdesign.internal.asyncoperations;

import com.google.gson.Gson;
import com.pgmacdesign.internal.AppConfig;
import com.pgmacdesign.internal.logging.MyErrorLogger;
import com.pgmacdesign.internal.controller.UserController;
import com.pgmacdesign.internal.datamodels.User;
import com.pgmacdesign.internal.BeanUtils;
import com.pgmacdesign.internal.services.UserServiceMongo;
import com.pgmacdesign.utils.L;
import com.pgmacdesign.utils.Utilities;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Async Rules: (From http://www.baeldung.com/spring-async)
 *    1) it must be applied to public methods only
 *    2) self-invocation – calling the async method from within the same class – won’t work
 * http://www.baeldung.com/spring-async
 */
@Service
public class UserTableAsyncOperations {

    private static UserTableAsyncOperations instance;
    public static UserTableAsyncOperations getInstance(){
        if(instance == null){
            try {
                instance = AppConfig.getContext().getBean(UserTableAsyncOperations.class);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Async
    public void fixAnyBrokenUsers(){
        try {
            Thread.sleep(250);
            UserServiceMongo userServiceMongo = BeanUtils.getBean(UserServiceMongo.class);
            userServiceMongo.fixAnyBrokenFields();
        } catch (InterruptedException ie){
            MyErrorLogger.logError(ie);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
    }

    @Async
    public void updateAllUsersWithField(String keyToUpdate, Object valueToUpdate){
        try {
            Thread.sleep(250);
            UserServiceMongo userServiceMongo = BeanUtils.getBean(UserServiceMongo.class);
            userServiceMongo.updateField(keyToUpdate, valueToUpdate);
        } catch (InterruptedException ie){
            MyErrorLogger.logError(ie);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
    }

    @Async
    public void updateAllUsersWithField(Map<String, Object> valsToUpdate){
        try {
            Thread.sleep(250);
            UserServiceMongo userServiceMongo = BeanUtils.getBean(UserServiceMongo.class);
            userServiceMongo.updateFields(valsToUpdate);
        } catch (InterruptedException ie){
            MyErrorLogger.logError(ie);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
    }

    @Async
    public void kickOffTestAsyncCall(final String userIdToIgnore){
        try {
            //todo do whatever operations need to be done here
            L.m("Starting sleep, then updating user list");
            Thread.sleep(5000);
            //Do stuff
            L.m("Finished sleep, updating user list");
            String str = (String) doStuff(userIdToIgnore);
            L.m("Finished async operations, response == \n" + str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Object doStuff(final String userIdToIgnore){
        L.m("Do stuff triggered, " + 136);
        UserServiceMongo userController = BeanUtils.getBean(UserServiceMongo.class);
        List<User> userList = userController.getAllUsers();
        if(Utilities.isListNullOrEmpty(userList)){
            L.m("Do stuff triggered, " + 140);
            return null;
        }
        L.m("Do stuff triggered, " + 143);
        int pos = 0;
        for(User u : userList){
            if(u == null){
                continue;
            }
            if(!Utilities.doesEqual(u.get_id(), userIdToIgnore))
                try {
                    Thread.sleep(250);
                    u.setNote(null);
                    u.setTemp1(null);
                    Object o = userController.updateUser(u.get_id(), u);
                    try {
                        User user = (User) o;
                        L.m("updated user == " + new Gson().toJson(user, User.class));
                    } catch (Exception e){e.printStackTrace();}
                } catch (InterruptedException ie){ie.printStackTrace();}
        }
        L.m("Do stuff triggered, " + 158);
        return "Number of records altered == " + pos;
    }

}
