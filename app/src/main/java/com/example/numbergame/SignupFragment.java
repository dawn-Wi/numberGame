package com.example.numbergame;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupFragment extends Fragment {
    LoginViewModel loginViewModel;
    EditText signup_et_email;
    EditText signup_et_password;
    EditText signup_et_displayname;
    Button signup_bt_go;

    public SignupFragment() {
        // Required empty public constructor
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
        FirebaseDataSource ds = new FirebaseDataSource();
        UserRepository.getInstance().setDataSource(ds);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        signup_et_email = view.findViewById(R.id.signup_et_email);
        signup_et_password = view.findViewById(R.id.signup_et_password);
        signup_et_displayname = view.findViewById(R.id.signup_et_displayname);
        signup_bt_go = view.findViewById(R.id.signup_bt_go);

        signup_bt_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean returnValue = false;
                String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(signup_et_email.getText().toString());
                if(m.matches()) {
                    returnValue = true;
                }
                if(returnValue == false) {
                    Toast.makeText(getActivity().getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                }
                //password에 4글자 이상인지
                else if(signup_et_password.getText().toString().trim().length() < 4) {
                    //실패
                    Toast.makeText(getActivity().getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                }
                else{
                    loginViewModel.tryRegister(signup_et_email.getText().toString(),signup_et_password.getText().toString(), signup_et_displayname.getText().toString());

                }
            }
        });

        loginViewModel.registerSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean registersuccessing) {
                if(registersuccessing){
                    NavHostFragment.findNavController(SignupFragment.this).navigate(R.id.action_signupFragment_to_loginFragment);
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                    signup_et_email.setText(null);
                    signup_et_password.setText(null);
                }
            }
        });

    }
}