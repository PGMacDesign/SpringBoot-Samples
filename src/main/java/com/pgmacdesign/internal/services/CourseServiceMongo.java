package com.pgmacdesign.internal.services;

import com.pgmacdesign.internal.logging.MyErrorLogger;
import com.pgmacdesign.internal.datamodels.Course;
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
public class CourseServiceMongo {

    @Autowired
    private MongoOperations mongoOperations;

    public List<Course> queryCourses(@NonNull String searchQuery){
        List<Course> courses = new ArrayList<>();
        try {
            Query query = new Query();
            //query.limit(10); //Use this if a limit should be set, IE, pagination
            Criteria criteria = Criteria.where("description").regex(searchQuery)
                    .orOperator(Criteria.where("name").regex(searchQuery));
            //Cannot add >2 --> InvalidMongoDbApiUsageException: Due to limitations of the com.mongodb.BasicDocument, you can't add a second '$or' expression specified as '$or
            query.addCriteria(criteria);
            courses = mongoOperations.find(query, Course.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
        return courses;
    }

    public List<Course> queryCourses2(@NonNull String searchQuery){
        List<Course> courses = new ArrayList<>();
        try {
            BasicQuery query = new BasicQuery("{\"tagName\": {$regex : '" + searchQuery + "'} }");
            //query.limit(10); //Use this if a limit should be set, IE, pagination
            courses = mongoOperations.find(query, Course.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
        return courses;
    }

    public List<Course> getAllCourses(){
        List<Course> courses = new ArrayList<>();
        try {
            courses = mongoOperations.findAll(Course.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
        }
        return courses;
    }

    public Course getCourse(String courseId){
        try {
            return mongoOperations.findOne(CourseServiceMongo.buildCourseIdQuery(courseId), Course.class);
        } catch (Exception e){
            MyErrorLogger.logError(e);
            return null;
        }
    }

    public boolean addCourse(Course courseToAdd) {
        try {
            if(getCourse(courseToAdd.getMyId()) != null){
                L.m("course already exists in db!");
                return false;
            } else {
                mongoOperations.save(courseToAdd);
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean updateCourse(String id, Course courseToAdd) {
        try {
            Course c = getCourse(id);
            if(c == null){
                return false;
            }
            Topic topic = c.getTopic();
            if(topic == null){
                topic = new Topic();
            }
            topic.setDescription("updated description in topic");
            topic.setName("updatedName in topic");
            Update update = new Update();
            update = update.set("name", courseToAdd.getName());
            update = update.set("description", courseToAdd.getDescription());
            update = update.set("topic", topic);
            mongoOperations.updateFirst(CourseServiceMongo.buildCourseIdQuery(id), update, Course.class);
            return true;
        } catch (Exception e){
            MyErrorLogger.logError(e);
            return false;
        }
    }


    public boolean deleteCourse(String courseId) {
        try {
            Course c = getCourse(courseId);
            if(c == null){
                return false;
            }
            mongoOperations.remove(c);
            //The one below will delete by the object as opposed to the id
            //courseRepository.delete(passedCourse);
            return true;
        } catch (org.springframework.dao.EmptyResultDataAccessException empty){
            //Will trigger if there is no result found with the course id
            L.m("No course found with that ID");
        } catch (org.springframework.dao.DataIntegrityViolationException dsql){
            L.m("Tried to delete course that is tied to a course by a foreign key, failed");
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    private static Query buildCourseIdQuery(@NonNull String courseId){
        return new Query(Criteria.where("myId").is(courseId));
    }
    
}
