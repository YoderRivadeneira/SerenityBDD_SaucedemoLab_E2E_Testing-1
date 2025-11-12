package com.automationtest.ui;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebDriver;

/**
 * Clase base para todas las Page Objects
 */
public class BasePage extends PageObject {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Espera a que la página se cargue completamente
     */
    public void waitForPageLoad() {
        // Implementar lógica de espera
    }
}
