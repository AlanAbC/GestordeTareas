package com.claresti.mistareas.gestordetareas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class DialogoAdvertenciaTodasTareas extends DialogFragment {

    static DialogoAdvertenciaTodasTareas newInstance(int[] id) {
        DialogoAdvertenciaTodasTareas f = new DialogoAdvertenciaTodasTareas();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putIntArray("id", id);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        final int[] id = getArguments().getIntArray("id");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.advertencia_tareas, null);
        builder.setView(layout).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AdminBD db = new AdminBD(getActivity());
                int flag = 0;
                for(int i = 0; i < id.length; i++){
                    flag += db.deleteTarea(id[i]);
                }
                if(flag > 0){
                    Intent in = new Intent(getActivity(), MainActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(in);
                    getActivity().finish();
                    Toast.makeText(getActivity(), "Se borro correctamente las tarea", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
