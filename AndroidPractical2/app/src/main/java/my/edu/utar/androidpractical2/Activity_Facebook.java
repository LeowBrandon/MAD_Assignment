package my.edu.utar.androidpractical2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Activity_Facebook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_facebook);

        // layout
        LinearLayout myOwnLayout = new LinearLayout(this);
        myOwnLayout.setOrientation((LinearLayout.VERTICAL));

        // text view
        TextView simpleText = new TextView(this);
        simpleText.setText("Please select friends to be added: ");
        simpleText.setTextSize(20.0f);

        // add component: text view to your layout
        myOwnLayout.addView(simpleText);

        // checkboxes

        /* CheckBox cb1 = new CheckBox(this);
        CheckBox cb2 = new CheckBox(this);
        myOwnLayout.addView(cb1);
        myOwnLayout.addView(cb2); */

        final List<CheckBox> cbs = new ArrayList<CheckBox>();

        for(int i = 0; i < 10; i++){
            CheckBox cb = new CheckBox(this);
            cb.setText("Friend " + (i+1));

            // add the individual checkbox to the list
            cbs.add(cb);

            // add each individual checkbox to the layout
            myOwnLayout.addView(cbs.get(i));
        }

        // add a button
        Button btn_addFriend = new Button(this);
        btn_addFriend.setText("Add the selected friends...");
        btn_addFriend.setAllCaps(false); // Prevents text from being in all caps
        myOwnLayout.addView(btn_addFriend);

        // set listener to the button
        btn_addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // form a string buffer to collect the selected friends
                StringBuffer str = new StringBuffer();

                for(int i = 0; i < cbs.size(); i++){
                    if(cbs.get(i).isChecked()){
                        str.append(cbs.get(i).getText().toString() + "; ");
                    }
                }

                // add to a new activity: Google_Meet
                Intent intent = new Intent(Activity_Facebook.this,
                        Activity_GoogleMeet.class);

                // sender
                intent.putExtra("checked_friends", str.toString());

                startActivity(intent);

            }
        });

        setContentView(myOwnLayout);
    }
}