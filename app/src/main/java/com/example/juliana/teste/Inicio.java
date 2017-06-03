package com.example.juliana.teste;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.support.v7.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inicio extends Activity {
    // declaração global
    RecyclerView lista_de_eventos;
    EventoAdapter adapter;
    List<Evento> eventos = new ArrayList<Evento>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        ImageButton telafavorito = (ImageButton) findViewById(R.id.favorito);
        ImageButton telacadastro = (ImageButton) findViewById(R.id.cadastro);
        ImageButton telaeditar = (ImageButton) findViewById(R.id.editar);
        ImageButton telainicial = (ImageButton) findViewById(R.id.inicio);

        telainicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Inicioo = new Intent(Inicio.this, Inicio.class);
                startActivity(Inicioo);
            }
        });
        telafavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verica se usuario esta logado
                if(SharedInstance.getInstance().IDUsuario == -1){
                    Intent Login = new Intent(Inicio.this, login.class);
                    startActivity(Login);
                }
                else{
                Intent Favoritoo = new Intent(Inicio.this, favoritos.class);
                startActivity(Favoritoo);}
            }
        });
        telacadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verica se usuario esta logado
                if(SharedInstance.getInstance().IDUsuario == -1){
                    Intent Login = new Intent(Inicio.this, login.class);
                    startActivity(Login);
                }
                else{
                Intent Cadastroo = new Intent(Inicio.this, cadastro.class);
                startActivity(Cadastroo);}
            }
        });
        telaeditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verica se usuario esta logado
                if(SharedInstance.getInstance().IDUsuario == -1){
                    Intent Login = new Intent(Inicio.this, login.class);
                    startActivity(Login);
                }
                else{
                Intent Editarr = new Intent(Inicio.this, Editar.class);
                startActivity(Editarr);}
            }
        });

        lista_de_eventos = (RecyclerView) findViewById(R.id.lista_de_eventos);


        adapter = new EventoAdapter(eventos, this);
        lista_de_eventos.setAdapter(adapter);
        RecyclerView.LayoutManager layout2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lista_de_eventos.setLayoutManager(layout2);
        //chama carregaevento()
        carregareventos();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    void carregareventos(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, SingletonNetwork.URLServer + "listadeevento",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                          JSONArray lista = new JSONArray(response);
                            for(int i =0; i<lista.length(); i++){
                                JSONObject conteudo = (JSONObject) lista.get(i);
                                String foto = conteudo.getString("foto");
                                String data = conteudo.getString("data");
                                String nome = conteudo.getString("nome");
                                String datacadastro = conteudo.getString("datacadastro");
                                int idusuario = conteudo.getInt("idusuario");
                                int idevento = conteudo.getInt("Id");
                                String descricao = conteudo.getString("descricao");

                                int efav = conteudo.getInt("efavorito");
                                boolean efavorito = (efav == 1 ? true : false);
                                Evento d = new Evento(idevento, foto, nome, data, descricao,  idusuario, datacadastro, efavorito);
                                d.setLocal(conteudo.getString("local"));
                                eventos.add(d);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String json = null;
                        NetworkResponse response = error.networkResponse;
                        if(response != null && response.data != null){
                            switch(response.statusCode) {
                                default:
                                    String value = null;
                                    try {

                                        value = new String(response.data, "UTF-8");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    json = trimMessage(value, "message");
                                    break;
                            }
                        }
                        error.printStackTrace();
                    }
                    public String trimMessage(String json, String key){
                        String trimmedString = null;
                        try{
                            JSONObject obj = new JSONObject(json);
                            trimmedString = obj.getString(key);
                        } catch(JSONException e){
                            e.printStackTrace();
                            return null;
                        }
                        return trimmedString;
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user",Integer.toString(SharedInstance.getInstance().IDUsuario));
                return params;
            }

        };


        // This adds the request to the request queue
        SingletonNetwork.getInstance(getApplicationContext())
                .addToRequestQueue(postRequest);
    }
}



