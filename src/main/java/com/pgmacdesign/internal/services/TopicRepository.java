package com.pgmacdesign.internal.services;

import com.pgmacdesign.internal.datamodels.Course;
import com.pgmacdesign.internal.datamodels.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Types for extend are <Class for persisting, type of ID (ie an int, String, etc)>
 *
 */
/*
Use this for running mysql or derby:
    public interface CourseRepository extends CrudRepository<Course, String> {
Use this for running mongodb:
    public interface CourseRepository extends MongoRepository<Course, String> {
 */
public interface TopicRepository extends MongoRepository<Topic, String> {

    public List<Topic> findByMyId(String myId);
}
