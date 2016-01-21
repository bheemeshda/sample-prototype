package com.example.bheemesh.myapplication.singleton;

import com.example.bheemesh.myapplication.beans.UserDetails;
import com.example.bheemesh.myapplication.entities.LoginType;

/**
 * Created by bheemesh on 10/1/16.
 *
 * @author bheemesh
 */
public class SSO {
    private static volatile SSO instance;
    private final UserDetails userDetails = new UserDetails();

    private SSO() {
        initUserDetails();
    }

    public static SSO getInstance() {
        if (instance == null) {
            synchronized (SSO.class) {
                if (instance == null) {
                    instance = new SSO();
                }
            }
        }
        return instance;
    }

    private void initUserDetails() {
        userDetails.setUserID("1234");
        userDetails.setUserFirstName("bheemesh");
        userDetails.setUserLastName("da");
        userDetails.setUserName("bheemesh@abcd.com");
        userDetails.setUserPassword("password");
        userDetails.setLoginType(LoginType.EMAIL);
        userDetails.setUserAbout("Please write something about you. like : Pick one specific topic, describe it in detail, and use that to introduce yourself. It\\'s better to pick one thing and use it to describe yourself in lots of detail, than to give someone a big long list of general topics");

    }


    // UserDetails Callback
    public static String getUserID() {
        return getInstance().userDetails.getUserID();
    }

    public static String getUserName() {
        return getInstance().userDetails.getUserName();
    }

    public static String getUserFirstName() {
        return getInstance().userDetails.getUserFirstName();
    }

    public static String getUserLastName() {
        return getInstance().userDetails.getUserLastName();
    }

    public static String getUserPassword() {
        return getInstance().userDetails.getUserPassword();
    }

    public static String getUserAbout() {
        return getInstance().userDetails.getUserAbout();
    }

    public static LoginType getLoginType() {
        return getInstance().userDetails.getLoginType();
    }

    // UserDetails setting

    public static void setUserID(String userID) {
        getInstance().userDetails.setUserID(userID);
    }

    public static void setUserName(String userName) {
        getInstance().userDetails.setUserName(userName);
    }

    public static void setUserFirstName(String firstName) {
        getInstance().userDetails.setUserFirstName(firstName);
    }

    public static void setUserLastName(String lastName) {
        getInstance().userDetails.setUserLastName(lastName);
    }

    public static void setUserPassword(String password) {
        getInstance().userDetails.setUserPassword(password);
    }

    public static void setUserAbout(String userAbout) {
        getInstance().userDetails.setUserAbout(userAbout);
    }

    public static void setLoginType(LoginType loginType) {
        getInstance().userDetails.setLoginType(loginType);
    }
}
