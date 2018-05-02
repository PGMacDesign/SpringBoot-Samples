package com.pgmacdesign.internal.datamodels;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Topic")
@Document(collection="Topic")
public class Topic {
    @Id
    @org.springframework.data.annotation.Id
    private String id;
    //No getters or setters for id
    @SerializedName("myId")
    private String myId;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;

    public Topic(){}

    public Topic(String myId, String name, String description) {
        this.myId = myId;
        this.name = name;
        this.description = description;
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
