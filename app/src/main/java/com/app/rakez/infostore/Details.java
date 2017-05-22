package com.app.rakez.infostore;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class Details extends AppCompatActivity {

    TextView fetchedname;
    ImageView fetchedphoto;

    String name;
    String filename;
    Uri filepath;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent i = getIntent();
        Bundle bun = i.getExtras();
        int id = bun.getInt("id");
        fetchedname = (TextView) findViewById(R.id.fetchedname);
        fetchedphoto = (ImageView) findViewById(R.id.fetchedphoto);
        DatabaseHelper dbh = new DatabaseHelper(this);
        Cursor result =  dbh.fetchid(id);
        while(result.moveToNext()){
            name =  result.getString(1);
            filename = result.getString(6);
        }
        fetchedname.setText(name);
        filepath = Uri.parse(filename);
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
            fetchedphoto.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
