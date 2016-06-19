package com.jit.tabbedview;

import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class RegistrationActivity extends AppCompatActivity {
    public static final int FLAG_ACTIVITY_CLEAR_TOP=67108864;
    private Button create;
    private EditText dmsName,editSource;
    String disasterName;
    static String source;
    static String fileName;//file where data is stored
    static String DirName;//Directory where data is stored
    Editable source1;
    static String DisasterName,disasterFileName;
    static String dirPath;
    public static int Flag=0;
    String root = Environment.getExternalStorageDirectory().toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editSource=(EditText)findViewById(R.id.EdtSource);
        create=(Button)findViewById(R.id.create);
        String txt1=null;

        File tfile=new File(StartingActivity.Path,"source.txt");
        if(tfile.exists())
        {
            BufferedReader buf1;
            try {
                buf1 = new BufferedReader(new FileReader(tfile));
                txt1=buf1.readLine();
                buf1.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //  txt1=txt1.substring(3, 12);
            //  editSource.setText(txt1);
        }

        create.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          //File existense=new File(CreateDms.Path1,"1.txt");

                                          try {
                                              source = (String) editSource.getText().toString();
                                              if (source.length() != 10) {
                                                  Toast.makeText(getApplicationContext(), "Enter a proper 10 Digit Phone Number", Toast.LENGTH_SHORT).show();

                                              } else {

                                                  //if(!existense.exists()){

                                                  File sfile = new File(root + "/DMS", "source.txt");
                                                  if (sfile.createNewFile()) ;
                                                  BufferedWriter buf = new BufferedWriter(new FileWriter(sfile, true));
                                                  buf.append("me:" + source);
                                                  buf.newLine();
                                                  buf.close();
                                                  //	}
                                                  //	File flag=new File(CreateDms.Path1,"1.txt");
                                                  //	flag.createNewFile();
                                                  newdms();
                                              }

                                          } catch (IOException e) {
                                              // TODO Auto-generated catch block
                                              e.printStackTrace();
                                          }


                                      }
                                  }
        );

        Toast.makeText(this, "done",Toast.LENGTH_SHORT);

        Button cancel=(Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          createdms();//back to the main page

                                      }
                                  }
        );
    }
    public void newdms() throws IOException
    {
        File myDir,Disaster;


        //Editable dms_Name=dmsName.getText();
        source1=editSource.getText();
        source=(String)source1.toString();




        String disastername="Disaster Name: "+disasterName+"\n";
        File sfile=new File(root+"/DMS","source.txt");
        if(sfile.createNewFile());
        BufferedWriter buf = new BufferedWriter(new FileWriter(sfile, true));
        buf.append(disastername);
        buf.newLine();
        buf.close();
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {

            String root = Environment.getExternalStorageDirectory().toString();

            myDir =new File(StartingActivity.Path);
            boolean tin=myDir.mkdir();

            if (tin)

            {




                disasterFileName= disasterName+".txt";
                Disaster=new File(dirPath,disasterFileName);
                if(Disaster.createNewFile())
                {
                    Toast.makeText(getApplicationContext(),"File Created" , Toast.LENGTH_SHORT).show();



                }

                else
                    Toast.makeText(getApplicationContext(),"Not Created" , Toast.LENGTH_SHORT).show();
            }


        }
        else
        {
            //Internal Storage
            File dir=getDir(DirName, MODE_WORLD_READABLE);
            fileName=DirName+"/"+disasterName;
            Disaster=new File(fileName);
            Disaster.createNewFile();

        }


        Intent i = new Intent(this,List_Activity.class);
        startActivity(i);
    }
    public void createdms()
    {


        Intent i = new Intent(this,StartingActivity.class);
        i.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
