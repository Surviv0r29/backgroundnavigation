package com.example.backgroundlocationservice;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public TextView number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // startService(new Intent(this,MyService.class));
        //number=findViewById(R.id.number);
     /*   stopService(new Intent(this,MyService.class));
        Toast.makeText(this, "Application ended" , Toast.LENGTH_SHORT).show();*/
     permission();
     //stopService(new Intent(MainActivity.this,MyService.class));
     //   Toast.makeText(this, "come", Toast.LENGTH_SHORT).show();
    }

    private void permission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        String rationale = "Please provide location permission ";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("warning");

        Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
                startService(new Intent(MainActivity.this,MyService.class));
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                permission();
            }
        });
    }
}
