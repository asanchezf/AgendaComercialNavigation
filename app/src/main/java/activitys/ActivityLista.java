package activitys;

/**
 * Created by Susana on 13/08/2015.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.videumcorp.desarrolladorandroid.navigatio.R;

import java.sql.SQLException;
import java.util.ArrayList;

import Beans.Contactos;
import controlador.SQLControlador;

import static android.widget.SearchView.OnQueryTextListener;

//import antonio.ejemplos.agendacomercial.R;


public class ActivityLista extends AppCompatActivity implements OnQueryTextListener, SearchView.OnQueryTextListener,MenuItemCompat.OnActionExpandListener {// -EXTENDS DE LISTACTIVITY---MODIFICACION-1..UPV

    private ListView lista;// OBJETO LISTVIEW
    private int index;//Para preservar el scroll de la listview
    private SQLControlador dbConnection;//CONTIENE LAS CONEXIONES A BBDD (CREADA EN DBHELPER.CLASS) Y LOS M�TODOS INSERT, UPDATE, DELETE, BUSCAR....
    private ArrayList<Contactos> contactos;//COLECCION DE TIPO CONTACTOS (BEAN CON LA MISMA ESTRUTURA DE CAMPOS QUE LA BBDD)

    //ADAPTADORES....===========================================================
   // ArrayAdapter<Contactos> adaptador;//Primer adaptador utilizado. EJEMPLO 1
    //private ContactosAdapter contactosAdapter;// Segundo adaptador utilizado.Ejemplo sin im�genes..
    //private ContactosAdapter_old contactosAdapter_Jarroba;// Ejemplo con im�genes.


    ////////////-------------///////////////////////
    //private ContactosAdapter_Imagenes contactosAdapter_imagenes;

    private Contacts_Adapter_Images_Search contactosAdapter_imagenes;
    private int totalRegistros;
    private String totales;
    private TextView txtTotales;

    ///////////////------------------////////////////





    //FIN ADAPTADORES....======================================================


    //CONSTANTES PARA EL MODO FORMULARIO Y PARA LOS TIPOS DE LLAMADA.============================
    public static final String C_MODO = "modo";
    public static final int C_VISUALIZAR = 551;
    public static final int C_CREAR = 552;
    public static final int C_EDITAR = 553;
    public static final int C_ELIMINAR = 554;
    public static final int C_CALL = 555;
    //FIN CONSTANTES==============================================================================

    //Añadimos la toolbar
    private Toolbar toolbar;

    //Para el buscador
    private int textlength = 0;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);//--MODIFICACION
        setContentView(R.layout.inicio);//Contiene un Listview y una caja d texto para la salida sin datos. UTILIZADO EN UPV
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        lista = (ListView) findViewById(android.R.id.list);// -----MODIFICACION-2
         //lista = (ListView) findViewById(R.id.list);


        txtTotales=(TextView)findViewById(R.id.text3);

        //Añadimos la toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //La acitivity debe extender de AppCompatActivity para poder hacer el seteo a ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        ActivityLista.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }


        // Permite al ListView mostrar un men� contextual
        //registerForContextMenu(this.getListView());// -----MODIFICACION-3 VER...
        //Forma de asociar el menú contextual...Pulsación prolongada...
        registerForContextMenu(lista);

        //Activar filtrado desde el teclado
        //lista.getListView();
        //lista.setTextFilterEnabled(true);


        // registerForContextMenu(lista);No funciona si la Activity no extends de ListActivity


//==============RESUMEN DE LOS DISTINTOS ADAPTADORES QUE SE HAN PROBADO===========================

//==============FIN RESUMEN DE LOS DISTINTOS ADAPTADORES QUE SE HAN PROBADO=======================================
        //searchView = (SearchView) findViewById(R.id.search_view);

        lista.setTextFilterEnabled(true);

        consultar();


        // Seleccionamos un elemento de la lista y lo vemos completo en otra Activity
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view,int posicion, long id) {// int posicion, long id

                // Solo hace falta si llamamos al m�todo que devuelve una Colleccion

                try {

                    dbConnection = new SQLControlador(getApplicationContext());
                    dbConnection.abrirBaseDeDatos(1);// Lectura. Solo para ver

                    // Devuelve una Colecci�n.Problema en el orden de recogida
                    // de los campos
                    // Se cambia por otro m�todo que devuelve un cursor...
                    Cursor c = dbConnection.CursorBuscarUno(id);// Devuelve un Cursor

                    int _id= c.getInt(c.getColumnIndex("_id"));


                    String nombre = c.getString(c.getColumnIndex("Nombre"));
                    String apellidos = c.getString(c
                            .getColumnIndex("Apellidos"));
                    String direccion = c.getString(c
                            .getColumnIndex("Direccion"));
                    String telefono = c.getString(c.getColumnIndex("Telefono"));
                    String email = c.getString(c.getColumnIndex("Email"));

                    int categoria=c.getInt(c.getColumnIndex("Id_Categoria"));
                    String observ = c.getString(c.getColumnIndex("Observaciones"));

                    // txtCaja2.setText("" + posicion + " posicion");

                    dbConnection.cerrar();

                    // Pasamos datos al formulario en modo visualizar
                    Intent i = new Intent(ActivityLista.this,ModificarUsuarios.class);
                    i.putExtra("Nombre", nombre);
                    i.putExtra("Apellidos", apellidos);
                    i.putExtra("Direccion", direccion);
                    i.putExtra("Telefono", telefono);
                    i.putExtra("Email", email);

                    i.putExtra("Id_Categoria", categoria);
                    i.putExtra("Observaciones", observ);

                    i.putExtra(C_MODO, C_VISUALIZAR);
                    startActivityForResult(i, C_VISUALIZAR);

                    // startActivity(i);

                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

    }
    @Override
    protected void onStart() {
    	/*
    	 * Nos indica que la actividad est� a punto de ser mostrada al usuario.*/
        super.onStart();
        //Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();

    }



    @Override
    protected void onResume() {
    		/*
    		 Se llama cuando la actividad va a comenzar a interactuar con el usuario.
    		 Es un buen lugar para lanzar las animaciones y la m�sica.
    		 *
    		 * */

        super.onResume();

        //mp.start();

        //Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();

        //Para preserver el scroll del listview
        if(lista != null) {
            if (lista.getCount() > index) lista.setSelectionFromTop(index, 0);
            else lista.setSelectionFromTop(0, 0);
        }
    }



    @Override
    protected void onPause() {
    	/*
    	 * Indica que la actividad est� a punto de ser lanzada a segundo plano, normalmente porque otra actividad es lanzada.
    	 * Es el lugar adecuado para detener animaciones, m�sica o almacenar los datos que estaban en edici�n.
    	 *
    	 * */
        //mp.stop();

        //Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        super.onPause();


        //Para preserver el scroll del listView
        index = lista.getFirstVisiblePosition(); // store index using shared preferences
    }



    @Override
    protected void onStop() {
    		/*
    		 *  La actividad ya no va a ser visible para el usuario. Ojo si hay muy poca memoria,
    		 *  es posible que la actividad se destruya sin llamar a este m�todo
    		 * */
        super.onStop();
        //Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onRestart() {
    		/*
    		 *  Indica que la actividad va a volver a ser representada despu�s de haber pasado por onStop().*/

        super.onRestart();
        //Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();

        consultar();

    }



    @Override
    protected void onDestroy() {
    		/*
    		 * Se llama antes de que la actividad sea totalmente destruida. Por ejemplo, cuando el usuario pulsa
    		 * el bot�n <volver> o cuando se llama al m�todo finish(). Ojo si hay muy poca memoria, es posible
    		 * que la actividad se destruya sin llamar a este m�todo.
    		 *
    		 * */

        super.onDestroy();
        //Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }



    private void visualizar(long id) throws SQLException {
        dbConnection = new SQLControlador(getApplicationContext());
        dbConnection.abrirBaseDeDatos(1);// Lectura. Solo para ver

        Cursor c = dbConnection.CursorBuscarUno(id);// Devuelve un Cursor

        // int _id= c.getInt(c.getColumnIndex("_id"));
        String nombre = c.getString(c.getColumnIndex("Nombre"));
        String apellidos = c.getString(c.getColumnIndex("Apellidos"));
        String direccion = c.getString(c.getColumnIndex("Direccion"));
        String telefono = c.getString(c.getColumnIndex("Telefono"));
        String email = c.getString(c.getColumnIndex("Email"));

        int categoria=c.getInt(c.getColumnIndex("Id_Categoria"));
        String observ = c.getString(c.getColumnIndex("Observaciones"));

        // txtCaja2.setText("" + posicion + " posicion");
        dbConnection.cerrar();

        // Pasamos datos al formulario en modo visualizar
        Intent i = new Intent(ActivityLista.this, ModificarUsuarios.class);
        i.putExtra("Nombre", nombre);
        i.putExtra("Apellidos", apellidos);
        i.putExtra("Direccion", direccion);
        i.putExtra("Telefono", telefono);
        i.putExtra("Email", email);

        i.putExtra("Id_Categoria", categoria);
        i.putExtra("Observaciones", observ);

        i.putExtra(C_MODO, C_VISUALIZAR);
        startActivityForResult(i, C_VISUALIZAR);


        //startActivity(i);

    }


    private void call(long id) throws SQLException {
        dbConnection = new SQLControlador(getApplicationContext());
        dbConnection.abrirBaseDeDatos(1);// Lectura. Solo para ver

        Cursor c = dbConnection.CursorBuscarUno(id);// Devuelve un Cursor

        // int _id= c.getInt(c.getColumnIndex("_id"));
        String nombre = c.getString(c.getColumnIndex("Nombre"));
        String apellidos = c.getString(c.getColumnIndex("Apellidos"));
        String direccion = c.getString(c.getColumnIndex("Direccion"));
        String telefono = c.getString(c.getColumnIndex("Telefono"));
        String email = c.getString(c.getColumnIndex("Email"));

        // txtCaja2.setText("" + posicion + " posicion");
        dbConnection.cerrar();


        //ACTION_DIAL NO necesita permisos en el Manifiest. Marca y sale ventana de confirmaci�n...
        //Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + telefono));

        //ACTION_CALL necesita permisos en el Manifiest. Marca directamente...
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telefono));
        startActivity(intent);

    }


    private void borrar(final long id) {
		/*
		 * Borramos el registro y refrescamos la lista
		 */
        AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);

        dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
        dialogEliminar.setTitle(getResources().getString(
                R.string.agenda_eliminar_titulo));
        dialogEliminar.setMessage(getResources().getString(
                R.string.agenda_eliminar_mensaje));
        dialogEliminar.setCancelable(false);

        dialogEliminar.setPositiveButton(
                getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int boton) {
                        // dbAdapter.delete(id);

                        dbConnection = new SQLControlador(
                                getApplicationContext());
                        try {
                            dbConnection.abrirBaseDeDatos(2);
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }// Escritura. Borrar

                        dbConnection.delete(id);

                        Toast.makeText(ActivityLista.this,
                                R.string.agenda_eliminar_confirmacion,
                                Toast.LENGTH_SHORT).show();
                        dbConnection.cerrar();
                        consultar();
                    }
                });

        dialogEliminar.setNegativeButton(android.R.string.no, null);

        dialogEliminar.show();
    }

    //Va a ser llaamado desde fuera por eso es público.
    public  void borrarTodos() {
		/*
		 * Borramos el registro y refrescamos la lista
		 */
        AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);

        dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
        dialogEliminar.setTitle(getResources().getString(
                R.string.agenda_eliminar_todos_titulo));
        dialogEliminar.setMessage(getResources().getString(
                R.string.agenda_eliminar_todos_mensaje));
        dialogEliminar.setCancelable(false);

        dialogEliminar.setPositiveButton(
                getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int boton) {
                        // dbAdapter.delete(id);

                        dbConnection = new SQLControlador(
                                getApplicationContext());
                        try {
                            dbConnection.abrirBaseDeDatos(2);
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }// Escritura. Borrar

                        dbConnection.borrarTodos();

                        Toast.makeText(ActivityLista.this,
                                R.string.agenda_eliminar_todos_confirmacion,
                                Toast.LENGTH_SHORT).show();
                        dbConnection.cerrar();
                        consultar();
                    }
                });

        dialogEliminar.setNegativeButton(android.R.string.no, null);

        dialogEliminar.show();




    }

    private void consultar()  {

         String concatena_numero_registros;
        dbConnection = new SQLControlador(getApplicationContext());
        try {
            dbConnection.abrirBaseDeDatos(1);//Modo lectura
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        contactos = dbConnection.BuscarTodos();// llamamos a BuscarTodos() que devuelve un arraylist de contactos...
        contactosAdapter_imagenes = new Contacts_Adapter_Images_Search(this,contactos);

        //int total=contactos.size();

        //lista.setAdapter( new ContactosAdapter_Imagenes(this,contactos));

         concatena_numero_registros=getResources().getString(R.string.concatenar_numero_registros);//DEvuelve HAY para concatenar y  mostrar el número total de registros...
        totalRegistros=  contactosAdapter_imagenes.getCount();

        //double euros
        // euros=Double.parseDouble(txtEuros.getText());
         totales= String.valueOf(totalRegistros);



        if(totalRegistros>0) {
            txtTotales.setText(concatena_numero_registros+ " " + totales + " " + getResources().getString(R.string.titulo_activity_lista));
        }

        else{
            txtTotales.setText(getResources().getString(R.string.no_hay_registros));
        }
        lista.setAdapter(contactosAdapter_imagenes);

        dbConnection.cerrar();
    }

    private void editar(long id) throws SQLException {
        dbConnection = new SQLControlador(getApplicationContext());
        dbConnection.abrirBaseDeDatos(1);// Lectura. Solo para ver

        Cursor c = dbConnection.CursorBuscarUno(id);// Devuelve un Cursor

        int idenviado= c.getInt(c.getColumnIndex("_id"));
        String nombre = c.getString(c.getColumnIndex("Nombre"));
        String apellidos = c.getString(c.getColumnIndex("Apellidos"));
        String direccion = c.getString(c.getColumnIndex("Direccion"));
        String telefono = c.getString(c.getColumnIndex("Telefono"));
        String email = c.getString(c.getColumnIndex("Email"));

        int Id_Categ=c.getInt(c.getColumnIndex("Id_Categoria"));
        String observ = c.getString(c.getColumnIndex("Observaciones"));

        // txtCaja2.setText("" + posicion + " posicion");
        dbConnection.cerrar();

        // Pasamos datos al formulario en modo visualizar
        Intent i = new Intent(ActivityLista.this, ModificarUsuarios.class);
        i.putExtra("_id", idenviado);
        i.putExtra("Nombre", nombre);
        i.putExtra("Apellidos", apellidos);
        i.putExtra("Direccion", direccion);
        i.putExtra("Telefono", telefono);
        i.putExtra("Email", email);

        i.putExtra("Id_Categoria", Id_Categ);
        i.putExtra("Observaciones", observ);

        i.putExtra(C_MODO, C_EDITAR);
        startActivityForResult(i, C_EDITAR);
        //finish();
        //consultar();Se va a llamar desde Onrestart();


    }




    // Men� contextual....
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // menu.setHeaderTitle(hipotecasAdapter.getItem(((AdapterContextMenuInfo)
        // menuInfo).position).getNombre());

        // menu.setHeaderTitle(contactos.getItem(((AdapterContextMenuInfo)
        // menuInfo).position).getNombre());//--MODIFICACION 6=

        menu.setHeaderTitle(contactosAdapter_imagenes.getItem(
                ((AdapterView.AdapterContextMenuInfo) menuInfo).position).getNombre());
        menu.add(Menu.NONE, C_VISUALIZAR, Menu.NONE, R.string.menu_visualizar);
        menu.add(Menu.NONE, C_EDITAR, Menu.NONE, R.string.menu_editar);
        menu.add(Menu.NONE, C_ELIMINAR, Menu.NONE, R.string.menu_eliminar);

        menu.add(Menu.NONE, C_CALL, Menu.NONE, R.string.menu_llamar);


    }

    // Men� contextual....
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent i;

        switch (item.getItemId()) {

            case C_ELIMINAR:
                borrar(info.id);
                return true;

            case C_VISUALIZAR:
                try {
                    visualizar(info.id);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;

            case C_EDITAR:
                try {
                    editar(info.id);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return true;

            case C_CALL:
                try {
                    call(info.id);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;

        }
        return super.onContextItemSelected(item);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
//Evitamos que funcione la tecla del menú que traen por defecto los samsung...
        switch(keyCode) {
            case KeyEvent.KEYCODE_MENU:
               // Toast.makeText(this, "Menú presionado",
                 //       Toast.LENGTH_LONG);
                //toolbar.canShowOverflowMenu();
                //toolbar.setFocusable(true);
                //toolbar.collapseActionView();



                return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_crear) {

            Intent i = new Intent(this, AltaUsuarios.class);
            startActivityForResult(i,C_CREAR);

//			 setResult(RESULT_OK);
//				finish();
//			//startActivity(i);
            //consultar();No hace nada. Se va a llamar desde Onrestart():
            //finish();
            return true;
        }

        if (id == R.id.menu_traer_contactos) {
            //MIGRAR DATOS DE LA AGENDA DE ANDROID=========================
            Intent i = new Intent(this, ImportarContactos.class);
            //startActivityForResult(i,C_CREAR);
            startActivity(i);

//			 setResult(RESULT_OK);
//				finish();
//			//startActivity(i);
            //consultar();No hace nada. Se va a llamar desde Onrestart():
            //finish();
            return true;
        }

       if (id == R.id.menu_borrar_todos) {
            //MIGRAR DATOS DE LA AGENDA DE ANDROID=========================
//				Intent i = new Intent(this, ImportarContactos.class);
//				 //startActivityForResult(i,C_CREAR);
//				 startActivity(i);

//				 setResult(RESULT_OK);
//					finish();
//				//startActivity(i);
            //consultar();No hace nada. Se va a llamar desde Onrestart():
            //finish();
            borrarTodos();
            return true;
        }

        if (id == R.id.menu_borrar_algunos) {

            Intent intent=new Intent(ActivityLista.this,BorrarUsuarios.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Men�
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);



    //SE IMPLEMENTA EL MENÚ BUSCAR. Se añaden a la clase dos interfaces y se implmenta sus métodos más abajo...
        //no está acabado.

       // MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem searchItem = menu.findItem(R.id.buscar);
         //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getResources().getString(R.string.buscar_en_searchview));

        //Personalizamos con color y tamaño de letra
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.white));
        searchAutoComplete.setTextSize(14);


        searchView.setSubmitButtonEnabled(true);

       searchView.setOnQueryTextListener(this);

        // LISTENER PARA LA APERTURA Y CIERRE DEL WIDGET
        MenuItemCompat.setOnActionExpandListener(searchItem, this);
        //FIN IMPLEMENTACION DEL MENU BUSCAR
///////////////////



/////////////////

        return super.onCreateOptionsMenu(menu);
        //return true;
    }


    @Override//para el menú buscar
    public boolean onQueryTextSubmit(String query) {
        //Toast.makeText(ActivityLista.this, "Se ha buscado", Toast.LENGTH_SHORT).show();

       //searchView.setQuery("", false);//Se borra el texto buscado
        //searchView.setIconified(true);//se oculta el EditText


        return false;
    }


    @Override//para el menú buscar
    public boolean onQueryTextChange(String newTextt) {

   /*Dos formas para gesionar el filtrado recibido del adatador:

       La primera mostrando un TextUtils con el texto introducido en el searchview, el filtrado de los items y la personalización del texto
       La segunda sin utilizar el TextUtils

        */

       //Forma primera
     /*  if (TextUtils.isEmpty(newTextt)) {

            //contactosAdapter_imagenes.clearTextFilter();
            lista.clearTextFilter();
        } else {
            //contactosAdapter_imagenes.getFilter().filter(newTextt.toString());
            lista.setFilterText(newTextt.toString());
        }*/


        //Forma segunda. No utilizamos un TextUtils. solo llamamos al adaptador para que nos pinte lo que haya filtrado y construido él mismo....


//        int textleng=newTextt.length();
//
//        if (textleng==0){
//
//            lista.clearTextFilter();
//        }
//        else {
//            contactosAdapter_imagenes.getFilter().filter(newTextt);
//        }
        contactosAdapter_imagenes.getFilter().filter(newTextt);
        return true;
    }


    @Override//para el menú buscar
    public boolean onMenuItemActionExpand(MenuItem item) {

        return true;
    }



    @Override//para el menú buscar
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }


    // Men�
    //@Override
    //public boolean onMenuItemSelected(int featureId, MenuItem item) {
      //  Intent i;

        //switch (item.getItemId()) {
          //  case R.id.menu_crear:
            //    i = new Intent(ActivityLista.this, AltaUsuarios.class);
              //  i.putExtra(C_MODO, C_CREAR);
                //startActivityForResult(i, C_CREAR);
                //consultar();No hace nada. Se va a llamar desde Onrestart():

                //startActivity(i);
//			 setResult(RESULT_OK);
                //finish();
                //return true;


            //case R.id.menu_editar:
            //i = new Intent(MainActivity.this, ModificarUsuarios.class);
            // i.putExtra(C_MODO, C_CREAR);
            // startActivityForResult(i, C_CREAR);
            //editar(info.id);

            //startActivity(i);
            //return true;

            // case R.id.menu_preferencias:
            // i = new Intent(HipotecaActivity.this, Configuracion.class);
            // startActivityForResult(i, C_CONFIGURAR);
            // return true;
//        }
  //      return super.onMenuItemSelected(featureId, item);
   // }



}

