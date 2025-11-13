package com.automationtest.tasks;

import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actor.Actor;
import net.serenitybdd.screenplay.actions.Click;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automationtest.ui.InventoryPage;

/**
 * Task para realizar acciones en la página de Inventario de Saucedemo
 * Contiene métodos para agregar items, validar imágenes y logout
 */
public class InventoryTask implements Task {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryTask.class);
    private static final Random RANDOM = new Random();

    /**
     * Agrega items aleatorios al carrito
     * Para problem_user agrega 2 items, para otros usuarios agrega 4 items
     * @param username El nombre del usuario
     */
    public static InventoryTask addRandomItemsToCart(String username) {
        return new InventoryTask() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                WebDriver driver = getWebDriver();
                List<WebElement> inventoryItems = driver.findElements(InventoryPage.INVENTORY_ITEMS);
                int totalItems = inventoryItems.size();

                validateItemsAvailable(totalItems);

                int itemsToAdd = calculateItemsToAdd(username, totalItems);
                List<Integer> selectedIndexes = selectRandomIndexes(itemsToAdd, totalItems);
                clickSelectedItems(inventoryItems, selectedIndexes);
                navigateToCart(actor);
            }
        };
    }

    /**
     * Valida que la página de Inventario tenga items disponibles
     */
    private static void validateItemsAvailable(int totalItems) {
        if (totalItems == 0) {
            throw new IllegalArgumentException("No hay items disponibles para seleccionar.");
        }
    }

    /**
     * Calcula la cantidad de items a agregar según el usuario
     */
    private static int calculateItemsToAdd(String username, int totalItems) {
        return "problem_user".equals(username) ? 2 : Math.min(4, totalItems);
    }

    /**
     * Selecciona índices aleatorios únicos
     */
    private static List<Integer> selectRandomIndexes(int itemsToAdd, int totalItems) {
        List<Integer> selectedIndexes = new ArrayList<>();
        while (selectedIndexes.size() < itemsToAdd && selectedIndexes.size() < totalItems) {
            int randomIndex = RANDOM.nextInt(totalItems);
            if (!selectedIndexes.contains(randomIndex)) {
                selectedIndexes.add(randomIndex);
            }
        }
        return selectedIndexes;
    }

    /**
     * Hace clic en los items seleccionados
     */
    private static void clickSelectedItems(List<WebElement> inventoryItems, List<Integer> selectedIndexes) {
        for (Integer index : selectedIndexes) {
            try {
                WebElement item = inventoryItems.get(index);
                WebElement button = item.findElement(By.cssSelector("button[data-test*='add-to-cart']"));
                button.click();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.warn("Click en item {} fue interrumpido", index);
            } catch (Exception e) {
                LOGGER.warn("Click en item {} falló, continuando...", index);
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Navega al carrito de compras
     */
    private static void navigateToCart(Actor actor) {
        actor.attemptsTo(Click.on(InventoryPage.SHOPPING_CART_LINK));
    }

    /**
     * Valida que las imágenes de los items se hayan cargado correctamente
     * Comprueba que no todas las imágenes sean idénticas
     */
    public static InventoryTask validateItemImages() {
        return new InventoryTask() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                WebDriver driver = getWebDriver();
                List<WebElement> images = driver.findElements(InventoryPage.ITEM_IMAGE);
                List<String> imageSources = extractImageSources(images);

                validateImageSourcesExist(imageSources);
                checkImageLoadStatus(imageSources);
            }
        };
    }

    /**
     * Extrae los src de todas las imágenes
     */
    private static List<String> extractImageSources(List<WebElement> images) {
        List<String> imageSources = new ArrayList<>();
        for (WebElement img : images) {
            String src = img.getAttribute("src");
            if (src != null && !src.isEmpty()) {
                imageSources.add(src);
            }
        }
        return imageSources;
    }

    /**
     * Valida que se encontraron imágenes para validar
     */
    private static void validateImageSourcesExist(List<String> imageSources) {
        if (imageSources.isEmpty()) {
            throw new IllegalStateException("No se encontraron imágenes para validar.");
        }
    }

    /**
     * Comprueba el estado de carga de las imágenes
     */
    private static void checkImageLoadStatus(List<String> imageSources) {
        String firstImage = imageSources.get(0);
        boolean allImagesAreSame = imageSources.stream()
            .allMatch(src -> src.equals(firstImage));

        if (allImagesAreSame) {
            LOGGER.info("All images are the same. They did not load correctly.");
        } else {
            LOGGER.info("The images are different. They loaded correctly.");
        }
    }

    /**
     * Realiza el logout del usuario
     * Abre el menú hamburguesa y hace clic en logout
     */
    public static InventoryTask logout() {
        return new InventoryTask() {
            @Override
            public <T extends Actor> void performAs(T actor) {
                actor.attemptsTo(Click.on(InventoryPage.OPEN_MENU_BUTTON));

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                actor.attemptsTo(Click.on(InventoryPage.LOGOUT_LINK));
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
