package com.bsv.www.biblestoryvideoproducer;

import android.app.Dialog;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class TransFrag extends Fragment {
    private MediaRecorder audioRecorder;
    private static final String SLIDE_NUM = "slidenum";
    private static final String NUM_OF_SLIDES = "numofslide";

    public static TransFrag newInstance(int position, int numOfSlides){
        TransFrag frag = new TransFrag();
        Bundle args = new Bundle();
        args.putInt(SLIDE_NUM, position);
        args.putInt(NUM_OF_SLIDES, numOfSlides);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        getActivity().getActionBar().setTitle(R.string.trans_menu_title);
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_trans, container, false);
//        Snackbar.make(getActivity().getCurrentFocus(), "TEST", Snackbar.LENGTH_LONG).show();

    //Change Slide Number and add on click
        TextView slideNum = (TextView)view.findViewById(R.id.trans_slide_indicator);
        slideNum.setText("#" + (getArguments().getInt(SLIDE_NUM) + 1));
        slideNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSlideSelectDialog();
            }
        });
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                audioRecorder = createAudioRecorder("recording");
                startAudioRecorder(audioRecorder);
                Snackbar.make(v, "Recording...", Snackbar.LENGTH_SHORT).show();
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
    private void launchSlideSelectDialog(){
        final Dialog dialog = new Dialog(getContext());
        int[] slides = new int[getArguments().getInt(NUM_OF_SLIDES)];
        for(int i=0; i<getArguments().getInt(NUM_OF_SLIDES); i++){
            slides[i] = (i+1);
        }
        final DialogListAdapter dialogListAdapter = new DialogListAdapter(getActivity(),slides, getArguments().getInt(SLIDE_NUM));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_slide_indicator);
        ListView listView = (ListView)dialog.findViewById(R.id.dialog_listview);
        listView.setAdapter(dialogListAdapter);
        Button okText = (Button)dialog.findViewById(R.id.dialog_ok);
        Button cancelText = (Button)dialog.findViewById(R.id.dialog_cancel);
        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        okText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                getActivity()
            }
        });
        dialog.show();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(menu != null){
            menu.findItem(R.id.menu_search).setVisible(false);
        }

    }
}
