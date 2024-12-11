package com.example.reminderpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

public class ReminderActivity extends AppCompatActivity {

    private TimePicker timePickerTop;
    private TimePicker timePickerBottom;
    private TextView descriptionText;
    private RecyclerView reminderRecyclerView;

    private List<String> reminders = new ArrayList<>();
    private ReminderAdapter adapter; // Custom adapter for RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_page);

        timePickerTop = findViewById(R.id.time_picker_top);
        timePickerBottom = findViewById(R.id.time_picker_bottom);
        descriptionText = findViewById(R.id.description_text);
        reminderRecyclerView = findViewById(R.id.reminder_recycler_view);

        // Set up RecyclerView
        adapter = new ReminderAdapter(reminders);
        reminderRecyclerView.setAdapter(adapter);
        reminderRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up icon click listeners
        ImageView iconCheckTyre = findViewById(R.id.icon_check_tyre);
        ImageView iconCheckBrake = findViewById(R.id.icon_check_brake);
        ImageView iconChangeAccessories = findViewById(R.id.icon_change_accessories);

        iconCheckTyre.setOnClickListener(v -> updateDescription("CHECK TYRE"));
        iconCheckBrake.setOnClickListener(v -> updateDescription("CHECK BRAKE"));
        iconChangeAccessories.setOnClickListener(v -> updateDescription("CHANGE ACCESSORIES"));

        // Set up button click listeners
        Button setReminderButton = findViewById(R.id.set_reminder_button);
        Button deleteButton = findViewById(R.id.delete_button);

        setReminderButton.setOnClickListener(v -> setReminder());

        deleteButton.setOnClickListener(v -> deleteReminder());
    }

    private void updateDescription(String text) {
        descriptionText.setText(text);
    }

    private void setReminder() {
        /*int hour = timePickerTop.getCurrentHour();
        int minute = timePickerTop.getCurrentMinute();*/
        int hour, minute;

        // Check if the bottom TimePicker is visible and use it if so, otherwise use the top one
        if (timePickerBottom.getVisibility() == View.VISIBLE) {
            hour = timePickerBottom.getCurrentHour();
            minute = timePickerBottom.getCurrentMinute();
        } else {
            hour = timePickerTop.getCurrentHour();
            minute = timePickerTop.getCurrentMinute();
        }

        String reminderTime = String.format(Locale.getDefault(), "Reminder set for %02d:%02d - %s", hour, minute, descriptionText.getText().toString());

        // Add reminder to the list and notify user
        reminders.add(reminderTime);

        adapter.notifyDataSetChanged(); // Update RecyclerView

        // Show a pop-up notification message (AlertDialog)
        showAlertDialog(reminderTime);

        // Optionally clear the description after setting a reminder
        descriptionText.setText("");

        // Show both TimePickers below buttons after setting a reminder (if needed)
        timePickerTop.setVisibility(View.INVISIBLE);
        timePickerBottom.setVisibility(View.VISIBLE);
    }

    private void deleteReminder() {
        if (!reminders.isEmpty()) {
            reminders.remove(reminders.size() - 1); // Remove last reminder as an example
            adapter.notifyDataSetChanged(); // Update RecyclerView
            descriptionText.setText("Last reminder deleted.");
        } else {
            descriptionText.setText("No reminders to delete.");
            timePickerBottom.setVisibility(View.INVISIBLE); // Hide TimePicker when deleting reminders
            timePickerTop.setVisibility(View.VISIBLE);
        }
    }

    private void showAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Reminder Set")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
