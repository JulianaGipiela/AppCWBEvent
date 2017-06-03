package com.example.juliana.teste;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.bitmap;

public class cadastro extends AppCompatActivity {
    RecyclerView lista_de_eventos;
    EventoAdapter adapter;
    EditText editTextData;
    EditText editTextInform;
    EditText editTextLocal;
    EditText editTextNome;
    Button btnfoto;
    ImageView imgfoto;
    Bitmap a=null;
    List<Evento> eventos = new ArrayList<Evento>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        editTextData = (EditText) findViewById(R.id.editTextDataEvento);
        editTextInform = (EditText) findViewById(R.id.editTextInformEvento);
        editTextLocal = (EditText) findViewById(R.id.editTextLocalEvento);
        editTextNome = (EditText) findViewById(R.id.editTextNomeEvento);
        imgfoto = (ImageView) findViewById(R.id.imgvizualizarimagemselecionada);
        btnfoto = (Button) findViewById(R.id.btnselecionarimagemcadastrodeeventos);

        ImageButton telacadastrosalva = (ImageButton) findViewById(R.id.salva);
        ImageButton telacadastrolixo = (ImageButton) findViewById(R.id.lixo);
        ImageButton telacadastrocancela = (ImageButton) findViewById(R.id.limpa);
        telacadastrocancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        telacadastrolixo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextData.setText("");
                editTextInform.setText("");
                editTextLocal.setText("");
                editTextNome.setText("");
            }
        });
        telacadastrosalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insereeventos();
            }
        });
        btnfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pegar imagem da camera
                //Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Activity s = this;

        if(requestCode == 1){
            try {

                if(data != null) {
                    a = MediaStore.Images.Media.getBitmap(s.getContentResolver(), data.getData());
                    imgfoto.setImageBitmap(a);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void insereeventos(){

        final ProgressDialog progDailog = ProgressDialog.show(this,
                "Carregando",
                "Por favor, aguarde...", true);
        StringRequest postRequest = new StringRequest(Request.Method.POST, SingletonNetwork.URLServer + "insereevento",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progDailog.dismiss();
                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cadastro.this);

                        alertDialogBuilder.setTitle(R.string.app_name);
                        alertDialogBuilder.setMessage("Evento "+ editTextNome.getText().toString() +" adicionado com sucesso");
                        alertDialogBuilder.setPositiveButton(" Ok ", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });

                        alertDialogBuilder.show();


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
                params.put("nome", editTextNome.getText().toString());
                params.put("data", editTextData.getText().toString());
                params.put("local", editTextLocal.getText().toString());
                params.put("informacao", editTextInform.getText().toString());
                if(a!= null){
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    a.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    params.put("foto", encoded);
                }
                else{
                    params.put("foto", "");
                }

                return params;
            }

        };


        // This adds the request to the request queue
        SingletonNetwork.getInstance(getApplicationContext())
                .addToRequestQueue(postRequest);
    }
}