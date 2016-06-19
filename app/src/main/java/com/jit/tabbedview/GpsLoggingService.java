package com.jit.tabbedview;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GpsLoggingService extends Service {

    private int mInterval = 500; // 500 ms by default

    public GPSTracker gpstracker;
    Thread background;
    private Handler mHandler;
    File f;
    String root = Environment.getExternalStorageDirectory().toString();

    //
    private LocationManager locationManager;
    private String locationProvider;
    private PowerManager.WakeLock wakeLock;


    //






    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("GPSService", String.valueOf(gpstracker.getLatitude())+" "+String.valueOf(gpstracker.getLongitude()));
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelockTag");
        wakeLock.acquire();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
      //  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, this);


        f = new File(root + "/DMS", "GpsLog.txt");
        try {
            if (!f.exists())
                f.createNewFile();

        }catch(Exception e){
            Log.d("Error in File GPS",e.toString());
        }
        mHandler = new Handler();
        startRepeatingTask();


        return START_STICKY;
    }
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                try{
                    if(f.exists()){
                        Log.d("GPSService", String.valueOf(gpstracker.getLatitude())+" "+String.valueOf(gpstracker.getLongitude()));
                        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                        BufferedWriter buf = new BufferedWriter(new FileWriter(f, true));
                        buf.append(String.valueOf(gpstracker.getLatitude())+","+String.valueOf(gpstracker.getLongitude())+","+timeStamp);
                        buf.newLine();
                        buf.close();
                    }
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Error in GPS Logging", Toast.LENGTH_SHORT).show();

                }


            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    public GpsLoggingService() {





    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mStatusChecker);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        gpstracker=new GPSTracker(getApplicationContext());
        Toast.makeText(this,"IN GPSLogger",Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
