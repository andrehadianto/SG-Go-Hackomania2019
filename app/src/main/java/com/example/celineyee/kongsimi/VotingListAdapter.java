package com.example.celineyee.kongsimi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VotingListAdapter extends RecyclerView.Adapter<VotingListAdapter.ViewHolder>{
    private Context mContext;
    private List<VotingList> mData;

    public VotingListAdapter(Context mContext, List<VotingList> mData) {
        this.mContext = mContext;
        this.mData = new ArrayList<>(mData);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.activity_voting_listview,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VotingList votingList = mData.get(position);

        holder.textViewTitle.setText(votingList.getTitle());
        holder.textViewOwner.setText(votingList.getOwner());
        holder.textViewRating.setText(String.valueOf(votingList.getVotesNum()));
        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(votingList.getImage()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewTitle, textViewOwner, textViewRating;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewOwner = (TextView) itemView.findViewById(R.id.textViewOwner);
            textViewRating = (TextView) itemView.findViewById(R.id.textViewRating);
        }
    }
}
