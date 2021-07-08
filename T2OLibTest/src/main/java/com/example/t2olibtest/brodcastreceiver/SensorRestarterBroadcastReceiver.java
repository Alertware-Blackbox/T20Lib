package com.example.t2olibtest.brodcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.t2olibtest.ble.BluetoothLeService;


public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {

    //List<Body> userInfo;
    @Override
    public void onReceive(Context context, Intent intent) {
        startDataInsert(context,intent);
    }

    private void startDataInsert(final Context context, Intent intent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, BluetoothLeService.class));
        } else {
            context.startService(new Intent(context, BluetoothLeService.class));
        }
    }
}
