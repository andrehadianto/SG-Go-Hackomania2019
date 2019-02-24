package com.example.celineyee.kongsimi;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VotingActivity extends AppCompatActivity {

    List<VotingList> votingLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        votingLists = new ArrayList<>();
        votingLists.add(
                new VotingList(
                        1,
                        84,
                        "Blur Sotong",
                        "Andre HL",
                        (R.drawable.blursotong)
                ));
        votingLists.add(
                new VotingList(
                        2,
                        124,
                        "Kopi Uncle",
                        "Nikos Chan",
                        (R.drawable.kopiuncle)
                ));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.votingRecyclerView);
        VotingListAdapter votingListAdapter = new VotingListAdapter(this,votingLists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(votingListAdapter);
    }
}
