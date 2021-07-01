package com.example.pedometerpixeldungeon.mainsrc.pedometer;

import java.util.Date;

public class Pedometer {
    private int id;
    private Date time;
    private int count;
    private int culCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCulCount() {
        return culCount;
    }

    public void setCulCount(int culCount) {
        this.culCount = culCount;
    }
}

