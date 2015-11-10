package com.bsv.www.storyproducer;

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
    private static int FRAG_TYPE = 0;
    public PagerAdapter(Context context, FragmentManager fm, int fragNum, int fragType){
        super(fm);
        this.context = context;
        NUM_OF_FRAGS = fragNum;
        FRAG_TYPE = fragType;
    }

    @Override
    public int getCount() {
        return NUM_OF_FRAGS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (FRAG_TYPE){
            //Translate
            case 1:
                return TransFrag.newInstance(position, NUM_OF_FRAGS);
            //Community
            case 2:
                return new ComCheckFrag();
            //Consultant
            case 3:
                return new ConCheckFrag();
        }
        return null;
    }
}
