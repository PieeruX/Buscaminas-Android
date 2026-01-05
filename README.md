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
  <img src="https://github.com/user-attachments/assets/aca94cfc-6421-4b06-8eef-8812a7b4a69b" height="300" />
  <img src="https://github.com/user-attachments/assets/aad83f1f-71f2-4293-b490-897861f132b7" height="300" />
  <img src="https://github.com/user-attachments/assets/073f9442-81eb-4157-b414-b0ced1ec0cdf" height="300" />
  <img src="https://github.com/user-attachments/assets/9f813017-0bf0-46eb-8a8e-63f3296cae35" height="300" />
  <img src="https://github.com/user-attachments/assets/883c53be-db3d-4720-b19b-b88fd2006c9f" height="300" />
  <img src="https://github.com/user-attachments/assets/b8c81ff3-ff47-4550-a75d-156e302e6241" height="300" />
  <img src="https://github.com/user-attachments/assets/295ab74a-0fe1-464b-bd7a-719e46c90d03" height="300" />

</p>

## ✨ Características Principales

*   **🎮 Jugabilidad Clásica:** Algoritmo recursivo para abrir huecos vacíos automáticamente.
*   **🚩 Sistema de Banderas:**
    *   **Toque corto:** Destapa la casilla.
    *   **Toque largo:** Coloca/Quita una bandera para marcar posibles minas (protege la casilla de ser abierta por error).
*   **⚙️ Niveles de Dificultad Dinámicos:**
    *   **Fácil:** 10 Bombas.
    *   **Medio:** 15 Bombas.
    *   **Difícil:** 20 Bombas.
    *   *(El fondo del menú cambia dinámicamente según la dificultad seleccionada).*
*   **😀 Feedback Visual (Caritas):** Botón de reinicio interactivo que reacciona al toque (cara sorprendida 😲, cara feliz 🙂, cara de "muerto" 😵 y cara de victoria 😎).
*   **🎨 Diseño 3D Retro:** Casillas dibujadas manualmente con `Canvas` simulando el efecto de relieve/sombra clásico.
*   **🎨 Temas y Estética Dinámica:**
    *   **Fondos Aleatorios:** Cada vez que reinicias la partida, el juego selecciona automáticamente un fondo sorpresa entre varias temáticas icónicas (*Hora de Aventuras, Dragon Ball, Scooby Doo, Rick y Morty, Joker...*).
    *   **Paleta de Colores Adaptativa:** El diseño no es estático; los colores de las casillas (relleno, luces y sombras 3D) cambian programáticamente para combinar en armonía con la imagen de fondo activa.

*   **⏱️ Cronómetro y Contador:** Contador de minas restantes y tiempo de partida estilo reloj digital (fuente 7-segmentos).
*   **✨ Animaciones Lottie:**
    *   **Splash Screen:** Pantalla de carga animada al iniciar la app.
    *   **Efectos:** Lluvia de confeti y animacion winner al ganar la partida y efectos visuales de explosion y calavera al perder.
*   **📱 Diseño Responsivo:** Interfaz adaptada con `ConstraintLayout` para diferentes tamaños de pantalla.

## 🛠️ Tecnologías Usadas

*   **Lenguaje:** Java.
*   **IDE:** Android Studio.
*   **UI/UX:** XML Layouts, Custom Views (clase `Tablero` dibujada con `Canvas` y `Paint`).
*   **Librerías:**
    *   [Lottie for Android](https://github.com/airbnb/lottie-android): Para animaciones vectoriales de alta calidad (JSON/dotLottie).
    *   `GestureDetector`: Para el manejo avanzado de eventos táctiles (SingleTap vs LongPress).
    *   `Material Design`: Para componentes de UI modernos como el `MaterialButtonToggleGroup` en el menú.

## 🚀 Instalación y Uso

1.  Clona este repositorio:
2.  `git clone https://github.com/TU_USUARIO/Buscaminas-Android.git `
3.  Abre el proyecto en Android Studio.
4.  Deja que Gradle sincronice las dependencias.
5.  Ejecuta la app en un emulador o dispositivo físico (Android 7.0+ recomendado).

## 🕹️ Cómo Jugar

1.  **Inicio:**
    *   Al abrir la app, verás un Splash Screen animado.
    *   **Menú:** Selecciona la dificultad (**Fácil, Medio o Difícil**).
        *   *Nota: Verás cómo cambia la imagen de fondo (Agallas) y el tamaño del texto según la dificultad elegida.*
    *   Pulsa **"JUGAR"**.
2.  **Objetivo:** Destapar todas las casillas que NO tengan mina.
3.  **Controles:**
    *   **Toca** una casilla para abrirla.
    *   **Mantén pulsado** para poner una bandera si crees que hay una mina.
    *   Si tocas una mina, ¡BOOM! 💥 Fin del juego.
4.  **Reiniciar:** Pulsa la carita inferior para empezar una partida nueva al instante sin salir al menú.

## 📂 Estructura del Proyecto

*   `MainActivity.java`: Lógica principal del juego, gestión del tablero (`Canvas`), cronómetro y eventos táctiles (`GestureDetector`).
*   `MenuActivity.java`: Pantalla de selección de dificultad. Gestiona el cambio dinámico de fondos e imágenes y envía la configuración de bombas mediante `Intents`.
*   `SplashActivity.java`: Pantalla de carga inicial con animaciones.
*   `Tablero.java`: Clase personalizada que dibuja la cuadrícula y las casillas.
*   `Casilla.java`: Modelo de datos para cada celda (coordenadas, contenido, estado de bandera/destapado).

    
