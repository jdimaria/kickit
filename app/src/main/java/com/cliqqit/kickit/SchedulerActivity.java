package com.cliqqit.kickit;

//import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import im.delight.android.ddp.MeteorCallback;


public class SchedulerActivity extends ActionBarActivity implements MeteorCallback {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //    private CardView mCardView;
//    private CardAdapter mCardAdapter;
//    private CardAdapter.CardViewHolder mCardViewHolder;
    private JSONArray cardData = new JSONArray();
    private JSONArray unavailable = new JSONArray();
    Context context;
    EditText eName;
    EditText eLoc;
    String name;
    String loc;
    String owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        context = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);

        Calendar calendar = Calendar.getInstance();
        int calendarDate = calendar.get(calendar.DATE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.tool_bar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME, ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setTitle("Pick a Time");
        actionBar.setSubtitle("These are your friends' availabilities");

        ImageButton actionButton = (ImageButton) findViewById(R.id.action_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(R.layout.dialog_create_event);
                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eName = (EditText)findViewById(R.id.event_name);
                        eLoc = (EditText)findViewById(R.id.event_location);
                        name = eName.getText().toString();
                        loc = eLoc.getText().toString();
                        owner = "Joe DiMaria";
                        if (cardData.length() > 0) {
                            insertData();
                        } else {
                            CharSequence text = "You need to select at least one time";
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
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
//            card1.put("time", "Now");
//
//            card2.put("time", "Never");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        cardData.put(card1);
//        cardData.put(card2);

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

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect(int i, String s) {

    }

    @Override
    public void onDataAdded(String collectionName, String documentID, String fieldsJson) {
        Log.d("Poop", "Data added to <"+collectionName+"> in document <"+documentID+">");
        Log.d("Poop", "    Added: "+fieldsJson);
//        TextView tv = (TextView)findViewById(R.id.hello_world);
        try {
            JSONObject jo = new JSONObject(fieldsJson);
            if (jo.has("name")) {
//                tv.append(jo.getString("name") + " at " + jo.getString("location") + "\n");
            } else {
                cardData.put(jo.get("value"));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataChanged(String s, String s2, String s3, String s4) {

    }

    @Override
    public void onDataRemoved(String s, String s2) {

    }

    @Override
    public void onException(Exception e) {

    }

    public void insertData() {
        // insert data into a collection
        Map<String, Object> mInsertValues = new HashMap<String, Object>();
        mInsertValues.put("name", name);
        mInsertValues.put("loc", loc);
        mInsertValues.put("owner", owner);
        mInsertValues.put("time", cardData);
        MainActivity.mMeteor.insert("hangouts", mInsertValues);
    }

}
