package com.example.numbergame.game;

import androidx.lifecycle.ViewModel;

import com.example.numbergame.Result;
import com.example.numbergame.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private int maxNumber;
    private List<GameButtonContent> gameButtonContentList;

    //
    //
    //
    //
    //
    //

    public void setupNewGame(int maxNumber)
    {
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
}
