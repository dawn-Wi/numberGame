package com.example.numbergame.login;

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
                if (!isEmailValid(et_email.getText().toString())) {
                    et_email.setTextColor(Color.RED);
                } else {
                    et_email.setTextColor(Color.BLACK);
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
                if(!isPasswordValid(et_password.getText().toString())){
                    et_password.setTextColor(Color.RED);
                }
                else{
                    et_password.setTextColor(Color.BLACK);
                }
            }
        });

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmailValid(et_email.getText().toString())) {
                    Toast.makeText(getActivity().getApplicationContext(), "Email format is wrong", Toast.LENGTH_SHORT).show();
                    et_email.setText(null);
                    et_password.setText(null);
                } else if (isPasswordValid(et_password.getText().toString())) {
                    loginViewModel.tryRegister(et_email.getText().toString(), et_password.getText().toString(), et_displayName.getText().toString());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Password is too short", Toast.LENGTH_SHORT).show();
                    et_password.setText(null);
                }
            }
        });

        loginViewModel.registerSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isRegistrationSuccessful) {
                if (isRegistrationSuccessful) {
                    Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(SignupFragment.this).navigate(R.id.action_signupFragment_to_loginFragment);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                    et_email.setText(null);
                    et_password.setText(null);
                }
            }
        });

    }

    private boolean isEmailValid(String email) {
        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private boolean isPasswordValid(String password) {
        return !(password.length() < 4);
    }


}