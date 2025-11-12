package com.automationtest.ui;

import org.openqa.selenium.By;

/**
 * Page Object para la página de Checkout de Saucedemo
 * Contiene los selectores para interactuar con la página de checkout
 */
public class CheckoutPage {

    private CheckoutPage() {
        // Clase de selectores, no se debe instanciar
    }

    /**
     * Selectores (localizadores) para los elementos de la página de checkout
     */
    public static final By FIRST_NAME_INPUT = By.cssSelector("[data-test=\"firstName\"]");
    public static final By LAST_NAME_INPUT = By.cssSelector("[data-test=\"lastName\"]");
    public static final By POSTAL_CODE_INPUT = By.cssSelector("[data-test=\"postalCode\"]");
    public static final By CONTINUE_BUTTON = By.cssSelector("[data-test=\"continue\"]");
    public static final By FINISH_BUTTON = By.cssSelector("[data-test=\"finish\"]");
    public static final By ERROR_CHECKOUT_FORM_MESSAGE = By.cssSelector("[data-test=\"error\"]");
}
