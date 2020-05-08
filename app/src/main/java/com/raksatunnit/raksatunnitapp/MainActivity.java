package com.raksatunnit.raksatunnitapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    List<dayData> dailyDataList = new ArrayList<dayData>();
    Button workHourListButton;
    Button sendButton;
    boolean openList;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NAME = "text";
    public static final String EMPLOYER_EMAIL = "text1";
    public static final String WEEK = "text2";
    public static final String LIST= "text3";
    EditText nameET;
    EditText emailET;
    EditText weekET;
    EditText informationET;
    EditText workDriving;
    private String name;
    private String email;
    private String week;
    String emailMessage;
    //private String[] dayList = {"Viikko 1 Maanantai","Viikko 1 Tiistai","Viikko 1 Keskiviikko","Viikko 1 Torstai","Viikko 1 Perjantai","Viikko 1 Lauantai","Viikko 1 Sunnuntai","Viikko 2 Maanantai","Viikko 2 Tiistai","Viikko 2 Keskiviikko","Viikko 2 Torstai","Viikko 2 Perjantai","Viikko 2 Lauantai","Viikko 2 Sunnuntai"};
    private String[] dayList = {"Viikko 1 Ma","Viikko 1 Ti","Viikko 1 Ke","Viikko 1 To","Viikko 1 Pe","Viikko 1 La","Viikko 1 Su","Viikko 2 Ma","Viikko 2 Ti","Viikko 2 Ke","Viikko 2 To","Viikko 2 Pe","Viikko 2 La","Viikko 2 Su"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameET = findViewById(R.id.nameEditText);
        emailET = findViewById(R.id.employerEditText);
        weekET = findViewById(R.id.week1EditText);
        informationET = findViewById(R.id.informationEditText);
        workDriving = findViewById(R.id.drivingEditText);

        workHourListButton = findViewById(R.id.workHourListButton);

        workHourListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(openList){
                    Intent myIntent = new Intent(MainActivity.this, Main3Activity.class);
                    myIntent.putExtra("list",(Serializable) dailyDataList);
                    openList = false;
                    saveData();
                    startActivityForResult(myIntent,1000);
                }

            }
        });
        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

                sendEmail();
            }
        });


        if(dailyDataList.size()<14){
            for(int i = dailyDataList.size();i < 14;i++){
                dailyDataList.add(i,new dayData());
            }
        }
        loadData();
        updateViews();

    }

    @Override
    protected void onStart() {
        super.onStart();
        openList = true;
        workHours();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            dailyDataList = (List<dayData>) data.getSerializableExtra("list2");
            saveData();
        }

    }
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME,nameET.getText().toString());
        editor.putString(EMPLOYER_EMAIL,emailET.getText().toString());
        editor.putString(WEEK,weekET.getText().toString());
        Gson gson = new Gson();
        String json = gson.toJson(dailyDataList);
        editor.putString(LIST,json);

        editor.apply();
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        name = sharedPreferences.getString(NAME,"");
        email = sharedPreferences.getString(EMPLOYER_EMAIL,"");
        week = sharedPreferences.getString(WEEK,"");

        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST,null);
        Type type = new TypeToken<ArrayList<dayData>>() {}.getType();
        dailyDataList = gson.fromJson(json, type);

        if(dailyDataList == null){
            dailyDataList = new ArrayList<dayData>();

            if(dailyDataList.size()<14){
                for(int i = dailyDataList.size();i < 14;i++){
                    dailyDataList.add(i,new dayData());
                }
            }
        }

    }
    public void updateViews(){
        nameET.setText(name);
         emailET.setText(email);
        weekET.setText(week);
    }


    public void sendEmail(){


        String[] TO = {emailET.getText().toString()};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("message/rfc822");


        buildEmailMessage();

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tuntilista: " + nameET.getText().toString() + " /Viikko: "+weekET.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailMessage);try {
            startActivity(Intent.createChooser(emailIntent, "Send mail /n"));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "Puhelimessa ei ole Sähköpostisovellusta.", Toast.LENGTH_SHORT).show();
        }

    }

    public void buildEmailMessage(){
        int days = dailyDataList.size();
        StringBuilder message = new StringBuilder();
        double workHoursCombined = workHours();
        message.append("Viikko: ").append(weekET.getText().toString()).append(System.getProperty("line.separator"));
        message.append("Nimi: ").append(nameET.getText().toString()).append(System.getProperty("line.separator"));
        message.append("Lisätietoja: ").append(informationET.getText().toString()).append(System.getProperty("line.separator"));
        message.append("Työmaa-ajo: ").append(workDriving.getText().toString()).append("km ").append(System.getProperty("line.separator"));
        message.append("Työtunnit yhteensä: ").append(workHours()).append("h ").append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));


        for(int i = 0;i < days;i++){
            for(int x = 0;x < 5;x++){
                if(!dailyDataList.get(i).getWorkLocation(x).equals("")){
                    message.append(dayList[i]).append(" /Työmaa: ").append(dailyDataList.get(i).getWorkLocation(x)).append(" /Työmatka: ").append(dailyDataList.get(i).getWorkCommute(x)).append(" /Ajoneuvo: ").append(dailyDataList.get(i).getWorkVehicle(x)).append(" /Tunnit: ").append(dailyDataList.get(i).getWorkHours(x)).append(System.getProperty("line.separator"));
                }else{
                    if(x==0){
                        message.append(dayList[i]).append(" /Työmaa: ").append(dailyDataList.get(i).getWorkLocation(x)).append(" /Työmatka: ").append(dailyDataList.get(i).getWorkCommute(x)).append(" /Ajoneuvo: ").append(dailyDataList.get(i).getWorkVehicle(x)).append(" /Tunnit: ").append(dailyDataList.get(i).getWorkHours(x)).append(System.getProperty("line.separator"));
                    }

                    break;
                }
            }

            message.append(System.getProperty("line.separator"));
        }

        emailMessage = message.toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveData();
    }

    public double workHours(){
        double workHoursSum = 0;
        for(int i = 0;i<dailyDataList.size();i++){
            for(int x = 0;x < 5; x++)
            {
                if (!dailyDataList.get(i).getWorkHours(x).equals("")){
                    workHoursSum = workHoursSum + Double.parseDouble(dailyDataList.get(i).getWorkHours(x));
                }

            }
        }

        return workHoursSum;
    }
}
