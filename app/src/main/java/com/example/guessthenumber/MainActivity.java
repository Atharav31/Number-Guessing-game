package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startGameButton = findViewById(R.id.buttonStartGame);
        RadioGroup radioGroupLevels = findViewById(R.id.radioGroupLevels);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupLevels.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);

                if (selectedRadioButton != null) {
                    String selectedLevel = selectedRadioButton.getText().toString();
                    // Start MainActivity2 with the selected level
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("selected_level", selectedLevel);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please select a level.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
