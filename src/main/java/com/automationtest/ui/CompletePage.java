package com.automationtest.ui;

import org.openqa.selenium.By;

/**
 * Page Object para la página de Confirmación de Orden de Saucedemo
 * Contiene los selectores para interactuar con la página de confirmación
 */
public class CompletePage {

    private CompletePage() {
        // Clase de selectores, no se debe instanciar
    }

    /**
     * Selectores (localizadores) para los elementos de la página de confirmación
     */
    public static final By THANKS_MESSAGE = By.cssSelector("[data-test=\"complete-header\"]");
    public static final By COMPLETE_TEXT = By.cssSelector("[data-test=\"complete-text\"]");
    public static final By BACK_HOME_BUTTON = By.cssSelector("[data-test=\"back-to-products\"]");
}
