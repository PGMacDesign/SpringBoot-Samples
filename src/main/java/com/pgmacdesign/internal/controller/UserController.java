package com.pgmacdesign.internal.controller;

import com.pgmacdesign.internal.datamodels.CustomException;
import com.pgmacdesign.internal.logging.MyErrorLogger;
import com.pgmacdesign.internal.datamodels.EmptyObject;
import com.pgmacdesign.internal.datamodels.User;
import com.pgmacdesign.internal.services.UserServiceMongo;
import com.pgmacdesign.internal.asyncoperations.UserTableAsyncOperations;
import com.pgmacdesign.utils.Constants;
import com.pgmacdesign.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    @Autowired  //Spring annotation to mark it as requiring dependency injection
    private UserServiceMongo instanceM;

    /*
    {
        "timestamp": "2018-04-27T15:16:23.571+0000",
        "status": 500,
        "error": "Internal Server Error",
        "message": "Missing URI template variable 'id' for method parameter of type String",
        "path": "/users"
    }
     */
    @RequestMapping(method = RequestMethod.GET, value="/users")
    public List<User> getAllUsers(@RequestHeader("clientId") String clientId,
                                  @RequestHeader("auth") String auth){
        if(Constants.USE_MONGODB_OVER_SQL){
            try {
                List<User> users = instanceM.getAllUsers();
                return users;
            } catch (Exception e){
                e.printStackTrace();
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * This is an example of a security risk in that it has no validation / authentication
     */
    public List<User> getAllUsers(){
        if(Constants.USE_MONGODB_OVER_SQL){
            try {
                List<User> users = instanceM.getAllUsers();
                return users;
            } catch (Exception e){
                e.printStackTrace();
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }


    @RequestMapping(method = RequestMethod.GET, value="/users/{userId}") //Get request as per path being utilized
    public Object getSingleUser(@RequestHeader("clientId") String clientId,
                                @RequestHeader("auth") String auth,
                                @PathVariable("userId") String userId) throws CustomException {
        User user = instanceM.getUser(userId);
        if(user == null){
            throw Utilities.buildCustomException(
                    "User with a userId of '" + userId + "' could not be found",
                    "BAD_REQUEST", 400);
        } else {
            return user;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="/users/query/{query}")
    public Object queryUsers(@RequestHeader("clientId") String clientId,
                             @RequestHeader("auth") String auth,
                             @PathVariable("query") String query){
        try {
            return instanceM.queryUsers(query);
        } catch (Exception e){
            MyErrorLogger.logError(e);
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value="/users/query")
    public List<User> queryUsers(@RequestHeader("clientId") String clientId,
                                 @RequestHeader("auth") String auth,
                                 @RequestBody User userToQuery){
        if(Constants.USE_MONGODB_OVER_SQL) {
            List<User> users = instanceM.queryUserComplicated(
                    userToQuery.getFirstName(), userToQuery.getLastName(), userToQuery.getAge() - 40,
                    userToQuery.getAge() + 40, userToQuery.getUsername()
            );
            return users;
        } else {
//            List<User> topics = instance.queryUsers(query);
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value= "/users")
    public Object addUser(@RequestHeader("clientId") String clientId,
                          @RequestBody User userToAdd){
        try {
            if(Constants.USE_MONGODB_OVER_SQL){
                return instanceM.addUser(userToAdd);
            } else {
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
            return Utilities.buildErrorMessage("Error adding user", e.getMessage(), 400);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value= "/users/test/{userId}")
    public Object updateUserTest(@PathVariable String userId,
                                 @RequestBody User userToAdd){
        try {
            if(Constants.USE_MONGODB_OVER_SQL) {
                //This will kick off an operation to do a bunch of batch edits
//                UserTableAsyncOperations.getInstance().kickOffTestAsyncCall(userId);
                //This will kick off an operation to update all users to have their note string read "test" as the value
//                UserTableAsyncOperations.getInstance().updateAllUsersWithField("note", "test3");
                //This will kick off an operation to update all users to attempt to alter the _id field but will fail due to a check on reserved fields
//                Map<String, Object> toUpdate = new HashMap<>();
//                toUpdate.put("_id", "testNote2");
//                toUpdate.put("email", "shouldFail");
//                UserTableAsyncOperations.getInstance().updateAllUsersWithField(toUpdate);
                //This will kick off an operation to update / fix all users with broken 'id' fields
//                UserTableAsyncOperations.getInstance().fixAnyBrokenUsers();
                //return instanceM.updateUser(userId, userToAdd);
                return true;
            } else {
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
            UserTableAsyncOperations.getInstance().kickOffTestAsyncCall(userId);
            return Utilities.buildErrorMessage("Error updating user", e.getMessage(), 400);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value= "/users/{userId}")
    public Object updateUser(@RequestHeader("clientId") String clientId,
                             @RequestHeader("auth") String auth,
                             @PathVariable String userId,
                             @RequestBody User userToAdd){
        try {
            if(Constants.USE_MONGODB_OVER_SQL) {
                return instanceM.updateUser(userId, userToAdd);
            } else {
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
            UserTableAsyncOperations.getInstance().kickOffTestAsyncCall(userId);
            return Utilities.buildErrorMessage("Error updating user", e.getMessage(), 400);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value= "/users/{userId}")
    public Object deleteSingleUser(@RequestHeader("clientId") String clientId,
                                   @RequestHeader("auth") String auth,
                                   @PathVariable("userId") String userId){
        try {
            if(Constants.USE_MONGODB_OVER_SQL) {
                return instanceM.deleteUser(userId);
            } else {
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
            return Utilities.buildErrorMessage("Error deleting user", e.getMessage(), 400);
        }
    }

}
