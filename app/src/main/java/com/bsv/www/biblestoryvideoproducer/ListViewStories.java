package com.bsv.www.biblestoryvideoproducer;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;

/**
 * Created by hannahbrown on 9/25/15.
 */
public class ListViewStories extends Activity{

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.listView);

        FileSystem fileSystem = new FileSystem();

        // Defined Array values to show in ListView
        String[] values = fileSystem.getVideos();

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        //TODO Images for listview

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.activity_menu_list, R.id.title, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);

            }

        });
    }

}
