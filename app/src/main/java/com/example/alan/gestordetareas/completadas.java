package com.example.alan.gestordetareas;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class completadas extends AppCompatActivity {

    private AdminBD db; //Variable del administrador de la base de datos
    //Menu, Declaracion de variables
    private DrawerLayout drawerLayout;
    final List<MenuItem> items = new ArrayList<>();
    private Menu menu;
    private ImageView btnMenu;
    private NavigationView nav;
    //Fin menu, declaracion de variables
    private ListView completadas;
    private ArrayList<ObjTarea> tareas;
    private ArrayList<ObjTarea> tareasCompletadas;
    private Button limpiarLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_completadas);
        //Cambiar el color en la barra de notificaciones (Solo funciona de lollipop hacia arriba)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.completadas));
        }
        //Fin cambio de color de barra de notificaciones
        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        menuNav();
        // Fin menu
        //Codigo para crear el objeto de la base de datos y
        //agregar el nombre de usuario al menu
        db = new AdminBD(this);
        ObjUsuario usuario = db.selectUsuario();
        //Codigo para poner en el Menu el nombre de usuario
        View header = nav.getHeaderView(0);
        TextView nombreUsuario = (TextView) header.findViewById(R.id.menuNombreUsuario);
        nombreUsuario.setText(usuario.getNombre());
        //Fin Codigo para poner el nombre de usuario en el menu
        completadas = (ListView)findViewById(R.id.completas);
        tareasCompletadas = new ArrayList<ObjTarea>();
        cargarTareas();//LLamada a funcion para llenar las tareas
        limpiarLista = (Button)findViewById(R.id.btn_limpiarLista);
        limpiarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tareasCompletadas.size() > 0){
                    int[] ids = new int[tareasCompletadas.size()];
                    for(int i = 0; i < ids.length; i ++){
                        ids[i] = tareasCompletadas.get(i).getId();
                    }
                    DialogoAdvertenciaTodasTareas advertencia = new DialogoAdvertenciaTodasTareas();
                    Bundle variable = new Bundle();
                    variable.putIntArray("id", ids);
                    advertencia.setArguments(variable);
                    advertencia.show(getFragmentManager(), "Advertencia");
                }else{
                    Toast.makeText(completadas.this, "No hay tareas completadas por eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Funcion encargada del menu
     * Muestra, oculta y redirecciona el menu
     */
    public void menuNav() {
        for(int i = 0; i < menu.size(); i++){
            items.add(menu.getItem(i));
        }
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                int pos = items.indexOf(item);
                if(pos == 0){
                    finish();
                }else if(pos == 1){
                    Intent i = new Intent(completadas.this, Materias.class);
                    startActivity(i);
                    finish();
                }else if(pos == 2){

                }else if(pos == 3) {
                    Intent i = new Intent(completadas.this, horario.class);
                    startActivity(i);
                    finish();
                }else if(pos == 4){
                    Intent i = new Intent(completadas.this, agregarTarea.class);
                    startActivity(i);
                    finish();
                }else if(pos == 5){
                    Intent i = new Intent(completadas.this, agregar_materia.class);
                    startActivity(i);
                    finish();
                }else if(pos == 6){
                    Intent i = new Intent(completadas.this, acerca.class);
                    startActivity(i);
                    finish();
                }
                drawerLayout.closeDrawer(nav);
                item.setChecked(false);
                return false;
            }
        });
        btnMenu = (ImageView)findViewById(R.id.Btnmenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(nav);
            }
        });
    }

    public void cargarTareas(){
        tareas = db.selectTareas();
        if(tareas.size() > 0){
            for(ObjTarea t : tareas){
                if(t.getCompletado() == 1){
                    tareasCompletadas.add(t);
                }
            }
            if(tareasCompletadas.size() > 0){
                completadas.setAdapter(new TareasAdaptador(getApplicationContext(), tareasCompletadas, getFragmentManager()));
                ajustarListView(completadas);
            }
        }
    }

    /**
     * Funcion la cual ajusta el tamaño de un ListView dependiendo de los elementos que contiene
     * @param listView
     */
    private void ajustarListView(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
