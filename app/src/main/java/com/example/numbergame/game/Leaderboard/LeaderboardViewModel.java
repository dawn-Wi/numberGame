package com.example.numbergame.game.Leaderboard;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.numbergame.Result;
import com.example.numbergame.UserRepository;
import com.example.numbergame.game.GameRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LeaderboardViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<Boolean> competitionRecordsLoaded = new MutableLiveData<>(false);

    private List<GameRecord> recordList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void loadCompetitionRecord(){
        userRepository.getCompetitionRecords(result->{
            if(result instanceof Result.Success){
                recordList = ((Result.Success<List<GameRecord>>)result).getData();
                competitionRecordsLoaded.setValue(true);
                recordList.sort(Comparator.naturalOrder());
            }
        });
    }

    public List<GameRecord> getCompetitionRecordList(){
        return recordList;
    }

    public LiveData<Boolean> competitionRecordsLoaded(){
        return competitionRecordsLoaded;
    }

}