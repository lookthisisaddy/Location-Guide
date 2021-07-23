package com.locationtracker.app.Common.LoginSignUp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.locationtracker.app.R;

public class LoginFragment extends Fragment {

    TextInputLayout emailLayout, passLayout;
    EditText email, pass;
    Button login, forgetPass;
    Animation animation;

    public LoginFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailLayout = view.findViewById(R.id.textInputLayout);
        passLayout = view.findViewById(R.id.textInputLayout2);
        email = view.findViewById(R.id.email_edit_text);
        pass = view.findViewById(R.id.pass_edit_text);
        login = view.findViewById(R.id.login_bt);
        forgetPass = view.findViewById(R.id.forget_bt);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.frag_content_anim);
        emailLayout.setAnimation(animation);
        passLayout.setAnimation(animation);
        login.setAnimation(animation);
        forgetPass.setAnimation(animation);

    }

}
