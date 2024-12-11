package my.edu.utar.androidpractical2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class List_Activity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list); // required to display the xml file

        // to extract the component from the xml file
        listView = findViewById(R.id.lv_socialMedia);

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

        listView.setAdapter(adapter);

        // listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem =
                        parent.getItemAtPosition(position).toString();

                if(selectedItem.contentEquals("Facebook")){
                    // call to a new activity
                    Intent intent = new Intent(view.getContext(),
                            Activity_Facebook.class);

                    startActivity(intent);
                }

                if(position==14){
                    Intent intent = new Intent(view.getContext(),
                            Activity_GoogleMeet.class);

                    startActivity(intent);
                }
            }
        });

    }
}