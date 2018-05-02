package com.pgmacdesign.internal.datamodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name="User")
@Document(collection="User")
public class User {

    public static enum UserRoles {
        RegularUser, CompanyEmployee, CompanyAdmin, AppAdmin
    }

    @Id
    @org.springframework.data.annotation.Id
    @JsonIgnore
    private String _id;
    //No getters or setters for _id
    @SerializedName("id")
    private String id;
    @SerializedName("username")
    @Indexed
    private String username;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("userRole")
    private UserRoles userRole;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("age")
    private Integer age;
    @SerializedName("weight")
    private Double weight;
    @SerializedName("email")
    @Indexed
    private String email;
    @SerializedName("password")
    @JsonIgnore
    @Transient
    private String password;
    @SerializedName("hashedPassword")
    @JsonIgnore
    private String hashedPassword;
    @SerializedName("salt")
    @JsonIgnore
    private String salt;
    @SerializedName("note")
    private String note;
    @SerializedName("temp1")
    private String temp1;
    @SerializedName("authToken")
    private String authToken;
    @SerializedName("authTokenExpiryTime")
    private Long authTokenExpiryTime;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Long getAuthTokenExpiryTime() {
        return authTokenExpiryTime;
    }

    public void setAuthTokenExpiryTime(Long authTokenExpiryTime) {
        this.authTokenExpiryTime = authTokenExpiryTime;
    }

    public UserRoles getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoles userRole) {
        this.userRole = userRole;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String get_id(){
        return this._id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
