package com.example.controltextil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import com.example.controltextil.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Verificar o estado do modo quiosque
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        boolean isKioskMode = sharedPreferences.getBoolean("kioskMode", false);

        // Verifica se o aplicativo é o launcher padrão
        if (isDefaultLauncher()) {
            // Se for o launcher padrão, iniciar MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("KIOSK_MODE", isKioskMode);
            startActivity(intent);
        }

        // Finaliza a IntroActivity
        finish();
    }

    private boolean isDefaultLauncher() {
        PackageManager pm = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        List<ResolveInfo> resolveInfoList = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        if (resolveInfoList != null && !resolveInfoList.isEmpty()) {
            for (ResolveInfo resolveInfo : resolveInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                if (packageName.equals(getPackageName())) {
                    return true;
                }
            }
        }

        return false;
    }
}
