package com.example.celineyee.kongsimi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecordCallback} interface
 * to handle interaction events.
 * Use the {@link RecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordFragment extends DialogFragment {

    public static final String TAG = "RecordFragment";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MAX_LEN = "arg_max_len";
    public static final String ARG_PROMPT = "arg_prompt";
    private int mMaxLen;
    private String mPrompt;

    private RecordCallback recordCallback;

    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;

    private MediaPlayer mediaPlayer;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;


    private TextView promptTextView;
    private ImageView captureButton;
    private ImageView submitButton;
    private ImageView cancelButton;
    private ImageView playButton;
    private ProgressBar remainingProgress;

    private enum State {
        START, RECORD, REVIEW, DONE
    }
    private boolean isPlaying = false;
    private State state = State.START;
    private String recordFilePath;

    public RecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param max_len Max length of recording in milliseconds.
     * @param prompt Prompt string
     * @return A new instance of fragment RecordFragment.
     */
    public static RecordFragment newInstance(int max_len, String prompt) {
        RecordFragment fragment = new RecordFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MAX_LEN, max_len);
        args.putString(ARG_PROMPT, prompt);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            mMaxLen = args.getInt(ARG_MAX_LEN);
            mPrompt = args.getString(ARG_PROMPT);
        }

        // Grab the permissions
        if (ContextCompat.checkSelfPermission(getContext(), permissions[0])
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        promptTextView = view.findViewById(R.id.promptTextView);
        captureButton = view.findViewById(R.id.captureButton);
        submitButton = view.findViewById(R.id.submitButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        playButton = view.findViewById(R.id.playButton);

        promptTextView.setText(mPrompt);

        captureButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    startRecording();
                } else if (action == MotionEvent.ACTION_UP) {
                    stopRecording();
                }
                return true;
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the file!
                deleteRecording();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tell parent Activity to grab the file and destroy me daddy
                recordCallback.onComplete(recordFilePath);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    stopPlayback();
                    isPlaying = false;
                } else {
                    startPlayback();
                    isPlaying = true;
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        state = State.START;
        redrawByState();
    }

    private void redrawByState() {
        switch (state) {
            case START:
                captureButton.setVisibility(View.VISIBLE);
                submitButton.setVisibility(View.INVISIBLE);
                cancelButton.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.GONE);
                break;

            case RECORD:
                captureButton.setVisibility(View.VISIBLE);
                submitButton.setVisibility(View.INVISIBLE);
                cancelButton.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.GONE);
                break;

            case REVIEW:
                captureButton.setVisibility(View.GONE);
                submitButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                playButton.setVisibility(View.VISIBLE);
                break;

            case DONE:
                captureButton.setVisibility(View.GONE);
                submitButton.setVisibility(View.INVISIBLE);
                cancelButton.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.GONE);
                break;
        }
        getView().invalidate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecordCallback) {
            recordCallback = (RecordCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RecordCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        recordCallback = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }

        if (!permissionToRecordAccepted) {
            // finish();
            Log.w(TAG, "onRequestPermissionsResult: Didn't get permission!");
            if (recordCallback != null) {
                recordCallback.onCancel();
            } else {
                dismiss();
            }
        }
    }

    private String getAudioFilePath(Context context) {
        final File dir = context.getExternalFilesDir(null);
        return (dir == null ? "" : (dir.getAbsolutePath() + "/"))
                + System.currentTimeMillis() + ".m4a";
    }

    private boolean startRecording() {
        if (state != State.START) {
            throw new RuntimeException("Must be in start state to record");
        }

        recordFilePath = getAudioFilePath(getContext());

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(recordFilePath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setMaxDuration(mMaxLen);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
            return false;
        }

        mediaRecorder.start();
        state = State.RECORD;
        redrawByState();
        return true;
    }

    private boolean stopRecording() {
        if (state != State.RECORD) {
            throw new RuntimeException("Must be in record state to stop");
        }

        mediaRecorder.stop();
        mediaRecorder.release();
        state = State.REVIEW;
        redrawByState();
        return true;
    }

    private void deleteRecording() {
        if (state != State.REVIEW) {
            throw new RuntimeException("State error");
        }

        if (recordFilePath != null) {
            // FIXME Crashes at this line, can't delete an absolute path for some reason
            getContext().deleteFile(recordFilePath);
        }

        state = State.START;
        redrawByState();
    }

    private void startPlayback() {
        if (state != State.REVIEW) {
            throw new RuntimeException("State error");
        }

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(recordFilePath);
            mediaPlayer.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare failed");
            return;
        }

        mediaPlayer.start();
    }

    private void stopPlayback() {
        if (state != State.REVIEW) {
            throw new RuntimeException("State error");
        }

        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface RecordCallback {
        /**
         * Called on successful record
         * @param path Path to the recorded audio file
         */
        void onComplete(String path);

        /**
         * Called on user cancel of recording
         */
        void onCancel();
    }
}
