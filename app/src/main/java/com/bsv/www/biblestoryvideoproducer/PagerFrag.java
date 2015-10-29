package com.bsv.www.biblestoryvideoproducer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jordan Skomer on 10/22/2015.
 */
public class PagerFrag extends Fragment{
    public static final String NUM_OF_FRAG = "fragnum";
    public static PagerFrag newInstance(int numOfFrags){
        PagerFrag frag = new PagerFrag();
        Bundle bundle = new Bundle();
        bundle.putInt(NUM_OF_FRAG, numOfFrags);
        frag.setArguments(bundle);
        return frag;
    }
    private ViewPager mPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        mPager = (ViewPager)view.findViewById(R.id.pager_viewpager);
//        Toast.makeText(getActivity(), getArguments().getInt("NUM_OF_FRAGS"), Toast.LENGTH_SHORT).show();
        mPager.setAdapter(new com.bsv.www.biblestoryvideoproducer.PagerAdapter(getActivity(), getChildFragmentManager(), getArguments().getInt(NUM_OF_FRAG)));
        return view;
    }

    public void changeView(int position){
        mPager.setCurrentItem(position, true);
    }

}
