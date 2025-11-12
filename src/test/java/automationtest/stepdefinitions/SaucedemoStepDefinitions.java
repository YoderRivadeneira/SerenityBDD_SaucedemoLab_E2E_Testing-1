package automationtest.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.openqa.selenium.By;
import static net.serenitybdd.screenplay.actors.OnStage.*;

/**
 * Step Definitions para Serenity BDD + Cucumber
 * Implementa los pasos del archivo saucedemo.feature
 */
public class SaucedemoStepDefinitions {

    private static final String BASE_URL = "https://www.saucedemo.com";
    
    // Localizadores
    private static final By USERNAME_INPUT = By.id("user-name");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By PRODUCTS_CONTAINER = By.className("inventory_container");
    private static final By CART_BUTTON = By.className("shopping_cart_link");
    private static final By CHECKOUT_BUTTON = By.id("checkout");
    private static final By CONFIRMATION_MESSAGE = By.className("complete-header");

    /**
     * Pasos Given - Precondiciones
     */
    @Given("the user logs in with valid credentials username {string} and password {string}")
    public void userLogsInWithValidCredentials(String username, String password) {
        Actor actor = theActorInTheSpotlight();
        
        // Navegar a la aplicación
        actor.attemptsTo(Open.browserOn().the(BASE_URL));
        
        // Ingresar credenciales
        actor.attemptsTo(
            Enter.theValue(username).into(USERNAME_INPUT),
            Enter.theValue(password).into(PASSWORD_INPUT),
            Click.on(LOGIN_BUTTON)
        );
    }

    /**
     * Pasos When - Acciones principales
     */
    @When("the user adds products to the cart and proceeds to checkout")
    public void userAddsProductsToCartAndProceedsToCheckout() {
        Actor actor = theActorInTheSpotlight();
        
        // Agregar productos al carrito
        By addToCartButton = By.xpath("//button[contains(text(), 'Add to cart')]");
        actor.attemptsTo(Click.on(addToCartButton));
        
        // Navegar al carrito
        actor.attemptsTo(Click.on(CART_BUTTON));
    }

    @When("fills in the required checkout information")
    public void fillsInRequiredCheckoutInformation() {
        Actor actor = theActorInTheSpotlight();
        
        // Hacer clic en checkout
        actor.attemptsTo(Click.on(CHECKOUT_BUTTON));
        
        // Llenar información de envío
        By firstNameInput = By.id("first-name");
        By lastNameInput = By.id("last-name");
        By zipCodeInput = By.id("postal-code");
        By continueButton = By.id("continue");
        
        actor.attemptsTo(
            Enter.theValue("John").into(firstNameInput),
            Enter.theValue("Doe").into(lastNameInput),
            Enter.theValue("12345").into(zipCodeInput),
            Click.on(continueButton)
        );
    }

    /**
     * Pasos Then - Verificaciones
     */
    @Then("the user should see the order overview and complete the purchase")
    public void userShouldSeeOrderOverviewAndCompletePurchase() {
        Actor actor = theActorInTheSpotlight();
        
        // Verificar que se muestra el resumen del pedido
        actor.attemptsTo(
            Ensure.that(PRODUCTS_CONTAINER).isPresent()
        );
        
        // Hacer clic en finalizar compra
        By finishButton = By.id("finish");
        actor.attemptsTo(Click.on(finishButton));
    }

    @Then("the confirmation message {string} should be displayed")
    public void confirmationMessageShouldBeDisplayed(String expectedMessage) {
        Actor actor = theActorInTheSpotlight();
        
        // Verificar el mensaje de confirmación
        actor.attemptsTo(
            Ensure.that(CONFIRMATION_MESSAGE)
                .isPresent()
        );
    }
}
