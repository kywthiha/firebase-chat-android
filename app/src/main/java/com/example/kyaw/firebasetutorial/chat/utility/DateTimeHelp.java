package com.example.kyaw.firebasetutorial.chat.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeHelp {
    public static String getTime(long milisecond){
        Date now=new Date(milisecond);
        DateFormat fromat=new SimpleDateFormat("hh:mm aaa");
        fromat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
       return fromat.format(now);
    }
}
