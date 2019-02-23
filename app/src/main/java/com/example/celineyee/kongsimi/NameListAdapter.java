package com.example.celineyee.kongsimi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class NameListAdapter extends RecyclerView.Adapter<NameListAdapter.NameListViewHolder> {
    LayoutInflater mInflater;
    Context context;

    public NameListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public NameListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.name_card, viewGroup, false);
        return new NameListViewHolder(itemView, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull NameListViewHolder nameListViewHolder, int i) {
        nameListViewHolder.textViewRank.setText(Integer.toString(i+1));
        nameListViewHolder.imageViewProfile.setImageResource(R.drawable.profile3);
        nameListViewHolder.textViewUsername.setText("User" + Integer.toString(i));
        nameListViewHolder.textViewPoints.setText(Integer.toString(85));
    }

    @Override
    public int getItemCount() {
        int numberOfRows = 5;
        return numberOfRows;
    }

    static class NameListViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewRank;
        public ImageView imageViewProfile;
        public TextView textViewUsername;
        public TextView textViewPoints;

        public NameListViewHolder(View view, final Context context){
            super(view);
            textViewRank = view.findViewById(R.id.cardViewRank);
            imageViewProfile = view.findViewById(R.id.cardViewProfileImage);
            textViewUsername = view.findViewById(R.id.cardViewUsername);
            textViewPoints = view.findViewById(R.id.cardViewPoints);
        }
    }
}