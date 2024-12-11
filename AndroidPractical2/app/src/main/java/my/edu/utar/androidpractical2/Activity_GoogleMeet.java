package my.edu.utar.androidpractical2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_GoogleMeet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_google_meet);

        LinearLayout displayLayout = new LinearLayout(this);
        displayLayout.setOrientation(LinearLayout.VERTICAL);

        // textView
        TextView tvLabel = new TextView(this);
        TextView tvResult = new TextView(this);
        tvLabel.setTextSize(20.0f);
        tvResult.setTextSize(20.0f);
        tvLabel.setText("Your selected friends are: ");

        // receiver
        tvResult.setText(
                this.getIntent().getStringExtra("checked_friends"));

        // add the components to the layout
        displayLayout.addView(tvLabel);
        displayLayout.addView(tvResult);

        // Dialog Box
        // button to trigger the Dialog Box
        Button button_alert = new Button(this);
        button_alert.setText("Press here for surprise!");

        // set up the dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert Dialog Box");
        builder.setMessage("Are you having FUN?");
        builder.setCancelable(false);

        // listener dialog box -> for the 2 buttons
        // positive button
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // pop out a short message box == toast box
                Toast.makeText(getApplicationContext(), "YES",
                        Toast.LENGTH_LONG).show();
            }
        });

        // negative button
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog alert = builder.create();

        // listener for the button to trigger the dialog box
        button_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();
            }
        });

        displayLayout.addView(button_alert);

        setContentView(displayLayout);
    }
}