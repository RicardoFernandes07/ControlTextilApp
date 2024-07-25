package com.example.controltextil;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyDeviceAdminReceiver extends DeviceAdminReceiver {

    // Método chamado quando o admin do dispositivo é ativado
    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        Toast.makeText(context, "Admin do dispositivo ativado", Toast.LENGTH_SHORT).show();
    }

    // Método chamado quando o admin do dispositivo é desativado
    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Toast.makeText(context, "Admin do dispositivo desativado", Toast.LENGTH_SHORT).show();
    }
}
