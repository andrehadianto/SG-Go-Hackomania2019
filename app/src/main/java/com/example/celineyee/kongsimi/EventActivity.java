package com.example.celineyee.kongsimi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class EventActivity extends AppCompatActivity implements RecordFragment.RecordCallback {

    public static final String TAG = "EventActivity";
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
    private ImageButton playButton;
    private ImageButton deleteButton;
    private RecordFragment recordFragment;
    private MediaPlayer mediaPlayer;

    SharedPreferences preferences;
    boolean mediaPlaying;
    String mediaPath;

    @Override
    public void onComplete(String path) {
        task1.setChecked(true);
//        Toast.makeText(this, "Got a file! at " + path, Toast.LENGTH_LONG).show();
//        String prompt = text_event_text.getText().toString();
        preferences.edit().putString(Integer.toString(event.getId()), path).apply();

        tryAddPlaybackButtons();

    }

    @Override
    public void onCancel() {
//        Toast.makeText(this, "Cancelled :(", Toast.LENGTH_SHORT).show();
    }

    private void initMediaPlayer(String path) {
        if (path == null || path.equals("")) {
            return;
        }

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlaying = false;

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(mediaPath);
            mediaPlayer.prepare();
            Log.d(TAG, "initMediaPlayer: Prepare ok");
        } catch (IOException e) {
            Log.e(TAG, "initMediaPlayer: prepare failed", e);
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlaying = false;
                Log.d(TAG, "onCompletion: Media complete");
            }
        });
    }

    private void tryAddPlaybackButtons() {
        mediaPath = preferences.getString(Integer.toString(event.getId()), "");

        if (mediaPath.equals("")) {
            playButton.setVisibility(View.INVISIBLE);
            deleteButton.setVisibility(View.INVISIBLE);
        } else {
            // Show button
            playButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            mediaPlaying = false;
            mediaPlayer = null;

            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer == null || !mediaPlaying) {
                        // Try playback
                        initMediaPlayer(mediaPath);
                        mediaPlayer.start();
                        mediaPlaying = true;
                    } else {
                        // Mediaplayer != null AND mediaplaying
                        mediaPlayer.stop();
                        mediaPlaying = false;
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Immediately release media player
                    if (mediaPlayer != null) {
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    mediaPlaying = false;

                    if (mediaPath != null && !mediaPath.equals("")) {
                        File file = new File(mediaPath);
                        boolean deleted = file.delete();
                        if (!deleted) {
                            Log.w(TAG, "deleteButton: recordFilePath=\"" + mediaPath + "\"not found");
                        }
                    }

                    mediaPath = "";

                    preferences.edit().remove(Integer.toString(event.getId())).apply();
                    playButton.setVisibility(View.INVISIBLE);
                    deleteButton.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent fromEvents = getIntent();
        Bundle myExtras = fromEvents.getExtras();
        int event_id = myExtras.getInt("ID");
        String event_name = myExtras.getString("NAME");
        String event_startdate = myExtras.getString("STARTDATE");
        String event_enddate = myExtras.getString("ENDDATE");
        String event_text = myExtras.getString("TEXT");
        String char_name = myExtras.getString("CHARNAME");
        String char_desc = myExtras.getString("CHARDESC");
        int char_thumbnail = myExtras.getInt("CHARTHUMBNAIL");
        character = new Character(char_name,char_desc,char_thumbnail);
        event = new Event(event_id,event_name,event_startdate,event_enddate,event_text,character);

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
        playButton = findViewById(R.id.playButton);
        deleteButton = findViewById(R.id.deleteButton);

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
                String prompt = text_event_text.getText().toString();

                recordFragment = RecordFragment.newInstance(60000, prompt);

                FragmentManager fragmentManager = getSupportFragmentManager();

                recordFragment.show(fragmentManager, "recordFragment");
            }
        });

        preferences = getPreferences(Context.MODE_PRIVATE);
        tryAddPlaybackButtons();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

    }
}
