package com.example.kyaw.firebasetutorial.chat.model;

import java.io.Serializable;

public class Chat implements Serializable {
    private String userid;
    private String adminid;
    private String username;
    private String lastmessage;
    private String time;

    public Chat() {
    }

    public Chat(String userid, String adminid,String username, String lastmessage, String time) {
        this.userid = userid;
        this.adminid=adminid;
        this.username = username;
        this.lastmessage = lastmessage;
        this.time = time;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
