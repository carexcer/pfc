package java_project;

public class Categoria {

	private Integer idCategoria;
	private String nombreCategoria;
	
	public Categoria(){
		this.idCategoria = new Integer(999);
		this.nombreCategoria= new String("DefaultCategory");
	}
	
	public Categoria(Integer idCategoria, String nombre) {
		super();
		this.idCategoria = idCategoria;
		nombreCategoria = nombre;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombre() {
		return nombreCategoria;
	}

	public void setNombre(String nombre) {
		nombreCategoria = nombre;
	}
	
	
	
}
