package com.example.celineyee.kongsimi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class EventActivity extends AppCompatActivity implements RecordFragment.RecordCallback {
    private Event event;
    private Character character;

    private TextView text_event_name;
    private TextView text_event_startdate;
    private TextView text_date_separator;
    private TextView text_event_enddate;
    private TextView text_event_text;
    private CheckBox task1;
    private CheckBox task2;
    private ImageView image_char_thumbnail;
    private TextView text_char_name;
    private TextView text_char_desc;
    private FloatingActionButton fabRecord;
    RecordFragment recordFragment;

    @Override
    public void onComplete(String path) {
        task1.setChecked(true);
//        Toast.makeText(this, "Got a file! at " + path, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCancel() {
//        Toast.makeText(this, "Cancelled :(", Toast.LENGTH_SHORT).show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent fromEvents = getIntent();
        String event_name = fromEvents.getExtras().getString("NAME");
        String event_startdate = fromEvents.getExtras().getString("STARTDATE");
        String event_enddate = fromEvents.getExtras().getString("ENDDATE");
        String event_text = fromEvents.getExtras().getString("TEXT");
        String char_name = fromEvents.getExtras().getString("CHARNAME");
        String char_desc = fromEvents.getExtras().getString("CHARDESC");
        int char_thumbnail = fromEvents.getExtras().getInt("CHARTHUMBNAIL");
        character = new Character(char_name,char_desc,char_thumbnail);
        event = new Event(event_name,event_startdate,event_enddate,event_text,character);

        text_event_name = findViewById(R.id.event_name);
        text_event_startdate = findViewById(R.id.event_startdate);
        text_date_separator = findViewById(R.id.date_separator);
        text_event_enddate = findViewById(R.id.event_enddate);
        text_event_text = findViewById(R.id.event_text);
        task1 = findViewById(R.id.event_task1);
        task2 = findViewById(R.id.event_task2);
        image_char_thumbnail = findViewById(R.id.char_thumbnail);
        text_char_name = findViewById(R.id.char_name);
        text_char_desc = findViewById(R.id.char_desc);
        fabRecord = findViewById(R.id.recordButton);

        text_event_name.setText(event_name);
        text_event_startdate.setText(event_startdate);

        if (event_enddate.equals("")) {
            text_date_separator.setVisibility(View.GONE);
            text_event_enddate.setVisibility(View.GONE);
        } else {
            text_event_enddate.setText(event_enddate);
        }
        text_event_text.setText(event_text);
        image_char_thumbnail.setImageResource(char_thumbnail);
        text_char_name.setText(char_name);
        text_char_desc.setText(char_desc);


        task1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked())
                {
                    task1.setEnabled(false);
                    buttonView.setButtonDrawable(R.drawable.tick);
                }
                if(!buttonView.isChecked())
                {
                    buttonView.setButtonDrawable(R.drawable.cross);
                }

            }
        });

//        task2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(buttonView.isChecked())
//                {
//                    task2.setEnabled(false);
//                    buttonView.setButtonDrawable(R.drawable.tick);
//                }
//                if(!buttonView.isChecked())
//                {
//                    buttonView.setButtonDrawable(R.drawable.cross);
//                }
//
//            }
//        });

        fabRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordFragment = RecordFragment.newInstance(60000, "Record something");

                FragmentManager fragmentManager = getSupportFragmentManager();

                recordFragment.show(fragmentManager, "recordFragment");
            }
        });
    }

}
