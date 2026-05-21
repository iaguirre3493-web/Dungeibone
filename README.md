# Dungeibone

Dungeibone es un videojuego 2D desarrollado con Java y libGDX para la asignatura PMDM.

El juego consiste en superar dos niveles evitando enemigos, recogiendo objetos y llegando a la bandera de salida.

## Objetivo del juego

El jugador debe moverse por el escenario, evitar a los enemigos y recoger el objeto de cada nivel.

- Nivel 1: recoger la moneda y llegar a la bandera.
- Nivel 2: recoger el tesoro final y llegar a la bandera para ganar.

Si la vida llega a 0, aparece la pantalla de Game Over.

## Controles

- W / Flecha arriba: mover hacia arriba
- A / Flecha izquierda: mover hacia la izquierda
- S / Flecha abajo: mover hacia abajo
- D / Flecha derecha: mover hacia la derecha
- ENTER: iniciar partida o volver al menú
- ESC: volver al menú o salir
- I: abrir instrucciones
- O: abrir opciones
- M: activar o desactivar sonido
- D: cambiar dificultad

## Menús

El juego incluye:

- Menú principal
- Pantalla de instrucciones
- Pantalla de opciones
- Pantalla de Game Over
- Pantalla de victoria

## Opciones configurables

Desde el menú de opciones se puede configurar:

- Sonido activado o desactivado
- Dificultad: FACIL, NORMAL o DIFICIL

La dificultad afecta a la velocidad de los enemigos y al daño recibido por el jugador.

## HUD

Durante la partida se muestra información en pantalla:

- Vida
- Puntos
- Nivel actual
- Si el objeto del nivel ha sido recogido o no

## Enemigos

El juego incluye tres NPCs/enemigos diferentes:

1. Enemigo estático  
   Permanece quieto en el escenario.

2. Enemigo patrullero  
   Se mueve horizontalmente entre dos puntos.

3. Enemigo perseguidor  
   Persigue al jugador durante la partida.

Cada enemigo tiene comportamiento propio y causa daño al jugador al tocarlo.

## Sonido

El juego incluye efectos de sonido:

- Sonido al recoger moneda o tesoro
- Sonido al recibir daño

El sonido puede activarse o desactivarse desde el menú de opciones.

## Animaciones

El juego incluye animaciones básicas:

- Animación del jugador al moverse
- Animación de enemigos según su dirección
- Animación de la moneda
- Animación del tesoro
- Animación de la bandera de salida

## Programación Orientada a Objetos

El proyecto está organizado usando clases para separar responsabilidades.

Clases principales:

- `DungeiboneGame`: clase principal del juego.
- `FirstScreen`: menú principal.
- `GameScreen`: pantalla principal de juego.
- `InstructionsScreen`: pantalla de instrucciones.
- `OptionsScreen`: pantalla de opciones.
- `VictoryScreen`: pantalla de victoria.
- `GameOverScreen`: pantalla de derrota.
- `Entity`: clase base para entidades del juego.
- `Player`: jugador principal.
- `Enemy`: clase base para enemigos.
- `StaticEnemy`: enemigo estático.
- `EnemyPatrol`: enemigo con patrulla horizontal.
- `EnemyChaser`: enemigo que persigue al jugador.

## Assets utilizados

Para los gráficos y sonidos se ha utilizado el pack:

**Ninja Adventure - Asset Pack**

Se han utilizado sprites para:

- Jugador
- Enemigos
- Moneda
- Tesoro
- Bandera
- Efectos de sonido

## Cómo ejecutar el juego

El proyecto se ejecuta desde IntelliJ IDEA usando la configuración de escritorio de libGDX.

También puede ejecutarse desde terminal con Gradle:

```bash
./gradlew lwjgl3:run
