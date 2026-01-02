package com.example.proyectobuscaminas;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;


public class MainActivity  extends AppCompatActivity implements View.OnTouchListener {
    private Tablero fondo;
    private int corX, corY, numBombas, numBanderitas, fondoRandom, contadorCasillas, numFondo = 0;
    private ImageView imagenFondo;
    private LinearLayout layout;
    private boolean juegoActivo;
    private Casilla [][] casillas;
    private ImageButton btnReiniciar;
    private TextView txtBombas;
    private Chronometer cronometro;
    private LottieAnimationView animacionConfeti, winnerAnimacion, perdedorAnimacion;
    private GestureDetector gestureDetector;

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

        numBombas = getIntent().getIntExtra("CANTIDAD_BOMBAS", 10);

        btnReiniciar = findViewById(R.id.btnReiniciar);
        imagenFondo = findViewById(R.id.imagenFondo);
        txtBombas = findViewById(R.id.txtBombas);
        cronometro = findViewById(R.id.cronometro);
        animacionConfeti = findViewById(R.id.animacionConfeti);
        winnerAnimacion = findViewById(R.id.winner);
        perdedorAnimacion = findViewById(R.id.perdedor);

        layout = findViewById(R.id.linearLayout);
        fondo = new Tablero(this);
        fondo.setOnTouchListener(this);
        layout.addView(fondo);

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                if (juegoActivo){
                    corX =(int) event.getX();
                    corY = (int) event.getY();

                    for (int f = 0; f < 8; f++){
                        for (int c = 0; c < 8; c++) {

                            if(casillas[f][c].dentro(corX, corY)){
                                if (casillas[f][c].getContenido() == 100){
                                    casillas[f][c].setDestapado(true);
                                    mostrarTodasLasBombas();
                                    juegoActivo = false;
                                    cronometro.stop();

                                    btnReiniciar.setImageResource(R.drawable.gameovercara);
                                    perdedorAnimacion.setVisibility(View.VISIBLE);
                                    perdedorAnimacion.playAnimation();

                                    //toastGanador(R.layout.game_over);

                                }else{
                                    if (casillas[f][c].banderita == true){
                                        txtBombas.setText(" : " + (++numBanderitas));
                                    }
                                    recorrer(f, c);
                                    if (contadorCasillas == (64 - numBombas)){
                                        mostrarTodasLasBombas();
                                        juegoActivo = false;
                                        cronometro.stop();
                                        txtBombas.setText(" : 0");

                                        btnReiniciar.setImageResource(R.drawable.ganador);

                                        animacionConfeti.setVisibility(View.VISIBLE);
                                        animacionConfeti.playAnimation();

                                        winnerAnimacion.setVisibility(View.VISIBLE);
                                        winnerAnimacion.playAnimation();

                                        //toastGanador(R.layout.winner);

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

            //Click largo
            @Override
            public void onLongPress(MotionEvent event) {

                if (juegoActivo){
                    corX =(int) event.getX();
                    corY = (int) event.getY();

                    for (int f = 0; f < 8; f++){
                        for (int c = 0; c < 8; c++) {
                            if(casillas[f][c].dentro(corX, corY)){
                                if (!casillas[f][c].destapado){
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

        iniciarJuego();

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               iniciarJuego();
            }
        });



    }

    private void iniciarJuego() {
        contadorCasillas = 0;
        juegoActivo = true;
        btnReiniciar.setImageResource(R.drawable.jugando);
        txtBombas.setText(" : " + numBombas);
        cronometro.setBase(SystemClock.elapsedRealtime());
        cronometro.start();

        animacionConfeti.setVisibility(View.GONE);
        animacionConfeti.cancelAnimation();
        winnerAnimacion.setVisibility(View.GONE);
        winnerAnimacion.cancelAnimation();
        perdedorAnimacion.setVisibility(View.GONE);
        perdedorAnimacion.cancelAnimation();

        numBanderitas = numBombas;

        do{
            fondoRandom = (int) (Math.random() * 6) +1;
        }while (fondoRandom == numFondo);

        numFondo = fondoRandom;
        switch (numFondo){
            case 1 -> imagenFondo.setImageResource(R.drawable.horadeaventuras);
            case 2 -> imagenFondo.setImageResource(R.drawable.goku2);
            case 3 -> imagenFondo.setImageResource(R.drawable.gokushenlong);
            case 4 -> imagenFondo.setImageResource(R.drawable.scoobydoo);
            case 5 -> imagenFondo.setImageResource(R.drawable.rickymorty);
            case 6 -> imagenFondo.setImageResource(R.drawable.jocker);
        }

        //Creación y referencia al Tablero
        casillas = new Casilla[8][8];
        for (int f = 0; f < 8; f++){
            for (int c = 0; c < 8; c++) {
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

    private void ubicarBombas() {
        int cantidad = numBombas;

        do {
            int fila = (int) (Math.random() * 8);
            int columna = (int) (Math.random() * 8);

            if (casillas[fila][columna].getContenido() == 0){
                casillas[fila][columna].setContenido(100);
                cantidad--;
            }

        }while(cantidad != 0);

        //Escribir por consola el contenido

        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                if (casillas[f][c].contenido == 100){
                    System.out.println("Fila " + f + " -- Columna " + c + ":" + casillas[f][c].getContenido());
                }
            }
        }
    }


    private void ubicarNumeros() {
        int contador;
        for (int f = 0; f < 8; f++){
            for (int c = 0; c < 8; c++) {
                contador = 0;
                if (casillas[f][c].getContenido() != 100){
                    for (int fila = f - 1; fila <= f + 1; fila++) {
                        if (fila >= 0 && fila < 8){
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

    private void toastGanador(int game_layout) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(game_layout, null);
        Toast notificacionImagen = new Toast(this);
        notificacionImagen.setDuration(Toast.LENGTH_SHORT);
        notificacionImagen.setView(layout);
        notificacionImagen.show();
    }


    private void recorrer(int fil, int col) {
        if (fil >= 0 && fil < 8 && col >= 0 && col < 8){
            if (!casillas[fil][col].destapado){
                casillas[fil][col].setDestapado(true);
                contadorCasillas++;
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
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
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

            int anchoCasilla = ancho/8;
            int altoCasilla = alto/8;

            //Dibujar cada cuadrado y guardar sus coordenadas
            for (int f = 0; f < 8; f++){
                for (int c = 0; c < 8; c++) {
                    int y = f * altoCasilla;
                    int x = c * anchoCasilla;

                    //Guardamos coordenada en array
                    casillas[f][c].fijarxy(x, y, anchoCasilla);

                    //Dibujamos el cuadrado en pantalla
                    if(!casillas[f][c].destapado){
                        int borde = 5;

                        String sombra = "", luz = "#FFFFFF", relleno="";

                        switch (numFondo){
                            case 1 -> {
                                //hora de aventuras
                                relleno= "#fbbd18";
                                sombra = "#977f3e";
                            }
                            case 2 -> {
                                // goku
                                relleno = "#e4c584";
                                sombra = "#5f5744";
                            }
                            case 3 -> {
                                //goku shenlong
                                relleno = "#e76a24";
                                sombra = "#855a43";

                            }case 4 -> {
                                //scooby doo
                                relleno="#a7772b";
                                sombra = "#59401a";
                            }
                            case 5 -> {
                                //rick y morty
                                sombra = "#2d4f3e";
                                luz = "#c9e260";
                                relleno = "#40b5cb";
                            }case 6 -> {
                                //jocker
                                relleno = "#9f5a4a";
                                sombra ="#421f1b";
                            }
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

                        if (casillas[f][c].banderita){
                            int margen = 20;
                            imgPulsadoLargo.setBounds(x+ margen, y+ margen, x + anchoCasilla -margen, y + altoCasilla -margen);
                            imgPulsadoLargo.draw(canvas);
                        }

                    }else{
                        pFondo.setColor(Color.GRAY);
                        canvas.drawRect(x, y, x + anchoCasilla, y + anchoCasilla, pFondo);

                        // Borde
                        canvas.drawRect(x, y, x + anchoCasilla, y + anchoCasilla, pLinea);

                        int valorCasilla = casillas[f][c].getContenido();

                        if (valorCasilla == 100){
                            int margen = 20;

                            if (contadorCasillas == (64 - numBombas)){
                                imagenBandera.setBounds(x+ margen, y+margen, x + anchoCasilla -margen, y + altoCasilla-margen);
                                imagenBandera.draw(canvas);

                            }else{
                                imagenBomba.setBounds(x+margen, y+margen, x + anchoCasilla -margen, y + altoCasilla-margen);
                                imagenBomba.draw(canvas);
                            }

                        }else if (valorCasilla > 0){
                            Paint pNumero = new Paint();

                            switch (valorCasilla){
                                case 1 -> pNumero.setColor(Color.BLUE);
                                case 2 -> pNumero.setColor(Color.parseColor("#008000")); // Verde oscuro
                                case 3 -> pNumero.setColor(Color.RED);
                                default -> pNumero.setColor(Color.parseColor("#000080")); // Azul oscuro
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



}