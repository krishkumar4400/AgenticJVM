package com.loose.coupling;

public class UserManager {
    private UserDataProvider userDataProvider;

    public UserManager(UserDataProvider databaseProvider) {
        this.userDataProvider = databaseProvider;
    }

    public String getUserInfo() {
        return userDataProvider.getUserDetails();
    }
}
