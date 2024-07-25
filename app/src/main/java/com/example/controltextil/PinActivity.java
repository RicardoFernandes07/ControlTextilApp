package com.example.controltextil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controltextil.R;

public class PinActivity extends AppCompatActivity {
    private ImageButton BackButton;
    private EditText pinEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        BackButton = findViewById(R.id.BackButton);
        pinEditText = findViewById(R.id.pinEditText);
        Button submitButton = findViewById(R.id.submitButton);

        BackButton.setOnClickListener(v -> {
            finish(); // Fecha a atividade atual e retorna à anterior
        });

        submitButton.setOnClickListener(v -> {
            String enteredPin = pinEditText.getText().toString();
            SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
            String savedPin = sharedPreferences.getString("pin", "");

            if (enteredPin.equals(savedPin)) {
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(PinActivity.this, "PIN incorreto. Tente novamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
