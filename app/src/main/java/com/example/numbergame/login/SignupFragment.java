package com.example.numbergame.login;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import android.graphics.Color;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupFragment extends Fragment {

    private LoginViewModel loginViewModel;

    private EditText et_email;
    private EditText et_password;
    private EditText et_passwordCheck;
    private EditText et_displayName;
    private Button bt_signup;

    public SignupFragment() {
    }

    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        bt_signup = view.findViewById(R.id.signup_bt_signup);

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!loginViewModel.isEmailValid(et_email.getText().toString())) {
                    et_email.setError("Email format is wrong");
                }
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
                if (!loginViewModel.isPasswordValid(et_password.getText().toString())) {
                    et_password.setError("Password is too short");
                }
            }
        });


        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!loginViewModel.isEmailValid(et_email.getText().toString())) {
                    Toast.makeText(getActivity().getApplicationContext(), "Email format is wrong", Toast.LENGTH_SHORT).show();
                    et_email.setText(null);
                    et_password.setText(null);
                } else if (!loginViewModel.isPasswordValid(et_password.getText().toString())) {
                    Toast.makeText(getActivity().getApplicationContext(), "Password is too short", Toast.LENGTH_SHORT).show();
                    et_password.setText(null);
                } else if (!loginViewModel.isPasswordEqual(et_password.getText().toString(), et_passwordCheck.getText().toString())) {
                    Toast.makeText(getActivity().getApplicationContext(), "Password is different", Toast.LENGTH_SHORT).show();
                    et_password.setText(null);
                    et_passwordCheck.setText(null);
                } else {
                    loginViewModel.tryRegister(et_email.getText().toString(), et_password.getText().toString(), et_displayName.getText().toString());
                }
            }
        });

        loginViewModel.getLoginFormState()

        loginViewModel.registerSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isRegistrationSuccessful) {
                if (isRegistrationSuccessful) {
                    Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(SignupFragment.this).navigate(R.id.action_signupFragment_to_loginFragment);
                    loginViewModel.setRegisterSuccess(false);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                    et_email.setText(null);
                    et_password.setText(null);
                }
            }
        });
    }
}