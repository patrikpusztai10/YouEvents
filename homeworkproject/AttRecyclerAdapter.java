package com.example.homeworkproject;

import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AttRecyclerAdapter extends RecyclerView.Adapter<AttRecyclerAdapter.ViewHolder> {
    private ArrayList<Event> eventList;
    private EventDatabaseHelper db;
    private Boolean star=false;
    public static final String PREFS_NAME = "PREFS_NAME";


    public AttRecyclerAdapter(ArrayList<Event> eventList,EventDatabaseHelper db)
    {
        this.eventList = eventList;
        this.db=db;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,date,location,description;
        private ImageView image;

        FloatingActionButton star_on,star_off;

        public ViewHolder(View view) {
            super(view);

            image=(ImageView) view.findViewById(R.id.eventImage);
            title = (TextView) view.findViewById(R.id.eventTitle);
            date=(TextView) view.findViewById(R.id.eventDate);
            location=(TextView) view.findViewById(R.id.eventLoc);
            description=(TextView) view.findViewById(R.id.eventDes);
            star_on=(FloatingActionButton) view.findViewById(R.id.star_on);
            star_off=(FloatingActionButton) view.findViewById(R.id.star_off);
        }

    }
    @NonNull
    @Override
    public AttRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_att_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttRecyclerAdapter.ViewHolder holder, int position) {
        Event event = eventList.get(position);
        String eventKey=event.getEvent_name();
        holder.title.setText(event.getEvent_name());
        String imagePath = event.getImage();
        if (imagePath != null) {
            Uri uri = Uri.parse(imagePath);
            holder.image.setImageURI(uri);
        } else {
            holder.image.setImageResource(R.drawable.placeholder); // fallback
        }
        holder.date.setText(event.getEvent_date());
        holder.location.setText(event.getEvent_location());
        holder.description.setText(event.getEvent_description());

        SharedPreferences prefs = holder.itemView.getContext().getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE);
        boolean isStarred = prefs.getBoolean(eventKey, false);

        if (isStarred) {
            holder.star_on.setVisibility(View.VISIBLE);
            holder.star_off.setVisibility(View.GONE);
        } else {
            holder.star_on.setVisibility(View.GONE);
            holder.star_off.setVisibility(View.VISIBLE);
        }

        holder.star_off.setOnClickListener(v -> {
            holder.star_off.setVisibility(View.GONE);
            holder.star_on.setVisibility(View.VISIBLE);
            db.incrementInterest(event.getEvent_name());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(eventKey, true);
            editor.apply();
        });

        holder.star_on.setOnClickListener(v -> {
            holder.star_on.setVisibility(View.GONE);
            holder.star_off.setVisibility(View.VISIBLE);
            db.decrementInterest(event.getEvent_name());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(eventKey, false);
            editor.apply();
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
    public void updateEvents(ArrayList<Event> newEvents) {
        this.eventList = newEvents;
    }


}
