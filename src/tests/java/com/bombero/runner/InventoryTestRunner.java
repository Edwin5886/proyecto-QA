package com.bombero.runner;

import com.bombero.tests.InventoryManagementTest;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class InventoryTestRunner {
    public static void main(String[] args) {
        TestNG testng = new TestNG();
        TestListenerAdapter tla = new TestListenerAdapter();

        testng.setTestClasses(new Class[] { InventoryManagementTest.class });
        testng.addListener(tla);

        System.out.println("🚀 Iniciando pruebas de Gestión de Inventario...");
        testng.run();

        System.out.println("✅ Pruebas de inventario completadas");
    }
}
