package com.example.t2olibtest.connection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;

import com.example.t2olibtest.ApplicationConstants.WristbandConstants;
import com.example.t2olibtest.Model.Attributes;
import com.example.t2olibtest.ble.Bluetooth;
import com.example.t2olibtest.ble.BluetoothLeService;
import com.example.t2olibtest.pref.WristbandSharePref;

import static android.content.Context.BIND_AUTO_CREATE;
import static com.example.t2olibtest.ApplicationConstants.WristbandConstants.mBluetoothLeService;


public class Conn {
    Activity activity;
    private Bluetooth bluetooth=null;
    private Attributes attributes;
    public Conn(Activity activity, String data, Bluetooth bluetooth, Attributes attributes) {
        this.activity = activity;
        this.bluetooth = bluetooth;
        this.attributes = attributes;
        authConnetion(data);
    }
    public void authConnetion(final String data) {
        @SuppressLint("StaticFieldLeak")
        class ConnectionAsync extends AsyncTask<String, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if(data.length()==0||data==null){
                    WristbandConstants.hasCon=false;
                }
                else
                {
                    WristbandConstants.macID=data;
                }
            }

            @Override
            protected Void doInBackground(String... params) {
                if(data.length()>0)
                {
                    bluetooth.initialize();
                    bluetooth.connect(WristbandConstants.macID);
                }

                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(data.length()>0)
                {
                    try {
                        System.out.println("AAAAAAAAAAAAAAAAAAAAA");
                        WristbandSharePref.savePairedMac(activity,data);
                        WristbandConstants.macID=data;
                        WristbandConstants.hasCon=true;
                    } catch (Exception e) {
                        WristbandConstants.hasCon=false;
                    }
                }
                else
                {
                    WristbandConstants.hasCon=false;

                }
            }
        }
        ConnectionAsync la = new ConnectionAsync();
        la.execute();
    }
}
