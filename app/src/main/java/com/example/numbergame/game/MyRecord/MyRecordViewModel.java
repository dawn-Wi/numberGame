package com.example.numbergame.game.MyRecord;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.numbergame.Result;
import com.example.numbergame.UserRepository;
import com.example.numbergame.game.GameRecord;

import java.util.ArrayList;
import java.util.List;

public class MyRecordViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<Boolean> myRecordsLoaded = new MutableLiveData<>(false);

    private List<GameRecord> recordList = new ArrayList<>();

    public void loadMyRecord(){
        userRepository.getMyRecord(result->{
           if(result instanceof Result.Success){
               recordList = ((Result.Success<List<GameRecord>>)result).getData();
               myRecordsLoaded.setValue(true);
           }
        });
    }

    public List<GameRecord> getMyRecordList(){
        return recordList;
    }

    public LiveData<Boolean> myRecordsLoaded(){
        return myRecordsLoaded;
    }

}