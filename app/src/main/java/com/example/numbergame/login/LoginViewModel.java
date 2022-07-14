package com.example.numbergame.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.numbergame.Result;
import com.example.numbergame.UserRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();
    private final MutableLiveData<SignupFormState> signupFormState = new MutableLiveData<>(new SignupFormState(null, null, null, null, false));
    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>(new LoginFormState(null,null,false));
    private MutableLiveData<Boolean> doingWork = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> loggedIn = new MutableLiveData<>();

    private String idText = "";
    private String passwordText = "";
    private String passwordCheckText = "";
    private String displayNameText = "";

    private String loginIdText = "";
    private String loginPasswordText="";

    private String loggedName;

    private List<String> userLists;

    public void tryLogin(String id, String password) {
        doingWork.setValue(true);
        userRepository.tryLogin(id, password, result -> {
            if (result instanceof Result.Success) {
                loggedIn.setValue(true);
            } else {
                loggedIn.setValue(false);
            }
            doingWork.setValue(false);
        });

    }

    public void tryRegister(String id, String password, String displayName) {
        userRepository.tryRegister(id, password, displayName, result -> {
            if (result instanceof Result.Success) {
                registerSuccess.postValue(true);
            } else {
                registerSuccess.postValue(false);
            }
        });
    }
    public void onLoginIdTextChanged(String changedId){
        loginIdText = changedId;
        if(changedId.length()==0){
            loginFormState.setValue(new LoginFormState(null,null,false));
        }else if(!isEmailValid(loginIdText)){
            loginFormState.setValue(new LoginFormState("Email format is wrong", loginFormState.getValue().getPasswordErrorMessage(),false));
        }else if(isPasswordValid(loginPasswordText)){
            loginFormState.setValue(new LoginFormState(null,null,true));
        }else if(loginFormState.getValue().getIdErrorMessage()!=null){
            loginFormState.setValue(new LoginFormState(null, loginFormState.getValue().getPasswordErrorMessage(),false));
        }
    }

    public void onLoginPasswordTextChanged(String changedPassword){
        loginPasswordText = changedPassword;
        if(changedPassword.length()==0){
            loginFormState.setValue(new LoginFormState(null,null,false));
        }else if(!isPasswordValid(loginPasswordText)){
            loginFormState.setValue(new LoginFormState(loginFormState.getValue().getIdErrorMessage(), "Password is too short",false));
        }else if(isEmailValid(loginIdText)){
            loginFormState.setValue(new LoginFormState(null,null,true));
        }else if(loginFormState.getValue().getPasswordErrorMessage()!=null){
            loginFormState.setValue(new LoginFormState(loginFormState.getValue().getIdErrorMessage(), null,false));
        }
    }


    public void onIdTextChanged(String changedId) {
        idText = changedId;
        if (changedId.length() == 0) {
            signupFormState.setValue(new SignupFormState(null, null, null, null, false));
        } else if (!isEmailValid(idText)) {
            signupFormState.setValue(new SignupFormState("Email format is wrong", signupFormState.getValue().getPasswordErrorMessage(), signupFormState.getValue().getPasswordCheckErrorMessage(), signupFormState.getValue().getDisplayNameErrorMessage(), false));
        } else if (isPasswordValid(passwordText) && isPasswordEqual(passwordText, passwordCheckText) && isDisplayNameValid(displayNameText)) {
            signupFormState.setValue(new SignupFormState(null, null, null, null, true));
        } else if (signupFormState.getValue().getIdErrorMessage() != null) {
            signupFormState.setValue(new SignupFormState(null, signupFormState.getValue().getPasswordErrorMessage(), signupFormState.getValue().getPasswordCheckErrorMessage(), signupFormState.getValue().getDisplayNameErrorMessage(), false));
        }
    }

    public void onPasswordTextChanged(String changedPassword) {
        passwordText = changedPassword;
        if (changedPassword.length() == 0) {
            signupFormState.setValue(new SignupFormState(null, null, null, null, false));
        } else if (!isPasswordValid(passwordText)) {
            signupFormState.setValue(new SignupFormState(signupFormState.getValue().getIdErrorMessage(), "Password is too short", signupFormState.getValue().getPasswordCheckErrorMessage(), signupFormState.getValue().getDisplayNameErrorMessage(), false));
        } else if (isEmailValid(idText) && isPasswordEqual(passwordText, passwordCheckText) && isDisplayNameValid(displayNameText)) {
            signupFormState.setValue(new SignupFormState(null, null, null, null, true));
        } else if (signupFormState.getValue().getPasswordErrorMessage() != null) {
            signupFormState.setValue(new SignupFormState(signupFormState.getValue().getIdErrorMessage(), null, signupFormState.getValue().getPasswordCheckErrorMessage(), signupFormState.getValue().getDisplayNameErrorMessage(), false));
        }
    }

    public void onPasswordCheckTextChanged(String changedPasswordCheck) {
        passwordCheckText = changedPasswordCheck;
        if (changedPasswordCheck.length() == 0) {
            signupFormState.setValue(new SignupFormState(null, null, null, null, false));
        } else if (!isPasswordEqual(passwordText, passwordCheckText)) {
            signupFormState.setValue(new SignupFormState(signupFormState.getValue().getIdErrorMessage(), signupFormState.getValue().getPasswordErrorMessage(), "Password is wrong", signupFormState.getValue().getDisplayNameErrorMessage(), false));
        } else if (isEmailValid(idText) && isPasswordValid(passwordText) && isDisplayNameValid(displayNameText)) {
            signupFormState.setValue(new SignupFormState(null, null, null, null, true));
        } else if (signupFormState.getValue().getPasswordCheckErrorMessage() != null) {
            signupFormState.setValue(new SignupFormState(signupFormState.getValue().getIdErrorMessage(), signupFormState.getValue().getPasswordErrorMessage(), null, signupFormState.getValue().getDisplayNameErrorMessage(), false));
        }
    }

    public void onDisplayNameTextChanged(String changedDisplayName) {
        displayNameText = changedDisplayName;
        if (changedDisplayName.length() == 0) {
            signupFormState.setValue(new SignupFormState(null, null, null, null, false));
        } else if (!isDisplayNameValid(displayNameText)) {
            signupFormState.setValue(new SignupFormState(signupFormState.getValue().getIdErrorMessage(), signupFormState.getValue().getPasswordErrorMessage(), signupFormState.getValue().getPasswordCheckErrorMessage(), "Name is too short", false));
        } else if (isEmailValid(idText) && isPasswordValid(passwordText)&&isPasswordEqual(passwordText,passwordCheckText)) {
            signupFormState.setValue(new SignupFormState(null, null, null, null, true));
        } else if (signupFormState.getValue().getDisplayNameErrorMessage() != null) {
            signupFormState.setValue(new SignupFormState(signupFormState.getValue().getIdErrorMessage(), signupFormState.getValue().getPasswordErrorMessage(), signupFormState.getValue().getPasswordCheckErrorMessage(), null, false));
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

    public void getId() {
        userRepository.getId(result -> {
            if (result instanceof Result.Success) {
                userLists = ((Result.Success<List<String>>) result).getData();
            }
        });
    }

    public boolean checkId(String id) {
        if (!userLists.contains(id)) {
            return true;
        } else {
            return false;
        }
    }

    public void saveRepositoryUserId(String userId){
        userRepository.saveRepositoryUserId(userId);
    }

    public void setUserId(String userId){
        loggedName=userId;
        saveRepositoryUserId(userId);
    }

    public boolean isDisplayNameValid(String displayName) {
        return !(displayName.length() < 2);
    }

    public LiveData<Boolean> registerSuccess() {
        return registerSuccess;
    }

    public void setRegisterSuccess(Boolean value) {
        registerSuccess.postValue(value);
    }

    public LiveData<SignupFormState> getSignupFormState() {
        return signupFormState;
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<Boolean> getDoingWork() {
        return doingWork;
    }

    public LiveData<Boolean> isLoggedIn() {
        return loggedIn;
    }

}
