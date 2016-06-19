package com.jit.tabbedview;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class TabFragment4 extends Fragment {



    //////////////////
    //Initialization
    private String fpass,nfpass,trans_tempo="";
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private String timeStamp=null;
    static String ttl1=null;
    static String ttl;
    private File mediaFile;
    private Uri fileUri;
    static String destination=null;
    private static String imageDir,videoDir,audioDir;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private File createImages,createVideos,createAudios;
    EditText editTtl,editDestination;
    static String root = Environment.getExternalStorageDirectory().toString();
    private static String DirectoryPath= root+"/DMS/source.txt";
    static File f = new File(DirectoryPath);
    static File f1=new File(root+"/DMS/Working");
    private boolean p;
    static String line;
    private static String source=null;
    static Timer timer;
    static TimerTask timerTask;
    public static String lat_long;
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();
    public String group=String.valueOf(MainActivity.group_num);
    ///
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    public static String PACKAGE_NAME;
    View View_item=null;

    String eng_trans;
    /////////////////////
    //GPS
    public double[] getGPS() {
        GPSTracker gps=new GPSTracker(getActivity());
        double[] gps1 = new double[2];

        gps1[0] = gps.getLatitude();
        gps1[1] = gps.getLongitude();

        if (gps1[0]==0 ||gps1[1]==0)
        {
            gps1[0]=23.548822;
            gps1[1]=87.292620;
        }
        return gps1;
    }

    /////////////////////
    //Source
    private static String source1()
    {
        try {
            if(f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(DirectoryPath));
                Log.d("AttachAud/Vid", DirectoryPath);
                if (br.ready()) {
                    line=br.readLine().substring(3,13);
                }
                br.close();
            }
        }catch(Exception e){
            Log.d("Error in Aud/Vid Source", e.toString());
        }
        Log.d("AttachAud/Vid",line);
        return line;
    }
    /////////////////////
    //URI
    private Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /////////////////////
    //MediaFileCreation
    private File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.


        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){

            //Latitude

            Random rand = new Random();
            int i=10;
            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
		/*	String latlong1 = "";

			while(i!=0){
				int randomNum = rand.nextInt((9999 - 0) + 1) + 0;
				int randomNum2=rand.nextInt((9999-0)+1)+0;
				latlong1="23.5488"+randomNum+"_87.2926"+randomNum2;
				i-=1;
			}

		*/
//
            double as[]=new double[2];
            as=getGPS();

            //Changed 12.03.2015
            String latlong1;

            latlong1 = Double.toString(as[0]) + "_" + Double.toString(as[1]);
            group=String.valueOf(MainActivity.group_num);
            //
            mediaFile = new File(imageDir,"IMG_"+ttl+"_"+source+"_"+destination+"_"+latlong1+"_"+timeStamp+"_"+group+".jpg.tmp");
            // Imp:
            //This location works best if you want the created images to be shared
            // between applications and persist after your app has been uninstalled.

            // Create the storage directory if it does not exist
		    /*if (! mediaFile.exists()){
		        try {
					if (! mediaFile.reateNewFile()){
					    Log.d("MyCameraApp", "failed to save Image");
					    return null;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }*/


        } else if(type == MEDIA_TYPE_VIDEO) {

            Random rand = new Random();
            int i=10;
            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
		/*	String latlong1 = "";

			while(i!=0){
				int randomNum = rand.nextInt((9999 - 0) + 1) + 0;
				int randomNum2=rand.nextInt((9999-0)+1)+0;
				latlong1="23.5488"+randomNum+"_87.2926"+randomNum2;
				i-=1;
			}
		*/
            double as[]=new double[2];
            as=getGPS();
            //Changed 12.03.2015
            String latlong1;

            latlong1 = Double.toString(as[0]) + "_" + Double.toString(as[1]);

            //
            group=String.valueOf(MainActivity.group_num);
            mediaFile = new File(videoDir,"VID_"+ttl+"_"+source+"_"+destination+"_"+latlong1+"_"+ timeStamp + "_"+group+".3gp.tmp");
        } else {
            return null;
        }

        return mediaFile;
    }
    /////////////////////
    //Rename
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            try{
                if (resultCode == Activity.RESULT_OK) {

                    // Image captured and saved to fileUri specified in the Intent
//	            Toast.makeText(this, "Image saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
                    //Start Image
                    //String outputFile=mediaFile.getAbsolutePath().toString();

                    String outputFile=fileUri.getPath();
                    Log.v("fileUri",outputFile);
                    File from=new File(outputFile);
                    String outputFile_omit_tmp=outputFile.substring(0,outputFile.length()-4);
                    Log.v("Output init", outputFile);
                    Log.v("OutputFileName", outputFile_omit_tmp);

                    File to=new File(outputFile_omit_tmp);
                    try {
                        if (from.exists())
                            if (from.renameTo(to))
                                Log.v("Rename", "Success");
                            else
                                Log.v("Rename","Failed");
                    }
                    catch(Exception e){
                        Log.e("UniqException", "Exception: "+Log.getStackTraceString(e));
                    }
                    //End





                } else if (resultCode == Activity.RESULT_CANCELED) {

                    // User cancelled the image capture
                    Toast.makeText(getActivity(), "Saving of Image is Cancelled",Toast.LENGTH_LONG).show();


                } else {
                    // Image capture failed, advise user
                    Toast.makeText(getActivity(), "Image Saving Failed",Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            try{
                if (resultCode == Activity.RESULT_OK) {
                    // Video captured and saved to fileUri specified in the Intent

                    Toast.makeText(getActivity(), "Video saved to:\n" +data.getData(), Toast.LENGTH_LONG).show();

                    //Start Video
                    //String outputFile=mediaFile.getAbsolutePath().toString();
                    String outputFile=fileUri.getPath();
                    Log.d("fileUri",outputFile);
                    //File from=new File(outputFile.substring(7,outputFile.length()));
                    File from=new File(outputFile);
                    String outputFile_omit_tmp=outputFile.substring(0, outputFile.length() - 4);
                    Log.v("Output init", outputFile);
                    Log.v("OutputFileName", outputFile_omit_tmp);

                    File to=new File(outputFile_omit_tmp);
                    try {
                        if (from.exists())
                            if (from.renameTo(to))
                                Log.v("Rename", "Success");
                            else
                                Log.v("Rename","Failed");
                    }
                    catch(Exception e){
                        Log.e("UniqException", "Exception: "+Log.getStackTraceString(e));
                    }
                    //End



                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // User cancelled the video capture
                } else {
                    // Video capture failed, advise user
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }



    }



    /////////////////////
    //Video & Image Function
    public void captureVideo()
    {
        //	videoDir=ListPage.mediaDir;
        //	createVideos=new File(videoDir);
        videoDir=StartingActivity.Path;
        createVideos=new File( StartingActivity.Path);
        if (!createVideos.exists())
            createVideos.mkdir();

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);  // create a file to save the video
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // set the video image quality to high

        // start the Video Capture Intent
        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);


    }
    public void captureImage()

    {

        //	imageDir=ListPage.mediaDir;

        imageDir=StartingActivity.Path;

        //	createImages=new File(imageDir);
        createImages=new File( StartingActivity.Path);
        if (!createImages.exists())
            createImages.mkdir();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);// create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    /////////////////
    //Get FileName Audio
    private String getFilenameAud() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(root + "/DMS/Working");
        if (!file.exists()) {
            file.mkdirs();
        }
        //    final double latitude = location.getLatitude();
        //    final double longitude = location.getLongitude();

        //    Log.v("Latitude",Double.toString(latitude));
        //    Log.v("Longitude",Double.toString(longitude));

        source = source1();
        ttl=editTtl.getText().toString();
        destination=editDestination.getText().toString();

        if (source.isEmpty()) {
            source = "defaultMCS";
        }
//Latitude
        //   LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //   Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //   double longitude = location.getLongitude();
        //   double latitude = location.getLatitude();

        Random rand = new Random();
        int i=10;
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
    /*    String latlong1 = "";


        while(i!=0){
            int randomNum = rand.nextInt((9999 - 0) + 1) + 0;
            int randomNum2=rand.nextInt((9999-0)+1)+0;
            latlong1="23.5488"+randomNum+"_87.2926"+randomNum2;
            i-=1;
        }
    */
//
        double as[]=new double[2];
        as=getGPS();
        String latlong1=Double.toString(as[0])+"_"+Double.toString(as[1]);
//
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //  return (file.getAbsolutePath() + "/"+"AUD_"+ timeStamp+".3gp");
        return (file.getAbsolutePath() + "/" + "SVS_" + ttl + "_" + source + "_" + destination + "_"+latlong1+"_" + timeStamp + "_"+group+".3gp.tmp");
    }

    /////////////////
    ///Get Filename Structured Text
    private String getFilename(){
        String filepath = Environment.getExternalStorageDirectory().getPath();
        //File file = new File(filepath,AUDIO_RECORDER_FOLDER);
        //   File file = new File(AUDIO_RECORDER_FOLDER);
        source=source1();

        File file = new File( root+"/DMS/Working");
        try{

            //testing
            //   Toast.makeText(getApplicationContext(), com.jit.pdm.dmstool.AttachAudioVideo.ttl+"  "+com.jit.pdm.dmstool.AttachAudioVideo.destination+"Display", Toast.LENGTH_LONG).show();

            if(!file.exists()){
                file.mkdirs();
            }


            ttl=editTtl.getText().toString();
            destination=editDestination.getText().toString();

            if(ttl.isEmpty())
            {
                ttl="50";					// default 50 mins
                Toast.makeText(getActivity(), "Default", Toast.LENGTH_LONG).show();

            }

            if(destination.isEmpty())
            {
                destination= "defaultMCS";		// default destination is defaultMCS
            }
        }catch(Exception e){}
//Latitude
        //    LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //    double longitude = location.getLongitude();
        //    double latitude = location.getLatitude();

        //
/*
        Random rand = new Random();
        int i=10;
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        String latlong1 = "";

        while(i!=0){
            int randomNum = rand.nextInt((9999 - 0) + 1) + 0;
            int randomNum2=rand.nextInt((9999-0)+1)+0;
            latlong1="23.5488"+randomNum+"_87.2926"+randomNum2;
            i-=1;
        }

*/
        //
        double as[]=new double[2];
        as=getGPS();
        String latlong1=Double.toString(as[0])+"_"+Double.toString(as[1]);
//
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //  return (file.getAbsolutePath() + "/"+"AUD_"+ timeStamp+".3gp");
        group=String.valueOf(MainActivity.group_num);
        return (file.getAbsolutePath() + "/"+"TXT_"+ttl+"_"+ source +"_"+ destination +"_"+latlong1+"_" + timeStamp+"_"+group +".txt");
        // added latitude,logitude
    }



    /////////////////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        ///////////////////////
        ///Localisation
        Locale myLocale=new Locale(PreStartingActivity.langSel);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);


        /////////////////////
        //Code
        ToggleButton audio=(ToggleButton) view.findViewById(R.id.imageButton);
        ImageButton camera=(ImageButton)view.findViewById(R.id.imageButton2);
        ImageButton vid=(ImageButton)view.findViewById(R.id.imageButton3);
        Button svbtn = (Button) view.findViewById(R.id.save);
        final TextView tt = (TextView) view.findViewById(R.id.op);
        Button addbtn = (Button) view.findViewById(R.id.add);
        EditText ed = (EditText)view.findViewById(R.id.item4);
        source=source1();
        /////////////////////
        //Spinner

        String[] victimArray = getResources().getStringArray(R.array.victim_array);
        final String[] victimArray_trans = getResources().getStringArray(R.array.victim_array1);

        List<String> victim = new ArrayList<String>(Arrays.asList(victimArray));


        final Spinner spinner2 = (Spinner) view.findViewById(R.id.item2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item, victim);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if (initialDisplay2 = false){
                String pass2 = parent.getItemAtPosition(position).toString();
                fpass = "Victim" + ": " + pass2 + ": ";
                eng_trans="Victim"+": " +victimArray_trans[position]+": ";
                //itemEntry3(fpass);
                // }
                // initialDisplay2 = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        String[] numArray = getResources().getStringArray(R.array.num_array);
        final String[] numArray_trans = getResources().getStringArray(R.array.num_array1);
        List<String> num = new ArrayList<String>(Arrays.asList(numArray));
        final Spinner spinner3 = (Spinner) view.findViewById(R.id.item3);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item, num);
        adapter1.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter1);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pass3 = parent.getItemAtPosition(position).toString();
                String trans_pass3=numArray_trans[position];
                String entry3;

                if (pass3.equals("Custom")) {
                    // citemEntry4(fpass);
                    //
                } else {
                    entry3 = pass3;
                    nfpass = fpass + entry3;
                    eng_trans=eng_trans+trans_pass3;
                    //  itemEntry4(finalpass + "\n");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ////////////////////
        //Struct Text Add & Save
        addbtn.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {
                                          String tempo = (String) tt.getText();
                                          tempo = tempo + nfpass+"\n";
                                          trans_tempo= trans_tempo + eng_trans + "\n";
                                          tt.setText(tempo);
                                          //fentry(tempo);
                                      }
                                  }

        );
        svbtn.setOnClickListener(new View.OnClickListener() {

                                     @Override
                                     public void onClick(View v) {


                                         try {
                                             System.out.println(fpass);
                                             File directory = Environment.getExternalStorageDirectory();
                                             outputFile=getFilename();
                                             File file = new File(outputFile);
                                             // If file does not exists, then create it
                                             if (!file.exists()) {
                                                 file.createNewFile();
                                             }
                                             FileWriter fw = new FileWriter(file. getAbsoluteFile());
                                             BufferedWriter bw = new BufferedWriter(fw);
                                             bw.write(trans_tempo);
                                             trans_tempo="";
                                             bw.close();
                                             Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
                                             Log.d("Success", "Success");
                                             tt.setText("");
                                             fpass="";


                                         } catch (IOException e) {
                                             e.printStackTrace();

                                         }
                                     }
                                 }

        );


        /////////////////////
        editTtl=(EditText)view.findViewById(R.id.editText1);
        editDestination=(EditText)view.findViewById(R.id.editText2);



        //////////////////////
        // Vid
        vid.setOnClickListener(new View.OnClickListener(){


            public void onClick(View v) {

                ttl1=(String)editTtl.getText().toString();

                //
                //lat_long=latitude_longitude();

                //
                if(ttl1.isEmpty())
                    ttl="50";					// default 50 mins
                else
                    ttl=(ttl1);
                destination=(String)editDestination.getText().toString();
                if(destination.isEmpty())
                    destination="defaultMCS";		// default destination is default
                if(source.isEmpty())
                    source="defaultMCS";

                captureVideo();
            }
        });
        //////////////////
        //Image
        camera.setOnClickListener(new View.OnClickListener(){


            public void onClick(View v) {

                ttl1=(String)editTtl.getText().toString();
                //

                //	lat_long=latitude_longitude();
                //
                if(ttl1.isEmpty())
                    ttl="50";					// default 50 mins
                else
                    ttl=(ttl1);
                destination=(String)editDestination.getText().toString();
                if(destination.isEmpty())
                    destination="defaultMCS";		// default destination is default
                if(source.isEmpty())
                    source="defaultMCS";

                captureImage();

            }
        });
        /////////////////
        //Audio
        audio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean on = ((ToggleButton) v).isChecked();
                if (on) {
                    outputFile = getFilenameAud();
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    myAudioRecorder = new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    myAudioRecorder.setOutputFile(outputFile);
                    // myAudioRecorder.setOutputFile(getFilename());


                    ///
                    try {
                        myAudioRecorder.prepare();
                        myAudioRecorder.start();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), "Recording started", Toast.LENGTH_LONG).show();


                } else {
                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                    myAudioRecorder = null;
                    // play.setEnabled(true);
                    Toast.makeText(getActivity(), "Audio recorded successfully",
                            Toast.LENGTH_SHORT).show();

                    //Start
                    File from=new File(outputFile);
                    String outputFile_omit_tmp=outputFile.substring(0,outputFile.length()-4);
                    Log.v("Output init", outputFile);
                    Log.v("OutputFileName", outputFile_omit_tmp);

                    File to=new File(outputFile_omit_tmp);
                    try {
                        if (from.exists())
                            if (from.renameTo(to))
                                Log.v("Rename", "Success");
                            else
                                Log.v("Rename","Failed");
                    }
                    catch(Exception e){
                        Log.e("UniqException", "Exception: "+Log.getStackTraceString(e));
                    }

                }
            }
        });

        return view;

    }

}
