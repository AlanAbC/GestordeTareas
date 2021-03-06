package com.claresti.mistareas.gestordetareas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class agregar_materia extends AppCompatActivity {

    //Variable del administrador de la base de datos
    private AdminBD db;
    private ObjUsuario usuario;

    //Variables de los elementos del Layout
    private EditText nombre;
    private EditText abreviacion;
    private EditText profesor;
    private Spinner spinner_colores;
    private CheckBox lunes;
    private CheckBox martes;
    private CheckBox miercoles;
    private CheckBox jueves;
    private CheckBox viernes;
    private CheckBox sabado;
    private CheckBox lunesViernes;
    private int flagLunes = 0;
    private int flagMartes = 0;
    private int flagMiercoles = 0;
    private int flagJueves = 0;
    private int flagViernes = 0;
    private int flagSabado = 0;
    private int flagLunesViernes = 0;
    private int flagSpinner;
    private RelativeLayout conLunes;
    private RelativeLayout conMartes;
    private RelativeLayout conMiercoles;
    private RelativeLayout conJueves;
    private RelativeLayout conViernes;
    private RelativeLayout conSabado;
    private RelativeLayout conLunesViernes;
    private TextView titulo;
    private EditText horLuEntrada;
    private EditText horLuSalida;
    private EditText horLuSalon;
    private EditText horMaEntrada;
    private EditText horMaSalida;
    private EditText horMaSalon;
    private EditText horMiEntrada;
    private EditText horMiSalida;
    private EditText horMiSalon;
    private EditText horJuEntrada;
    private EditText horJuSalida;
    private EditText horJuSalon;
    private EditText horViEntrada;
    private EditText horViSalida;
    private EditText horViSalon;
    private EditText horSaEntrada;
    private EditText horSaSalida;
    private EditText horSaSalon;


    //Array de colores
    private ArrayList<ObjColor> colores; //lista de objetos tipo ObjColores
    private String[] c;

    //Menu, Declaracion de variables
    private DrawerLayout drawerLayout;
    private final List<MenuItem> items = new ArrayList<>();
    private Menu menu;
    private ImageView btnMenu;
    private NavigationView nav;

    //Objeto de materia para editar
    private ObjMateria materia;
    private int flagEditar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_agregar_materia);

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
        titulo = (TextView)findViewById(R.id.txt_titulo);
        nombre = (EditText)findViewById(R.id.nombreT);
        abreviacion = (EditText)findViewById(R.id.abreviacionT);
        profesor = (EditText)findViewById(R.id.maestroT);
        spinner_colores = (Spinner)findViewById(R.id.colorS);
        llenarColores();
        lunes = (CheckBox)findViewById(R.id.checkLunes);
        conLunes = (RelativeLayout)findViewById(R.id.conLunes);
        martes = (CheckBox)findViewById(R.id.checkMartes);
        conMartes = (RelativeLayout)findViewById(R.id.conMartes);
        miercoles = (CheckBox)findViewById(R.id.checkMiercoles);
        conMiercoles = (RelativeLayout)findViewById(R.id.conMiercoles);
        jueves = (CheckBox)findViewById(R.id.checkJueves);
        conJueves = (RelativeLayout)findViewById(R.id.conJueves);
        viernes = (CheckBox)findViewById(R.id.checkViernes);
        conViernes = (RelativeLayout)findViewById(R.id.conViernes);
        sabado = (CheckBox)findViewById(R.id.checkSabado);
        conSabado = (RelativeLayout)findViewById(R.id.conSabado);
        lunesViernes = (CheckBox)findViewById(R.id.checkLunesViernes);
        conLunesViernes = (RelativeLayout)findViewById(R.id.conLunesViernes);
        horLuEntrada = (EditText) findViewById(R.id.entradaTL);
        horLuSalida = (EditText) findViewById(R.id.salidaTL);
        horLuSalon = (EditText) findViewById(R.id.salonTL);
        horMaEntrada = (EditText) findViewById(R.id.entradaTMa);
        horMaSalida = (EditText) findViewById(R.id.salidaTMa);
        horMaSalon = (EditText) findViewById(R.id.salonTMa);
        horMiEntrada = (EditText) findViewById(R.id.entradaTMi);
        horMiSalida = (EditText) findViewById(R.id.salidaTMi);
        horMiSalon = (EditText) findViewById(R.id.salonTMi);
        horJuEntrada = (EditText) findViewById(R.id.entradaTJ);
        horJuSalida = (EditText) findViewById(R.id.salidaTJ);
        horJuSalon = (EditText) findViewById(R.id.salonTJ);
        horViEntrada = (EditText) findViewById(R.id.entradaTV);
        horViSalida = (EditText) findViewById(R.id.salidaTV);
        horViSalon = (EditText) findViewById(R.id.salonTV);
        horSaEntrada = (EditText) findViewById(R.id.entradaTS);
        horSaSalida = (EditText) findViewById(R.id.salidaTS);
        horSaSalon = (EditText) findViewById(R.id.salonTS);

        // Escuchadores de los checkBox de los dias
        lunes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    conLunes.setVisibility(View.VISIBLE);
                    flagLunes = 1;
                    lunesViernes.setChecked(false);
                    conLunesViernes.setVisibility(View.GONE);
                    flagLunesViernes = 0;
                }else{
                    conLunes.setVisibility(View.GONE);
                    flagLunes = 0;
                }
            }
        });
        martes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    conMartes.setVisibility(View.VISIBLE);
                    flagMartes = 1;
                    lunesViernes.setChecked(false);
                    conLunesViernes.setVisibility(View.GONE);
                    flagLunesViernes = 0;
                }else{
                    conMartes.setVisibility(View.GONE);
                    flagMartes = 0;
                }
            }
        });
        miercoles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    conMiercoles.setVisibility(View.VISIBLE);
                    flagMiercoles = 1;
                    lunesViernes.setChecked(false);
                    conLunesViernes.setVisibility(View.GONE);
                    flagLunesViernes = 0;
                }else{
                    conMiercoles.setVisibility(View.GONE);
                    flagMiercoles = 0;
                }
            }
        });
        jueves.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    conJueves.setVisibility(View.VISIBLE);
                    flagJueves = 1;
                    lunesViernes.setChecked(false);
                    conLunesViernes.setVisibility(View.GONE);
                    flagLunesViernes = 0;
                }else{
                    conJueves.setVisibility(View.GONE);
                    flagJueves = 0;
                }
            }
        });
        viernes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    conViernes.setVisibility(View.VISIBLE);
                    flagViernes = 1;
                    lunesViernes.setChecked(false);
                    conLunesViernes.setVisibility(View.GONE);
                    flagLunesViernes = 0;
                }else{
                    conViernes.setVisibility(View.GONE);
                    flagViernes = 0;
                }
            }
        });
        sabado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    conSabado.setVisibility(View.VISIBLE);
                    flagSabado = 1;
                }else{
                    conSabado.setVisibility(View.GONE);
                    flagSabado = 0;
                }
            }
        });
        lunesViernes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    conLunesViernes.setVisibility(View.VISIBLE);
                    flagLunesViernes = 1;
                    conLunes.setVisibility(View.GONE);
                    conMartes.setVisibility(View.GONE);
                    conMiercoles.setVisibility(View.GONE);
                    conJueves.setVisibility(View.GONE);
                    conViernes.setVisibility(View.GONE);
                    flagLunes = 0;
                    flagMartes = 0;
                    flagMiercoles = 0;
                    flagJueves = 0;
                    flagViernes = 0;
                    lunes.setChecked(false);
                    martes.setChecked(false);
                    miercoles.setChecked(false);
                    jueves.setChecked(false);
                    viernes.setChecked(false);
                }else{
                    conLunesViernes.setVisibility(View.GONE);
                    flagLunesViernes = 0;
                }
            }
        });

        //Validacion si es agregar tarea y actualizar tarea
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            if (extras.containsKey("editar")) {
                // Obtencion de la tarea
                materia = ObjComunicadorMateria.getMateria();
                flagEditar = 1;
                editar();
                //msg(getIntent().getExtras().getString("msg"));
            }
        }
    }

    /**
     * Funcion encargada de rellenar los valores de la materia
     */
    private void editar() {
        titulo.setText("ACTUALIZAR MATERIA");
        nombre.setText(materia.getNombre());
        abreviacion.setText(materia.getAbv());
        profesor.setText(materia.getProfesor());
        SetSpinnerSelection(spinner_colores, c, materia.getColor().getExadecimal());
        ArrayList<ObjHorario> horario = db.selectHorariosMateria(materia);
        if(horario.size() > 0){
            for(ObjHorario h : horario){
                if(h.getDia().equals("Lunes")){
                    lunes.setChecked(true);
                    conLunes.setVisibility(View.VISIBLE);
                    horLuEntrada.setText(h.getEntrada());
                    horLuSalida.setText(h.getSalida());
                    horLuSalon.setText(h.getSalon());
                }
                if(h.getDia().equals("Martes")){
                    martes.setChecked(true);
                    conMartes.setVisibility(View.VISIBLE);
                    horMaEntrada.setText(h.getEntrada());
                    horMaSalida.setText(h.getSalida());
                    horMaSalon.setText(h.getSalon());
                }
                if(h.getDia().equals("Miercoles")){
                    miercoles.setChecked(true);
                    conMiercoles.setVisibility(View.VISIBLE);
                    horMiEntrada.setText(h.getEntrada());
                    horMiSalida.setText(h.getSalida());
                    horMiSalon.setText(h.getSalon());
                }
                if(h.getDia().equals("Jueves")){
                    jueves.setChecked(true);
                    conJueves.setVisibility(View.VISIBLE);
                    horJuEntrada.setText(h.getEntrada());
                    horJuSalida.setText(h.getSalida());
                    horJuSalon.setText(h.getSalon());
                }
                if(h.getDia().equals("Viernes")){
                    viernes.setChecked(true);
                    conViernes.setVisibility(View.VISIBLE);
                    horViEntrada.setText(h.getEntrada());
                    horViSalida.setText(h.getSalida());
                    horViSalon.setText(h.getSalon());
                }
                if(h.getDia().equals("Sabado")){
                    sabado.setChecked(true);
                    conSabado.setVisibility(View.VISIBLE);
                    horSaEntrada.setText(h.getEntrada());
                    horSaSalida.setText(h.getSalida());
                    horSaSalon.setText(h.getSalon());
                }
            }
        }
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
                    Intent i = new Intent(agregar_materia.this, Materias.class);
                    startActivity(i);
                    finish();
                }else if(pos == 2){
                    Intent i = new Intent(agregar_materia.this, completadas.class);
                    startActivity(i);
                    finish();
                }else if(pos == 3) {
                    Intent i = new Intent(agregar_materia.this, horario.class);
                    startActivity(i);
                    finish();
                }else if(pos == 4){
                    Intent i = new Intent(agregar_materia.this, agregarTarea.class);
                    startActivity(i);
                    finish();
                }else if(pos == 5){
                    Intent intent = getIntent();
                    Bundle extras = intent.getExtras();
                    if(extras != null){
                        if (extras.containsKey("editar")) {
                            Intent i = new Intent(agregar_materia.this, agregar_materia.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }else if(pos == 6){
                    Intent i = new Intent(agregar_materia.this, acerca.class);
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
                Intent i = new Intent(agregar_materia.this, EditarMenu.class);
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
     * Funcion encargada de crear el spinner de colores
     */
    public void llenarColores(){
        colores = db.selectColores();
        c = new String[colores.size()];
        for(int i = 0; i < colores.size(); i++){
            c[i] = colores.get(i).getExadecimal();
        }
        spinner_colores.setAdapter(new SpinnerAdaptador(getApplicationContext(), c));
        spinner_colores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                flagSpinner = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                flagSpinner = 0;
            }
        });
    }

    public void msg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void agregarTarea(View view) {
        if (flagEditar == 1) {
            int validacion = 0;
            if (!nombre.getText().toString().equals("")) {
                materia.setNombre(nombre.getText().toString());
                if (!abreviacion.getText().toString().equals("")) {
                    materia.setAbv(abreviacion.getText().toString());
                    if (!profesor.getText().toString().equals("")) {
                        materia.setProfesor(profesor.getText().toString());
                        materia.setColor(colores.get(flagSpinner));
                        validacion += db.updatetMateria(materia);
                        db.deleteHorario(materia.getId());
                        if (validacion == 1) {
                            if (flagLunes == 1) {
                                ObjHorario horarioLu = new ObjHorario();
                                if (horLuEntrada.getText().toString().equals("")) {
                                    msg("Ingresa hora de entrada para el dia lunes");
                                } else if (horLuSalida.getText().toString().equals("")) {
                                    msg("ingresa hora de salida para el dia lunes");
                                } else if (horLuSalon.getText().toString().equals("")) {
                                    msg("Ingresa salon para el dia lunes");
                                } else {
                                    ObjMateria mLu = db.findMateria(materia.getNombre());
                                    horarioLu.setMateria(mLu);
                                    horarioLu.setEntrada(horLuEntrada.getText().toString());
                                    horarioLu.setSalida(horLuSalida.getText().toString());
                                    horarioLu.setSalon(horLuSalon.getText().toString());
                                    horarioLu.setDia("Lunes");
                                    validacion += db.insertHorario(horarioLu);
                                }
                            }
                            if (flagMartes == 1) {
                                ObjHorario horarioMa = new ObjHorario();
                                if (horMaEntrada.getText().toString().equals("")) {
                                    msg("Ingresa hora de entrada para el dia martes");
                                } else if (horMaSalida.getText().toString().equals("")) {
                                    msg("ingresa hora de salida para el dia martes");
                                } else if (horMaSalon.getText().toString().equals("")) {
                                    msg("Ingresa salon para el dia martes");
                                } else {
                                    ObjMateria mMa = db.findMateria(materia.getNombre());
                                    horarioMa.setMateria(mMa);
                                    horarioMa.setEntrada(horMaEntrada.getText().toString());
                                    horarioMa.setSalida(horMaSalida.getText().toString());
                                    horarioMa.setSalon(horMaSalon.getText().toString());
                                    horarioMa.setDia("Martes");
                                    validacion += db.insertHorario(horarioMa);
                                }
                            }
                            if (flagMiercoles == 1) {
                                ObjHorario horarioMi = new ObjHorario();
                                if (horMiEntrada.getText().toString().equals("")) {
                                    msg("Ingresa hora de entrada para el dia miercoles");
                                } else if (horMiSalida.getText().toString().equals("")) {
                                    msg("ingresa hora de salida para el dia mirecoles");
                                } else if (horMiSalon.getText().toString().equals("")) {
                                    msg("Ingresa salon para el dia miercoles");
                                } else {
                                    ObjMateria mMi = db.findMateria(materia.getNombre());
                                    horarioMi.setMateria(mMi);
                                    horarioMi.setEntrada(horMiEntrada.getText().toString());
                                    horarioMi.setSalida(horMiSalida.getText().toString());
                                    horarioMi.setSalon(horMiSalon.getText().toString());
                                    horarioMi.setDia("Miercoles");
                                    validacion += db.insertHorario(horarioMi);
                                }
                            }
                            if (flagJueves == 1) {
                                ObjHorario horarioJu = new ObjHorario();
                                if (horJuEntrada.getText().toString().equals("")) {
                                    msg("Ingresa hora de entrada para el dia jueves");
                                } else if (horJuSalida.getText().toString().equals("")) {
                                    msg("ingresa hora de salida para el dia jueves");
                                } else if (horJuSalon.getText().toString().equals("")) {
                                    msg("Ingresa salon para el dia jueves");
                                } else {
                                    ObjMateria mJu = db.findMateria(materia.getNombre());
                                    horarioJu.setMateria(mJu);
                                    horarioJu.setEntrada(horJuEntrada.getText().toString());
                                    horarioJu.setSalida(horJuSalida.getText().toString());
                                    horarioJu.setSalon(horJuSalon.getText().toString());
                                    horarioJu.setDia("Jueves");
                                    validacion += db.insertHorario(horarioJu);
                                }
                            }
                            if (flagViernes == 1) {
                                ObjHorario horarioVi = new ObjHorario();
                                if (horViEntrada.getText().toString().equals("")) {
                                    msg("Ingresa hora de entrada para el dia viernes");
                                } else if (horViSalida.getText().toString().equals("")) {
                                    msg("ingresa hora de salida para el dia viernes");
                                } else if (horViSalon.getText().toString().equals("")) {
                                    msg("Ingresa salon para el dia viernes");
                                } else {
                                    ObjMateria mVi = db.findMateria(materia.getNombre());
                                    horarioVi.setMateria(mVi);
                                    horarioVi.setEntrada(horViEntrada.getText().toString());
                                    horarioVi.setSalida(horViSalida.getText().toString());
                                    horarioVi.setSalon(horViSalon.getText().toString());
                                    horarioVi.setDia("Viernes");
                                    validacion += db.insertHorario(horarioVi);
                                }
                            }
                            if (flagSabado == 1) {
                                ObjHorario horarioSa = new ObjHorario();
                                if (horSaEntrada.getText().toString().equals("")) {
                                    msg("Ingresa hora de entrada para el dia sabado");
                                } else if (horSaSalida.getText().toString().equals("")) {
                                    msg("ingresa hora de salida para el dia sabado");
                                } else if (horSaSalon.getText().toString().equals("")) {
                                    msg("Ingresa salon para el dia sabado");
                                } else {
                                    ObjMateria mSa = db.findMateria(materia.getNombre());
                                    horarioSa.setMateria(mSa);
                                    horarioSa.setEntrada(horSaEntrada.getText().toString());
                                    horarioSa.setSalida(horSaSalida.getText().toString());
                                    horarioSa.setSalon(horSaSalon.getText().toString());
                                    horarioSa.setDia("Sabado");
                                    validacion += db.insertHorario(horarioSa);
                                }
                            }
                            if (flagLunesViernes == 1) {
                                ObjHorario horarioSa = new ObjHorario();
                                EditText horLVEntrada = (EditText) findViewById(R.id.entradaTLV);
                                EditText horLVSalida = (EditText) findViewById(R.id.salidaTLV);
                                EditText horLVSalon = (EditText) findViewById(R.id.salonTLV);
                                if (horLVEntrada.getText().toString().equals("")) {
                                    msg("Ingresa hora de entrada para el horario de lunes a viernes");
                                } else if (horLVSalida.getText().toString().equals("")) {
                                    msg("ingresa hora de salida para el horario de lunes a viernes");
                                } else if (horLVSalon.getText().toString().equals("")) {
                                    msg("Ingresa salon para el horario de lunes a viernes");
                                } else {
                                    ObjMateria mLV = db.findMateria(materia.getNombre());
                                    horarioSa.setMateria(mLV);
                                    horarioSa.setEntrada(horLVEntrada.getText().toString());
                                    horarioSa.setSalida(horLVSalida.getText().toString());
                                    horarioSa.setSalon(horLVSalon.getText().toString());
                                    horarioSa.setDia("Lunes");
                                    validacion += db.insertHorario(horarioSa);
                                    horarioSa.setDia("Martes");
                                    db.insertHorario(horarioSa);
                                    horarioSa.setDia("Miercoles");
                                    db.insertHorario(horarioSa);
                                    horarioSa.setDia("Jueves");
                                    db.insertHorario(horarioSa);
                                    horarioSa.setDia("Viernes");
                                    db.insertHorario(horarioSa);
                                }
                            }
                            Log.d("valor validacion", validacion + " - " + (1 + flagLunes + flagMartes + flagMiercoles + flagJueves + flagViernes + flagSabado));
                            if (validacion == (1 + flagLunes + flagMartes + flagMiercoles + flagJueves + flagViernes + flagSabado + flagLunesViernes)) {
                                msg("Se actualizo correctamente :)");
                                Intent i = new Intent(agregar_materia.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                msg("Ocurrio un error intentelo de nuevo");
                            }
                        } else {
                            msg("Ocurrio un error intentelo de nuevo");
                        }
                    } else {
                        msg("Ingresa el masestro que imparte la materia");
                    }
                } else {
                    msg("Ingresa una abreviacion a la materia");
                }
            } else {
                msg("Ingresa un nombre a la materia");
            }
        } else {
            ObjMateria nuevaMateria = new ObjMateria();
            int validacion = 0;
            if (!nombre.getText().toString().equals("")) {
                nuevaMateria.setNombre(nombre.getText().toString());
                if (!abreviacion.getText().toString().equals("")) {
                    nuevaMateria.setAbv(abreviacion.getText().toString());
                    if (!profesor.getText().toString().equals("")) {
                        nuevaMateria.setProfesor(profesor.getText().toString());
                        nuevaMateria.setColor(colores.get(flagSpinner));
                        validacion += db.insertMateria(nuevaMateria);
                        if (validacion == 1) {
                            if (flagLunes == 1) {
                                ObjHorario horarioLu = new ObjHorario();
                                if (horLuEntrada.getText().toString().equals("")) {
                                    msg("Ingresa hora de entrada para el dia lunes");
                                } else if (horLuSalida.getText().toString().equals("")) {
                                    msg("ingresa hora de salida para el dia lunes");
                                } else if (horLuSalon.getText().toString().equals("")) {
                                    msg("Ingresa salon para el dia lunes");
                                } else {
                                    ObjMateria mLu = db.findMateria(nuevaMateria.getNombre());
                                    horarioLu.setMateria(mLu);
                                    horarioLu.setEntrada(horLuEntrada.getText().toString());
                                    horarioLu.setSalida(horLuSalida.getText().toString());
                                    horarioLu.setSalon(horLuSalon.getText().toString());
                                    horarioLu.setDia("Lunes");
                                    validacion += db.insertHorario(horarioLu);
                                }
                            }
                            if (flagMartes == 1) {
                                ObjHorario horarioMa = new ObjHorario();
                                if (horMaEntrada.getText().toString().equals("")) {
                                    msg("Ingresa hora de entrada para el dia martes");
                                } else if (horMaSalida.getText().toString().equals("")) {
                                    msg("ingresa hora de salida para el dia martes");
                                } else if (horMaSalon.getText().toString().equals("")) {
                                    msg("Ingresa salon para el dia martes");
                                } else {
                                    ObjMateria mMa = db.findMateria(nuevaMateria.getNombre());
                                    horarioMa.setMateria(mMa);
                                    horarioMa.setEntrada(horMaEntrada.getText().toString());
                                    horarioMa.setSalida(horMaSalida.getText().toString());
                                    horarioMa.setSalon(horMaSalon.getText().toString());
                                    horarioMa.setDia("Martes");
                                    validacion += db.insertHorario(horarioMa);
                                }
                            }
                            if (flagMiercoles == 1) {
                                ObjHorario horarioMi = new ObjHorario();
                                if (horMiEntrada.getText().toString().equals("")) {
                                    msg("Ingresa hora de entrada para el dia miercoles");
                                } else if (horMiSalida.getText().toString().equals("")) {
                                    msg("ingresa hora de salida para el dia mirecoles");
                                } else if (horMiSalon.getText().toString().equals("")) {
                                    msg("Ingresa salon para el dia miercoles");
                                } else {
                                    ObjMateria mMi = db.findMateria(nuevaMateria.getNombre());
                                    horarioMi.setMateria(mMi);
                                    horarioMi.setEntrada(horMiEntrada.getText().toString());
                                    horarioMi.setSalida(horMiSalida.getText().toString());
                                    horarioMi.setSalon(horMiSalon.getText().toString());
                                    horarioMi.setDia("Miercoles");
                                    validacion += db.insertHorario(horarioMi);
                                }
                            }
                            if (flagJueves == 1) {
                                ObjHorario horarioJu = new ObjHorario();
                                if (horJuEntrada.getText().toString().equals("")) {
                                    msg("Ingresa hora de entrada para el dia jueves");
                                } else if (horJuSalida.getText().toString().equals("")) {
                                    msg("ingresa hora de salida para el dia jueves");
                                } else if (horJuSalon.getText().toString().equals("")) {
                                    msg("Ingresa salon para el dia jueves");
                                } else {
                                    ObjMateria mJu = db.findMateria(nuevaMateria.getNombre());
                                    horarioJu.setMateria(mJu);
                                    horarioJu.setEntrada(horJuEntrada.getText().toString());
                                    horarioJu.setSalida(horJuSalida.getText().toString());
                                    horarioJu.setSalon(horJuSalon.getText().toString());
                                    horarioJu.setDia("Jueves");
                                    validacion += db.insertHorario(horarioJu);
                                }
                            }
                            if (flagViernes == 1) {
                                ObjHorario horarioVi = new ObjHorario();
                                if (horViEntrada.getText().toString().equals("")) {
                                    msg("Ingresa hora de entrada para el dia viernes");
                                } else if (horViSalida.getText().toString().equals("")) {
                                    msg("ingresa hora de salida para el dia viernes");
                                } else if (horViSalon.getText().toString().equals("")) {
                                    msg("Ingresa salon para el dia viernes");
                                } else {
                                    ObjMateria mVi = db.findMateria(nuevaMateria.getNombre());
                                    horarioVi.setMateria(mVi);
                                    horarioVi.setEntrada(horViEntrada.getText().toString());
                                    horarioVi.setSalida(horViSalida.getText().toString());
                                    horarioVi.setSalon(horViSalon.getText().toString());
                                    horarioVi.setDia("Viernes");
                                    validacion += db.insertHorario(horarioVi);
                                }
                            }
                            if (flagSabado == 1) {
                                ObjHorario horarioSa = new ObjHorario();
                                if (horSaEntrada.getText().toString().equals("")) {
                                    msg("Ingresa hora de entrada para el dia sabado");
                                } else if (horSaSalida.getText().toString().equals("")) {
                                    msg("ingresa hora de salida para el dia sabado");
                                } else if (horSaSalon.getText().toString().equals("")) {
                                    msg("Ingresa salon para el dia sabado");
                                } else {
                                    ObjMateria mSa = db.findMateria(nuevaMateria.getNombre());
                                    horarioSa.setMateria(mSa);
                                    horarioSa.setEntrada(horSaEntrada.getText().toString());
                                    horarioSa.setSalida(horSaSalida.getText().toString());
                                    horarioSa.setSalon(horSaSalon.getText().toString());
                                    horarioSa.setDia("Sabado");
                                    validacion += db.insertHorario(horarioSa);
                                }
                            }
                            if (flagLunesViernes == 1) {
                                ObjHorario horarioSa = new ObjHorario();
                                EditText horLVEntrada = (EditText) findViewById(R.id.entradaTLV);
                                EditText horLVSalida = (EditText) findViewById(R.id.salidaTLV);
                                EditText horLVSalon = (EditText) findViewById(R.id.salonTLV);
                                if (horLVEntrada.getText().toString().equals("")) {
                                    msg("Ingresa hora de entrada para el horario de lunes a viernes");
                                } else if (horLVSalida.getText().toString().equals("")) {
                                    msg("ingresa hora de salida para el horario de lunes a viernes");
                                } else if (horLVSalon.getText().toString().equals("")) {
                                    msg("Ingresa salon para el horario de lunes a viernes");
                                } else {
                                    ObjMateria mLV = db.findMateria(nuevaMateria.getNombre());
                                    horarioSa.setMateria(mLV);
                                    horarioSa.setEntrada(horLVEntrada.getText().toString());
                                    horarioSa.setSalida(horLVSalida.getText().toString());
                                    horarioSa.setSalon(horLVSalon.getText().toString());
                                    horarioSa.setDia("Lunes");
                                    validacion += db.insertHorario(horarioSa);
                                    horarioSa.setDia("Martes");
                                    db.insertHorario(horarioSa);
                                    horarioSa.setDia("Miercoles");
                                    db.insertHorario(horarioSa);
                                    horarioSa.setDia("Jueves");
                                    db.insertHorario(horarioSa);
                                    horarioSa.setDia("Viernes");
                                    db.insertHorario(horarioSa);
                                }
                            }
                            Log.d("valor validacion", validacion + " - " + (1 + flagLunes + flagMartes + flagMiercoles + flagJueves + flagViernes + flagSabado));
                            if (validacion == (1 + flagLunes + flagMartes + flagMiercoles + flagJueves + flagViernes + flagSabado + flagLunesViernes)) {
                                msg("Se registro correctamente :)");
                                Intent i = new Intent(agregar_materia.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                ObjMateria mFlag = db.findMateria(nuevaMateria.getNombre());
                                db.deleteMateria(mFlag.getId());
                            }
                        } else {
                            msg("Ocurrio un error intentelo de nuevo");
                        }
                    } else {
                        msg("Ingresa el masestro que imparte la materia");
                    }
                } else {
                    msg("Ingresa una abreviacion a la materia");
                }
            } else {
                msg("Ingresa un nombre a la materia");
            }
        }
    }
}
