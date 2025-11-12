package com.automationtest.ui;

import org.openqa.selenium.By;

/**
 * Page Object para la página de Login de Saucedemo
 * Contiene los selectores para interactuar con la página de login
 */
public class LoginPage {

    private LoginPage() {
        // Clase de selectores, no se debe instanciar
    }

    /**
     * Selectores (localizadores) para los elementos de la página de login
     */
    public static final By USERNAME_INPUT = By.cssSelector("[data-test=\"username\"]");
    public static final By PASSWORD_INPUT = By.cssSelector("[data-test=\"password\"]");
    public static final By LOGIN_BUTTON = By.cssSelector("[data-test=\"login-button\"]");
    public static final By ERROR_MESSAGE = By.cssSelector("[data-test=\"error\"]");
}
