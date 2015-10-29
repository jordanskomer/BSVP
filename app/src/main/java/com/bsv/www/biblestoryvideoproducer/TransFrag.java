package com.bsv.www.biblestoryvideoproducer;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import com.getbase.floatingactionbutton.FloatingActionButton;
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
    private String outputFile=null;
    private String fileName = "recording.mp3";

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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_trans, container, false);
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
        //stuff for saving and playing the audio
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath();
        outputFile += "/" + fileName;
        FloatingActionButton floatingActionButton1 = (FloatingActionButton) view.findViewById(R.id.trans_record);
        floatingActionButton1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
//                        v.setPressed(true);
                        audioRecorder = createAudioRecorder(outputFile);
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

        FloatingActionButton floatingActionButton2 = (FloatingActionButton) view.findViewById(R.id.trans_play);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getContext(), "Playing Audio", Toast.LENGTH_LONG).show();
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
        //recorder = null;
    }
    private MediaRecorder createAudioRecorder(String fileName){
        MediaRecorder mediaRecorder = new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(fileName);

        return  mediaRecorder;
    }

    private void launchSlideSelectDialog() {
        final Dialog dialog = new Dialog(getContext());
        int[] slides = new int[getArguments().getInt(NUM_OF_SLIDES)];
        for (int i = 0; i < getArguments().getInt(NUM_OF_SLIDES); i++) {
            slides[i] = (i + 1);
        }
        final DialogListAdapter dialogListAdapter = new DialogListAdapter(getActivity(), slides, getArguments().getInt(SLIDE_NUM));
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