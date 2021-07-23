package com.locationtracker.app.Common.LoginSignUp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.locationtracker.app.R;

public class SignUpFragment extends Fragment {

    EditText username, email, pass;
    Button signup;

    public SignUpFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        username = view.findViewById(R.id.username_edit_text);
        email = view.findViewById(R.id.email_edit_text);
        pass = view.findViewById(R.id.pass_edit_text);

        signup = view.findViewById(R.id.signup_bt);
    }

}
