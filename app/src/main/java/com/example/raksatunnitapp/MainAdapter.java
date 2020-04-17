package com.example.raksatunnitapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class MainAdapter extends BaseAdapter {
    private Context context;
    private List<dayData> dailyDataList;
    private String[] list = {"Viikko 1 Maanantai","Viikko 1 Tiistai","Viikko 1 Keskiviikko","Viikko 1 Torstai","Viikko 1 Perjantai","Viikko 1 Lauantai","Viikko 1 Sunnuntai","Viikko 2 Maanantai","Viikko 2 Tiistai","Viikko 2 Keskiviikko","Viikko 2 Torstai","Viikko 2 Perjantai","Viikko 2 Lauantai","Viikko 2 Sunnuntai"};
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
        workLocationTV.setText("Työmaa: " + dailyDataList.get(position).getWorkLocation());
        workHoursTV.setText("Tunnit: "+ dailyDataList.get(position).getWorkHours());
        commuteDistanceTV.setText("Työmatka: " + dailyDataList.get(position).getWorkCommute());
        carTV.setText("Ajoneuvo: " + dailyDataList.get(position).getWorkVehicle());


        ConstraintLayout layout = view.findViewById(R.id.listViewItemLayout);
        if(position % 2 == 0){
            layout.setBackgroundColor(Color.parseColor("#dae8b0"));
        }else{
            layout.setBackgroundColor(Color.parseColor("#f8fee7"));
        }

        return view;
    }
}
