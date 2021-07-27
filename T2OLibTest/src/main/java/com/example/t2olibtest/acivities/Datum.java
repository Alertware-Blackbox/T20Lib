package com.example.t2olibtest.acivities;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.t2olibtest.ApplicationConstants.WristbandConstants;
import com.example.t2olibtest.Model.Attributes;
import com.example.t2olibtest.R;
import com.example.t2olibtest.asynctask.FactoryTask;
import com.example.t2olibtest.asynctask.SaveTask;
import com.example.t2olibtest.ble.BleWrapperUiCallbacks;
import com.example.t2olibtest.ble.Bluetooth;
import com.example.t2olibtest.ble.BluetoothLeService;
import com.example.t2olibtest.ble.SampleGattAttributes;
import com.example.t2olibtest.connection.Conn;
import com.example.t2olibtest.interfc.MyInterface;
import com.example.t2olibtest.pref.WristbandSharePref;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.t2olibtest.ApplicationConstants.WristbandConstants.createDateHex;
import static com.example.t2olibtest.ApplicationConstants.WristbandConstants.dt_tm_parsing;
import static com.example.t2olibtest.ApplicationConstants.WristbandConstants.dyn_srvc;
import static com.example.t2olibtest.ApplicationConstants.WristbandConstants.hexStringToByteArray;
import static com.example.t2olibtest.ApplicationConstants.WristbandConstants.inRange;
import static com.example.t2olibtest.ApplicationConstants.WristbandConstants.mBluetoothLeService;

public class Datum extends AppCompatActivity implements BleWrapperUiCallbacks {
    private Bluetooth bluetooth;
    Attributes attributes;
    String _mac;
    Activity mCon;
    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<>();
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    public Datum(Activity mCon, Attributes attributes,String _mac) {
        this.mCon=mCon;
        this._mac=_mac;
        this.attributes=attributes;
        WristbandSharePref.savePairedMac(mCon,_mac);
        bluetooth = Bluetooth.getInstance(mCon, Datum.this);
        bluetooth.initialize(attributes);
        Timer timer = new Timer();
        TimerTask task = new MyTask();
        timer.schedule(task, 100, 100);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
        if (bluetooth != null) {
            bluetooth.disconnect();
        }
    }
    public void redirect(Attributes attributes) {
        this.attributes=attributes;
        Timer timer = new Timer();
        TimerTask hourlyTask = new TimerTask () {
            @Override
            public void run () {
                bluetooth.startScanning(mCon);
              /*  if(WristbandConstants.hasCon)
                {

                }*/
               /* if(WristbandConstants.is_device_available)
                    alert();*/

                get_UUID();
                get_major();
                get_minor();
                get_rssi();

            }
        };
        timer.schedule (hourlyTask, 0L, 3000);
    }
    //Set Data...
    public void set_UUID(String _val)
    {
        if(WristbandConstants.hasCon&&!_val.equals("")&&_val.length()==32&&!attributes.getFw_ver().equalsIgnoreCase("5.3"))
        {
            new SaveTask(mCon,bluetooth,WristbandConstants.UUID_chng_srvc,_val).execute();
        }
    }
    public void set_major(String _val)
    {
        if(attributes.getFw_ver().equalsIgnoreCase("5.0")||attributes.getFw_ver().equalsIgnoreCase("5.1")||attributes.getFw_ver().equalsIgnoreCase("5.3")) {
            if (WristbandConstants.hasCon && !_val.equals("") && inRange(0,65535,Integer.parseInt(_val))) {
                new SaveTask(mCon, bluetooth, WristbandConstants.major_updt_srvc, _val).execute();
            }
        }
    }
    public void set_minor(String _val)
    {
        if(attributes.getFw_ver().equalsIgnoreCase("5.0")) {
            if (WristbandConstants.hasCon && !_val.equals("") && inRange(0,65535,Integer.parseInt(_val))) {
                new SaveTask(mCon, bluetooth, WristbandConstants.minor_updt_srvc, _val).execute();
            }
        }
    }
    public void set_dvc_nm(String _val)
    {
        if(WristbandConstants.hasCon&&!_val.equals(""))
        {
            new SaveTask(mCon,bluetooth,WristbandConstants.Dvc_srvc,_val).execute();
        }
    }
    public void set_TX(String _val)
    {
        if(WristbandConstants.hasCon&&!_val.equals("")&&inRange(-6,0,Integer.parseInt(_val)))
        {
            new SaveTask(mCon,bluetooth,WristbandConstants.TX_Power_srvc,_val).execute();
        }
    }
    public void set_strt_hr(String _val)
    {
        if(WristbandConstants.hasCon&&!_val.equals("")&&inRange(0,24,Integer.parseInt(_val))&&!attributes.getFw_ver().equalsIgnoreCase("5.3"))
        {
            new SaveTask(mCon,bluetooth,WristbandConstants.wrk_strt_srvc,_val).execute();
        }
    }
    public void set_end_hr(String _val)
    {
        if(WristbandConstants.hasCon&&!_val.equals("")&&inRange(0,24,Integer.parseInt(_val))&&!attributes.getFw_ver().equalsIgnoreCase("5.3"))
        {
            new SaveTask(mCon,bluetooth,WristbandConstants.wrk_end_srvc,_val).execute();
        }
    }
    public void set_TI(String _val)
    {
        if(WristbandConstants.hasCon&&!_val.equals("")&&inRange(100,1000,Integer.parseInt(_val)))
        {
            new SaveTask(mCon,bluetooth,WristbandConstants.Trans_Intv_srvc,_val).execute();
        }
    }
    public void set_msrd_pwr(String _val)
    {
        if(WristbandConstants.hasCon&&!_val.equals("")&&inRange(-100,0,Integer.parseInt(_val)))
        {
            new SaveTask(mCon,bluetooth,WristbandConstants.Beacon_measured_pwr_srvc,_val).execute();
        }
    }
    //Fac Rst...
    public void fc_rst()
    {
        if(WristbandConstants.hasCon)
        {
            new FactoryTask(mCon, bluetooth, WristbandConstants.Fc_rst_srvc, new MyInterface() {
                @Override
                public void do_reset(boolean result) {
                    //
                }
            }).execute();
        }
    }
    //Set Data...

