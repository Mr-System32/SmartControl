package com.droid.ashref.smartcontrol;

public class DataList_Room_Device {


    String ID;
    String Name;
    String Status;
    String Power;
    String Port;
    String Hours;
    String Image;

    public DataList_Room_Device(String ID, String name, String status, String power, String port, String hours, String image) {
        this.ID = ID;
        this.Name = name;
        this. Status = status;
        this.  Power = power;
        this. Port = port;
        this. Hours = hours;
        this. Image = image;
    }


    public String getHours() {
        return Hours;
    }

    public void setHours(String hours) {
        Hours = hours;
    }


    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPower() {
        return Power;
    }

    public void setPower(String power) {
        Power = power;
    }

    public String getPort() {
        return Port;
    }

    public void setPort(String port) {
        Port = port;
    }
}
