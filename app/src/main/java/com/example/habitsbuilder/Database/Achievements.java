package com.example.habitsbuilder.Database;

import java.io.Serializable;

public class Achievements implements Serializable {
    private long AchieveId;
    private String Name;
    private String Description;
    private long RewardID;
    private String Image;
    private int State;

    Achievements(){};

    Achievements(String name, String des, long rewId, String img, int state){
        Name = name;
        Description = des;
        RewardID = rewId;
        Image = img;
        State = state;
    }

    public long GetAchId(){return AchieveId;}
    public String GetAchName(){return Name;}
    public String GetAchDes(){return Description;}
    public long GetAchRewId(){return RewardID;}
    public String GetAchImg(){return Image;}
    public int GetAchState(){return State;}

    public void SetAchName(String name){Name = name;}
    public void SetAchDes(String des){Description = des;}
    public void SetAchRewId(long rewId){RewardID = rewId;}
    public void SetAchImg(String img){Image = img;}
    public void SetAchState(int state){State = state;}
}
