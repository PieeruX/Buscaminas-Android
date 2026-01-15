package com.example.proyectobuscaminas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;


public class MainActivity  extends AppCompatActivity implements View.OnTouchListener {
    private Tablero fondo;
    private int corX, corY, numBombas, numBanderitas, fondoRandom, contadorCasillas,
            numFondo, sonidoCasilla, sonidoBanderita, sonidoVictoria, sonidoPerder,
            sonidoGameOver, sonidoFallo, soniCarita, sonidoCuentaAtras, sonidoFinal, numCasillas, numFilas;
    private final int NUM_COLUMNAS = 8;

    private ImageView imagenFondo;
    private LinearLayout layout;
    private Chronometer cronometro;
    private boolean juegoActivo, clasico;
    private Casilla [][] casillas;
    private ImageButton btnReiniciar;
    private TextView txtBombas, txtCronometro, txtCasillas;
    private LottieAnimationView animacionConfeti, winnerAnimacion, perdedorAnimacion;
    private GestureDetector gestureDetector;
    private SoundPool soundPool;
    private CountDownTimer cuentaAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Datos del intent
        numBombas = getIntent().getIntExtra("CANTIDAD_BOMBAS", 10);
        clasico = getIntent().getBooleanExtra("CLASICO", true);
        numFilas = getIntent().getIntExtra("NUM_FILAS", 8);


        btnReiniciar = findViewById(R.id.btnReiniciar);
        imagenFondo = findViewById(R.id.imagenFondo);
        txtBombas = findViewById(R.id.txtBombas);
        txtCronometro = findViewById(R.id.txtCronometro);
        txtCasillas = findViewById(R.id.txtCasillas);
        animacionConfeti = findViewById(R.id.animacionConfeti);
        winnerAnimacion = findViewById(R.id.winner);
        perdedorAnimacion = findViewById(R.id.perdedor);
        cronometro = findViewById(R.id.cronometro);

        layout = findViewById(R.id.linearLayout);

        if (numFilas != NUM_COLUMNAS){
            //Calculamos el alto del layout
            int nuevaAltuta = numFilas * 40;
            float dpApixeles = getResources().getDisplayMetrics().density;
            int nuevaAltutaPx = (int) (nuevaAltuta * dpApixeles);

            //Se añade el nuevo valor de altura al layout
            ViewGroup.LayoutParams parametros = layout.getLayoutParams();
            parametros.height = nuevaAltutaPx;
            layout.setLayoutParams(parametros);
        }

        fondo = new Tablero(this);
        fondo.setOnTouchListener(this);
        layout.addView(fondo);

