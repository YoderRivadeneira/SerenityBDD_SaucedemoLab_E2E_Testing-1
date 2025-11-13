package com.automationtest.tasks;

import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actor.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automationtest.ui.CartPage;

/**
 * Task para realizar acciones en la página del Carrito de Saucedemo
 * Contiene métodos para validar cantidad de items y proceder al checkout
 */
public class CartTask implements Task {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartTask.class);
    private static final int EXPECTED_ITEM_COUNT = 4;

    /**
     * Valida la cantidad de items en el carrito
     * Para problem_user: valida que haya al menos 1 item
     * Para otros usuarios: valida que haya exactamente 4 items
     * @param username El nombre del usuario
     */
    public static CartTask cartItemsCounter(String username) {
        return new CartTask() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                WebDriver driver = getWebDriver();
                List<WebElement> cartItems = driver.findElements(CartPage.CART_ITEMS);
                int itemCount = cartItems.size();

                validateCartItems(username, itemCount);
            }
        };
    }

    /**
     * Valida la cantidad de items según el tipo de usuario
     */
    private static void validateCartItems(String username, int itemCount) {
        if ("problem_user".equals(username)) {
            if (itemCount < 1) {
                throw new IllegalStateException("No items found in cart for problem_user");
            }
            LOGGER.info("Cart items for problem_user: {}", itemCount);
        } else {
            if (itemCount != EXPECTED_ITEM_COUNT) {
                throw new AssertionError(
                    String.format("Expected %d items in cart, but found %d", EXPECTED_ITEM_COUNT, itemCount)
                );
            }
            LOGGER.info("Cart has the expected {} items", EXPECTED_ITEM_COUNT);
        }
    }

    /**
     * Procede al checkout
     * Verifica que el botón de checkout sea visible y hace clic en él
     */
    public static CartTask proceedToCheckout() {
        return new CartTask() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                actor.attemptsTo(
                    Ensure.that(CartPage.CHECKOUT_BUTTON).isPresent()
                );

                actor.attemptsTo(
                    Click.on(CartPage.CHECKOUT_BUTTON)
                );
                
                LOGGER.info("User proceeded to checkout");
            }
        };
    }

    /**
     * Obtiene el WebDriver de Serenity
     */
    private static WebDriver getWebDriver() {
        return org.openqa.selenium.WebDriver.class.cast(
            net.serenitybdd.core.Serenity.getDriver()
        );
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Método base no implementa nada
    }
}
