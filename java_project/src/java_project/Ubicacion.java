package java_project;

public class Ubicacion {

	private Integer IdUbicacion;
	private Integer IdAlmacen;
	private String Zona;
	private Integer Estante;
	
	public Ubicacion(){
	
		this.IdUbicacion = null;
		this.IdAlmacen = null;
		this.Zona = null;
		this.Estante = null;
		
	}
	
	public Ubicacion(Integer idUbicacion, Integer idAlmacen, String zona,
			Integer estante) {
		super();
		IdUbicacion = idUbicacion;
		IdAlmacen = idAlmacen;
		Zona = zona;
		Estante = estante;
	}

	public Integer getIdUbicacion() {
		return IdUbicacion;
	}

	public void setIdUbicacion(Integer idUbicacion) {
		IdUbicacion = idUbicacion;
	}

	public Integer getIdAlmacen() {
		return IdAlmacen;
	}

	public void setIdAlmacen(Integer idAlmacen) {
		IdAlmacen = idAlmacen;
	}

	public String getZona() {
		return Zona;
	}

	public void setZona(String zona) {
		Zona = zona;
	}

	public Integer getEstante() {
		return Estante;
	}

	public void setEstante(Integer estante) {
		Estante = estante;
	}
	
	
	
}
