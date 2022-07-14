package com.example.numbergame.ui.Leaderboard;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.numbergame.Result;
import com.example.numbergame.UserRepository;
import com.example.numbergame.game.GameRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;

public class LeaderboardViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<Boolean> allRecordsLoaded = new MutableLiveData<>(false);

    private List<GameRecord> recordList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void loadAllRecord(){
        userRepository.getAllRecords(result->{
            if(result instanceof Result.Success){
                recordList = ((Result.Success<List<GameRecord>>)result).getData();
                allRecordsLoaded.setValue(true);
                recordList.sort(Comparator.naturalOrder());
            }
        });
    }

    public List<GameRecord> getAllRecordList(){
        return recordList;
    }

    public LiveData<Boolean> allRecordsLoaded(){
        return allRecordsLoaded;
    }

}