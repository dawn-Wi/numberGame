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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.numbergame.R;

public class SignupFragment extends Fragment {

    private LoginViewModel loginViewModel;

    private EditText et_email;
    private EditText et_password;
    private EditText et_passwordCheck;
    private EditText et_displayName;
    private Button bt_checkId;
    private Button bt_signup;

    public SignupFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

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
        bt_checkId = view.findViewById(R.id.signup_bt_checkId);
        bt_signup = view.findViewById(R.id.signup_bt_signup);
        bt_signup.setVisibility(View.INVISIBLE);

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

        loginViewModel.getLoginFormState().observe(getViewLifecycleOwner(), new Observer<LoginFormState>() {
            @Override
            public void onChanged(LoginFormState loginFormState) {
                if (loginFormState.getIdErrorMessage() != null) {
                    et_email.setError(loginFormState.getIdErrorMessage());
                }
                if (loginFormState.getPasswordErrorMessage() != null) {
                    et_password.setError(loginFormState.getPasswordErrorMessage());
                }
                if (loginFormState.getDisplayNameErrorMessage() != null) {
                    et_displayName.setError(loginFormState.getDisplayNameErrorMessage());
                }
                bt_signup.setEnabled(loginFormState.isFieldsValid());
            }
        });

        bt_checkId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!loginViewModel.checkId(et_email.getText().toString())) {
                    Toast.makeText(getActivity().getApplicationContext(), "Email already exist", Toast.LENGTH_SHORT).show();
                    et_email.setText(null);
                    bt_signup.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Available Email", Toast.LENGTH_SHORT).show();
                    bt_signup.setVisibility(View.VISIBLE);
                    et_email.setEnabled(false);
                }
            }
        });


        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!loginViewModel.isPasswordEqual(et_password.getText().toString(), et_passwordCheck.getText().toString())) {
                    Toast.makeText(getActivity().getApplicationContext(), "Password is different", Toast.LENGTH_SHORT).show();
                    et_password.setText(null);
                    et_passwordCheck.setText(null);
                } else {
                    loginViewModel.tryRegister(et_email.getText().toString(), et_password.getText().toString(), et_displayName.getText().toString());
                }

            }
        });


        loginViewModel.registerSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isRegistrationSuccessful) {
                if (isRegistrationSuccessful) {
                    Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(SignupFragment.this).navigate(R.id.action_signupFragment_to_loginFragment);
                    loginViewModel.setRegisterSuccess(false);
                } else {
                    //Toast.makeText(getActivity().getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                    et_email.setText(null);
                    et_password.setText(null);
                }
            }
        });
    }
}