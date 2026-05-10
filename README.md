# Dungeibone

Dungeibone es un videojuego 2D desarrollado en Java con libGDX para la asignatura de Programación Multimedia y Dispositivos Móviles.

## Objetivo del juego

El jugador debe avanzar por diferentes mazmorras, recoger todos los objetos necesarios de cada nivel y llegar a la bandera para pasar al siguiente nivel.

Al completar el nivel 4, se muestra la pantalla de victoria.

## Controles

- Flechas o WASD: mover al personaje.
- P: abrir o cerrar el menú de pausa.
- Arriba / Abajo: moverse por el menú de pausa.
- Enter: seleccionar una opción del menú de pausa.

## Características principales

- Juego 2D desarrollado con libGDX.
- Personaje principal controlable.
- 4 niveles diferentes.
- Objetos coleccionables por nivel.
- HUD con vida, puntos, nivel actual y objetos recogidos.
- Menú principal.
- Pantalla de instrucciones.
- Pantalla de opciones.
- Pantalla de victoria.
- Pantalla de game over.
- Menú de pausa durante la partida.
- Música de fondo y efectos de sonido.
- Dificultad configurable: fácil, normal y difícil.
- Sonido configurable.
- NPCs/enemigos diferentes:
  - StaticEnemy.
  - EnemyPatrol.
  - EnemyChaser.
- IA simple en EnemyChaser, que persigue al jugador.

## Requisitos cumplidos

### Requisitos obligatorios

- Personaje principal, inicio, final y objetivo claro.
- Al menos dos niveles claramente diferenciados.
- Información en pantalla: puntuación, vida y nivel actual.
- Menús para iniciar, configurar, terminar e instrucciones.
- Dos opciones configurables: sonido y dificultad.
- Al menos 3 NPCs diferentes.
- Sonido y animaciones en personajes con movimiento.

### Funcionalidades opcionales implementadas

- Repositorio en GitHub con issues, release y documentación.
- IA simple en un NPC mediante EnemyChaser.
- Menú de pausa durante la partida.
- Dos niveles adicionales, pasando de 2 a 4 niveles.

## Tecnologías utilizadas

- Java.
- libGDX.
- Gradle.
- IntelliJ IDEA.
- Git y GitHub.

## Estructura del proyecto

- `core`: módulo principal con la lógica del videojuego.
- `lwjgl3`: módulo de escritorio para ejecutar el juego en PC.
- `assets`: carpeta con imágenes, fuentes y sonidos del juego.

## Ejecución del proyecto

El proyecto se puede ejecutar desde IntelliJ IDEA usando la configuración de escritorio/lwjgl3.

También se puede ejecutar desde terminal con Gradle:

```bash
./gradlew lwjgl3:run
