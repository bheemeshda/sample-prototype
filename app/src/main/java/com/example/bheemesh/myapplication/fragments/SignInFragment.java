package com.example.bheemesh.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.Utils.Utils;
import com.example.bheemesh.myapplication.entities.SignOnActionType;
import com.example.bheemesh.myapplication.listeners.SignOnEventListener;

/**
 * Created by bheemesh on 5/1/16.
 */
public class SignInFragment extends BaseFragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private EditText editUseName, editPassword;
    private Button signInBtn;
    private TextView signUpTv;
    private SignOnEventListener activityListener;

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        editUseName = (EditText) view.findViewById(R.id.et_user_name);
        editPassword = (EditText) view.findViewById(R.id.et_password);
        signInBtn = (Button) view.findViewById(R.id.bt_sign_in);
        signUpTv = (TextView) view.findViewById(R.id.tv_sign_up);
        setOnclickListener();
        return view;
    }

    private void setOnclickListener() {
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = verifyValues();
                if (verifyValues()) {
                    //TODO(bheemesh)
                    //Please verify the login
                    activityListener.onSignOnAction(SignOnActionType.GO_TO_PROFILE);
                }
            }
        });
        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityListener.onSignOnAction(SignOnActionType.GO_TO_SIGN_UP);
            }
        });
    }

    private boolean verifyValues() {
        String userName = editUseName.getText().toString();
        String passWord = editPassword.getText().toString();

        if (Utils.isEmpty(userName)) {
            Toast.makeText(getActivity(), "Please enter the user name", Toast.LENGTH_LONG).show();
            return false;
        }

        if (Utils.isEmpty(passWord)) {
            Toast.makeText(getActivity(), "Please enter the pass word", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
