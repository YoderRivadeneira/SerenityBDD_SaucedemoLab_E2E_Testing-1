package com.automationtest.tasks;

import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actor.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automationtest.ui.CompletePage;

/**
 * Task para realizar acciones en la página de Confirmación de Orden de Saucedemo
 * Contiene métodos para validar la orden completada y volver al inicio
 */
public class CompleteTask implements Task {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompleteTask.class);
    private static final String EXPECTED_THANKS_MESSAGE = "Thank you for your order!";
    private static final String EXPECTED_COMPLETE_TEXT = "Your order has been dispatched, and will arrive just as fast as the pony can get there!";
    private static final String INVENTORY_URL_PATTERN = "inventory.html";

    /**
     * Verifica que la orden se haya completado exitosamente
     * Valida que los mensajes de confirmación sean visibles y contengan el texto esperado
     */
    public static CompleteTask verifyOrderCompletion() {
        return new CompleteTask() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                actor.attemptsTo(
                    Ensure.that(CompletePage.THANKS_MESSAGE).isPresent()
                );

                validateThanksMessage(actor);
                validateCompleteText(actor);
                
                LOGGER.info("Order completion verification passed successfully");
            }
        };
    }

    /**
     * Valida que el mensaje de agradecimiento sea visible y contenga el texto esperado
     */
    private static void validateThanksMessage(Actor actor) {
        WebDriver driver = getWebDriver();
        WebElement thanksMessageElement = driver.findElement(CompletePage.THANKS_MESSAGE);
        
        actor.attemptsTo(
            Ensure.that(CompletePage.THANKS_MESSAGE).isDisplayed()
        );

        String actualText = thanksMessageElement.getText();
        if (!actualText.equals(EXPECTED_THANKS_MESSAGE)) {
            throw new AssertionError(
                String.format("Expected thanks message: '%s', but found: '%s'", 
                    EXPECTED_THANKS_MESSAGE, actualText)
            );
        }
        LOGGER.info("Thanks message validated: {}", actualText);
    }

    /**
     * Valida que el texto de confirmación sea visible y contenga el texto esperado
     */
    private static void validateCompleteText(Actor actor) {
        WebDriver driver = getWebDriver();
        WebElement completeTextElement = driver.findElement(CompletePage.COMPLETE_TEXT);
        
        actor.attemptsTo(
            Ensure.that(CompletePage.COMPLETE_TEXT).isDisplayed()
        );

        String actualText = completeTextElement.getText();
        if (!actualText.equals(EXPECTED_COMPLETE_TEXT)) {
            throw new AssertionError(
                String.format("Expected complete text: '%s', but found: '%s'", 
                    EXPECTED_COMPLETE_TEXT, actualText)
            );
        }
        LOGGER.info("Complete text validated: {}", actualText);
    }

    /**
     * Vuelve a la página de inicio (inventario)
     * Verifica que el botón sea visible y hace clic en él
     * Espera a que la URL contenga inventory.html
     */
    public static CompleteTask backToHome() {
        return new CompleteTask() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                actor.attemptsTo(
                    Ensure.that(CompletePage.BACK_HOME_BUTTON).isPresent()
                );

                actor.attemptsTo(
                    Click.on(CompletePage.BACK_HOME_BUTTON)
                );

                waitForInventoryPage();
                LOGGER.info("User navigated back to home (inventory page)");
            }
        };
    }

    /**
     * Espera a que la página cargue la URL del inventario
     */
    private static void waitForInventoryPage() {
        WebDriver driver = getWebDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        wait.until(ExpectedConditions.urlContains(INVENTORY_URL_PATTERN));
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
