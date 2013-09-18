package java_project;

import java.util.Calendar;

public class Venta {
	
	private Integer idVenta;
	private Integer idProducto;
	private Integer cantidadVendida;
	private Float precioVentaUnitario;
	private Calendar fechaVenta;
	private String detalles;

	public Venta(){
		
	}

	public Venta(Integer idVenta, Integer idProducto, Integer cantidadVendida,
			Float precioVentaUnitario, Calendar fechaVenta, String detalles) {
		super();
		this.idVenta = idVenta;
		this.idProducto = idProducto;
		this.cantidadVendida = cantidadVendida;
		this.precioVentaUnitario = precioVentaUnitario;
		this.fechaVenta = fechaVenta;
		this.detalles = detalles;
	}

	public Integer getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Float getPrecioVentaUnitario() {
		return precioVentaUnitario;
	}

	public void setPrecioVentaUnitario(Float precioVentaUnitario) {
		this.precioVentaUnitario = precioVentaUnitario;
	}

	public Calendar getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Calendar fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public Integer getCantidadVendida() {
		return cantidadVendida;
	}

	public void setCantidadVendida(Integer cantidadVendida) {
		this.cantidadVendida = cantidadVendida;
	}
	
}