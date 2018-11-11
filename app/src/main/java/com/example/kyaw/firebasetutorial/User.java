package com.example.kyaw.firebasetutorial;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String email;
    public String name;
    public String profileimageurl;
    public String time;
    public String message;



    public User(){

    }
    public User(String name,String email,String profileimageurl,String time,String message){
        this.name=name;
        this.email=email;
        this.time=time;
        this.message=message;

        this.profileimageurl=profileimageurl;
    }



}
