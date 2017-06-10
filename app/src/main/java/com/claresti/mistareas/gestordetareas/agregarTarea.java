package com.claresti.mistareas.gestordetareas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class agregarTarea extends AppCompatActivity {

    //Variable del administrador de la base de datos
    private AdminBD db;
    private ObjUsuario usuario;

    //Menu, Declaracion de variables
    private DrawerLayout drawerLayout;
    final List<MenuItem> items = new ArrayList<>();
    private Menu menu;
    private RelativeLayout principal;
    private ImageView btnMenu;
    private NavigationView nav;

    //Declaracion de variables del layout
    private EditText nombre;
    private Spinner spinnerMaterias;
    private DatePicker fechaEntrega;
    private EditText descripcion;
    private TextView titulo;
    private int materiaSeleccionada = 0;
    private ArrayList<ObjMateria> materias;
    private String[] nombreMaterias;
    private ObjTarea tarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_agregar_tarea);
        principal =(RelativeLayout)findViewById(R.id.principal);

        //Cambiar el color en la barra de notificaciones (Solo funciona de lollipop hacia arriba)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.agregar));
        }

        //Codigo para crear el objeto de la base de datos y
        //agregar el nombre de usuario al menu
        db = new AdminBD(this);
        usuario = db.selectUsuario();

        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        menuNav();

        //Codigo para poner en el Menu el nombre de usuario
        View header = nav.getHeaderView(0);
        TextView nombreUsuario = (TextView) header.findViewById(R.id.menuNombreUsuario);
        nombreUsuario.setText(usuario.getNombre());

        //Asignacion de variables del layout
        nombre = (EditText)findViewById(R.id.nombreT);
        spinnerMaterias  =(Spinner)findViewById(R.id.materiaS);
        fechaEntrega = (DatePicker)findViewById(R.id.fechaS);
        descripcion = (EditText)findViewById(R.id.descripcionT);
        titulo = (TextView)findViewById(R.id.txt_titulo);
        llenarMaterias();

        //Validacion si es agregar tarea y actualizar tarea
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            if (extras.containsKey("editar")) {
                // Obtencion de la tarea
                tarea = ObjComunicadorTarea.getTarea();
                editar();
                //msg(getIntent().getExtras().getString("msg"));
            }
        }
    }

    /**
     * Funcion encargada de llenar los valores del layout con la informacion de la tarea
     */
    private void editar() {

        // LLenado de los valores en el layout
        titulo.setText("ACTUALIZAR TAREA");
        nombre.setText(tarea.getNombre());
        descripcion.setText(tarea.getDescripcion());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tarea.getFechaEntrega());
        fechaEntrega.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        SetSpinnerSelection(spinnerMaterias, nombreMaterias, tarea.getMateria().getNombre());
    }

    /**
     * Funcion encargada de seleccionar un item del spinner comparado con un STring
     * @param spinner spinner al que se le quiere seleccionar
     * @param array array de objetos que se ingreso en el espiner
     * @param text texto el cual se quiere buscar en el espinner
     */
    public void SetSpinnerSelection(Spinner spinner,String[] array,String text) {
        for(int i=0;i<array.length;i++) {
            if(array[i].equals(text)) {
                spinner.setSelection(i);
            }
        }
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
                    Intent i = new Intent(agregarTarea.this, Materias.class);
                    startActivity(i);
                    finish();
                }else if(pos == 2){
                    Intent i = new Intent(agregarTarea.this, completadas.class);
                    startActivity(i);
                    finish();
                }else if(pos == 3) {
                    Intent i = new Intent(agregarTarea.this, horario.class);
                    startActivity(i);
                    finish();
                }else if(pos == 4){
                    //Validacion si es agregar tarea y actualizar tarea
                    Intent intent = getIntent();
                    Bundle extras = intent.getExtras();
                    if(extras != null){
                        if (extras.containsKey("editar")) {
                            // Obtencion de la tarea
                            Intent i = new Intent(agregarTarea.this, agregarTarea.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }else if(pos == 5){
                    Intent i = new Intent(agregarTarea.this, agregar_materia.class);
                    startActivity(i);
                    finish();
                }else if(pos == 6){
                    Intent i = new Intent(agregarTarea.this, acerca.class);
                    startActivity(i);
                    finish();
                }
                drawerLayout.closeDrawer(nav);
                item.setChecked(false);
                return false;
            }
        });
        //Bloque de codigo que da funcionalidad al boton de editar del header del menu
        View headerview = nav.getHeaderView(0);
        ImageView editar = (ImageView)headerview.findViewById(R.id.editar);
        RelativeLayout imgFondo = (RelativeLayout)headerview.findViewById(R.id.l_imgFondo);
        if(usuario.getImg().equals("imgmenu")){
            imgFondo.setBackgroundResource(R.drawable.imgmenu);
        }else{
            Uri path = Uri.fromFile(new File(usuario.getImg()));
            Bitmap bitmap = BitmapFactory.decodeFile(usuario.getImg());
            BitmapDrawable bdrawable = new BitmapDrawable(getApplicationContext().getResources(),bitmap);
            imgFondo.setBackground(bdrawable);
        }
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(agregarTarea.this, EditarMenu.class);
                startActivity(i);
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
     * Funcion encargada de llenar el espiner de las materias
     */
    public void llenarMaterias(){
        materias = db.selectMaterias();
        if(materias.size() > 0){
            nombreMaterias = new String[materias.size()];
            for(int i = 0; i < materias.size(); i++){
                nombreMaterias[i] = materias.get(i).getNombre();
            }
            ArrayAdapter adaptadorMaterias = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_materias, nombreMaterias);
            spinnerMaterias.setAdapter(adaptadorMaterias);

            spinnerMaterias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    materiaSeleccionada = position;
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#a1a1a1"));//con  esta linea se cambia el color del texto pero no se como cambiarlo a todosactualmente solo cambia el del elemento seleccionado
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    /**
     * Funcion encargada de generar mensajes en pantalla
     * @param msg mensaje a mostrar
     */
    public void msg(String msg){
        Snackbar.make(principal, msg, Snackbar.LENGTH_SHORT).show();

    }

    /**
     * Funcion encargada de agregar o actualizar una tarea
     * @param view
     */
    public void agregarTarea(View view) {
        //Validacion si es agregar tarea y actualizar tarea
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("editar")) {
                if (!nombre.getText().toString().equals("")) {
                    String f = fechaEntrega.getYear() + "/" + (fechaEntrega.getMonth() + 1) + "/" + fechaEntrega.getDayOfMonth() + " 23:59";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    Date fe = sdf.parse(f, new ParsePosition(0));
                    Date feAc = new Date();
                    if (fe.after(feAc)) {
                        if (!descripcion.getText().toString().equals("")) {
                            ObjTarea tareaNueva = new ObjTarea();
                            tareaNueva.setMateria(materias.get(materiaSeleccionada));
                            tareaNueva.setCompletado(0);
                            tareaNueva.setDescripcion(descripcion.getText().toString());
                            tareaNueva.setFechaCreacion(feAc);
                            tareaNueva.setFechaEntrega(fe);
                            tareaNueva.setNombre(nombre.getText().toString());
                            tareaNueva.setId(tarea.getId());
                            int flag = db.updateTarea(tareaNueva);
                            if (flag == 1) {
                                Intent i = new Intent(agregarTarea.this, MainActivity.class);
                                i.putExtra("msg", "Se actualizó correctamente la tarea");
                                startActivity(i);
                                finish();
                            } else {
                                msg("Ocurrio un erro vuelva a intentar");
                            }
                        } else {
                            msg("Ingresa una descripcion de la tarea");
                        }
                    } else {
                        msg("No puedes ingresar una fecha que ya paso");
                    }
                } else {
                    msg("Ingres un nombre a la tarea");
                }
            }
        } else {
            if (materias.size() > 0) {
                if (!nombre.getText().toString().equals("")) {
                    String f = fechaEntrega.getYear() + "/" + (fechaEntrega.getMonth() + 1) + "/" + fechaEntrega.getDayOfMonth() + " 23:59";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    Date fe = sdf.parse(f, new ParsePosition(0));
                    Date feAc = new Date();
                    if (fe.after(feAc)) {
                        if (!descripcion.getText().toString().equals("")) {
                            ObjTarea tareaNueva = new ObjTarea();
                            tareaNueva.setMateria(materias.get(materiaSeleccionada));
                            tareaNueva.setCompletado(0);
                            tareaNueva.setDescripcion(descripcion.getText().toString());
                            tareaNueva.setFechaCreacion(feAc);
                            tareaNueva.setFechaEntrega(fe);
                            tareaNueva.setNombre(nombre.getText().toString());
                            int flag = db.insertTarea(tareaNueva);
                            if (flag == 1) {
                                Intent i = new Intent(agregarTarea.this, MainActivity.class);
                                i.putExtra("msg", "Tarea creada correctamente");
                                startActivity(i);
                                finish();
                            } else {
                                msg("Ocurrio un erro vuelva a intentar");
                            }
                        } else {
                            msg("Ingresa una descripcion de la tarea");
                        }
                    } else {
                        msg("No puedes ingresar una fecha que ya paso");
                    }
                } else {
                    msg("Ingres un nombre a la tarea");
                }
            } else {
                msg("No puedes agregar una tarea porque no tienes materias");
            }
        }
    }
}
