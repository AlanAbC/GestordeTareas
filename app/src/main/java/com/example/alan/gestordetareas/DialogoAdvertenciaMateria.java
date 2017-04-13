package com.example.alan.gestordetareas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class DialogoAdvertenciaMateria extends DialogFragment {

    static DialogoAdvertenciaMateria newInstance(int id) {
        DialogoAdvertenciaMateria f = new DialogoAdvertenciaMateria();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("id", id);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        final int id = getArguments().getInt("id");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.advertenciam, null);
        builder.setView(layout).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AdminBD db = new AdminBD(getActivity());
                if(db.deleteMateria(id) == 1){
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    Toast.makeText(getActivity(), "Se borro correctamente la tarea", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "No se pudo borrar la tarea", Toast.LENGTH_SHORT).show();
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
