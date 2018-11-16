package com.example.kyaw.firebasetutorial.chat.utility;

import android.content.Context;
import android.graphics.Bitmap;

public class Image {
    private Bitmap bitmap;
    private String userid;
    private String adminid;

    public Image() {
    }

    public Image( Bitmap bitmap, String userid, String adminid) {
        this.bitmap = bitmap;
        this.userid = userid;
        this.adminid = adminid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
