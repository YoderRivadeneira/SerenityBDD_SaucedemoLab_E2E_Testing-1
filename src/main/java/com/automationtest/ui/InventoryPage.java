package com.automationtest.ui;

import org.openqa.selenium.By;

/**
 * Page Object para la página de Inventario de Saucedemo
 * Contiene los selectores para interactuar con la página de productos
 */
public class InventoryPage {

    private InventoryPage() {
        // Clase de selectores, no se debe instanciar
    }

    /**
     * Selectores (localizadores) para los elementos de la página de inventario
     */
    public static final By ITEM_IMAGE = By.cssSelector(".inventory_item_img");
    public static final By INVENTORY_ITEMS = By.cssSelector("[data-test=\"inventory-item\"]");
    public static final By ADD_TO_CART_BUTTON = By.xpath("//button[contains(text(), 'Add to cart')]");
    public static final By REMOVE_BUTTON = By.xpath("//button[contains(text(), 'Remove')]");
    public static final By SHOPPING_CART_LINK = By.cssSelector("[data-test=\"shopping-cart-link\"]");
    public static final By OPEN_MENU_BUTTON = By.cssSelector("#react-burger-menu-btn");
    public static final By LOGOUT_LINK = By.cssSelector("[data-test=\"logout-sidebar-link\"]");
}
