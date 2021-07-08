package com.example.t2olibtest.Model;

public class Attributes {
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    private  String major="NA";
    private  String minor="NA";
    private  String UUID="NA";
    private  String rssi="NA";
    private  String dt_time="NA";
    private  String dvc_mnfc="NA";
    private  String dvc_model="NA";
    private  String fw_ver="NA";
    private  String tx_pwr="NA";
    private  String trns_intvl="NA";
    private  int bat_lvl=0;
    private  String dvc_nm="NA";
    private  String systemId="NA";
    private  String strt_wrk="NA";
    private  String end_wrk="NA";
    private  String msrd_pwr="NA";
    private  String lv_tmp="NA";
    private  String lv_humi="NA";
    private  String Gatt_state="NA";

    public String getGatt_state() {
        return Gatt_state;
    }

    public void setGatt_state(String gatt_state) {
        Gatt_state = gatt_state;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }


    public String getlv_tmp() {
        return lv_tmp;
    }

    public void setlv_tmp(String lv_tmp) {
        this.lv_tmp = lv_tmp;
    }

    public String getlv_humi() {
        return lv_humi;
    }

    public void setlv_humi(String lv_humi) {
        this.lv_humi = lv_humi;
    }



    public String getDvc_mnfc() {
        return dvc_mnfc;
    }

    public void setDvc_mnfc(String dvc_mnfc) {
        this.dvc_mnfc = dvc_mnfc;
    }
    public String getFw_ver() {
        return fw_ver;
    }

    public void setFw_ver(String fw_ver) {
        this.fw_ver = fw_ver;
    }

    public String getDvc_model() {
        return dvc_model;
    }

    public void setDvc_model(String dvc_model) {
        this.dvc_model = dvc_model;
    }

    public int getBat_lvl() {
        return bat_lvl;
    }

    public void setBat_lvl(int bat_lvl) {
        this.bat_lvl = bat_lvl;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }


    public String getDt_time() {
        return dt_time;
    }

    public void setDt_time(String dt_time) {
        this.dt_time = dt_time;
    }
    public String getTx_pwr() {
        return tx_pwr;
    }

    public void setTx_pwr(String tx_pwr) {
        this.tx_pwr = tx_pwr;
    }

    public String getTrns_intvl() {
        return trns_intvl;
    }
    public void setTrns_intvl(String trns_intvl) {
        this.trns_intvl = trns_intvl;
    }
    public String getDvc_nm() {
        return dvc_nm;
    }

    public void setDvc_nm(String dvc_nm) {
        this.dvc_nm = dvc_nm;
    }

    public String getStrt_wrk() {
        return strt_wrk;
    }

    public void setStrt_wrk(String strt_wrk) {
        this.strt_wrk = strt_wrk;
    }

    public String getEnd_wrk() {
        return end_wrk;
    }

    public void setEnd_wrk(String end_wrk) {
        this.end_wrk = end_wrk;
    }

    public String getMsrd_pwr() {
        return msrd_pwr;
    }

    public void setMsrd_pwr(String msrd_pwr) {
        this.msrd_pwr = msrd_pwr;
    }
}
