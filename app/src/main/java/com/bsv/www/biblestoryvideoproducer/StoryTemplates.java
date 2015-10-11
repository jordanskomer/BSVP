package com.bsv.www.biblestoryvideoproducer;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class StoryTemplates extends AppCompatActivity {
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavItem> mNavItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_templates);
        String[] aNavTitles = getResources().getStringArray(R.array.nav_labels);
        TypedArray aNavIcons = getResources().obtainTypedArray(R.array.nav_icons);
        final String mActivityTitle = getTitle().toString();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_layout);
        mDrawerList = (ListView) findViewById(R.id.nav_list);

        for (int i = 0; i < aNavTitles.length; i++) {
            mNavItems.add(new NavItem(aNavTitles[i], aNavIcons.getResourceId(i, -1)));
        }
        aNavIcons.recycle();

        NavItemAdapter adapter = new NavItemAdapter(getApplicationContext(), mNavItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Pages");
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.menu_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_story_templates, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
            return super.onOptionsItemSelected(item);
    }

    //CREATED CLASSES
    private void setupNavDrawer(){
        String[] aNavTitles = getResources().getStringArray(R.array.nav_labels);
        TypedArray aNavIcons = getResources().obtainTypedArray(R.array.nav_icons);
        final String mActivityTitle = getTitle().toString();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_layout);
        mDrawerList = (ListView) findViewById(R.id.nav_list);

        for (int i = 0; i < aNavTitles.length; i++) {
            mNavItems.add(new NavItem(aNavTitles[i], aNavIcons.getResourceId(i, -1)));
        }
        aNavIcons.recycle();

        NavItemAdapter adapter = new NavItemAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Pages");
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(StoryTemplates.this, Integer.toString(position), Toast.LENGTH_SHORT).show();
//            displayView(position);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }
    protected void displayView(int pos){
        switch (pos){
            case 1:
                Intent intent = new Intent(StoryTemplates.this, InfoPage.class);
                startActivity(intent);
        }
    }

}

