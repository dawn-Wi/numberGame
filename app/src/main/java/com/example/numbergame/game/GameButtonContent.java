package com.example.numbergame.game;

import android.content.Context;

public class GameButtonContent {
    private String value;
    private boolean isClicked;

    public GameButtonContent(String value, boolean isClicked)
    {
        this.value = value;
        this.isClicked = isClicked;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public boolean isClicked()
    {
        return isClicked;
    }

    public void setClicked(boolean clicked)
    {
        isClicked = clicked;
    }
}
