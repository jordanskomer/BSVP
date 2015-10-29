package com.bsv.www.biblestoryvideoproducer;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Jordan Skomer on 10/22/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    Context context;
    private static int NUM_OF_FRAGS = 5;
    public PagerAdapter(Context context, FragmentManager fm, int fragNum){
        super(fm);
        this.context = context;
        NUM_OF_FRAGS = fragNum;
    }

    @Override
    public int getCount() {
        return NUM_OF_FRAGS;
    }

    @Override
    public Fragment getItem(int position) {
        return TransFrag.newInstance(position, NUM_OF_FRAGS);
    }
}
