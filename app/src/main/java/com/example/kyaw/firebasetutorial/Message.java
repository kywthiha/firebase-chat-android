package com.example.kyaw.firebasetutorial;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Message {
    public String userid;
    public String messagetext;
    public String adminid;
    public String type;
    public Message(){

    }
    public Message(String userid,String messagetext,String adminid,String type){
        this.userid=userid;
        this.messagetext=messagetext;
        this.type=type;
        this.adminid=adminid;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userid", userid);
        result.put("messgetext", messagetext);
        result.put("adminid",adminid);
        result.put("type",type);
        return result;
    }
}
