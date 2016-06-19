package com.jit.tabbedview;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class StartingActivity extends Activity implements OnClickListener {

    boolean mExternalStorageAvailable = false;
    boolean mExternalStorageWriteable = false;
    static String Path, Path1;
    private File myDir;
    static int Flag = 0;
    public static String langSel;
    public Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
            String root = Environment.getExternalStorageDirectory().toString();
            Path1 = root + "/" + "DMS";
            Path = root + "/" + "DMS" + "/" + "Working";
            myDir = new File(Path);
            boolean tin = myDir.mkdir();
            if (tin)

            {
                Toast.makeText(getApplicationContext(), " created in " + root, Toast.LENGTH_SHORT).show();
                //Creating Disaster File in the above directory

            }

            if (!myDir.exists()) {
                myDir.mkdirs();
            }

        }

        Button createDms = (Button) findViewById(R.id.btnNext);
        createDms.setOnClickListener(this);


        Button exit1 = (Button) findViewById(R.id.exit1);
        exit1.setOnClickListener(this);
        File f = new File(Path1 + "/source.txt");
        if (f.exists()) {
            Flag = 1;
            Toast.makeText(this, "Source Present", Toast.LENGTH_SHORT).show();
        } else {
            Flag = 0;
            Toast.makeText(this, "No Source:Registering", Toast.LENGTH_SHORT).show();
        }
        /*
        String[] langArray = getResources().getStringArray(R.array.lang_list);
        List<String> lang = new ArrayList<String>(Arrays.asList(langArray));


        final Spinner spinner2 = (Spinner)findViewById(R.id.spinnerLang);
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
                    langSel="en";
                    setLocale("en");
                } else if (pos == 1) {

                    Toast.makeText(parent.getContext(),
                            "You have selected Bengali", Toast.LENGTH_SHORT)
                            .show();
                    langSel="bn";
                    setLocale("bn");
                } else if (pos == 2) {

                    Toast.makeText(parent.getContext(),
                            "You have selected Hindi", Toast.LENGTH_SHORT)
                            .show();
                    langSel="hi";
                    setLocale("hi");
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        */




    }
/*
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
*/
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        File f = new File(Path1 + "/source.txt");
        if (f.exists()) {
            Flag = 1;
            Toast.makeText(this, "Source Present", Toast.LENGTH_SHORT).show();
        } else {
            Flag = 0;
            Toast.makeText(this, "No Source:Registering", Toast.LENGTH_SHORT).show();
        }


    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.btnNext && Flag == 0) {
            Log.d("CreateDms", "Before");
            startActivity(new Intent(this, RegistrationActivity.class));
            Log.d("CreateDms", "After");
        } else if (v.getId() == R.id.btnNext && Flag == 1) {
            Intent i = new Intent(this, List_Activity.class);
            startActivity(i);
        } else if (v.getId() == R.id.exit1) {
            System.exit(0);
        }


    }

}