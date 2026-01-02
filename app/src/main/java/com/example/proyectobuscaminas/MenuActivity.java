package com.example.proyectobuscaminas;

import android.content.Intent;
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
    private int numeroBombas;

    private MaterialButtonToggleGroup tgDificultad;
    private Button btnFacil, btnMedio, btnDificil;
    private ImageView imgFondo;



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

        btnJugar = findViewById(R.id.btnJugar);
        tgDificultad = findViewById(R.id.tgDificultad);
        btnFacil = findViewById(R.id.btnFacil);
        btnMedio = findViewById(R.id.btnMedio);
        btnDificil = findViewById(R.id.btnDificil);
        imgFondo = findViewById(R.id.imagenFondo);

        btnFacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFacil.setTextSize(20);
                btnMedio.setTextSize(15);
                btnDificil.setTextSize(15);
                imgFondo.setImageResource(R.drawable.agallaspordefecto);
            }
        });

        btnMedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMedio.setTextSize(20);
                imgFondo.setImageResource(R.drawable.agallasmedio);
                btnFacil.setTextSize(15);
                btnDificil.setTextSize(15);
            }
        });

        btnDificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDificil.setTextSize(20);
                imgFondo.setImageResource(R.drawable.agallasdificil);
                btnFacil.setTextSize(15);
                btnMedio.setTextSize(15);
            }
        });

        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idSeleccionado = tgDificultad.getCheckedButtonId();

                if (idSeleccionado == btnFacil.getId()){
                    numeroBombas = 10;
                } else if (idSeleccionado == btnMedio.getId()) {
                    numeroBombas = 15;

                }else{
                    numeroBombas = 20;
                }

                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("CANTIDAD_BOMBAS", numeroBombas);
                startActivity(intent);
            }
        });
    }
}