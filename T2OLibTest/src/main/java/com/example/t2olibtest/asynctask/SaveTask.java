package com.example.t2olibtest.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.t2olibtest.ApplicationConstants.WristbandConstants;
import com.example.t2olibtest.ble.Bluetooth;
import com.example.t2olibtest.pref.WristbandSharePref;

import static com.example.t2olibtest.ApplicationConstants.WristbandConstants.addspace;
import static com.example.t2olibtest.ApplicationConstants.WristbandConstants.dyn_srvc;
import static com.example.t2olibtest.ApplicationConstants.WristbandConstants.hexStringToByteArray;
import static com.example.t2olibtest.ApplicationConstants.WristbandConstants.hexToByteArray;

public class SaveTask extends AsyncTask<Void, Void, Void> {
    Activity mContext;
    Bluetooth bluetooth;
    String srvc_typ;
    String update_val;
    public SaveTask(Activity mContext, Bluetooth bluetooth, String srvc_typ, String update_val) {
        this.mContext=mContext;
        this.bluetooth=bluetooth;
        this.srvc_typ=srvc_typ;
        this.update_val=update_val;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        bluetooth.initialize();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        bluetooth.connect(WristbandSharePref.getPairedMac(
                mContext));
        try {
            Thread.sleep(3000);
            try {
                mContext.runOnUiThread(() -> {
                    String[] val = dyn_srvc(srvc_typ).split(":");
                    if(val[0].equals("NA")||val[1].equals("NA"))
                    {

                    }
                    else
                    {
                        if(srvc_typ.equals(WristbandConstants.UUID_chng_srvc)) {
                            bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                    hexStringToByteArray(update_val));
                        }
                        else if(srvc_typ.equals(WristbandConstants.major_updt_srvc)) {
                            String deci_to_hex=Integer.toHexString(Integer.parseInt(update_val));
                            if(deci_to_hex.length()==1)
                                bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                        hexStringToByteArray("000" +  deci_to_hex));
                            else if(deci_to_hex.length()==2)
                                bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                        hexStringToByteArray("00" +  deci_to_hex));
                            else if(deci_to_hex.length()==3)
                                bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                        hexStringToByteArray("0" +  deci_to_hex));
                            else if(deci_to_hex.length()==4)
                            {
                                String val_1=deci_to_hex.substring(0,2);
                                String val_2=deci_to_hex.substring(2,4);
                                String ori_val=val_1+val_2;
                                bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                        hexStringToByteArray(ori_val));
                            }
                        }
                        else if(srvc_typ.equals(WristbandConstants.minor_updt_srvc)) {
                            String deci_to_hex=Integer.toHexString(Integer.parseInt(update_val));
                            if(deci_to_hex.length()==1)
                                bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                        hexStringToByteArray("000" +  deci_to_hex));
                            else if(deci_to_hex.length()==2)
                                bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                        hexStringToByteArray("00" +  deci_to_hex));
                            else if(deci_to_hex.length()==3)
                                bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                        hexStringToByteArray("0" +  deci_to_hex));
                            else if(deci_to_hex.length()==4)
                            {
                                String val_1=deci_to_hex.substring(0,2);
                                String val_2=deci_to_hex.substring(2,4);
                                String ori_val=val_1+val_2;
                                bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                        hexStringToByteArray(ori_val));
                            }
                        }
                        else if(srvc_typ.equals(WristbandConstants.Dvc_srvc)) {
                               String edited_value=update_val;
                               String nm=addspace((16-edited_value.length()),edited_value);
                               bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                  nm.getBytes());
                        }
                        else if(srvc_typ.equals(WristbandConstants.wrk_strt_srvc)) {
                            String edited_value=update_val;
                            bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                   hexToByteArray(Integer.toHexString(Integer.parseInt(edited_value))));
                        }
                        else if(srvc_typ.equals(WristbandConstants.wrk_end_srvc)) {
                            String edited_value=update_val;
                            bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                    hexToByteArray(Integer.toHexString(Integer.parseInt(edited_value))));
                        }
                        else if(srvc_typ.equals(WristbandConstants.TX_Power_srvc)) {
                            String edited_value=update_val;
                            if(Integer.parseInt(edited_value)!=0)
                            {
                                String value =Integer.toHexString(Integer.parseInt(edited_value)).substring(Integer.toHexString(Integer.parseInt(edited_value)).length() - 2);
                                bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                        hexStringToByteArray(value));
                            }
                            else
                                bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                        hexStringToByteArray("00"));
                        }
                        else if(srvc_typ.equals(WristbandConstants.Trans_Intv_srvc)) {
                             int converted_selected_value = Integer.parseInt(update_val) / 100;
                                 if (String.valueOf(converted_selected_value).length() == 1) {
                                    bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                            hexStringToByteArray("0" + converted_selected_value));
                                    }
                                 else
                                       bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                               hexStringToByteArray("0" + Integer.toHexString(converted_selected_value)));
                        }//WristbandConstants.Fc_rst_srvc
                        else if(srvc_typ.equals(WristbandConstants.Beacon_measured_pwr_srvc)) {
                             int getVal=Integer.parseInt(update_val);
                                if(getVal!=0) {
                                        String value = Integer.toHexString(getVal).substring(Integer.toHexString(Integer.parseInt(update_val)).length() - 2);
                                           bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                                hexStringToByteArray(value));
                                           }
                                 else
                                        bluetooth.writeDataToCharacteristic(bluetooth.getGatt().getServices().get(Integer.parseInt(val[0])).getCharacteristics().get(Integer.parseInt(val[1])),
                                               hexStringToByteArray("00"));
                        }
                    }
                });
            }
            catch(StringIndexOutOfBoundsException e){}

        } catch (Exception e) {
            System.out.println("AAAAAAAA--->> "+e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
