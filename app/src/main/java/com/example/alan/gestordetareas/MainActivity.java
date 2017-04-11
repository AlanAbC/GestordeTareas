package com.example.alan.gestordetareas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AdminBD db; //Variable del administrador de la base de datos
    private FloatingActionButton agregar; //Variable del boton agregar
    //Menu, Declaracion de variables
    private DrawerLayout drawerLayout;
    final List<MenuItem> items = new ArrayList<>();
    private Menu menu;
    private ImageView btnMenu;
    private NavigationView nav;
    //Fin menu, declaracion de variables
    private ArrayList<ObjTarea> tareas;
    private ListView hoy;
    private ListView manana;
    private ListView semana;
    private ListView mes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        //Cambiar el color en la barra de notificaciones (Solo funciona de lollipop hacia arriba)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.main));
        }
        //Fin cambio de color de barra de notificaciones
        //Inserta un lisener en el boton agregar para cambiar de ventana
        agregar = (FloatingActionButton)findViewById(R.id.agregar);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,agregarTarea.class);
                startActivity(i);
            }
        });
        //FIn insertar listener boton agregar
        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        menuNav();
        // Fin menu
        //Creacion del objeto usuario y comprobacion de primera ves o no en el sistemaa
        //cambia el nombre de la base de datos y el valor de usuPrimera a 1 en caso de que sea la primera vez
        //en caso contrario solo agrega nombre del usuario al menu y carga las tareas
        db = new AdminBD(this);
        ObjUsuario usuario = db.selectUsuario();
        if(usuario.getPrimera() == 0){
            //Creacion de la ventana de inicio
            final RelativeLayout inicio = (RelativeLayout)findViewById(R.id.lPrimeraVez);
            inicio.setVisibility(View.VISIBLE);
            Button actualizar = (Button)findViewById(R.id.actualizarUsuario);
            actualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ObjUsuario usuarioActualizar = new ObjUsuario();
                    EditText usuNombre = (EditText)findViewById(R.id.nombreUsuario);
                    String nombre = usuNombre.getText().toString();
                    usuarioActualizar.setNombre(nombre);
                    int respuesta = db.updateUsuario(usuarioActualizar);
                    if(respuesta == 1){
                        msg("Se a registrado correctamente");
                        inicio.setVisibility(View.GONE);
                        View header = nav.getHeaderView(0);
                        TextView nombreUsuario = (TextView) header.findViewById(R.id.menuNombreUsuario);
                        nombreUsuario.setText(nombre);
                    }else{
                        msg("Ocurrio un error, intente nuevamente");
                    }
                }
            });
        }else {
            //Codigo para poner en el Menu el nombre de usuario
            View header = nav.getHeaderView(0);
            TextView nombreUsuario = (TextView) header.findViewById(R.id.menuNombreUsuario);
            nombreUsuario.setText(usuario.getNombre());
            //Fin Codigo para poner el nombre de usuario en el menu
            hoy = (ListView)findViewById(R.id.listaHoy);
            manana = (ListView)findViewById(R.id.listaManana);
            semana = (ListView)findViewById(R.id.listaSemana);
            mes = (ListView)findViewById(R.id.listaMes);
            cargarTareas(); //LLamada a funcion para llenar las tareas
        }
        //FIn creacion y comprobacion de primera vez
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

                }else if(pos == 1){
                    Intent i = new Intent(MainActivity.this, Materias.class);
                    startActivity(i);
                }else if(pos == 2){
                    Intent i = new Intent(MainActivity.this, completadas.class);
                    startActivity(i);
                }else if(pos == 3) {
                    Intent i = new Intent(MainActivity.this, horario.class);
                    startActivity(i);
                }else if(pos == 4){
                    Intent i = new Intent(MainActivity.this, agregarTarea.class);
                    startActivity(i);
                }else if(pos == 5){
                    Intent i = new Intent(MainActivity.this, agregar_materia.class);
                    startActivity(i);
                }else if(pos == 6){
                    Intent i = new Intent(MainActivity.this, acerca.class);
                    startActivity(i);
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

    /**
     * Funcion para cambiar de ventana a la ventada de agregar tarea
     * @param view
     */
    public void agregarTarea(View view){
        Intent i = new Intent(MainActivity.this, agregarTarea.class);
        startActivity(i);
    }

    public Date sumarDiasAFecha(Date fecha, int dias){
        if (dias==0) return fecha;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }

    public static int getDayOfTheWeek(Date d){
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Funcion para llenar los listview con las tareas pendientes
     */
    public void cargarTareas(){
        tareas = db.selectTareas();
        Log.d("Hasta donde llega", "SI cargo las tareas y el tamaño es: " + tareas.size());
        if(tareas.size() > 0){
            ArrayList<ObjTarea> tareasPendientes = new ArrayList<ObjTarea>();
            for(ObjTarea t : tareas){
                if(t.getCompletado() == 0){
                    tareasPendientes.add(t);
                }
            }
            if(tareasPendientes.size() > 0){
                ArrayList<ObjTarea> tareasHoy = new ArrayList<ObjTarea>();
                ArrayList<ObjTarea> tareasManana = new ArrayList<ObjTarea>();
                ArrayList<ObjTarea> tareasSemana = new ArrayList<ObjTarea>();
                ArrayList<ObjTarea> tareasMes = new ArrayList<ObjTarea>();
                Date feAc = new Date();
                for(ObjTarea t : tareasPendientes){
                    if(t.getFechaEntrega().before(sumarDiasAFecha(feAc, 1))){
                        tareasHoy.add(t);
                    }else if(t.getFechaEntrega().after(sumarDiasAFecha(feAc, 1)) && t.getFechaEntrega().before(sumarDiasAFecha(feAc, 3))){
                        tareasManana.add(t);
                    }else if(getDayOfTheWeek(t.getFechaEntrega()) == getDayOfTheWeek(feAc)){
                        tareasSemana.add(t);
                    }else if(t.getFechaEntrega().getMonth() == feAc.getMonth()){
                        tareasMes.add(t);
                    }
                    Log.d("Veces del ciclo", "más 1 - " + t.getFechaEntrega() + " (-) " + feAc + " - " + t.getFechaEntrega().after(sumarDiasAFecha(feAc, 1)) + " (-) " +  t.getFechaEntrega().before(sumarDiasAFecha(feAc, 2)));
                }
                if(tareasHoy.size() > 0){
                    Log.d("materias hoy", tareasHoy.size() + "");
                    hoy.setAdapter(new TareasAdaptador(getApplicationContext(), tareasHoy));
                }
                if(tareasManana.size() > 0){
                    Log.d("materias mañana", tareasManana.size() + "");
                    manana.setAdapter(new TareasAdaptador(getApplicationContext(), tareasManana));
                }
                if(tareasSemana.size() > 0){
                    Log.d("materias semana", tareasSemana.size() + "");
                    semana.setAdapter(new TareasAdaptador(getApplicationContext(), tareasSemana));
                }
                if(tareasMes.size() > 0){
                    Log.d("materias Mes", tareasMes.size() + "");
                    mes.setAdapter(new TareasAdaptador(getApplicationContext(), tareasMes));
                }
            }
        }
    }

    /**
     * Funcion para crear un mensaje en pantalla
     * @param msg
     */
    public void msg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
