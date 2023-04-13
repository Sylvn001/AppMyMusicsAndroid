package br.unoeste.appmymusics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import br.unoeste.appmymusics.api.Letra;
import br.unoeste.appmymusics.api.Mu;
import br.unoeste.appmymusics.api.RetrofitConfig;
import br.unoeste.appmymusics.api.Translate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LetraActivity extends AppCompatActivity {
    private Letra letra;
    private Translate translate = null;
    private Mu music = null;

    private int idioma = 1;

    private TextView tvLetra;
    private Button btnTraduzir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letra);
        tvLetra = findViewById(R.id.mlLetra);

        btnTraduzir = findViewById(R.id.btTrocaIdioma);

        Intent intent = getIntent();
        String musica = intent.getStringExtra("musica");
        String artista = intent.getStringExtra("artista");

        btnTraduzir.setOnClickListener(e -> {
            if(letra != null && music != null){
                if(idioma == 1){
                    idioma = 2;
                    if(music.translate != null){
                        tvLetra.setText(music.translate.get(0).getText());
                    }else{
                        tvLetra.setText("Tradução indisponivel");
                    }
                }else{
                    idioma = 1;
                    tvLetra.setText(music.getText());
                }
            }
        });

        chamarWS(artista, musica);
    }

    private void chamarWS(String artista, String musica) {

        String apiKey = "660a4395f992ff67786584e238f501aa";
        Call<Letra> call = new RetrofitConfig().getLetraService().buscarLetra(apiKey, artista, musica);

        call.enqueue(new Callback<Letra>() {
            @Override
            public void onResponse(Call<Letra> call, Response<Letra> response) {
                letra = response.body();
                if(letra.type.compareToIgnoreCase("notfound") == 0){
                    tvLetra.setText("Não encontrado");
                }else{
                    if(letra.mus != null){
                        music = letra.mus.get(0);
                        tvLetra.setText(letra.mus.get(0).getText());
                    }else{
                        tvLetra.setText("Musica não encontrada");
                    }
                }
                Log.i("retorno: ",""+letra);
            }

            @Override
            public void onFailure(Call<Letra> call, Throwable t) {
                Log.e("CEPService   ", "Erro ao buscar o cep:" + t.getMessage());

            }
        });
    }

}