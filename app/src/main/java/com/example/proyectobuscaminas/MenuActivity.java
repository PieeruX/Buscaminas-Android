package com.example.proyectobuscaminas;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
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
    private boolean silencio = false;
    private int numeroBombas, sonidoClick;
    private boolean yendoAlJuego;

    private MaterialButtonToggleGroup tgDificultad;
    private Button btnFacil, btnMedio, btnDificil;
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

        musica = MediaPlayer.create(this, R.raw.modofacil);
        musica.setVolume(0.2f, 0.2f);
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

        btnFacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(sonidoClick, 1, 1, 0, 0, 1);
                btnFacil.setTextSize(20);
                btnMedio.setTextSize(15);
                btnDificil.setTextSize(15);
                imgFondo.setImageResource(R.drawable.agallaspordefecto);

                if (!silencio) {
                    musica.stop();
                    musica.release();
                    musica = MediaPlayer.create(MenuActivity.this, R.raw.modofacil);
                    musica.setVolume(0.2f, 0.2f);
                    musica.start();
                    musica.setLooping(true);

                }

            }
        });

        btnMedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(sonidoClick, 1, 1, 0, 0, 1);

                btnMedio.setTextSize(20);
                imgFondo.setImageResource(R.drawable.agallasmedio);
                btnFacil.setTextSize(15);
                btnDificil.setTextSize(15);

                if (!silencio) {
                    musica.stop();
                    musica.release();
                    musica = MediaPlayer.create(MenuActivity.this, R.raw.modomedio);
                    musica.setVolume(0.2f, 0.2f);
                    musica.start();
                    musica.setLooping(true);

                }
            }
        });

        btnDificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(sonidoClick, 1, 1, 0, 0, 1);

                btnDificil.setTextSize(20);
                imgFondo.setImageResource(R.drawable.agallasdificil);
                btnFacil.setTextSize(15);
                btnMedio.setTextSize(15);


                if (!silencio) {
                    musica.stop();
                    musica.release();
                    musica = MediaPlayer.create(MenuActivity.this, R.raw.mododificil);
                    musica.setVolume(0.2f, 0.2f);
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