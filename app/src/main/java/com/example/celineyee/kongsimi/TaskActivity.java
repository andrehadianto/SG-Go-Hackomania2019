package com.example.celineyee.kongsimi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskActivity extends Activity {
    private ImageView image_task_image;
    private TextView text_task_name;
    private TextView text_task_desc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = getWindowManager();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(0.8*width),(int)(0.8*height));


        image_task_image = findViewById(R.id.task_image);
        text_task_name = findViewById(R.id.task_name);
        text_task_desc = findViewById(R.id.task_desc);

        Intent fromTasks = getIntent();
        int task_image = fromTasks.getExtras().getInt("IMAGE");
        String task_name = fromTasks.getExtras().getString("NAME");
        String task_desc = fromTasks.getExtras().getString("DESC");

        image_task_image.setImageResource(task_image);
        text_task_name.setText(task_name);
        text_task_desc.setText(task_desc);
    }
}
