package Beans;

public class Contactos {

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

	private long _id;
	private String Nombre;
	private String Apellidos;
	private String Direccion;
	private String Telefono;
	private String Email;
	private int Id_Categoria;
	private String Observaciones;

	private int Importado;
	private int Sincronizado;


	public Contactos(long _id, String nombre, String apellidos, String direccion, String telefono, String email, int id_Categoria, String observaciones, int importado, int sincronizado) {
		this._id = _id;
		Nombre = nombre;
		Apellidos = apellidos;
		Direccion = direccion;
		Telefono = telefono;
		Email = email;
		Id_Categoria = id_Categoria;
		Observaciones = observaciones;
		Importado = importado;
		Sincronizado = sincronizado;
	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getApellidos() {
		return Apellidos;
	}

	public void setApellidos(String apellidos) {
		Apellidos = apellidos;
	}

	public String getDireccion() {
		return Direccion;
	}

	public void setDireccion(String direccion) {
		Direccion = direccion;
	}

	public String getTelefono() {
		return Telefono;
	}

	public void setTelefono(String telefono) {
		Telefono = telefono;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public int getId_Categoria() {
		return Id_Categoria;
	}

	public void setId_Categoria(int id_Categoria) {
		Id_Categoria = id_Categoria;
	}

	public String getObservaciones() {
		return Observaciones;
	}

	public void setObservaciones(String observaciones) {
		Observaciones = observaciones;
	}

	public int getImportado() {
		return Importado;
	}

	public void setImportado(int importado) {
		Importado = importado;
	}

	public int getSincronizado() {
		return Sincronizado;
	}

	public void setSincronizado(int sincronizado) {
		Sincronizado = sincronizado;
	}
	
	
	
	
	
}
