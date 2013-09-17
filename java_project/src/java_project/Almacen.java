package java_project;

public class Almacen {

	private Integer IdAlmacen;
	private String NombreAlmacen;
	private String Ciudad;
	private String Direccion;
	private String Detalles;
	
	public Almacen(){
		
	}

	public Almacen(Integer idAlmacen, String nombreAlmacen, String ciudad,
			String direccion, String detalles) {
		super();
		IdAlmacen = idAlmacen;
		NombreAlmacen = nombreAlmacen;
		Ciudad = ciudad;
		Direccion = direccion;
		Detalles = detalles;
	}

	public Integer getIdAlmacen() {
		return IdAlmacen;
	}

	public void setIdAlmacen(Integer idAlmacen) {
		IdAlmacen = idAlmacen;
	}

	public String getNombreAlmacen() {
		return NombreAlmacen;
	}

	public void setNombreAlmacen(String nombreAlmacen) {
		NombreAlmacen = nombreAlmacen;
	}

	public String getCiudad() {
		return Ciudad;
	}

	public void setCiudad(String ciudad) {
		Ciudad = ciudad;
	}

	public String getDireccion() {
		return Direccion;
	}

	public void setDireccion(String direccion) {
		Direccion = direccion;
	}

	public String getDetalles() {
		return Detalles;
	}

	public void setDetalles(String detalles) {
		Detalles = detalles;
	}
	
	
}
