package com.example.numbergame.game.GameSetting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameSettingViewModel extends ViewModel
{
    private final MutableLiveData<GameSettingFormState> gameFormState = new MutableLiveData<>(new GameSettingFormState(null, false));
    private String maxNumberText = "";
    private int maxNumber;

    public GameSettingViewModel()
    {
    }

    public void onMaxNumberTextChanged(String changedMaxNumber)
    {
        maxNumberText = changedMaxNumber;
        if (!isMaxNumberValid(maxNumberText))
        {
            gameFormState.setValue(new GameSettingFormState("Please enter a number between 20 and 50", false));
        }
        else
        {
            gameFormState.setValue(new GameSettingFormState(null, true));
        }
    }

    private boolean isMaxNumberValid(String maxNumber)
    {
        boolean answer = false;
        if (maxNumber.length() > 0)
        {
            if (Integer.parseInt(maxNumber) < 20 || Integer.parseInt(maxNumber) > 50)
            {
                answer = false;
            }
            else
            {
                answer = true;
            }
        }
        return answer;
    }

    public void setMaxNumberText(String maxNumber)
    {
        if(maxNumber.length() > 0)
        {
            maxNumberText = maxNumber;
            this.maxNumber = Integer.parseInt(maxNumber);
        }
    }

    public LiveData<GameSettingFormState> getGameSettingFormState()
    {
        return gameFormState;
    }

    public int getMaxNumber()
    {
        return maxNumber;
    }

}