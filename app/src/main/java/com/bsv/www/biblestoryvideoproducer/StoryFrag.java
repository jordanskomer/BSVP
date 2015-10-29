package com.bsv.www.biblestoryvideoproducer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jordan Skomer on 10/26/2015.
 */
public class StoryFrag extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story,container,false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.story_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private class StoryHolder extends RecyclerView.ViewHolder {
        public StoryHolder(View itemView){
            super(itemView);
        }
    }
}
