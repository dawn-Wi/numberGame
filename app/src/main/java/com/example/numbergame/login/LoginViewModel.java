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
    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>(new LoginFormState(null, null, null, false));

    private String idText = "";
    private String passwordText = "";
    private String displayNameText = "";

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
            loginFormState.setValue(new LoginFormState(null, null, null, false));
        } else if (!isEmailValid(idText)) {
            loginFormState.setValue(new LoginFormState("Email format is wrong", loginFormState.getValue().getPasswordErrorMessage(), loginFormState.getValue().getDisplayNameErrorMessage(), false));
        } else if (isPasswordValid(passwordText)) {
            loginFormState.setValue(new LoginFormState(null, null, null, true));
        } else if (loginFormState.getValue().getIdErrorMessage() != null) {
            loginFormState.setValue(new LoginFormState(null, loginFormState.getValue().getPasswordErrorMessage(), loginFormState.getValue().getDisplayNameErrorMessage(), false));
        }
    }

    public void onPasswordTextChanged(String changedPassword) {
        passwordText = changedPassword;
        if (changedPassword.length() == 0) {
            loginFormState.setValue(new LoginFormState(null, null, null, false));
        } else if (!isPasswordValid(passwordText)) {
            loginFormState.setValue(new LoginFormState(loginFormState.getValue().getIdErrorMessage(), "Password is too short", loginFormState.getValue().getDisplayNameErrorMessage(), false));
        } else if (isEmailValid(idText)) {
            loginFormState.setValue(new LoginFormState(null, null, null, true));
        } else if (loginFormState.getValue().getPasswordErrorMessage() != null) {
            loginFormState.setValue(new LoginFormState(loginFormState.getValue().getIdErrorMessage(), null, loginFormState.getValue().getDisplayNameErrorMessage(), false));
        }
    }

    public void onDisplayNameTextChanged(String changedDisplayName) {
        displayNameText = changedDisplayName;
        if (changedDisplayName.length() == 0) {
            loginFormState.setValue(new LoginFormState(null, null, null, false));
        } else if (!isDisplayNameValid(displayNameText)) {
            loginFormState.setValue(new LoginFormState(loginFormState.getValue().getIdErrorMessage(), loginFormState.getValue().getPasswordErrorMessage(), "Name is too short", false));
        } else if (isDisplayNameValid(displayNameText)) {
            loginFormState.setValue(new LoginFormState(null, null, null, true));
        } else if (loginFormState.getValue().getDisplayNameErrorMessage() != null) {
            loginFormState.setValue(new LoginFormState(loginFormState.getValue().getIdErrorMessage(), loginFormState.getValue().getPasswordErrorMessage(), null, false));
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

    public boolean isDisplayNameValid(String displayName) {
        return !(displayName.length() < 2);
    }

    public LiveData<Boolean> registerSuccess() {
        return registerSuccess;
    }

    public void setRegisterSuccess(Boolean value) {
        registerSuccess.setValue(value);
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }


}
