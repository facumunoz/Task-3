package com.example.task1;

import java.sql.Time;
import java.util.Date;

public class Visit {

     private float date;
     private float stime;
     private float etime;

     public Visit(Float date, Float stime, Float etime) {
         this.date = date;
         this.stime = stime;
         this.etime = etime;
     }

     public Float getDate() {
         return date;
     }

     public void setDate(Float date) {
         this.date = date;
     }

     public Float getStime() {
         return stime;
     }

     public void time(Float time) {
         this.stime = time;
     }

    public float getEtime() {
        return etime;
    }

    public void setEtime(Float etime) {
        this.etime = etime;
    }
}
