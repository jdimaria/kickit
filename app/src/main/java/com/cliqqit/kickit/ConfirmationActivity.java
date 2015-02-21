package com.cliqqit.kickit;

//import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ConfirmationActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //    private CardView mCardView;
//    private CardAdapter mCardAdapter;
//    private CardAdapter.CardViewHolder mCardViewHolder;
    private JSONObject card1 = new JSONObject();
    private JSONObject card2 = new JSONObject();
    private JSONArray cardData = new JSONArray();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        context = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);

        Calendar calendar = Calendar.getInstance();
        int calendarDate = calendar.get(calendar.DATE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.tool_bar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME, ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setTitle("KickIt");
        actionBar.setSubtitle(Integer.toString(calendarDate));

//        ImageButton actionButton = (ImageButton) findViewById(R.id.action_button);
//        actionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, InviteFriendsActivity.class);
//                startActivity(intent);
//            }
//        });

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
            card1.put("time", "Now");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cardData.put(card1);

        Log.d("MainActivity", "cardData: " + cardData.toString());

        // specify an adapter (see also next example)

        mAdapter = new TimeSlotAdapter(cardData);
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
