package java_project;

public class Producto {

	private Integer idProducto;
	private Integer idCategoria;
	private Integer idMarca;
	private String nombreProducto;
	private Float precioCompraUnitario;
	private Float precioVentaUnitario;
	private String stock;
	private Integer cantidadStock;
	private Float peso;
	private String nombreCategoria;
	private String nombreMarca;
	
	public Producto(){
		
	}

	public Producto(Integer idProducto, Integer idCategoria, Integer idMarca,
			String nombreProducto, Float precioCompraUnitario,
			Float precioVentaUnitario, String stock, Integer cantidadStock,
			Float peso, String nombreCategoria, String nombreMarca) {
		super();
		this.idProducto = idProducto;
		this.idCategoria = idCategoria;
		this.idMarca = idMarca;
		this.nombreProducto = nombreProducto;
		this.precioCompraUnitario = precioCompraUnitario;
		this.precioVentaUnitario = precioVentaUnitario;
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

	public Float getPrecioCompraUnitario() {
		return precioCompraUnitario;
	}

	public void setPrecioCompraUnitario(Float precioCompraUnitario) {
		this.precioCompraUnitario = precioCompraUnitario;
	}

	public Float getPrecioVentaUnitario() {
		return precioVentaUnitario;
	}

	public void setPrecioVentaUnitario(Float precioVentaUnitario) {
		this.precioVentaUnitario = precioVentaUnitario;
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
