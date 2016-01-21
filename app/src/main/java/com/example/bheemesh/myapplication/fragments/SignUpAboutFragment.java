package com.example.bheemesh.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.Utils.Utils;
import com.example.bheemesh.myapplication.entities.SignOnActionType;
import com.example.bheemesh.myapplication.listeners.SignOnEventListener;
import com.example.bheemesh.myapplication.singleton.SSO;

/**
 * Created by bheemesh on 5/1/16.
 */
public class SignUpAboutFragment extends BaseFragment {
    private EditText editAbout;
    private Button nextBtn;
    private TextView signInTv;
    private SignOnEventListener activityListener;

    public static SignUpAboutFragment newInstance() {
        SignUpAboutFragment fragment = new SignUpAboutFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up_about, container, false);
        editAbout = (EditText) view.findViewById(R.id.et_about);
        nextBtn = (Button) view.findViewById(R.id.bt_next);
        signInTv = (TextView) view.findViewById(R.id.tv_sign_up);
        setOnclickListener();
        return view;
    }

    private void setOnclickListener() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityListener.onSignOnAction(SignOnActionType.GO_TO_PROFILE);
            }
        });
        signInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityListener.onSignOnAction(SignOnActionType.GO_TO_SIGN_IN);
            }
        });
    }

    private void updateUserDetails() {
        String about = editAbout.getText().toString();
        about = Utils.isEmpty(about) ? getResources().getString(R.string.something_about_you) : about;
        SSO.setUserAbout(about);
    }
}
