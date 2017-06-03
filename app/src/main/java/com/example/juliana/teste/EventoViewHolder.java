package com.example.juliana.teste;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by juliana on 20/05/2017.
 */

public class EventoViewHolder extends RecyclerView.ViewHolder{
    public ImageView imagemInicio;
    public TextView nomeInicio;
    public TextView dataInicio;
    public ImageButton btnF;

    public EventoViewHolder(View view, final List<Evento> lista, final Context context){
        super(view);

        imagemInicio = (ImageView) view.findViewById(R.id.image_evento_inico);
        btnF = (ImageButton) view.findViewById(R.id.btn_evento_inico);
        nomeInicio = (TextView) view.findViewById(R.id.nome_evento_inicio);
        dataInicio = (TextView) view.findViewById(R.id.data_evento_inicio);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = getAdapterPosition();
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setTitle(R.string.app_name);
                alertDialogBuilder.setMessage(lista.get(p).getDecricao() + "\n" + lista.get(p).getLocal());
                alertDialogBuilder.setPositiveButton(" Ok ", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                alertDialogBuilder.show();
            }
        });
    }
}
