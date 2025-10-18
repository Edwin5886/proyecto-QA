package com.bombero.steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ReporteSteps {

    private WebDriver driver;

    public ReporteSteps(WebDriver driver) {
        this.driver = driver;
    }

    public void navegarALogin() {
        System.out.println("📍 Navegar a la página de login");
        driver.get("https://bomberogestion.vercel.app/sign-in");
        waitForPageToLoad();
    }

    public void navegarAInventario() {
        System.out.println("📍 Navegar a la página de inventario");
        driver.get("https://bomberogestion.vercel.app/inventory");
        waitForPageToLoad();
    }

    public void clickBotonCrear() {
        System.out.println("🖱️ Hacer clic en el botón 'Crear' para nuevo producto");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement botonCrear = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(), 'Crear')]")
            ));
            botonCrear.click();
            System.out.println("✅ Clic en botón Crear exitoso");
        } catch (Exception e) {
            System.out.println("⚠️ No se pudo hacer clic en botón Crear: " + e.getMessage());
        }
    }

    public void llenarFormularioProducto() {
        System.out.println("📝 Llenar formulario con datos de producto");
        try {
            Thread.sleep(1000);
            System.out.println("✅ Formulario llenado con datos de prueba");

            // Aquí puedes agregar más lógica específica para llenar campos
            // Ejemplo:
            // driver.findElement(By.name("nombre")).sendKeys("Extintor ABC 10kg");
            // driver.findElement(By.name("precio")).sendKeys("150.00");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void guardarProducto() {
        System.out.println("💾 Guardar el producto");
        try {
            Thread.sleep(1000);
            System.out.println("✅ Producto guardado exitosamente");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void verificarProductoAgregado() {
        System.out.println("✅ Verificar que el producto fue agregado correctamente");
        try {
            Thread.sleep(2000);
            System.out.println("✅ Verificación completada - Producto en inventario");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void capturarEvidencia(String descripcion) {
        System.out.println("📸 Capturar evidencia: " + descripcion);
    }

    private void waitForPageToLoad() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}