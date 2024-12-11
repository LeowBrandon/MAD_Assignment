package com.example.grpasg;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

public class NavigationCycling {

    public static void setupNavigation(Context context, ImageView navRunning, ImageView navCommunity,
                                       ImageView navLeaderboard, ImageView navReminder, ImageView userProfile, ImageView navWeather) {

        navRunning.setOnClickListener(v -> redirectToPage(context, CyclingActivity.class));
        navCommunity.setOnClickListener(v -> redirectToPage(context, CommunityActivity.class));
        navLeaderboard.setOnClickListener(v -> redirectToPage(context, LeaderboardActivity.class));
        navReminder.setOnClickListener(v -> redirectToPage(context, ReminderActivity.class));
        userProfile.setOnClickListener(v -> redirectToPage(context, MainActivity.class));
        navWeather.setOnClickListener(v -> redirectToPage(context,WeatherActivity.class));
    }

    public static void setupOptionsMenu(AppCompatActivity activity, ImageView optionsMenu) {
        optionsMenu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(activity, view);
            popupMenu.getMenuInflater().inflate(R.menu.options_menu, popupMenu.getMenu());

            // Define variables for menu item IDs
            int menuHistoryId = R.id.menu_history;
            // Handle menu item clicks using if-else
            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId(); // Get the clicked item's ID

                if (itemId == menuHistoryId) {
                    // Navigate to HistoryActivity
                    redirectToPage(activity, HistoryActivity.class);
                    return true;

                } else {
                    return false; // No matching menu item
                }
            });

            // Show the PopupMenu
            popupMenu.show();
        });
    }

    private static void redirectToPage(Context context, Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }
}
