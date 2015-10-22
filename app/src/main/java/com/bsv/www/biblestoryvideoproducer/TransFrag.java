package com.bsv.www.biblestoryvideoproducer;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class TransFrag extends Fragment {
    private MediaRecorder audioRecorder;
    private static final String SLIDE_NUM = "slidenum";

    public static TransFrag newInstance(int position){
        TransFrag frag = new TransFrag();
        Bundle args = new Bundle();
        args.putInt(SLIDE_NUM, position);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        getActivity().getActionBar().setTitle(R.string.trans_menu_title);
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_trans, container, false);

    //Change Slide Number and add on click
        TextView slideNum = (TextView)view.findViewById(R.id.trans_slide_indicator);
        slideNum.setText("#" + (getArguments().getInt(SLIDE_NUM)+1));
        slideNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
//                        v.setPressed(true);
                        audioRecorder = createAudioRecorder("recording");
                        startAudioRecorder(audioRecorder);
                        Toast.makeText(getContext(), "Recording Started", Toast.LENGTH_LONG).show();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_CANCEL:
                        Toast.makeText(getContext(), "Recording Stopped", Toast.LENGTH_LONG).show();
                        stopAudioRecorder(audioRecorder);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;

                }
                return true;
            }
        });

        return view;
    }
    private void startAudioRecorder(MediaRecorder recorder){
        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException | IOException e){
            e.printStackTrace();
        }
    }
    private void stopAudioRecorder(MediaRecorder recorder){
        recorder.stop();
        recorder.release();
    }
    private MediaRecorder createAudioRecorder(String fileName){
        MediaRecorder mediaRecorder = new MediaRecorder();
        String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath();
        outputFile += "/" + fileName;

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(outputFile);

        return  mediaRecorder;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
