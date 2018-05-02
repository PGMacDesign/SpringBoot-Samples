package com.pgmacdesign.internal.datamodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DBLogging")
@Document(collection="DBLogging")
public class DBLogging {

    public static final String EVENT_FIX_BROKEN_USERS = "Fix_Broken_Users";
    public static final String EVENT_CRON_JOB_RAN = "Cron_Job_Ran";
    public static final String EVENT_EXCEPTION = "Exception_Caught";
    public static final String EVENT_TBD3 = "tbd3";

    @Id
    @org.springframework.data.annotation.Id
    private String _id;
    //No getters or setters for _id
    private String description;
    private String event;
    private String dateTime;
    private String misc;
    private String stackTrace;

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMisc() {
        return misc;
    }

    public void setMisc(String misc) {
        this.misc = misc;
    }
}
