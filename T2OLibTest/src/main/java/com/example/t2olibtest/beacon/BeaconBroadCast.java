package com.example.t2olibtest.beacon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.t2olibtest.ble.BeaconService;


/*
    This is class that inherits BroadcastReceiver
    This BroadcastReceiver Class is used to restart the service whenever
    by chance our Service get Destroyed.
 */
public class BeaconBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Again Starting the service");
        //Again starting the service
        context.startService(new Intent(context, BeaconService.class));
    }
}
