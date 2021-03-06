package com.bsv.www.storyproducer;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new StoryFrag()).commit();
//        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_bg_trans, getTheme()));
        setupNavDrawer();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        if(drawerOpen || hideIcon) {
            menu.findItem(R.id.menu_lang).setVisible(false);
            menu.findItem(R.id.menu_play).setVisible(true);
        } else {
            menu.findItem(R.id.menu_lang).setVisible(true);
            menu.findItem(R.id.menu_play).setVisible(false);
        }
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
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        if(item.getItemId() == R.id.menu_lang){
            Snackbar.make(getCurrentFocus(), "Languages", Snackbar.LENGTH_LONG).show();
        }
            return super.onOptionsItemSelected(item);
    }

    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavItem> mNavItems = new ArrayList<>();
    private String mActivityTitle;

    private void setupNavDrawer(){
        mActivityTitle = getTitle().toString();
        String[] aNavTitles = getResources().getStringArray(R.array.nav_labels);
        TypedArray aNavIcons = getResources().obtainTypedArray(R.array.nav_icons);
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
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position == 0) {
                FragmentManager fm = getFragmentManager();
                Toast.makeText(getBaseContext(), "In backstack " + fm.getBackStackEntryCount(), Toast.LENGTH_LONG).show();
                for(int entry = 0; entry < fm.getBackStackEntryCount(); entry++){
                    Toast.makeText(getBaseContext(), "Found fragment: " + fm.getBackStackEntryAt(entry).getId(), Toast.LENGTH_LONG).show();
                }
//                startFragment(position, 0, "");
            }
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }
    private boolean hideIcon = false;
    private PagerFrag pagerFrag;
    public void startFragment(int iFragNum, int slideCount, String storyName){
        String title = "";
        Fragment fragment = null;
        switch (iFragNum) {
            case 0:
                fragment = new StoryFrag();
                title=getApplicationContext().getString(R.string.title_activity_story_templates);
                hideIcon = false;
                break;
            case 1:
                pagerFrag = PagerFrag.newInstance(slideCount, iFragNum, storyName);
                fragment = pagerFrag;
                title=getApplicationContext().getString(R.string.title_fragment_translate);
                hideIcon = true;
                break;
            case 2:
                pagerFrag= PagerFrag.newInstance(slideCount, iFragNum, storyName);
                title=getApplicationContext().getString(R.string.title_fragment_community);
                hideIcon = true;
                break;
            case 3:
                fragment = PagerFrag.newInstance(slideCount, iFragNum, storyName);
                title=getApplicationContext().getString(R.string.title_fragment_consultant);
                hideIcon = true;
                break;

        }
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, title).addToBackStack(null).commit();
            mActivityTitle = title;
            getSupportActionBar().setTitle(title);
            invalidateOptionsMenu();
        }
    }
    public void changeSlide(int slidePosition){
        if(pagerFrag != null) {
            pagerFrag.changeView(slidePosition);
        }
    }
}

