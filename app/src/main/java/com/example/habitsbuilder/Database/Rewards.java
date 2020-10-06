package com.example.habitsbuilder.Database;

import java.io.Serializable;

public class Rewards implements Serializable {
    private int RewardID;
    private Integer TreeID;
    private Integer WaterAmount;

    public Rewards() {}

    public Rewards(int RewardID, Integer TreeID, Integer WaterAmount) {
        this.RewardID = RewardID;
        this.TreeID = TreeID;
        this.WaterAmount = WaterAmount;
    }

    public Rewards(Integer TreeID, Integer WaterAmount){
        this.TreeID = TreeID;
        this.WaterAmount = WaterAmount;
    };

    public Rewards(Integer WaterAmount){
        this.WaterAmount = WaterAmount;
    }

    public int getRewardID() {
        return RewardID;
    }

    public Integer getTreeID() {
        return TreeID;
    }

    public Integer getWaterAmount() {
        return WaterAmount;
    }

    public void setRewardID(int rewardID) {
        RewardID = rewardID;
    }

    public void setTreeID(Integer treeID) {
        TreeID = treeID;
    }

    public void setWaterAmount(Integer waterAmount) {
        WaterAmount = waterAmount;
    }
}
