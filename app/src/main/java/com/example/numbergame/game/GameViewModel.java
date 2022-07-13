package com.example.numbergame.game;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.numbergame.Result;
import com.example.numbergame.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();

    private MutableLiveData<Boolean> pressedButtonCorrect = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> gameFinished = new MutableLiveData<>();

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
        currentNumber = 1;
        this.maxNumber = maxNumber;
        gameButtonContentList = new ArrayList<>();
        for (int i = 0; i < maxNumber; i++) {
            gameButtonContentList.add(new GameButtonContent("" + (i + 1), false));
        }
        Collections.shuffle(gameButtonContentList);
    }

    private void checkIfFinished() {
        if (currentNumber > maxNumber) {
            gameFinished.setValue(true);
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

    public String getLoggedUserId() {
        return userRepository.sendRepositoryUserId();
    }


    public LiveData<Boolean> isPressedButtonCorrect() {
        return pressedButtonCorrect;
    }

    public LiveData<Boolean> isGameFinished(){
        return gameFinished;
    }
}