    //Get Data...
    public String get_Gatt_status() {
        return attributes.getGatt_state()==null?"NA":attributes.getGatt_state();
    }
    public boolean get_Con_status() {
        return WristbandConstants.hasCon;
    }
    public String get_UUID() {
        return attributes.getUUID()==null?"NA":attributes.getUUID();
    }
    public String get_major() {
        return attributes.getMajor()==null?"NA":attributes.getMajor();
    }
    public String get_minor() {
        return attributes.getMinor()==null?"NA":attributes.getMinor();
    }
    public String get_rssi() {
        return attributes.getRssi()==null?"NA":attributes.getRssi();
    }
    public String get_dt_time() {
        if(WristbandConstants.hasCon)
            return attributes.getDt_time()==null?"NA":attributes.getDt_time();
        else
            return "NA";
    }
    public int get_battery() {
        if(WristbandConstants.hasCon)
            return attributes.getBat_lvl();
        else
            return 0;
    }
    public String get_dvc_nm() {
        if(WristbandConstants.hasCon)
            return attributes.getDvc_nm()==null?"NA":attributes.getDvc_nm();
        else
            return "NA";
    }
    public String get_dvc_mdl() {
        if(WristbandConstants.hasCon)
            return attributes.getDvc_model()==null?"NA":attributes.getDvc_model();
        else
            return "NA";
    }
    public String get_dvc_manf() {
        if(WristbandConstants.hasCon)
            return attributes.getDvc_mnfc()==null?"NA":attributes.getDvc_mnfc();
        else
            return "NA";
    }
    public String get_TX() {
        if(WristbandConstants.hasCon)
            return attributes.getTx_pwr()==null?"NA":attributes.getTx_pwr();
        else
            return "NA";
    }
    public String get_Trns_intvl() {
        if(WristbandConstants.hasCon)
            return attributes.getTrns_intvl()==null?"NA":attributes.getTrns_intvl();
        else
            return "NA";
    }
    public String get_msr_pwr() {
        if(WristbandConstants.hasCon)
            return attributes.getMsrd_pwr()==null?"NA":attributes.getMsrd_pwr();
        else
            return "NA";
    }
    public String get_Strt_hr() {
        if(WristbandConstants.hasCon&&!attributes.getFw_ver().equals("5.3"))
            return attributes.getStrt_wrk()==null?"NA":attributes.getStrt_wrk();
        else
            return "NA";
    }
    public String get_End_hr() {
        if(WristbandConstants.hasCon&&!attributes.getFw_ver().equals("5.3"))
            return attributes.getEnd_wrk()==null?"NA":attributes.getEnd_wrk();
        else
            return "NA";
    }
    public String get_sys_id() {
        if(attributes.getFw_ver().equals("5.0"))
            return "NA";
        else
            if(WristbandConstants.hasCon)
                return attributes.getSystemId()==null?"NA":attributes.getSystemId();
            else
                return "NA";
    }
    public String get_lv_tmp() {
        if(attributes.getFw_ver().equals("5.0")||attributes.getFw_ver().equals("5.3"))
            return "NA";
        else
            if(WristbandConstants.hasCon)
                return attributes.getlv_tmp()==null?"NA":attributes.getlv_tmp();
            else
                return "NA";
    }
    public String get_lv_humd() {
        if(attributes.getFw_ver().equals("5.0")||attributes.getFw_ver().equals("5.1")||attributes.getFw_ver().equals("5.3"))
            return "NA";
        else
            if(WristbandConstants.hasCon)
                return attributes.getlv_humi()==null?"NA":attributes.getlv_humi();
            else
                return "NA";
    }
    public String get_fw() {
        if(WristbandConstants.hasCon)
            return attributes.getFw_ver()==null?"NA":attributes.getFw_ver();
        else
            return "NA";
    }
    //Get Data...
    @Override
    public void uiDeviceFound(BluetoothDevice device, int rssi, byte[] record) {

    }

