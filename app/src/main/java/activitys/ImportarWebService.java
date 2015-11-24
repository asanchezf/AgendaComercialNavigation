package activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.videumcorp.desarrolladorandroid.navigatio.Inicio;
import com.videumcorp.desarrolladorandroid.navigatio.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import Beans.Clientes;
import controlador.SQLControlador;

//import antonio.ejemplos.agendacomercial.R;


public class ImportarWebService extends AppCompatActivity {
	

	//Para la barra de progreso
		private static int progreso;
		//private ProgressBar barraProgreso;
		private ProgressBar barraProgreso2;

		private TextView txt;
		//private int estatusProgreso = 0;
		//private Handler manejador = new Handler();//Manejador del hilo

		private static final int SINCRONIZADO = 7;

        private TextView txtconfirm;

    	private Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Esta activity comparte layout con la activity ImportarContactos
		setContentView(R.layout.activity_importar_contactos);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//Añadimos la toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //La acitivity debe extender de AppCompatActivity para poder hacer el seteo a ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        ImportarWebService.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }


		//progreso = 0;
		//barraProgreso = (ProgressBar) findViewById(R.id.barradeprogreso);
		barraProgreso2=(ProgressBar)findViewById(R.id.barradeprogreso2);
		barraProgreso2.setMax(100);


		txt=(TextView) findViewById(R.id.textView1);
		txt.setVisibility(View.INVISIBLE);

        txtconfirm=(TextView)findViewById(R.id.txtconfirm);

        txtconfirm.setVisibility(View.INVISIBLE);
		//barraProgreso.setVisibility(View.INVISIBLE);

		//Diálogo de confirmación....


		AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);

		dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
		dialogEliminar.setTitle(getResources().getString(
				R.string.title_activity_importar_contactosWebService));
		dialogEliminar.setMessage(getResources().getString(
				R.string.aceptar_importWebService));
		dialogEliminar.setCancelable(false);

		dialogEliminar.setPositiveButton(
				getResources().getString(android.R.string.ok),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int boton) {

						// ---Hacer alg�n trabajo en el hilo de fondo---
						//new SincronizarconWebService().execute();//FORMA 1-Sin utilizar Gson: GetInputStream + BufferedReader
						new SincronizarconWebService2().execute();//FORMA 2-Utilizando Gson: GetInputStream + JsonReader en GsonClientesParser.java

					}//Fin onclick del alert

				});//Fin setPositiveButton


		dialogEliminar.setNegativeButton(android.R.string.no,


				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int boton) {
						barraProgreso2.setVisibility(View.INVISIBLE);

						System.exit(0);//Cerramos la activity actual
						//Intent intent=new Intent(ImportarContactos.this,ActivityLista.class);
						//startActivity(intent);

					}
				});




		dialogEliminar.show();


	}//Fin Oncreate


	public  class SincronizarconWebService2 extends AsyncTask<List<String>, Integer, ArrayList<Clientes>>{
		//String []arrayDeStrings;
		HttpURLConnection con;
		//Context contexto;
		Clientes clientes;
		ArrayList<Clientes> listaClientes=new ArrayList<Clientes>();


		protected void onProgressUpdate(Integer... values) {


			barraProgreso2.setProgress(values[0]);

		}
		@Override
		protected void onPreExecute() {
			barraProgreso2.setVisibility(View.VISIBLE);

		}

		//Recibe un List<String> y devuelve un ArrayList<Clientes>
		@Override
		protected ArrayList<Clientes> doInBackground(List<String>... params) {


			Boolean result = true;

			//List<String> comments = null;

			//Para SQLControlador:
			SQLControlador Connection;


			try {

				// Establecer la conexi�n..Las IPS van cambiando
				//URL url = new URL("http://192.168.0.154:8080/WebServicesRESTGlassFishJEE7/webresources/contactos");
				//URL url = new URL("http://192.168.0.157:8080/WebServicesRESTGlassFishJEE7/webresources/contactos");

				//URL PARA BUSCAR POR PROPIETARIO:
				URL url = new URL("http://192.168.0.157:8080/WebServicesRESTGlassFishJEE7/webresources/contactos/propietario/Antonio");
				con = (HttpURLConnection) url.openConnection();

				//con = (HttpURLConnection).openConnection(url);
				//URLConnection con = url.openConnection();


				// Obtener el estado del recurso
				int statusCode = con.getResponseCode();
				//int statusCode = url.getResponseCode();

				//La conexión ha ido mal. Salimos...
				if (statusCode != 200) {
					//comments = new ArrayList<Clientes>();
					//comments.add("El recurso no est� disponible");

					clientes = new Clientes();
					listaClientes.add(clientes);
					return listaClientes;
				} else {

                    /*
                    Parsear el flujo con formato JSON a una lista de Strings
                    que permitan crean un adaptador
                     */
					InputStream in = new BufferedInputStream(con.getInputStream());


			////////////////NUEVO utilizando Gson//////////////

					// JsonAnimalParser parser = new JsonAnimalParser();
					GsonClientesParser parser = new GsonClientesParser();

					listaClientes = parser.leerFlujoJson(in);

				}

			} catch (Exception e) {
				e.printStackTrace();
				result = false;
				Log.e("SERVICIO REST", "ERROR DE CONEXION", e);

			} finally {
				con.disconnect();
			}
			//return listaClientes;
			/// ArrayAdapter<Clientes> adaptador=new ArrayAdapter<Clientes>(contexto,android.R.layout.simple_list_item_2,listaClientes);


			//==============================================VAMOS A SQLITE PARA COMPARAR RESULTADOS==============

			//Construimos la fecha de operación de sincronización con la BB.DD. de MySql
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = new GregorianCalendar();

			String dia = Integer.toString(c2.get(Calendar.DATE));
			String mes = Integer.toString(c2.get(Calendar.MONTH) + 1);
			String annio = Integer.toString(c2.get(Calendar.YEAR));


			String fecha = dia + "/" + mes + "/" + annio;


			Connection = new SQLControlador(getApplicationContext());//Objeto SQLControlador
			try {
				Connection.abrirBaseDeDatos(2);

				//Connection.ImportaColeccionContent(nombres, telefonos, emails);//Enviando tres colecciones. Problemas al recuperar los valores de email
				//Connection.ImportaCursorContent(cur, pCur, emailCur);//Enviando los tres cursores del Contentprovider..Problemas recibiendo..

				//Connection.ImportCollectionContactsContent(ArrayListcontactos);

				Connection.insertarSincronizados(listaClientes, fecha);


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//Para la barra de progreso
			for (int z = 1; z <= 100; z++) {
				try {
					Thread.sleep(10);//Para la barra
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				publishProgress(z);//Actualizamos la barra de progreso
			}

			return listaClientes;

		}

		@Override
		protected void onPostExecute(ArrayList<Clientes> result) {//Recibe un ArrayList<Clientes> llamado listaClientes


			//NO SE HA CONSEGUIDO CONECTAR. MOSTRAMOS UN TOAST Y REDIRECCIONAMOS AL MAIN
			String observacionesparasalir="El recurso no está disponible en estos momentos. Inténtalo más tarde...";
			if(listaClientes.size()==0){
				Toast.makeText(getApplicationContext(), observacionesparasalir, Toast.LENGTH_LONG).show();
				Intent intent=new Intent(ImportarWebService.this, Inicio.class);
				startActivity(intent);
			}

			else {
				Toast.makeText(getApplicationContext(), "Se ha sincronizado la agenda de clientes", Toast.LENGTH_LONG).show();

				Log.i("SERVICIO REST", "Tarea realizada");

				//barraProgreso.setVisibility(View.INVISIBLE);
				barraProgreso2.setVisibility(View.INVISIBLE);
				//txtconfirm.setVisibility(View.VISIBLE);
				txt.setVisibility(View.VISIBLE);

			}
		}

	}

//=========================================================================================================
//PARAMETROS: LO QUE RECIBE, VARIABLE PARA CONTROLAR EL PROGRESO, LO QUE DEVUELVE al postUpdate
	//<List<String>, Integer, Boolean>

	public  class SincronizarconWebService extends AsyncTask<List<String>, Integer, ArrayList<Clientes>> {
		//String []arrayDeStrings;
		HttpURLConnection con;
		//Context contexto;
		Clientes clientes;
		ArrayList<Clientes> listaClientes=new ArrayList<Clientes>();


		protected void onProgressUpdate(Integer... values) {


			barraProgreso2.setProgress(values[0]);

		}
		@Override
		protected void onPreExecute() {
			barraProgreso2.setVisibility(View.VISIBLE);

		}


		//Recibe un List<String> y devuelve un ArrayList<Clientes>
		@Override
		protected ArrayList<Clientes> doInBackground(List<String>... params) {

			int id;
			int id_Servidor;
			int id_Android = 0;
			String nombre;
			String apellidos;
			String direccion;
			String telefono;
			String email;
			int idCategoria;
			String observaciones;
			String propietario;
			Boolean result = true;

			//List<String> comments = null;

			//Para SQLControlador:
			SQLControlador Connection;


			try {

				// Establecer la conexi�n..Las IPS van cambiando
				//URL url = new URL("http://192.168.0.154:8080/WebServicesRESTGlassFishJEE7/webresources/contactos");
				//URL url = new URL("http://192.168.0.157:8080/WebServicesRESTGlassFishJEE7/webresources/contactos");

				//URL PARA BUSCAR POR PROPIETARIO:
				//http://localhost:8080/WebServicesRESTGlassFishJEE7/webresources/contactos/propietario/Antonio
				URL url = new URL("http://192.168.0.157:8080/WebServicesRESTGlassFishJEE7/webresources/contactos/propietario/Antonio");
				con = (HttpURLConnection) url.openConnection();

				//con = (HttpURLConnection).openConnection(url);
				//URLConnection con = url.openConnection();


				// Obtener el estado del recurso
				int statusCode = con.getResponseCode();
				//int statusCode = url.getResponseCode();

				//La conexión ha ido mal. Salimos...
				if (statusCode != 200) {
					//comments = new ArrayList<Clientes>();
					//comments.add("El recurso no est� disponible");

					clientes = new Clientes();
					listaClientes.add(clientes);
					return listaClientes;
				} else {

                    /*
                    Parsear el flujo con formato JSON a una lista de Strings
                    que permitan crean un adaptador
                     */
					InputStream in = new BufferedInputStream(con.getInputStream());

					//JSONCommentsParser parser = new JSONCommentsParser();

					//comments = parser.readJsonStream(in);//Es una Colección

////////////////NUEVO//////////////
					BufferedReader lector = new BufferedReader(new InputStreamReader(in));
					StringBuilder sb = new StringBuilder();
					String linea = null;

					try {
						while ((linea = lector.readLine()) != null) {

							sb.append(linea);
						}
					} catch (IOException e) {
						e.printStackTrace();

					}
					try {
						in.close();

					} catch (IOException e) {
						e.printStackTrace();
					}

					String resultado = sb.toString();


					try {

						//Para la barra de progreso
						//for (int z = 1; z <= 100; z++) {

						//Thread.sleep(10);//Para la barra
						JSONArray respJson = new JSONArray(resultado);

						//arrayDeStrings=new String[respJson.length()];

						for (int i = 0; i < respJson.length(); i++) {


							JSONObject objetoJSon = respJson.getJSONObject(i);

							id_Servidor = objetoJSon.getInt("id");


//                            for (int i = 0; i < innerProjectarray.length(); i++) {
//                                JSONObject obj=innerProjectarray.getJSONObject(i);
//                                if(obj.has("androidID"))
//                                { Log.i("androidID ",obj.getString("androidID"));
//                                } else { Log.i("Project Number ","No Such Tag as PROJECT_NUMBER"); }
//                                if(obj.has("PROJECT_NAME")) { Log.i("Project Name ",obj.getString("PROJECT_NAME"));
//                                } else { Log.i("Project Name ","No Such Tag as PROJECT_NAME"); } }


							//if((objetoJSon.getInt("androidID"))>0) {
							//if(objetoJSon.length()>0) {


							//id_Android = objetoJSon.getInt("androidID");//Cuando se dió de alta desde la página web no está informado...
							if (objetoJSon.has("androidID")) {//Se comprueba previamente si está informado el campo androidID
								Log.i("androidID ", objetoJSon.getString("androidID"));
								id_Android = objetoJSon.getInt("androidID");

							} else {
								Log.i("Project Number ", "androidID no tiene valor en el JSON");
							}


							nombre = objetoJSon.getString("nombre");
							apellidos = objetoJSon.getString("apellidos");
							direccion = objetoJSon.getString("direccion");
							telefono = objetoJSon.getString("telefono");
							email = objetoJSon.getString("email");
							idCategoria = objetoJSon.getInt("idCategoria");
							observaciones = objetoJSon.getString("observaciones");
							propietario = objetoJSon.getString("owner");

							//clientes = new Clientes(id_Servidor + "-" + id_Android + "-" + nombre + "-" + apellidos + "-" + direccion + "-" + telefono + "-" + email + "-" + idCategoria + "-" + observaciones + "-" + propietario);

							//Se rea objeto de la clase Clientes para pasárselo al adaptador, se le pasa al arraylist y luego al adaptador
//								if (objetoJSon.has("androidID")) {
//									clientes = new Clientes(id_Servidor + "-" + id_Android + "-" + nombre + "-" + apellidos + "-" + direccion + "-" + telefono + "-" + email + "-" + idCategoria + "-" + observaciones + "-" + propietario);
//								} else {
//									clientes = new Clientes(id_Servidor + "-" + 0 + "-" + nombre + "-" + apellidos + "-" + direccion + "-" + telefono + "-" + email + "-" + idCategoria + "-" + observaciones + "-" + propietario);
//								}

							clientes = new Clientes(nombre, apellidos, direccion, telefono, email, idCategoria, observaciones);

							//Obtenermos un ArrayList de clientes desde la BB.DD. de MySql
							listaClientes.add(clientes);

							//Si utilizamos un Array de String para pasárselo al adaptador
							//arrayDeStrings[i]=objetoJSon.getString("nombre");--Funciona también
							//arrayDeStrings[i]=id_Servidor+"-"+id_Android+"-"+nombre+"-"+apellidos+"-"+telefono;
							//arrayDeStrings[i]=id_Servidor+"-"+nombre+"-"+apellidos+"-"+telefono;

						}//Fin JSon

						//publishProgress(z);//Actualizamos la barra de progreso
						//}//Fin For barra de progreso


					} catch (JSONException e) {

						e.printStackTrace();
						//e.getMessage();
						Log.e("SERVICIO REST", "ERROR LEYENDO JSON", e);
					}


				}

			} catch (Exception e) {
				e.printStackTrace();
				result = false;
				Log.e("SERVICIO REST", "ERROR DE CONEXION", e);

			} finally {
				con.disconnect();
			}
			//return listaClientes;
			/// ArrayAdapter<Clientes> adaptador=new ArrayAdapter<Clientes>(contexto,android.R.layout.simple_list_item_2,listaClientes);


			//==============================================VAMOS A SQLITE PARA COMPARAR RESULTADOS==============

			//Construimos la fecha de operación de sincronización con la BB.DD. de MySql
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = new GregorianCalendar();

			String dia = Integer.toString(c2.get(Calendar.DATE));
			String mes = Integer.toString(c2.get(Calendar.MONTH) + 1);
			String annio = Integer.toString(c2.get(Calendar.YEAR));


			String fecha = dia + "/" + mes + "/" + annio;


			Connection = new SQLControlador(getApplicationContext());//Objeto SQLControlador
			try {
				Connection.abrirBaseDeDatos(2);

				//Connection.ImportaColeccionContent(nombres, telefonos, emails);//Enviando tres colecciones. Problemas al recuperar los valores de email
				//Connection.ImportaCursorContent(cur, pCur, emailCur);//Enviando los tres cursores del Contentprovider..Problemas recibiendo..

				//Connection.ImportCollectionContactsContent(ArrayListcontactos);

				Connection.insertarSincronizados(listaClientes, fecha);


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//Para la barra de progreso
			for (int z = 1; z <= 100; z++) {
				try {
					Thread.sleep(10);//Para la barra
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				publishProgress(z);//Actualizamos la barra de progreso
			}

			return listaClientes;

		}


		 @Override
		 protected void onPostExecute(ArrayList<Clientes> result) {//Recibe un ArrayList<Clientes> llamado listaClientes
			    /*
            Se crea un adaptador con el el resultado del
            que se realiz� al array JSON*/

			 //ADAPTADOR UTILIZANDO UN ARRAYLIST DE LA CLASE CLIENTES
			 //ArrayAdapter<Clientes> adaptador=new ArrayAdapter<Clientes>(Inicio.this,android.R.layout.simple_list_item_1,listaClientes);

			 //ADAPTADOR UITLIZANDO UN ARRAY DE STRINGS
			 //ArrayAdapter<String>adaptador=new ArrayAdapter<String>(Inicio.this,android.R.layout.simple_list_item_1,arrayDeStrings);

			 // Relacionar adaptador a la lista
			 //listaJSon.setAdapter(adaptador);

//			 if (result.size()>0){}

			//result;

			 //NO SE HA CONSEGUIDO CONECTAR. MOSTRAMOS UN TOAST Y REDIRECCIONAMOS AL MAIN
			 String observacionesparasalir="El recurso no está disponible en estos momentos. Inténtalo más tarde...";
			 if(listaClientes.size()==0){
				 Toast.makeText(getApplicationContext(), observacionesparasalir, Toast.LENGTH_LONG).show();
				 Intent intent=new Intent(ImportarWebService.this, Inicio.class);
				 startActivity(intent);
			 }

			 else {
				 Toast.makeText(getApplicationContext(), "Se ha sincronizado la agenda de clientes", Toast.LENGTH_LONG).show();

				 Log.i("SERVICIO REST", "Tarea realizada");

				 //barraProgreso.setVisibility(View.INVISIBLE);
				 barraProgreso2.setVisibility(View.INVISIBLE);
				 //txtconfirm.setVisibility(View.VISIBLE);
				 txt.setVisibility(View.VISIBLE);

			 }
		 }
	 }
//========================================================================================================
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.importar_contactos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
