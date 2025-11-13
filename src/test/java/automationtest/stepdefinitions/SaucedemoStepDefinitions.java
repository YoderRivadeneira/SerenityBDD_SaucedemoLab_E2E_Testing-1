package automationtest.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.Cast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automationtest.tasks.LoginTask;
import com.automationtest.tasks.InventoryTask;
import com.automationtest.tasks.CartTask;
import com.automationtest.tasks.CheckoutTask;
import com.automationtest.tasks.CompleteTask;

/**
 * Step Definitions para Serenity BDD + Cucumber
 * Implementa los pasos del archivo saucedemo.feature
 */
public class SaucedemoStepDefinitions {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaucedemoStepDefinitions.class);
    private Actor actor;

    @Before
    public void setUp() {
        OnStage.setTheStage(new Cast());
        actor = OnStage.theActorCalled("User");
        LOGGER.info("Test setup completed");
    }

    /**
     * Pasos Given - Precondiciones
     */
    @Given("the user logs in with valid credentials username {string} and password {string}")
    public void userLogsInWithValidCredentials(String username, String password) {
        LOGGER.info("User logging in with username: {}", username);
        
        actor.attemptsTo(
            LoginTask.navigateToSwagLabs(),
            LoginTask.typeUsernameAndPassword(username, password)
        );
        
        LOGGER.info("User successfully logged in");
    }

    /**
     * Pasos When - Acciones principales
     */
    @When("the user adds products to the cart and proceeds to checkout")
    public void userAddsProductsToCartAndProceedsToCheckout() {
        LOGGER.info("User adding products to cart");
        
        actor.attemptsTo(
            InventoryTask.addRandomItemsToCart("standard_user")
        );
        
        LOGGER.info("Products added to cart successfully");
    }

    @When("the user adds products to the cart for {string}")
    public void userAddsProductsToCart(String username) {
        LOGGER.info("User {} adding products to cart", username);
        
        actor.attemptsTo(
            InventoryTask.addRandomItemsToCart(username)
        );
        
        LOGGER.info("Products added to cart for user: {}", username);
    }

    @When("fills in the required checkout information")
    public void fillsInRequiredCheckoutInformation() {
        LOGGER.info("User filling checkout information");
        
        actor.attemptsTo(
            CheckoutTask.fillCheckoutInformation("John", "Doe", "12345")
        );
        
        LOGGER.info("Checkout information filled successfully");
    }

    @When("the user validates the cart items for {string}")
    public void userValidatesCartItems(String username) {
        LOGGER.info("Validating cart items for user: {}", username);
        
        actor.attemptsTo(
            CartTask.cartItemsCounter(username)
        );
        
        LOGGER.info("Cart items validated successfully");
    }

    @When("the user validates item images")
    public void userValidatesItemImages() {
        LOGGER.info("Validating item images");
        
        actor.attemptsTo(
            InventoryTask.validateItemImages()
        );
        
        LOGGER.info("Item images validated successfully");
    }

    /**
     * Pasos Then - Verificaciones
     */
    @Then("the user should see the order overview and complete the purchase")
    public void userShouldSeeOrderOverviewAndCompletePurchase() {
        LOGGER.info("Completing the purchase");
        
        actor.attemptsTo(
            CartTask.proceedToCheckout()
        );
        
        LOGGER.info("Purchase completed successfully");
    }

    @Then("the confirmation message {string} should be displayed")
    public void confirmationMessageShouldBeDisplayed(String expectedMessage) {
        LOGGER.info("Verifying confirmation message: {}", expectedMessage);
        
        actor.attemptsTo(
            CompleteTask.verifyOrderCompletion()
        );
        
        LOGGER.info("Confirmation message verified");
    }

    @Then("the user should be able to go back to home")
    public void userShouldBeAbleToGoBackToHome() {
        LOGGER.info("User returning to home");
        
        actor.attemptsTo(
            CompleteTask.backToHome()
        );
        
        LOGGER.info("User successfully returned to home");
    }

    @Then("the user should be able to logout")
    public void userShouldBeAbleToLogout() {
        LOGGER.info("User logging out");
        
        actor.attemptsTo(
            InventoryTask.logout()
        );
        
        LOGGER.info("User successfully logged out");
    }
}
