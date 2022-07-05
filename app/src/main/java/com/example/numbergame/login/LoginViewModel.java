package com.example.numbergame.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.numbergame.Result;
import com.example.numbergame.UserRepository;

public class LoginViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();

    public void tryRegister(String id, String password, String displayName) {
        userRepository.tryRegister(id, password, displayName, result -> {
            if (result instanceof Result) {
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
