package com.example.numbergame.ui.GameSetting;

public class GameSettingFormState {
    private final String maxNumberErrorMessage;
    private final boolean isFieldsValid;

    public GameSettingFormState(String maxNumberErrorMessage, boolean isFieldsValid) {
        this.maxNumberErrorMessage = maxNumberErrorMessage;
        this.isFieldsValid = isFieldsValid;
    }

    public String getMaxNumberErrorMessage() {
        return maxNumberErrorMessage;
    }

    public boolean isFieldsValid() {
        return isFieldsValid;
    }
}
