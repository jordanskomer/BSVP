package com.bsv.www.storyproducer;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by Jordan Skomer on 10/26/2015.
 */
public class DialogListAdapter extends BaseAdapter  {
    private int[] slides;
    int currentSlide;
    int checkSlide;
    private static LayoutInflater inflater;
    Context context;
    public DialogListAdapter(FragmentActivity mainActivity, int[] slides, int currentSlide) {
        this.slides = slides;
        this.currentSlide = currentSlide;
        this.context = mainActivity;
        this.inflater = (LayoutInflater)mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return slides.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    public int getSelectedSlide(){
        return checkSlide;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    RadioButton currentlyCheckedRadio;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.dialog_slide_list_item, null);
        TextView textView = (TextView)view.findViewById(R.id.dialog_slide_text);
        final RadioButton radioButton = (RadioButton)view.findViewById(R.id.dialog_radio);
        if(position == currentSlide){
            currentlyCheckedRadio = radioButton;
            currentlyCheckedRadio.setChecked(true);
        }
        textView.setText("Slide " + slides[position]);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton radioButton = (RadioButton)v.findViewById(R.id.dialog_radio);
                if (currentlyCheckedRadio == null) {
                    currentlyCheckedRadio = radioButton;
                    currentlyCheckedRadio.setChecked(true);
                }
                if (currentlyCheckedRadio == radioButton) {
                    return;
                }
                currentlyCheckedRadio.setChecked(false);
                radioButton.setChecked(true);
                checkSlide = position;
                currentlyCheckedRadio = radioButton;

            }
        });
        return view;
    }

}

