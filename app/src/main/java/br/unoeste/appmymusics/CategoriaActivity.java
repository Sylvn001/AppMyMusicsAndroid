package br.unoeste.appmymusics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import br.unoeste.appmymusics.db.bean.Genero;
import br.unoeste.appmymusics.db.dal.GeneroDAL;

public class CategoriaActivity extends AppCompatActivity {
    private ListView lvCategoria;
    private Button btConfirmar;
    private EditText etGenero;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        lvCategoria = findViewById(R.id.lvCategoria);
        linearLayout = findViewById(R.id.linearLayout);
        btConfirmar=findViewById(R.id.btConfirmar);
        etGenero=findViewById(R.id.etNomeGenero);
        ArrayList<Genero> generos=new GeneroDAL(this).get("");
        ArrayAdapter<Genero> adapter=new ArrayAdapter<Genero>(this,
                android.R.layout.simple_list_item_1,generos);
        lvCategoria.setAdapter(adapter);
        linearLayout.setVisibility(View.GONE);
        btConfirmar.setOnClickListener(e->{
            cadastrarGenero();
            linearLayout.setVisibility(View.GONE);
        });

    }

    private void cadastrarGenero() {
        GeneroDAL dal = new GeneroDAL(this);
        Genero genero=new Genero(etGenero.getText().toString());
        dal.salvar(genero);
        this.atualizaListaCategorias();
    }

    private void apagarGenero(Genero genero){
        GeneroDAL dal = new GeneroDAL(this);
        dal.apagar(1);
        this.atualizaListaCategorias();
    }

    private void editarGenero(Genero genero){
        GeneroDAL dal = new GeneroDAL(this);
        //dal.alterar("");
        this.atualizaListaCategorias();
    }

    private void atualizaListaCategorias(){
        ArrayList<Genero> generos=new GeneroDAL(this).get("");
        ArrayAdapter<Genero> adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, generos);
        lvCategoria.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.itncategoria:
                linearLayout.setVisibility(View.VISIBLE);
                etGenero.setText("");
                etGenero.requestFocus();
                // cadastrar nova categoria
                break;
            case R.id.itvoltar:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}