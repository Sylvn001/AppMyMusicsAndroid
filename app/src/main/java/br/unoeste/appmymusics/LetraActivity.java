package br.unoeste.appmymusics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import br.unoeste.appmymusics.api.Letra;
import br.unoeste.appmymusics.api.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LetraActivity extends AppCompatActivity {

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

        chamarWS(artista, musica);
    }

    private void chamarWS(String artista, String musica) {

        String apiKey = "apikey=660a4395f992ff67786584e238f501aa";
        Call<Letra> call = new RetrofitConfig().getLetraService().buscarLetra(apiKey, artista, musica);

        call.enqueue(new Callback<Letra>() {
            @Override
            public void onResponse(Call<Letra> call, Response<Letra> response) {
                Letra letra = response.body();
                if(letra.type.compareToIgnoreCase("notfound") == 0){
                    tvLetra.setText("Não encontrado");
                }else{
                tvLetra.setText("");

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