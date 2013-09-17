package java_project;

public class Marca {

	private Integer idMarca;
	private String nombreMarca;
	
	public Marca(){
		idMarca = null;
		nombreMarca = null;
	}
	public Marca(Integer idMarca,
			String nombre, String descripcion) {
		super();
		this.idMarca = idMarca;
		nombreMarca = nombre;
	}
	public Integer getIdMarca() {
		return idMarca;
	}
	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}
	public String getNombre() {
		return nombreMarca;
	}
	public void setNombre(String nombre) {
		nombreMarca = nombre;
	}
	public void printInsertSQL(){
		System.out.println("INSERT INTO MARCA (id_marca, nombre_marca) VALUES ("
							+ idMarca + ",'" + nombreMarca  + "');");
	}
	public String getInsertSQL(){
		return "INSERT INTO MARCA (id_marca, nombre_marca) VALUES ("
							+ idMarca + ",'" + nombreMarca + "');\n";
	}
}
