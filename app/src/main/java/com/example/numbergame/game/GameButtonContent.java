package com.example.numbergame.game;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.numbergame.game.PlayGame.GameViewModel;

public class GameButtonContent {
    private String value;
    private boolean isClicked;
    private ButtonState buttonState= ButtonState.VISIBLE;

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

    public void setButtonState(ButtonState buttonState){this.buttonState = buttonState;}

    public ButtonState getButtonState(){return buttonState;}

    public enum ButtonState{
        INVISIBLE,
        VISIBLE,
        ANIMATING
    }
}
