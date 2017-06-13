package com.claresti.mistareas.gestordetareas;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
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
    private ScrollView scrollView;
    private TextView txtHoy;
    private int flagHoy=0;
    private TextView txtMañana;
    private int flagManañana=0;
    private TextView txtSemana;
    private int flagSemana=0;
    private TextView txtMes;
    private int flagMes=0;
    private RelativeLayout principal;
    private ObjUsuario usuario;
    //Declaracion de variables para el control de bottom sheet
    private Button btnConBottomSheet;
    private LinearLayout bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        principal=(RelativeLayout)findViewById(R.id.principal);
        //Publicidad
        AdView mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //Fin publicidad
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
        db = new AdminBD(this);
        usuario = db.selectUsuario();
        //Menu, Inicia las variables del menu y llama la funcion encargada de su manipulacion
        drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        nav = (NavigationView)findViewById(R.id.navigation);
        menu = nav.getMenu();
        menuNav();
        // Fin menu
        //Llamada de las variables para el control de bottomsheet
        bottomSheet = (LinearLayout)findViewById(R.id.bottomSheet);
        final BottomSheetBehavior bsb = BottomSheetBehavior.from(bottomSheet);

        if(bd.selectUsuario().equals("0")) {
            //funcion para expandir bottomsheet en cuanto inicia la app
            bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
            bd.updateUsuario();                                         //------------Aqui iria la comprobacion para saber si hay materias
        }else {
            bsb.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        //Fin bottom  sheet
        //Control para esconder bottomsheet
        btnConBottomSheet=(Button)findViewById(R.id.btnConBottomSheet);
        btnConBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bsb.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        //Fin control para esconder bottom sheet
        //Creacion del objeto usuario y comprobacion de primera ves o no en el sistemaa
        //cambia el nombre de la base de datos y el valor de usuPrimera a 1 en caso de que sea la primera vez
        //en caso contrario solo agrega nombre del usuario al menu y carga las tareas

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
            cargarTareas();//LLamada a funcion para llenar las tareas

            //funcion para ocultar listviewHoy
            txtHoy=(TextView)findViewById(R.id.txtHoy);
            txtHoy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(flagHoy==0){
                        hoy.setVisibility(View.GONE);
                        flagHoy++;
                    }
                    else {
                        hoy.setVisibility(View.VISIBLE);
                        flagHoy--;
                    }
                }
            });

            //funcion para ocultar listviewMañana
            txtMañana=(TextView)findViewById(R.id.txtMañana);
            txtMañana.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(flagManañana==0){
                        manana.setVisibility(View.GONE);
                        flagManañana++;
                    }
                    else {
                        manana.setVisibility(View.VISIBLE);
                        flagManañana--;
                    }
                }
            });

            //funcion para ocultar listviewSemana
            txtSemana=(TextView)findViewById(R.id.txtSemana);
            txtSemana.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(flagSemana==0){
                        semana.setVisibility(View.GONE);
                        flagSemana++;
                    }
                    else {
                        semana.setVisibility(View.VISIBLE);
                        flagSemana--;
                    }
                }
            });

            //funcion para ocultar listviewMes
            txtMes=(TextView)findViewById(R.id.txtMes);
            txtMes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(flagMes==0){
                        mes.setVisibility(View.GONE);
                        flagMes++;
                    }
                    else {
                        mes.setVisibility(View.VISIBLE);
                        flagMes--;
                    }
                }
            });
        }
        //FIn creacion y comprobacion de primera vez
        Intent servicio = new Intent(MainActivity.this, ServicioNotificaciones.class);
        startService(servicio);
        NotificationManager nManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        nManager.cancel(12345);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if(extras != null){
            if (extras.containsKey("msg")) {
                msg(getIntent().getExtras().getString("msg"));
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
                Intent i = new Intent(MainActivity.this, EditarMenu.class);
                startActivity(i);
            }
        });
        //FIn bloque
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

    /**
     * Funcion que suma un dia a la fecha ingresada
     * @param fecha
     * @param dias
     * @return DATE regresa la fecha ingresada mas un dia
     */
    public Date sumarDiasAFecha(Date fecha, int dias){
        if (dias==0) return fecha;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }

    /**
     * Funcion que regresa el numero de la semana del año de una fecha
     * @param d
     * @return INT numro de la semana del año
     */
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
                    }else if(t.getFechaEntrega().after(sumarDiasAFecha(feAc, 1)) && t.getFechaEntrega().before(sumarDiasAFecha(feAc, 2))){
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
                    hoy.setAdapter(new TareasAdaptador(getApplicationContext(), tareasHoy, getFragmentManager(), MainActivity.this));
                    ajustarListView(hoy);
                }
                if(tareasManana.size() > 0){
                    Log.d("materias mañana", tareasManana.size() + "");
                    manana.setAdapter(new TareasAdaptador(getApplicationContext(), tareasManana, getFragmentManager(), MainActivity.this));
                    ajustarListView(manana);
                }
                if(tareasSemana.size() > 0){
                    Log.d("materias semana", tareasSemana.size() + "");
                    semana.setAdapter(new TareasAdaptador(getApplicationContext(), tareasSemana, getFragmentManager(), MainActivity.this));
                    ajustarListView(semana);
                }
                if(tareasMes.size() > 0){
                    Log.d("materias Mes", tareasMes.size() + "");
                    mes.setAdapter(new TareasAdaptador(getApplicationContext(), tareasMes, getFragmentManager(), MainActivity.this));
                    ajustarListView(mes);
                }
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

    /**
     * Funcion para crear un mensaje en pantalla
     * @param msg
     */
    public void msg(String msg){
        Snackbar.make(principal, msg, Snackbar.LENGTH_LONG).setAction("Aceptar", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();


    }
}
