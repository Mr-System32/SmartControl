package com.droid.ashref.smartcontrol;

public class DataLiset_OUT_IN {
    String Name;
    String ID;
    public DataLiset_OUT_IN(String ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
