package com.example.filemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class MakeNewFileActivity extends AppCompatActivity {
    EditText txtData, txtName;
    Button btnWriteSDFile, btnClearScreen;
    String mySDPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_new_file);

        //find SD card absolute location
        mySDPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        //bind GUI elements to local controls
        txtData = findViewById(R.id.txtData);
        txtName = findViewById(R.id.txtName);

        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        findViewById(R.id.btnWriteSDFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String fileName = txtName.getText().toString();
                    if(!fileName.equals("")) {
                        File myFile = new File(mySDPath + "/" + fileName + ".txt");
                        OutputStreamWriter outputStream = new OutputStreamWriter(new FileOutputStream(myFile));
                        outputStream.append(txtData.getText());
                        outputStream.close();
                        Toast.makeText(getBaseContext(), "Done Writing SD " + fileName + ".txt", Toast.LENGTH_SHORT).show();
                        intent.putExtra("result", fileName + ".txt");
                        setResult(Activity.RESULT_OK, intent);
                    }else{
                        Toast.makeText(getBaseContext(), "Please enter your file name", Toast.LENGTH_SHORT).show();
                    }

                }catch(Exception e){
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btnClearScreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtData.setText("");
            }
        });

        findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





    }




}
