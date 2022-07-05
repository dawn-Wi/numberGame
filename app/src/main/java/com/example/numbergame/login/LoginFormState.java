package com.example.numbergame.login;

public class LoginFormState {
    private final String usernameErrorMessage;
    private final String passwordErrorMessage;
    private final boolean isFieldsValid;

    public LoginFormState(String usernameErrorMessage, String passwordErrorMessage, boolean isFieldsValid) {
        this.usernameErrorMessage = usernameErrorMessage;
        this.passwordErrorMessage = passwordErrorMessage;
        this.isFieldsValid = isFieldsValid;
    }

    public String getUsernameErrorMessage() {
        return usernameErrorMessage;
    }

    public String getPasswordErrorMessage() {
        return passwordErrorMessage;
    }

    public boolean isFieldsValid() {
        return isFieldsValid;
    }
}
