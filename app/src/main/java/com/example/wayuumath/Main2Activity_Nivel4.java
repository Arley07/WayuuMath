package com.example.wayuumath;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class Main2Activity_Nivel4 extends AppCompatActivity {


    private TextView tv_nombre, tv_score;
    private ImageView iv_Auno, iv_Ados, iv_vidas, iv_signo, iv_numwa1, iv_numwa2;
    private EditText et_respuesta;
    private MediaPlayer mp, mp_great, mp_bad;


    int score, numAleatorio_uno, numAleatorio_dos, resultado, vidas = 3;
    String nombre_jugador, string_score, string_vidas;

    String numero[] = {"cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"};
    String number[]= {"zero","one", "two","three","ford","five","six","seven","eight","nine"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_nivel4);


        Toast.makeText(this, "lüli 4 - tsama waa jawaru", Toast.LENGTH_SHORT).show();
        tv_nombre = (TextView) findViewById(R.id.textView_nombre);
        tv_score = (TextView) findViewById(R.id.textView_score);
        iv_vidas = (ImageView) findViewById(R.id.imageView_vidas);
        iv_Auno = (ImageView) findViewById(R.id.imageView_NumUno);
        iv_Ados = (ImageView) findViewById(R.id.imageView_NumDos);
        iv_numwa1 = (ImageView) findViewById(R.id.imageView_wayuu1);
        iv_numwa2 = (ImageView) findViewById(R.id.imageView_wayuu2);
        iv_signo=(ImageView) findViewById(R.id.imageView_signo);
        et_respuesta = (EditText) findViewById(R.id.editText_resultado);

        nombre_jugador = getIntent().getStringExtra("jugador");
        tv_nombre.setText("Ashaitajai´i: " + nombre_jugador);

        string_score= getIntent().getStringExtra("score");
        score= Integer.parseInt(string_score);
        tv_score.setText("Jerairu: " + score);

        string_vidas=getIntent().getStringExtra("vidas");
        vidas=Integer.parseInt(string_vidas);

        if(vidas==3){
            iv_vidas.setImageResource(R.drawable.tresvidas);
        }if(vidas==2){
            iv_vidas.setImageResource(R.drawable.dosvidas);
        }if (vidas==1){
            iv_vidas.setImageResource(R.drawable.unavida);
        }


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mp = MediaPlayer.create(this, R.raw.goats);
        mp.start();
        mp.setLooping(true);

        mp_great = MediaPlayer.create(this, R.raw.wonderful);
        mp_bad = MediaPlayer.create(this, R.raw.bad);
        NumAleatorio();
    }


    public void Comparar(View view) {
        String respuesta = et_respuesta.getText().toString();

        if (!respuesta.equals("")) {

            int respuesta_jugador = Integer.parseInt(respuesta);
            if (resultado == respuesta_jugador) {

                mp_great.start();
                score++;
                tv_score.setText("Score: " + score);
                et_respuesta.setText("");
                BaseDeDatos();

            } else {

                mp_bad.start();
                vidas--;
                BaseDeDatos();

                switch (vidas) {
                    case 3:
                        iv_vidas.setImageResource(R.drawable.tresvidas);
                        break;
                    case 2:
                        Toast.makeText(this, "Jiya'ü 2 jawü", Toast.LENGTH_LONG).show();
                        iv_vidas.setImageResource(R.drawable.dosvidas);
                        break;
                    case 1:
                        Toast.makeText(this, "Jiya'ü 1 jawü", Toast.LENGTH_LONG).show();
                        iv_vidas.setImageResource(R.drawable.unavida);
                        break;
                    case 0:
                        Toast.makeText(this, "Süpü'ü waya mii'ü jawü", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        mp.stop();
                        mp.release();
                        break;
                }

                et_respuesta.setText("");

            }

            NumAleatorio();

        } else {
            Toast.makeText(this, "Escribe tu respuesta", Toast.LENGTH_SHORT).show();
        }
    }

    public void NumAleatorio() {
        if (score <= 39) {

            numAleatorio_uno = (int) (Math.random() * 10);
            numAleatorio_dos = (int) (Math.random() * 10);

            if(numAleatorio_uno>=0 && numAleatorio_uno <=4){
                resultado = numAleatorio_uno + numAleatorio_dos;
                iv_signo.setImageResource(R.drawable.adicion);
            }else{
                resultado = numAleatorio_uno - numAleatorio_dos;
                iv_signo.setImageResource(R.drawable.resta);
            }



            if(resultado>=0){
                for (int i = 0; i < numero.length; i++) {

                    int id = getResources().getIdentifier(numero[i], "drawable", getPackageName());
                    int ide = getResources().getIdentifier(number[i],"drawable", getPackageName());
                    if (numAleatorio_uno == i) {
                        iv_Auno.setImageResource(id);
                        iv_numwa1.setImageResource(ide);
                    }
                    if (numAleatorio_dos == i) {
                        iv_Ados.setImageResource(id);
                        iv_numwa2.setImageResource(ide);
                    }
                }
            }else{
                NumAleatorio();
            }






        } else {
            Intent intent = new Intent(this, Main2Activity_Nivel5.class);
            string_score = String.valueOf(score);
            string_vidas = String.valueOf(vidas);
            intent.putExtra("jugador", nombre_jugador);
            intent.putExtra("score", string_score);
            intent.putExtra("vidas", string_vidas);

            startActivity(intent);
            finish();
            mp.stop();
            mp.release();
        }

    }

    public void BaseDeDatos(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor consulta = BD.rawQuery("select * from puntaje where score = (select max(score) from puntaje)", null);
        if(consulta.moveToFirst()){
            String temp_nombre = consulta.getString(0);
            String temp_score = consulta.getString(1);

            int bestScore = Integer.parseInt(temp_score);

            if(score > bestScore){
                ContentValues modificacion = new ContentValues();
                modificacion.put("nombre", nombre_jugador);
                modificacion.put("score", score);

                BD.update("puntaje", modificacion, "score=" + bestScore, null);
            }

            BD.close();

        } else {
            ContentValues insertar = new ContentValues();

            insertar.put("nombre", nombre_jugador);
            insertar.put("score", score);

            BD.insert("puntaje", null, insertar);
            BD.close();
        }
    }

    @Override
    public void onBackPressed(){

    }
}