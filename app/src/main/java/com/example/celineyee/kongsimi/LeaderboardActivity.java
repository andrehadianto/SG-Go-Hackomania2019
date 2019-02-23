package com.example.celineyee.kongsimi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class LeaderboardActivity extends AppCompatActivity {
    RecyclerView nameListView;
    NameListAdapter nameListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        nameListView = findViewById(R.id.name_list);
        nameListAdapter = new NameListAdapter(this);
        nameListView.setAdapter(nameListAdapter);
        nameListView.setLayoutManager(new LinearLayoutManager(this));
    }
}
