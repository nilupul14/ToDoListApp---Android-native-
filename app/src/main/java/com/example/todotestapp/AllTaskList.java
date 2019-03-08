package com.example.todotestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllTaskList extends AppCompatActivity {

    public static View.OnClickListener myOnClickListener;
    private List<Task> toDoList;

    private ArrayList<Task> taskArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskAdapter tAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_task_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotToAddTaskIntent = new Intent(AllTaskList.this, CreateTask.class);
                startActivity(gotToAddTaskIntent);

            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        tAdapter = new TaskAdapter(taskArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tAdapter);

        listTaskData();
    }

    private void listTaskData(){

        DatabaseReference todoListDbReference = FirebaseDatabase.getInstance().getReference();
        todoListDbReference.child("todo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setToDoListData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tAdapter.notifyDataSetChanged();
    }

    private void setToDoListData(DataSnapshot dataSnapshot) {

        for (DataSnapshot data : dataSnapshot.getChildren()) {

            Task tasks = data.getValue(Task.class);
            taskArrayList.add(tasks);
        }
        setDataToAdapter();
    }

    public void setDataToAdapter() {
        tAdapter.notifyDataSetChanged();
    }
}
