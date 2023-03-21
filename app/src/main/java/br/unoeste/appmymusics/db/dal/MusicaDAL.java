package br.unoeste.appmymusics.db.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;


import java.util.ArrayList;

import br.unoeste.appmymusics.db.bean.Musica;
import br.unoeste.appmymusics.db.util.Conexao;


public class MusicaDAL
{   private Conexao con;
    private Context context;
    private final String TABLE="musica";

    public MusicaDAL(Context context) {
        this.context=context;
        con = new Conexao(context);
        try {
            con.conectar();
        }
        catch(Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public boolean salvar(Musica o)
    {
        ContentValues dados=new ContentValues();
        dados.put("mus_ano",o.getAno());
        dados.put("mus_titulo",o.getTitulo());
        dados.put("mus_interprete",o.getInterprete());
        dados.put("mus_genero",o.getGenero().getId());
        dados.put("mus_duracao",o.getDuracao());
        return con.inserir(TABLE,dados)>0;
    }
    public boolean alterar(Musica o)
    {
        ContentValues dados=new ContentValues();
        dados.put("mus_id",o.getId());
        dados.put("mus_ano",o.getAno());
        dados.put("mus_titulo",o.getTitulo());
        dados.put("mus_interprete",o.getInterprete());
        dados.put("mus_genero",o.getGenero().getId());
        dados.put("mus_duracao",o.getDuracao());
        return con.alterar(TABLE,dados,"mus_id="+o.getId())>0;
    }
    public boolean apagar(long chave)
    {
        return con.apagar(TABLE,"mus_id="+chave)>0;
    }

    public Musica get(int id)
    {   GeneroDAL gdal=new GeneroDAL(context);
        Musica o = null;
        Cursor cursor=con.consultar("select * from "+TABLE+" where mus_id="+id);
        if(cursor.moveToFirst())
            o=new Musica(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),gdal.get(cursor.getInt(4)),cursor.getDouble(5));
        cursor.close();;
        return o;
    }
    public ArrayList <Musica> get(String filtro)
    {   GeneroDAL gdal=new GeneroDAL(context);
        ArrayList <Musica> objs = new ArrayList();
        String sql="select * from "+TABLE;
        if (!filtro.equals(""))
            sql+=" where "+filtro;

        Cursor cursor=con.consultar(sql);
        if(cursor.moveToFirst())
            while (!cursor.isAfterLast()) {
                objs.add(new Musica(cursor.getInt(0),cursor.getInt(1),
                        cursor.getString(2), cursor.getString(3),
                        gdal.get(cursor.getInt(4)), cursor.getDouble(5)));
                cursor.moveToNext();
            }
        cursor.close();;
        return objs;
    }
}
