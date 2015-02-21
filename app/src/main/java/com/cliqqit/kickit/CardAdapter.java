package com.cliqqit.kickit;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jdimaria on 2/21/15.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private JSONArray cardList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected static TextView vName;
        protected static TextView vOwner;
        protected static TextView vLoc;
        protected static TextView vDate;
        protected static TextView vTime;

        public CardViewHolder(View v) {
            super(v);
            vName = (TextView) v.findViewById(R.id.name);
            vOwner = (TextView) v.findViewById(R.id.owner);
            vLoc = (TextView) v.findViewById(R.id.location);
            vDate = (TextView) v.findViewById(R.id.date);
            vTime = (TextView) v.findViewById(R.id.time);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardAdapter(JSONArray cardList) {
        this.cardList = cardList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_card, parent, false);

        return new CardViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        CardInfo ci = cardList.get(position);
//        CardViewHolder.vName.setText(ci.name);
//        CardViewHolder.vOwner.setText(ci.owner);
//        CardViewHolder.vLoc.setText(ci.location);
//        CardViewHolder.vDate.setText(ci.date);
//        CardViewHolder.vTime.setText(ci.time);
        try {
            JSONObject card = cardList.getJSONObject(position);
            Log.d("CardAdapter", "card: " + card.toString());
            CardViewHolder.vName.setText(card.getString("name"));
            CardViewHolder.vOwner.setText(card.getString("owner"));
            CardViewHolder.vLoc.setText(card.getString("location"));
            CardViewHolder.vDate.setText(card.getString("date"));
            CardViewHolder.vTime.setText(card.getString("time"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cardList.length();
    }
}
