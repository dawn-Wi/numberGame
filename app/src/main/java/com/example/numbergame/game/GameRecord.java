package com.example.numbergame.game;

public class GameRecord {
    private long timestamp;
    private String userId;
    private int buttonNum;

    public GameRecord(long timestamp, String userId,int buttonNum) {
        this.timestamp = timestamp;
        this.userId = userId;
        this.buttonNum = buttonNum;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getButtonNum() {
        return buttonNum;
    }

    public void setButtonNum(int buttonNum) {
        this.buttonNum = buttonNum;
    }

}