package com.example.celineyee.kongsimi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventsActivity extends AppCompatActivity {

    private List<Event> eventsList;
    private String date1 = "23/02/2019";
    private String date2 = "24/02/2019";
    private String dateweek = "03/03/2019";
    private Character charBlurSotong = new Character("Blur Sotong",
            "This is the friend that is always a little slow to catch on and have trouble understanding things", R.drawable.blursotong);
    private Character charKopiUncle = new Character("Kopi Uncle",
            "Your friendly neighbourhood uncle that makes the best teh peng.", R.drawable.kopiuncle);
    private Character charTaxiUncle = new Character("Taxi Uncle",
            "Taxi driver that knows Singapore roads like their backyard.", R.drawable.taxiuncle);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        eventsList = new ArrayList<>();
        eventsList.add(new Event("Daily Event", date2, "", "Paiseh, I super blur sotong one.", charBlurSotong));
        eventsList.add(new Event("Weekly Event", date2, dateweek, "Ah bang, ai lim kopi or not?", charKopiUncle));
        eventsList.add(new Event("Special Event - Hackomania", date1, date2, "Girl today so chio, going paktor ah?", charTaxiUncle));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.events_recyclerview);
        Events_RecyclerViewAdapter recyclerViewAdapter = new Events_RecyclerViewAdapter(this, eventsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);


    }
}
