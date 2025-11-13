# 🚀 Serenity BDD - Saucedemo E2E Testing

Proyecto de pruebas End-to-End para la aplicación Saucedemo usando **Serenity BDD** y **Cucumber**.

## 📋 Requisitos previos

- Java 11 o superior
- Maven 3.6.0 o superior
- ChromeDriver (se descarga automáticamente con Serenity)
- Git

## 🏗️ Estructura del proyecto

```
src/
├── main/java/com/automationtest/
│   ├── ui/                    # Page Objects (selectores)
│   │   ├── LoginPage.java
│   │   ├── InventoryPage.java
│   │   ├── CartPage.java
│   │   ├── CheckoutPage.java
│   │   └── CompletePage.java
│   ├── tasks/                 # Tasks (acciones)
│   │   ├── LoginTask.java
│   │   ├── InventoryTask.java
│   │   ├── CartTask.java
│   │   ├── CheckoutTask.java
│   │   └── CompleteTask.java
│   └── util/                  # Utilidades
│       └── Constants.java
│
└── test/
    ├── java/automationtest/
    │   ├── stepdefinitions/   # Glue code (pasos Cucumber)
    │   │   └── SaucedemoStepDefinitions.java
    │   └── runners/           # Test Runners
    │       ├── CucumberTestRunner.java
    │       └── SaucedemoTestRunner.java
    │
    └── resources/
        ├── features/          # Archivos .feature
        │   └── saucedemo.feature
        ├── serenity.properties
        └── logback.xml

serenity.conf                  # Configuración de Serenity
```

## 🔧 Configuración

### Archivo: `serenity.conf`

Define la configuración del navegador, URLs base y comportamiento de las pruebas:

```conf
webdriver {
  driver = chrome                    # Navegador a usar
  headless = false                  # Mostrar el navegador
  restart.browser.for.each.scenario = true  # Reiniciar navegador entre escenarios
}

serenity {
  take.screenshots = "FOR_EACH_ACTION"  # Tomar screenshot de cada acción
  browser.maximized = true              # Maximizar ventana del navegador
}
```

### Archivo: `serenity.properties`

Configuración adicional de WebDriver:

- `webdriver.driver=chrome` - Define el navegador
- `webdriver.base.url` - URL base de la aplicación
- `headless.mode=false` - Mostrar el navegador
- `serenity.take.screenshots=FOR_EACH_ACTION` - Capturas en cada acción

## 🎯 Cómo ejecutar las pruebas

### Opción 1: Ejecutar con Maven (recomendado)

```bash
# Limpiar, compilar y ejecutar pruebas
mvn clean test

# Solo compilar
mvn clean compile

# Ejecutar solo pruebas
mvn test

# Generar reporte de Serenity
mvn serenity:aggregate
```

### Opción 2: Usar el script

```bash
# Dar permisos de ejecución
chmod +x run-tests.sh

# Ejecutar el script
./run-tests.sh
```

### Opción 3: Ejecutar desde IDE

Hacer clic derecho en `CucumberTestRunner` o `SaucedemoTestRunner` → Run As → JUnit Test

## 📊 Reportes

Después de ejecutar las pruebas, se generan reportes en:

- **Serenity Report**: `target/site/serenity/index.html`
- **Cucumber Report**: `target/cucumber-reports/index.html`

Para ver el reporte de Serenity:

```bash
# En Linux/Mac
open target/site/serenity/index.html

# En Windows
start target/site/serenity/index.html
```

## 📝 Escribir nuevas pruebas

### 1. Crear un archivo .feature

```gherkin
Feature: Nueva funcionalidad

  Scenario: Descripción del escenario
    Given el usuario está en la página de login
    When el usuario realiza una acción
    Then se debería ver un resultado
```

### 2. Implementar Step Definitions

Agregar métodos en `SaucedemoStepDefinitions.java`:

```java
@Given("el usuario está en la página de login")
public void navigateToLogin() {
    actor.attemptsTo(LoginTask.navigateToSwagLabs());
}
```

### 3. Crear Page Objects si es necesario

```java
public class NewPage {
    public static final By ELEMENT = By.cssSelector("[data-test='element']");
}
```

### 4. Crear Tasks para acciones

```java
public static class NewTask implements Task {
    @Override
    public <T extends Actor> void performAs(T actor) {
        // Implementar acción
    }
}
```

## 🌐 Navegadores soportados

- **Chrome** (por defecto)
- **Firefox** (cambiar en serenity.conf)
- **Edge** (cambiar en serenity.conf)

## 🔍 Debugging

### Ver logs detallados

Modificar `logback.xml`:

```xml
<logger name="com.automationtest" level="DEBUG"/>
```

### Tomar screenshots en pasos específicos

Los screenshots se toman automáticamente. Están en `target/site/serenity/`.

### Pausar la ejecución para debugging

```java
Thread.sleep(5000); // Pausa de 5 segundos
```

## 🚀 Características principales

✅ Serenity BDD para reportes avanzados  
✅ Cucumber para escenarios BDD  
✅ Page Objects para mantenibilidad  
✅ Tasks para acciones reutilizables  
✅ Logs detallados con SLF4J  
✅ Screenshots automáticos  
✅ Reportes HTML profesionales  
✅ Soporte para múltiples navegadores  

## 📚 Recursos útiles

- [Documentación Serenity BDD](https://serenity-bdd.github.io/)
- [Documentación Cucumber](https://cucumber.io/docs/cucumber/)
- [Selenium WebDriver](https://www.selenium.dev/)

## 👥 Autor

Automatización de pruebas E2E para Saucedemo

## 📄 Licencia

MIT License

---

**¡Feliz Testing! 🎉**
