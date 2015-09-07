package activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.videumcorp.desarrolladorandroid.navigatio.R;

import java.sql.SQLException;

import controlador.SQLControlador;

/**
 * Created by Susana on 29/08/2015.
 */
public class Comunes {

    private SQLControlador dbConnection;//CONTIENE LAS CONEXIONES A BBDD (CREADA EN DBHELPER.CLASS) Y LOS M�TODOS INSERT, UPDATE, DELETE, BUSCAR....
    private Context context;


    //Va a ser llaamado desde fuera por eso es público.
    public  void borrarTodos(Activity ventana) {
		/*
		 * Borramos el registro y refrescamos la lista
		 */
        AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(ventana);

        dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
        dialogEliminar.setTitle(context.getResources().getString(
                R.string.agenda_eliminar_todos_titulo));
        dialogEliminar.setMessage(context.getResources().getString(
                R.string.agenda_eliminar_todos_mensaje));
        dialogEliminar.setCancelable(false);

        dialogEliminar.setPositiveButton(
                context.getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int boton) {
                        // dbAdapter.delete(id);

                        dbConnection = new SQLControlador(
                                context.getApplicationContext());
                        try {
                            dbConnection.abrirBaseDeDatos(2);
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }// Escritura. Borrar

                        dbConnection.borrarTodos();

                        /*Toast.makeText(ActivityLista.this,
                                R.string.agenda_eliminar_todos_confirmacion,
                                Toast.LENGTH_SHORT).show();*/
                        dbConnection.cerrar();
                        //Intent intent=new Intent(ventana.this,ActivityLista.class);
                        //startActivity(intent);
                        //consultar();
                    }
                });

        dialogEliminar.setNegativeButton(android.R.string.no, null);

        dialogEliminar.show();
    }

}
