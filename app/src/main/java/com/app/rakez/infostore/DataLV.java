package com.app.rakez.infostore;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DataLV extends AppCompatActivity {

    ListView infoLV;
    ArrayList<String> infolist = new ArrayList<>();
    ArrayList<Integer> idlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_lv);
        infoLV = (ListView) findViewById(R.id.infoLV);
        DatabaseHelper dbh = new DatabaseHelper(this);
        Cursor result = dbh.fetchdata();
        while(result.moveToNext()){
            infolist.add(result.getString(1));
            idlist.add(result.getInt(0));
        }

        ArrayAdapter<String> ad = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,infolist);
        infoLV.setAdapter(ad);
        infoLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = idlist.get(i);
                Bundle bun = new Bundle();
                bun.putInt("id",id);
                Intent in = new Intent(getApplicationContext(),Details.class);
                in.putExtras(bun);
                startActivity(in);


            }
        });

    }
}
