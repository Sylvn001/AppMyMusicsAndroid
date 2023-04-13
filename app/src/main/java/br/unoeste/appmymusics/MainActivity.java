package br.unoeste.appmymusics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import br.unoeste.appmymusics.db.bean.Genero;
import br.unoeste.appmymusics.db.bean.Musica;
import br.unoeste.appmymusics.db.dal.GeneroDAL;
import br.unoeste.appmymusics.db.dal.MusicaDAL;

public class MainActivity extends AppCompatActivity {
    private ListView lvMusica;
    private Button btConfirmar;

    private EditText etTitulo;
    private EditText etAno;
    private EditText etInterprete;
    private EditText etDuracao;

    private Genero generoSelecionado;

    private AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<Genero> adapterGeneros;
    private FloatingActionButton fabNovaMusica;
    private LinearLayout linearLayout;
    private ArrayList<Musica> musicas;
    private Musica musica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMusica = findViewById(R.id.lvMusicas);
        linearLayout = findViewById(R.id.linearLayoutMusica);
        btConfirmar=findViewById(R.id.btConfirmarMusica);

        etTitulo = findViewById(R.id.etTituloMusica);
        etAno = findViewById(R.id.etAno);
        etDuracao = findViewById(R.id.etDuracao);
        etInterprete = findViewById(R.id.etInterprete);

        //Categorias
        ArrayList<Genero> generos = new GeneroDAL(this).get("");
        adapterGeneros = new ArrayAdapter<Genero>(this, R.layout.list_item, generos);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        autoCompleteTxt.setAdapter(adapterGeneros);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Genero genero = (Genero) adapterView.getItemAtPosition(i);
                generoSelecionado = genero;
            }
        });

        autoCompleteTxt.setText(adapterGeneros.getItem(0).toString(), false);

        this.musicas= new MusicaDAL(this).get("");
        MusicaAdapter adapter = new MusicaAdapter(this, R.layout.item_layout,musicas);
        lvMusica.setAdapter(adapter);
        lvMusica.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("cliquei nessa porra");
                linearLayout.setVisibility(View.VISIBLE);
                Musica musicaEncontrado = musicas.get(i);
                etTitulo.setText(musicaEncontrado.getTitulo().toString());
                etAno.setText(musicaEncontrado.getAno() + "");
                etInterprete.setText(musicaEncontrado.getInterprete());
                etDuracao.setText(musicaEncontrado.getDuracao() + "");
                generoSelecionado = musicaEncontrado.getGenero();
                musica = musicaEncontrado;

                int posItem;
                for(posItem = 0;
                    posItem < adapterGeneros.getCount()
                            && adapterGeneros.getItem(posItem).getNome().compareToIgnoreCase(generoSelecionado.getNome()) != 0; posItem++){}

                autoCompleteTxt.setText(adapterGeneros.getItem(posItem).toString(), false);
                generoSelecionado = adapterGeneros.getItem(posItem);
            }
        });
        lvMusica.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Snackbar sbar;
                sbar = Snackbar.make(view, "Deseja apagar a musica?", Snackbar.LENGTH_LONG);
                sbar.show();
                sbar.setAction("Apagar ?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Musica musica =  musicas.get(i);
                        if(musica.getId() > 0){
                            apagarMusica(musica);
                        }
                    }
                });
                return true;
            }
        });

        fabNovaMusica =  findViewById(R.id.fabNovaMusica);

        fabNovaMusica.setOnClickListener(e-> {
            linearLayout.setVisibility(View.VISIBLE);
            limparCampos();
        });

        linearLayout.setVisibility(View.GONE);
        btConfirmar.setOnClickListener(e->{
            if(musica != null){
                atualizarMusica();
            }else{
                cadastrarMusica();
            }
            musica = null;
            linearLayout.setVisibility(View.GONE);
        });

    }


    private void cadastrarMusica() {
        try{
            MusicaDAL dal = new MusicaDAL(this);
            System.out.println(generoSelecionado.getNome());
            Musica musica = new Musica(
                    Integer.parseInt(etAno.getText().toString()),
                    etTitulo.getText().toString(),
                    etInterprete.getText().toString(),
                    generoSelecionado,
                    Double.parseDouble(etDuracao.getText().toString())
            );
            dal.salvar(musica);
            System.out.println(musica.getGenero().getNome());
            System.out.println(musica.toString());
            this.atualizarDados();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void atualizarMusica() {
        try{
            musica.setTitulo(etTitulo.getText().toString());
            musica.setAno(Integer.parseInt(etAno.getText().toString()));
            musica.setDuracao(Double.parseDouble(etDuracao.getText().toString()));
            musica.setGenero(generoSelecionado);
            musica.setInterprete(etInterprete.getText().toString());
            MusicaDAL dal = new MusicaDAL(this);
            dal.alterar(musica);
            this.atualizarDados();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void apagarMusica(Musica musica){
        try{
            MusicaDAL dal = new MusicaDAL(this);
            dal.apagar(musica.getId());
            this.atualizarDados();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void atualizarDados(){
        this.musicas= new MusicaDAL(this).get("");
        MusicaAdapter adapter = new MusicaAdapter(this, R.layout.item_layout,musicas);
        lvMusica.setAdapter(adapter);
        this.musica = null;
        this.limparCampos();
    }

    private void buscarMusica(String text){
        this.musicas=new MusicaDAL(this).get(text);
        ArrayAdapter<Musica> adapter=new ArrayAdapter<Musica>(this,
                android.R.layout.simple_list_item_1,musicas);
        lvMusica.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);

        MenuItem searchItem = menu.findItem(R.id.itemSearchMusica);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println(s);
                buscarMusica("mus_titulo like '%" + s +"%' OR mus_interprete like '%" + s +"%'");
                return false;
            }
        });
        ;
        return super.onCreateOptionsMenu(menu);
    }

    private void limparCampos(){
        etTitulo.setText("");
        etTitulo.requestFocus();
        etAno.setText("");
        etAno.requestFocus();
        etDuracao.setText("");
        etDuracao.requestFocus();
        etInterprete.setText("");
        etInterprete.requestFocus();
        generoSelecionado = adapterGeneros.getItem(0);
        musica = null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.itmusica:
                linearLayout.setVisibility(View.VISIBLE);
                limparCampos();
                break;
            case R.id.itcategoria:
                Intent intent = new Intent(this,CategoriaActivity.class);
                startActivity(intent);
                break;
            case R.id.itfechar:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}