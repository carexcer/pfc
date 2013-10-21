package java_project;

import java.util.Calendar;

public class Movimiento {

	private Integer IdMovimiento;
	private Integer IdProducto;
	private Integer IdUbicacion;
	private Integer IdLoteRecibido;
	private Integer IdVenta;
	private String ES;
	private Integer Cantidad;
	private Calendar FechaMovimiento;
	
	public Movimiento(){
		
	}

	public Movimiento(Integer idMovimiento, Integer idProducto,
			Integer idUbicacion, Integer idLoteRecibido, Integer idVenta,
			String eS, Integer cantidad, Calendar fechaMovimiento) {
		super();
		IdMovimiento = idMovimiento;
		IdProducto = idProducto;
		IdUbicacion = idUbicacion;
		IdLoteRecibido = idLoteRecibido;
		IdVenta = idVenta;
		ES = eS;
		Cantidad = cantidad;
		FechaMovimiento = fechaMovimiento;
	}

	public Integer getIdMovimiento() {
		return IdMovimiento;
	}

	public void setIdMovimiento(Integer idMovimiento) {
		IdMovimiento = idMovimiento;
	}

	public Integer getIdProducto() {
		return IdProducto;
	}

	public void setIdProducto(Integer idProducto) {
		IdProducto = idProducto;
	}

	public Integer getIdUbicacion() {
		return IdUbicacion;
	}

	public void setIdUbicacion(Integer idUbicacion) {
		IdUbicacion = idUbicacion;
	}

	public Integer getIdLoteRecibido() {
		return IdLoteRecibido;
	}

	public void setIdLoteRecibido(Integer idLoteRecibido) {
		IdLoteRecibido = idLoteRecibido;
	}

	public Integer getIdVenta() {
		return IdVenta;
	}

	public void setIdVenta(Integer idVenta) {
		IdVenta = idVenta;
	}

	public String getES() {
		return ES;
	}

	public void setES(String eS) {
		ES = eS;
	}

	public Integer getCantidad() {
		return Cantidad;
	}

	public void setCantidad(Integer cantidad) {
		Cantidad = cantidad;
	}

	public Calendar getFechaMovimiento() {
		return FechaMovimiento;
	}

	public void setFechaMovimiento(Calendar fechaMovimiento) {
		FechaMovimiento = fechaMovimiento;
	}
	
}
