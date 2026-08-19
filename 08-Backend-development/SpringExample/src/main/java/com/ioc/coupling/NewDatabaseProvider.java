package com.ioc.coupling;

public class NewDatabaseProvider implements UserDataProvider {
    @Override
    public String getUserDetails() {
        return "new Database in action";
    }
}
