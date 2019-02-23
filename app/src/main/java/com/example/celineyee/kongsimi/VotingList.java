package com.example.celineyee.kongsimi;

public class VotingList{
    private int id, votesNum;
    private String title, owner;
    private int image;

    public VotingList(){
    }

    public VotingList(int id, int votesNum, String title, String owner, int image) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.votesNum = votesNum;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getVotesNum() {
        return votesNum;
    }

    public String getTitle() {
        return title;
    }

    public String getOwner() {
        return owner;
    }

    public int getImage() {
        return image;
    }
}
