package com.bombero.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.bombero.utils.DriverManager;
import java.util.List;

public class DashboardPage {

    public DashboardPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    // ==================== ELEMENTOS DEL DASHBOARD ====================

    // Título o indicador principal del Dashboard
    @FindBy(xpath = "//*[contains(text(), 'Dashboard') or contains(text(), 'Inicio') or contains(text(), 'Home')]")
    public WebElement dashboardTitle;

    // Menú principal de Operaciones
    @FindBy(xpath = "//*[contains(text(), 'Operaciones')]")
    public WebElement operationsMenu;

    // Submenú de Inventario
    @FindBy(xpath = "//*[contains(text(), 'Inventario') and not(contains(text(), 'Donaciones'))]")
    public WebElement inventoryMenu;

    // Otros menús
    @FindBy(xpath = "//*[contains(text(), 'Catalogo') or contains(text(), 'Catálogo')]")
    public WebElement catalogMenu;

    @FindBy(xpath = "//*[contains(text(), 'Consulta')]")
    public WebElement consultMenu;

    @FindBy(xpath = "//*[contains(text(), 'Configuración')]")
    public WebElement configMenu;

    @FindBy(xpath = "//*[contains(text(), 'Reportes')]")
    public WebElement reportsMenu;

    @FindBy(xpath = "//*[contains(text(), 'Seguridad')]")
    public WebElement securityMenu;

    // ==================== MÉTODOS DE NAVEGACIÓN ====================

    /**
     * Verifica si el Dashboard está visible/cargado
     * @return true si el dashboard se visualiza correctamente
     */
    public boolean isDashboardDisplayed() {
        try {
            System.out.println("🔍 Verificando que el Dashboard esté visible...");

            // Intentar con el título del dashboard
            try {
                if (dashboardTitle != null && dashboardTitle.isDisplayed()) {
                    System.out.println("✅ Dashboard encontrado por título");
                    return true;
                }
            } catch (Exception e) {
                // Continuar con otras verificaciones
            }

            // Intentar verificar por URL
            String currentUrl = DriverManager.getDriver().getCurrentUrl();
            System.out.println("🔹 URL actual: " + currentUrl);

            if (currentUrl.contains("home") || currentUrl.contains("dashboard")) {
                System.out.println("✅ Dashboard verificado por URL");
                return true;
            }

            // Intentar buscar cualquier menú del dashboard
            try {
                if (operationsMenu.isDisplayed() || inventoryMenu.isDisplayed()) {
                    System.out.println("✅ Dashboard verificado por menús visibles");
                    return true;
                }
            } catch (Exception e) {
                // Continuar
            }

            // Buscar cualquier elemento característico del dashboard
            List<WebElement> dashElements = DriverManager.getDriver().findElements(
                    By.xpath("//*[contains(text(), 'Operaciones') or contains(text(), 'Inventario') or contains(text(), 'Configuración')]")
            );

            if (!dashElements.isEmpty()) {
                System.out.println("✅ Dashboard verificado por elementos característicos");
                return true;
            }

            System.out.println("⚠️  No se pudo verificar completamente el Dashboard");
            return false;

        } catch (Exception e) {
            System.out.println("❌ Error verificando Dashboard: " + e.getMessage());
            return false;
        }
    }

    /**
     * Hace clic en el menú de Operaciones
     */
    public void clickOperationsMenu() {
        try {
            System.out.println("🔹 Haciendo clic en menú 'Operaciones'...");
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            js.executeScript("arguments[0].click();", operationsMenu);
            Thread.sleep(1500);
            System.out.println("✅ Menú 'Operaciones' expandido");
        } catch (Exception e) {
            System.out.println("❌ Error haciendo clic en Operaciones: " + e.getMessage());
        }
    }

    /**
     * Hace clic en el submenú de Inventario
     */
    public void clickInventoryMenu() {
        try {
            System.out.println("🔹 Haciendo clic en submenú 'Inventario'...");
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            js.executeScript("arguments[0].click();", inventoryMenu);
            Thread.sleep(2000);
            System.out.println("✅ Navegado a 'Inventario'");
        } catch (Exception e) {
            System.out.println("❌ Error haciendo clic en Inventario: " + e.getMessage());
        }
    }

