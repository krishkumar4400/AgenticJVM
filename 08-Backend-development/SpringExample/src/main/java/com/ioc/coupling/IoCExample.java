package com.ioc.coupling;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IoCExample {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationIoCLooseCouplingExample.xml");


//        UserDataProvider databaseProvider = new UserDatabaseProvider();
//        UserDataProvider databaseProvider = new UserManager(databaseProvider);
        UserManager userManagerWithDB = (UserManager) context.getBean("userManagerWithUserDataProvider");
        System.out.println(userManagerWithDB.getUserInfo());

//        UserDataProvider webServiceProvider = new WebServiceDataProvider();
//        UserManager userManagerWithWebService = new UserManager(webServiceProvider);
        UserManager userManagerWithWebService = (UserManager) context.getBean("userManagerWithNewDatabaseProvider");
        System.out.println(userManagerWithWebService.getUserInfo());

//        UserDataProvider newDatabaseProvider = new NewDatabaseProvider();
//        UserManager userManagerWithNewDatabase = new UserManager(newDatabaseProvider);
        UserManager userManagerWithNewDatabase = (UserManager) context.getBean("userManagerWithWebServiceProvider");
        System.out.println(userManagerWithNewDatabase.getUserInfo());
    }
}
