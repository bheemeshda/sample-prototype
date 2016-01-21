package com.example.bheemesh.myapplication.beans;

import com.example.bheemesh.myapplication.entities.LoginType;

/**
 * Created by bheemesh on 10/1/16.
 *
 * @author bheemesh
 */
public class UserDetails {
    private String userID = null;
    private String userFirstName = null;
    private String userLastName = null;
    private String userName = null;
    private String userPassword = null;
    private String userAbout = null;
    private LoginType loginType = LoginType.NONE;

    // Setter methods
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public void setUserAbout(String userAbout) {
        this.userAbout = userAbout;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    // Getter methods
    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public String getUserAbout() {
        return userAbout;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }
}
