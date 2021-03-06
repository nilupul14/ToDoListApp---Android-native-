package com.example.todotestapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CreateTask extends AppCompatActivity implements View.OnClickListener{

    EditText task;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button addButton;

    DatabaseReference databaseToDo;

    Button btnDatePicker;
    Button btnTimePicker;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String priority ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        FirebaseApp.initializeApp(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseToDo = database.getReference("todo");
//        databaseToDo.setValue("hello paul");
        database.getReference("app_title").setValue("ToDoList Database");


        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        task = findViewById(R.id.etTask);

        radioGroup = (RadioGroup) findViewById(R.id.rgPriority);
        radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());

        priority = radioButton.getText().toString();

        addButton = (Button) findViewById(R.id.btn_add);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){

                // Get the selected Radio Button
                switch (checkedId){
                    case R.id.rb_high:
                        priority = "HIGH";
                        break;
                    case R.id.rb_medium:
                        priority = "MEDIUM";
                        break;
                    case R.id.rb_low:
                        priority = "LOW";
                        break;
                }
            }
        });

        final Task newTask = new Task("Reminder 1", "1");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newTask.setTaskName(task.getText().toString());
                newTask.setTaskPriority(priority);
                databaseToDo.child("todos").setValue(newTask);

                // Go to all task activity
                Intent goToAllTastIntentAfterAdd = new Intent(CreateTask.this, AllTaskList.class);
                startActivity(goToAllTastIntentAfterAdd);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

//                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

//                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}
