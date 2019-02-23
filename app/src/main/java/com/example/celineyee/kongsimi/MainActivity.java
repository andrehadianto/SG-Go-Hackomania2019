package com.example.celineyee.kongsimi;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    FloatingActionButton event;
    FloatingActionButton leaderboard;
    FloatingActionButton vote;
    FloatingActionButton tasks;
    Boolean ismenuopen = false;
    Button backgrounddefault;
    Button backgroundone;
    Button backgroundtwo;
    Button backgroundthree;
    ImageView backgroundimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundimg = findViewById(R.id.backgroundimg);
        backgrounddefault = findViewById(R.id.fabbackgrounddefault);
        backgroundone = findViewById(R.id.fabbackgroundone);
        backgroundtwo = findViewById(R.id.fabbackgroundtwo);
        backgroundthree = findViewById(R.id.fabbackgroundthree);
        backgrounddefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundimg.setImageResource(R.drawable.background_main);
            }
        });
        backgroundone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundimg.setImageResource(R.drawable.background_one);
            }
        });
        backgroundtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundimg.setImageResource(R.drawable.background_two);
            }
        });
        backgroundthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundimg.setImageResource(R.drawable.background_three);
            }
        });


        fab = findViewById(R.id.fab);
        event = findViewById(R.id.fabevent);
        leaderboard = findViewById(R.id.fableaderboard);
        vote = findViewById(R.id.fabvote);
        tasks = findViewById(R.id.fabtasks);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ismenuopen){
                    showmenu();
                }else{
                    closemenu();
                }
            }
        });


        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toEvents = new Intent(MainActivity.this,EventsActivity.class);
                startActivity(toEvents);
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intleaderboard = new Intent(getApplicationContext(),LeaderboardActivity.class);
                startActivity(intleaderboard);
            }
        });
//
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intvote = new Intent(getApplicationContext(),VotingActivity.class);
                startActivity(intvote);
            }
        });

        tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inttasks = new Intent(getApplicationContext(),TasksActivity.class);
                startActivity(inttasks);
            }
        });

    }

    private void showmenu(){
        ismenuopen = true;
        event.animate().translationY(getResources().getDimension(R.dimen.standard_55));
        leaderboard.animate().translationY(getResources().getDimension(R.dimen.standard_105));
        vote.animate().translationY(getResources().getDimension(R.dimen.standard_155));
        tasks.animate().translationY(getResources().getDimension(R.dimen.standard_205));
    }

    private void closemenu(){
        ismenuopen = false;
        event.animate().translationY(0);
        leaderboard.animate().translationY(0);
        vote.animate().translationY(0);
        tasks.animate().translationY(0);
    }
}
