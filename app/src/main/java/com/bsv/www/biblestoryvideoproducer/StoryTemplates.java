package com.bsv.www.biblestoryvideoproducer;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;

public class StoryTemplates extends AppCompatActivity {
    ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ArrayList<NavItem> mNavItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_templates);

        String[] aNavTitles  = getResources().getStringArray(R.array.nav_labels);
        TypedArray aNavIcons = getResources().obtainTypedArray(R.array.nav_icons);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        for(int i = 0; i < aNavTitles.length; i++){
            mNavItems.add(new NavItem(aNavTitles[i], aNavIcons.getResourceId(i, -1)));
        }
        aNavIcons.recycle();

        mDrawerLayout = (DrawerLayout)findViewById(R.id.nav_layout);
        mDrawerList = (ListView) findViewById(R.id.nav_list);

        NavItemAdapter adapter = new NavItemAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.nav_open, R.string.title_activity_story_templates) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.menu_search).setVisible(!drawerOpen);
        menu.findItem(R.id.menu_info).setVisible(!drawerOpen);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_info) {
            Intent intent;
            intent = new Intent(getApplicationContext(), InfoPage.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }
}

