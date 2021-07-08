package com.example.t2olibtest.pref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class WristbandSharePref {

    public static final String orsacpreference = "orsacpref";
    public static final String orsacpreference_is_register = "is_registerorsacpref";
    public static final String IMEI = "imeiKey";
    public static final String USERID = "user_id";
    public static final String USERNAME = "user_name";
    public static final String ORGID = "org_id";
    public static final String PROFILE_IMAGE = "profile_image";
    public static final String ORG_NAME = "org_name";
    public static final String EMAIL_ID = "email_id";
    public static final String REGISTRATIN_DONE = "registration_done";
    public static final String APPLICATION_TYPE = "application_type";
    public static final String PASSWORD = "password";
    public static final String DATABASE_POPULATION = "db_population";
    public static final String PAIRED_DEVICE_MACADDRESS = "paired_device_mac_address";
    public static final String FROM_DASHBOARD = "from_dashboard";
    public static final String UNIQUE_ID = "unique_id";
    public static final String USER_GSM_NUMBER = "user_gsm_number";
    public static final String TIME_INTERVAL = "time_interval";
    private static SharedPreferences sharedpreferences;


    public static void saveSharePref(Activity activity){
        sharedpreferences = activity.getSharedPreferences(orsacpreference,
                Context.MODE_PRIVATE);
    }

    public static void saveSharePrefIsRegister(Activity activity){
        sharedpreferences = activity.getSharedPreferences(orsacpreference_is_register,
                Context.MODE_PRIVATE);
    }

    public static void saveImeiNumber(String imei, Activity activity){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(IMEI, imei);
        editor.commit();
    }

    public static String getImeiNumber(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getString(IMEI, "");
    }


    //User Id
    public static void saveUserId(int imei, Activity activity){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(USERID, imei);
        editor.commit();
    }

    public static int getUserId(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getInt(USERID, -1);
    }

    //User Name
    public static void saveUserName(String imei, Activity activity){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USERNAME, imei);
        editor.commit();
    }


    //User Id
    public static void savePassword(String imei, Activity activity){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(PASSWORD, imei);
        editor.commit();
    }

    public static String getPassword(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getString(PASSWORD, "");
    }

    public static String getUserName(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getString(USERNAME, "");
    }


    //User Name
    public static void saveOrgId(int imei, Activity activity){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(ORGID, imei);
        editor.commit();
    }

    public static int getOrgId(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getInt(ORGID, -1);
    }

    //User Name
    public static void saveProfileImage(String imei, Activity activity){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(PROFILE_IMAGE, imei);
        editor.commit();
    }

    public static String getProfileImage(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getString(PROFILE_IMAGE, "");
    }

    //User Name
    public static void saveOrgName(String imei, Activity activity){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(ORG_NAME, imei);
        editor.commit();
    }

    public static String getOrgName(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getString(ORG_NAME, "");
    }

    //User Name
    public static void saveEmailAddress(String email, Activity activity){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(EMAIL_ID, email);
        editor.commit();
    }

    public static String getEmailAddress(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getString(EMAIL_ID, "");
    }
    public static void clearAllSharePref(Activity activity){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }

    //Registration Done
    public static void saveRegistrationDone(Activity activity){
        saveSharePrefIsRegister(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(REGISTRATIN_DONE, true);
        editor.commit();
    }

    public static boolean getRegistrationDone(Activity activity){
        saveSharePrefIsRegister(activity);
        return sharedpreferences.getBoolean(REGISTRATIN_DONE, false);
    }


    //Application Selection
    public static void saveApplicationType(Activity activity, int value){
        saveSharePrefIsRegister(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(APPLICATION_TYPE, value);
        editor.commit();
    }

    public static int getApplicationType(Activity activity){
        saveSharePrefIsRegister(activity);
        return sharedpreferences.getInt(APPLICATION_TYPE, 0);
    }

    //Application Selection
    public static void saveDbPopulatopn(Activity activity){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(DATABASE_POPULATION, true);
        editor.commit();
    }

    public static boolean getDbPopulatopn(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getBoolean(DATABASE_POPULATION, false);
    }

    //User Name
    public static void saveEmailAddressFromLogin(String email, Activity activity){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(EMAIL_ID, email);
        editor.commit();
    }

    public static String getEmailAddressLogin(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getString(EMAIL_ID, "");
    }

    //Application Selection
    public static void savePairedMac(Activity activity, String mac_address){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(PAIRED_DEVICE_MACADDRESS, mac_address);
        editor.commit();
    }

    public static String getPairedMac(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getString(PAIRED_DEVICE_MACADDRESS, "00:00:00:00:00");
    }

    //Application Selection
    public static void saveFromDashboard(Activity activity){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(FROM_DASHBOARD, true);
        editor.commit();
    }

    public static boolean getFromDashboard(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getBoolean(FROM_DASHBOARD, false);
    }

    //Unique id
    public static void saveUniqueID(Activity activity, String unique_id){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(UNIQUE_ID, unique_id);
        editor.commit();
    }

    public static String getUniqueID(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getString(UNIQUE_ID, "");
    }

    //Unique id
    public static void saveUserGSMNumber(Activity activity, String gsm_number){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USER_GSM_NUMBER, gsm_number);
        editor.commit();
    }

    public static String getUserGSMNumber(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getString(USER_GSM_NUMBER, "");
    }

    //Time interval
    public static void saveTimeInterval(Activity activity, String time_interval){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(TIME_INTERVAL, time_interval);
        editor.commit();
    }

    public static String getTimeInterval(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getString(TIME_INTERVAL, "30");
    }

    //Set Firmware Version
    public static void saveVersionFirmware(Activity activity, String time_interval){
        saveSharePref(activity);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(TIME_INTERVAL, time_interval);
        editor.commit();
    }

    public static String getFirmwareVersion(Activity activity){
        saveSharePref(activity);
        return sharedpreferences.getString(TIME_INTERVAL, "1.0");
    }




}
