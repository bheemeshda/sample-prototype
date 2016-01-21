package com.example.bheemesh.myapplication.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.Utils.Constants;
import com.example.bheemesh.myapplication.entities.SignOnActionType;
import com.example.bheemesh.myapplication.fragments.BaseFragment;
import com.example.bheemesh.myapplication.fragments.SignInFragment;
import com.example.bheemesh.myapplication.fragments.SignUpAboutFragment;
import com.example.bheemesh.myapplication.fragments.SignUpFragment;
import com.example.bheemesh.myapplication.listeners.SignOnEventListener;

/**
 * Created by bheemesh on 5/1/16.
 */
public class SignOnActivity extends AppCompatActivity implements SignOnEventListener {

    private FrameLayout container;
    private RelativeLayout progressErrorContainer;
    private ProgressBar progressBar;
    private RelativeLayout errorContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_on);
        container = (FrameLayout) findViewById(R.id.container);
        progressErrorContainer = (RelativeLayout) findViewById(R.id.rv_error_layout);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        errorContainer = (RelativeLayout) findViewById(R.id.rv_error_layout);
        showSignInFragment();
    }

    //Depending on the action type Next action to be decided by switch statement
    @Override
    public void onSignOnAction(SignOnActionType actionType) {
        switch (actionType) {
            case GO_TO_SIGN_IN:
                showSignInFragment();
                break;
            case GO_TO_SIGN_UP:
                showSignUpFragment();
                break;
            case GO_TO_SIGN_UP_ABOUT_NEXT:
                showSignUpAboutFragment();
                break;
            case GO_TO_FORGOT_PASSWORD:
                showForgotPasswordFragment();
                break;
            case GO_TO_PROFILE:
                callProfileActivity();
                break;
        }
    }

    public void showSignInFragment() {
        if (isFinishing()) {
            return;
        }
        SignInFragment fragment = SignInFragment.newInstance();
        addFragment(fragment);
    }

    public void showSignUpFragment() {
        if (isFinishing()) {
            return;
        }
        SignUpFragment fragment = SignUpFragment.newInstance();
        addFragment(fragment);
    }

    public void showSignUpAboutFragment() {
        if (isFinishing()) {
            return;
        }
        SignUpAboutFragment fragment = SignUpAboutFragment.newInstance();
        addFragment(fragment);
    }

    public void showForgotPasswordFragment() {
        if (isFinishing()) {
            return;
        }
        SignInFragment fragment = SignInFragment.newInstance();
        addFragment(fragment);
    }

    private void addFragment(BaseFragment fragment) {
        try {
            getFragmentManager().beginTransaction().replace(R.id.container, fragment,
                    Constants.TAG_FRAGMENT).addToBackStack(Constants.EMPTY_STRING + fragment.getId()).commit();
        } catch (Exception e) {
            close(false);
        }
    }

    private void callProfileActivity(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        BaseFragment fragment = (BaseFragment) getFragmentManager().findFragmentByTag(Constants
                .TAG_FRAGMENT);
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
            getFragmentManager().popBackStackImmediate();
        }
        if (getFragmentManager().getBackStackEntryCount() <= 0) {
            super.onBackPressed();
        }
    }

    public void close(boolean result) {
        finish();
    }

}
