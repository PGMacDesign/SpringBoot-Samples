package com.pgmacdesign.internal.services;

import com.pgmacdesign.internal.logging.MyErrorLogger;
import com.pgmacdesign.internal.datamodels.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplexQueriesSamples {

    @Autowired
    private UserComplexRepository userOperations;
    @Autowired
    private MongoOperations mongoOperations;

    /**
     * Find people whose names start with A
     * @return
     */
    public List<User> findPeopleWithThisName(String name){
        List<User> users = new ArrayList<>();
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("name").regex(name));
            users = mongoOperations.find(query, User.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
        return users;
    }

    /**
     * Find people whose names start with A
     * @return
     */
    public List<User> findPeopleWhoseNamesStartWithA(){
        List<User> users = new ArrayList<>();
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("name").regex("^A"));
            users = mongoOperations.find(query, User.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
        return users;
    }

    /**
     * Find people whose names end with T
     * @return
     */
    public List<User> findPeopleWhoseNamesEndWithT(){
        List<User> users = new ArrayList<>();
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("name").regex("t$"));
            users = mongoOperations.find(query, User.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
        return users;
    }

    /**
     * Find users whose age is: <51 and >17
     * @return
     */
    public List<User> findPeopleOlderThan18AndLessThan50(){
        List<User> users = new ArrayList<>();
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("age").lt(51).gt(17));
            users = mongoOperations.find(query, User.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
        return users;
    }

    /**
     * Find users and sort by their age, ascending
     * @return
     */
    public List<User> findAllPeopleAndSortByAge(){
        List<User> users = new ArrayList<>();
        try {
            Query query = new Query();
            query.with(new Sort(Sort.Direction.ASC, "age"));
            users = mongoOperations.find(query, User.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
        return users;
    }

    /**
     * Find users and sort by their age, ascending
     * @return
     */
    public List<User> findAllPeoplePaginate(int startingPage, int endingPage){
        if(startingPage < 0){
            startingPage = 0;
        }
        List<User> users = new ArrayList<>();
        try {
            Query query = new Query();
            Pageable pageableResult = PageRequest.of(startingPage, endingPage); //Can add overloaded Sort as well
            query.with(pageableResult);
            users = mongoOperations.find(query, User.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
        return users;
    }



}
