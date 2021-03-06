package com.example.numbergame.game.PlayGame;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.numbergame.Result;
import com.example.numbergame.UserRepository;
import com.example.numbergame.game.GameButtonContent;
import com.example.numbergame.game.GameRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();

    private MutableLiveData<Boolean> pressedButtonCorrect = new MutableLiveData<>(false);
    private MutableLiveData<GameState> myState = new MutableLiveData<>();

    private int maxNumber;
    private List<GameButtonContent> gameButtonContentList;
    private int currentNumber;
    private long reStartTime, pauseTime;


    public void checkPressedButtonIsCorrect(GameButtonContent gameButtonContent) {
        if (gameButtonContent.getValue().equals(String.valueOf(currentNumber))) {
            pressedButtonCorrect.setValue(true);
            currentNumber++;
            checkIfFinished();
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
        myState.setValue(GameState.PLAYING);
    }

    public void resume() {
        myState.setValue(GameState.PLAYING);
    }

    public void gamePause() {
        myState.setValue(GameState.PAUSE);
    }

    private void checkIfFinished() {
        if (currentNumber > maxNumber) {
            myState.setValue(GameState.FINISHED);
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

    public void setPauseTime(long pauseTime) {
        this.pauseTime = pauseTime;
    }

    public long getPauseTime() {
        return pauseTime;
    }

    public void setReStartTime(long reStartTime) {
        this.reStartTime = reStartTime;
    }

    public long getReStartTime() {
        return reStartTime;
    }


    public LiveData<GameState> getGameState() {
        return myState;
    }

    public enum GameState {
        PLAYING,
        PAUSE,
        FINISHED
    }
}
