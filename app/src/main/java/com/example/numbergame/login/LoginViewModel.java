package com.example.numbergame.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.numbergame.Result;
import com.example.numbergame.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();
    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>(new LoginFormState(null, null, false));

    private String idText = "";
    private String passwordText = "";

    public void tryRegister(String id, String password, String displayName) {
        userRepository.tryRegister(id, password, displayName, result -> {
            if (result instanceof Result.Success) {
                registerSuccess.postValue(true);
            } else {
                registerSuccess.postValue(false);
            }
        });
    }

    public void onIdTextChanged(String changedId) {
        idText = changedId;
        if (changedId.length() == 0) {

        } else if (!isEmailValid(idText)) {
            loginFormState.setValue(new LoginFormState("email format is wrong", loginFormState.getValue().getPasswordErrorMessage(), false));
        } else if (isPasswordValid(passwordText)) {
            loginFormState.setValue(new LoginFormState(null, null, true));
        } else if (loginFormState.getValue().getUsernameErrorMessage()!=null){
            loginFormState.setValue(new LoginFormState(null, loginFormState.getValue().getPasswordErrorMessage(),false));
        }
    }

    public boolean isEmailValid(String email) {
        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean isPasswordValid(String password) {
        return !(password.length() < 4);
    }

    public boolean isPasswordEqual(String password, String passwordCheck) {
        if (password.equals(passwordCheck)) {
            return true;
        } else {
            return false;
        }
    }

    public LiveData<Boolean> registerSuccess() {
        return registerSuccess;
    }

    public void setRegisterSuccess(Boolean value) {
        registerSuccess.setValue(value);
    }


}
