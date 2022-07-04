package com.example.numbergame;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> doingWork = new MutableLiveData<>(false);

    public void tryRegister(String id, String password, String displayname){
        userRepository.tryRegister(id, password,displayname,result->{

        });
    }

    public LiveData<Boolean> registerSuccess(){
        return registerSuccess;
    }

    public LiveData<Boolean> getDoingWork(){
        return doingWork;
    }
}
