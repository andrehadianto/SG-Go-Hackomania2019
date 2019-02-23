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
    private int[] imageList = new int[]{
            R.drawable.profile1,
            R.drawable.profile2,
            R.drawable.profile3,
            R.drawable.profile4,
            R.drawable.profile5
    };
    private int[] scoreList = new int[]{93, 85, 80, 79, 77};
    private String[] nameList = new String[]{"Andre", "Claire", "Nikos", "Celine", "TayTS"};

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
        nameListViewHolder.imageViewProfile.setImageResource(imageList[i]);
        nameListViewHolder.textViewUsername.setText(nameList[i]);
        nameListViewHolder.textViewPoints.setText(String.valueOf(scoreList[i]));
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