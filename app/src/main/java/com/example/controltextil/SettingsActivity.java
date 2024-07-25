package com.example.controltextil;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    private TextInputEditText textInputEditText;
    private ImageButton backButton, sendButton, pinSaveButton;
    private LinearLayout submenu;
    private Switch switch1, switch2;
    private Button exitButton;
    private EditText pinInputField;

    private TextView tvMondayEntry, tvMondayExit, tvTuesdayEntry, tvTuesdayExit;
    private TextView tvWednesdayEntry, tvWednesdayExit, tvThursdayEntry, tvThursdayExit;
    private TextView tvFridayEntry, tvFridayExit;

    private ImageButton btnEditMondayEntry, btnEditMondayExit, btnEditTuesdayEntry, btnEditTuesdayExit;
    private ImageButton btnEditWednesdayEntry, btnEditWednesdayExit, btnEditThursdayEntry, btnEditThursdayExit;
    private ImageButton btnEditFridayEntry, btnEditFridayExit;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Inicialização dos componentes
        textInputEditText = findViewById(R.id.textInputEditText);
        backButton = findViewById(R.id.BackButton);
        sendButton = findViewById(R.id.sendButton);
        pinSaveButton = findViewById(R.id.Pinsave);
        submenu = findViewById(R.id.submenu);
        switch1 = findViewById(R.id.switch1); // Modo Kiosk
        switch2 = findViewById(R.id.switch2); // Horário
        exitButton = findViewById(R.id.exitButton); // Botão para sair
        pinInputField = findViewById(R.id.Pin); // Campo para entrada de PIN

        // Horários de entrada e saída para cada dia da semana
        tvMondayEntry = findViewById(R.id.tvMondayTime);
        tvMondayExit = findViewById(R.id.tvMondayTime);
        tvTuesdayEntry = findViewById(R.id.tvTuesdayTime);
        tvTuesdayExit = findViewById(R.id.tvTuesdayTime);
        tvWednesdayEntry = findViewById(R.id.tvWednesdayTime);
        tvWednesdayExit = findViewById(R.id.tvWednesdayTime);
        tvThursdayEntry = findViewById(R.id.tvThursdayTime);
        tvThursdayExit = findViewById(R.id.tvThursdayTime);
        tvFridayEntry = findViewById(R.id.tvFridayTime);
        tvFridayExit = findViewById(R.id.tvFridayTime);

        // Botões de edição de horário
        btnEditMondayEntry = findViewById(R.id.btnEditMonday);
        btnEditMondayExit = findViewById(R.id.btnEditMonday);
        btnEditTuesdayEntry = findViewById(R.id.btnEditTuesday);
        btnEditTuesdayExit = findViewById(R.id.btnEditTuesday);
        btnEditWednesdayEntry = findViewById(R.id.btnEditWednesday);
        btnEditWednesdayExit = findViewById(R.id.btnEditWednesday);
        btnEditThursdayEntry = findViewById(R.id.btnEditThursday);
        btnEditThursdayExit = findViewById(R.id.btnEditThursday);
        btnEditFridayEntry = findViewById(R.id.btnEditFriday);
        btnEditFridayExit = findViewById(R.id.btnEditFriday);

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);

        // Carregar dados salvos do SharedPreferences
        loadSavedPreferences();

        // Configurar botões e listeners
        setupListeners();
    }

    private void loadSavedPreferences() {
        String savedUrl = sharedPreferences.getString("savedUrl", "");
        if (!savedUrl.isEmpty()) {
            textInputEditText.setText(savedUrl);
        }

        boolean isKioskMode = sharedPreferences.getBoolean("kioskMode", false);
        switch1.setChecked(isKioskMode);

        // Carregar horários de entrada e saída salvos
        tvMondayEntry.setText(sharedPreferences.getString("mondayEntry", "08:00"));
        tvMondayExit.setText(sharedPreferences.getString("mondayExit", "17:00"));
        tvTuesdayEntry.setText(sharedPreferences.getString("tuesdayEntry", "08:00"));
        tvTuesdayExit.setText(sharedPreferences.getString("tuesdayExit", "17:00"));
        tvWednesdayEntry.setText(sharedPreferences.getString("wednesdayEntry", "08:00"));
        tvWednesdayExit.setText(sharedPreferences.getString("wednesdayExit", "17:00"));
        tvThursdayEntry.setText(sharedPreferences.getString("thursdayEntry", "08:00"));
        tvThursdayExit.setText(sharedPreferences.getString("thursdayExit", "17:00"));
        tvFridayEntry.setText(sharedPreferences.getString("fridayEntry", "08:00"));
        tvFridayExit.setText(sharedPreferences.getString("fridayExit", "17:00"));
    }

    private void setupListeners() {
        backButton.setOnClickListener(view -> finish());

        sendButton.setOnClickListener(view -> {
            String url = textInputEditText.getText().toString().trim();
            if (!url.isEmpty()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("savedUrl", url);
                editor.apply();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("URL", url);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Por favor, insira uma URL", Toast.LENGTH_SHORT).show();
            }
        });

        pinSaveButton.setOnClickListener(view -> {
            String pin = pinInputField.getText().toString().trim();
            if (!pin.isEmpty()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("pin", pin);
                editor.apply();
                Toast.makeText(this, "PIN salvo com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Por favor, insira um PIN", Toast.LENGTH_SHORT).show();
            }
        });

        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("kioskMode", isChecked);
            editor.apply();
            if (isChecked) {
                showLauncherPromptDialog();
            } else {
                showLauncherPromptDialog1();
            }
        });

        switch2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            submenu.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        submenu.setVisibility(switch2.isChecked() ? View.VISIBLE : View.GONE);

        exitButton.setOnClickListener(view -> showPinPromptDialog());

        // Configurar botões de edição de horário
        btnEditMondayEntry.setOnClickListener(view -> showTimePickerDialog(tvMondayEntry, "mondayEntry"));
        btnEditMondayExit.setOnClickListener(view -> showTimePickerDialog(tvMondayExit, "mondayExit"));
        btnEditTuesdayEntry.setOnClickListener(view -> showTimePickerDialog(tvTuesdayEntry, "tuesdayEntry"));
        btnEditTuesdayExit.setOnClickListener(view -> showTimePickerDialog(tvTuesdayExit, "tuesdayExit"));
        btnEditWednesdayEntry.setOnClickListener(view -> showTimePickerDialog(tvWednesdayEntry, "wednesdayEntry"));
        btnEditWednesdayExit.setOnClickListener(view -> showTimePickerDialog(tvWednesdayExit, "wednesdayExit"));
        btnEditThursdayEntry.setOnClickListener(view -> showTimePickerDialog(tvThursdayEntry, "thursdayEntry"));
        btnEditThursdayExit.setOnClickListener(view -> showTimePickerDialog(tvThursdayExit, "thursdayExit"));
        btnEditFridayEntry.setOnClickListener(view -> showTimePickerDialog(tvFridayEntry, "fridayEntry"));
        btnEditFridayExit.setOnClickListener(view -> showTimePickerDialog(tvFridayExit, "fridayExit"));
    }

    private void showTimePickerDialog(TextView timeTextView, String preferenceKey) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        new TimePickerDialog(this, (view, hourOfDay, minuteOfHour) -> {
            String time = String.format("%02d:%02d", hourOfDay, minuteOfHour);
            timeTextView.setText(time);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(preferenceKey, time);
            editor.apply();
        }, hour, minute, true).show();
    }

    private void showLauncherPromptDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Definir como Launcher")
                .setMessage("Deseja definir este aplicativo como o launcher padrão?")
                .setPositiveButton("Sim", (dialog, which) -> openLauncherSettings())
                .setNegativeButton("Não", null)
                .show();
    }

    private void showLauncherPromptDialog1() {
        new AlertDialog.Builder(this)
                .setTitle("Redefinir como Launcher")
                .setMessage("Deseja redefinir para o launcher do smartphone?")
                .setPositiveButton("Sim", (dialog, which) -> openLauncherSettings())
                .setNegativeButton("Não", null)
                .show();
    }

    private void openLauncherSettings() {
        Intent intent = new Intent(Settings.ACTION_HOME_SETTINGS);
        startActivity(intent);
    }

    private void resetDefaultLauncher() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finishAffinity(); // Fecha todas as atividades e encerra o aplicativo
    }

    private void showPinPromptDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirme seu PIN");

        final EditText pinInput = new EditText(this);
        pinInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        builder.setView(pinInput);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String enteredPin = pinInput.getText().toString();
            String savedPin = sharedPreferences.getString("pin", "");

            if (enteredPin.equals(savedPin)) {
                resetDefaultLauncher();
            } else {
                Toast.makeText(SettingsActivity.this, "PIN incorreto", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}
