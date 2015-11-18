package activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.videumcorp.desarrolladorandroid.navigatio.R;

import java.sql.SQLException;

import controlador.SQLControlador;

//import antonio.ejemplos.agendacomercial.R;

public class ModificarUsuarios extends AppCompatActivity {

	private EditText nombre;
	private EditText apellidos;
	private EditText direc;
	private EditText telefono;
	private EditText email;
	
	//private Spinner categoria;
	
	private RadioButton radio1,radio2,radio3,radio4,radio5,radio6;
	private EditText observaciones;
	
	
	private Button cancelar;
	private Button guardar;

	private SQLControlador dbConnection;

	// Modo del formulario
	private int modo;
	
	//validaci�n del formulario...
	private boolean validar=true;
	
	
	private int id_recogido;

    //Añadimos la toolbar
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modificar_usuarios);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		nombre = (EditText) findViewById(R.id.nombre);
		apellidos = (EditText) findViewById(R.id.apellidos);
		direc = (EditText) findViewById(R.id.direc);
		telefono = (EditText) findViewById(R.id.telefono);
		email = (EditText) findViewById(R.id.email);
		
		
		
		radio1=(RadioButton) findViewById(R.id.radio1);
		radio2=(RadioButton) findViewById(R.id.radio2);
		radio3=(RadioButton) findViewById(R.id.radio3);
		radio4=(RadioButton) findViewById(R.id.radio4);
		radio5=(RadioButton) findViewById(R.id.radio5);
		radio6=(RadioButton) findViewById(R.id.radio6);
		
//		categoria=(Spinner) findViewById(R.id.tipo);
		
		
		observaciones=(EditText) findViewById(R.id.observaciones);

		cancelar = (Button) findViewById(R.id.boton_cancelar);
		guardar = (Button) findViewById(R.id.boton_guardar);

		//Añadimos la toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //La acitivity debe extender de AppCompatActivity para poder hacer el seteo a ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        ModificarUsuarios.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }
		
		//Para el Spinner:
//				ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.categorias,android.R.layout.simple_spinner_item);
//				//A�adimos el layout para el men�
//				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				//Le indicamos al spinner el adaptador a usar
//				categoria.setAdapter(adapter);
				
				
		
		Bundle bundle = getIntent().getExtras();

//		final int id_recogido = bundle.getInt("_id");// Se guarda el id recogido
														// del Main.
		
		
		id_recogido = bundle.getInt("_id");
		
		nombre.setText(bundle.getString("Nombre"));
		apellidos.setText(bundle.getString("Apellidos"));
		direc.setText(bundle.getString("Direccion"));
		telefono.setText(bundle.getString("Telefono"));
		email.setText(bundle.getString("Email"));
		
