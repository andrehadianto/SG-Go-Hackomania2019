package com.example.celineyee.kongsimi;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VotingListAdapter extends RecyclerView.Adapter<VotingListAdapter.ViewHolder>{
    private Context mContext;
    private List<VotingList> mData;

    ImageButton upVoteButton;

    ColorStateList myColorStateList = new ColorStateList(
            new int[][]{
                    new int[]{-android.R.attr.state_enabled}
            },
            new int[] {
                    Color.RED
            }
    );


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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final VotingList votingList = mData.get(position);

        holder.textViewTitle.setText(votingList.getTitle());
        holder.textViewOwner.setText(votingList.getOwner());
        holder.textViewRating.setText(String.valueOf(votingList.getVotesNum()));
        holder.imageButton.setBackground(mContext.getResources().getDrawable(votingList.getImage()));
        holder.upVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rating = Integer.parseInt(String.valueOf(holder.textViewRating.getText()));
                rating++;
                holder.textViewRating.setText(String.valueOf(rating));
                holder.upVoteButton.setBackgroundTintList(myColorStateList);
                holder.upVoteButton.setEnabled(false);
            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(mContext, R.raw.kopiaimai);
                if (mediaPlayer.isPlaying() == false){
                    mediaPlayer.start();
                };
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton imageButton,upVoteButton;
        TextView textViewTitle, textViewOwner, textViewRating;

        public ViewHolder(View itemView) {
            super(itemView);
            imageButton = (ImageButton) itemView.findViewById(R.id.imageView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewOwner = (TextView) itemView.findViewById(R.id.textViewOwner);
            textViewRating = (TextView) itemView.findViewById(R.id.textViewRating);
            upVoteButton = itemView.findViewById(R.id.upVoteButton);
        }
    }
}
