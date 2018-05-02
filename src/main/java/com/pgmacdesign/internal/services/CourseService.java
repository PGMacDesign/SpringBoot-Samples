package com.pgmacdesign.internal.services;

import com.pgmacdesign.internal.datamodels.Course;
import com.pgmacdesign.internal.datamodels.Topic;
import com.pgmacdesign.utils.L;
import com.pgmacdesign.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class will work if using mysql, derby, or mongodb; but, the object will be overridden entirely via the save()
 * method. I have decided to write a different version of this class with Mongo as the suffix to demo
 */
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses(){
        List<Course> topics = new ArrayList<>();
        courseRepository.findAll().forEach(topics::add);
        return topics;
    }

    public List<Course> getAllCourses(String topicId){
        //        courseRepository.findByTopicId(topicId).forEach(topics::add);
        try {
            return new ArrayList<>(courseRepository.findByMyId(topicId));
        } catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Course getCourse(String courseId){
        try {
            return courseRepository.findByMyId(courseId).get(0);
        } catch (Exception e){
            return null;
        }
    }

    public Course getCourseByName(String name){
        try {
            return courseRepository.findByName(name).get(0);
        } catch (Exception e){
            return null;
        }
    }

    public boolean addCourse(Course courseToAdd) {
        try {
            courseRepository.save(courseToAdd);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean updateCourse(Course courseToUpdate) {
        try {
            courseRepository.save(courseToUpdate);
            return true;
//        } catch (org.springframework.orm.jpa.JpaObjectRetrievalFailureException jpa){
//            L.m("Could not find object to update");
//            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCourse(String courseId) {
        try {
            courseRepository.deleteById(courseId);
            //The one below will delete by the object as opposed to the id
            //courseRepository.delete(passedTopic);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}

