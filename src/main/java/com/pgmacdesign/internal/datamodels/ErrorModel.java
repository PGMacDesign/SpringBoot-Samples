package com.pgmacdesign.internal.datamodels;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorModel {
    private String message;
    private Integer code;
    private String error;
    private String timeStamp;

    public ErrorModel(){}

    public ErrorModel(String error, String message, Integer code){
        this.message = message;
        this.error = error;
        this.code = (code == null) ? 400 : code;
        //Nanoseconds explanation - https://softwareengineering.stackexchange.com/questions/225343/why-are-the-java-8-java-time-classes-missing-a-getmillis-method
        String s = ZonedDateTime.now( ZoneOffset.UTC ).withNano(0).format( DateTimeFormatter.ISO_INSTANT);
        this.timeStamp = s;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}
