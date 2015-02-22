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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorCallback;


public class MainActivity extends ActionBarActivity implements MeteorCallback {
    public static Meteor mMeteor;
    private TextView noEvents;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
//    private CardView mCardView;
//    private CardAdapter mCardAdapter;
//    private CardAdapter.CardViewHolder mCardViewHolder;
//    private JSONObject card1 = new JSONObject();
//    private JSONObject card2 = new JSONObject();
//    private JSONArray cardData = new JSONArray();
    public boolean eventsInMain = false;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        context = getApplicationContext();
        noEvents = (TextView) findViewById(R.id.no_events);
        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);

//        if (bundle.getString("show_events") != null) {
//            eventsInMain = true;
//        } else {
//            eventsInMain = false;
//        }
//        if (eventsInMain) {
//            // Show events
//            mRecyclerView.setVisibility(View.VISIBLE);
//            noEvents.setVisibility(View.GONE);
//        } else {
//            // Hide events
//            mRecyclerView.setVisibility(View.GONE);
//            noEvents.setVisibility(View.VISIBLE);
//        }


        // create a new instance (protocol version in second parameter is optional)
        mMeteor = new Meteor("ws://kickit.meteor.com/websocket");

        // register the callback that will handle events and receive messages
        mMeteor.setCallback(this);

        Calendar calendar = Calendar.getInstance();
        int calendarDate = calendar.get(calendar.DATE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.tool_bar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME, ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setTitle("KickIt");
        actionBar.setSubtitle(Integer.toString(calendarDate));

        ImageButton actionButton = (ImageButton) findViewById(R.id.action_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InviteFriendsActivity.class);
                startActivity(intent);
            }
        });

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

//        try {
//            card1.put("name", "Hacking Poly");
//            card1.put("owner", "Joe");
//            card1.put("location", "Cal Poly");
//            card1.put("date", "Today");
//            card1.put("time", "Now");
//
//            card2.put("name", "Don't Hack Me Bro");
//            card2.put("owner", "Joe");
//            card2.put("location", "CPP");
//            card2.put("date", "Today");
//            card2.put("time", "Never");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        cardData.put(card1);
//        cardData.put(card2);
//
//        Log.d("MainActivity", "cardData: " + cardData.toString());

        // specify an adapter (see also next example)

//        mAdapter = new CardAdapter(cardData);
//        mRecyclerView.setAdapter(mAdapter);
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

    @Override
    public void onConnect() {
        Log.d("Poop","Connected");
    }

    @Override
    public void onDisconnect(int code, String reason) {
        Log.d("Poop", "Disconnected: " + reason);
    }

    @Override
    public void onDataAdded(String collectionName, String documentID, String fieldsJson) {
        Log.d("Poop", "Data added to <"+collectionName+"> in document <"+documentID+">");
        Log.d("Poop", "    Added: "+fieldsJson);
//        TextView tv = (TextView)findViewById(R.id.hello_world);
//        try {
//            JSONObject jo = new JSONObject(fieldsJson);
//            if (jo.has("name")) {
//                tv.append(jo.getString("name") + " at " + jo.getString("location") + "\n");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onDataChanged(String collectionName, String documentID, String updatedValuesJson, String removedValuesJson) {
        Log.d("Poop", "Data changed in <"+collectionName+"> in document <"+documentID+">");
        Log.d("Poop", "    Updated: "+updatedValuesJson);
        Log.d("Poop", "    Removed: "+removedValuesJson);
        // TODO Update value
    }

    @Override
    public void onDataRemoved(String collectionName, String documentID) {
        Log.d("Poop", "Data removed from <" + collectionName + "> in document <" + documentID + ">");
        // TODO Remove value
    }

    @Override
    public void onException(Exception e) {
        Log.d("Poop", "Exception");
        if (e != null) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMeteor.disconnect();
    }

    public void insertData(View view) {
        // insert data into a collection
        Map<String, Object> mInsertValues = new HashMap<String, Object>();
//        EditText et = (EditText)findViewById(R.id.event_name);
//        mInsertValues.put("name", et.getText().toString());
//        et = (EditText)findViewById(R.id.event_location);
//        mInsertValues.put("location", et.getText().toString());
//        mMeteor.insert("hangouts", mInsertValues);
    }
}
