package com.bsv.www.biblestoryvideoproducer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class InfoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);

        getInfo();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getInfo() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        /*set the text for each of the different textview ids*/
        String contents = getResources().getString(R.string.setup_langcode_hint, "");
        TextView chooseView = (TextView) findViewById(R.id.lcode_tv);
        chooseView.setText(contents);
        contents = getResources().getString(R.string.setup_countries_hint, "");
        chooseView = (TextView) findViewById(R.id.country_tv);
        chooseView.setText(contents);
        contents = getResources().getString(R.string.setup_consemail_hint, "");
        chooseView = (TextView) findViewById(R.id.cemail_tv);
        chooseView.setText(contents);
        contents = getResources().getString(R.string.setup_consname_hint, "");
        chooseView = (TextView) findViewById(R.id.cname_tv);
        chooseView.setText(contents);
        contents = getResources().getString(R.string.setup_consphone_hint, "");
        chooseView = (TextView) findViewById(R.id.cphone_tv);
        chooseView.setText(contents);
        contents = getResources().getString(R.string.setup_teamcontact_hint, "");
        chooseView = (TextView) findViewById(R.id.tcontact_tw);
        chooseView.setText(contents);
        contents = getResources().getString(R.string.setup_teamname_hint, "");
        chooseView = (TextView) findViewById(R.id.tname_tw);
        chooseView.setText(contents);

    }


}
