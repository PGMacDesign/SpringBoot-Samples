package com.pgmacdesign.internal.datamodels;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmptyObject {
    /*
    If the name field is null, this will send back as an empty JSON object: "{}"
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
