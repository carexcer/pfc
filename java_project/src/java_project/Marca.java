package java_project;

public class Marca {

	Integer IdMarca;
	String NombreComercial;
	String NombreCompleto;
	String Descripcion;
	
	public Marca(){
		IdMarca = null;
		NombreComercial = null;
		NombreCompleto = null;
		Descripcion = null;
	}
	public Marca(Integer idMarca, String nombreComercial,
			String nombreCompleto, String descripcion) {
		super();
		IdMarca = idMarca;
		NombreComercial = nombreComercial;
		NombreCompleto = nombreCompleto;
		Descripcion = descripcion;
	}
	public Integer getIdMarca() {
		return IdMarca;
	}
	public void setIdMarca(Integer idMarca) {
		IdMarca = idMarca;
	}
	public String getNombreComercial() {
		return NombreComercial;
	}
	public void setNombreComercial(String nombreComercial) {
		NombreComercial = nombreComercial;
	}
	public String getNombreCompleto() {
		return NombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		NombreCompleto = nombreCompleto;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public void printInsertSQL(){
		System.out.println("INSERT INTO MARCA (id_marca, nombre_comercial, nombre_completo, descripcion) VALUES ("
							+ IdMarca + ",'" + NombreComercial + "','" + NombreCompleto + "','" + Descripcion + "');");
	}
	public String getInsertSQL(){
		return "INSERT INTO MARCA (id_marca, nombre_comercial, nombre_completo, descripcion) VALUES ("
							+ IdMarca + ",'" + NombreComercial + "','" + NombreCompleto + "','" + Descripcion + "');\n";
	}
}
