package com.example.a41p;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;



public class EventListFragment extends Fragment {
    RecyclerView recyclerEvents;
    EventAdapter eventAdapter;
    List<Event> eventList;
    Event selectedEvent = null;

    public EventListFragment() {

    }

    private void loadEvents() {
        EventDatabase db = EventDatabase.getInstance(requireContext());
        eventList.clear();
        eventList.addAll(db.eventDao().getAllEvents());
        eventAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerEvents = view.findViewById(R.id.recyclerEvents);
        recyclerEvents.setLayoutManager(new LinearLayoutManager(requireContext()));

        eventList = new ArrayList<>();

        eventAdapter = new EventAdapter(
                eventList,
                event -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", event.id);
                    bundle.putString("title", event.title);
                    bundle.putString("category", event.category);
                    bundle.putString("location", event.location);
                    bundle.putString("dateTime", event.dateTime);

                    Navigation.findNavController(view).navigate(R.id.addEventFragment, bundle);
        },
                event-> {
                    EventDatabase database = EventDatabase.getInstance(requireContext());
                    database.eventDao().delete(event);
                    loadEvents();


                    Toast.makeText(requireContext(), "Event deleted", Toast.LENGTH_SHORT).show();
                }
        );

        recyclerEvents.setAdapter(eventAdapter);
        loadEvents();


        }

    @Override
    public void onResume() {
        super.onResume();
        loadEvents();
    }
}
