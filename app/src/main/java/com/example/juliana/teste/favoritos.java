package com.example.juliana.teste;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

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

public class favoritos extends Activity {
    RecyclerView lista_de_eventos_favoritos;
    EventoFAdapter adapter;
    List<Evento> eventosF = new ArrayList<Evento>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        ImageButton telainicial = (ImageButton) findViewById(R.id.inicio);

        ImageButton telacadastro = (ImageButton) findViewById(R.id.cadastro);
        ImageButton telaeditar = (ImageButton) findViewById(R.id.editar);
        telainicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Inicioo = new Intent(favoritos.this, Inicio.class);
                startActivity(Inicioo);
            }
        });
        telacadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verificar se ta logado
                if(SharedInstance.getInstance().IDUsuario == -1){
                    Intent Login = new Intent(favoritos.this, login.class);
                    startActivity(Login);
                }
                else{
                Intent Cadastroo = new Intent(favoritos.this, cadastro.class);
                startActivity(Cadastroo);}
            }
        });
        telaeditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verificar se ta logado
                if(SharedInstance.getInstance().IDUsuario == -1){
                    Intent Login = new Intent(favoritos.this, login.class);
                    startActivity(Login);
                }
                else{
                Intent Editarr = new Intent(favoritos.this, Editar.class);
                startActivity(Editarr);}
            }
        });
        lista_de_eventos_favoritos = (RecyclerView) findViewById(R.id.lista_de_eventos_favoritos);




        adapter = new EventoFAdapter(eventosF, this, null, this);
        lista_de_eventos_favoritos.setAdapter(adapter);
        RecyclerView.LayoutManager layout2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lista_de_eventos_favoritos.setLayoutManager(layout2);
       carregareventos();
    }
    void carregareventos(){
        eventosF.clear();
        StringRequest postRequest = new StringRequest(Request.Method.POST, SingletonNetwork.URLServer + "listadefavorito",
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
                                String descricao = conteudo.getString("descricao");
                                int idevento = conteudo.getInt("Id");

                                boolean efavorito = (true);
                                Evento Teste = new Evento(idevento, foto, nome, data, descricao, idusuario,datacadastro, efavorito);
                                Teste.setLocal(conteudo.getString("local"));
                                        eventosF.add(Teste);
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
                   params.put("user", Integer.toString(SharedInstance.getInstance().IDUsuario));
                return params;
            }

        };


        // This adds the request to the request queue
        SingletonNetwork.getInstance(getApplicationContext())
                .addToRequestQueue(postRequest);
    }

}
