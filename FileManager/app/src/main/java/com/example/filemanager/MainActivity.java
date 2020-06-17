package com.example.filemanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String mySDPath;
    ListView listView;
    ActionBar actionBar;
    ArrayAdapter<String> adapter;
    ArrayList<String> FilesInFolder;
    String fileNameSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find SD card absolute location
        mySDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        actionBar = getSupportActionBar();

       //List View
        FilesInFolder = GetFiles(mySDPath);
        listView = findViewById(R.id.listView);
        adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, FilesInFolder);
        listView.setAdapter(adapter);

        listView.setLongClickable(true);


        registerForContextMenu(listView);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_newText){
            Intent intent = new Intent(MainActivity.this, MakeNewFileActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            startActivityForResult(intent, 1111);
            return true;
        }

        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select action");
        menu.add(0, 0, 0, "Rename");
        menu.add(0, 1, 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info =  (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int id = item.getItemId();
        if (id == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            AlertDialog.Builder dialog = builder.setTitle("Rename file")
                    .setMessage("Are you sure you want to rename this file?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", null);


            dialog.show();

        }
        else if (id == 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            AlertDialog.Builder dialog = builder.setTitle("Delete file")
                    .setMessage("Are you sure you want to delete this file?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes",null);

            dialog.show();

        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1111){
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                if(!result.equals("")) {
                    FilesInFolder.add(result);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    public ArrayList<String> GetFiles(String DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);

        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (int i=0; i<files.length; i++)
                MyFiles.add(files[i].getName());
        }

        return MyFiles;
    }
}
