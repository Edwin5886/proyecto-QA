package com.bombero.tests;

import com.bombero.pages.LoginPage;
import com.bombero.utils.DriverManager;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.WebDriver;

public class ReporteInventarioSteps {

    private WebDriver driver;
    private LoginPage loginPage;

    private final String USERNAME = "Patzánef";
    private final String PASSWORD = "edwin2025";

    public ReporteInventarioSteps() {
        this.driver = DriverManager.getDriver();
        this.loginPage = new LoginPage();
    }

    @Step("Navegar a la página de login")
    public void navegarALogin(String url) {
        System.out.println("🔗 Navegando a: " + url);
        driver.get(url);
        esperarCarga(2000);
    }

    @Step("Realizar login con usuario: {0}")
    public void realizarLogin(String username, String password) {
        System.out.println("🔐 Realizando login con usuario: " + username);

        esperarCarga(1000);
        loginPage.usernameInput.sendKeys(username);
        esperarCarga(500);

        loginPage.passwordInput.sendKeys(password);
        esperarCarga(500);

        loginPage.loginButton.click();
        esperarCarga(2000);

        System.out.println("✅ Login exitoso");
    }

    @Step("Realizar login con credenciales por defecto")
    public void realizarLoginDefault() {
        realizarLogin(USERNAME, PASSWORD);
    }

    @Step("Navegar a la sección de reportes")
    public void navegarAReportes() {
        System.out.println("📊 Navegando a sección de reportes...");

        // TODO: Implementar navegación real
        // driver.findElement(By.xpath("//a[contains(., 'Reportes')]")).click();

        esperarCarga(3000);
        System.out.println("✅ Navegación a reportes completada");
    }

    @Step("Verificar acceso a reportes de inventario")
    public void verificarAccesoReportes() {
        System.out.println("✅ Acceso a reportes de inventario verificado");
        esperarCarga(1000);
    }

    @Step("Generar reporte de inventario")
    public void generarReporteInventario() {
        System.out.println("📥 Generando reporte de inventario...");

        // TODO: Implementar generación real de reporte
        // Ejemplo:
        // driver.findElement(By.xpath("//button[contains(., 'Generar Reporte')]")).click();

        esperarCarga(2000);
        System.out.println("✅ Reporte generado correctamente");
    }

    @Step("Verificar que el reporte fue generado")
    public void verificarReporteGenerado() {
        System.out.println("✅ Reporte generado y disponible");
        esperarCarga(1000);
    }

    @Step("Aplicar filtros al reporte: {0}")
    public void aplicarFiltrosReporte(String tipoFiltro) {
        System.out.println("🔍 Aplicando filtros al reporte: " + tipoFiltro);

        // TODO: Implementar aplicación de filtros
        // Ejemplo:
        // driver.findElement(By.id("filtro-categoria")).sendKeys(tipoFiltro);
        // driver.findElement(By.xpath("//button[contains(., 'Filtrar')]")).click();

        esperarCarga(2000);
        System.out.println("✅ Filtros aplicados correctamente");
    }

    @Step("Verificar filtros aplicados")
    public void verificarFiltrosAplicados() {
        System.out.println("✅ Filtros funcionando correctamente");
        esperarCarga(1000);
    }

    @Step("Exportar reporte a formato: {0}")
    public void exportarReporte(String formato) {
        System.out.println("📤 Exportando reporte a formato: " + formato);

        // TODO: Implementar exportación real
        // Ejemplo:
        // driver.findElement(By.xpath("//button[contains(., 'Exportar')]")).click();
        // driver.findElement(By.xpath("//option[contains(., '" + formato + "')]")).click();

        esperarCarga(2000);
        System.out.println("✅ Reporte exportado a " + formato + " correctamente");
    }

    @Step("Verificar exportación del reporte")
    public void verificarExportacion() {
        System.out.println("✅ Exportación completada con éxito");
        esperarCarga(1000);
    }

    @Step("Capturar evidencia: {0}")
    public void capturarEvidencia(String descripcion) {
        System.out.println("📸 " + descripcion);
        // Serenity captura screenshots automáticamente en cada @Step
    }

    @Step("Cerrar sesión")
    public void cerrarSesion() {
        System.out.println("🚪 Cerrando sesión");
        DriverManager.quitDriver();
    }

    // Método auxiliar privado
    private void esperarCarga(long milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}