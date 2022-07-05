package com.example.numbergame;

public class GameRecord {
    private long timestamp;
    private String userId;
    private long timeTaken;

    public GameRecord(long timestamp, String userId, long timeTaken) {
        this.timestamp = timestamp;
        this.userId = userId;
        this.timeTaken = timeTaken;
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

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }
}