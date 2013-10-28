package java_project;

import java.util.ArrayList;

public class ListaProductos {

	private ArrayList<Producto> lista;
	private Integer numElementos;
	private float pesoTotal;
	private float valorCompraTotal;
	private float valorVentaTotal;
	private Integer cantidadStockTotal;
	private Integer numPrimariosTotal;
	private Integer numSecundariosTotal;
	private Integer numProductosConStock;
	private Integer numProductosSinStock;
	private float cantidadStockMedio;
	
	public ListaProductos(){
		lista = new ArrayList<Producto>();
		numElementos=0;
		pesoTotal= 0;
		valorCompraTotal=0;
		valorVentaTotal=0;
		cantidadStockTotal=0;
		numPrimariosTotal=0;
		numSecundariosTotal=0;
		numProductosConStock=0;
		numProductosSinStock=0;
		cantidadStockMedio=0;
	}

	public ListaProductos(ArrayList<Producto> lprod){	
		lista = lprod;
		numElementos = lprod.size();
		pesoTotal= 0;
		valorCompraTotal=0;
		valorVentaTotal=0;
		cantidadStockTotal=0;
		numPrimariosTotal=0;
		numSecundariosTotal=0;
		numProductosConStock=0;
		numProductosSinStock=0;
		cantidadStockMedio=0;
		calcularDatos();
	}
	
	public void calcularDatos(){
		
		for(int i=0; i<numElementos; i++){
			pesoTotal += lista.get(i).getPeso() * lista.get(i).getCantidadStock();
			cantidadStockTotal += lista.get(i).getCantidadStock();
			valorCompraTotal += lista.get(i).getPrecioMedioCompraUnitario() + lista.get(i).getCantidadStock();
			valorVentaTotal += lista.get(i).getPrecioMedioVentaUnitario() + lista.get(i).getCantidadStock();
			
			
		}
	}
	public ListaProductos(ArrayList<Producto> lista, Integer numElementos,
			float pesoTotal, float precioCompraTotal, float precioVentaTotal,
			Integer cantidadStockTotal, Integer numPrimariosTotal,
			Integer numSecundariosTotal, Integer numProductoConStock,
			Integer numProductosSinStock, float cantidadStockMedio,
			float precioCompraMedio, float precioVentaMedio) {
		super();
		this.lista = lista;
		this.numElementos = numElementos;
		this.pesoTotal = pesoTotal;
		this.setValorCompraTotal(precioCompraTotal);
		this.setValorVentaTotal(precioVentaTotal);
		this.cantidadStockTotal = cantidadStockTotal;
		this.numPrimariosTotal = numPrimariosTotal;
		this.numSecundariosTotal = numSecundariosTotal;
		this.numProductosConStock = numProductoConStock;
		this.numProductosSinStock = numProductosSinStock;
		this.cantidadStockMedio = cantidadStockMedio;
	}

	public ArrayList<Producto> getLista() {
		return lista;
	}

	public void setLista(ArrayList<Producto> lista) {
		this.lista = lista;
	}

	public Integer getNumElementos() {
		return numElementos;
	}

	public void setNumElementos(Integer numElementos) {
		this.numElementos = numElementos;
	}

	public float getPesoTotal() {
		return pesoTotal;
	}

	public void setPesoTotal(float pesoTotal) {
		this.pesoTotal = pesoTotal;
	}

	public Integer getCantidadStockTotal() {
		return cantidadStockTotal;
	}

	public void setCantidadStockTotal(Integer cantidadStockTotal) {
		this.cantidadStockTotal = cantidadStockTotal;
	}

	public Integer getNumPrimariosTotal() {
		return numPrimariosTotal;
	}

	public void setNumPrimariosTotal(Integer numPrimariosTotal) {
		this.numPrimariosTotal = numPrimariosTotal;
	}

	public Integer getNumSecundariosTotal() {
		return numSecundariosTotal;
	}

	public void setNumSecundariosTotal(Integer numSecundariosTotal) {
		this.numSecundariosTotal = numSecundariosTotal;
	}

	public Integer getNumProductoConStock() {
		return numProductosConStock;
	}

	public void setNumProductoConStock(Integer numProductoConStock) {
		this.numProductosConStock = numProductoConStock;
	}

	public Integer getNumProductosSinStock() {
		return numProductosSinStock;
	}

	public void setNumProductosSinStock(Integer numProductosSinStock) {
		this.numProductosSinStock = numProductosSinStock;
	}

	public float getCantidadStockMedio() {
		return cantidadStockMedio;
	}

	public void setCantidadStockMedio(float cantidadStockMedio) {
		this.cantidadStockMedio = cantidadStockMedio;
	}

	public float getValorCompraTotal() {
		return valorCompraTotal;
	}

	public void setValorCompraTotal(float valorCompraTotal) {
		this.valorCompraTotal = valorCompraTotal;
	}

	public float getValorVentaTotal() {
		return valorVentaTotal;
	}

	public void setValorVentaTotal(float valorVentaTotal) {
		this.valorVentaTotal = valorVentaTotal;
	}	
	
}
