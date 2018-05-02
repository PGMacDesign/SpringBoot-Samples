package com.pgmacdesign.internal.controller;


import com.pgmacdesign.internal.datamodels.Course;
import com.pgmacdesign.internal.datamodels.Topic;
import com.pgmacdesign.internal.services.CourseService;
import com.pgmacdesign.internal.services.CourseServiceMongo;
import com.pgmacdesign.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    @Autowired  //Spring annotation to mark it as requiring dependency injection
    private CourseService instance;
    @Autowired  //Spring annotation to mark it as requiring dependency injection
    private CourseServiceMongo instanceM;

    @RequestMapping("/topics/{id}/courses")
    public List<Course> getAllCourses(@PathVariable("id") String topicId){
        if(Constants.USE_MONGODB_OVER_SQL){
            List<Course> courses = instanceM.getAllCourses();
            return courses;
        } else {
            List<Course> courses = instance.getAllCourses(topicId);
            return courses;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="/topics/courses/{courseId}") //Get request as per path being utilized
    public Course getSingleCourse(@PathVariable("courseId") String courseId){
        try {
            if(Constants.USE_MONGODB_OVER_SQL){
                return instanceM.getCourse(courseId);
            } else {
                return instance.getCourse(courseId);
            }

        } catch (Exception e){
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="/courses/query/{id}")
    public List<Course> queryCourses(@PathVariable("id") String query){
        if(Constants.USE_MONGODB_OVER_SQL) {
            List<Course> courses = instanceM.queryCourses(query);
            return courses;
        } else {
//            List<Course> topics = instance.queryCourses(query);
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value= "/topics/{topicId}/courses")
    public boolean addCourse(@RequestBody Course courseToAdd,
                             @PathVariable("topicId") String topicId){
        try {
            courseToAdd.setTopic(new Topic(topicId, "", ""));
            if(Constants.USE_MONGODB_OVER_SQL){
                return instanceM.addCourse(courseToAdd);
            } else {
                return instance.addCourse(courseToAdd);
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value= "/topics/{topicId}/courses")
    public boolean updateCourse(@PathVariable String topicId,
                               @RequestBody Course courseToAdd){
        try {
            courseToAdd.setTopic(new Topic(topicId, "", ""));
            if(Constants.USE_MONGODB_OVER_SQL) {
                return instanceM.updateCourse(topicId, courseToAdd);
            } else {
                return instance.updateCourse(courseToAdd);
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value= "/topics/{topicId}/courses/{id}")
    public boolean deleteSingleCourse(@PathVariable("id") String courseId,
                                      @PathVariable("topicId") String topicId){
        try {
            if(Constants.USE_MONGODB_OVER_SQL) {
                return instanceM.deleteCourse(courseId);
            } else {
                return instance.deleteCourse(courseId);
            }
        } catch (Exception e){
            return false;
        }
    }

}
