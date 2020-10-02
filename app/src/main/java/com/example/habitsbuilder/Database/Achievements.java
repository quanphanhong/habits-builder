package com.example.habitsbuilder.Database;

import java.io.Serializable;

public class Achievements implements Serializable {
    private int AchieveId;
    private String Name;
    private String Description;
    private int RewardID;
    private String Image;
    private int State;

    public Achievements(){};

    public Achievements(String name, String des, int rewId, String img, int state){
        Name = name;
        Description = des;
        RewardID = rewId;
        Image = img;
        State = state;
    }

    public int GetAchId(){return AchieveId;}
    public String GetAchName(){return Name;}
    public String GetAchDes(){return Description;}
    public int GetAchRewId(){return RewardID;}
    public String GetAchImg(){return Image;}
    public int GetAchState(){return State;}

    public void SetAchId(int id){AchieveId = id;}
    public void SetAchName(String name){Name = name;}
    public void SetAchDes(String des){Description = des;}
    public void SetAchRewId(int rewId){RewardID = rewId;}
    public void SetAchImg(String img){Image = img;}
    public void SetAchState(int state){State = state;}
}
