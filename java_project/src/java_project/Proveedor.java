package java_project;

public class Proveedor {
	
	Integer IdProveedor;
	String NIF;
	String Nombre;
	String Telefono;
	String Email;
	String Direccion;
	
	public Proveedor(){
		IdProveedor = null;
		NIF = null;
		Nombre = null;
		Telefono = null;
		Email = null;
		Direccion = null;
	}

	public Proveedor(Integer idProveedor, String nIF, String nombre,
			String telefono, String email, String direccion) {
		super();
		IdProveedor = idProveedor;
		NIF = nIF;
		Nombre = nombre;
		Telefono = telefono;
		Email = email;
		Direccion = direccion;
	}

	public Integer getIdProveedor() {
		return IdProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		IdProveedor = idProveedor;
	}

	public String getNIF() {
		return NIF;
	}

	public void setNIF(String nIF) {
		NIF = nIF;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
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

	public String getDireccion() {
		return Direccion;
	}

	public void setDireccion(String direccion) {
		Direccion = direccion;
	}
	public void printInsertSQL(){
		System.out.println("INSERT INTO PROVEEDOR (id_proveedor, NIF, nombre, telefono, email, direccion) VALUES ("
							+ IdProveedor + ",'" + NIF + "','" + Nombre + "','" + Telefono + "','" + Email +  
							"','" + Direccion+ "');\n");
	}
	public String getInsertSQL(){
		return "INSERT INTO PROVEEDOR (id_proveedor, NIF, nombre, telefono, email, direccion) VALUES ("
							+ IdProveedor + ",'" + NIF + "','" + Nombre + "','" + Telefono + "','" + Email +  
							"','" + Direccion+ "');\n";
	}

}
