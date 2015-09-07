package modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBhelper extends SQLiteOpenHelper {
	
	private static int version = 1;
	private static String name = "Agenda_clientes.db";
	private static CursorFactory factory = null;

	private String sql = "CREATE TABLE Contactos (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "Nombre TEXT NOT NULL, "
			+ "Apellidos TEXT, "
			+ "Direccion TEXT, "
			+ "Telefono TEXT, "
			+ "Email TEXT,"
			+ "Id_Categoria INTEGER,"//Campo nuevo
			+ "Observaciones TEXT)";//Campo nuevo
/*
 *Id_Categoria:
 *          1:Alcorcón y alrededores
 * 			2:Madrid capital
 * 			3:Madrid CC.AA.
 * 			4:Otra CC.AA..
 * 			5:Otro País
 * 		    6:Sin zona o importado de la Agenda
 * valorar posibilidad de crear otra tabla...
 * */
	

	public DBhelper(Context context) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Creamos la BB.DD. Agenda.db
		db.execSQL(sql);
		Log.i(this.getClass().toString(), "Tabla Contactos creada");
		
		// Insertamos en primer registro....
		//db.execSQL("INSERT INTO Contactos(Nombre) VALUES('Susana')");
		db.execSQL("INSERT INTO Contactos(Nombre,Apellidos,Direccion,Telefono,Email,Id_Categoria,Observaciones) VALUES('Alcorcón','Apellidos','Rubens, 12 M�stoles, MADRID', '676048719','email@gmail.com',1,'Observaciones incluidas por defecto')");
		db.execSQL("INSERT INTO Contactos(Nombre,Apellidos,Direccion,Telefono,Email,Id_Categoria,Observaciones) VALUES('Madrid capital','Apellidos','Rubens, 12 M�stoles, MADRID', '659355808','email@gmail.com',2,'Observaciones incluidas por defecto')");
		db.execSQL("INSERT INTO Contactos(Nombre,Apellidos,Direccion,Telefono,Email,Id_Categoria,Observaciones) VALUES('Madrid CC.AA.','Apellidos','Rubens, 12 M�stoles, MADRID', '659355808','email@gmail.com',3,'Observaciones incluidas por defecto')");
		db.execSQL("INSERT INTO Contactos(Nombre,Apellidos,Direccion,Telefono,Email,Id_Categoria,Observaciones) VALUES('Otra CC..AA.','Apellidos','Rubens, 12 M�stoles, MADRID', '659355808','email@gmail.com',4,'Observaciones incluidas por defecto')");
		db.execSQL("INSERT INTO Contactos(Nombre,Apellidos,Direccion,Telefono,Email,Id_Categoria,Observaciones) VALUES('Otro País','Apellidos','Rubens, 12 M�stoles, MADRID', '659355808','email@gmail.com',5,'Observaciones incluidas por defecto')");
		db.execSQL("INSERT INTO Contactos(Nombre,Apellidos,Direccion,Telefono,Email,Id_Categoria,Observaciones) VALUES('Otros','Apellidos','Rubens, 12 M�stoles, MADRID', '659355808','email@gmail.com',6,'Observaciones incluidas por defecto')");

		Log.i(this.getClass().toString(), "Datos iniciales creados. BB.DD. creada");
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