    @Override
    public void uiDeviceConnected(BluetoothGatt gatt, BluetoothDevice device) {

    }

    @Override
    public void uiDeviceDisconnected(BluetoothGatt gatt, BluetoothDevice device) {

    }

    @Override
    public void uiAvailableServices(BluetoothGatt gatt, BluetoothDevice device, List<BluetoothGattService> services) {

    }

    @Override
    public void uiCharacteristicForService(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, List<BluetoothGattCharacteristic> chars) {

    }

    @Override
    public void uiCharacteristicsDetails(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, BluetoothGattCharacteristic characteristic) {

    }

    @Override
    public void uiNewValueForCharacteristic(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, BluetoothGattCharacteristic ch, String strValue, int intValue, byte[] rawValue, String timestamp) {

    }

    @Override
    public void uiGotNotification(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, BluetoothGattCharacteristic characteristic) {

    }

    @Override
    public void uiSuccessfulWrite(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, BluetoothGattCharacteristic ch, String description) {
    }

    @Override
    public void uiFailedWrite(BluetoothGatt gatt, BluetoothDevice device, BluetoothGattService service, BluetoothGattCharacteristic ch, String description) {

    }

    @Override
    public void uiNewRssiAvailable(BluetoothGatt gatt, BluetoothDevice device, int rssi) {
    }
    //********************************************
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                System.out.println("Error is--->>1 Connected");
                attributes.setGatt_state("Gatt Connected");
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                System.out.println("Error is--->>2 Not Connected");
                attributes.setGatt_state("Gatt Disconnected");
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                try {
                    System.out.println("Error is--->>3 ACTION_GATT_SERVICES_DISCOVERED");
                    attributes.setGatt_state("Discovered Gatt");
                    displayGattServices(mBluetoothLeService.getSupportedGattServices());
                } catch (NullPointerException ex) {
                    attributes.setGatt_state("Error in Discovered Gatt");
                    System.out.println("Error is--->> " + ex);
                }
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                attributes.setGatt_state("Action Data Available");
                System.out.println("Error is--->> ACTION_DATA_AVAILABLE");
                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };
    //*************************************************************************
    private void displayData(String data) {
        if (data != null) {
            if (!data.equalsIgnoreCase("")) {
                try {
                    String[] value = data.split("\r\n");
                    for (int i = 0; i < value.length; i++) {
                        if(WristbandConstants.call_srvc_num==1){
                            try {
                                //CONVERT Time Start...
                                String without_spc=data.replaceAll(" ", "");
                                String dgt_14=without_spc.substring(0,14);
                                String display_dt_tm=dt_tm_parsing(dgt_14);
                                if(display_dt_tm.contains("2020-Jan-31"))   //2000-Aug-16
                                {
                                    String[] tym_val = dyn_srvc(WristbandConstants.tym_srvc).split(":");
                                    if (!tym_val[0].equals("NA")&&bluetooth.getGatt()!=null) {
                                        bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(tym_val[0])).getCharacteristics().get(Integer.parseInt(tym_val[1])),
                                                hexStringToByteArray(createDateHex()));
                                    }
                                }
                                attributes.setDt_time(display_dt_tm);
                                System.out.println("Final Fat--->> " + bluetooth.getGatt().getServices());
                            } catch (Exception ignored) {
                            }
                        }
                        else if(WristbandConstants.call_srvc_num==2)
                        {
                            try {
                                String without_spc=data.replaceAll(" ", "");
                                int txPower=Integer.valueOf(without_spc,16).byteValue();
                                attributes.setTx_pwr(String.valueOf(txPower));
                            } catch (Exception ignored) {
                            }
                        }
                        else if(WristbandConstants.call_srvc_num==3)
                        {
                            try {
                                String without_spc=data.replaceAll(" ", "");
                                StringBuilder output = new StringBuilder();
                                for (int l = 0; l < without_spc.length(); l+=2) {
                                    String str = without_spc.substring(l, l+2);
                                    output.append((char)Integer.parseInt(str, 16));
                                }
                                attributes.setDvc_model(output.toString());
                            } catch (Exception ignored) {
                            }
                        }
                        else if(WristbandConstants.call_srvc_num==4)
                        {
                            try {
                                String without_spc=data.replaceAll(" ", "");
                                StringBuilder output = new StringBuilder();
                                for (int l = 0; l < without_spc.length(); l+=2) {
                                    String str = without_spc.substring(l, l+2);
                                    output.append((char)Integer.parseInt(str, 16));
                                }
                                attributes.setDvc_mnfc(output.toString());
                            } catch (Exception ignored) {
                            }
                        }
                        else if(WristbandConstants.call_srvc_num==5)
                        {
                            String without_spc=data.replaceAll(" ", "");
                            int bat_lvl=Integer.parseInt(without_spc,16);
                            attributes.setBat_lvl(bat_lvl);
                        }
                        else if(WristbandConstants.call_srvc_num==6)
                        {
                            try {
                                String without_spc=data.replaceAll(" ", "");
                                StringBuilder output = new StringBuilder();
                                for (int l = 0; l < without_spc.length(); l+=2) {
                                    String str = without_spc.substring(l, l+2);
                                    output.append((char)Integer.parseInt(str, 16));
                                }
                                String ver=output.toString();
                                attributes.setFw_ver(ver.replace("FW-Ver-",""));
                        } catch (Exception ignored) {
                        }
                    }
                        else if(WristbandConstants.call_srvc_num==7)
                        {
                            try {
                                String without_spc=data.replaceAll(" ", "");
                                int start_time=Integer.parseInt(without_spc,16);
                                String st_tym=String.valueOf(start_time);
                                attributes.setStrt_wrk(st_tym.length()==1?"0"+st_tym:st_tym);
                            } catch (Exception ignored) {
                            }
                        }
                        else if(WristbandConstants.call_srvc_num==8)
                        {
                            try {
                                String without_spc=data.replaceAll(" ", "");
                                int end_time=Integer.parseInt(without_spc,16);
                                String end_tym=String.valueOf(end_time);
                                attributes.setEnd_wrk(end_tym.length()==1?"0"+end_tym:end_tym);
                            } catch (Exception ignored) {
                            }
                        }
                        else if(WristbandConstants.call_srvc_num==10)
                        {
                            try {
                                String without_spc=data.replaceAll(" ", "");
                                if(without_spc.equalsIgnoreCase("0A"))
                                    without_spc="10";
                                attributes.setTrns_intvl(String.valueOf(Integer.parseInt(without_spc)*100));
                                } catch (Exception ignored) {
                            }
                        }
                        else if(WristbandConstants.call_srvc_num==11)
                        {
                            try {
                                String without_spc=data.replaceAll(" ", "");
                                attributes.setSystemId(without_spc);
                            } catch (Exception ignored) {
                            }
                        }
                        else if(WristbandConstants.call_srvc_num==12)
                        {
                            try {
                                String without_spc=data.replaceAll(" ", "");
                                StringBuilder output = new StringBuilder();
                                for (int l = 0; l < without_spc.length(); l+=2) {
                                    String str = without_spc.substring(l, l+2);
                                    output.append((char)Integer.parseInt(str, 16));
                                }
                                attributes.setDvc_nm(output.toString());
                            }
                            catch (Exception ignored) {
                            }
                        }
                        else if(WristbandConstants.call_srvc_num==30)
                        {
                            try {
                                String without_spc=data.replaceAll(" ", "");
                                int result=-5;
                                if(without_spc.contains("00"))
                                {
                                    result=0;
                                }
                                else
                                    result = (Integer.parseInt(Integer.toString(Integer.parseInt(without_spc, 16), 2).replace('0', 'X').replace('1', '0').replace('X', '1'), 2) + 1) * -1;
                                attributes.setMsrd_pwr(String.valueOf(result));
                            } catch (Exception ignored) {}
                        }
                        if(attributes.getFw_ver().equals("5.1")){
                            if (WristbandConstants.call_srvc_num == 13) {
                                try {
                                    String without_spc = data.replaceAll(" ", "");
                                    String temp_1 = without_spc.substring(0, 2);
                                    String temp_2 = without_spc.substring(2, 4);
                                    String temp = temp_2 + temp_1;
                                    int decimal = Integer.parseInt(temp, 16);
                                    DecimalFormat df1 = new DecimalFormat("#.00");
                                    df1.format(decimal);
                                    attributes.setlv_tmp(df1.format(decimal * 0.01));
                                } catch (Exception ignored) {
                                }
                            }
                        }
                        else if(attributes.getFw_ver().equals("5.2")) {
                            if (WristbandConstants.call_srvc_num == 13) {
                                try {
                                    String without_spc = data.replaceAll(" ", "");
                                    String temp_1 = without_spc.substring(0, 2);
                                    String temp_2 = without_spc.substring(2, 4);
                                    String temp = temp_2 + temp_1;
                                    int decimal = Integer.parseInt(temp, 16);
                                    DecimalFormat df1 = new DecimalFormat("#.00");
                                    df1.format(decimal);
                                    attributes.setlv_tmp(df1.format(decimal * 0.01));
                                } catch (Exception ignored) {
                                }
                            }
                            if (WristbandConstants.call_srvc_num == 14) {
                                try {
                                    String without_spc = data.replaceAll(" ", "");
                                    String humi_1 = without_spc.substring(0, 2);
                                    String humi_2 = without_spc.substring(2, 4);
                                    String humi = humi_2 + humi_1;
                                    int decimal = Integer.parseInt(humi, 16);
                                    DecimalFormat df1 = new DecimalFormat("#.00");
                                    df1.format(decimal);
                                    attributes.setlv_humi(df1.format(decimal * 0.01));//+ " %"
                                } catch (Exception e) {
                                }
                            }
                        }

                       /*

                        if(WristbandConstants.fw_version.equals("5.1")||WristbandConstants.fw_version.equals("5.2")||WristbandConstants.fw_version.equals("5.1.1"))
                        {
                            if(WristbandConstants.call_srvc_num==13)
                            {
                                try {
                                    String without_spc=data.replaceAll(" ", "");
                                    String temp_1=without_spc.substring(0,2);
                                    String temp_2=without_spc.substring(2,4);
                                    String temp=temp_2+temp_1;
                                    int decimal=Integer.parseInt(temp,16);
                                    DecimalFormat df1 = new DecimalFormat("#.00");
                                    df1.format(decimal);
                                    mutableLiveData_Temp.setValue(df1.format(decimal*0.01)+" \u2103");
                                } catch (Exception ignored) {
                                }
                            }
                            if(WristbandConstants.call_srvc_num==14)
                            {
                                try {
                                    String without_spc=data.replaceAll(" ", "");
                                    String humi_1=without_spc.substring(0,2);
                                    String humi_2=without_spc.substring(2,4);
                                    String humi=humi_2+humi_1;
                                    int decimal=Integer.parseInt(humi,16);
                                    DecimalFormat df1 = new DecimalFormat("#.00");
                                    df1.format(decimal);
                                    mutableLiveData_Humi.setValue(df1.format(decimal*0.01)+" %");
                                } catch (Exception e) {
                                }
                            }
                            if(WristbandConstants.fw_version.equals("5.1.1")&&WristbandConstants.call_which.equals("Temp_page"))
                            {
                                try {
                                    String without_spc=data.replaceAll(" ", "");
                                    System.out.println("Temperature 0--->>"+without_spc);
                                    mutableLiveData_temp_log.setValue(without_spc);
                                } catch (Exception e) {
                                }
                            }
                            if(WristbandConstants.call_srvc_num==18)
                            {
                                try {
                                    String without_spc=data.replaceAll(" ", "");
                                    mutableLiveData_temp_intvl.setValue(without_spc);
                                } catch (Exception e) {
                                }
                            }
                            if(WristbandConstants.fw_version.equals("4.5"))
                            {
                                if(WristbandConstants.call_srvc_num==19)
                                {
                                    try {
                                        String without_spc=data.replaceAll(" ", "");
                                        if(without_spc.contains("000000000000000000000000000000000000000000000000000000000000"))
                                        {
                                            Toast.makeText(MainActivity.this, "No Contact Tracing Data available!", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                            mutableLiveData_cntct_log.setValue(without_spc);
                                    } catch (Exception e) {
                                    }
                                }
                                if(WristbandConstants.call_srvc_num==20)
                                {
                                    try {
                                        String without_spc=data.replaceAll(" ", "");
                                        // String without_spc= "$2021-03-22,10:00:00,84712721848B#$2021-03-22,10:30:00,84712721848D#$2021-03-22,11:00:00";
                                        mutableLiveData_cntct_log2.setValue(without_spc);
                                    } catch (Exception e) {
                                    }
                                }
                                if(WristbandConstants.call_srvc_num==21)
                                {
                                    try {
                                        String without_spc=data.replaceAll(" ", "");
                                        mutableLiveData_cntct_log3.setValue(without_spc);
                                    } catch (Exception e) {
                                    }
                                }
                            }
                            else if(WristbandConstants.fw_version.equals("4.5.1"))
                            {
                                if(WristbandConstants.call_srvc_num==22)
                                {
                                    try {
                                        String without_spc=data.replaceAll(" ", "");
                                        System.out.println("Result--->>0 "+without_spc);
                                        if(without_spc.contains("000000000000000000000000000000000000000000000000000000000000"))
                                        {
                                            Toast.makeText(MainActivity.this, "No Contact Tracing Data available!", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                            mutableLiveData_cntct_log4.setValue(without_spc);
                                    } catch (Exception e) {
                                    }
                                }
                                if(WristbandConstants.call_srvc_num==23)
                                {
                                    try {
                                        String without_spc=data.replaceAll(" ", "");
                                        System.out.println("Result--->>1 "+without_spc);
                                        mutableLiveData_cntct_log5.setValue(without_spc);
                                    } catch (Exception e) {
                                    }
                                }
                                if(WristbandConstants.call_srvc_num==24)
                                {
                                    try {
                                        String without_spc=data.replaceAll(" ", "");
                                        System.out.println("Result--->>2 "+without_spc);
                                        mutableLiveData_cntct_log6.setValue(without_spc);
                                    } catch (Exception e) {
                                    }
                                }
                                if(WristbandConstants.call_srvc_num==25)
                                {
                                    try {
                                        String without_spc=data.replaceAll(" ", "");
                                        System.out.println("Result--->>3 "+without_spc);
                                        mutableLiveData_cntct_log7.setValue(without_spc);
                                    } catch (Exception e) {
                                    }
                                }
                                if(WristbandConstants.call_srvc_num==26)
                                {
                                    try {
                                        String without_spc=data.replaceAll(" ", "");
                                        System.out.println("Result--->>4 "+without_spc);
                                        mutableLiveData_cntct_log8.setValue(without_spc);
                                    } catch (Exception e) {
                                    }
                                }
                                if(WristbandConstants.call_srvc_num==27)
                                {
                                    try {
                                        String without_spc=data.replaceAll(" ", "");
                                        System.out.println("Result--->>5 "+without_spc);
                                        mutableLiveData_cntct_log9.setValue(without_spc);
                                    } catch (Exception e) {
                                    }
                                }
                                if(WristbandConstants.call_srvc_num==28)
                                {
                                    try {
                                        String without_spc=data.replaceAll(" ", "");
                                        System.out.println("Result--->>6 "+without_spc);
                                        mutableLiveData_cntct_log10.setValue(without_spc);
                                    } catch (Exception e) {
                                    }
                                }
                                if(WristbandConstants.call_srvc_num==29)
                                {
                                    try {
                                        String without_spc=data.replaceAll(" ", "");
                                        System.out.println("Result--->>7 "+without_spc);
                                        mutableLiveData_cntct_log11.setValue(without_spc);
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }

                        if(WristbandConstants.call_srvc_num!=1)
                        {
                            mutableLiveData_tx.setValue(tx_val);
                            mutableLiveData_model.setValue(model_num);
                            mutableLiveData_manuf.setValue(manfc);
                            mutableLiveData_pwr.setValue(batry_percnt);
                        }*/
                    }

                } catch (Exception e) {
                    Log.v("error", e.toString());

                }
            }
        }
    }
    //*************************************************************************
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(WristbandSharePref.getPairedMac(mCon));
            mBluetoothLeService = mBluetoothLeService;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = mCon.getResources().getString(R.string.unknown_service);
        String unknownCharaString = mCon.getResources().getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<>();

        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            WristbandConstants.mGattCharacteristics_tot=mGattCharacteristics;
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }
    }
    public void connect() {
        bluetooth = Bluetooth.getInstance(mCon, Datum.this,attributes);
        new Conn(mCon,_mac,bluetooth,attributes);
        Intent gattServiceIntent = new Intent(mCon, BluetoothLeService.class);
        mCon.bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        mCon.registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(WristbandSharePref.getPairedMac(mCon));
        }
    }
    public boolean disconnect()
    {
        String[] val_rw = dyn_srvc(WristbandConstants.RW_srvc).split(":");
        if (!val_rw[0].equals("NA")) {
            try {
                Thread.sleep(2000);
                if (WristbandConstants.mBluetoothLeService != null) {
                    bluetooth.disconnect();
                    bluetooth.getGatt().disconnect();
                    WristbandConstants.hasCon=false;
                } else {
                    return false;
                }
            } catch (InterruptedException e) {
                return false;
            }
        }
        return true;
    }
