package com.pgmacdesign.internal.services;

import com.pgmacdesign.internal.datamodels.Topic;
import com.pgmacdesign.utils.L;
import com.pgmacdesign.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Business service (Singleton that starts upon app load)
 */
@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    private List<Topic> topics = new ArrayList<>(Arrays.asList(
            new Topic("abc123", "Spring", "Springapp"),
            new Topic("abc234", "Spring2", "Tutorials"),
            new Topic("abc345", "Spring3", "Videos")
    ));

    public List<Topic> getAllTopics(){
        List<Topic> topics = new ArrayList<>();
        topicRepository.findAll().forEach(topics::add);
        return topics;
    }

    @Deprecated //Using diff one now
    public List<Topic> getAllTopics0(){
        return this.topics;
    }

    public Topic getTopic(String topicId){
        try {
            return topicRepository.findById(topicId).get();
        } catch (Exception e){
            return null;
        }
    }

    @Deprecated //Using diff one now
    public Topic getTopic0(String topicId){
        try {
            return topics.stream().filter(t -> t.getMyId().equals(topicId)).findFirst().get();
        } catch (Exception e){
            return null;
        }
    }

    public boolean addTopic(Topic topicToAdd) {
        try {
            topicRepository.save(topicToAdd);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Deprecated //Using diff one now
    public void addTopic0(Topic topicToAdd) {
        topics.add(topicToAdd);
    }

    public boolean updateTopic(String id, Topic topicToAdd) {
        try {
            topicRepository.save(topicToAdd);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Deprecated //Using diff one now
    public void updateTopic0(String id, Topic topicToAdd) {
        //Using for loop here instead of lambda just for different visual. Lambda likely still better
        for(int i = 0; i < topics.size(); i++){
            Topic t = topics.get(i);
            if(t != null){
                if(Utilities.doesEqual(t.getMyId(), id)){
                    topics.set(i, topicToAdd);
                }
            }
        }
    }

    public boolean deleteTopic(String topicId) {
        try {
            topicRepository.deleteById(topicId);
            //The one below will delete by the object as opposed to the id
            //topicRepository.delete(passedTopic);
            return true;
        } catch (org.springframework.dao.EmptyResultDataAccessException empty){
            //Will trigger if there is no result found with the topic id
            L.m("No topic found with that ID");
        } catch (org.springframework.dao.DataIntegrityViolationException dsql){
            L.m("Tried to delete topic that is tied to a course by a foreign key, failed");
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Deprecated //Using diff one now
    public void deleteTopic0(String topicId) {
        try {
            topics.removeIf(topic -> topic.getMyId().equals(topicId));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
