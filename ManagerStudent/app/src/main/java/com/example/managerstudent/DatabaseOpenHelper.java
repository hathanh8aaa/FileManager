package com.example.managerstudent;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DatabaseOpenHelper {
    String DATABASE_NAME ="StudentDB.db";
    private  static final  String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase db = null;

    Context context;


    //constructor
    DatabaseOpenHelper(Context context) {
        this.context= context;
        processSQLite();
    }

    private void processSQLite(){
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()){
            try{
                CopyDatabaseFromAsset();
                Toast.makeText(context, "Copy Successfull", Toast.LENGTH_SHORT).show();
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
    }
    private  void CopyDatabaseFromAsset(){
        try{
            InputStream databaseInput = context.getAssets().open(DATABASE_NAME);
            String outputStream = getPathDatabaseSystem();
            File file = new File(context.getApplicationInfo().dataDir+ DB_PATH_SUFFIX);
            if(!file.exists()){
                file.mkdir();
            }
            OutputStream databaseOuput = new FileOutputStream(outputStream);
            byte[] buffer = new byte[1024];
            int length;
            while((length= databaseInput.read(buffer)) >0){
                databaseOuput.write(buffer, 0, length);
            }

            databaseOuput.flush();
            databaseOuput.close();
            databaseInput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  String  getPathDatabaseSystem(){
        return context.getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
    }

    //now lets create a method to query and return the result from database
    public ArrayList<Student> getAllStudent(){
        ArrayList<Student> arrayList = new ArrayList<>();
        db=context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
         @SuppressLint("Recycle") Cursor c = db.rawQuery("SELECT * FROM student", null);
        while (c.moveToNext()){
            String mssv = c.getString(0);
            String name = c.getString(1);
            String birthday = c.getString(2);
            String email = c.getString(3);
            String address = c.getString(4);
            arrayList.add(new Student(mssv, name, birthday, email, address));
        }
        return  arrayList;
    }

    public  void insertStudent(Student student){
        db=context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("MSSV", student.getMssv());
            values.put("Name", student.getName());
            values.put("Birthday", student.getBirthday());
            values.put("Email", student.getEmail());
            values.put("Address", student.getAddress());
            if(db.insert("student", null, values)>0){
                Toast.makeText(context, "successful", Toast.LENGTH_SHORT).show();
            }
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void updateStudent( Student student)
    {
        db=context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("Name", student.getName());
            values.put("Birthday", student.getBirthday());
            values.put("Email", student.getEmail());
            values.put("Address", student.getAddress());
            if(db.update("student", values, "id =" + student.getMssv(),null) >0){
                Toast.makeText(context, "successful", Toast.LENGTH_SHORT).show();
            }
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
    public void deleteStudent(Student student)
    {
        db=context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        db.beginTransaction();
        try {
            long ret = db.delete("Student", "MSSV= "+student.getMssv(), null);
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
}
