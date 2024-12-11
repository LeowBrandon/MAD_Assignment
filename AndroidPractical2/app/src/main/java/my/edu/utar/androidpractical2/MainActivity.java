package my.edu.utar.androidpractical2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // define the data sources
        String[] values = new String[] {
                "Facebook", "Instagram", "Twitter", "YouTube",
                "Tumblr", "Tiktok", "WhatsApp", "WeChat", "Telegram",
                "Twitch", "Github", "SnapChat", "Viber", "Android Rion",
                "Google Meet", "Zoom", "Messenger", "Yahoo", "Google Chat",
                "Discord", "Microsoft Teams", "微博", "抖音",
        };

        // An adapter acts as a bridge between an adapter view
        // and the underlying data for that view
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,
                values
        );

        setListAdapter(adapter);  // similar to setContentView



    }
}