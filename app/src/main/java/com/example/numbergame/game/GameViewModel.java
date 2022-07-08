package com.example.numbergame.game;

import androidx.lifecycle.ViewModel;

import com.example.numbergame.Result;
import com.example.numbergame.UserRepository;

public class GameViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private String getMaxNumber;

    public String getMaxNumber() {
        userRepository.getMaxNumber(result -> {
               getMaxNumber=((Result.Success<String>)result).getData();
        });
        return getMaxNumber;
    }
}
