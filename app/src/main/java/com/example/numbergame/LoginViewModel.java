package com.example.numbergame;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> doingWork = new MutableLiveData<>(false);

    public void tryRegister(String id, String password, String displayname){
        doingWork.setValue(true);
        userRepository.tryRegister(id, password,displayname,result->{
            if(result.equals("Success")){
                registerSuccess.postValue(true);
            }
            else{
                registerSuccess.postValue(false);
            }
            doingWork.postValue(false);
        });
    }

    public LiveData<Boolean> registerSuccess(){
        return registerSuccess;
    }

    public LiveData<Boolean> getDoingWork(){
        return doingWork;
    }
}
