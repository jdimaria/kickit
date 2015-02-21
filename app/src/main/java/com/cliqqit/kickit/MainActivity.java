package com.cliqqit.kickit;

//import android.app.ActionBar;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.cliqqit.kickit.CardInfo;
import com.cliqqit.kickit.CardAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
//    private CardView mCardView;
//    private CardAdapter mCardAdapter;
//    private CardAdapter.CardViewHolder mCardViewHolder;
    private JSONObject card1 = new JSONObject();
    private JSONObject card2 = new JSONObject();
    private JSONArray cardData = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);
//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

//        toolbar.setTitle("KickIt");
        Calendar calendar = Calendar.getInstance();
        int calendarDate = calendar.get(calendar.DATE);
//        toolbar.setSubtitle(Integer.toString(calendarDate));

//        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.tool_bar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME, ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setTitle("KickIt");
        actionBar.setSubtitle(Integer.toString(calendarDate));

        ImageButton fabImageButton = (ImageButton) findViewById(R.id.action_button);

        Log.d("MainActivity", "ab: " + actionBar);
        Log.d("MainActivity", "abTitle: " + actionBar.getTitle());
        Log.d("MainActivity", "abSub: " + actionBar.getSubtitle());

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        try {
            card1.put("name", "Hacking Poly");
            card1.put("owner", "Joe");
            card1.put("location", "Cal Poly");
            card1.put("date", "Today");
            card1.put("time", "Now");

            card2.put("name", "Don't Hack Me Bro");
            card2.put("owner", "Joe");
            card2.put("location", "CPP");
            card2.put("date", "Today");
            card2.put("time", "Never");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cardData.put(card1);
        cardData.put(card2);

        Log.d("MainActivity", "cardData: " + cardData.toString());

        // specify an adapter (see also next example)

        mAdapter = new CardAdapter(cardData);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}
