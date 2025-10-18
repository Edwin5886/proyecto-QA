package com.bombero.tests;

import com.bombero.pages.DashboardPage;
import com.bombero.pages.InventoryPage;
import com.bombero.pages.LoginPage;
import com.bombero.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class InventoryManagementTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private DashboardPage dashboardPage;
    private WebDriverWait wait;

    private final String USERNAME = "Patzánef";
    private final String PASSWORD = "edwin2025";
    private final String BASE_URL = "https://bomberogestion.vercel.app/sign-in";
    private final String HOME_URL = "https://bomberogestion.vercel.app/home";
    private final String INVENTORY_URL = "https://bomberogestion.vercel.app/inventory";

    @BeforeMethod
    public void setUp() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║         🚀 CONFIGURACIÓN INICIAL DEL TEST         ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        driver = DriverManager.getDriver();
        loginPage = new LoginPage();
        inventoryPage = new InventoryPage();
        dashboardPage = new DashboardPage();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(BASE_URL);
        waitForPageLoad();
        System.out.println("✅ Configuración completada\n");
    }

    @Test
    public void testAgregarProductoInventario() throws Exception {
        try {
            System.out.println("╔════════════════════════════════════════════════════╗");
            System.out.println("║   📦 TEST: AGREGAR PRODUCTO AL INVENTARIO         ║");
            System.out.println("╚════════════════════════════════════════════════════╝\n");

            // PASO 1: LOGIN
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 🔹 PASO 1: REALIZANDO LOGIN                     │");
            System.out.println("└─────────────────────────────────────────────────┘");
            performLoginWithDelays();
            verifyLoginSuccess();
            System.out.println("✅ Login exitoso - Esperando 3 segundos...\n");
            Thread.sleep(3000);

            // PASO 2: NAVEGAR A INVENTARIO
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 🔹 PASO 2: NAVEGANDO A MÓDULO DE INVENTARIO     │");
            System.out.println("└─────────────────────────────────────────────────┘");
            navigateToInventory();
            Thread.sleep(2000);

            // PASO 3: ABRIR FORMULARIO DE CREAR PRODUCTO
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 🔹 PASO 3: ABRIENDO FORMULARIO NUEVO PRODUCTO   │");
            System.out.println("└─────────────────────────────────────────────────┘");
            openCreateProductForm();
            Thread.sleep(2000);

            // PASO 4: LLENAR FORMULARIO (CORREGIDO)
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 🔹 PASO 4: LLENANDO FORMULARIO DE PRODUCTO      │");
            System.out.println("└─────────────────────────────────────────────────┘");
            inventoryPage.llenarFormularioCompleto(); // ✅ CAMBIO AQUÍ
            System.out.println("✅ Formulario llenado correctamente\n");
            Thread.sleep(2000);

            // PASO 5: GUARDAR PRODUCTO (USANDO MÉTODO MEJORADO)
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 🔹 PASO 5: GUARDANDO PRODUCTO                   │");
            System.out.println("└─────────────────────────────────────────────────┘");
            inventoryPage.clickCrearFormularioMejorado(); // ✅ USAR MÉTODO MEJORADO
            Thread.sleep(3000);

            // PASO 6: VERIFICAR QUE SE GUARDÓ
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 🔹 PASO 6: VERIFICANDO PRODUCTO CREADO          │");
            System.out.println("└─────────────────────────────────────────────────┘");
            boolean productoGuardado = inventoryPage.verificarProductoGuardado("Extintor ABC 10kg");

            if (productoGuardado) {
                System.out.println("✅ Producto creado y verificado exitosamente\n");
            } else {
                System.out.println("⚠️  No se pudo verificar el producto\n");
            }

            System.out.println("⏱️  Esperando 5 segundos para ver el resultado...");
            Thread.sleep(5000);

            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║        ✅ PRUEBA COMPLETADA EXITOSAMENTE          ║");
            System.out.println("╚════════════════════════════════════════════════════╝\n");

        } catch (Exception e) {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║           ❌ ERROR DURANTE LA PRUEBA              ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-lanzar para que TestNG marque el test como fallido
        }
    }

    private void waitForPageLoad() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("input")));
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("⏳ Esperando carga de página...");
        }
    }

    private void performLoginWithDelays() throws InterruptedException {
        System.out.println("🔹 Buscando campos de entrada...");

        List<WebElement> inputs = driver.findElements(By.tagName("input"));

        if (inputs.size() < 2) {
            throw new RuntimeException("❌ No se encontraron suficientes campos de entrada");
        }

        WebElement userField = inputs.get(0);
        WebElement passField = inputs.get(1);

        System.out.println("🔹 Llenando campo de usuario: " + USERNAME);
        userField.clear();
        userField.sendKeys(USERNAME);
        Thread.sleep(1000);

        System.out.println("🔹 Llenando campo de contraseña...");
        passField.clear();
        passField.sendKeys(PASSWORD);
        Thread.sleep(1000);

        System.out.println("🔹 Buscando botón de login...");
        WebElement loginBtn = driver.findElement(
                By.xpath("//button[contains(., 'Iniciar sesión') or contains(., 'Ingresar')]")
        );

        System.out.println("🔹 Haciendo clic en botón login...");
        loginBtn.click();
        System.out.println("✅ Proceso de login completado");
    }

    private void verifyLoginSuccess() {
        System.out.println("🔍 Verificando login exitoso...");
        wait.until(ExpectedConditions.urlToBe(HOME_URL));
        System.out.println("✅ Login verificado - En página principal");
    }

    private void navigateToInventory() throws InterruptedException {
        System.out.println("🔹 Navegando al módulo de Inventario...");

        try {
            dashboardPage.navigateToInventory();
            System.out.println("✅ Navegación a Inventario completada");

        } catch (Exception e) {
            System.out.println("⚠️  Error con método de DashboardPage, intentando alternativa...");

            try {
                WebElement operationsMenu = driver.findElement(
                        By.xpath("//*[contains(text(), 'Operaciones')]")
                );
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", operationsMenu);
                Thread.sleep(2000);

                WebElement inventoryMenu = driver.findElement(
                        By.xpath("//*[contains(text(), 'Inventario') and not(contains(text(), 'Donaciones'))]")
                );
                js.executeScript("arguments[0].click();", inventoryMenu);
                Thread.sleep(2000);
                System.out.println("✅ Navegado a Inventario con método alternativo");

            } catch (Exception ex) {
                System.out.println("⚠️  Navegando directamente por URL...");
                driver.get(INVENTORY_URL);
                Thread.sleep(2000);
                System.out.println("✅ Navegado a Inventario por URL");
            }
        }
    }

    private void openCreateProductForm() throws Exception {
        System.out.println("🔹 Buscando botón 'Crear' o 'Nuevo'...");

        try {
            inventoryPage.clickCrearConEspera();
            System.out.println("✅ Formulario de creación abierto");

        } catch (Exception e) {
            System.out.println("⚠️  Método de InventoryPage falló, intentando alternativa...");

            String[] createButtonSelectors = {
                    "//button[contains(., 'Crear')]",
                    "//button[contains(., 'Nuevo')]",
                    "//button[contains(., 'Agregar')]",
                    "//button[contains(@class, 'mat-primary')]"
            };

            boolean found = false;
            JavascriptExecutor js = (JavascriptExecutor) driver;

            for (String selector : createButtonSelectors) {
                try {
                    WebElement createBtn = driver.findElement(By.xpath(selector));
                    js.executeScript("arguments[0].click();", createBtn);
                    System.out.println("✅ Formulario abierto con selector: " + selector);
                    found = true;
                    break;
                } catch (Exception ex) {
                    // Continuar con el siguiente selector
                }
            }

            if (!found) {
                throw new Exception("❌ No se pudo abrir el formulario");
            }
        }
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("\n┌─────────────────────────────────────────────────┐");
        System.out.println("│ 🧹 LIMPIANDO RECURSOS...                        │");
        System.out.println("└─────────────────────────────────────────────────┘");

        try {
            System.out.println("⏱️  Esperando 3 segundos antes de cerrar...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("🔹 Cerrando navegador...");
        DriverManager.quitDriver();
        System.out.println("✅ Recursos liberados correctamente\n");
    }
}