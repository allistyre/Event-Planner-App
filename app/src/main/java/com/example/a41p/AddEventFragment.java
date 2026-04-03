package com.example.a41p;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEventFragment extends Fragment {
    EditText editTitle, editLocation, editDateTime;
    Spinner spinnerCategory;
    Button saveButton;
    Event selectedEvent = null;

    final String[] categories = {"Work", "Social", "Travel"};

    public AddEventFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTitle = view.findViewById(R.id.editTitle);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        editLocation = view.findViewById(R.id.editLocation);
        editDateTime = view.findViewById(R.id.editDateTime);
        saveButton = view.findViewById(R.id.saveButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        Bundle args = getArguments();
        if (args != null) {
            selectedEvent = new Event();
            selectedEvent.id = args.getInt("id");
            selectedEvent.title = args.getString("title");
            selectedEvent.category = args.getString("category");
            selectedEvent.location = args.getString("location");
            selectedEvent.dateTime = args.getString("dateTime");

            editTitle.setText(selectedEvent.title);
            editLocation.setText(selectedEvent.location);
            editDateTime.setText(selectedEvent.dateTime);

            int categoryPosition = 0;
            for (int i = 0; i < categories.length; i++) {
                if (categories[i].equals(selectedEvent.category)) {
                    categoryPosition = i;
                    break;
                }
            }
            spinnerCategory.setSelection(categoryPosition);

            saveButton.setText("Update Event");
        }

        saveButton.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem().toString();
            String location = editLocation.getText().toString().trim();
            String dateTime = editDateTime.getText().toString().trim();

            // Validation
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(dateTime)) {
                Toast.makeText(requireContext(), "Title and Date/Time are required", Toast.LENGTH_SHORT).show();
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            sdf.setLenient(false);

            try {
                Date enteredDate = sdf.parse(dateTime);
                Date now = new Date();

                if (enteredDate == null || enteredDate.before(now)) {
                    Toast.makeText(requireContext(), "Please enter a future date/time in format yyyy-MM-dd HH:mm", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (ParseException e) {
                Toast.makeText(requireContext(), "Invalid date format. Use yyyy-MM-dd HH:mm", Toast.LENGTH_SHORT).show();
                return;
            }

            EventDatabase db = EventDatabase.getInstance(requireContext());

            if (selectedEvent == null) {
                Event event = new Event();
                event.title = title;
                event.category = category;
                event.location = location;
                event.dateTime = dateTime;
                db.eventDao().insert(event);

                Toast.makeText(requireContext(), "Event saved", Toast.LENGTH_SHORT).show();
            } else {
                selectedEvent.title = title;
                selectedEvent.category = category;
                selectedEvent.location = location;
                selectedEvent.dateTime = dateTime;
                db.eventDao().update(selectedEvent);

                Toast.makeText(requireContext(), "Event updated", Toast.LENGTH_SHORT).show();
                selectedEvent = null;
                saveButton.setText("Save Event");
            }

            editTitle.setText("");
            editLocation.setText("");
            editDateTime.setText("");
            spinnerCategory.setSelection(0);
        });
    }
}