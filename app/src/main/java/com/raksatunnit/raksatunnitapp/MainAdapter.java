package com.raksatunnit.raksatunnitapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainAdapter extends BaseAdapter {
    private Context context;
    private List<dayData> dailyDataList;
    private String[] list = {"Viikko 1 Maanantai","Viikko 1 Tiistai","Viikko 1 Keskiviikko","Viikko 1 Torstai","Viikko 1 Perjantai","Viikko 1 Lauantai","Viikko 1 Sunnuntai","Viikko 2 Maanantai","Viikko 2 Tiistai","Viikko 2 Keskiviikko","Viikko 2 Torstai","Viikko 2 Perjantai","Viikko 2 Lauantai","Viikko 2 Sunnuntai"};

    private StringBuilder location;
    private StringBuilder hours;
    private StringBuilder vehicle;
    private StringBuilder commute;
    private String locationS;
    private String hoursS;
    private String vehicleS;
    private String commuteS;
    public MainAdapter(Context c, List<dayData> twoWeekList){
        context = c;
        this.dailyDataList = twoWeekList;

    }

    @Override
    public int getCount() {
        return dailyDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_item, null,true);
        }
        TextView dayTV = view.findViewById(R.id.dayListviewTextView);
        TextView workLocationTV = view.findViewById(R.id.locationListviewTextView);
        TextView workHoursTV= view.findViewById(R.id.hoursListviewTextView);
        TextView commuteDistanceTV = view.findViewById(R.id.commuteListviewTextView);
        TextView carTV = view.findViewById(R.id.carListviewTextView);

        Button editButton = view.findViewById(R.id.editButton);
        Button copyButton = view.findViewById(R.id.copyButton);
        Button pasteButton = view.findViewById(R.id.pasteButton);

        pasteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof Main3Activity) {
                    ((Main3Activity)context).pasteDay(position);
                }
            }
        });
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof Main3Activity) {
                    ((Main3Activity)context).copyDay(position);
                }
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof Main3Activity) {
                    ((Main3Activity)context).openDayediting(position);
                }

            }
        });


        dayTV.setText(list[position]);

        location = new StringBuilder();
         hours = new StringBuilder();
         vehicle = new StringBuilder();
         commute = new StringBuilder();

        for (int i = 0;i < 5;i++){

            if(!dailyDataList.get(position).getWorkLocation(i).equals("")||!dailyDataList.get(position).getWorkCommute(i).equals("")||!dailyDataList.get(position).getWorkHours(i).equals("")||!dailyDataList.get(position).getWorkVehicle(i).equals("")){
                if(i==0 && dailyDataList.get(position).getWorkLocation(i+1).equals("")){
                    location.append("Työmaa").append(": ").append(dailyDataList.get(position).getWorkLocation(i)).append(System.getProperty("line.separator"));
                    hours.append("Työtunnit").append(": ").append(dailyDataList.get(position).getWorkHours(i)).append("h").append(System.getProperty("line.separator"));
                    vehicle.append("Ajoneuvo").append(": ").append(dailyDataList.get(position).getWorkVehicle(i)).append(System.getProperty("line.separator"));
                    commute.append("Työmatka").append(": ").append(dailyDataList.get(position).getWorkCommute(i)).append("km").append(System.getProperty("line.separator"));

                }else{
                    location.append("Työmaa").append(i+1).append(": ").append(dailyDataList.get(position).getWorkLocation(i)).append(System.getProperty("line.separator"));
                    hours.append("Työtunnit").append(i+1).append(": ").append(dailyDataList.get(position).getWorkHours(i)).append("h").append(System.getProperty("line.separator"));
                    vehicle.append("Ajoneuvo").append(i+1).append(": ").append(dailyDataList.get(position).getWorkVehicle(i)).append(System.getProperty("line.separator"));
                    commute.append("Työmatka").append(i+1).append(": ").append(dailyDataList.get(position).getWorkCommute(i)).append("km").append(System.getProperty("line.separator"));
                }
            }
            else{

                if(i == 0){
                    locationS = "Työmaa";
                    hoursS = "Työtunnit";
                    vehicleS = "Ajoneuvo";
                    commuteS = "Työmatka";
                }else{
                    location.setLength(location.length() - 1);
                    hours.setLength(hours.length() - 1);
                    vehicle.setLength(vehicle.length() - 1);
                    commute.setLength(commute.length() - 1);

                    locationS = location.toString();
                    hoursS = hours.toString();
                    vehicleS = vehicle.toString();
                    commuteS = commute.toString();
                }

                break;
            }
            if(i==4){
                location.setLength(location.length() - 1);
                hours.setLength(hours.length() - 1);
                vehicle.setLength(vehicle.length() - 1);
                commute.setLength(commute.length() - 1);

                locationS = location.toString();
                hoursS = hours.toString();
                vehicleS = vehicle.toString();
                commuteS = commute.toString();
            }

        }

        workLocationTV.setText(locationS);
        workHoursTV.setText(hoursS);
        commuteDistanceTV.setText(commuteS);
        carTV.setText(vehicleS);


        ConstraintLayout layout = view.findViewById(R.id.listViewItemLayout);
        if(position % 2 == 0){
            layout.setBackgroundColor(Color.parseColor("#dae8b0"));
        }else{
            layout.setBackgroundColor(Color.parseColor("#f8fee7"));
        }

        return view;
    }
}
