package com.automationtest.tasks;

import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actor.Actor;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

import com.automationtest.ui.LoginPage;
import com.automationtest.ui.InventoryPage;

/**
 * Task para realizar acciones de Login en Saucedemo
 * Contiene los métodos para navegación y autenticación
 */
public class LoginTask implements Task {

    private static final String BASE_URL = "https://www.saucedemo.com";

    /**
     * Navega a la página de Saucedemo
     */
    public static LoginTask navigateToSwagLabs() {
        return new LoginTask() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                actor.attemptsTo(
                    Open.browserOn().the(BASE_URL)
                );
            }
        };
    }

    /**
     * Ingresa el usuario y contraseña y realiza validaciones según el tipo de usuario
     * @param username El nombre de usuario
     * @param password La contraseña
     */
    public static LoginTask typeUsernameAndPassword(String username, String password) {
        return new LoginTask() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                // Ingresar credenciales
                actor.attemptsTo(
                    Enter.theValue(username).into(LoginPage.USERNAME_INPUT),
                    Enter.theValue(password).into(LoginPage.PASSWORD_INPUT),
                    Click.on(LoginPage.LOGIN_BUTTON)
                );

                // Validaciones según el tipo de usuario
                switch (username) {
                    case "standard_user":
                    case "problem_user":
                        validateSuccessfulLogin(actor);
                        break;

                    case "locked_out_user":
                        validateLockedOutUser(actor);
                        break;

                    case "performance_glitch_user":
                        validatePerformanceGlitchUser(actor);
                        break;

                    default:
                        throw new IllegalArgumentException("Unknown user type: " + username);
                }
            }
        };
    }

    /**
     * Valida el login exitoso para usuarios estándar
     */
    private static void validateSuccessfulLogin(Actor actor) {
        actor.attemptsTo(
            Ensure.that(InventoryPage.INVENTORY_ITEMS).isPresent()
        );
    }

    /**
     * Valida el mensaje de error para usuario bloqueado
     */
    private static void validateLockedOutUser(Actor actor) {
        actor.attemptsTo(
            Ensure.that(LoginPage.ERROR_MESSAGE).isPresent()
        );
    }

    /**
     * Valida el login con retraso para usuario con problemas de rendimiento
     */
    private static void validatePerformanceGlitchUser(Actor actor) {
        try {
            Thread.sleep(10000); // Esperar 10 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        actor.attemptsTo(
            Ensure.that(InventoryPage.INVENTORY_ITEMS).isPresent()
        );
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Método base no implementa nada
    }
}
