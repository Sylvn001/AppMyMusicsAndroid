package br.unoeste.appmymusics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import br.unoeste.appmymusics.db.bean.Genero;
import br.unoeste.appmymusics.db.dal.GeneroDAL;

public class CategoriaActivity extends AppCompatActivity {
    private ListView lvCategoria;
    private Button btConfirmar;
    private EditText etGenero;

    private FloatingActionButton fabNovaCategoria;
    private LinearLayout linearLayout;

    private ArrayList<Genero> generos;

    private Genero genero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        lvCategoria = findViewById(R.id.lvCategoria);
        linearLayout = findViewById(R.id.linearLayoutCategoria);
        btConfirmar=findViewById(R.id.btConfirmar);
        etGenero=findViewById(R.id.etGenero);
        this.generos= new GeneroDAL(this).get("");
        ArrayAdapter<Genero> adapter=new ArrayAdapter<Genero>(this,
                android.R.layout.simple_list_item_1,generos);
        lvCategoria.setAdapter(adapter);

        lvCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                linearLayout.setVisibility(View.VISIBLE);
                Genero generoEncontrado = generos.get(i);
                etGenero.setText(generoEncontrado.getNome().toString());
                genero = generoEncontrado;
            }
        });

        lvCategoria.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Snackbar sbar;
                sbar = Snackbar.make(view, "Deseja apagar a categoria?", Snackbar.LENGTH_LONG);
                sbar.show();
                sbar.setAction("Apagar ?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Genero genero =  generos.get(i);
                        if(genero.getId() > 0){
                            apagarGenero(genero);
                        }
                    }
                });
                return true;
            }
        });

        fabNovaCategoria =  findViewById(R.id.fabNovaCategoria);

        fabNovaCategoria.setOnClickListener(e-> {
            linearLayout.setVisibility(View.VISIBLE);
            etGenero.setText("");
            etGenero.requestFocus();
        });

        linearLayout.setVisibility(View.GONE);
        btConfirmar.setOnClickListener(e->{
            if(genero != null){
                atualizarGenero();
            }else{
                cadastrarGenero();
            }
            linearLayout.setVisibility(View.GONE);
        });
    }

    private void cadastrarGenero() {
        GeneroDAL dal = new GeneroDAL(this);
        Genero genero=new Genero(etGenero.getText().toString());
        dal.salvar(genero);
        this.atualizarDados();
    }

    private void atualizarGenero() {
        genero.setNome(etGenero.getText().toString());
        GeneroDAL dal = new GeneroDAL(this);
        System.out.println(genero.getId());
        dal.alterar(genero);
        this.atualizarDados();
    }

    private void apagarGenero(Genero genero){
        GeneroDAL dal = new GeneroDAL(this);
        dal.apagar(genero.getId());
        this.atualizarDados();
    }

    private void atualizarDados(){
        this.generos=new GeneroDAL(this).get("");
        this.genero = null;
        ArrayAdapter<Genero> adapter=new ArrayAdapter<Genero>(this,
                android.R.layout.simple_list_item_1,generos);
        lvCategoria.setAdapter(adapter);
    }

    private void buscarCategoria(String text){
        this.generos=new GeneroDAL(this).get(text);
        ArrayAdapter<Genero> adapter=new ArrayAdapter<Genero>(this,
                android.R.layout.simple_list_item_1,generos);
        lvCategoria.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        MenuItem searchItem = menu.findItem(R.id.itemSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println(s);
                buscarCategoria("gen_nome like '%" + s +"%'");
                return false;
            }
        });
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
                break;
            case R.id.itvoltar:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}