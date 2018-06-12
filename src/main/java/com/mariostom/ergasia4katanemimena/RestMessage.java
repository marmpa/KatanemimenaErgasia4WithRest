//Λιάρος Θωμάς - icsd15107
//Μπαντόλας Μάριος Δημήτρης - icsd15137
package com.mariostom.ergasia4katanemimena;



import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestMessage implements Serializable {
    private String name;
    private String surname;
    private String username;
    private String gender;
    private String description;
    private String country;
    private String city;
    private String post;
    private String user_received;
    private String id;
    private Date date;

    public RestMessage(String id ,String name,String user_received, String post) {
        this.id = id;
        this.name = name;
        this.post = post;
        this.user_received = user_received;
        this.date = new Date();
    }

    public RestMessage(String name, String surname, String username, Date date, String gender, String description, String country, String city) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.date = date;
        this.gender = gender;
        this.description = description;
        this.country = country;
        this.city = city;
    }
    
    public RestMessage(String name, String username)
    {
        this.name=name;
        this.user_received=username;
    }
    
    public RestMessage(){}

    //Getters Παρακάτω
    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getUsername() {
        return this.username;
    }

    public String getGender() {
        return this.gender;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCountry() {
        return this.country;
    }

    public String getCity() {
        return this.city;
    }

    public String getPost() {
        return this.post;
    }

    public String getUser_received() {
        return this.user_received;
    }

    
    public Date getDate() {
        return this.date;
    }
    
    public String getID() {
        return this.id;
    }
    
    //Setters Παρακάτω
    //................
    //................
    //................
    //................
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setUser_received(String user_received) {
        this.user_received = user_received;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @JsonDeserialize(using = JsonDateDeserializer.class)
    public void setDate(Date date) 
    {
        this.date = date;
    }


    public String showPost() {
        return "O xristis " + name + " tin mera:" +date + " dimosieuse sto profil tou " +user_received+ " to eksis: " + post;
    }
}

    

    
