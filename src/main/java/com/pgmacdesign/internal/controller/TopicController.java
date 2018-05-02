package com.pgmacdesign.internal.controller;

import com.pgmacdesign.internal.datamodels.Topic;
import com.pgmacdesign.internal.services.TopicService;
import com.pgmacdesign.internal.services.TopicServiceMongo;
import com.pgmacdesign.utils.Constants;
import com.pgmacdesign.utils.L;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicController {

    @Autowired  //Spring annotation to mark it as requiring dependency injection
    private TopicService instance;
    @Autowired
    private TopicServiceMongo instanceM;

    @RequestMapping(method = RequestMethod.GET, value="/topics")
    public List<Topic> getAllTopics(){
        if(Constants.USE_MONGODB_OVER_SQL) {
            List<Topic> topics = instanceM.getAllTopics();
            return topics;
        } else {
            List<Topic> topics = instance.getAllTopics();
            return topics;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="/topics/query/{query}")
    public List<Topic> queryTopics(@PathVariable("query") String query){
        if(Constants.USE_MONGODB_OVER_SQL) {
            L.m("sending query @41");
            List<Topic> topics = instanceM.queryTopics(query);
            return topics;
        } else {
//            List<Topic> topics = instance.queryTopics(topicId);
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="topics/{id}") //Get request as per path being utilized
    public Topic getSingleTopic(@PathVariable("id") String topicId){
        try {
            if(Constants.USE_MONGODB_OVER_SQL) {
                return instanceM.getTopic(topicId);
            } else {
                return instance.getTopic(topicId);
            }
        } catch (Exception e){
            return null;
        }
    }

    //This is a sample without the path variable name being overridden
//    @RequestMapping("topics/{topicId}")
//    public Topic getSingleTopic(@PathVariable String topicId){
//        try {
//            return instance.getCourse(topicId);
//        } catch (Exception e){
//            return null;
//        }
//    }

    @RequestMapping(method = RequestMethod.POST, value= "/topics")
    public boolean addTopic(@RequestBody Topic topicToAdd){
        try {
            if(Constants.USE_MONGODB_OVER_SQL) {
                return instanceM.addTopic(topicToAdd);
            } else {
                return instance.addTopic(topicToAdd);
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

//    @RequestMapping(method = RequestMethod.POST, value= "/topics")
//    public void addTopic(String id, String name, String description){
//        if(!Utilities.isNullOrEmpty(id) && !Utilities.isNullOrEmpty(name) &&  !Utilities.isNullOrEmpty(description)){
//
//        } else {
//
//        }
//    }

    @RequestMapping(method = RequestMethod.PUT, value= "/topics/{id}")
    public boolean updateTopic(@PathVariable String id,
                            @RequestBody Topic topicToAdd){
        try {
            if(Constants.USE_MONGODB_OVER_SQL) {
                return instanceM.updateTopic(id, topicToAdd);
            } else {
                return instance.updateTopic(id, topicToAdd);
            }

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value= "/topics/{id}")
    public boolean deleteSingleTopic(@PathVariable("id") String topicId){
        try {
            if(Constants.USE_MONGODB_OVER_SQL) {
                return instanceM.deleteTopic(topicId);
            } else {
                return instance.deleteTopic(topicId);
            }
        } catch (org.springframework.dao.DataIntegrityViolationException dsql){
            L.m("Tried to delete topic that is tied to a course by a foreign key, failed");
            return false;
        } catch (Exception e){
            return false;
        }
    }

}
