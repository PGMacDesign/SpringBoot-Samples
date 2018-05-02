package com.pgmacdesign.internal.datamodels;


import com.google.gson.annotations.SerializedName;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Course")
@Document(collection="Course")
public class Course {
    @SerializedName("id")
    @Id
    @org.springframework.data.annotation.Id
    private String id;
    //No getter or setter for id
    @SerializedName("myId")
    private String myId;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("topic")
    @ManyToOne //Link this as a foreign key to the topic table
    private Topic topic;

    public Course(){}

    public Course(String myId, String name, String description) {
        this.myId = myId;
        this.name = name;
        this.description = description;
    }

    public Course(String myId, String name, String description, String topicId) {
        super();
        this.myId = myId;
        this.name = name;
        this.description = description;
        this.topic = new Topic(topicId, "", "");
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

