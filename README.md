# 💣 Buscaminas Android (Minesweeper Clone)

**Proyecto académico desarrollado en 2º DAM para la asignatura de Programación Móvil.**

Un clon moderno y completo del clásico juego **Buscaminas (Minesweeper)** desarrollado en Android nativo con **Java**. Este proyecto recrea la experiencia clásica de Windows 95 pero con una interfaz renovada, animaciones fluidas y características modernas.

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green?logo=android" alt="Platform Android" />
  <img src="https://img.shields.io/badge/Language-Java-orange?logo=java" alt="Language Java" />
  <img src="https://img.shields.io/badge/Animation-Lottie-blue" alt="Lottie Animation" />
  <img src="https://img.shields.io/badge/IDE-Android%20Studio-purple?logo=android-studio" alt="Android Studio" />
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/72bb8d1e-48bd-4bd6-923e-8fef159a5de7" height="300" />
  <img src="https://github.com/user-attachments/assets/6e445489-aab5-4a19-937b-5789fd23fcc6" height="300" />
  <img src="https://github.com/user-attachments/assets/df1116bd-7430-4855-ade9-caeb972d701b" height="300" />
  <img src="https://github.com/user-attachments/assets/9f813017-0bf0-46eb-8a8e-63f3296cae35" height="300" />
  <img src="https://github.com/user-attachments/assets/883c53be-db3d-4720-b19b-b88fd2006c9f" height="300" />
  <img src="https://github.com/user-attachments/assets/b8c81ff3-ff47-4550-a75d-156e302e6241" height="300" />
  <img src="https://github.com/user-attachments/assets/295ab74a-0fe1-464b-bd7a-719e46c90d03" height="300" />

</p>

## ✨ Características Principales

*   **🎮 Modos de Juego Versátiles:**
    *   **Modo Clásico:** Juega relajado con un cronómetro que cuenta el tiempo que tardas.
    *   **Modo Desafío:** ¡Corre contra el reloj! Tienes un tiempo límite (cuenta atrás) para resolver el tablero antes de que explote.
*   **🚩 Sistema de Banderas:**
    *   **Toque corto:** Destapa la casilla.
    *   **Toque largo:** Coloca/Quita una bandera para marcar posibles minas (protege la casilla de ser abierta por error).
*   **⚙️ Niveles de Dificultad Dinámicos:**
    *   **Fácil:** 10 Bombas.
    *   **Medio:** 15 Bombas.
    *   **Difícil:** 20 Bombas.
    *   *(El fondo del menú y la música cambian dinámicamente según la dificultad seleccionada).*
*   **🎵 Experiencia Sonora Inmersiva:**
    *   **Música Adaptativa:** Cada dificultad tiene su propia banda sonora que acompaña al jugador desde el menú hasta el juego.
    *   **Efectos de Sonido:** Feedback auditivo al hacer clic, poner banderas, ganar o perder.
    *   **Control de Audio:** Botón de mute/unmute.
*   **😀 Feedback Visual (Caritas):** Botón de reinicio interactivo (sorpresa 😲, feliz 🙂, muerto 😵, ganador 😎).
*   **🎨 Diseño 3D Retro & Temas Dinámicos:**
    *   **Fondos Aleatorios:** Cada reinicio muestra un fondo sorpresa icónico (*Hora de Aventuras, Dragon Ball, Scooby Doo...*).
    *   **Paleta Adaptativa:** Las casillas cambian de color para combinar con el fondo.
*   **✨ Animaciones Lottie:** Splash Screen, victoria (Winner) y derrota (Game Over) animadas.


## 🛠️ Tecnologías Usadas

*   **Lenguaje:** Java (Nativo Android).
*   **IDE:** Android Studio.
*   **Interfaz de Usuario (UI):**
    *   **Custom Views:** Tablero de juego dibujado manualmente usando la clase `Canvas` y `Paint`.
    *   **Layouts:** XML con `ConstraintLayout` para diseño responsivo.
    *   **Material Design:** Componentes modernos para los selectores del menú (`MaterialButtonToggleGroup`).
*   **Multimedia (Audio):**
    *   **`SoundPool`:** Implementado para la gestión eficiente de efectos de sonido simultáneos y de baja latencia (explosiones, clicks, victoria, cuenta atrás...).
    *   **`MediaPlayer`:** Para la reproducción de música de fondo continua entre pantallas.
*   **Animaciones:**
    *   [Lottie for Android](https://github.com/airbnb/lottie-android): Renderizado de animaciones vectoriales complejas (JSON) para feedback visual (Winner, Game Over, Splash Screen).
*   **Lógica y Control:**
    *   **`GestureDetector`:** Detección precisa de gestos táctiles para diferenciar entre "Click corto" (destapar casilla) y "Click largo" (colocar bandera).
    *   **Gestión de Tiempo:** Implementación híbrida usando `Chronometer` (para el modo Clásico) y `CountDownTimer` (para el modo Desafío).
    *   **Intents:** Paso de datos y persistencia de estado (dificultad, modo juego) entre actividades.

## 🚀 Instalación y Uso

1.  Clona este repositorio:
2.  `git clone https://github.com/TU_USUARIO/Buscaminas-Android.git `
3.  Abre el proyecto en Android Studio.
4.  Deja que Gradle sincronice las dependencias.
5.  Ejecuta la app en un emulador o dispositivo físico (Android 7.0+ recomendado).

## 🕹️ Cómo Jugar

1.  **Inicio:**
    *   Al abrir la app, verás un Splash Screen animado.
    *   **Menú de Configuración:**
        *   Selecciona la **Dificultad** (Fácil, Medio o Difícil). *Nota: Cambia el fondo y la música.*
        *   Selecciona el **Modo de Juego**: **Clásico** (sin límite de tiempo) o **Desafío** (cuenta atrás).
    *   Pulsa **"JUGAR"**.
2.  **Objetivo:** Destapar todas las casillas que NO tengan mina antes de que se acabe el tiempo (en modo Desafío).
3.  **Controles:**
    *   **Toca** una casilla para abrirla.
    *   **Mantén pulsado** para poner una bandera.
    *   Si tocas una mina, ¡BOOM! 💥 Fin del juego.
4.  **Reiniciar:** Pulsa la carita inferior para empezar una partida nueva al instante.


## 📂 Estructura del Proyecto

*   `MainActivity.java`: Lógica principal del juego, gestión del tablero (`Canvas`), cronómetro y eventos táctiles (`GestureDetector`).
*   `MenuActivity.java`: Pantalla de selección de dificultad. Gestiona el cambio dinámico de fondos e imágenes y envía la configuración de bombas mediante `Intents`.
*   `SplashActivity.java`: Pantalla de carga inicial con animaciones.
*   `Tablero.java`: Clase personalizada que dibuja la cuadrícula y las casillas.
*   `Casilla.java`: Modelo de datos para cada celda (coordenadas, contenido, estado de bandera/destapado).

    
