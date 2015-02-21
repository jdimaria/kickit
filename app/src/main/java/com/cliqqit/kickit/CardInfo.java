package com.cliqqit.kickit;

/**
 * Created by jdimaria on 2/21/15.
 */
public class CardInfo {
    protected String name;
    protected String owner;
    protected String location;
    protected String date;
    protected String time;

    CardInfo() {
        name = null;
        owner = null;
        location = null;
        date = null;
        time = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
