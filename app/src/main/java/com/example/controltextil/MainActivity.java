package com.example.controltextil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controltextil.R;

public class MainActivity extends AppCompatActivity {

    private static final int SETTINGS_REQUEST_CODE = 2;
    private static final int PIN_REQUEST_CODE = 1;
    private WebView webView;
    private String currentUrl;
    private boolean isFirstSettingsAccess = true;
    private boolean isKioskModeEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar o modo Kiosk
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        isKioskModeEnabled = sharedPreferences.getBoolean("kioskMode", false);

        if (isKioskModeEnabled) {
            // Modo quiosque: ocultar a barra de status e a barra de navegação
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                            View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        // Inicializar a WebView
        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Ativar JavaScript
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        // Recuperar a URL salva
        currentUrl = sharedPreferences.getString("savedUrl", "https://controltextil.com");
        webView.loadUrl(currentUrl);

        // Configurar botão de recarregar
        ImageButton refreshButton = findViewById(R.id.imageButton2);
        refreshButton.setOnClickListener(v -> {
            webView.reload();
            Toast.makeText(MainActivity.this, "Página recarregada", Toast.LENGTH_SHORT).show();
        });

        // Configurar botão de configurações
        ImageButton settingsButton = findViewById(R.id.SettingsButton);
        settingsButton.setOnClickListener(v -> openSettingsActivity());
    }

    private void openSettingsActivity() {
        Intent intent = new Intent(this, PinActivity.class);
        startActivityForResult(intent, PIN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, SETTINGS_REQUEST_CODE);
            } else {
                Toast.makeText(this, "PIN incorreto. Tente novamente.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SETTINGS_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String newUrl = data.getStringExtra("URL");
                if (newUrl != null && !newUrl.isEmpty()) {
                    currentUrl = newUrl;

                    // Salvar a URL no SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("savedUrl", currentUrl);
                    editor.apply();

                    // Carregar a URL atualizada na WebView
                    webView.loadUrl(currentUrl);
                    Toast.makeText(MainActivity.this, "URL atualizada para: " + currentUrl, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!isKioskModeEnabled) {
            super.onBackPressed(); // Permitir sair do aplicativo se o modo Kiosk estiver desativado
        }
    }
}
