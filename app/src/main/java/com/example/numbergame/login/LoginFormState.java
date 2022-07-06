package com.example.numbergame.login;

public class LoginFormState {
    private final String idErrorMessage;
    private final String passwordErrorMessage;
    private final String displayNameErrorMessage;
    private final boolean isFieldsValid;

    public LoginFormState(String usernameErrorMessage, String passwordErrorMessage, String displayNameErrorMessage, boolean isFieldsValid) {
        this.idErrorMessage = usernameErrorMessage;
        this.passwordErrorMessage = passwordErrorMessage;
        this.displayNameErrorMessage = displayNameErrorMessage;
        this.isFieldsValid = isFieldsValid;
    }

    public String getIdErrorMessage() {
        return idErrorMessage;
    }

    public String getPasswordErrorMessage() {
        return passwordErrorMessage;
    }

    public String getDisplayNameErrorMessage() {
        return displayNameErrorMessage;
    }

    public boolean isFieldsValid() {
        return isFieldsValid;
    }
}
