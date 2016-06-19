package com.jit.tabbedview;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PreStartingActivity extends AppCompatActivity {
    public static String langSel;
    public Locale myLocale;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    public static String PACKAGE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ////////////////////////
        ///Marshmallow Patch
        PACKAGE_NAME = getApplicationContext().getPackageName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Grant Permission");
                builder.setMessage("Please grant  coarse location access and writing setting.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    @TargetApi(23)
                    public void onDismiss(DialogInterface dialog) {

                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                        Intent goToSettings = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        goToSettings.setData(Uri.parse("package:" + PACKAGE_NAME));
                        startActivity(goToSettings);

                    }
                });
                builder.show();
            }
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Grant Permission");
                builder.setMessage("Please grant fine location access and writing setting.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    @TargetApi(23)
                    public void onDismiss(DialogInterface dialog) {

                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        Intent goToSettings = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        goToSettings.setData(Uri.parse("package:" + PACKAGE_NAME));
                        startActivity(goToSettings);

                    }
                });
                builder.show();
            }
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Grant Permission");
                builder.setMessage("Please grant external read access setting.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    @TargetApi(23)
                    public void onDismiss(DialogInterface dialog) {

                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        Intent goToSettings = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        goToSettings.setData(Uri.parse("package:" + PACKAGE_NAME));
                        startActivity(goToSettings);

                    }
                });
                builder.show();
            }
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Grant Permission");
                builder.setMessage("Please grant write access setting.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    @TargetApi(23)
                    public void onDismiss(DialogInterface dialog) {

                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        Intent goToSettings = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        goToSettings.setData(Uri.parse("package:" + PACKAGE_NAME));
                        startActivity(goToSettings);

                    }
                });
                builder.show();
            }
            if (this.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Grant Permission");
                builder.setMessage("Please grant camera access setting.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    @TargetApi(23)
                    public void onDismiss(DialogInterface dialog) {

                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                        Intent goToSettings = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        goToSettings.setData(Uri.parse("package:" + PACKAGE_NAME));
                        startActivity(goToSettings);

                    }
                });
                builder.show();
            }
            if (this.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Grant Permission");
                builder.setMessage("Please grant audio access setting.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    @TargetApi(23)
                    public void onDismiss(DialogInterface dialog) {

                        requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                        Intent goToSettings = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        goToSettings.setData(Uri.parse("package:" + PACKAGE_NAME));
                        startActivity(goToSettings);

                    }
                });
                builder.show();
            }
            if (this.checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Grant Permission");
                builder.setMessage("Please grant wifi access setting.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    @TargetApi(23)
                    public void onDismiss(DialogInterface dialog) {

                        requestPermissions(new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 1);
                        Intent goToSettings = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        goToSettings.setData(Uri.parse("package:" + PACKAGE_NAME));
                        startActivity(goToSettings);

                    }
                });
                builder.show();

            }
            if (this.checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Grant Permission");
                builder.setMessage("Please grant wifi change setting.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    @TargetApi(23)
                    public void onDismiss(DialogInterface dialog) {

                        requestPermissions(new String[]{Manifest.permission.CHANGE_WIFI_STATE}, 1);
                        Intent goToSettings = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        goToSettings.setData(Uri.parse("package:" + PACKAGE_NAME));
                        startActivity(goToSettings);

                    }
                });
                builder.show();

            }
            if (this.checkSelfPermission(Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Grant Permission");
                builder.setMessage("Please grant write setting.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    @TargetApi(23)
                    public void onDismiss(DialogInterface dialog) {

                        requestPermissions(new String[]{Manifest.permission.WRITE_SETTINGS}, 1);
                        Intent goToSettings = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        goToSettings.setData(Uri.parse("package:" + PACKAGE_NAME));
                        startActivity(goToSettings);

                    }
                });
                builder.show();

            }
        }


        /////////////////////////






        setContentView(R.layout.activity_pre_starting);
        String[] langArray = getResources().getStringArray(R.array.lang_list);
        List<String> lang = new ArrayList<String>(Arrays.asList(langArray));
        Button enter=(Button)findViewById(R.id.button3);

        final Spinner spinner2 = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, lang);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                langSel = parent.getItemAtPosition(pos).toString();
                if (pos == 0) {

                    Toast.makeText(parent.getContext(),
                            "You have selected English", Toast.LENGTH_SHORT)
                            .show();
                    langSel = "en";
                    setLocale("en");
                } else if (pos == 1) {

                    Toast.makeText(parent.getContext(),
                            "You have selected Bengali", Toast.LENGTH_SHORT)
                            .show();
                    langSel = "bn";
                    setLocale("bn");
                } else if (pos == 2) {

                    Toast.makeText(parent.getContext(),
                            "You have selected Hindi", Toast.LENGTH_SHORT)
                            .show();
                    langSel = "hi";
                    setLocale("hi");
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        enter.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(), StartingActivity.class);
                startActivity(i);
            }
        });

    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        //Intent refresh = new Intent(this, StartingActivity.class);
        //startActivity(refresh);
    }

}
