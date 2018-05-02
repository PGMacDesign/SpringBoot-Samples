package com.pgmacdesign.internal.services;

import com.pgmacdesign.internal.datamodels.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/*
Use this for running mysql or derby:
    public interface CourseRepository extends CrudRepository<Course, String> {
Use this for running mongodb:
    public interface CourseRepository extends MongoRepository<Course, String> {
 */
public interface CourseRepository extends MongoRepository<Course, String> {
    public List<Course> findByName(String name); //Get all courses by name
    public List<Course> findByDescription(String description); //Get all courses by description
    public List<Course> findByTopicId(String topicId);
    public List<Course> findByMyId(String myId);


}
