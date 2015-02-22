package com.cliqqit.kickit;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by jdimaria on 2/21/15.
 */
public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {
//    private JSONArray timeSlotList;
    private HashMap<Integer, Object> timeSlotList = new HashMap<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class TimeSlotViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected static CardView cardView;
        protected static TextView vTime;

        public TimeSlotViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view);
            vTime = (TextView) v.findViewById(R.id.time);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TimeSlotAdapter(
            HashMap<Integer, Object> timeSlotList) {
        this.timeSlotList = timeSlotList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TimeSlotAdapter.TimeSlotViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_time_slot, parent, false);

        return new TimeSlotViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TimeSlotViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try {
            Object timeSlot = timeSlotList.get(position);
            TimeSlotViewHolder.vTime.setText(timeSlot.getString("time"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return timeSlotList.length();
    }
}