//*********************************** TIMER tASK ************************************
class MyTask extends TimerTask {
    int m = 0;
    int l = 0;
    int n = 0;
    public void run() {
        if (mGattCharacteristics != null) {
            if (mGattCharacteristics.size() > 3) {
                BluetoothGattCharacteristic characteristic = null;
         //      if (WristbandConstants.call_which.equals("Data_page")) {
                    if (m == 15)
                        m = 0;
                if (m == 0) {
                    String[] val_manuf = dyn_srvc(WristbandConstants.manuf_srvc).split(":");
                    if (!val_manuf[0].equals("NA")) {
                        characteristic =
                                mGattCharacteristics.get(Integer.parseInt(val_manuf[0])).get(Integer.parseInt(val_manuf[1]));
                    }
                }
                else if (m == 1) {
                    String[] val = dyn_srvc(WristbandConstants.fw_srvc).split(":");
                    if (!val[0].equals("NA")) {
                        characteristic =
                                mGattCharacteristics.get(Integer.parseInt(val[0])).get(Integer.parseInt(val[1]));
                    }
                }

                else if (m == 2) {
                        String[] val = dyn_srvc(WristbandConstants.model_srvc).split(":");
                        if (!val[0].equals("NA")) {
                            characteristic =
                                    mGattCharacteristics.get(Integer.parseInt(val[0])).get(Integer.parseInt(val[1]));
                        }
                    }
                else if (m == 3) {
                        String[] val = dyn_srvc(WristbandConstants.btry_srvc).split(":");
                        if (!val[0].equals("NA")) {
                            characteristic =
                                    mGattCharacteristics.get(Integer.parseInt(val[0])).get(Integer.parseInt(val[1]));
                        }
                    }
                else if (m == 4) {
                    String[] val = dyn_srvc(WristbandConstants.wrk_strt_srvc).split(":");
                    if (!val[0].equals("NA")) {
                        characteristic =
                                mGattCharacteristics.get(Integer.parseInt(val[0])).get(Integer.parseInt(val[1]));
                    }
                } else if (m == 5) {
                    String[] val = dyn_srvc(WristbandConstants.wrk_end_srvc).split(":");
                    if (!val[0].equals("NA")) {
                        characteristic =
                                mGattCharacteristics.get(Integer.parseInt(val[0])).get(Integer.parseInt(val[1]));
                    }
                }
                else if (m == 7) {
                    String[] val = dyn_srvc(WristbandConstants.Trans_Intv_srvc).split(":");
                    if (!val[0].equals("NA")) {
                        characteristic =
                                mGattCharacteristics.get(Integer.parseInt(val[0])).get(Integer.parseInt(val[1]));
                    }
                }
                else if (m == 8) {
                        String[] val = dyn_srvc(WristbandConstants.tym_srvc).split(":");
                        if (!val[0].equals("NA")) {
                            characteristic =
                                    mGattCharacteristics.get(Integer.parseInt(val[0])).get(Integer.parseInt(val[1]));
                        }
                    }
                else if (m == 9) {
                    String[] val = dyn_srvc(WristbandConstants.SYSTEM_ID_srvc).split(":");
                    if (!val[0].equals("NA")) {
                        characteristic =
                                mGattCharacteristics.get(Integer.parseInt(val[0])).get(Integer.parseInt(val[1]));
                    }
                }
                else if (m == 10) {
                    String[] val = dyn_srvc(WristbandConstants.humidity_srvc).split(":");
                    if (!val[0].equals("NA")) {
                        characteristic =
                                mGattCharacteristics.get(Integer.parseInt(val[0])).get(Integer.parseInt(val[1]));
                    }
                }
                else if (m == 11) {
                    String[] val = dyn_srvc(WristbandConstants.Dvc_srvc).split(":");
                    if (!val[0].equals("NA")) {
                        characteristic =
                                mGattCharacteristics.get(Integer.parseInt(val[0])).get(Integer.parseInt(val[1]));
                    }
                }
                else if (m == 12) {
                    String[] val = dyn_srvc(WristbandConstants.TX_Power_srvc).split(":");
                    if (!val[0].equals("NA")) {
                        characteristic =
                                mGattCharacteristics.get(Integer.parseInt(val[0])).get(Integer.parseInt(val[1]));
                    }
                }
                else if(m==13){
                    String[] val_interval = dyn_srvc(WristbandConstants.Beacon_measured_pwr_srvc).split(":");
                    if (!val_interval[0].equals("NA")) {
                        characteristic =
                                mGattCharacteristics.get(Integer.parseInt(val_interval[0])).get(Integer.parseInt(val_interval[1]));
                    }
                }
                else if (m == 14) {
                    String[] val = dyn_srvc(WristbandConstants.temp_srvc).split(":");
                    if (!val[0].equals("NA")) {
                        characteristic =
                                mGattCharacteristics.get(Integer.parseInt(val[0])).get(Integer.parseInt(val[1]));
                    }
                }
                m = m + 1;


                /* else if (m == 10) {
                        String[] val = dyn_srvc(WristbandConstants.OTA_srvc).split(":");
                        if (!val[0].equals("NA")) {
                            characteristic =
                                    mGattCharacteristics.get(Integer.parseInt(val[0])).get(Integer.parseInt(val[1]));
                        }
                    }


                    else if(m==15){
                        String[] val_interval = dyn_srvc(WristbandConstants.Temp_inter_srvc).split(":");
                        if (!val_interval[0].equals("NA")) {
                            characteristic =
                                    mGattCharacteristics.get(Integer.parseInt(val_interval[0])).get(Integer.parseInt(val_interval[1]));
                        }
                    }
                   */

                //}
                try{
                    final int charaProp = characteristic.getProperties();
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                        if (mNotifyCharacteristic != null
                               &&mBluetoothLeService != null) {
                            mBluetoothLeService.setCharacteristicNotification(
                                    mNotifyCharacteristic, false);
                            mNotifyCharacteristic = null;
                        }
                        if (mBluetoothLeService != null) {
                            mBluetoothLeService.readCharacteristic(characteristic);
                        }
                    }
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                        mNotifyCharacteristic = characteristic;
                        if (mBluetoothLeService != null) {
                            mBluetoothLeService.setCharacteristicNotification(
                                    characteristic, true);
                        }
                    }
                }
                catch(NullPointerException ex)
                {
                    System.out.println("AAAAAAAAAAAA--->> "+ex);
                }
            }
        }
    }
}
//*********************************** TIMER tASK ************************************
}