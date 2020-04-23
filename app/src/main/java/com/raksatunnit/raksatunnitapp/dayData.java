package com.raksatunnit.raksatunnitapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dayData implements Serializable{
    private String  workHours = "";
    private String workCommute = "";
    private String workLocation = "";
    private String workVehicle = "";

    List<String> workHoursList = new ArrayList<>(Arrays.asList( "", "", "","",""));
    List<String> workCommuteList = new ArrayList<>(Arrays.asList( "", "", "","",""));
    List<String> workLocationList = new ArrayList<>(Arrays.asList( "", "", "","",""));
    List<String> workVehicleList= new ArrayList<>(Arrays.asList( "", "", "","",""));


    void setWorkHours(String hours, int page)
    {
        workHoursList.set(page,hours);
    }
    void setWorkCommute(String commute, int page){
        workCommuteList.set(page,commute);
    }
    void setWork(String location, int page){
        workLocationList.set(page,location);
    }
    void setWorkVehicle(String vehicle, int page)
    {
        workVehicleList.set(page,vehicle);
    }

     String getWorkHours(int page){
        return workHoursList.get(page);
    }
     String getWorkCommute(int page){
        return workCommuteList.get(page);
    }
     String getWorkVehicle(int page){
        return workVehicleList.get(page);
    }
     String getWorkLocation(int page){
        return workLocationList.get(page);
    }
}