//		i.putExtra("Id_Categoria", Id_Categ);
//		i.putExtra("Observaciones", observ);
//		bundle.getInt("Id_Categoria");

		if (bundle.getInt("Id_Categoria")==1) {
			//radio1.isChecked();
			radio1.setChecked(true);
		}
		if (bundle.getInt("Id_Categoria")==2) {
			//radio2.isChecked();
			radio2.setChecked(true);
		}
		if (bundle.getInt("Id_Categoria")==3) {
			//radio3.isChecked();
			radio3.setChecked(true);
		}
		if (bundle.getInt("Id_Categoria")==4) {
			//radio4.isChecked();
			radio4.setChecked(true);
		}
        if (bundle.getInt("Id_Categoria")==5) {
            //radio4.isChecked();
            radio5.setChecked(true);
        }
        if (bundle.getInt("Id_Categoria")==6) {
            //radio4.isChecked();
            radio6.setChecked(true);
        }

		observaciones.setText(bundle.getString("Observaciones"));

		// Intent intent = getIntent();
		// Bundle extra = intent.getExtras();
		//
		// if (extra == null) return;

		// Establecemos el modo del formulario
		establecerModo(bundle.getInt(ActivityLista.C_MODO));

		cancelar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Devolvemos el control y cerramos la Activity
				setResult(RESULT_CANCELED);
				finish();

			}
		});

		guardar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// CREA UN OBJETO DEL CONTROLADOR Y ABRE LA BBDD EN MODO
				// LECTURA...
				dbConnection = new SQLControlador(getApplicationContext());// Estamos
																			// en
																			// un
																			// contexto
																			// qu
																			// es
																			// un
																			// listener
				int Id_Categ=0;
				if (radio1.isChecked()==true) {
					Id_Categ=1;
					
				}
				else if(radio2.isChecked()==true)   {
					Id_Categ=2;
				}
				else if(radio3.isChecked()==true)   {
					Id_Categ=3;
				}
				else if(radio4.isChecked()==true)   {
					Id_Categ=4;
				}

                else if(radio5.isChecked()==true)   {
                    Id_Categ=5;
                }
                else if(radio6.isChecked()==true)   {
                    Id_Categ=6;
                }
				
				if (validar (validar) ){
				
				try {
					dbConnection.abrirBaseDeDatos(2);//objeto SQLcontrolador
					dbConnection.ModificarContacto(id_recogido, nombre
							.getText().toString(), apellidos.getText()
							.toString(), direc.getText().toString(), telefono
							.getText().toString(), email.getText().toString(),Id_Categ,observaciones.getText().toString());

					// Log.i(this.getClass().toString(), id_recogido +
					// "UPDATE_1" + "  "+ nombre.getText().toString() );

					Toast.makeText(ModificarUsuarios.this,
							R.string.agenda_editar_confirmacion,
							Toast.LENGTH_SHORT).show();
					dbConnection.cerrar();
					
					// Devolvemos el control y cerramos la Activity
//					Intent i = new Intent(ModificarUsuarios.this, MainActivity.class);
//					startActivity(i);
					
					setResult(RESULT_OK);
					finish();
					//Para actualizar datos en MainActivity Se va a llamar a Consultar() desde Onrestart() del com.agendacomercial.navigatio.
					

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// Abrimos en modo escritura...

				

			}//fin validar
				
				
				
			}//fin onclick()
			
			
			});//fin listener()
		
		

	}

	
	//Validaci�n para que el nombre no se deje vac�o
		private boolean  validar(boolean validar){
			if (nombre.getText().toString().equals("")  ||  telefono.getText().toString().equals("")){
				//if (nombre.getText().toString().length() == 0){
			
				//Toast.makeText(getApplicationContext(), "Es obligatorio rellenar el nombre" , Toast.LENGTH_LONG).show();
				
				//Se prepara la alerta creando nueva instancia     
				AlertDialog.Builder dialogValidar = new AlertDialog.Builder(this);
				dialogValidar.setIcon(android.R.drawable.ic_dialog_alert);//icono
				dialogValidar.setTitle(getResources().getString(R.string.agenda_editar_titulo));//T�tulo
				dialogValidar.setMessage(getResources().getString(R.string.agenda_texto_vacio));
				//Se a�ade un solo bot�n para que el usuario confirme...
				dialogValidar.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	                }
	            });
				dialogValidar.create().show(); 
				
				
				
				return false;
			}
			
			return true;
		}
		
	
	private void establecerModo(int m) {
		this.modo = m;

		if (modo == ActivityLista.C_VISUALIZAR) {
			this.setTitle(nombre.getText().toString());
			this.setEdicion(false);
		} else if (modo == ActivityLista.C_CREAR) {
			// this.setTitle(R.string.hipoteca_crear_titulo);
			this.setEdicion(true);
		} else if (modo == ActivityLista.C_EDITAR) {
			this.setTitle(R.string.agenda_editar_titulo);
			this.setEdicion(true);
		}
	}

	private void setEdicion(boolean opcion) {
		nombre.setEnabled(opcion);
		apellidos.setEnabled(opcion);
		direc.setEnabled(opcion);
		telefono.setEnabled(opcion);
		email.setEnabled(opcion);
		
		
//		radio1.setVisibility(1);
//		radio2.setVisibility(1);
//		radio3.setVisibility(1);
//		radio4.setVisibility(1);
		
		radio1.setEnabled(opcion);
		radio2.setEnabled(opcion);
		radio3.setEnabled(opcion);
		radio4.setEnabled(opcion);
        radio5.setEnabled(opcion);
        radio6.setEnabled(opcion);

		observaciones.setEnabled(opcion);
		
		// Controlamos visibilidad de botonera
		LinearLayout v = (LinearLayout) findViewById(R.id.botonera);

		if (opcion)
			v.setVisibility(View.VISIBLE);

		else
			v.setVisibility(View.GONE);
		// Lineas para ocultar el teclado virtual (Hide keyboard)
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(nombre.getWindowToken(), 0);
	}
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modificar_usuarios, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		
		
		if (id == R.id.menu_llamar) {
			
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telefono.getText().toString()));
			startActivity(intent);
			
			
			return true;
		}
		
	if (id == R.id.menu_compartir) {
			
		Uri uri = Uri.parse("smsto:" + telefono.getText().toString());
		 Intent i = new Intent(Intent.ACTION_SENDTO, uri);
					
		//i.putExtra("sms_body", smsText); 
		i.setPackage("com.whatsapp");
		startActivity(i); 
			
			return true;
		}
		
		
		if (id == R.id.menu_email) {
			
//====SALEN PARA ELEGIR TODAS LAS APPS QUE PUDIERAN TRATAR TEXTO==========
//	        Intent intent = new Intent(Intent.ACTION_SEND);
//
//	        intent.setType("text/plain");
//
//	        //intent.putExtra(Intent.EXTRA_SUBJECT, "Enviado autom�ticamente==>");
//
//	        //intent.putExtra(Intent.EXTRA_TEXT, "Hola nena...");
//
//	        intent.putExtra(Intent.EXTRA_EMAIL, 
//	                                    new String[] {email.getText().toString()});
//
//	        startActivity(intent);
//==============================================
			
//==================SALEN PARA ELEGIR TAMBI�N LAS APPS QUE COMPARTEN TEXTO....			
			
			Intent intent = new Intent(Intent.ACTION_SENDTO);
			intent.setData(Uri.parse("mailto:"));
			intent.putExtra(Intent.EXTRA_EMAIL  , new String[] {email.getText().toString()});
			//intent.putExtra(Intent.EXTRA_SUBJECT, "My subject");

			startActivity(Intent.createChooser(intent, "Excoge una aplicaci�n para enviar el email"));

//==================SOLO SALEN PARA SER ELEGIDAS APPS DE CORREO ELECTR�NICO...			

			
			return true;
		}
		
//		if (id == R.id.action_settings) {
//			return true;
//		}
		
		
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
