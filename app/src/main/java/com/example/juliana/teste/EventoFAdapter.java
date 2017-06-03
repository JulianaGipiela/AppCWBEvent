package com.example.juliana.teste;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by juliana on 21/05/2017.
 */

public class EventoFAdapter extends RecyclerView.Adapter{
    private List<Evento> lista_evento_inicio_favoritos;
    private View.OnClickListener listenerf;
    private Context contextf;
    Editar ponteiroparaclassepai;
    favoritos ponteiroparaclassepai2;

    public EventoFAdapter(List<Evento> lista_evento_inicio_favoritos, Context contextf, Editar classepai, favoritos ponteiroparaclassepai2) {
        this.lista_evento_inicio_favoritos = lista_evento_inicio_favoritos;
        this.contextf = contextf;
        this.ponteiroparaclassepai = classepai;
        this.ponteiroparaclassepai2 = ponteiroparaclassepai2;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contextf).inflate(R.layout.celulafavoritos, parent, false);
        EventoFViewHolder cell2 = new EventoFViewHolder(view, lista_evento_inicio_favoritos, this.contextf);
        return cell2;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final EventoFViewHolder primeira = (EventoFViewHolder) holder;
        final Evento eventFavorit = lista_evento_inicio_favoritos.get(position);

        primeira.dataFavorito.setText(eventFavorit.getDataInicio());
        primeira.nomeFavorito.setText(eventFavorit.getNomeInico());
        primeira.btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ponteiroparaclassepai != null)
                    deletarevento(eventFavorit.getIdevento());
                else{
                    deletarfavorito(eventFavorit.getIdevento());
                }

            }
        });
        Picasso.with(contextf).load(eventFavorit.getImagemInicio()).into(primeira.imagemFavorito);


    }

    @Override
    public int getItemCount() {
        return lista_evento_inicio_favoritos.size();
    }


    void deletarfavorito(final int idevento){
        StringRequest postRequest = new StringRequest(Request.Method.POST, SingletonNetwork.URLServer + "atualizarfavorito",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                                ponteiroparaclassepai2.carregareventos();
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
                params.put("idevento", Integer.toString(idevento));
                params.put("status", "0");
                return params;
            }

        };


        // This adds the request to the request queue
        SingletonNetwork.getInstance(getApplicationContext())
                .addToRequestQueue(postRequest);
    }



    void deletarevento(final int idevento){
        StringRequest postRequest = new StringRequest(Request.Method.POST, SingletonNetwork.URLServer + "deletarmeuevento",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                                // terminou de executar, preciso me lembrar de atualizar a tela
                        ponteiroparaclassepai.carregarmeuseventos();
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
                params.put("idevento", Integer.toString(idevento));
                return params;
            }

        };


        // This adds the request to the request queue
        SingletonNetwork.getInstance(getApplicationContext())
                .addToRequestQueue(postRequest);
    }
}
