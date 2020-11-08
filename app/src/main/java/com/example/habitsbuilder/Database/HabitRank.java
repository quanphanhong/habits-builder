package com.example.habitsbuilder.Database;

import java.io.Serializable;

public class HabitRank implements Serializable {
    private int RankId;
    private String Name;
    private String Description;
    private String Image;
    private int achieveScore;

    public HabitRank(){};

    public HabitRank(String name, String des, String img, int score){
        Name = name;
        Description = des;
        Image = img;
        achieveScore = score;
    }

    public int getRankId() {
        return RankId;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getImage() {
        return Image;
    }

    public int getAchieveScore() {
        return achieveScore;
    }

    public void setRankId(int rankId) {
        RankId = rankId;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setAchieveScore(int score) {
        achieveScore = score;
    }
}
