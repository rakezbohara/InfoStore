package com.app.rakez.infostore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.util.Date;

/**
 * Created by RAKEZ on 5/21/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    public static String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/databases/";

    public static final String DB_NAME = "students.db";
    public static final String TABLE_NAME = "info";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "GRADE";
    public static final String COL_4 = "SECTION";
    public static final String COL_5 = "PHONE1";
    public static final String COL_6 = "PHONE2";
    public static final String COL_7 = "IMAGEURL";
    public static final String COL_8 = "DOA";


    public DatabaseHelper(Context context) {
        super(context, DB_PATH+DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry = "CREATE TABLE "+TABLE_NAME+" ("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_2+" TEXT NOT NULL, "+COL_3+" TEXT, "+COL_4+" TEXT, "+COL_5+" TEXT, "+COL_6+" TEXT, "+COL_7+" TEXT, "+COL_8+" DATE );";
        sqLiteDatabase.execSQL(qry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(sqLiteDatabase);
    }

    public void insertdata(String name, String grade, String section, String phone1, String phone2, String imageurl, String doa){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2,name);
        cv.put(COL_3,grade);
        cv.put(COL_4,section);
        cv.put(COL_5,phone1);
        cv.put(COL_6,phone2);
        cv.put(COL_7,imageurl);
        cv.put(COL_8,doa);
        long result = db.insert(TABLE_NAME,null,cv);
        if(result==-1){
            Log.d("result","Data failed");
        }else{
            Log.d("result","Data success");
        }
    }

    public Cursor fetchdata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return result;
    }
    public Cursor fetchid(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE ID="+id,null);
        return result;
    }

}
