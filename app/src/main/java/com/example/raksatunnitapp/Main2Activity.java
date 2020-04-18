package com.example.raksatunnitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";
    int index = 0;
    List<dayData> dailyDataList = new ArrayList<>();

    Spinner hoursSpinner;
    Spinner carSpinner;
    EditText location;
    EditText commute;
    TextView dayText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dayText = findViewById(R.id.dayTextView);
        List<String> dayList = Arrays.asList(getResources().getStringArray(R.array.two_week_array));


        carSpinner = findViewById(R.id.carSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.car_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carSpinner.setAdapter(adapter);

        hoursSpinner = findViewById(R.id.workHoursSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.work_hours_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hoursSpinner.setAdapter(adapter2);


        location = findViewById(R.id.locationEditText);
        commute = findViewById(R.id.distanceEditText);

        String value = getIntent().getStringExtra("key");
        index = Integer.parseInt(value);

        dayText.setText(dayList.get(index));

        dailyDataList = (List<dayData>) getIntent().getSerializableExtra("list");

        String compareCarValue = dailyDataList.get(index).getWorkVehicle();
        String compareHoursValue = dailyDataList.get(index).getWorkHours();;


        if (compareHoursValue != null) {
            int spinnerPosition = adapter2.getPosition(compareHoursValue);
            hoursSpinner.setSelection(spinnerPosition);
        }
        if (compareCarValue != null) {
            int spinnerPosition = adapter.getPosition(compareCarValue);
            carSpinner.setSelection(spinnerPosition);
        }

        Button saveButton = (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               dailyDataList.get(index).setWork(location.getText().toString());
               dailyDataList.get(index).setWorkCommute(commute.getText().toString());
                dailyDataList.get(index).setWorkHours(hoursSpinner.getSelectedItem().toString());
                dailyDataList.get(index).setWorkVehicle(carSpinner.getSelectedItem().toString());


                Intent myIntent = new Intent(Main2Activity.this, Main3Activity.class);
                myIntent.putExtra("list2",(Serializable) dailyDataList);
                setResult(1000,myIntent);

                finish();
            }
        });



        location.setText(dailyDataList.get(index).getWorkLocation());
        commute.setText((dailyDataList.get(index).getWorkCommute()));

    }
}
