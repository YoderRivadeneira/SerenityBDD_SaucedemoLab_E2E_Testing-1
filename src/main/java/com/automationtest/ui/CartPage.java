package com.automationtest.ui;

import org.openqa.selenium.By;

/**
 * Page Object para la página del Carrito de Compras de Saucedemo
 * Contiene los selectores para interactuar con la página del carrito
 */
public class CartPage {

    private CartPage() {
        // Clase de selectores, no se debe instanciar
    }

    /**
     * Selectores (localizadores) para los elementos de la página del carrito
     */
    public static final By CART_ITEMS = By.cssSelector("[data-test=\"inventory-item\"]");
    public static final By CHECKOUT_BUTTON = By.cssSelector("[data-test=\"checkout\"]");
    public static final By CONTINUE_SHOPPING_BUTTON = By.cssSelector("[data-test=\"continue-shopping\"]");
}
