package com.bombero.runner;

import com.bombero.tests.LoginLogoutTest;
import com.bombero.tests.InventoryManagementTest;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class TestRunner {
    public static void main(String[] args) {
        TestNG testng = new TestNG();
        TestListenerAdapter tla = new TestListenerAdapter();

        testng.setTestClasses(new Class[] {
                LoginLogoutTest.class,
                InventoryManagementTest.class
        });
        testng.addListener(tla);

        System.out.println("🚀 Iniciando TODAS las pruebas automatizadas...");
        testng.run();

        System.out.println("✅ Todas las pruebas completadas");
    }
}