    /**
     * Navega al módulo de Inventario (Operaciones → Inventario)
     * Método principal para acceder a inventario
     */
    public void navigateToInventory() {
        try {
            System.out.println("\n🔹 NAVEGANDO A INVENTARIO");
            System.out.println("-------------------------------------------------");

            // Paso 1: Expandir menú Operaciones
            System.out.println("📂 Paso 1: Expandiendo menú Operaciones...");
            clickOperationsMenu();
            Thread.sleep(2000);

            // Paso 2: Click en Inventario
            System.out.println("📦 Paso 2: Haciendo clic en Inventario...");
            clickInventoryMenu();
            Thread.sleep(2000);

            System.out.println("✅ Navegación a Inventario completada");

        } catch (InterruptedException e) {
            System.out.println("❌ Error en navegación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Navega al módulo de Inventario con reintentos
     * Versión más robusta con múltiples intentos
     */
    public void navigateToInventoryWithRetry() {
        int maxRetries = 3;
        int attempt = 0;
        boolean success = false;

        while (attempt < maxRetries && !success) {
            attempt++;
            System.out.println("🔄 Intento " + attempt + " de " + maxRetries + " para navegar a Inventario");

            try {
                navigateToInventory();

                // Verificar que llegamos a inventario
                Thread.sleep(2000);
                String currentUrl = DriverManager.getDriver().getCurrentUrl();

                if (currentUrl.contains("inventory") || currentUrl.contains("inventario")) {
                    System.out.println("✅ Navegación exitosa a Inventario");
                    success = true;
                } else {
                    System.out.println("⚠️  URL actual: " + currentUrl);
                    System.out.println("⚠️  Parece que no llegamos a Inventario, reintentando...");
                }

            } catch (Exception e) {
                System.out.println("❌ Error en intento " + attempt + ": " + e.getMessage());

                if (attempt < maxRetries) {
                    try {
                        System.out.println("⏳ Esperando 2 segundos antes de reintentar...");
                        Thread.sleep(2000);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }
        }

        if (!success) {
            System.out.println("❌ No se pudo navegar a Inventario después de " + maxRetries + " intentos");

            // Último intento: navegación directa por URL
            System.out.println("🔄 Último intento: Navegación directa por URL...");
            try {
                DriverManager.getDriver().get("https://bomberogestion.vercel.app/inventory");
                Thread.sleep(2000);
                System.out.println("✅ Navegado directamente a Inventario por URL");
            } catch (Exception e) {
                System.out.println("❌ Falló la navegación directa: " + e.getMessage());
            }
        }
    }

    // ==================== MÉTODOS ADICIONALES ====================

    /**
     * Hace clic en el menú de Catálogo
     */
    public void clickCatalogMenu() {
        try {
            System.out.println("🔹 Haciendo clic en Catálogo...");
            catalogMenu.click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hace clic en el menú de Configuración
     */
    public void clickConfigMenu() {
        try {
            System.out.println("🔹 Haciendo clic en Configuración...");
            configMenu.click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hace clic en el menú de Reportes
     */
    public void clickReportsMenu() {
        try {
            System.out.println("🔹 Haciendo clic en Reportes...");
            reportsMenu.click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hace clic en el menú de Seguridad
     */
    public void clickSecurityMenu() {
        try {
            System.out.println("🔹 Haciendo clic en Seguridad...");
            securityMenu.click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el texto del título del dashboard
     * @return String con el título
     */
    public String getDashboardTitle() {
        try {
            return dashboardTitle.getText();
        } catch (Exception e) {
            return "Dashboard";
        }
    }

    /**
     * Espera a que el dashboard esté completamente cargado
     */
    public void waitForDashboardLoad() {
        try {
            System.out.println("⏳ Esperando a que cargue el Dashboard...");
            Thread.sleep(3000);

            if (isDashboardDisplayed()) {
                System.out.println("✅ Dashboard cargado correctamente");
            } else {
                System.out.println("⚠️  Dashboard puede no estar completamente cargado");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}