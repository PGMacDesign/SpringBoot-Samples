package com.pgmacdesign.internal.services;

import com.pgmacdesign.internal.logging.MyErrorLogger;
import com.pgmacdesign.internal.datamodels.Topic;
import com.pgmacdesign.utils.L;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicServiceMongo {

    @Autowired
    private MongoOperations mongoOperations;

    public List<Topic> getAllTopics(){
        List<Topic> topics = new ArrayList<>();
        try {
            topics = mongoOperations.findAll(Topic.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
        return topics;
    }

    public List<Topic> queryTopics(@NonNull String searchQuery){
        List<Topic> topics = new ArrayList<>();
        L.m(("querying - " + searchQuery));
        try {
            Query query = new Query();
            //query.limit(10); //Use this if a limit should be set, IE, pagination
            Criteria criteria = Criteria.where("description").regex(searchQuery);
            //Cannot add >2 --> InvalidMongoDbApiUsageException: Due to limitations of the com.mongodb.BasicDocument, you can't add a second '$or' expression specified as '$or
            query.addCriteria(criteria);
            topics = mongoOperations.find(query, Topic.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
        return topics;
    }

    public List<Topic> sampleQuery1(@NonNull String searchQuery){
        List<Topic> topics = new ArrayList<>();
        try {
            BasicQuery query = new BasicQuery("{\"description\": {$regex : '" + searchQuery + "'} }");
            topics = mongoOperations.find(query, Topic.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
        return topics;
    }

    public Topic getTopic(String topicId){
        try {
            return mongoOperations.findOne(TopicServiceMongo.buildTopicIdQuery(topicId), Topic.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
            return null;
        }
    }

    public boolean addTopic(Topic topicToAdd) {
        try {
            if(getTopic(topicToAdd.getMyId()) != null){
                L.m("topic already exists in db!");
                return false;
            } else {
                mongoOperations.save(topicToAdd);
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean updateTopic(String id, Topic topicToAdd) {
        try {
            Update update = new Update();
            //update.addToSet() //Used for sets (arrays)
            update = update.set("name", topicToAdd.getName());
            update = update.set("description", topicToAdd.getDescription());
            mongoOperations.updateFirst(TopicServiceMongo.buildTopicIdQuery(id), update, Topic.class);
            return true;
        } catch (Exception e){
            MyErrorLogger.logError(e);
            return false;
        }
    }


    public boolean deleteTopic(String topicId) {
        try {
            Topic t = getTopic(topicId);
            if(t == null){
                return false;
            }
            mongoOperations.remove(t);
            //The one below will delete by the object as opposed to the id
            //topicRepository.delete(passedTopic);
            return true;
        } catch (org.springframework.dao.EmptyResultDataAccessException empty){
            //Will trigger if there is no result found with the topic id
            L.m("No topic found with that ID");
        } catch (org.springframework.dao.DataIntegrityViolationException dsql){
            L.m("Tried to delete topic that is tied to a topic by a foreign key, failed");
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    private static Query buildTopicIdQuery(@NonNull String topicId){
        return new Query(Criteria.where("myId").is(topicId));
    }

}
