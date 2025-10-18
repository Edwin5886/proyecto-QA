package com.bombero.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.bombero.utils.DriverManager;

public class LoginPage {

    public LoginPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    // Locators para login
    @FindBy(xpath = "//input[@type='text']")
    public WebElement usernameInput;

    @FindBy(xpath = "//input[@type='password']")
    public WebElement passwordInput;

    @FindBy(xpath = "//button[contains(., 'Iniciar sesión')]")
    public WebElement loginButton;

    // Locators para logout - BUSCAREMOS ESTOS ELEMENTOS
    @FindBy(xpath = "//button[contains(., 'Cerrar') or contains(., 'Salir') or contains(., 'Logout')]")
    public WebElement logoutButton;

    @FindBy(xpath = "//div[contains(@class, 'user') or contains(@class, 'avatar') or contains(@class, 'menu')]")
    public WebElement userMenu;

    @FindBy(xpath = "//img[contains(@alt, 'user') or contains(@alt, 'avatar')]")
    public WebElement userAvatar;

    // Método optimizado para login
    public void quickLogin(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
    }
}
