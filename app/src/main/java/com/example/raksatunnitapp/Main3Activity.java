package com.example.raksatunnitapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    private static final String TAG = "Main3Activity";
    List<dayData> dailyDataList = new ArrayList<>();

    ListView hoursWorkedList;

    MainAdapter adapter;

    String workDayHours;
    String commuteDistance;
    String vehicle;
    String workLocation;

    int listIndex2;
    View v2;
    int top2;

    boolean allowEditButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        hoursWorkedList = findViewById(R.id.hourListview);
        dailyDataList = (List<dayData>) getIntent().getSerializableExtra("list");

        adapter = new MainAdapter(Main3Activity.this,dailyDataList);
        hoursWorkedList.setAdapter(adapter);



    }

    public void openDayediting(int index){
        if(allowEditButton){
            allowEditButton = false;
            listIndex2 = hoursWorkedList.getFirstVisiblePosition();
            v2 = hoursWorkedList.getChildAt(0);
            top2 = (v2 == null) ? 0 : (v2.getTop() - hoursWorkedList.getPaddingTop());

            Intent myIntent = new Intent(Main3Activity.this, Main2Activity.class);
            myIntent.putExtra("key",Integer.toString(index));
            myIntent.putExtra("list",(Serializable) dailyDataList);
            startActivityForResult(myIntent,1000);
        }
    }
    public void copyDay(int index){
        Toast.makeText(Main3Activity.this,
                "Päivä kopioitu", Toast.LENGTH_SHORT).show();
         workDayHours = dailyDataList.get(index).getWorkHours();
         commuteDistance= dailyDataList.get(index).getWorkCommute();
         vehicle= dailyDataList.get(index).getWorkVehicle();
         workLocation= dailyDataList.get(index).getWorkLocation();

    }
    void pasteDay(int index){
        int listIndex = hoursWorkedList.getFirstVisiblePosition();
        View v = hoursWorkedList.getChildAt(0);
        int top = (v == null) ? 0 : (v.getTop() - hoursWorkedList.getPaddingTop());


        if(workDayHours != null)
        {
            dailyDataList.get(index).setWorkHours(workDayHours);
            dailyDataList.get(index).setWork(workLocation);
            dailyDataList.get(index).setWorkCommute(commuteDistance);
            dailyDataList.get(index).setWorkVehicle(vehicle);
        }

        adapter = new MainAdapter(Main3Activity.this,dailyDataList);
        hoursWorkedList.setAdapter(adapter);

        hoursWorkedList.setSelectionFromTop(listIndex, top);

    }

    @Override
    protected void onStart() {
        super.onStart();
        allowEditButton = true;
        Intent myIntent = new Intent(Main3Activity.this, MainActivity.class);
        myIntent.putExtra("list2",(Serializable) dailyDataList);
        setResult(1000,myIntent);

        hoursWorkedList.setSelectionFromTop(listIndex2, top2);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            dailyDataList = (List<dayData>) data.getSerializableExtra("list2");
            adapter = new MainAdapter(Main3Activity.this,dailyDataList);
            hoursWorkedList.setAdapter(adapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.actionbar1,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void refreshListview(MenuItem item){
        for(int i = 0;i<dailyDataList.size();i++)
        {
            dailyDataList.get(i).setWorkVehicle("");
            dailyDataList.get(i).setWork("");
            dailyDataList.get(i).setWorkCommute("");
            dailyDataList.get(i).setWorkHours("");
        }

        adapter = new MainAdapter(Main3Activity.this,dailyDataList);
        hoursWorkedList.setAdapter(adapter);
        Toast.makeText(Main3Activity.this,
                "Päivät nollattu", Toast.LENGTH_SHORT).show();

    }
}
