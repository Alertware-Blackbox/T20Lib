package activity;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.t20lib.R;
import com.example.t2olibtest.ApplicationConstants.WristbandConstants;
import com.example.t2olibtest.Model.Attributes;
import com.example.t2olibtest.acivities.Datum;

import java.util.Timer;
import java.util.TimerTask;


public class ValueActivity extends AppCompatActivity {

    ActionBar myActionBar=null;
    private Attributes attributes=null;
    private Datum datum=null;
    private TextView tv=null;
    private Button UUID_btn,maj_btn,min_btn,cntct_btn,discntct_btn,dvc_nm_btn,TX_btn,strt_btn,end_btn,ti_btn,bmp_btn,fc_btn=null;
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myActionBar = getSupportActionBar();
        if (myActionBar != null) {
            myActionBar.setDisplayHomeAsUpEnabled(true);
            myActionBar.setTitle("Beacon Data");
        }
        tv=findViewById(R.id.tv);
        UUID_btn=findViewById(R.id.UUID_btn);
        maj_btn=findViewById(R.id.major);
        min_btn=findViewById(R.id.minor);
        cntct_btn=findViewById(R.id.cntct);
        discntct_btn=findViewById(R.id.disCon);
        dvc_nm_btn=findViewById(R.id.dvc_nm);
        TX_btn=findViewById(R.id.TX);
        strt_btn=findViewById(R.id.strt);
        end_btn=findViewById(R.id.end);
        ti_btn=findViewById(R.id.ti);
        bmp_btn=findViewById(R.id.bmp);
        fc_btn=findViewById(R.id.fc);
        attributes = new Attributes();  //1
        datum =new Datum(this,attributes,"84:71:27:21:84:71"); //2
        if (Build.VERSION.SDK_INT < 23) {
            if (Constants.checkAndRequestPermissions(this)) {
                datum.redirect(attributes);  //3
                Timer timer = new Timer();
                TimerTask hourlyTask = new TimerTask() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                      //  if (WristbandConstants.hasCon) {
                        runOnUiThread(() -> {
                            if (datum.get_UUID() != null) {
                                if(myActionBar!=null)
                                    myActionBar.setSubtitle(Html.fromHtml("<small color='#FFBF02'>"+ datum.get_dt_time()+"</small>"));
                                    tv.setText("Connected Status: "+ datum.get_Con_status()
                                        + "\n" +"Gatt Status: " + datum.get_Gatt_status()
                                        + "\n" +"UUID: " + datum.get_UUID()
                                        + "\n" + "Major: " + datum.get_major()
                                        + "\n" + "Minor: " + datum.get_minor()
                                        + "\n" + "RSSI: " + datum.get_rssi()
                                        + "\n" + "Model No.: " + datum.get_dvc_mdl()
                                        + "\n" + "Manufacturer Name: " + datum.get_dvc_manf()
                                        + "\n" + "FW Version: " + datum.get_fw()
                                        + "\n" + "Device Name: " + datum.get_dvc_nm()
                                        + "\n" + "TX Power: " + datum.get_TX()
                                        + "\n" + "Trans Intvl: " + datum.get_Trns_intvl()+" ms."
                                        + "\n" + "Beacon Measured Power: " + datum.get_msr_pwr()+" dBm"
                                        + "\n" + "Strt Wrk Hr.: " + datum.get_Strt_hr()
                                        + "\n" + "End Wrk Hr.: " + datum.get_End_hr()
                                        + "\n" + "System ID.: " + datum.get_sys_id()
                                        + "\n" + "Temperature: " + datum.get_lv_tmp()+" \u2103"
                                        + "\n" + "Humidity: " + datum.get_lv_humd()+" %"
                                        + "\n" + "Battery Level: " + datum.get_battery()+" %");
                            }
                            tv.setTextColor(Color.GRAY);
                        });
                        //}
                    }
                };
                timer.schedule(hourlyTask, 0L, 3000);
            }
        } else {
            if (Constants.checkAndRequestPermissions(this)) {
                datum.redirect(attributes);  //3
                Timer timer = new Timer();
                TimerTask hourlyTask = new TimerTask() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        // if (WristbandConstants.hasCon) {
                        runOnUiThread(() -> {
                            if (datum.get_UUID() != null) {
                                if(myActionBar!=null)
                                   myActionBar.setSubtitle(Html.fromHtml("<small color='#FFBF02'>"+ datum.get_dt_time()+"</small>"));
                                   tv.setText("Connected Status: "+ datum.get_Con_status()
                                           + "\n" +"Gatt Status: " + datum.get_Gatt_status()
                                           + "\n" + "UUID: " + datum.get_UUID()
                                           + "\n" + "Major: " + datum.get_major()
                                           + "\n" + "Minor: " + datum.get_minor()
                                           + "\n" + "RSSI: " + datum.get_rssi()
                                           + "\n" + "Model No.: " + datum.get_dvc_mdl()
                                           + "\n" + "Manufacturer Name: " + datum.get_dvc_manf()
                                           + "\n" + "FW Version: " + datum.get_fw()
                                           + "\n" + "Device Name: " + datum.get_dvc_nm()
                                           + "\n" + "TX Power: " + datum.get_TX()
                                           + "\n" + "Trans Intvl: " + datum.get_Trns_intvl()+" ms."
                                           + "\n" + "Beacon Measured Power: " + datum.get_msr_pwr()+" dBm"
                                           + "\n" + "Strt Wrk Hr.: " + datum.get_Strt_hr()
                                           + "\n" + "End Wrk Hr.: " + datum.get_End_hr()
                                           + "\n" + "System ID.: " + datum.get_sys_id()
                                           + "\n" + "Temperature: " + datum.get_lv_tmp()+" \u2103"
                                           + "\n" + "Humidity: " + datum.get_lv_humd()+" %"
                                           + "\n" + "Battery Level: " + datum.get_battery()+" %");
                            }
                            tv.setTextColor(Color.GRAY);
                        });
                    }
                };
                timer.schedule(hourlyTask, 0L, 3000);
            }
        }

        cntct_btn.setOnClickListener(v -> {
            Toast.makeText(ValueActivity.this, "Connect...", Toast.LENGTH_SHORT).show();
            datum.connect();
        });
        discntct_btn.setOnClickListener(v -> {
            boolean a=datum.disconnect();
            System.out.println("Result--->> "+a);
        });//WristbandConstants.hasCon
        UUID_btn.setOnClickListener(v -> datum.set_UUID("AADDCCDDEEFF22222222224545454566"));
        maj_btn.setOnClickListener(v -> datum.set_major("5000"));
        min_btn.setOnClickListener(v -> datum.set_minor("65535"));
        dvc_nm_btn.setOnClickListener(v -> datum.set_dvc_nm("John Doe."));
        TX_btn.setOnClickListener(v -> datum.set_TX("-4"));
        strt_btn.setOnClickListener(v -> datum.set_strt_hr("0"));
        end_btn.setOnClickListener(v -> datum.set_end_hr("24"));
        ti_btn.setOnClickListener(v -> datum.set_TI("600"));
        bmp_btn.setOnClickListener(v -> datum.set_msrd_pwr("-61"));
        fc_btn.setOnClickListener(v -> {
            datum.fc_rst();
            Toast.makeText(ValueActivity.this, "Factory Reset Status...", Toast.LENGTH_LONG).show();
        });
    }
}