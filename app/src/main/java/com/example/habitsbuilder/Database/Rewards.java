package com.example.habitsbuilder.Database;

import java.io.Serializable;

public class Rewards implements Serializable {
    private int RewardID;
    private int TreeID;
    private int WaterAmount;

    public Rewards() {}

    public Rewards(int RewardID, int TreeID, int WaterAmount) {
        this.RewardID = RewardID;
        this.TreeID = TreeID;
        this.WaterAmount = WaterAmount;
    }

    public Rewards(int TreeID, int WaterAmount){
        this.TreeID = TreeID;
        this.WaterAmount = WaterAmount;
    };

    public Rewards(int WaterAmount){
        this.WaterAmount = WaterAmount;
    }

    public int getRewardID() {
        return RewardID;
    }

    public int getTreeID() {
        return TreeID;
    }

    public int getWaterAmount() {
        return WaterAmount;
    }

    public void setRewardID(int rewardID) {
        RewardID = rewardID;
    }

    public void setTreeID(int treeID) {
        TreeID = treeID;
    }

    public void setWaterAmount(int waterAmount) {
        WaterAmount = waterAmount;
    }
}
