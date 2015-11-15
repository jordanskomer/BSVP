package com.bsv.www.storyproducer;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by hannahbrown on 9/25/15.
 */
public class StoryFrag extends Fragment{

    ListView listView;
    FileSystem fileSystem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_list_view, container, false);

        // Get ListView object from xml
        listView = (ListView)getActivity().findViewById(R.id.story_list_view);

        fileSystem = new FileSystem();

        // Defined Array values to show in ListView
        final String[] values = fileSystem.getVideos();
        final ListFiles[] listFiles = new ListFiles[values.length];

        for(int i = 0; i < listFiles.length; i++) {
            listFiles[i] = new ListFiles(fileSystem.getImage(values[i], 1), values[i]);
        }

        CustomAdapter adapter = new CustomAdapter(getContext(), R.layout.story_list_item, listFiles);

        listView = (ListView)view.findViewById(R.id.story_list_view);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int slideNum = fileSystem.getImageAmount(values[position]);
                ((MainActivity)getActivity()).startFragment(1, slideNum, values[position]);
            }
        });

        return view;
    }

}
