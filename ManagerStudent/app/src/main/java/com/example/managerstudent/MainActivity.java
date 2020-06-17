package com.example.managerstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Student> arrayList;
    StudentAdapter studentAdapter;
    DatabaseOpenHelper databaseAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseAccess = new DatabaseOpenHelper(MainActivity.this);
        arrayList= databaseAccess.getAllStudent();

        //Khai báo RecycleView
        final RecyclerView recyclerView= (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //Khai báo kiểu layour manage
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Khai báo Adapter
        studentAdapter= new StudentAdapter(MainActivity.this, R.layout.layout_row, arrayList);
        recyclerView.setAdapter(studentAdapter);
    }

    //add student
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnu_add) {
            final  Student student = new Student();
            AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Insert student");
            builder.setCancelable(false);

            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View view= inflater.inflate(R.layout.layout_insert, null);

            final EditText updateMSSV= view.findViewById(R.id.insertMssv);
            final EditText updateName= view.findViewById(R.id.insertName);
            final EditText updateBirthday= view.findViewById(R.id.insertBirth);
            final EditText updateEmail= view.findViewById(R.id.insertEmail);
            final EditText updateAddr= view.findViewById(R.id.insertAddr);
            final ImageView imageView= view.findViewById(R.id.image);

            builder.setView(view);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    student.setMssv(updateMSSV.getText().toString());
                    student.setName(updateName.getText().toString());
                    student.setBirthday(updateBirthday.getText().toString());
                    student.setEmail(updateEmail.getText().toString());
                    student.setAddress(updateAddr.getText().toString());

                    databaseAccess.insertStudent(student);
                    arrayList.add(student);
                    studentAdapter.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog= builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, 0, 0, "Update");
        menu.add(0, 1, 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case 0:
                final  Student student = arrayList.get(info.position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Update student");
                builder.setCancelable(false);

                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View view= inflater.inflate(R.layout.layout_update, null);

                final EditText editName= view.findViewById(R.id.updateName);
                final EditText editBirthday= view.findViewById(R.id.updateBirth);
                final EditText editEmail= view.findViewById(R.id.updateEmail);
                final EditText editAddr= view.findViewById(R.id.updateAddr);
                final ImageView imageView= view.findViewById(R.id.image);

                editName.setText(student.getName());
                editBirthday.setText(student.getBirthday());
                editEmail.setText(student.getEmail());
                editAddr.setText(student.getAddress());
                builder.setView(view);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        student.setName(editName.getText().toString());
                        student.setBirthday(editBirthday.getText().toString());
                        student.setEmail(editEmail.getText().toString());
                        student.setAddress(editAddr.getText().toString());
                        imageView.setImageResource(R.drawable.ic_person);

                        databaseAccess.updateStudent(student);
                        arrayList.set(info.position, student);
                        studentAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog= builder.create();
                alertDialog.show();
                break;

            case 1:
                final  Student student1 = arrayList.get(info.position);
                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("Update student");
                builder1.setCancelable(false);
                builder1.setMessage("Are you sure delete \"" + student1.getName()+"\"");

                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseAccess.deleteStudent(student1);
                        arrayList.remove(info.position);
                        studentAdapter.notifyDataSetChanged();
                    }
                });

                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                android.app.AlertDialog alertDialog1= builder1.create();
                alertDialog1.show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
