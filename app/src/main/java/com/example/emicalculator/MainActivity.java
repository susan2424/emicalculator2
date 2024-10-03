package com.example.emicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mortgageInput, tenureInput, interestInput;
    private TextView resultText;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Path for the xml to java code
        mortgageInput = findViewById(R.id.mortgageInput);
        tenureInput = findViewById(R.id.tenureInput);
        interestInput = findViewById(R.id.interestInput);
        resultText = findViewById(R.id.resultText);
        calculateButton = findViewById(R.id.calculateButton);

        // Creating the button action by the listener
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // calculates the total EMI
                String result = calculateEMI();

                if (result.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter all values", Toast.LENGTH_SHORT).show();

                }

                else{
                    //Intent to display the result in a new activity page
                    Intent displayEMI = new Intent(MainActivity.this, DisplayEMI.class);

                    //Sends the message of the calculated EMI to the next activity in the intent using putExtra()
                    displayEMI.putExtra("sendingResult", result);
                    startActivity(displayEMI);
                }

            }
        });
    }

    // /Function to calculate EMI based on the numerical inputs and return it as a string message
    private String calculateEMI() {

        String mortgageStr = mortgageInput.getText().toString();
        String tenureStr = tenureInput.getText().toString();
        String interestStr = interestInput.getText().toString();

        if (mortgageStr.isEmpty() || tenureStr.isEmpty() || interestStr.isEmpty()) {
            return "";
        }

        else{

            double principal = Double.parseDouble(mortgageStr);
            int tenure = Integer.parseInt(tenureStr);
            double annualInterestRate = Double.parseDouble(interestStr);


            double monthlyInterestRate = (annualInterestRate / 12) / 100;

            int numberOfPayments = tenure * 12;

            // The formula for EMI calculator
            double emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments))
                    / (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1);

            // Output answer to user
            String result = "Your Monthly EMI is: $" + String.format("%.2f", emi);
            return result;
        }

    }
}