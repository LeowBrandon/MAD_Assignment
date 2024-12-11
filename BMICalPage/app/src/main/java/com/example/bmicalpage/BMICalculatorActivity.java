package com.example.bmicalpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BMICalculatorActivity extends AppCompatActivity {

    private EditText weightInput;
    private EditText heightInput;
    private TextView bmiResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        weightInput = findViewById(R.id.weight_input);
        heightInput = findViewById(R.id.height_input);
        bmiResultText = findViewById(R.id.bmi_result_text);

        Button calculateButton = findViewById(R.id.calculate_button);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }

    private void calculateBMI() {
        String weightStr = weightInput.getText().toString();
        String heightStr = heightInput.getText().toString();

        if (!weightStr.isEmpty() && !heightStr.isEmpty()) {
            float weight = Float.parseFloat(weightStr);
            float height = Float.parseFloat(heightStr) / 100; // Convert cm to meters

            float bmi = weight / (height * height);
            String category = getBMICategory(bmi);

            bmiResultText.setText(String.format("Your BMI is: %.2f\nCategory: %s", bmi, category));
        } else {
            bmiResultText.setText("Please enter valid values.");
        }
    }

    private String getBMICategory(float bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            return "Normal weight";
        } else if (bmi >= 25 && bmi < 29.9) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }
}