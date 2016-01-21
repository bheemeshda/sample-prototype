package com.example.bheemesh.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.Utils.Utils;
import com.example.bheemesh.myapplication.entities.LoginType;
import com.example.bheemesh.myapplication.entities.SignOnActionType;
import com.example.bheemesh.myapplication.listeners.SignOnEventListener;
import com.example.bheemesh.myapplication.singleton.SSO;

/**
 * Created by bheemesh on 5/1/16.
 */
public class SignUpFragment extends BaseFragment {
    private EditText editUseName, editPassword, editEmail, editBirthday;
    private Button joinBtn;
    private TextView signInTv;
    private SignOnEventListener activityListener;

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        try {
            activityListener = (SignOnEventListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement SignOnEventListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        editUseName = (EditText) view.findViewById(R.id.et_user_name);
        editEmail = (EditText) view.findViewById(R.id.et_email);
        editPassword = (EditText) view.findViewById(R.id.et_password);
        editBirthday = (EditText) view.findViewById(R.id.et_birth_date);

        joinBtn = (Button) view.findViewById(R.id.bt_sign_up);
        signInTv = (TextView) view.findViewById(R.id.tv_sign_in);
        setOnclickListener();
        return view;
    }


    private void setOnclickListener() {
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserDetails();
                activityListener.onSignOnAction(SignOnActionType.GO_TO_SIGN_UP_ABOUT_NEXT);
            }
        });
        signInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityListener.onSignOnAction(SignOnActionType.GO_TO_SIGN_IN);
            }
        });
    }

    private void updateUserDetails(){
        String name = editUseName.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
//        String birthday = editBirthday.getText().toString();

        name = Utils.isEmpty(name)? "default name change it": name;
        email = Utils.isEmpty(email)? "abcd@abcd.com change it": email;
        password = Utils.isEmpty(password)? "default password": password;

        SSO.setUserID(name);
        SSO.setUserName(email);
        SSO.setUserFirstName(name);
        SSO.setUserLastName("");
        SSO.setUserPassword(password);
        SSO.setLoginType(LoginType.EMAIL);
    }
}
