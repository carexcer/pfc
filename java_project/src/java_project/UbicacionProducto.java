package java_project;

public class UbicacionProducto {

	private Integer IdUbicacion;
	private Integer IdProducto;
	private Integer Cantidad;
	
	public UbicacionProducto(){
		
		this.IdProducto = null;
		this.IdUbicacion = null;
		this.Cantidad = null;
		
	}

	public UbicacionProducto(Integer idUbicacion, Integer idProducto,
			Integer cantidad) {
		super();
		IdUbicacion = idUbicacion;
		IdProducto = idProducto;
		Cantidad = cantidad;
	}

	public Integer getIdUbicacion() {
		return IdUbicacion;
	}

	public void setIdUbicacion(Integer idUbicacion) {
		IdUbicacion = idUbicacion;
	}

	public Integer getIdProducto() {
		return IdProducto;
	}

	public void setIdProducto(Integer idProducto) {
		IdProducto = idProducto;
	}

	public Integer getCantidad() {
		return Cantidad;
	}

	public void setCantidad(Integer cantidad) {
		Cantidad = cantidad;
	}
	
}
