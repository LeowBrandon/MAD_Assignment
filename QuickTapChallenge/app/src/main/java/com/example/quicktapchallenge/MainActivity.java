package com.example.quicktapchallenge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView timerTextView;

    private TextView levelTextView; // New TextView for displaying level information

    private GridLayout gridLayout;
    private Button endGameButton;
    private int level = 1;
    private int totalTouches = 0;
    private final List<View> views = new ArrayList<>();
    private final Random random = new Random();
    private final Handler handler = new Handler();

    private Button highlightedButton;

    // Array of messages for each level
    private final String[] levelMessages = {
            "Welcome to Level 1! Tap the highlighted view!",
            "Welcome to Level 2! Keep it up!",
            "Welcome to Level 3! You're doing great!",
            "Welcome to Level 4! Final challenge!"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        levelTextView = findViewById(R.id.levelTextView); // Initialize the new TextView
        gridLayout = findViewById(R.id.gridLayout);
        endGameButton = findViewById(R.id.endGameButton);

        initializeDefaultScores(); // Initialize default scores
        setupLevel(level);

        endGameButton.setOnClickListener(v -> endGame());

    }

    private void setupLevel(int level) {
        gridLayout.removeAllViews();
        views.clear();

        // Update the level text
        if (level <= levelMessages.length) {
            levelTextView.setText(levelMessages[level - 1]); // Set text based on current level
        }

        // Create view items based on level
        for (int i = 0; i < (level + 1) * (level + 1); i++) { // Level starts at 4 views
            Button button = new Button(this);
            button.setText("View " + (i + 1));
            button.setOnClickListener(v -> handleTouch(button));
            gridLayout.addView(button);
            views.add(button);
        }

        startTimer();
    }

    private void startTimer() {
        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time left: " + millisUntilFinished / 1000);
                highlightRandomView();
            }

            public void onFinish() {
                level++;
                if (level <= 4) { // Limit to 4 levels
                    setupLevel(level);
                } else {
                    endGame(); // End game after last level
                }
            }
        }.start();
    }

    private void highlightRandomView() {
        int index = random.nextInt(views.size());

        for (View view : views) {
            view.setBackgroundColor(Color.LTGRAY); // Reset color
        }

        highlightedButton = (Button) views.get(index); // Keep track of the highlighted button

        highlightedButton.setBackgroundColor(Color.YELLOW); // Highlight random view
    }

    private void handleTouch(Button button) {
        if (button == highlightedButton) { // Check if the touched button is the highlighted one
            totalTouches++;
            button.setBackgroundColor(Color.LTGRAY); // Reset color after touch
            highlightRandomView(); // Highlight a new view immediately
        }
    }

    private void endGame() {
        // Logic to save score and show score table
        if (isTopScore(totalTouches)) {
            showNameInputDialog(); // Prompt user for their name
        } else {
            showScoreTable(); // Show score table without saving score
        }
    }

    private void initializeDefaultScores() {
        SharedPreferences sharedPreferences = getSharedPreferences("Scores", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Example default scores
        if (sharedPreferences.getAll().isEmpty()){
            for (int i = 1; i <= 25; i++) {
                editor.putInt("Player" + i, (int) (Math.random() * 25)); // Random scores
            }
            editor.apply();
        }

    }

    private void saveScore(String name, int score) {
        SharedPreferences sharedPreferences = getSharedPreferences("Scores", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(name, score); // Save score with player's name as key
        editor.apply();
    }

    private boolean isTopScore(int score) {
        // Logic to check if the score is among the top 25 scores
        // You may need to retrieve existing scores from SharedPreferences and compare
        SharedPreferences sharedPreferences = getSharedPreferences("Scores", MODE_PRIVATE);

        Map<String, Integer> scoresMap = new HashMap<>();

        for (String name : sharedPreferences.getAll().keySet()) {
            scoresMap.put(name, sharedPreferences.getInt(name, 0));
        }

        List<Integer> scoresList = new ArrayList<>(scoresMap.values());
        Collections.sort(scoresList, Collections.reverseOrder());

        return scoresList.size() < 25 || score > scoresList.get(24); // Check if in top 25
    }

    private void showNameInputDialog() {
        // Code to display a dialog for user to enter their name
        // After entering, call saveScore(name, totalTouches);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your Name");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String name = input.getText().toString();
            saveScore(name, totalTouches); // Save the score with the entered name
            showScoreTable();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showScoreTable() {
        SharedPreferences sharedPreferences = getSharedPreferences("Scores", MODE_PRIVATE);

        StringBuilder scoreTableBuilder = new StringBuilder();

        for (String name : sharedPreferences.getAll().keySet()) {
            int score = sharedPreferences.getInt(name, 0);
            scoreTableBuilder.append(name).append(": ").append(score).append("\n");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Score Table");

        builder.setMessage(scoreTableBuilder.toString());

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        builder.show();
    }
}