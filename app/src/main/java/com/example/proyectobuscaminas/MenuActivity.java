package com.example.proyectobuscaminas;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButtonToggleGroup;

public class MenuActivity extends AppCompatActivity {
    private Button btnJugar;
    private boolean silencio = false, modoJuegoClasico = true;
    private int numeroBombas, sonidoClick, numFilas = 8;
    private boolean yendoAlJuego;

    private MaterialButtonToggleGroup tgDificultad, tgModoJuego;
    private Button btnFacil, btnMedio, btnDificil, btnClasico, btnDesafio;
    private ImageView imgFondo, ivSonido;
    private SoundPool soundPool;
    private MediaPlayer musica;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder().setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        sonidoClick = soundPool.load(this, R.raw.click, 1);
        btnJugar = findViewById(R.id.btnJugar);
        tgDificultad = findViewById(R.id.tgDificultad);
        btnFacil = findViewById(R.id.btnFacil);
        btnMedio = findViewById(R.id.btnMedio);
        btnDificil = findViewById(R.id.btnDificil);
        imgFondo = findViewById(R.id.imagenFondo);
        ivSonido = findViewById(R.id.ivSonido);
        tgModoJuego = findViewById(R.id.tgModoJuego);
        btnClasico = findViewById(R.id.btnClasico);
        btnDesafio = findViewById(R.id.btnDesafio);

        musica = MediaPlayer.create(this, R.raw.modofacil);
        musica.setVolume(0.5f, 0.5f);
        musica.start();
        musica.setLooping(true);

        ivSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musica.isPlaying()){
                    ivSonido.setImageResource(R.drawable.mute);
                    musica.pause();
                    silencio = true;

                }else{
                    ivSonido.setImageResource(R.drawable.sonido);
                    musica.start();
                    silencio = false;
                }

            }
        });

        btnClasico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(sonidoClick, 1, 1, 0, 0, 1);
                btnDesafio.setTextSize(15);
                btnClasico.setTextSize(17);
                modoJuegoClasico = true;

            }
        });

        btnDesafio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(sonidoClick, 1, 1, 0, 0, 1);
                btnClasico.setTextSize(15);
                btnDesafio.setTextSize(17);
                modoJuegoClasico = false;
            }
        });

        btnFacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(sonidoClick, 1, 1, 0, 0, 1);
                btnFacil.setTextSize(17);
                btnMedio.setTextSize(15);
                btnDificil.setTextSize(15);
                imgFondo.setImageResource(R.drawable.agallaspordefecto);
                numFilas = 8;

                if (!silencio) {
                    musica.stop();
                    musica.release();
                    musica = MediaPlayer.create(MenuActivity.this, R.raw.modofacil);
                    musica.setVolume(0.5f, 0.5f);
                    musica.start();
                    musica.setLooping(true);

                }

            }
        });

        btnMedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(sonidoClick, 1, 1, 0, 0, 1);

                btnMedio.setTextSize(17);
                imgFondo.setImageResource(R.drawable.agallasmedio);
                btnFacil.setTextSize(15);
                btnDificil.setTextSize(15);
                numFilas = 10;


                if (!silencio) {
                    musica.stop();
                    musica.release();
                    musica = MediaPlayer.create(MenuActivity.this, R.raw.modomedio);
                    musica.setVolume(0.5f, 0.5f);
                    musica.start();
                    musica.setLooping(true);

                }
            }
        });

        btnDificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(sonidoClick, 1, 1, 0, 0, 1);

                btnDificil.setTextSize(17);
                imgFondo.setImageResource(R.drawable.agallasdificil);
                btnFacil.setTextSize(15);
                btnMedio.setTextSize(15);
                numFilas = 12;


                if (!silencio) {
                    musica.stop();
                    musica.release();
                    musica = MediaPlayer.create(MenuActivity.this, R.raw.mododificil);
                    musica.setVolume(0.5f, 0.5f);
                    musica.start();
                    musica.setLooping(true);

                }
            }
        });

        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(sonidoClick, 1, 1, 0, 0, 1);

                int idSeleccionado = tgDificultad.getCheckedButtonId();

                if (idSeleccionado == btnFacil.getId()){
                    numeroBombas = 10;
                } else if (idSeleccionado == btnMedio.getId()) {
                    numeroBombas = 15;

                }else{
                    numeroBombas = 20;
                }

                yendoAlJuego = true;

                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("CANTIDAD_BOMBAS", numeroBombas);
                intent.putExtra("CLASICO", modoJuegoClasico);
                intent.putExtra("NUM_FILAS", numFilas);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }

        if (musica != null) {
            musica.stop();
            musica.release();
            musica = null;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Si minimizamos la app, la música se pausa.
        if (!yendoAlJuego) {
            if (musica != null && musica.isPlaying()) {
                musica.pause();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Al volver, reseteamos la variable
        yendoAlJuego = false;

        // Si la música existe, no suena y no está en silencio, lo volvemos a reproducir
        if (musica != null && !musica.isPlaying() && !silencio) {
            musica.start();
        }
    }


}