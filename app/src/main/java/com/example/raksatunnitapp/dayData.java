package com.example.raksatunnitapp;

import java.io.Serializable;

public class dayData implements Serializable{
    private String  workHours = "";
    private String workCommute = "";
    private String workLocation = "";
    private String workVehicle = "";

    void setWorkHours(String hours){ workHours = hours; }
    void setWorkCommute(String commute){
    workCommute = commute;
    }
    void setWork(String location){
    workLocation = location;
    }
    void setWorkVehicle(String vehicle)
    {
     workVehicle = vehicle;
    }

     String getWorkHours(){
        return workHours;
    }
     String getWorkCommute(){
        return workCommute;
    }
     String getWorkVehicle(){
        return workVehicle;
    }
     String getWorkLocation(){
        return workLocation;
    }
}
