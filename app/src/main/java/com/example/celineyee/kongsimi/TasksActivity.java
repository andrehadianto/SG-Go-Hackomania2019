package com.example.celineyee.kongsimi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class TasksActivity extends AppCompatActivity {

    private ImageButton button_angkukueh;
    private ImageButton button_stones;
    private ImageButton button_minjiangkueh;
    private ImageButton button_icepops;
    private ImageButton button_rabbitsweet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        button_angkukueh = findViewById(R.id.sticker_angkukueh);
        button_stones = findViewById(R.id.sticker_stones);
        button_minjiangkueh = findViewById(R.id.sticker_minjiangkueh);
        button_icepops = findViewById(R.id.sticker_icepops);
        button_rabbitsweet = findViewById(R.id.sticker_rabbitsweet);

        button_angkukueh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_angkukueh.setImageResource(R.drawable.sticker_angkukueh);
            }
        });
        button_rabbitsweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_rabbitsweet.setImageResource(R.drawable.sticker_rabbitsweet);
            }
        });

        button_icepops.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent toIcepops = new Intent(TasksActivity.this,TaskActivity.class);
                toIcepops.putExtra("IMAGE",R.drawable.sticker_icepops);
                toIcepops.putExtra("NAME","Ice Popsicles");
                toIcepops.putExtra("DESC","Our favourite after-school snack from the shop opposite school");
                startActivity(toIcepops);
            }
        });
        button_stones.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent toStones = new Intent(TasksActivity.this,TaskActivity.class);
                toStones.putExtra("IMAGE",R.drawable.sticker_stones);
                toStones.putExtra("NAME","Pick Up Stones");
                toStones.putExtra("DESC","From when internet wasn't a thing");
                startActivity(toStones);
            }
        });
        button_minjiangkueh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent toMinjiangkueh = new Intent(TasksActivity.this,TaskActivity.class);
                toMinjiangkueh.putExtra("IMAGE",R.drawable.sticker_minjiangkueh);
                toMinjiangkueh.putExtra("NAME","Min Jiang Kueh");
                toMinjiangkueh.putExtra("DESC","Yummy at any time of the day - breakfast, lunch, dinner or supper");
                startActivity(toMinjiangkueh);
            }
        });
    }
}
