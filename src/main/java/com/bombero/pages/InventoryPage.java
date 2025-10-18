package com.bombero.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.bombero.utils.DriverManager;
import java.time.Duration;
import java.util.List;

public class InventoryPage {

    private WebDriverWait wait;

    public InventoryPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
        this.wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15));
    }

    @FindBy(xpath = "//button[contains(., 'Crear') or contains(., 'Nuevo')]")
    public WebElement createButton;

    @FindBy(xpath = "//button[contains(., 'Crear') and (contains(@class, 'mat-primary') or @type='submit')]")
    public WebElement crearButton;

    @FindBy(xpath = "//input[@placeholder='Nombre' or contains(@placeholder, 'nombre')]")
    public WebElement nameInput;

    @FindBy(xpath = "//input[@placeholder='Cantidad' or contains(@placeholder, 'cantidad')]")
    public WebElement quantityInput;

    @FindBy(xpath = "//input[@placeholder='Precio' or contains(@placeholder, 'precio')]")
    public WebElement priceInput;

    @FindBy(xpath = "//*[contains(text(), 'Inventario')]")
    public WebElement inventoryTitle;

    // ✅ CORREGIDO: Llenar formulario según HTML real
    public void llenarFormularioCompleto() {
        try {
            System.out.println("🔹 Esperando carga del formulario...");
            Thread.sleep(3000);

            // Esperar a que el diálogo esté completamente cargado
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//mat-dialog-container")
            ));

            System.out.println("🔹 Llenando campos del formulario...");

            // CAMPOS EN ORDEN SEGÚN LA IMAGEN:
            // 1. Método de ingreso (dropdown)
            seleccionarDropdownPorLabel("Metodo de ingreso", "Transferencia");

            // 2. Categoría (dropdown)
            seleccionarDropdownPorLabel("Categoria", "Equipos de Emergencia y Rescate");

            // 3. Nombre (input text)
            llenarCampoPorLabel("Nombre", "botiquin");

            // 4. Stock actual (input)
            llenarCampoPorLabel("Stock actual", "10");

            // 5. Stock mínimo (input)
            llenarCampoPorLabel("Stock minimo", "5");

            // 6. Unidad de medida (dropdown)
            seleccionarDropdownPorLabel("Unidad de medida", "Unidad");

            // 7. Detalle (textarea o input)
            llenarCampoPorLabel("Detalle", "Manguera Industrial");

            // 8. Checkboxes (HTML normales, NO mat-checkbox)
            marcarCheckboxesHTML();

            System.out.println("✅ Formulario completado");
            Thread.sleep(1000);

        } catch (Exception e) {
            System.out.println("❌ Error llenando formulario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ NUEVO: Llenar campo buscando por el label
    private void llenarCampoPorLabel(String labelText, String valor) {
        try {
            System.out.println("   🔹 Llenando: " + labelText + " = " + valor);

            // Buscar el label que contiene el texto
            WebElement label = DriverManager.getDriver().findElement(
                    By.xpath("//label[contains(text(), '" + labelText + "')] | " +
                            "//*[contains(text(), '" + labelText + "')]")
            );

            // Buscar el input asociado (puede estar como hermano o en el mismo contenedor)
            WebElement input = null;

            try {
                // Estrategia 1: Input siguiente al label
                input = label.findElement(By.xpath("./following::input[1]"));
            } catch (Exception e) {
                try {
                    // Estrategia 2: Input en el mismo contenedor padre
                    WebElement parent = label.findElement(By.xpath("./.."));
                    input = parent.findElement(By.xpath(".//input"));
                } catch (Exception ex) {
                    // Estrategia 3: Buscar por el contenedor del label
                    WebElement container = label.findElement(By.xpath("./ancestor::div[contains(@class, 'flex')]"));
                    input = container.findElement(By.xpath(".//input"));
                }
            }

            if (input != null) {
                JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", input);
                Thread.sleep(300);

                input.clear();
                Thread.sleep(200);
                input.sendKeys(valor);
                Thread.sleep(300);

                System.out.println("      ✅ " + labelText + ": " + valor);
            } else {
                System.out.println("      ❌ No se encontró input para: " + labelText);
            }

        } catch (Exception e) {
            System.out.println("      ❌ Error con " + labelText + ": " + e.getMessage());
        }
    }

    // ✅ NUEVO: Seleccionar dropdown buscando por label
    private void seleccionarDropdownPorLabel(String labelText, String opcion) {
        try {
            System.out.println("   🔹 Dropdown: " + labelText + " -> " + opcion);

            // Buscar el label
            WebElement label = DriverManager.getDriver().findElement(
                    By.xpath("//label[contains(text(), '" + labelText + "')] | " +
                            "//*[contains(text(), '" + labelText + "')]")
            );

            // Buscar el mat-select asociado
            WebElement dropdown = null;

            try {
                // Estrategia 1: mat-select siguiente al label
                dropdown = label.findElement(By.xpath("./following::mat-select[1]"));
            } catch (Exception e) {
                try {
                    // Estrategia 2: En el mismo contenedor
                    WebElement parent = label.findElement(By.xpath("./.."));
                    dropdown = parent.findElement(By.xpath(".//mat-select"));
                } catch (Exception ex) {
                    // Estrategia 3: En el contenedor padre
                    WebElement container = label.findElement(By.xpath("./ancestor::div[1]"));
                    dropdown = container.findElement(By.xpath(".//mat-select"));
                }
            }

            if (dropdown != null) {
                JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", dropdown);
                Thread.sleep(500);

                // Hacer clic en el dropdown
                js.executeScript("arguments[0].click();", dropdown);
                Thread.sleep(2000);

                // Buscar y seleccionar la opción
                WebElement opcionElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//mat-option//span[contains(text(), '" + opcion + "')]")
                ));

                js.executeScript("arguments[0].click();", opcionElement);
                System.out.println("      ✅ Seleccionado: " + opcion);
                Thread.sleep(1000);
            } else {
                System.out.println("      ❌ No se encontró dropdown para: " + labelText);
            }

        } catch (Exception e) {
            System.out.println("      ❌ Error con dropdown " + labelText + ": " + e.getMessage());

            // Mostrar opciones disponibles para debugging
            try {
                List<WebElement> opciones = DriverManager.getDriver().findElements(
                        By.xpath("//mat-option//span")
                );
                if (!opciones.isEmpty()) {
                    System.out.println("      📋 Opciones disponibles:");
                    for (WebElement op : opciones) {
                        System.out.println("         - '" + op.getText() + "'");
                    }
                }
            } catch (Exception ex) {
                // Ignorar
            }
        }
    }

    // ✅ MEJORADO: Marcar SOLO UN checkbox con verificación robusta
    private void marcarCheckboxesHTML() {
        try {
            System.out.println("   🔹 Marcando checkboxes (solo se necesita UNO)...");

            String[] checkboxLabels = {
                    "¿Es consumible?",
                    "¿Es asignable?",
                    "¿Prestable interno?",
                    "¿Prestable externo?"
            };

            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            boolean checkboxMarcado = false;

            // Intentar marcar el PRIMER checkbox que funcione
            for (String labelText : checkboxLabels) {
                if (checkboxMarcado) {
                    System.out.println("      ℹ️  Saltando: " + labelText + " (ya se marcó uno)");
                    continue;
                }

                try {
                    System.out.println("      🔹 Intentando: " + labelText);

                    // ESTRATEGIA MÚLTIPLE para encontrar el checkbox
                    WebElement checkbox = null;

                    // Estrategia 1: Buscar todos los checkboxes y encontrar el correcto por posición
                    try {
                        List<WebElement> todosLosCheckboxes = DriverManager.getDriver().findElements(
                                By.xpath("//input[@type='checkbox']")
                        );

                        System.out.println("         📋 Total checkboxes encontrados: " + todosLosCheckboxes.size());

                        // Mapeo de labels a índices (basado en el orden visual del formulario)
                        int indice = -1;
                        switch (labelText) {
                            case "¿Es consumible?": indice = 0; break;
                            case "¿Es asignable?": indice = 1; break;
                            case "¿Prestable interno?": indice = 2; break;
                            case "¿Prestable externo?": indice = 3; break;
                        }

                        if (indice >= 0 && indice < todosLosCheckboxes.size()) {
                            checkbox = todosLosCheckboxes.get(indice);
                            System.out.println("         ✅ Checkbox encontrado por índice: " + indice);
                        }
                    } catch (Exception e) {
                        System.out.println("         ⚠️  Estrategia por índice falló: " + e.getMessage());
                    }

                    // Estrategia 2: Buscar por label
                    if (checkbox == null) {
                        try {
                            WebElement label = DriverManager.getDriver().findElement(
                                    By.xpath("//label[contains(text(), '" + labelText + "')]")
                            );

                            // Intentar encontrar el checkbox cerca del label
                            try {
                                checkbox = label.findElement(By.xpath("./preceding-sibling::input[@type='checkbox'][1]"));
                            } catch (Exception e1) {
                                try {
                                    checkbox = label.findElement(By.xpath("./following-sibling::input[@type='checkbox'][1]"));
                                } catch (Exception e2) {
                                    WebElement parent = label.findElement(By.xpath("./parent::*"));
                                    checkbox = parent.findElement(By.xpath(".//input[@type='checkbox']"));
                                }
                            }

                            System.out.println("         ✅ Checkbox encontrado por label");
                        } catch (Exception e) {
                            System.out.println("         ⚠️  Estrategia por label falló: " + e.getMessage());
                        }
                    }

                    if (checkbox != null) {
                        // Hacer scroll suave
                        js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", checkbox);
                        Thread.sleep(500);

                        // Verificar estado ANTES de marcar
                        boolean yaEstaMarcado = checkbox.isSelected() ||
                                "true".equals(checkbox.getAttribute("checked")) ||
                                checkbox.getAttribute("class").contains("checked");

                        System.out.println("         🔍 Estado actual: " + (yaEstaMarcado ? "MARCADO" : "NO MARCADO"));

                        if (!yaEstaMarcado) {
                            // ESTRATEGIA 1: Click con JavaScript directo
                            System.out.println("         🔹 Estrategia 1: JavaScript click...");
                            js.executeScript("arguments[0].click();", checkbox);
                            Thread.sleep(800);

                            // Verificar si funcionó
                            boolean marcado1 = checkbox.isSelected() ||
                                    "true".equals(checkbox.getAttribute("checked"));

                            if (!marcado1) {
                                // ESTRATEGIA 2: Cambiar la propiedad checked directamente
                                System.out.println("         🔹 Estrategia 2: Forzar checked=true...");
                                js.executeScript("arguments[0].checked = true;", checkbox);
                                js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", checkbox);
                                Thread.sleep(800);
                            }

                            // Verificar si funcionó
                            boolean marcado2 = checkbox.isSelected() ||
                                    "true".equals(checkbox.getAttribute("checked"));

                            if (!marcado2) {
                                // ESTRATEGIA 3: Click normal de Selenium
                                System.out.println("         🔹 Estrategia 3: Click normal...");
                                checkbox.click();
                                Thread.sleep(800);
                            }

                            // VERIFICACIÓN FINAL
                            boolean marcadoFinal = checkbox.isSelected() ||
                                    "true".equals(checkbox.getAttribute("checked")) ||
                                    checkbox.getAttribute("class").contains("checked");

                            if (marcadoFinal) {
                                System.out.println("         ✅ ÉXITO: " + labelText + " marcado correctamente");
                                checkboxMarcado = true;
                                break; // Salir del loop - solo necesitamos uno
                            } else {
                                System.out.println("         ❌ FALLO: No se pudo marcar " + labelText);
                            }
                        } else {
                            System.out.println("         ℹ️  Ya estaba marcado: " + labelText);
                            checkboxMarcado = true;
                            break;
                        }
                    } else {
                        System.out.println("         ❌ No se encontró checkbox para: " + labelText);
                    }

                } catch (Exception e) {
                    System.out.println("         ❌ Error con " + labelText + ": " + e.getMessage());
                }
            }

            if (!checkboxMarcado) {
                System.out.println("   ⚠️  ADVERTENCIA: No se pudo marcar ningún checkbox");
                System.out.println("   ⚠️  El formulario puede no guardarse correctamente");
            } else {
                System.out.println("   ✅ Al menos un checkbox está marcado");
            }

        } catch (Exception e) {
            System.out.println("   ❌ Error crítico marcando checkboxes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ MÉTODO MEJORADO Y SIMPLIFICADO para hacer clic en Crear
    public void clickCrearFormularioMejorado() {
        try {
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 🔹 BUSCANDO BOTÓN CREAR (MÉTODO MEJORADO)       │");
            System.out.println("└─────────────────────────────────────────────────┘");

            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

            // Esperar a que el formulario esté listo
            Thread.sleep(2000);

            // ESTRATEGIA 1: Buscar el botón por su texto exacto y posición en el diálogo
            WebElement crearBtn = null;

            // Buscar en el área del diálogo actual
            WebElement dialogContainer = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//mat-dialog-container")
            ));

            // Buscar botones dentro del diálogo
            List<WebElement> botonesEnDialogo = dialogContainer.findElements(By.tagName("button"));
            System.out.println("🔍 Botones encontrados en diálogo: " + botonesEnDialogo.size());

            for (WebElement btn : botonesEnDialogo) {
                String textoBtn = btn.getText().trim();
                System.out.println("   📋 Botón: '" + textoBtn + "' - Visible: " + btn.isDisplayed() + " - Habilitado: " + btn.isEnabled());

                if (textoBtn.equalsIgnoreCase("Crear") || textoBtn.equalsIgnoreCase("Guardar")) {
                    crearBtn = btn;
                    System.out.println("✅ Botón objetivo encontrado: '" + textoBtn + "'");
                    break;
                }
            }

            // ESTRATEGIA 2: Si no se encuentra, buscar por atributos específicos
            if (crearBtn == null) {
                System.out.println("🔍 Estrategia 2: Buscando por atributos específicos...");
                try {
                    crearBtn = dialogContainer.findElement(
                            By.xpath(".//button[contains(@class, 'mat-primary') and contains(., 'Crear')]")
                    );
                    System.out.println("✅ Botón encontrado por clase mat-primary");
                } catch (Exception e) {
                    System.out.println("⚠️  No se encontró por mat-primary");
                }
            }

            // ESTRATEGIA 3: Buscar el último botón en las acciones del diálogo (normalmente es Crear)
            if (crearBtn == null) {
                System.out.println("🔍 Estrategia 3: Buscando en mat-dialog-actions...");
                try {
                    WebElement dialogActions = dialogContainer.findElement(By.xpath(".//div[contains(@class, 'mat-dialog-actions')]"));
                    List<WebElement> actionButtons = dialogActions.findElements(By.tagName("button"));
                    if (!actionButtons.isEmpty()) {
                        // El último botón suele ser "Crear"
                        crearBtn = actionButtons.get(actionButtons.size() - 1);
                        System.out.println("✅ Usando último botón de acciones: '" + crearBtn.getText() + "'");
                    }
                } catch (Exception e) {
                    System.out.println("⚠️  No se encontró en mat-dialog-actions");
                }
            }

            if (crearBtn == null) {
                System.out.println("❌ NO SE PUDO ENCONTRAR EL BOTÓN CREAR");
                System.out.println("🔍 Intentando búsqueda global...");

                // Último intento: buscar en toda la página
                List<WebElement> todosLosBotones = DriverManager.getDriver().findElements(
                        By.xpath("//button[contains(., 'Crear') or contains(., 'Guardar')]")
                );

                for (WebElement btn : todosLosBotones) {
                    if (btn.isDisplayed()) {
                        crearBtn = btn;
                        System.out.println("✅ Botón encontrado globalmente: '" + btn.getText() + "'");
                        break;
                    }
                }
            }

            if (crearBtn != null) {
                System.out.println("🎯 EJECUTANDO CLIC EN BOTÓN...");
                System.out.println("   Texto: '" + crearBtn.getText() + "'");
                System.out.println("   Clases: " + crearBtn.getAttribute("class"));
                System.out.println("   Habilitado: " + crearBtn.isEnabled());

                if (!crearBtn.isEnabled()) {
                    System.out.println("⚠️  Botón deshabilitado - Verificando estado del formulario...");
                    validarEstadoFormularioAntesDeSave();
                    return;
                }

                // Hacer scroll y clic con JavaScript
                js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", crearBtn);
                Thread.sleep(1000);

                // Disparar eventos completos
                js.executeScript(
                        "var btn = arguments[0];" +
                                "btn.dispatchEvent(new MouseEvent('mousedown', { bubbles: true }));" +
                                "setTimeout(function() {" +
                                "  btn.dispatchEvent(new MouseEvent('mouseup', { bubbles: true }));" +
                                "  btn.dispatchEvent(new MouseEvent('click', { bubbles: true }));" +
                                "}, 100);" +
                                "btn.click();",
                        crearBtn
                );

                System.out.println("✅ CLIC EJECUTADO - Esperando resultado...");
                Thread.sleep(3000);

                // Verificar si el diálogo se cerró
                List<WebElement> dialogos = DriverManager.getDriver().findElements(By.xpath("//mat-dialog-container"));
                if (dialogos.isEmpty()) {
                    System.out.println("✅ DIÁLOGO CERRADO - Producto creado exitosamente");
                } else {
                    System.out.println("⚠️  El diálogo sigue abierto - Puede haber errores de validación");
                    mostrarMensajesErrorActuales();
                }

            } else {
                System.out.println("❌ NO SE ENCONTRÓ NINGÚN BOTÓN VÁLIDO");
            }

        } catch (Exception e) {
            System.out.println("❌ ERROR en clickCrearFormularioMejorado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ MÉTODO ALTERNATIVO MÁS SIMPLE
    public void clickCrearDirecto() {
        try {
            System.out.println("🎯 BUSCANDO BOTÓN CREAR (MÉTODO DIRECTO)");

            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            Thread.sleep(2000);

            // Buscar el botón Crear específicamente en el diálogo actual
            WebElement crearBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//mat-dialog-container//button[.//span[contains(text(), 'Crear')]]")
            ));

            System.out.println("✅ Botón encontrado: '" + crearBtn.getText() + "'");

            // Clic directo con JavaScript
            js.executeScript("arguments[0].click();", crearBtn);
            System.out.println("✅ Clic ejecutado");
            Thread.sleep(3000);

        } catch (Exception e) {
            System.out.println("❌ Error en clickCrearDirecto: " + e.getMessage());
        }
    }

    // 🐛 MÉTODO DE DEBUG para ver todos los botones
    public void debugBotonesDisponibles() {
        try {
            System.out.println("🐛 DEBUG: ANALIZANDO BOTONES DISPONIBLES");

            WebElement dialog = DriverManager.getDriver().findElement(By.xpath("//mat-dialog-container"));
            List<WebElement> botones = dialog.findElements(By.tagName("button"));

            System.out.println("🔍 Total de botones en diálogo: " + botones.size());

            for (int i = 0; i < botones.size(); i++) {
                WebElement btn = botones.get(i);
                System.out.println("--- Botón " + i + " ---");
                System.out.println("   Texto: '" + btn.getText() + "'");
                System.out.println("   Clases: " + btn.getAttribute("class"));
                System.out.println("   Tipo: " + btn.getAttribute("type"));
                System.out.println("   Color: " + btn.getAttribute("color"));
                System.out.println("   Visible: " + btn.isDisplayed());
                System.out.println("   Habilitado: " + btn.isEnabled());
                System.out.println("-------------------");
            }

        } catch (Exception e) {
            System.out.println("❌ Error en debug: " + e.getMessage());
        }
    }

    // ✅ MANTENER método original pero mejorarlo llamando al nuevo
    public void clickCrearFormulario() {
        System.out.println("🔹 Usando método mejorado para botón Crear...");
        clickCrearFormularioMejorado();
    }

    // ✅ NUEVO: Validar estado del formulario antes de guardar
    private void validarEstadoFormularioAntesDeSave() {
        try {
            System.out.println("🔍 Validando formulario antes de guardar...");

            // 1. Verificar campos requeridos
            List<WebElement> camposRequeridos = DriverManager.getDriver().findElements(
                    By.xpath("//input[@required]")
            );

            int camposVacios = 0;
            for (WebElement campo : camposRequeridos) {
                String valor = campo.getAttribute("value");
                if (valor == null || valor.trim().isEmpty()) {
                    camposVacios++;
                }
            }

            if (camposVacios > 0) {
                System.out.println("   ⚠️  Hay " + camposVacios + " campos requeridos vacíos");
            } else {
                System.out.println("   ✅ Todos los campos requeridos están llenos");
            }

            // 2. Verificar checkboxes
            verificarEstadoCheckboxes();

            // 3. Verificar errores visibles
            boolean hayErrores = mostrarMensajesErrorActuales();
            if (!hayErrores) {
                System.out.println("   ✅ No hay errores de validación visibles");
            }

        } catch (Exception e) {
            System.out.println("   ⚠️  Error validando: " + e.getMessage());
        }
    }

    // ✅ NUEVO: Verificar estado de checkboxes
    private void verificarEstadoCheckboxes() {
        try {
            List<WebElement> checkboxes = DriverManager.getDriver().findElements(
                    By.xpath("//input[@type='checkbox']")
            );

            System.out.println("   📋 Estado de checkboxes:");
            int marcados = 0;

            for (int i = 0; i < checkboxes.size(); i++) {
                WebElement cb = checkboxes.get(i);
                boolean checked = cb.isSelected() ||
                        "true".equals(cb.getAttribute("checked")) ||
                        cb.getAttribute("class").contains("checked");

                if (checked) {
                    marcados++;
                    System.out.println("      ✅ Checkbox " + i + ": MARCADO");
                } else {
                    System.out.println("      ⬜ Checkbox " + i + ": NO MARCADO");
                }
            }

            System.out.println("   📊 Total marcados: " + marcados + " de " + checkboxes.size());

            if (marcados == 0) {
                System.out.println("   ⚠️  ADVERTENCIA: Ningún checkbox marcado - puede causar error");
            }

        } catch (Exception e) {
            System.out.println("   ⚠️  Error verificando checkboxes: " + e.getMessage());
        }
    }

    // ✅ Remover overlay forzadamente
    private void removerOverlayForzadamente() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            js.executeScript(
                    "var overlays = document.querySelectorAll('.cdk-overlay-backdrop');" +
                            "overlays.forEach(function(o) { " +
                            "  o.style.display = 'none'; " +
                            "  o.style.visibility = 'hidden'; " +
                            "  o.remove(); " +
                            "});"
            );
            Thread.sleep(500);
            System.out.println("   ✅ Overlay removido");
        } catch (Exception e) {
            System.out.println("   ⚠️  Error removiendo overlay: " + e.getMessage());
        }
    }

    // Mostrar mensajes de error
    private boolean mostrarMensajesErrorActuales() {
        try {
            List<WebElement> errores = DriverManager.getDriver().findElements(
                    By.xpath("//mat-error | //div[contains(@class, 'error')]")
            );

            boolean hayErrores = false;
            for (WebElement error : errores) {
                try {
                    if (error.isDisplayed()) {
                        if (!hayErrores) {
                            System.out.println("   ❌ Errores encontrados:");
                            hayErrores = true;
                        }
                        System.out.println("      - " + error.getText());
                    }
                } catch (Exception e) {
                    // Ignorar
                }
            }

            return hayErrores;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickCrearConEspera() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            js.executeScript("arguments[0].click();", createButton);
            Thread.sleep(6000);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean verificarProductoGuardado(String nombreProducto) {
        try {
            System.out.println("┌─────────────────────────────────────────────────┐");
            System.out.println("│ 🔹 VERIFICANDO PRODUCTO CREADO                  │");
            System.out.println("└─────────────────────────────────────────────────┘");

            System.out.println("🔍 Buscando: '" + nombreProducto + "'");
            Thread.sleep(3000);

            String[] selectores = {
                    "//*[contains(text(), '" + nombreProducto + "')]",
                    "//td[contains(text(), '" + nombreProducto + "')]",
                    "//tr[contains(., '" + nombreProducto + "')]"
            };

            for (String selector : selectores) {
                try {
                    List<WebElement> productos = DriverManager.getDriver().findElements(By.xpath(selector));
                    if (!productos.isEmpty() && productos.get(0).isDisplayed()) {
                        System.out.println("✅ Producto encontrado");
                        return true;
                    }
                } catch (Exception e) {
                    // Continuar
                }
            }

            System.out.println("⚠️  Producto no encontrado");
            return false;

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            return false;
        }
    }

    public void clickGuardar() {
        clickCrearFormulario();
    }

    public void llenarFormularioBasico() {
        System.out.println("🔹 Método llenarFormularioBasico() -> delegando...");
        llenarFormularioCompleto();
    }
}