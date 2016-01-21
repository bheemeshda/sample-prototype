package com.example.bheemesh.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.entities.LoginType;
import com.example.bheemesh.myapplication.listeners.ActivityEventListener;
import com.example.bheemesh.myapplication.singleton.SSO;

/**
 * Created by bheemesh on 5/1/16.
 */
public class ProfileGeneralFragment extends BaseFragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private TextView tvName, tvEmail, tvAbout;
    private LinearLayout nameContainer, emailContainer, aboutContainer;
    private ActivityEventListener activityListener;

    public static ProfileAboutFragment newInstance() {
        ProfileAboutFragment fragment = new ProfileAboutFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        try {
            activityListener = (ActivityEventListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement SignOnEventListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_about, container, false);
        nameContainer = (LinearLayout) view.findViewById(R.id.lv_name_container);
        emailContainer = (LinearLayout) view.findViewById(R.id.lv_email_container);
        aboutContainer = (LinearLayout) view.findViewById(R.id.lv_about_container);

        tvName = (TextView) nameContainer.findViewById(R.id.tv_sub_title);
        tvEmail = (TextView) emailContainer.findViewById(R.id.tv_sub_title);
        tvAbout = (TextView) aboutContainer.findViewById(R.id.tv_sub_title);

        prepareView();
        return view;
    }

    private void prepareView() {
        ((TextView) nameContainer.findViewById(R.id.tv_title)).setText(getResources()
                .getString(R.string.name));
        ((TextView) aboutContainer.findViewById(R.id.tv_title)).setText(getResources()
                .getString(R.string.about));

        LoginType loginType = SSO.getLoginType();
        switch (loginType) {
            case EMAIL:
                ((TextView) emailContainer.findViewById(R.id.tv_title)).setText(getResources()
                        .getString(R.string.email));
                tvEmail.setText(SSO.getUserName());
                break;
            case PHONE:
                ((TextView) emailContainer.findViewById(R.id.tv_title)).setText(getResources()
                        .getString(R.string.phone));
                tvEmail.setText(SSO.getUserName());
                break;
        }
        tvName.setText(SSO.getUserFirstName() + " " + SSO.getUserLastName());
        tvAbout.setText(SSO.getUserAbout());
    }
}
