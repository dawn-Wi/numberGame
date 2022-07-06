package com.example.numbergame.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.numbergame.dataSource.FirebaseDataSource;
import com.example.numbergame.R;
import com.example.numbergame.UserRepository;

public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;

    private EditText et_email;
    private EditText et_password;
    private Button bt_login;
    private Button bt_signup;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_email = view.findViewById(R.id.login_et_email);
        et_password = view.findViewById(R.id.login_et_password);
        bt_login = view.findViewById(R.id.login_bt_login);
        bt_signup = view.findViewById(R.id.login_bt_signup);

        loginViewModel.getDoingWork().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isWorking) {
                if (isWorking == true) {
                    et_email.setEnabled(false);
                    et_password.setEnabled(false);
                    bt_login.setEnabled(false);
                    bt_signup.setEnabled(false);
                } else {
                    et_email.setEnabled(true);
                    et_password.setEnabled(true);
                    bt_login.setEnabled(true);
                    bt_signup.setEnabled(true);
                }
            }
        });

        loginViewModel.isLoggedIn().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoggedIn) {
                if (isLoggedIn == true) {
                    NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_gameSettingFragment);
                    Toast.makeText(getActivity().getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    et_email.setText(null);
                    et_password.setText(null);
                }
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginViewModel.tryLogin(et_email.getText().toString(), et_password.getText().toString());
            }
        });

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });


    }
}