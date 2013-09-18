package java_project;

public class Producto {

	private Integer idProducto;
	private Integer idCategoria;
	private Integer idMarca;
	private String nombreProducto;
	private Float precioMedioCompraUnitario;
	private Float precioMedioVentaUnitario;
	private String stock;
	private Integer cantidadStock;
	private Float peso;
	private String nombreCategoria;
	private String nombreMarca;
	
	public Producto(){
		
	}

	public Producto(Integer idProducto, Integer idCategoria, Integer idMarca,
			String nombreProducto, Float precioMedioCompraUnitario,
			Float precioMedioVentaUnitario, String stock, Integer cantidadStock,
			Float peso, String nombreCategoria, String nombreMarca) {
		super();
		this.idProducto = idProducto;
		this.idCategoria = idCategoria;
		this.idMarca = idMarca;
		this.nombreProducto = nombreProducto;
		this.precioMedioCompraUnitario = precioMedioCompraUnitario;
		this.precioMedioVentaUnitario = precioMedioVentaUnitario;
		this.stock = stock;
		this.cantidadStock = cantidadStock;
		this.peso = peso;
		this.nombreCategoria = nombreCategoria;
		this.nombreMarca = nombreMarca;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Integer getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public Float getPrecioMedioCompraUnitario() {
		return precioMedioCompraUnitario;
	}

	public void setPrecioMedioCompraUnitario(Float precioMedioCompraUnitario) {
		this.precioMedioCompraUnitario = precioMedioCompraUnitario;
	}

	public Float getPrecioMedioVentaUnitario() {
		return precioMedioVentaUnitario;
	}

	public void setPrecioMedioVentaUnitario(Float precioMedioVentaUnitario) {
		this.precioMedioVentaUnitario = precioMedioVentaUnitario;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public Integer getCantidadStock() {
		return cantidadStock;
	}

	public void setCantidadStock(Integer cantidadStock) {
		this.cantidadStock = cantidadStock;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public String getNombreMarca() {
		return nombreMarca;
	}

	public void setNombreMarca(String nombreMarca) {
		this.nombreMarca = nombreMarca;
	}
		
	
}
