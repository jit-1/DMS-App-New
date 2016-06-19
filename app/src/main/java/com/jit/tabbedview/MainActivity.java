package com.jit.tabbedview;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sanna.cse.disarmlibrary.MyService;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    ///////////////////////
    ///For pSync


    SyncService syncService;
    boolean syncServiceBound = false;

    /////////////////////
    // Grouper
    public static int group_num = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    /////////////////////
    //GPS
    public double[] getGPS() {
        GPSTracker gps = new GPSTracker(this);
        double[] gps1 = new double[2];

        gps1[0] = gps.getLatitude();
        gps1[1] = gps.getLongitude();

        if (gps1[0] == 0 || gps1[1] == 0) {
            gps1[0] = 23.548822;
            gps1[1] = 87.292620;
        }
        return gps1;
    }

    /////////////////////
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    public static String PACKAGE_NAME;
    public void startService(View view) {
        startService(new Intent(getBaseContext(), MyService.class));
    }
    public void startGpsService(View view) {
        startService(new Intent(getBaseContext(), GpsLoggingService.class));
    }
    public void stopGpsService(View view) {
        stopService(new Intent(getBaseContext(), GpsLoggingService.class));
    }

    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ///////////////////////
        ///Localisation
        Locale myLocale = new Locale(PreStartingActivity.langSel);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);


        ///////////////////////
        /// Codes
        ToggleButton togglebtn = (ToggleButton) findViewById(R.id.button);
        ToggleButton psynctgl = (ToggleButton) findViewById(R.id.button2);
        FloatingActionButton fbtn = (FloatingActionButton) findViewById(R.id.fab);
        ToggleButton btnGps = (ToggleButton) findViewById(R.id.button4);
        Button btnMap=(Button)findViewById(R.id.button5);
        btnGps.setOnClickListener(new View.OnClickListener()  {

            @Override
            public void onClick(View v) {
                boolean on = ((ToggleButton) v).isChecked();
                if (on) {


                    startGpsService(v);

                } else {

                    stopGpsService(v);

                }
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener()  {

            @Override
            public void onClick(View V) {

                Toast.makeText(getApplicationContext(), "Loading Offline Map & Data Viewer ", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), ViewMap.class);
                startActivity(i);
            }
        });
        fbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View V) {
                group_num += 1;
                Toast.makeText(getApplicationContext(), "New Group Created: Entering Group: " + String.valueOf(group_num), Toast.LENGTH_SHORT).show();
            }
        });
        togglebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean on = ((ToggleButton) v).isChecked();
                if (on) {

                    startService(v);


                } else {

                    stopService(v);

                }
            }
        });
        psynctgl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                boolean on = ((ToggleButton) v).isChecked();
                if (on){
                    final Intent syncServiceIntent = new Intent(getBaseContext(), SyncService.class);
                    bindService(syncServiceIntent, syncServiceConnection, Context.BIND_AUTO_CREATE);
                    startService(syncServiceIntent);
                    Toast.makeText(getApplicationContext(), "Starting to Sync", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    final Intent syncServiceIntent = new Intent(getBaseContext(), SyncService.class);
                    if(syncServiceBound) {
                        unbindService(syncServiceConnection);
                    }
                    syncServiceBound = false;
                    stopService(syncServiceIntent);
                }
            }
        });


        ////////////////////////
        ///Marshmallow Patch
        PACKAGE_NAME = getApplicationContext().getPackageName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Grant Permission");
                builder.setMessage("Please grant location access and writing setting.");
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

        }


        /////////////////////////


        final int[] ICONS = new int[]{
                R.drawable.health,
                R.drawable.food,
                R.drawable.shelter,
                R.drawable.victim,
        };

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Health").setIcon(ICONS[0]));
        tabLayout.addTab(tabLayout.newTab().setText("Food").setIcon(ICONS[1]));
        tabLayout.addTab(tabLayout.newTab().setText("Shelter").setIcon(ICONS[2]));
        tabLayout.addTab(tabLayout.newTab().setText("Victim").setIcon(ICONS[3]));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.jit.tabbedview/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.jit.tabbedview/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private ServiceConnection syncServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            SyncService.SyncServiceBinder binder = (SyncService.SyncServiceBinder) service;
            syncService = binder.getService();
            syncServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            syncServiceBound = false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
           }

    @Override
    protected void onDestroy(){

        final Intent syncServiceIntent = new Intent(getBaseContext(), SyncService.class);
        if(syncServiceBound) {
            unbindService(syncServiceConnection);
        }
        syncServiceBound = false;
        stopService(syncServiceIntent);
        super.onDestroy();
    }

}