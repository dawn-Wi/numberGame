package com.example.numbergame.game;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.numbergame.Result;
import com.example.numbergame.UserRepository;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();

    private MutableLiveData<Boolean> pressedButtonCorrect = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> gameFinished = new MutableLiveData<>();
    private MutableLiveData<GameState> myState = new MutableLiveData<>();

    private int maxNumber;
    private List<GameButtonContent> gameButtonContentList;
    private int currentNumber;

    public void checkPressedButtonIsCorrect(GameButtonContent gameButtonContent) {
        if (gameButtonContent.getValue().equals(String.valueOf(currentNumber))) {
            pressedButtonCorrect.setValue(true);
            checkIfFinished();
            currentNumber++;
        } else {
            pressedButtonCorrect.setValue(false);
        }
    }

    public void addRecord(GameRecord gameRecord) {
        userRepository.addRecord(gameRecord, result -> {
            if (result instanceof Result.Success) {

            }
        });
    }

    public void setupNewGame(int maxNumber) {
        myState.setValue(GameState.LOADING);
        currentNumber = 1;
        this.maxNumber = maxNumber;
        gameButtonContentList = new ArrayList<>();
        for (int i = 0; i < maxNumber; i++) {
            gameButtonContentList.add(new GameButtonContent("" + (i + 1), false));
        }
        Collections.shuffle(gameButtonContentList);
        myState.setValue(GameState.PLAYING);
    }

    private void checkIfFinished() {
        if (currentNumber > maxNumber) {
            gameFinished.setValue(true);
            myState.setValue(GameState.FINISHED);
        } else {
            gameFinished.setValue(false);
        }
    }

    public List<GameButtonContent> getGameButtonContentList() {
        return gameButtonContentList;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public String getLoggedInUserId() {
        return userRepository.getLoggedInUserId();
    }

    public LiveData<Boolean> isPressedButtonCorrect() {
        return pressedButtonCorrect;
    }

    public LiveData<Boolean> isGameFinished() {
        return gameFinished;
    }

    public LiveData<GameState> getGameState(){ return myState;}

    public enum GameState{
        LOADING,
        PLAYING,
        FINISHED
    }
}
