package com.example.juliana.teste;

/**
 * Created by juliana on 20/05/2017.
 */
import android.content.Context;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class EventoAdapter extends RecyclerView.Adapter {
    private List<Evento> lista_evento_inicio;
    private View.OnClickListener listener;
    private Context context;

    public EventoAdapter(List<Evento> lista_evento_inicio, Context context) {
        this.lista_evento_inicio = lista_evento_inicio;
        this.context = context;
    }
     @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.celula, parent, false);
        EventoViewHolder cell = new EventoViewHolder(view, lista_evento_inicio, this.context);
        return cell;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final EventoViewHolder primeira = (EventoViewHolder) holder;
        final Evento temp = lista_evento_inicio.get(position);

        primeira.dataInicio.setText(temp.getDataInicio());
        primeira.nomeInicio.setText(temp.getNomeInico());
        if(temp.isEfavorito()){
            primeira.btnF.setImageResource(R.drawable.favoritorosa);
        }else {
            primeira.btnF.setImageResource(R.drawable.frosa);
        }

    primeira.btnF.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            temp.setEfavorito(!temp.isEfavorito());

            if(temp.isEfavorito()){
                primeira.btnF.setImageResource(R.drawable.favoritorosa);
            }else {
                primeira.btnF.setImageResource(R.drawable.frosa);
            }
            salvafavotitos(temp.getIdevento(), temp.isEfavorito());


        }
    });
        Picasso.with(context).load(temp.getImagemInicio()).into(primeira.imagemInicio);

    }

    @Override
    public int getItemCount() {
        return lista_evento_inicio.size();
    }

    void salvafavotitos(final int idevento, final boolean status){
        StringRequest postRequest = new StringRequest(Request.Method.POST, SingletonNetwork.URLServer + "atualizarfavorito",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


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
                params.put("status",(status ? "1" : "0"));
                return params;
            }

        };


        // This adds the request to the request queue
        SingletonNetwork.getInstance(getApplicationContext())
                .addToRequestQueue(postRequest);
    }



}
