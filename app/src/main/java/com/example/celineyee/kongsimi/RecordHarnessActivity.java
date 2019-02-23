package com.example.celineyee.kongsimi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class RecordHarnessActivity extends AppCompatActivity implements RecordFragment.RecordCallback {

    RecordFragment recordFragment;

    @Override
    public void onComplete(String path) {
        Toast.makeText(this, "Got a file! at " + path, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "Cancelled :(", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_harness);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordFragment = RecordFragment.newInstance(60000, "Record something");

                FragmentManager fragmentManager = getSupportFragmentManager();

                recordFragment.show(fragmentManager, "recordFragment");
            }
        });
    }

}
