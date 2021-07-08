package com.example.t2olibtest.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.t2olibtest.ApplicationConstants.WristbandConstants;
import com.example.t2olibtest.ble.Bluetooth;
import com.example.t2olibtest.interfc.MyInterface;

import static com.example.t2olibtest.ApplicationConstants.WristbandConstants.dyn_srvc;
import static com.example.t2olibtest.ApplicationConstants.WristbandConstants.hexStringToByteArray;

 public class FactoryTask extends AsyncTask<String, Void, Boolean> {
    Activity mContext;
    Bluetooth bluetooth;
    String srvc_typ;
    private MyInterface mListener;
    public FactoryTask(Activity mContext, Bluetooth bluetooth, String srvc_typ,MyInterface mListener) {
        this.mContext=mContext;
        this.bluetooth=bluetooth;
        this.srvc_typ=srvc_typ;
        this.mListener=mListener;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(bluetooth.getGatt().getServices()==null)
        {
            Log.e("Error","Service Not Found!");
        }
        else
        {
            String[] val_rw = dyn_srvc(WristbandConstants.RW_srvc).split(":");
            if (!val_rw[0].equals("NA")) {
                bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val_rw[0])).getCharacteristics().get(Integer.parseInt(val_rw[1])),
                        hexStringToByteArray("01"));
            }
        }
    }
    @Override
    protected Boolean doInBackground(String... params) {
        try {
            if(bluetooth.getGatt().getServices()==null)
            {
                Log.e("Error","Service Not Found!");
                return false;
            }
            else
            {
                Thread.sleep(3000);
                try {
                    String[] val_fc_rs = dyn_srvc(WristbandConstants.Fc_rst_srvc).split(":");
                    if (!val_fc_rs[0].equals("NA")) {
                        bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val_fc_rs[0])).getCharacteristics().get(Integer.parseInt(val_fc_rs[1])),
                                hexStringToByteArray("01"));
                        WristbandConstants.hasCon=false;
                        return true;
                    }
                    else
                    {
                        Log.e("Error","Service Not Found!");
                        return false;
                    }
                } catch (Exception ignored) {
                    return false;
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
     @Override
     protected void onPostExecute(Boolean result) {
         if (mListener != null)
             mListener.do_reset(result);
     }
}
