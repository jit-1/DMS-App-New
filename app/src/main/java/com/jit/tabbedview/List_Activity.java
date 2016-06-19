package com.jit.tabbedview;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class List_Activity extends ListActivity {

    public static final int FLAG_ACTIVITY_CLEAR_TOP=67108864;
    String root = Environment.getExternalStorageDirectory().toString();
    static String mediaDir;
    private String DirectoryPath= root+"/DMS/Working";
    File f = new File(DirectoryPath);
    private boolean p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] list_arr = getResources().getStringArray(R.array.list_array);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_arr));
        ListView lv=getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

                String itemName = (String) ((TextView) view).getText();

                detectItem(itemName);
            }
        })
        ;
    }


    protected void detectItem(String itemName)
    {

        if (itemName.equals("Delete Source"))
        {
            final Intent i = new Intent(this, RegistrationActivity.class);


            final File del1=new File(StartingActivity.Path1+"/source.txt");
            StartingActivity.Flag=0;
            if(del1.exists())
            {

                new AlertDialog.Builder(this)
                        .setTitle("Delete Source")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                del1.delete();
                                Toast.makeText(getApplicationContext(),"Source Deleted", Toast.LENGTH_SHORT).show();
                                startActivity(i);


                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();








            }
        }

        else if(itemName.equals("Data Collection"))
            attachAudioVideo();

        else if(itemName.equals("Delete All Previous Content"))
        {
            final Intent i = new Intent(this, RegistrationActivity.class);
            new AlertDialog.Builder(this)
                    .setTitle("Delete previous Contents")
                    .setMessage("Are you sure you want to delete this entry?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            DirectoryPath = root + "/DMS/Working";
                            File f = new File(DirectoryPath);
                            if (!f.exists()) {
                                Toast.makeText(getApplicationContext(), "Folder not found", Toast.LENGTH_SHORT).show();
                            }
                            p = deleteDirectory(f);
                            File s1 = new File(root + "/DMS/source.txt");
                            // File s2 = new File(root + "/DMS/1.txt");
                            if (s1.exists())
                                s1.delete();
                            f.mkdir();
                            Log.d("Clearing Old Files", DirectoryPath);

                            startActivity(i);


                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else if(itemName.equals("Ping")){

            //final Intent i = new Intent(this, Ping.class);
            // startActivity(i);




        }
        else{

        }


    }

    public boolean deleteDirectory(File path) {

        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {

                return true;
            }
            else {
                Toast.makeText(getApplicationContext(), "Deleting old Files", Toast.LENGTH_SHORT).show();

                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    } else {

                        files[i].delete();

                        Toast.makeText(getApplicationContext(), "Deleting old Files", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        //  Log.d("File Deletion",path.toString());
        return (path.delete());
    }


    protected void attachAudioVideo()
    {
        mediaDir=RegistrationActivity.dirPath+"/Media";
        File createMedia=new File(mediaDir);

        if(!createMedia.exists())
            createMedia.mkdir();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.main_menu:
                exit();
                return true;
            case R.id.exit:
                System.exit(1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void exit()
    {
        Intent i = new Intent(this, PreStartingActivity.class);
        startActivity(i);

    }
}
