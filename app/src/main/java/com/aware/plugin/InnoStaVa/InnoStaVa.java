package com.aware.plugin.InnoStaVa;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aware.Applications;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.plugin.bluetooth_beacon_detect.*;
import com.aware.plugin.bluetooth_beacon_detect.Provider;
import com.aware.ui.PermissionsHandler;

import java.util.ArrayList;

/**
 * Created by niels on 07/12/2016.
 */

public class InnoStaVa extends AppCompatActivity {

    private TextView device_id;
    private Button join_study, set_settings, sync_data, free_comment;
    int activity = -1;
    String location = "";

    private ArrayList<String> REQUIRED_PERMISSIONS = new ArrayList<>();

    private ActivityLocationReceiver br;
    private class ActivityLocationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(com.aware.plugin.google.activity_recognition.Plugin.ACTION_AWARE_GOOGLE_ACTIVITY_RECOGNITION)) {
                activity = intent.getIntExtra(com.aware.plugin.google.activity_recognition.Plugin.EXTRA_ACTIVITY, -1);
                if (current_activity != null) current_activity.setText("Current activity: " + Utils.getActivityName(intent.getIntExtra(com.aware.plugin.google.activity_recognition.Plugin.EXTRA_ACTIVITY, -1)));
            }
            else if (intent.getAction().equals(com.aware.plugin.bluetooth_beacon_detect.Plugin.ACTION_AWARE_PLUGIN_BT_BEACON_NEAREST)) {
                location = intent.getStringExtra(Provider.NearestBeacon_Data.MAC_ADDRESS);
                if (current_location != null) current_location.setText("Current location: " + intent.getStringExtra(Provider.NearestBeacon_Data.MAC_ADDRESS));
            }
        }
    }

    private Context context;

    TextView current_activity;
    TextView current_location;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.card);
        device_id = (TextView) findViewById(R.id.device_id);
        join_study = (Button) findViewById(R.id.join_study);
        set_settings = (Button) findViewById(R.id.set_settings);
        sync_data = (Button) findViewById(R.id.sync_data);
        free_comment = (Button) findViewById(R.id.add_comment);
        current_activity = (TextView) findViewById(R.id.current_activity);
        current_location = (TextView) findViewById(R.id.current_location);

        br = new ActivityLocationReceiver();
        IntentFilter alfilter = new IntentFilter();
        alfilter.addAction(com.aware.plugin.google.activity_recognition.Plugin.ACTION_AWARE_GOOGLE_ACTIVITY_RECOGNITION);
        alfilter.addAction(com.aware.plugin.bluetooth_beacon_detect.Plugin.ACTION_AWARE_PLUGIN_BT_BEACON_NEAREST);
        registerReceiver(br, alfilter);

        Intent aware = new Intent(this, Aware.class);
        startService(aware);

        Aware.startSignificant(this);

        Intent startPlugin = new Intent(this, Plugin.class);
        startService(startPlugin);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String device_id_string = "UUID : " + Aware.getSetting(this, Aware_Preferences.DEVICE_ID);
        device_id.setText(device_id_string);

        REQUIRED_PERMISSIONS.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        REQUIRED_PERMISSIONS.add(Manifest.permission.ACCESS_WIFI_STATE);
        REQUIRED_PERMISSIONS.add(Manifest.permission.BLUETOOTH);
        REQUIRED_PERMISSIONS.add(Manifest.permission.BLUETOOTH_ADMIN);
        REQUIRED_PERMISSIONS.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        REQUIRED_PERMISSIONS.add(Manifest.permission.ACCESS_FINE_LOCATION);

        boolean permissions_ok = true;
        for (String p : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                permissions_ok = false;
                break;
            }
        }

        if (!permissions_ok) {
            Intent permissions = new Intent(this, PermissionsHandler.class);
            permissions.putExtra(PermissionsHandler.EXTRA_REQUIRED_PERMISSIONS, REQUIRED_PERMISSIONS);
            permissions.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            permissions.putExtra(PermissionsHandler.EXTRA_REDIRECT_ACTIVITY,
                    getPackageName() + "/" + InnoStaVa.class.getName());

            startActivity(permissions);
            finish();
        } else {
            Applications.isAccessibilityServiceActive(getApplicationContext());
            if (!Aware.isStudy(this)) Aware.joinStudy(getApplicationContext(), "https://api.awareframework.com/index.php/webservice/index/989/73JTLkqEcwWZ");
        }

        free_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startFreeCommentOnly = new Intent(context, InnoStaVaESM.class);
                startFreeCommentOnly.putExtra(InnoStaVaESM.FREE_COMMENT_ONLY, true);
                startActivity(startFreeCommentOnly);
            }
        });

        current_activity.setText("Current activity: " + Utils.getActivityName(activity));
        current_location.setText("Current location: " + location);

        if (Aware.isStudy(getApplicationContext())) {
            join_study.setEnabled(false);
            set_settings.setEnabled(false);
        } else {
            sync_data.setVisibility(View.INVISIBLE);
            free_comment.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }
}