        efectoSonido();
        controlPulsacion();
        iniciarJuego();

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soniCarita, 1, 1, 0, 0, 1);
                iniciarJuego();
            }
        });

    }

    //Controlamos pulsaciones del usuario
    private void controlPulsacion() {
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            //pulsado normal
            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                if (juegoActivo){
                    corX =(int) event.getX();
                    corY = (int) event.getY();

                    for (int f = 0; f < numFilas; f++){
                        for (int c = 0; c < NUM_COLUMNAS; c++) {

                            if(casillas[f][c].dentro(corX, corY)){
                                if (casillas[f][c].getContenido() == 100){
                                    soundPool.play(sonidoFallo, 1, 1, 0, 0, 1);
                                    casillas[f][c].setDestapado(true);
                                    gameOver();

                                }else{
                                    soundPool.play(sonidoCasilla, 1, 1, 0, 0, 1);
                                    if (casillas[f][c].banderita == true){
                                        txtBombas.setText(" : " + (++numBanderitas));
                                    }
                                    recorrer(f, c);
                                    if (contadorCasillas == ((NUM_COLUMNAS*numFilas) - numBombas)){
                                        winner();

                                    }
                                }
                            }
                        }
                    }
                }

                //Provocar que vuelva a pintar (metodo onDraw)
                fondo.invalidate();
                return true;
            }

            //Pulsado largo
            @Override
            public void onLongPress(MotionEvent event) {

                if (juegoActivo){
                    corX =(int) event.getX();
                    corY = (int) event.getY();

                    for (int f = 0; f < numFilas; f++){
                        for (int c = 0; c < NUM_COLUMNAS; c++) {
                            if(casillas[f][c].dentro(corX, corY)){
                                if (!casillas[f][c].destapado){
                                    soundPool.play(sonidoBanderita, 1, 1, 0, 0, 1);
                                    casillas[f][c].banderita = !casillas[f][c].banderita;

                                    if (casillas[f][c].banderita == true){
                                        txtBombas.setText(" : " + (--numBanderitas));
                                    }else{
                                        txtBombas.setText(" : " + (++numBanderitas));
                                    }

                                    fondo.invalidate();
                                }

                            }
                        }
                    }
                }
            }
        });
    }

    //Agregamos efectos de sonido
    private void efectoSonido() {

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder().setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        sonidoCasilla = soundPool.load(this, R.raw.destaparcasilla, 1);
        sonidoBanderita = soundPool.load(this, R.raw.casilla, 1);
        sonidoPerder = soundPool.load(this, R.raw.perder, 1);
        sonidoVictoria = soundPool.load(this, R.raw.victoria, 1);
        sonidoGameOver = soundPool.load(this, R.raw.gameover, 1);
        sonidoFallo = soundPool.load(this, R.raw.failure, 1);
        soniCarita = soundPool.load(this, R.raw.sonidocarita, 1);
        sonidoCuentaAtras = soundPool.load(this, R.raw.cuentaatras, 1);
        sonidoFinal = soundPool.load(this, R.raw.cuentafinal, 1);
    }


    private void iniciarJuego() {
        reinicioJuego();
        long tiempoJuego = 120000; // 2min

        if (clasico){
            cronometro.setBase(SystemClock.elapsedRealtime());
            cronometro.start();
            txtCronometro.setVisibility(View.GONE);
            cronometro.setVisibility(View.VISIBLE);

        }else{
            //Modo desafio
            if(numBombas >= 20) {
                tiempoJuego = 300000; // 5 minutos
            } else if (numBombas >= 15) {
                tiempoJuego = 180000; // 3 minutos
            }
            txtCronometro.setVisibility(View.VISIBLE);
            cronometro.setVisibility(View.GONE);
            iniciarCuentaAtras(tiempoJuego);


        }

        fondoAleatorio();

        //Creación y referencia al Tablero
        casillas = new Casilla[numFilas][NUM_COLUMNAS];
        for (int f = 0; f < numFilas; f++){
            for (int c = 0; c < NUM_COLUMNAS; c++) {
                casillas[f][c] = new Casilla();
            }
        }

        ubicarBombas();
        ubicarNumeros();

        //Actualizar la vista del tablero (pintarlo de nuevo)
        if (fondo != null) {
            fondo.invalidate();
        }
    }

    private void reinicioJuego() {
        contadorCasillas = 0;
        juegoActivo = true;
        btnReiniciar.setImageResource(R.drawable.jugando);
        txtBombas.setText(" : " + numBombas);
        numCasillas = (NUM_COLUMNAS * numFilas) - numBombas;
        txtCasillas.setText(" : " + numCasillas);

        animacionConfeti.setVisibility(View.GONE);
        animacionConfeti.cancelAnimation();
        winnerAnimacion.setVisibility(View.GONE);
        winnerAnimacion.cancelAnimation();
        perdedorAnimacion.setVisibility(View.GONE);
        perdedorAnimacion.cancelAnimation();
        numBanderitas = numBombas;

    }

    private void fondoAleatorio() {
        do{
            fondoRandom = (int) (Math.random() * 11) +1;
        }while (fondoRandom == numFondo);

        numFondo = fondoRandom;

        if (numBombas == 20){
            imagenFondo.setImageResource(generarFondoDificil(numFondo));
        } else if (numBombas == 15) {
            imagenFondo.setImageResource(generarFondoMedio(numFondo));
        } else{
            imagenFondo.setImageResource(generarFondo(numFondo));
        }
    }


    private int generarFondoDificil(int numFondo) {
        int fondos [] = {
                R.drawable.horadeaventuras, R.drawable.goku2d, R.drawable.gokushenlongd,
                R.drawable.scoobydood, R.drawable.rickymortyr, R.drawable.jockerd,
                R.drawable.picoroygohand, R.drawable.naruto, R.drawable.kungfupandar,
                R.drawable.ededdyeddyr, R.drawable.pokemon};

        return fondos[numFondo -1];

    }

    private int generarFondoMedio(int numFondo) {
        int fondos [] = {
                R.drawable.horadeaventuras, R.drawable.goku2r, R.drawable.gokushenlongr,
                R.drawable.scoobydoor, R.drawable.rickymorty, R.drawable.jockerr,
                R.drawable.picoroygohanr, R.drawable.naruto, R.drawable.kungfupanda,
                R.drawable.ededdyeddyr, R.drawable.pokemon};

        return fondos[numFondo -1];

    }

    private int generarFondo(int numFondo) {
        int fondos [] = {
                R.drawable.horadeaventuras, R.drawable.goku2, R.drawable.gokushenlong,
                R.drawable.scoobydoo, R.drawable.rickymorty, R.drawable.jocker,
                R.drawable.picoroygohan, R.drawable.naruto, R.drawable.kungfupanda,
                R.drawable.ededdyeddy, R.drawable.pokemon};

        return fondos[numFondo -1];

    }

    private void iniciarCuentaAtras(long tiempoJuego) {
        if (cuentaAtras != null){
            cuentaAtras.cancel();

        }

        //Orden de cada cuantos segundo se ejecuta el metodo
        cuentaAtras = new CountDownTimer(tiempoJuego, 1000) {
            //Acción cuando se acaba el tiempo
            @Override
            public void onFinish() {
                txtCronometro.setText("00:00");
                if (juegoActivo){
                    gameOver();
                }
            }
            //Ejecucion del metodo cada 1seg, como determinammos arriba
            @Override
            public void onTick(long millisUntilFinished) {
                int segundos = (int) (millisUntilFinished / 1000);
                //obtenemos los minutos
                int minutos = segundos / 60;
                //modulo para obtener los segundos restantes
                segundos = segundos % 60;

                txtCronometro.setText(String.format("%02d:%02d", minutos, segundos));

                if (segundos < 11 && segundos > 1){
                    soundPool.play(sonidoCuentaAtras, 0.2f, 0.2f, 0, 0, 1);
                    txtCronometro.setTextColor(Color.RED);

                } else if (segundos < 1) {
                    soundPool.play(sonidoFinal, 0.2f, 0.2f, 0, 0, 1);
                } else{
                    txtCronometro.setTextColor(Color.BLACK);
                }

            }
        }.start();
    }

    private void gameOver() {
        juegoActivo = false;
        mostrarTodasLasBombas();

        if (clasico){
            cronometro.stop();
        }else{
            cuentaAtras.cancel();
        }

        soundPool.play(sonidoPerder, 1, 1, 0, 0, 1);
        btnReiniciar.setImageResource(R.drawable.gameovercara);

        perdedorAnimacion.setVisibility(View.VISIBLE);
        perdedorAnimacion.playAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                soundPool.play(sonidoGameOver, 1, 1, 0, 0, 1);
            }
        }, 1200);

        fondo.invalidate();
    }

    //Metodo que contine la logica y animación de victoria
    private void winner() {
        soundPool.play(sonidoVictoria, 1, 1, 0, 0, 1);
        mostrarTodasLasBombas();
        juegoActivo = false;
        txtBombas.setText(" : 0");

        if (clasico){
            cronometro.stop();
        }else{
            cuentaAtras.cancel();
        }

        btnReiniciar.setImageResource(R.drawable.ganador);

        animacionConfeti.setVisibility(View.VISIBLE);
        animacionConfeti.playAnimation();

        winnerAnimacion.setVisibility(View.VISIBLE);
        winnerAnimacion.setSpeed(0.8f);
        winnerAnimacion.playAnimation();
    }

    private void ubicarBombas() {
        int cantidad = numBombas;

        do {
            int fila = (int) (Math.random() * numFilas);
            int columna = (int) (Math.random() * NUM_COLUMNAS);

            if (casillas[fila][columna].getContenido() == 0){
                casillas[fila][columna].setContenido(100);
                cantidad--;
            }

        }while(cantidad != 0);

        //Escribir por consola el contenido

        for (int f = 0; f < numFilas; f++) {
            for (int c = 0; c < NUM_COLUMNAS; c++) {
                if (casillas[f][c].contenido == 100){
                    System.out.println("Fila " + f + " -- Columna " + c + ":" + casillas[f][c].getContenido());
                }
            }
        }
    }

    //Bucle anidado para poder contar cuantas bombas hay en el entrono 3x3 de cada casilla
    private void ubicarNumeros() {
        int contador;
        for (int f = 0; f < numFilas; f++){
            for (int c = 0; c < NUM_COLUMNAS; c++) {
                contador = 0;
                if (casillas[f][c].getContenido() != 100){
                    for (int fila = f - 1; fila <= f + 1; fila++) {
                        if (fila >= 0 && fila < numFilas){
                            for (int columna = c - 1; columna <= c + 1; columna++) {
                                if (columna >= 0 && columna < 8) {
                                    if (casillas[fila][columna].getContenido() == 100) {
                                        contador++;
                                    }
                                }
                            }

                        }

                    }
                    casillas[f][c].setContenido(contador);
                }

            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (juegoActivo){
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                btnReiniciar.setImageResource(R.drawable.sorpresa);

            }

            if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL){
                if (juegoActivo){
                    btnReiniciar.setImageResource(R.drawable.jugando);
                }
            }
        }

        gestureDetector.onTouchEvent(event);
        return true;

    }

    private void recorrer(int fil, int col) {
        if (fil >= 0 && fil < numFilas && col >= 0 && col < NUM_COLUMNAS){
            if (!casillas[fil][col].destapado){
                casillas[fil][col].setDestapado(true);
                contadorCasillas++;
                numCasillas--;
                txtCasillas.setText(" : " + numCasillas);
                if (casillas[fil][col].getContenido() == 0) {
                    recorrer(fil, col + 1);
                    recorrer(fil, col - 1);
                    recorrer(fil + 1, col);
                    recorrer(fil - 1, col);
                    recorrer(fil - 1, col - 1);
                    recorrer(fil - 1, col + 1);
                    recorrer(fil + 1, col + 1);
                    recorrer(fil + 1, col - 1);
                }

            }
        }
    }

    private void mostrarTodasLasBombas() {
        for (int f = 0; f < numFilas; f++) {
            for (int c = 0; c < NUM_COLUMNAS; c++) {
                if (casillas[f][c].getContenido() == 100) {
                    casillas[f][c].setDestapado(true);
                }
            }
        }
    }



    class Tablero extends View {

        Drawable imagenBomba, imagenBandera, imgPulsadoLargo;
        public Tablero(Context context) {
            super(context);
            imagenBomba = getResources().getDrawable(R.drawable.bomba);
            imagenBandera = getResources().getDrawable(R.drawable.bandera);
            imgPulsadoLargo = getResources().getDrawable(R.drawable.banderita);

        }
        protected void onDraw(Canvas canvas){
            canvas.drawColor(Color.parseColor("#256865"));

            Paint pFondo = new Paint();
            pFondo.setStyle(Paint.Style.FILL); // Relleno

            Paint pLinea = new Paint();
            pLinea.setStyle(Paint.Style.STROKE); // Borde
            pLinea.setColor(Color.LTGRAY);
            pLinea.setStrokeWidth(5);

            int ancho = getWidth();
            int alto = getHeight();

            int anchoCasilla = ancho/NUM_COLUMNAS;
            int altoCasilla = alto/numFilas;

            //Dibujar cada cuadrado y guardar sus coordenadas
            for (int f = 0; f < numFilas; f++){
                for (int c = 0; c < NUM_COLUMNAS; c++) {
                    int y = f * altoCasilla;
                    int x = c * anchoCasilla;

                    //Guardamos coordenada en array
                    casillas[f][c].fijarxy(x, y, anchoCasilla);

                    //Dibujamos el cuadrado en pantalla
                    if(!casillas[f][c].destapado){
                        int borde = 5;

                        String sombra = generarSombra(numFondo),
                                luz = "#FFFFFF",
                                relleno = generarRelleno(numFondo);

                        if (numFondo == 5){
                            luz = "#c9e260";
                        }

                        //Capa de sombra
                        pFondo.setColor(Color.parseColor(sombra));
                        canvas.drawRect(x, y, x + anchoCasilla, y + altoCasilla, pFondo);

                        //Capa luz
                        pFondo.setColor(Color.parseColor(luz));
                        canvas.drawRect(x, y, x + anchoCasilla - borde, y + altoCasilla - borde, pFondo);

                        //Capa color centro
                        pFondo.setColor(Color.parseColor(relleno));
                        canvas.drawRect(x + borde, y + borde, x + anchoCasilla - borde, y + altoCasilla - borde, pFondo);

                        //pintar banderita si hubo pulsación larga
                        if (casillas[f][c].banderita){
                            int margen = 20;
                            imgPulsadoLargo.setBounds(x+ margen, y+ margen, x + anchoCasilla -margen, y + altoCasilla -margen);
                            imgPulsadoLargo.draw(canvas);
                        }

                    }else{
                        //pintar casilla abierta
                        pFondo.setColor(Color.GRAY);
                        canvas.drawRect(x, y, x + anchoCasilla, y + anchoCasilla, pFondo);

                        // Borde
                        canvas.drawRect(x, y, x + anchoCasilla, y + anchoCasilla, pLinea);

                        int valorCasilla = casillas[f][c].getContenido();

                        if (valorCasilla == 100){
                            int margen = 20;

                            //Al ganar se pintan las bombas como banderas
                            if (contadorCasillas == ((NUM_COLUMNAS * numFilas) - numBombas)){
                                imagenBandera.setBounds(x+ margen, y+margen, x + anchoCasilla -margen, y + altoCasilla-margen);
                                imagenBandera.draw(canvas);

                            }else{
                                //al perder se pintan las bombas
                                imagenBomba.setBounds(x+margen, y+margen, x + anchoCasilla -margen, y + altoCasilla-margen);
                                imagenBomba.draw(canvas);
                            }

                        }else if (valorCasilla > 0){
                            Paint pNumero = new Paint();

                            switch (valorCasilla){
                                case 1 -> pNumero.setColor(Color.BLUE);
                                case 2 -> pNumero.setColor(Color.parseColor(getString(R.string.verde_oscuro)));
                                case 3 -> pNumero.setColor(Color.RED);
                                default -> pNumero.setColor(Color.parseColor(getString(R.string.azul_oscuro)));
                            }

                            pNumero.setTextSize(anchoCasilla/2);
                            pNumero.setTypeface(Typeface.DEFAULT_BOLD);
                            pNumero.setTextAlign(Paint.Align.CENTER);

                            canvas.drawText(String.valueOf(valorCasilla), x + anchoCasilla/2, y + altoCasilla/2 + 20, pNumero);



                        }
                    }

                }
            }

        }

    }

    private String generarRelleno(int numFondo) {
        //hora de aventuras, goku, goku shenlong, scoobydoo, rick y morty, joker,
        // picoro y gohan, naruto, Kungfu Panda, ed edd y eddy, pokemon
        String rellenos [] = {"#fbbd18", "#e4c584", "#e76a24", "#a7772b", "#40b5cb", "#9f5a4a",
                "#674465", "#e17439", "#fde9ae", "#a78a6c", "#ffd723"};
         return rellenos[numFondo -1];
    }

    private String generarSombra(int numFondo) {
        //hora de aventuras, goku, goku shenlong, scoobydoo, rick y morty, joker,
        // picoro y gohan, naruto, Kungfu Panda, ed edd y eddy, pokemon

        String sombras [] = {"#977f3e", "#5f5744", "#855a43", "#59401a", "#2d4f3e",
                "#421f1b", "#37243a", "#934433", "#898272", "#5c5044", "#8f6e30"};

        return sombras[numFondo -1];

    }

    //Liberar recursos
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }

        if (cuentaAtras != null) {
            cuentaAtras.cancel();
        }
    }



}