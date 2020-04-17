package com.example.raksatunnitapp;

import java.io.Serializable;

public class dayData implements Serializable{
    String  workHours = "";
    String workCommute = "";
    String workLocation = "";
    String workVehicle = "";
    public void setWorkHours(String hours){ workHours = hours; }
    public void setWorkCommute(String commute){
    workCommute = commute;
    }
    public void setWork(String location){
    workLocation = location;
    }
    public void setWorkVehicle(String vehicle)
    {
     workVehicle = vehicle;
    }

    public String getWorkHours(){
        return workHours;
    }
    public String getWorkCommute(){
        return workCommute;
    }
    public String getWorkVehicle(){
        return workVehicle;
    }
    public String getWorkLocation(){
        return workLocation;
    }
}
