package com.example.numbergame.game;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.numbergame.Result;
import com.example.numbergame.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<Boolean> checkedNumber = new MutableLiveData<>(false);

    private int maxNumber;
    private List<GameButtonContent> gameButtonContentList;
    private int currentNumber;

    public void isNumberValid(GameButtonContent gameButtonContent){
        if(gameButtonContent.getValue().equals(String.valueOf(currentNumber))){
            currentNumber++;
            checkedNumber.setValue(true);
        }else{
            checkedNumber.setValue(false);
        }
    }
    //
    //
    //
    //
    //

    public void setupNewGame(int maxNumber)
    {
        currentNumber=1;
        this.maxNumber = maxNumber;
        gameButtonContentList = new ArrayList<>();
        for(int i=0; i<maxNumber; i++)
        {
            gameButtonContentList.add(new GameButtonContent("" + (i+1),false ));
        }
        Collections.shuffle(gameButtonContentList);
    }

    public List<GameButtonContent> getGameButtonContentList()
    {
        return gameButtonContentList;
    }

    public int getMaxNumber()
    {
        return maxNumber;
    }

    public LiveData<Boolean> isCheckNumber(){return checkedNumber;}
}
