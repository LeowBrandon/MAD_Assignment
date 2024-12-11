package com.example.grpasg;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<HistoryEntry> historyEntries = new ArrayList<>();
    private boolean isRunningMode = true;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://elt-tracking-app-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference databaseReference = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);



        // Initialize Views
        ImageView navRunning = findViewById(R.id.nav_running);
        ImageView navCommunity = findViewById(R.id.nav_community);
        ImageView navLeaderboard = findViewById(R.id.nav_leaderboard);
        ImageView navReminder = findViewById(R.id.nav_reminder);
        ImageView userProfile = findViewById(R.id.UserProfile);
        ImageView optionsMenu = findViewById(R.id.OptionsMenu);
        ImageView  navWeather =findViewById(R.id.Weather);

        // Set up navigation
        Navigation.setupNavigation(this, navRunning, navCommunity, navLeaderboard, navReminder, userProfile,navWeather);

        // Set up options menu
        Navigation.setupOptionsMenu(this, optionsMenu);

        recyclerView = findViewById(R.id.history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter(historyEntries);
        recyclerView.setAdapter(adapter);
        // Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("history");

        // Set up cycling and running toggle buttons
        findViewById(R.id.run).setOnClickListener(v -> loadHistoryData("running"));
        findViewById(R.id.cycle).setOnClickListener(v -> loadHistoryData("cycling"));

        // Load default data
        loadHistoryData("running");
    }



    private void loadHistoryData(String activityType) {
        // Access the correct nested path
        databaseReference.child(activityType).child("history").child(activityType)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        historyEntries.clear(); // Clear previous data
                        for (DataSnapshot entrySnapshot : snapshot.getChildren()) {
                            try {
                                HistoryEntry entry = entrySnapshot.getValue(HistoryEntry.class);
                                if (entry != null && entry.getActivityType() != null) {
                                    historyEntries.add(entry);
                                } else {
                                    Log.e("HistoryActivity", "Invalid entry: " + entrySnapshot.toString());
                                }
                            } catch (DatabaseException e) {
                                Log.e("HistoryActivity", "Error parsing entry: ", e);
                            }
                        }
                        adapter.notifyDataSetChanged(); // Update RecyclerView
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(HistoryActivity.this, "Failed to load " + activityType + " history", Toast.LENGTH_SHORT).show();
                    }
                });
    }




}
