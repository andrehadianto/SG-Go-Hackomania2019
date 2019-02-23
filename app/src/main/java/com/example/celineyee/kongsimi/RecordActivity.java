package com.example.celineyee.kongsimi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.camera2.*;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Activity to capture audio and video and write it out to a file. Has "instant preview" function
 * like Instagram. startActivityForIntent on this activity will return the path to a temporary video
 * file.
 *
 * Loads of code lifted from Camera2 sample: https://github.com/googlesamples/android-Camera2Video
 */
public class RecordActivity extends AppCompatActivity {

    public static final String TAG = "RecordActivity";

    public static final int START_FOR_FILE = 1;

    // This is used to request permission at runtime
    private static final String[] VIDEO_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
    };

    private enum State {
        PREVIEW, RECORD, REVIEW, DONE
    }
    private State state;

    private CameraManager cameraManager;
    private CameraDevice frontFacingCamera;
    private MediaRecorder mediaRecorder;
    private CameraCaptureSession cameraCaptureSession;

    private TextView promptTextView;
    private TextureView previewView;
    private ImageView captureButton;
    private ImageView submitButton;
    private ImageView cancelButton;
    private ProgressBar remainingProgress;



    private TextureView.SurfaceTextureListener surfaceTextureListener =
            new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        captureButton = findViewById(R.id.captureButton);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);
        remainingProgress = findViewById(R.id.remainingProgressBar);

        promptTextView = findViewById(R.id.promptTextView);
        previewView = findViewById(R.id.previewTextureView);

        // Initialize the camera


        // Attach touch and release handlers
        captureButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    // Begin record!
                    state = State.RECORD;
                    stateTransition();
                } else if (action == MotionEvent.ACTION_UP) {
                    state = State.REVIEW;
                    stateTransition();
                }
                return true;
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = State.DONE;
                stateTransition();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = State.PREVIEW;
                stateTransition();
            }
        });

        state = State.PREVIEW;
        stateTransition();

    }

    void stateTransition() {
        switch(this.state) {
            case PREVIEW:
                // Initial visibility
                submitButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                remainingProgress.setVisibility(View.GONE);
                captureButton.setVisibility(View.VISIBLE);

                //


                break;

            case RECORD:
                submitButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                remainingProgress.setVisibility(View.VISIBLE);
                captureButton.setVisibility(View.VISIBLE);
                break;

            case REVIEW:
                submitButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                remainingProgress.setVisibility(View.VISIBLE);
                captureButton.setVisibility(View.INVISIBLE);
                break;

            case DONE:
                submitButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                remainingProgress.setVisibility(View.GONE);
                captureButton.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onStart() {
        // Open the front facing camera
        // And render the frames to the TextureView
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
