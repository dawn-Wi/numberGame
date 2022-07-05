package com.example.numbergame;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();

    public void tryRegister(String id, String password, String displayname) {
        userRepository.tryRegister(id, password, displayname, result -> {
            if (result.equals("Success")) {
                registerSuccess.postValue(true);
            } else {
                registerSuccess.postValue(false);
            }
        });
    }

    public LiveData<Boolean> registerSuccess() {
        return registerSuccess;
    }

}
