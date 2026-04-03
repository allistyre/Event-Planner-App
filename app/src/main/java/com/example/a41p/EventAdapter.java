package com.example.a41p;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList;

    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    private OnItemClickListener listener;

    public interface OnItemLongClickListener {
        void onItemLongClick(Event event);
    }

    private OnItemLongClickListener longClickListener;

    public EventAdapter(List<Event> eventList, OnItemClickListener listener, OnItemLongClickListener longClickListener) {
        this.eventList = eventList;
        this.listener = listener;
        this.longClickListener = longClickListener;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textCategory, textLocation, textDateTime;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textCategory = itemView.findViewById(R.id.textCategory);
            textLocation = itemView.findViewById(R.id.textLocation);
            textDateTime = itemView.findViewById(R.id.textDateTime);
        }
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.textTitle.setText(event.title);
        holder.textCategory.setText("Category: " + event.category);
        holder.textLocation.setText("Location: " + event.location);
        holder.textDateTime.setText("Date/Time: " + event.dateTime);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(event));
        holder.itemView.setOnLongClickListener(v -> {
            longClickListener.onItemLongClick(event);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
