package com.example.t2olibtest.ApplicationConstants;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.t2olibtest.ble.BluetoothLeService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WristbandConstants {
    //********************* New ************************
    public static boolean is_device_available=false;
    public static double distance_from_bcon=0;
    //********************* New ************************
    public static int call_srvc_num=0;
    public static String visi_page="Data";
    public static String call_which="Data_page";
    public static String fw_version="NA";
    public static String flag_con_ste ="0";
    public static String NETWORK_ERROR="Network Error";
    public static ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics_tot;
    public static BluetoothGatt bluetoothGatt;
    public static String tym_srvc="00002a2b-0000-1000-8000-00805f9b34fb";
    public static String fw_srvc="00002a26-0000-1000-8000-00805f9b34fb";
    public static String btry_srvc="00002a19-0000-1000-8000-00805f9b34fb";
    public static String manuf_srvc="00002a29-0000-1000-8000-00805f9b34fb";
    public static String model_srvc="00002a24-0000-1000-8000-00805f9b34fb";
    public static String temp_srvc="00002A6E-0000-1000-8000-00805f9b34fb";
    public static String humidity_srvc="00002A6F-0000-1000-8000-00805f9b34fb";
    public static String major_updt_srvc="10005a81-bf5a-466f-993e-f3e04fdf9c9e";
    public static String minor_updt_srvc="10005a81-bf5a-466f-993e-f3e04fdf8c9a";
    public static String wrk_strt_srvc="10005a81-bf5a-466f-993e-f3e04fdf9caa";
    public static String wrk_end_srvc="10005a81-bf5a-466f-993e-f3e04fdf9cab";
    public static String UUID_chng_srvc="10005a81-bf5a-466f-993e-f3e04fdf8c9e";
    public static String Trans_Intv_srvc="10005a81-bf5a-466f-993e-f3e04fdf8c9f";
    public static String SYSTEM_ID_srvc="00002a23-0000-1000-8000-00805f9b34fb";
    public static String OTA_srvc="f7bf3564-fb6d-4e53-88a4-5e37e0326063";
    public static String RW_srvc="10005a81-bf5a-466f-993e-f3e04fdf9c90";
    public static String Dis_srvc="10005a81-bf5a-466f-993e-f3e04fdf9c91";
    public static String Fc_rst_srvc="10005a81-bf5a-466f-993e-f3e04fdf9c9a";
    public static String Dvc_srvc="00002a00-0000-1000-8000-00805f9b34fb";
    public static String TX_Power_srvc="00002a07-0000-1000-8000-00805f9b34fb";
    public static String Beacon_measured_pwr_srvc="10005a81-bf5a-466f-993e-f3e04fdf8c9d";
    public static String Temp_inter_srvc="10005a81-bf5a-466f-993e-f3e04fdf8ca0";
    //Contact Tracing...4.5.0
    public static String Contact_1_450_srvc="ddcecee7-29f5-40fe-904f-f0639c50fa04";
    public static String Contact_2_450_srvc="ddcecee7-29f5-40fe-904f-f0639c50fa05";
    public static String Contact_3_450_srvc="ddcecee7-29f5-40fe-904f-f0639c50fa06";
    //Contact Tracing...4.5.1
    public static String Contact_1_451_srvc="ddcecee7-29f5-40fe-904f-f0639c50fa01";
    public static String Contact_2_451_srvc="ddcecee7-29f5-40fe-904f-f0639c50fa02";

    public static String Contact_3_451_srvc="ddcecee7-29f5-40fe-904f-f0639c50fa03";

    public static String Contact_4_451_srvc="ddcecee7-29f5-40fe-904f-f0639c50fa04";
    public static String Contact_5_451_srvc="ddcecee7-29f5-40fe-904f-f0639c50fa05";
    public static String Contact_6_451_srvc="ddcecee7-29f5-40fe-904f-f0639c50fa06";

    public static String Contact_7_451_srvc="ddcecee7-29f5-40fe-904f-f0639c50fa07";
    public static String Contact_8_451_srvc="ddcecee7-29f5-40fe-904f-f0639c50fa08";
    //Log clear...
    public static String Mem_Log_clr_srvc="10005a82-bf5a-466f-993e-f3e04fdf8c00";

    public static String hex_to_ascII(String hex)
    {
        StringBuilder builder = new StringBuilder();
        if(hex.length()%2!=0){
            System.err.println("Invlid hex string.");
        }
        else
        {
            for (int i = 0; i < hex.length(); i = i + 2) {
                // Step-1 Split the hex string into two character group
                String s = hex.substring(i, i + 2);
                // Step-2 Convert the each character group into integer using valueOf method
                int n = Integer.valueOf(s, 16);
                // Step-3 Cast the integer value to char
                builder.append((char)n);
            }
        }
        return builder.toString();
    }
    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new  StringBuilder();
        for(byte byteChar : bytes) {
            builder.append(String.format("%02x",byteChar));
        }
        return builder.toString();
    }
    public static BluetoothLeService mBluetoothLeService=null;
    public static boolean hasCon=false;
    public static String macID="NA";
    public static String dyn_srvc(String srvc)
    {
        String res="NA:NA";
        try {
            if (WristbandConstants.mGattCharacteristics_tot.size() > 0) {
                for (int i = 0; i < WristbandConstants.mGattCharacteristics_tot.size(); i++) {
                    for (int k = 0; k < WristbandConstants.mGattCharacteristics_tot.get(i).size(); k++) {
                        if (srvc.equalsIgnoreCase(WristbandConstants.mGattCharacteristics_tot.get(i).get(k).getUuid().toString())) {
                            res = i + ":" + k;
                            break;
                        }
                    }
                }
            }
        }
        catch(NullPointerException ex)
        {}
        return res;
    }
    public static byte[] hexToByteArray(String hex) {
        hex = hex.length()%2 != 0?"0"+hex:hex;

        byte[] b = new byte[hex.length() / 2];

        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(hex.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }
    public static byte[] hexStringToByteArray(String s) {
        byte[] data=null;
        try {
            int len = s.length();
            data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                        + Character.digit(s.charAt(i + 1), 16));
            }
        }
        catch(StringIndexOutOfBoundsException ex)
        {
            System.out.println("Error123...");
        }
        return data;
    }
    public static boolean inRange(int lowerBound, int upperBound, int input) { //-6 0 0
        // (First, be sure to check for null values)
        return input >= lowerBound && input <= upperBound;
    }
    public static String createDateHex(){
        String dateTime[] = getCurrentTime().split("-");
        String year = ((Integer.toHexString(Integer.parseInt(dateTime[0])).length() == 3)
                ? "0"+Integer.toHexString(Integer.parseInt(dateTime[0])) : Integer.toHexString(Integer.parseInt(dateTime[0])));
        String finalString= lastTwoCharecter(year)+  // YY - Year MSB
                firstTwoCharecte(year);  // YY - Year LSB
        // MM (Month),  DD (Day), HH (Hour), MM (Minute), SS (Secs)
        for(int i=1;i<dateTime.length;i++){
            finalString += ((Integer.toHexString(Integer.parseInt(dateTime[i])).length() == 1)
                    ? "0"+Integer.toHexString(Integer.parseInt(dateTime[i])) : Integer.toHexString(Integer.parseInt(dateTime[i])));
        }
        Calendar c = Calendar.getInstance();
        System.out.println(c.get(Calendar.DAY_OF_WEEK));
        finalString += "0"+(c.getTime().getDay());  //WEEKDAYNAME Week name (Monday=01, Tuesday=02 etc.)
        finalString += "00"; //FRACTIONS (millsecs)- 00 –FF
        return finalString.toUpperCase()+ "01"; //Adjust - 01
        // return "E50707030D0A37052D01"; //Adjust - 01
    }
    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String firstTwoCharecte(String str) {

        if(str.length()<2){
            return str;
        }
        else{
            return str.substring(0,2);
        }
    }
    public static String lastTwoCharecter(String value){
        String lastTwo = null;
        if (value != null && value.length() >= 2) {
            lastTwo = value.substring(value.length() - 2);
        }
        return lastTwo;
    }
    public static String dt_tm_parsing(String dgt_14)
    {
        String yr_mk_1=dgt_14.substring(0,2);
        String yr_mk_2=dgt_14.substring(2,4);
        String mm=dgt_14.substring(4,6);
        String dy=dgt_14.substring(6,8);
        String hr=dgt_14.substring(8,10);
        String min=dgt_14.substring(10,12);
        String sec=dgt_14.substring(12,14);
        String yr=yr_mk_2+yr_mk_1;
        int year=Integer.parseInt(yr,16);
        int month=Integer.parseInt(mm,16);
        int day=Integer.parseInt(dy,16);
        int hour=Integer.parseInt(hr,16);
        int minute=Integer.parseInt(min,16);
        int second=Integer.parseInt(sec,16);
        String hour_conv=String.valueOf(hour);
        String second_conv=String.valueOf(second);
        String minute_conv=String.valueOf(minute);
        String day_conv=String.valueOf(day);
        if(day_conv.length()==1) {
            day_conv = "0" + day_conv;
        }if(hour_conv.length()==1) {
        hour_conv = "0" + hour_conv;
    }
        if(minute_conv.length()==1) {
            minute_conv = "0" + minute_conv;
        }
        if(second_conv.length()==1) {
            second_conv = "0" + second_conv;
        }
        String display_month="";
        switch (month)
        {
            case 1:
                display_month="Jan";
                break;
            case 2:
                display_month="Feb";
                break;
            case 3:
                display_month="Mar";
                break;
            case 4:
                display_month="Apr";
                break;
            case 5:
                display_month="May";
                break;
            case 6:
                display_month="Jun";
                break;
            case 7:
                display_month="Jul";
                break;
            case 8:
                display_month="Aug";
                break;
            case 9:
                display_month="Sep";
                break;
            case 10:
                display_month="Oct";
                break;
            case 11:
                display_month="Nov";
                break;
            case 12:
                display_month="Dec";
        }
         return year+"-"+display_month +"-"+day_conv+" at "+ hour_conv+":"+minute_conv;
        // return year+"-"+display_month +"-"+day_conv+" at "+ hour_conv+":"+minute_conv+":"+second_conv;
    }
    public static String addspace(int i, String str)
    {
        StringBuilder str1 = new StringBuilder();
        for(int j=0;j<i;j++)
        {
            str1.append(" ");
        }
        //str1.append(str);
        String dvc_nm=str.concat(str1.toString());
        return dvc_nm;
    }

}
