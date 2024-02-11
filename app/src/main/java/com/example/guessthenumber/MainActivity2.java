package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    private int randomNumber;
    private int remainingAttempts;
    private int minNumber;
    private int maxNumber;

    private EditText editTextGuess;
    private TextView textViewAttempts;
    private TextView textViewLevel;
    private Button buttonEnter;
    private Button buttonTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize views
        editTextGuess = findViewById(R.id.editTextGuess);
        textViewAttempts = findViewById(R.id.textViewAttempts);
        textViewLevel = findViewById(R.id.textViewLevel);
        buttonEnter = findViewById(R.id.buttonEnter);
        buttonTryAgain = findViewById(R.id.buttonTryAgain);

        // Retrieve the selected level from the intent
        Intent intent = getIntent();
        String selectedLevel = intent.getStringExtra("selected_level");
        textViewLevel.setText("Level: " + selectedLevel);

        // Set the minimum and maximum numbers based on the selected level
        switch (selectedLevel) {
            case "Easy":
                minNumber = 1;
                maxNumber = 10;
                remainingAttempts = 5;
                break;
            case "Medium":
                minNumber = 1;
                maxNumber = 100;
                remainingAttempts = 10;
                break;
            case "Hard":
                minNumber = 100;
                maxNumber = 999;
                remainingAttempts = 10;
                break;
            default:
                minNumber = 1;
                maxNumber = 10;
                remainingAttempts = 5;
                break;
        }

        // Generate a random number within the specified range
        Random random = new Random();
        randomNumber = random.nextInt(maxNumber - minNumber + 1) + minNumber;

        // Set initial attempts text
        updateAttemptsText();

        // Register number buttons
        int[] numberButtonIds = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};
        for (int id : numberButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle number button click
                    Button clickedButton = (Button) v;
                    String number = clickedButton.getText().toString();
                    String currentGuess = editTextGuess.getText().toString();
                    editTextGuess.setText(currentGuess + number);
                }
            });
        }

        // Set click listener for the "Clr" button
        Button buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the text in the EditText
                editTextGuess.setText("");
            }
        });

        // Set click listener for the "Enter" button
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleGuess();
            }
        });

        // Set click listener for the "Try Again" button
        buttonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private void handleGuess() {
        // Get user's guess
        String guessString = editTextGuess.getText().toString();

        // Check if the guess is empty
        if (guessString.isEmpty()) {
            Toast.makeText(MainActivity2.this, "Please enter a number.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert the guess to an integer
        int guess = Integer.parseInt(guessString);

        // Check if the guess is correct
        if (guess == randomNumber) {
            // Display a success message
            Toast.makeText(MainActivity2.this, "Congratulations! You guessed the number.", Toast.LENGTH_SHORT).show();
            // Redirect back to MainActivity
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close MainActivity2
        } else {
            // Decrement remaining attempts
            remainingAttempts--;

            // Update attempts text
            updateAttemptsText();

            // Check if attempts are exhausted
            if (remainingAttempts == 0) {
                // Display a failure message
                Toast.makeText(MainActivity2.this, "Sorry, you have exhausted all attempts. The correct number was " + randomNumber + ".", Toast.LENGTH_LONG).show();
                disableInput();
            } else {
                // Provide feedback to the user
                String message = guess < randomNumber ? "Too low. Try again." : "Too high. Try again.";
                Toast.makeText(MainActivity2.this, message, Toast.LENGTH_SHORT).show();
            }
        }

        // Clear the guess EditText
        editTextGuess.setText("");
    }

    private void resetGame() {
        // Generate a new random number within the specified range
        Random random = new Random();
        randomNumber = random.nextInt(maxNumber - minNumber + 1) + minNumber;

        // Reset attempts
        remainingAttempts = (textViewLevel.getText().toString().equals("Easy")) ? 5 : 5;

        // Update attempts text
        updateAttemptsText();

        // Enable input and hide "Try Again" button
        editTextGuess.setEnabled(true);
        buttonTryAgain.setVisibility(View.GONE);
    }

    private void disableInput() {
        // Disable input and show "Try Again" button
        editTextGuess.setEnabled(false);
        buttonTryAgain.setVisibility(View.VISIBLE);
    }

    private void updateAttemptsText() {
        // Update attempts text
        textViewAttempts.setText("Remaining attempts: " + remainingAttempts);
    }
}
