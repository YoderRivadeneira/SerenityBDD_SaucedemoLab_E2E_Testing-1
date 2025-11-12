package com.automationtest.tasks;

import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actor.Actor;

/**
 * Clase base para todas las Tasks
 */
public abstract class BaseTask implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Implementar en clases derivadas
    }
}
