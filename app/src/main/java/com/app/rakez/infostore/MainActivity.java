package com.app.rakez.infostore;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button datePicker,sImage,sSave;
    ImageView sImageView;
    EditText sName,sPhoneNo1,sPhoneNo2,sClass,sSection;
    static String doa;

    Bitmap bitmap;
    Uri filePath;

    private void initialize() {
        datePicker = (Button) findViewById(R.id.datePicker);
        sImage = (Button) findViewById(R.id.sImage);
        sSave = (Button) findViewById(R.id.sSave);

        sImageView = (ImageView) findViewById(R.id.sImageView);

        sName = (EditText) findViewById(R.id.sName);
        sPhoneNo1 = (EditText) findViewById(R.id.sPhoneNo1);
        sPhoneNo2 = (EditText) findViewById(R.id.sPhoneNo2);
        sClass = (EditText) findViewById(R.id.sClass);
        sSection = (EditText) findViewById(R.id.sSection);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        sImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setType("image/*");
                in.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(in,"Select Image"),1);
            }
        });

        sSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = sName.getText().toString();
                String grade = sClass.getText().toString();
                String section = sSection.getText().toString();
                String phone1 = sPhoneNo1.getText().toString();
                String phone2 = sPhoneNo2.getText().toString();
                String imageurl = filePath.toString();
                /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date=null;
                try {
                    date = sdf.parse(doa);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                    */
                DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());
                dbh.insertdata(name,grade,section,phone1,phone2,imageurl,doa);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                sImageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            doa = year+"-"+month+"-"+day;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.viewData){
            Intent in = new Intent(getApplicationContext(),DataLV.class);
            startActivity(in);
        }
        return super.onOptionsItemSelected(item);
    }
}
