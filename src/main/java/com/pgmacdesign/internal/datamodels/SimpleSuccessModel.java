package com.pgmacdesign.internal.datamodels;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleSuccessModel {
    private Boolean successful;
    private String result;
    private String description;

    public SimpleSuccessModel(){}

    public SimpleSuccessModel(boolean successful, String result){
        this.result = result;
        this.successful = successful;
    }

    public SimpleSuccessModel(boolean successful, String result, String description){
        this.result = result;
        this.successful = successful;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
