package java_project;

import java.util.Calendar;

public class LoteRecibido {
	
	private Integer IdLoteRecibido;
	private Integer IdProveedor;
	private Integer IdProducto;
	private Calendar FechaRecepcion;
	private Integer CantidadRecibida;
	private Float PrecioPagado;
	
	public LoteRecibido(){
		
	}
	
	public LoteRecibido(Integer idLoteRecibido, Integer idProveedor,
			Integer idProducto, Calendar fechaRecepcion, Integer cantidadRecibida,
			Float precioPagado) {
		super();
		IdLoteRecibido = idLoteRecibido;
		IdProveedor = idProveedor;
		IdProducto = idProducto;
		FechaRecepcion = fechaRecepcion;
		CantidadRecibida = cantidadRecibida;
		PrecioPagado = precioPagado;
	}

	public Integer getIdLoteRecibido() {
		return IdLoteRecibido;
	}

	public void setIdLoteRecibido(Integer idLoteRecibido) {
		IdLoteRecibido = idLoteRecibido;
	}

	public Integer getIdProveedor() {
		return IdProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		IdProveedor = idProveedor;
	}

	public Integer getIdProducto() {
		return IdProducto;
	}

	public void setIdProducto(Integer idProducto) {
		IdProducto = idProducto;
	}

	public Calendar getFechaRecepcion() {
		return FechaRecepcion;
	}

	public void setFechaRecepcion(Calendar fechaRecepcion) {
		FechaRecepcion = fechaRecepcion;
	}

	public Integer getCantidadRecibida() {
		return CantidadRecibida;
	}

	public void setCantidadRecibida(Integer cantidadRecibida) {
		CantidadRecibida = cantidadRecibida;
	}

	public Float getPrecioPagado() {
		return PrecioPagado;
	}

	public void setPrecioPagado(Float precioPagado) {
		PrecioPagado = precioPagado;
	}

}
