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
 * Created by juliana on 21/05/2017.
 */

public class EventoFViewHolder extends RecyclerView.ViewHolder{
    public ImageView imagemFavorito;
    public TextView nomeFavorito;
    public TextView dataFavorito;
    public ImageButton btnF;

    public EventoFViewHolder(View view, final List<Evento> lista, final Context context){
        super(view);
        imagemFavorito = (ImageView) view.findViewById(R.id.image_evento_favorito);
        btnF = (ImageButton) view.findViewById(R.id.btn_evento_favorito);
        nomeFavorito = (TextView) view.findViewById(R.id.nome_evento_favorito);
        dataFavorito = (TextView) view.findViewById(R.id.data_evento_favorito);
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
