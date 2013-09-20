package java_project;

public class Proveedor {
	
	Integer idProveedor;
	String nif;
	String nombreProveedor;
	String telefono;
	String email;
	String contacto;
	String direccion;
	
	public Proveedor(){
		idProveedor = null;
		nif = null;
		nombreProveedor = null;
		telefono = null;
		email = null;
		contacto = null;
		direccion = null;
	}
	
	
	public Proveedor(Integer idProveedor, String nif, String nombreProveedor,
			String telefono, String email, String contacto, String direccion) {
		super();
		this.idProveedor = idProveedor;
		this.nif = nif;
		this.nombreProveedor = nombreProveedor;
		this.telefono = telefono;
		this.email = email;
		this.contacto = contacto;
		this.direccion = direccion;
	}


	public Integer getIdProveedor() {
		return idProveedor;
	}


	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}


	public String getNif() {
		return nif;
	}


	public void setNif(String nif) {
		this.nif = nif;
	}


	public String getNombreProveedor() {
		return nombreProveedor;
	}


	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getContacto() {
		return contacto;
	}


	public void setContacto(String contacto) {
		this.contacto = contacto;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public void printInsertSQL(){
		System.out.println("INSERT INTO PROVEEDOR (id_proveedor, NIF, nombre_proveedor, telefono, email, direccion) VALUES ("
							+ idProveedor + ",'" + nif + "','" + nombreProveedor + "','" + telefono + "','" + email +  
							"','" + direccion+ "');\n");
	}
	public String getInsertSQL(){
		return "INSERT INTO PROVEEDOR (id_proveedor, NIF, nombre_proveedor, telefono, email, direccion) VALUES ("
							+ idProveedor + ",'" + nif + "','" + nombreProveedor + "','" + telefono + "','" + email +  
							"','" + direccion+ "');\n";
	}

}
