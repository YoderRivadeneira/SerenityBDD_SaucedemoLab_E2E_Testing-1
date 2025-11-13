#!/bin/bash

# Script para ejecutar las pruebas de Serenity BDD con Saucedemo

echo "========================================"
echo "Ejecutando pruebas de Serenity BDD"
echo "========================================"
echo ""

# Limpiar y compilar
echo "1. Limpiando y compilando el proyecto..."
mvn clean compile -DskipTests

# Ejecutar las pruebas
echo ""
echo "2. Ejecutando las pruebas..."
mvn test

# Generar reportes de Serenity
echo ""
echo "3. Generando reportes de Serenity BDD..."
mvn serenity:aggregate

# Mostrar resultado
echo ""
echo "========================================"
echo "Pruebas completadas"
echo "========================================"
echo ""
echo "Reportes disponibles en:"
echo "- target/site/serenity/index.html (Serenity Report)"
echo "- target/cucumber-reports/index.html (Cucumber Report)"
echo ""
