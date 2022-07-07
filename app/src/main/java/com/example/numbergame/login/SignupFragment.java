package com.example.numbergame.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

import com.example.numbergame.R;

public class SignupFragment extends Fragment {

    private LoginViewModel loginViewModel;

    private EditText et_email;
    private EditText et_password;
    private EditText et_passwordCheck;
    private EditText et_displayName;
    private Button bt_signup;

    public SignupFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(SignupFragment.this).navigate(R.id.action_signupFragment_to_loginFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_email = view.findViewById(R.id.signup_et_email);
        et_password = view.findViewById(R.id.signup_et_password);
        et_passwordCheck = view.findViewById(R.id.signup_et_passwordCheck);
        et_displayName = view.findViewById(R.id.signup_et_displayName);
        bt_signup = view.findViewById(R.id.signup_bt_signup);


        loginViewModel.getId();

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginViewModel.onIdTextChanged(editable.toString());
            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginViewModel.onPasswordTextChanged(editable.toString());
            }
        });

        et_passwordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginViewModel.onPasswordCheckTextChanged(editable.toString());
            }
        });

        et_displayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginViewModel.onDisplayNameTextChanged(editable.toString());
            }
        });


        loginViewModel.getSignupFormState().observe(getViewLifecycleOwner(), new Observer<SignupFormState>() {
            @Override
            public void onChanged(SignupFormState SignupFormState) {
                if (SignupFormState.getIdErrorMessage() != null) {
                    et_email.setError(SignupFormState.getIdErrorMessage());
                }
                if (SignupFormState.getPasswordErrorMessage() != null) {
                    et_password.setError(SignupFormState.getPasswordErrorMessage());
                }
                if (SignupFormState.getPasswordCheckErrorMessage() != null) {
                    et_passwordCheck.setError(SignupFormState.getPasswordCheckErrorMessage());
                }
                if (SignupFormState.getDisplayNameErrorMessage() != null) {
                    et_displayName.setError(SignupFormState.getDisplayNameErrorMessage());
                }
                bt_signup.setEnabled(SignupFormState.isFieldsValid());
            }
        });


        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginViewModel.checkId(et_email.getText().toString())) {
                    loginViewModel.tryRegister(et_email.getText().toString(), et_password.getText().toString(), et_displayName.getText().toString());
                } else {
                    Toast.makeText(requireContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                    et_email.setText("");
                }

            }
        });


        loginViewModel.registerSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isRegistrationSuccessful) {
                if (isRegistrationSuccessful) {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(SignupFragment.this).navigate(R.id.action_signupFragment_to_loginFragment);
                    loginViewModel.setRegisterSuccess(false);
                } else {
                    Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show();
                    et_email.setText("");
                    et_password.setText("");
                }
            }
        });
    }
}