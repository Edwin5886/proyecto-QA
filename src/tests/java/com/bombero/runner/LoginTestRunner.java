package com.bombero.runner;

import com.bombero.tests.LoginLogoutTest;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class LoginTestRunner {
    public static void main(String[] args) {
        TestNG testng = new TestNG();
        TestListenerAdapter tla = new TestListenerAdapter();

        testng.setTestClasses(new Class[] { LoginLogoutTest.class });
        testng.addListener(tla);

        System.out.println("🚀 Iniciando pruebas de Login...");
        testng.run();

        System.out.println("✅ Pruebas de login completadas");
    }
}
