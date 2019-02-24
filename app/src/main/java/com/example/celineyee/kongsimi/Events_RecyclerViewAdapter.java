package com.example.celineyee.kongsimi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Events_RecyclerViewAdapter extends RecyclerView.Adapter<Events_RecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private List<Event> mData;

    public Events_RecyclerViewAdapter(Context mContext, List<Event> mData){
        this.mContext = mContext;
        this.mData = new ArrayList<>(mData);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_event,viewGroup,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Event event = mData.get(position);
        viewHolder.event_name.setText(event.getName());
        viewHolder.event_startdate.setText(event.getStartdate());
        if (event.getEnddate().equals("")) {
            viewHolder.event_date_separator.setVisibility(View.GONE);
        }
        viewHolder.event_enddate.setText(event.getEnddate());
        viewHolder.event_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toEvent = new Intent(mContext,EventActivity.class);
                toEvent.putExtra("ID",event.getId());
                toEvent.putExtra("NAME",event.getName());
                toEvent.putExtra("STARTDATE",event.getStartdate());
                toEvent.putExtra("ENDDATE",event.getEnddate());
                toEvent.putExtra("TEXT",event.getText());
                toEvent.putExtra("CHARNAME",event.getChara().getName());
                toEvent.putExtra("CHARDESC",event.getChara().getDescription());
                toEvent.putExtra("CHARTHUMBNAIL",event.getChara().getThumbnail());
                mContext.startActivity(toEvent);
            }
        });
    }

    public int getItemCount() {return mData.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView event_name;
        TextView event_startdate;
        TextView event_date_separator;
        TextView event_enddate;
        CardView event_cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            event_name = (TextView) itemView.findViewById(R.id.events_name);
            event_startdate = (TextView) itemView.findViewById(R.id.events_startdate);
            event_date_separator = (TextView) itemView.findViewById(R.id.event_data_separator);
            event_enddate = (TextView) itemView.findViewById(R.id.events_enddate);
            event_cardview = (CardView) itemView.findViewById(R.id.events_cardview);
        }
    }
}


