package com.example.wayuumath;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity_Nivel1 extends AppCompatActivity {

    private TextView tv_nombre, tv_score;
    private ImageView iv_Auno, iv_Ados, iv_vidas;
    private EditText et_respuesta;
    private MediaPlayer mp, mp_great, mp_bad;
    int score, numAleatorio_uno, numAleatorio_dos, resultado, vidas=3;
    String nombre_jugador, string_score, string_vidas;

    String numero[]={"cero","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_nivel1);


        Toast.makeText(this, "Nivel 1 - Sumas basicas",Toast.LENGTH_SHORT).show();
        tv_nombre=(TextView)findViewById(R.id.textView_nombre);
        tv_score=(TextView)findViewById(R.id.textView_score);
        iv_vidas=(ImageView)findViewById(R.id.imageView_vidas);
        iv_Auno=(ImageView)findViewById(R.id.imageView_NumUno);
        iv_Ados=(ImageView)findViewById(R.id.imageView_NumDos);
        et_respuesta=(EditText)findViewById(R.id.editText_resultado);

        nombre_jugador = getIntent().getStringExtra("jugador");
        tv_nombre.setText("Jugador: " + nombre_jugador);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mp = MediaPlayer.create(this, R.raw.goats);
        mp.start();
        mp.setLooping(true);

        mp_great=MediaPlayer.create(this, R.raw.wonderful);
        mp_bad=MediaPlayer.create(this, R.raw.bad);
    }
}