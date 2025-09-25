package com.example.homeworkproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MenuRepresentative extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_eventrep); // Loads the layout
        String username = getIntent().getStringExtra("username");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EventDatabaseHelper dbHelper = new EventDatabaseHelper(this);
        ArrayList<Event> eventList = dbHelper.readEventsforReps(username);
        ReprRecyclerAdapter adapter = new ReprRecyclerAdapter(eventList);
        recyclerView.setAdapter(adapter);
        FloatingActionButton addEventButton;
        addEventButton=findViewById(R.id.newEventButton);
        addEventButton.setOnClickListener(view->{
            try{
                Intent intent=new Intent(getApplicationContext(),AddEvents.class);
                intent.putExtra("username", username);
                startActivity(intent);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }



}
