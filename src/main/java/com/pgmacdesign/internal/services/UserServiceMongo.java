package com.pgmacdesign.internal.services;

import com.pgmacdesign.internal.logging.MyErrorLogger;
import com.pgmacdesign.internal.datamodels.SimpleSuccessModel;
import com.pgmacdesign.internal.datamodels.User;
import com.pgmacdesign.utils.L;
import com.pgmacdesign.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceMongo {

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private UserComplexRepository complexUserOperations;

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        try {
            users = mongoOperations.findAll(User.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
        return users;
    }

    public User getUser(String userId){
        L.m("query for user, userID == " + userId);
        if(Utilities.isNullOrEmpty(userId)){
            return null;
        }
        try {
            //return mongoOperations.findOne(UserServiceMongo.buildUserIdQuery(userId), User.class);
            return mongoOperations.findById(userId, User.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
            return null;
        }
    }

    public Object addUser(User userToAdd) {
        try {
            if(getUser(userToAdd.getId()) != null){
                L.m("user already exists in db!");
                return Utilities.buildErrorMessage("Add User Error", "User already exists", 400);
            } else {
                if(doesEmailAlreadyExist(userToAdd.getEmail())){
                    return Utilities.buildErrorMessage("Add User Error", "Email already exists", 400);
                } else if (doesUsernameAlreadyExist(userToAdd.getUsername())){
                    return Utilities.buildErrorMessage("Add User Error", "Username already exists", 400);
                } else {
                    mongoOperations.insert(userToAdd);
                    String uid = userToAdd.get_id();
                    if (!Utilities.isNullOrEmpty(uid)) {
                        userToAdd.setId(uid);
                        mongoOperations.save(userToAdd);
                    }
                }
            }
            return userToAdd;
        } catch (Exception e){
            e.printStackTrace();
            return Utilities.buildErrorMessage("Add User Error", e.getMessage(), 400);
        }
    }

    public boolean updateFields(Map<String, Object> keyValsToUpdate){
        Update update = new Update();
        for(Map.Entry<String, Object> map : keyValsToUpdate.entrySet()){
            if(map == null){
                return false;
            }
            String key = map.getKey();
            Object value = map.getValue();
            if(Utilities.isNullOrEmpty(key)){
                continue;
            }
            if(isBatchProtectedField(key)){
                String nope = "Attempting to batch alter protected field";
                MyErrorLogger.logError(nope);
                return false;
            }
            update = update.set(key, value);
        }
        try {
            mongoOperations.updateMulti(UserServiceMongo.buildAllUsersQuery(), update, User.class);
            return true;
        } catch (Exception e){
            MyErrorLogger.logError(e);
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateField(String key, Object value){
        Update update = new Update();
        //update.addToSet() //Used for sets (arrays)
        if(isBatchProtectedField(key)){
            String nope = "Attempting to batch alter protected field";
            MyErrorLogger.logError(nope);
            return false;
        }
        update = update.set(key, value);
        try {
            mongoOperations.updateMulti(UserServiceMongo.buildAllUsersQuery(), update, User.class);
            return true;
        } catch (Exception e){
            MyErrorLogger.logError(e);
            e.printStackTrace();
            return false;
        }
    }

    public Object updateUser(String id, User userToAdd) {
        try {
            if(!Utilities.isNullOrEmpty(userToAdd.getEmail())){
                List<User> searchUsers = complexUserOperations.findByEmail(userToAdd.getEmail());
                if(searchUsers != null){
                    if(searchUsers.size() > 0){
                        for(User u : searchUsers){
                            if(u != null){
                                String email = u.getEmail();
                                if(Utilities.doesEqualIgnoreCase(email, userToAdd.getEmail())){
                                    String foundUserId = u.get_id();
                                    if (!Utilities.doesEqual(id, foundUserId)) {
                                        return Utilities.buildErrorMessage("Update User Error",
                                                "Error updating user, email already exists", 400);
                                    }
                                }
                                String username = u.getUsername();
                                if(Utilities.doesEqualIgnoreCase(username, userToAdd.getUsername())){
                                    String foundUserId = u.get_id();
                                    if (!Utilities.doesEqual(id, foundUserId)) {
                                        return Utilities.buildErrorMessage("Update User Error",
                                                "Error updating user, username already exists", 400);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //BasicDBObject basic =
            Update update = new Update();
            //update.addToSet() //Used for sets (arrays)
            update = update.set("firstName", userToAdd.getFirstName());
            update = update.set("lastName", userToAdd.getLastName());
            update = update.set("username", userToAdd.getUsername());
            update = update.set("age", userToAdd.getAge());
            update = update.set("weight", userToAdd.getWeight());
            update = update.set("email", userToAdd.getEmail());
            update = update.set("note", userToAdd.getNote());
            update = update.set("temp1", userToAdd.getTemp1());
            mongoOperations.updateFirst(UserServiceMongo.buildUserIdQuery(id), update, User.class);
            return userToAdd;
        } catch (Exception e){
            MyErrorLogger.logError(e);
            return Utilities.buildErrorMessage("Update User Error", e.getMessage(), 400);
        }
    }


    public Object deleteUser(String userId) {
        try {
            User t = getUser(userId);
            if(t == null){
                return Utilities.buildErrorMessage("Deletion Error", "Could not find user to delete", 400);
            }
            mongoOperations.remove(t);
            //The one below will delete by the object as opposed to the id
            //userRepository.delete(passedUser);
            return new SimpleSuccessModel(true, "success");
        } catch (org.springframework.dao.EmptyResultDataAccessException empty){
            //Will trigger if there is no result found with the user id
            return Utilities.buildErrorMessage("Deletion Error", "Could not find user to delete", 400);
        } catch (org.springframework.dao.DataIntegrityViolationException dsql){
            return Utilities.buildErrorMessage("Deletion Error", "Error Deleting User within Database", 400);
        } catch (Exception e){
            e.printStackTrace();
            return Utilities.buildErrorMessage("Deletion Error", e.getMessage(), 400);
        }
    }

    public List<User> queryUserComplicated(String firstName, String lastName, int startAge, int endAge, String username){
        try {
            return complexUserOperations.findByRegexpFirstNameRegexpLastNameAgeBetweenUsernameRegexp(
                    firstName, lastName, startAge, endAge, username);
        } catch (Exception e){
            MyErrorLogger.logError(e);
            return null;
        }
    }

    public Object queryUsers(String nameQuery){
        try {
            L.m("Query hit (query == " + nameQuery + ") @ 159");
            return complexUserOperations.findUsersByRegexpFirstName(nameQuery);
        } catch (Exception e){
            MyErrorLogger.logError(e);
            return null;
        }
    }

    /**
     * Fix any broken fields. todo Add in more here if needed
     * Sample of fixing users with broken id fields below
     */
    public void fixAnyBrokenFields() {
        //This query grabs all users where their 'id' is null but their '_id' is not null
        Query q = new Query(Criteria.where("_id").exists(true)
                .andOperator(Criteria.where("id").exists(false)));
        List<User> users = mongoOperations.find(q, User.class);
        for(User u : users){
            if(u == null){
                continue;
            }
            u.setId(u.get_id());
            mongoOperations.save(u);
        }

        //Run more here if needed.

    }

    public boolean doesUsernameAlreadyExist(String username){
        if(Utilities.isNullOrEmpty(username)){
            return true; //So as to prevent null inserts
        }
        List<User> users = complexUserOperations.findByUsername(username);
        if(users == null){
            return true;
        }
        if(users.size() > 0){
            return true;
        }
        return false;
    }

    public boolean doesEmailAlreadyExist(String email){
        if(Utilities.isNullOrEmpty(email)){
            return true; //So as to prevent null inserts
        }
        List<User> users = complexUserOperations.findByEmail(email);
        if(users == null){
            return true;
        }
        if(users.size() > 0){
            return true;
        }
        return false;
    }

    private static Query buildUserIdQuery(@NonNull String userId){
        return new Query(Criteria.where("_id").is(userId));
    }

    /**
     * Query to check where the _id field exists (should be all users) and where it is not an empty String.
     * See reference: https://docs.mongodb.com/v3.2/tutorial/query-for-null-fields/
     */
    private static Query buildAllUsersQuery(){
        return new Query(Criteria.where("_id").exists(true)
                .andOperator(Criteria.where("_id").ne("")));
    }

    /**
     * Checks if the field is a restricted one for batch updates. IE, this will prevent
     * the 'salt' or 'hashedPassword' fields from being bulk updated
     * @param key Key to check. Should match Instance variables in {@link User}
     * @return true if it is protected, false if it is not
     */
    public static boolean isBatchProtectedField(String key){
        if(Utilities.isNullOrEmpty(key)){
            return false;
        }
        if(Utilities.doesEqual(key, "_id")){
            return true;
        } else if(Utilities.doesEqual(key, "id")){
            return true;
        } else if(Utilities.doesEqual(key, "username")){
            return true;
        } else if(Utilities.doesEqual(key, "email")){
            return true;
        } else if(Utilities.doesEqual(key, "password")){
            return true;
        } else if(Utilities.doesEqual(key, "hashedPassword")){
            return true;
        } else if(Utilities.doesEqual(key, "salt")){
            return true;
        }
        return false;
    }

}
