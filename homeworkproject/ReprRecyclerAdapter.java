package com.example.homeworkproject;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReprRecyclerAdapter extends RecyclerView.Adapter<ReprRecyclerAdapter.ViewHolder> {
    private final ArrayList<Event> eventList;
    public ReprRecyclerAdapter(ArrayList<Event> eventList)
    {
        this.eventList = eventList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView text;
        private final TextView interest;
        private final ImageView image;

        public ViewHolder(View view) {
            super(view);

            image=(ImageView) view.findViewById(R.id.recyclerImage);
            text = (TextView) view.findViewById(R.id.recyclerTitle);
            interest=(TextView)view.findViewById(R.id.recyclerInterest);
        }

    }
    @NonNull
    @Override
    public ReprRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_rec_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReprRecyclerAdapter.ViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.text.setText(event.getEvent_name());
        String imagePath = event.getImage();
        if (imagePath != null) {
            Uri uri = Uri.parse(imagePath);
            holder.image.setImageURI(uri);
        } else {
            holder.image.setImageResource(R.drawable.placeholder); // fallback
        }
        String interest_text="People interested:"+ String.valueOf(event.getInterest());
        holder.interest.setText(interest_text);

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
