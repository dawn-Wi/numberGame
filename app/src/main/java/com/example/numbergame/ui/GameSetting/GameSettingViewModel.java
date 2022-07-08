package com.example.numbergame.ui.GameSetting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.numbergame.Result;
import com.example.numbergame.UserRepository;

public class GameSettingViewModel extends ViewModel {
    private final UserRepository userRepository = UserRepository.getInstance();
    private final MutableLiveData<GameSettingFormState> gameFormState = new MutableLiveData<>(new GameSettingFormState(null,  false));
    private final MutableLiveData<Boolean> savingMaxNumber = new MutableLiveData<>();

    private String maxNumberText = "";


    private final MutableLiveData<String> mText;

    public GameSettingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void onMaxNumberTextChanged(String changedMaxNumber) {
        maxNumberText = changedMaxNumber;
        if (!isMaxNumberValid(maxNumberText)) {
            gameFormState.setValue(new GameSettingFormState("Please enter a number between 20 and 50", false));
        } else{
            gameFormState.setValue(new GameSettingFormState(null,  true));
        }
    }



    public boolean isMaxNumberValid(String maxNumber) {
        boolean answer=false;
        if(maxNumber.toString().length()>0){
            if(Integer.parseInt(maxNumber.toString())<20 || Integer.parseInt(maxNumber.toString())>50){
                answer=false;
            }else {
                answer=true;
            }
        }
        return answer;
    }

    public void setMaxNumber(String maxNumber){
        userRepository.setMaxNumber(maxNumber,result -> {
            if (result instanceof Result.Success) {
                savingMaxNumber.setValue(true);
            } else {
                savingMaxNumber.setValue(false);
            }
        });
    }

    public LiveData<GameSettingFormState> getGameSettingFormState() {
        return gameFormState;
    }


    public LiveData<Boolean> isSaved() {
        return savingMaxNumber;
    }

}