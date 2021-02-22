package com;

import java.util.ArrayList;

public class User {

    public String id;
    public long startTime, endTime, lastTime;
    public boolean started, finished;
    public final ArrayList<Solve> solves = new ArrayList<>();

    public User(String id) {
        this.id = id;
    }
}
