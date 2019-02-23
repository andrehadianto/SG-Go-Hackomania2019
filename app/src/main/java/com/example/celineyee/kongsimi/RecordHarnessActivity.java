package com.example.celineyee.kongsimi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.security.auth.login.LoginException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecordHarnessActivity extends AppCompatActivity implements RecordFragment.RecordCallback {

    public static final String TAG = "RecordHarnessActivity";
    RecordFragment recordFragment;
    FragmentManager fragmentManager;
    private static final String recordFragmentTag = "recordFragmentTag";
    public static final String recogEndpoint = "http://52.163.240.180/client/dynamic/recognize";
    private TextView textView;

    @Override
    public void onComplete(String path) {
        Log.d(TAG, "onComplete: " + path);
        Toast.makeText(this, "Got a file! at " + path, Toast.LENGTH_LONG).show();

        textView.append("File: " + path + "\n");
        uploadToRecognitionServer(path);
    }

    @Override
    public void onCancel() {
        Log.d(TAG, "onCancel: ");
        Toast.makeText(this, "Cancelled :(", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_harness);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        textView = findViewById(R.id.recordHarnessResultTextView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordFragment = RecordFragment.newInstance(60000, "Kua simi lan?");

                recordFragment.show(fragmentManager, recordFragmentTag);
            }
        });
    }

    private void uploadToRecognitionServer(String path) {
        File file = new File(path);
        MediaType mediaType = MediaType.parse("audio/x-wav");
        Handler handler = new Handler(getMainLooper());

        Request request = new Request.Builder()
                .url(recogEndpoint)
                .put(RequestBody.create(mediaType, file))
                .addHeader("Connection", "close")
                .build();

        App.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "uploadToRecognitionServer: Request failed", e);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.append("Recognition request failed\n");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "uploadToRecognitionServer: Response");
                String respString = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.append(respString);
                        textView.append("\n");
                    }
                });
            }
        });
    }

}
