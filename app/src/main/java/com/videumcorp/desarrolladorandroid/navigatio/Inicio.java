package com.videumcorp.desarrolladorandroid.navigatio;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import activitys.ActivityLista;
import activitys.AltaUsuarios;
import activitys.BorrarUsuarios;
import activitys.ImportarContactos;
import activitys.ImportarWebService;


public class Inicio extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    TextView textView;
    //Button boton;



    //NUEVO AÑADIDO..../////////////////////////////////////////////////////



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Objeto Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//Es obligatorio setearla a una ActionBar

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);//Establecemos el icono
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //boton=(Button)findViewById(R.id.boton_agenda);


        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }

        setupNavigationDrawerContent(navigationView);


        //Ver la Agenda..o cerrar la app
      /*  boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent in=new Intent(Inicio.this,ActivityLista.class);
                //startActivity(in);
                finish();


            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
   }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.menu_ver_agenda:
                Intent intent=new Intent(Inicio.this,ActivityLista.class);
                startActivity(intent);

                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {//VER AGENDA
                            case R.id.item_navigation_drawer_inbox:
                                menuItem.setChecked(true);

                                drawerLayout.closeDrawer(GravityCompat.START);

                                Intent intent = new Intent(Inicio.this, ActivityLista.class);
                                startActivity(intent);
                                return true;

                            case R.id.item_navigation_drawer_starred://DAR DE ALTA
                                menuItem.setChecked(true);
                                //textView.setText(menuItem.getTitle());
                                drawerLayout.closeDrawer(GravityCompat.START);

                                Intent intent2 = new Intent(Inicio.this, AltaUsuarios.class);
                                startActivity(intent2);

                                return true;

                            case R.id.item_navigation_drawer_sent_mail://BORRAR LA AGENDA
                                menuItem.setChecked(true);
                                //textView.setText(menuItem.getTitle());//ES LA TEXTVIEW QUE HE BORRADO
                                drawerLayout.closeDrawer(GravityCompat.START);

                                Intent intentborrar = new Intent(Inicio.this, BorrarUsuarios.class);
                                startActivity(intentborrar);
                                return true;


                            case R.id.item_navigation_drawer_drafts://IMPORTAR AGENDA DE ANDROID
                                menuItem.setChecked(true);
                                //textView.setText(menuItem.getTitle());//ES LA TEXTVIEW QUE HE BORRADO
                                drawerLayout.closeDrawer(GravityCompat.START);

                                Intent intent3 = new Intent(Inicio.this, ImportarContactos.class);
                                startActivity(intent3);
                                return true;

                            case R.id.item_navigation_drawer_maps://Google maps
                                menuItem.setChecked(true);
                                //textView.setText(menuItem.getTitle());//ES LA TEXTVIEW QUE HE BORRADO
                                drawerLayout.closeDrawer(GravityCompat.START);

                               //////////////////////////////
                              /*  public static void routeTo(Activity activity, String destiny){
                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+destiny));
                                activity.startActivity(i);
                            }*/


                              /*  En este caso la acción es un ACTION_VIEW, pero le decimos que abra el navegador parseando a un
                                objeto Uri y añadiendo “google.navigation:q=” a nuestro destino. Los valores validos para destiny
                                son los mismos que si buscarais algo en la web de Google Maps, pero con algunas cosillas.
                                    Si ponemos una dirección, por ejemplo: Puerta de Alcala, Madrid, España, debemos sustituir
                                los espacios por el símbolo más (+). Podemos poner unas coordenadas GPS, pero al contrario de lo
                                que hicimos en los capítulos de Google Maps, las coordenadas las tenemos que poner
                                así: “43.007230,-7.556199”. Si os fijáis tienen punto decimal.*/

                                //////////////////


                                //Navegar
                                //String uri = String.format(Locale.getDefault(), "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f",37.424307,-122.095023, 37.422867,-122.0932849);
                                //Posicionarse
                                String uri = String.format("http://maps.google.com/maps?q=%f,%f",37.424307,122.095023);

                                Intent intentnavigator = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                intentnavigator.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                try
                                {
                                    startActivity(intentnavigator);
                                }
                                catch (ActivityNotFoundException e)
                                {
                                    //Toast.makeText(this, R.string.agenda_texto_vacio, Toast.LENGTH_SHORT).show();
                                }

                                return true;


                            case R.id.item_navigation_drawer_settings://Navegar por internet
                                menuItem.setChecked(true);
                                //textView.setText(menuItem.getTitle());//ES LA TEXTVIEW QUE HE BORRADO
                                //Toast.makeText(Inicio.this, "Launching " + menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                //drawerLayout.closeDrawer(GravityCompat.START);
                                //Intent intent4 = new Intent(Inicio.this, SettingsActivity.class);
                                //startActivity(intent4);

                                Intent intentweb = new Intent(Intent.ACTION_VIEW);
                                intentweb.setData(Uri.parse("http://www.google.es"));
                                startActivity(intentweb);


                                return true;

                            case R.id.item_navigation_drawer_help_and_feedback:
                                menuItem.setChecked(true);
                                Intent intentAyudda = new Intent(Inicio.this,SettingsActivity.class);
                                startActivity(intentAyudda);
                                return true;

                            case R.id.item_navigation_drawer_sync:
                                menuItem.setChecked(true);
                               // Snackbar.make(navigationView, "No ha sido posible la conexión con el servidor", Snackbar.LENGTH_LONG).show();

                                conectarConWebService(navigationView);
                                


                                return true;

                        }
                        return true;
                    }
                });
    }


    public void conectarConWebService(View view){

        //Primeramente se comprueba que tengamos la conexión a internet habilitada....
        try {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            final android.net.NetworkInfo wifi =
                    connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if ((networkInfo != null) && networkInfo.isConnected() && wifi.isConnected()){
                Intent intentWebService = new Intent(Inicio.this,ImportarWebService.class);
                startActivity(intentWebService);}

            else {


                //Toast.makeText(Inicio.this, "Error de conexion", Toast.LENGTH_LONG).show();
                //Snackbar.make(navigationView, "No ha sido posible la conexión con el servidor", Snackbar.LENGTH_LONG).show();
                //Snackbar.make(view, "Su terminal no tiene habilitada ninguna conexión de red", Snackbar.LENGTH_LONG)
                Snackbar snack = Snackbar.make(view, "Su terminal no tiene habilitada ninguna conexión wifi para poder acceder a este recurso.", Snackbar.LENGTH_LONG);
                ViewGroup group = (ViewGroup) snack.getView();
                group.setBackgroundColor(getResources().getColor(R.color.md_blue_600));

                //if (Build.VERSION.SDK_INT >= 17) {

                //group.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            //}

                snack.show();

                        //.setActionTextColor(getResources().getColor(R.color.md_blue_600))

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


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
}

