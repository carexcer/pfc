package java_project;

import java.util.Calendar;

public class LoteRecibido {
	
	private Integer idLoteRecibido;
	private Integer idProveedor;
	private Integer idProducto;
	private Calendar fechaRecepcion;
	private Integer cantidadRecibida;
	private Float precioCompraUnitario;
	private Integer stockProductoAux;
	
	
	public LoteRecibido(){
		
	}

	public LoteRecibido(Integer idLoteRecibido, Integer idProveedor,
			Integer idProducto, Calendar fechaRecepcion,
			Integer cantidadRecibida, Float precioCompraUnitario) {
		super();
		this.idLoteRecibido = idLoteRecibido;
		this.idProveedor = idProveedor;
		this.idProducto = idProducto;
		this.fechaRecepcion = fechaRecepcion;
		this.cantidadRecibida = cantidadRecibida;
		this.precioCompraUnitario = precioCompraUnitario;
	}

	public Integer getIdLoteRecibido() {
		return idLoteRecibido;
	}

	public void setIdLoteRecibido(Integer idLoteRecibido) {
		this.idLoteRecibido = idLoteRecibido;
	}

	public Integer getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Calendar getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Calendar fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public Integer getCantidadRecibida() {
		return cantidadRecibida;
	}

	public void setCantidadRecibida(Integer cantidadRecibida) {
		this.cantidadRecibida = cantidadRecibida;
	}

	public Float getPrecioCompraUnitario() {
		return precioCompraUnitario;
	}

	public void setPrecioCompraUnitario(Float precioCompraUnitario) {
		this.precioCompraUnitario = precioCompraUnitario;
	}
	
	public Integer getStockProductoAux() {
		return stockProductoAux;
	}

	public void setStockProductoAux(Integer stockProductoAux) {
		this.stockProductoAux = stockProductoAux;
	}


}
