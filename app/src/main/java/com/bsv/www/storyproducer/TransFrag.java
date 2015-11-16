package com.bsv.www.biblestoryvideoproducer;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;

import com.bsv.www.biblestoryvideoproducer.DialogListAdapter;
import com.bsv.www.biblestoryvideoproducer.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class TransFrag extends Fragment {
    private MediaRecorder audioRecorder;
    private static final String SLIDE_NUM = "slidenum";
    private static final String NUM_OF_SLIDES = "numofslide";
    private static final String STORY_NAME = "storyname";
    private String outputFile=null;
    private String fileName = "recording.mp3";
    private int record_count = 2;
    private GestureDetector gestureDetector;

    public static TransFrag newInstance(int position, int numOfSlides, String storyName){
        TransFrag frag = new TransFrag();
        Bundle args = new Bundle();
        args.putInt(SLIDE_NUM, position);
        args.putInt(NUM_OF_SLIDES, numOfSlides);
        args.putString(STORY_NAME, storyName);
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
        int currentSlide = getArguments().getInt(SLIDE_NUM);
        String storyName = getArguments().getString(STORY_NAME);
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_trans, container, false);

        //Change Slide Number and add on click
        TextView slideNum = (TextView)view.findViewById(R.id.trans_slide_indicator);
        // Setting Story Text
        FileSystem fileSystem = new FileSystem();
        ImageView slideimage = (ImageView)view.findViewById(R.id.trans_image_slide);
        slideimage.setImageBitmap(fileSystem.getImage(storyName, currentSlide));

        slideNum.setText("#" + (getArguments().getInt(SLIDE_NUM) + 1));
        slideNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSlideSelectDialog();
            }
        });
        //stuff for saving and playing the audio
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath();
        outputFile += "/BSVP/" + getArguments().getString(STORY_NAME) + "/" + fileName;
        final FloatingActionButton floatingActionButton1 = (FloatingActionButton) view.findViewById(R.id.trans_record);
        final FloatingActionButton floatingActionButton2 = (FloatingActionButton) view.findViewById(R.id.trans_play);
        floatingActionButton2.setVisibility(View.INVISIBLE);
        gestureDetector = new GestureDetector(getContext(), new SingleTapUp());

        //TODO handle an event when you simply click -> it crashes when you do this
        floatingActionButton1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //works with longPress.  Try using onShowPress and onSingleTapUp maybe?
                // https://developer.xamarin.com/guides/cross-platform/application_fundamentals/touch/part_3_touch_in_android/
                //sounds super complicated? not really sure where to go with this.
                if (gestureDetector.onTouchEvent(event)) {
                    Toast.makeText(getContext(), "Hold to record", Toast.LENGTH_LONG).show();
                    return true;
                } else {
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
                    case MotionEvent.ACTION_POINTER_DOWN:
                    case MotionEvent.ACTION_POINTER_UP:
                    Toast.makeText(getContext(), "Recording Stopped", Toast.LENGTH_LONG).show();
                    stopAudioRecorder(audioRecorder);
                    //keep track of the number of records
                    if (record_count == 2) {
                        record_count--;
                        floatingActionButton2.setVisibility(View.VISIBLE);

                    } else if (record_count == 1) {
                        record_count--;
                        floatingActionButton1.setColorNormal(R.color.yellow);
                    } else if (record_count == 0) {
                        floatingActionButton1.setColorNormal(R.color.green);
                    }
                    break;
                    case MotionEvent.ACTION_MOVE:
                        break;

                    }
                }
                return true;
            }
        });

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

    //experimenting with GestureListener
    private class SingleTapUp extends SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            return true;
        }
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
}