package com.bombero.runner;

import com.bombero.tests.LoginLogoutTest;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import java.util.Arrays;

public class TestRunner {
    public static void main(String[] args) {
        TestNG testng = new TestNG();
        TestListenerAdapter tla = new TestListenerAdapter();

        testng.setTestClasses(new Class[] { LoginLogoutTest.class });
        testng.addListener(tla);

        System.out.println("🚀 Iniciando ejecución de pruebas automatizadas...");
        testng.run();

        System.out.println("✅ Ejecución completada");
    }
}
