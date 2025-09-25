package com.example.homeworkproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuAttendee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_attendee);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_att);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EventDatabaseHelper dbHelper = new EventDatabaseHelper(this);
        EditText search = findViewById(R.id.search_bar);

        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                ArrayList<Event> filteredEvents;
                String search_query = search.getText().toString();
                if (TextUtils.isEmpty(search_query))
                {
                    filteredEvents = dbHelper.readEventsforAtt();
                }
                else {
                    filteredEvents = dbHelper.readEventsSearchQuery(search_query);
                    if (filteredEvents.isEmpty()) {
                        Toast.makeText(MenuAttendee.this, "No events were found", Toast.LENGTH_SHORT).show();
                        filteredEvents = dbHelper.readEventsforAtt();
                    }
                    else
                    {
                        Toast.makeText(MenuAttendee.this, "Found "+filteredEvents.size()+" events", Toast.LENGTH_SHORT).show();
                    }
                }

                AttRecyclerAdapter adapter = new AttRecyclerAdapter(filteredEvents,dbHelper);
                recyclerView.setAdapter(adapter);
                search.setText("");
                return true;
            }
            return false;
        });
        ArrayList<Event> eventList;
        eventList=dbHelper.readEventsforAtt();
        AttRecyclerAdapter adapter = new AttRecyclerAdapter(eventList,dbHelper);
        recyclerView.setAdapter(adapter);


    }

}
