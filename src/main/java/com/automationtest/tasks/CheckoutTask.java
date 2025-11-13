package com.automationtest.tasks;

import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actor.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automationtest.ui.CheckoutPage;

/**
 * Task para realizar acciones en la página de Checkout de Saucedemo
 * Contiene métodos para llenar información de checkout y validaciones
 */
public class CheckoutTask implements Task {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutTask.class);
    private static final String CHECKOUT_STEP_TWO_URL = "checkout-step-two.html";
    private static final int TIMEOUT_SECONDS = 10;

    /**
     * Llena el formulario de checkout con los datos proporcionados
     * @param firstName El nombre
     * @param lastName El apellido
     * @param postalCode El código postal
     */
    public static CheckoutTask fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        return new CheckoutTask() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                LOGGER.info("Filling checkout form - First Name: {}, Last Name: {}, Postal Code: {}", 
                    firstName, lastName, postalCode);

                WebDriver driver = getWebDriver();
                
                // Limpiar y llenar nombre
                WebElement firstNameInput = driver.findElement(CheckoutPage.FIRST_NAME_INPUT);
                firstNameInput.clear();
                firstNameInput.sendKeys(firstName);
                
                // Limpiar y llenar apellido
                WebElement lastNameInput = driver.findElement(CheckoutPage.LAST_NAME_INPUT);
                lastNameInput.clear();
                lastNameInput.sendKeys(lastName);
                
                // Limpiar y llenar código postal
                WebElement postalCodeInput = driver.findElement(CheckoutPage.POSTAL_CODE_INPUT);
                postalCodeInput.clear();
                postalCodeInput.sendKeys(postalCode);
                
                // Hacer clic en continuar
                actor.attemptsTo(Click.on(CheckoutPage.CONTINUE_BUTTON));
                
                LOGGER.info("Checkout form filled and submitted successfully");
            }
        };
    }

    /**
     * Verifica que la página de checkout se haya cargado correctamente
     * Para problem_user espera a que la página esté en estado networkidle
     * Para otros usuarios valida que la URL sea checkout-step-two.html
     * @param username El nombre del usuario
     */
    public static CheckoutTask verifyCheckoutPage(String username) {
        return new CheckoutTask() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                LOGGER.info("Verifying checkout page for user: {}", username);
                
                if ("problem_user".equals(username)) {
                    waitForNetworkIdle();
                    LOGGER.info("Network idle state reached for problem_user");
                } else {
                    waitForCheckoutStepTwo();
                    validateCheckoutURL();
                    LOGGER.info("Checkout step two URL verified");
                }
            }
        };
    }

    /**
     * Hace clic en el botón Finish
     * Para problem_user espera a que la página esté en estado networkidle
     * Para otros usuarios valida que el botón sea visible antes de hacer clic
     * @param username El nombre del usuario
     */
    public static CheckoutTask clickFinishButton(String username) {
        return new CheckoutTask() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                LOGGER.info("Clicking finish button for user: {}", username);
                
                if ("problem_user".equals(username)) {
                    waitForNetworkIdle();
                    LOGGER.info("Network idle state reached for problem_user before clicking finish");
                } else {
                    actor.attemptsTo(
                        Ensure.that(CheckoutPage.FINISH_BUTTON).isPresent()
                    );
                    actor.attemptsTo(Click.on(CheckoutPage.FINISH_BUTTON));
                    LOGGER.info("Finish button clicked successfully");
                }
            }
        };
    }

    /**
     * Verifica que aparezca un mensaje de error en el formulario de checkout
     * @param expectedMessage El mensaje de error esperado
     */
    public static CheckoutTask verifyErrorMessageInCheckoutForm(String expectedMessage) {
        return new CheckoutTask() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                LOGGER.info("Verifying error message in checkout form: {}", expectedMessage);
                
                WebDriver driver = getWebDriver();
                WebElement errorElement = driver.findElement(CheckoutPage.ERROR_CHECKOUT_FORM_MESSAGE);
                
                actor.attemptsTo(
                    Ensure.that(CheckoutPage.ERROR_CHECKOUT_FORM_MESSAGE).isPresent()
                );
                
                String actualMessage = errorElement.getText();
                if (!actualMessage.equals(expectedMessage)) {
                    throw new AssertionError(
                        String.format("Expected error message: '%s', but found: '%s'", 
                            expectedMessage, actualMessage)
                    );
                }
                
                LOGGER.info("Error message validated: {}", actualMessage);
            }
        };
    }

    /**
     * Espera a que la página esté en estado networkidle
     */
    private static void waitForNetworkIdle() {
        // Esperar un tiempo razonable para que la red se estabilice
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Espera a que la URL contenga checkout-step-two.html
     */
    private static void waitForCheckoutStepTwo() {
        WebDriver driver = getWebDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));
        
        try {
            wait.until(ExpectedConditions.urlContains(CHECKOUT_STEP_TWO_URL));
        } catch (Exception e) {
            LOGGER.warn("Timeout waiting for checkout-step-two URL");
        }
    }

    /**
     * Valida que la URL actual contenga checkout-step-two.html
     */
    private static void validateCheckoutURL() {
        WebDriver driver = getWebDriver();
        String currentURL = driver.getCurrentUrl();
        
        if (!currentURL.contains(CHECKOUT_STEP_TWO_URL)) {
            throw new AssertionError(
                String.format("Expected URL to contain '%s', but current URL is: '%s'", 
                    CHECKOUT_STEP_TWO_URL, currentURL)
            );
        }
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
