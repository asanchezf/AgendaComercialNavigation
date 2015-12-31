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

	//PRIMERA VERSIÓN
//	private String sql = "CREATE TABLE Contactos (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
//			+ "Nombre TEXT NOT NULL, "
//			+ "Apellidos TEXT, "
//			+ "Direccion TEXT, "
//			+ "Telefono TEXT, "
//			+ "Email TEXT,"
//			+ "Id_Categoria INTEGER,"//Campo nuevo
//			+ "Observaciones TEXT)";//Campo nuevo


	//SEGUNDA VERSIÓN
	private String sql = "CREATE TABLE Contactos (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "Nombre TEXT NOT NULL, "
			+ "Apellidos TEXT, "
			+ "Direccion TEXT, "
			+ "Telefono TEXT, "
			+ "Email TEXT,"
			+ "Id_Categoria INTEGER,"
			+ "Observaciones TEXT,"
			+ "Importado INTEGER,"//Campo nuevo para sincronizar con agenda android
			+ "Sincronizado INTEGER)";//Campo nuevo para sincronizar con WS REST



/*
 *Categorías existentes:
 *          1:Alcorcón y alrededores
 * 			2:Madrid capital
 * 			3:Madrid CC.AA.
 * 			4:Otra CC.AA..
 * 			5:Otro País
 * 		    6:Sin zona o importado de Android
 * 		    7:Importado de Servidor Web
 * valorar posibilidad de crear otra tabla...
 * */
	

	public DBhelper(Context context) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Creamos la BB.DD. Agenda_clientes.db
		db.execSQL(sql);
		Log.i(this.getClass().toString(), "Tabla Contactos creada");
		
		// DATOS INICIALES

		db.execSQL("INSERT INTO Contactos(Nombre,Apellidos,Direccion,Telefono,Email,Id_Categoria,Observaciones,Importado,Sincronizado) VALUES('Alcorcón','Apellidos','Rubens, 12 M�stoles, MADRID', '676048719','susimail62@gmail.com',1,'Observaciones incluidas por defecto',null,null)");
		db.execSQL("INSERT INTO Contactos(Nombre,Apellidos,Direccion,Telefono,Email,Id_Categoria,Observaciones,Importado,Sincronizado) VALUES('Madrid capital','Apellidos','Rubens, 12 M�stoles, MADRID', '659355808','antoniom.sanchezf@gmail.com',2,'Observaciones incluidas por defecto',null,null)");
		db.execSQL("INSERT INTO Contactos(Nombre,Apellidos,Direccion,Telefono,Email,Id_Categoria,Observaciones,Importado,Sincronizado) VALUES('Madrid CC.AA.','Apellidos','Rubens, 12 M�stoles, MADRID', '659355808','antoniom.sanchezf@gmail.com',3,'Observaciones incluidas por defecto',null,null)");
		db.execSQL("INSERT INTO Contactos(Nombre,Apellidos,Direccion,Telefono,Email,Id_Categoria,Observaciones,Importado,Sincronizado) VALUES('Otra CC..AA.','Apellidos','Rubens, 12 M�stoles, MADRID', '659355808','antoniom.sanchezf@gmail.com',4,'Observaciones incluidas por defecto',null,null)");
		db.execSQL("INSERT INTO Contactos(Nombre,Apellidos,Direccion,Telefono,Email,Id_Categoria,Observaciones,Importado,Sincronizado) VALUES('Otro País','Apellidos','Rubens, 12 M�stoles, MADRID', '659355808','antoniom.sanchezf@gmail.com',5,'Observaciones incluidas por defecto',null,null)");

		db.execSQL("INSERT INTO Contactos(Nombre,Apellidos,Direccion,Telefono,Email,Id_Categoria,Observaciones,Importado,Sincronizado) VALUES('Importado Android','Apellidos','Rubens, 12 M�stoles, MADRID', '659355808','antoniom.sanchezf@gmail.com',6,'Importado Android',1,null)");
		db.execSQL("INSERT INTO Contactos(Nombre,Apellidos,Direccion,Telefono,Email,Id_Categoria,Observaciones,Importado,Sincronizado) VALUES('Sincronizado WS','Apellidos','Rubens, 12 M�stoles, MADRID', '659355808','antoniom.sanchezf@gmail.com',7,'Sincronizado',null,1)");



		Log.i(this.getClass().toString(), "Datos iniciales incluyendo campos nuevos... insertados. BB.DD. creada");



	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		//NOTA: Por simplicidad del ejemplo aquí utilizamos directamente
		// la opción de eliminar la tabla anterior y crearla de nuevo
		// vacía con el nuevo formato.
		// Sin embargo lo normal será que haya que migrar datos de la
		// tabla antigua a la nueva, por lo que este método debería
		// ser más elaborado.
		//Se elimina la versión anterior de la tabla
			//db.execSQL("DROP TABLE IF EXISTS Contactos");
		//Se crea la nueva versión de la tabla
			//db.execSQL(sqlCreate);


	}

}
