package automationtest.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Serenity Test Runner con opciones de ejecución específicas
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"automationtest.stepdefinitions"},
    plugin = {
        "pretty",
        "json:target/cucumber-reports/cucumber.json",
        "html:target/cucumber-reports/cucumber-report.html",
        "junit:target/cucumber-reports/cucumber.xml",
        "json:target/test-results/cucumber.json"
    },
    monochrome = false,
    tags = "not @ignore",
    dryRun = false,
    strict = false
)
public class SaucedemoTestRunner {
}
