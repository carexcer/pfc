package java_project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class GeneradorCSV {

	String rutaDefectoEntrada = "/home/carlos/pfc/pfc/entradas_app/";
	String rutaDefectoSalida = "/home/carlos/pfc/pfc/salidas_app/";
	ArrayList<Marca> listaMarcas ;
	ArrayList<Categoria> listaCategorias ;
	ArrayList<Producto> listaProductos ;
	ArrayList<LoteRecibido> listaLotesRecibidos;
	ArrayList<Proveedor> listaProveedores;
	ArrayList<Almacen> listaAlmacenes;	
	ArrayList<Venta> listaVentas;
	
	public GeneradorCSV(){
		this.listaCategorias = new ArrayList<Categoria>();
		this.listaMarcas = new ArrayList<Marca>();
		this.listaProductos = new ArrayList<Producto>();
		this.listaLotesRecibidos = new ArrayList<LoteRecibido>();
		this.listaProveedores = new ArrayList<Proveedor>();
		this.listaAlmacenes= new ArrayList<Almacen>();
		this.listaVentas = new ArrayList<Venta>();

	}
	/******************************PRODUCTOS************************************************************************/
	public void GenerarProductos(String ruta) throws IOException{
		leerProductos(ruta);
		generarPreciosVenta();
		generarCantidadStock();
		escribirProductos(ruta);
	}
	public void leerProductos(String ruta) throws NumberFormatException, IOException{
		if(ruta==null)
			ruta=rutaDefectoEntrada+"productos_entrada.csv";
		
		File file = new File(ruta);
		FileInputStream fent = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fent);
		BufferedReader r = new BufferedReader(isr);
		String linea;
		
		while((linea = r.readLine()) != null){
			String[] campos = linea.split(";");
			if(campos[0].equals("999"))
				break;
			System.out.println("Campo[0]: " + campos[0] + "; Campo[1]: " + campos[1]);
			Producto p = new Producto();
			p.setIdProducto(Integer.valueOf(campos[0]));
			p.setIdCategoria(Integer.valueOf(campos[1]));			
			p.setIdMarca(Integer.valueOf(campos[2]));
			p.setNombreProducto(campos[3]);
			p.setPrecioCompraUnitario(Float.valueOf(campos[4]));
			p.setPrecioVentaUnitario(Float.valueOf(campos[5]));
			p.setStock(campos[6]);
//			if(campos[6].equals("No"))
//				p.setCantidadStock(0);
//			else if(campos[6].equals("Si")){
//				Random rand = new Random();
//				p.setCantidadStock(rand.nextInt(20)+1);
//			}else{
//				p.setCantidadStock(999);
//			}
			p.setPeso(Float.valueOf(campos[8]));
			listaProductos.add(p);
			
		}
		r.close();
	}
	public void escribirProductos(String ruta) throws IOException{
		
		if(ruta==null)
			ruta = rutaDefectoSalida+"productos_salida.csv" ;
		generarPreciosVenta();
		File file = new File(ruta);
		FileOutputStream fsal = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fsal);
		Writer w = new BufferedWriter(osw);
		int numProd = listaProductos.size();
		for(int i=0; i<numProd;i++){
			w.write(listaProductos.get(i).getIdProducto() + ";"
					+ listaProductos.get(i).getIdCategoria() + ";"
					+ listaProductos.get(i).getIdMarca() + ";" 
					+ listaProductos.get(i).getNombreProducto() + ";"
					+ listaProductos.get(i).getPrecioCompraUnitario() + ";"
					+ listaProductos.get(i).getPrecioVentaUnitario() + ";"
					+ listaProductos.get(i).getStock() + ";"
					+ listaProductos.get(i).getCantidadStock() + ";"
					+ listaProductos.get(i).getPeso() + "\n"
			);
			
		}
		w.close();
		
	}
	public void generarPreciosVenta(){

		int numProd = listaProductos.size();
		for(int i=0; i<numProd; i++){
			float precioCompra = listaProductos.get(i).getPrecioCompraUnitario();
			if(precioCompra <= 5){
				listaProductos.get(i).setPrecioVentaUnitario((float) java.lang.Math.round(precioCompra*1.45));
			}else if(precioCompra > 5 && precioCompra <= 10 ){
				listaProductos.get(i).setPrecioVentaUnitario((float) java.lang.Math.round(precioCompra*1.40));
			}else if(precioCompra > 10 && precioCompra <= 50){
				listaProductos.get(i).setPrecioVentaUnitario((float) java.lang.Math.round(precioCompra*1.35));
			}else if(precioCompra > 50 && precioCompra <= 150){
				listaProductos.get(i).setPrecioVentaUnitario((float) java.lang.Math.round(precioCompra*1.25));
			}else if(precioCompra > 150){
				listaProductos.get(i).setPrecioVentaUnitario((float) java.lang.Math.round(precioCompra*1.20));
			}
		}
		
	}
	public void generarCantidadStock(){
		
		Integer cantidadGenerada = 0;
		int numProd = listaProductos.size();
		for(int i=0; i < numProd; i++){
			String stock = listaProductos.get(i).getStock();
			Random rand = new Random();
			if(stock.equals("No")){
				listaProductos.get(i).setCantidadStock(0);
			}else if(stock.equals("Si")){
				
				if(listaProductos.get(i).getPrecioCompraUnitario() <= 200){
					int cant = rand.nextInt(25)+1;
					cantidadGenerada += cant;
					listaProductos.get(i).setCantidadStock(cant);
					System.out.println("Cantidad Stock generada[" + i + "]= " + listaProductos.get(i).getCantidadStock());
				}else{
					int cant = rand.nextInt(15)+1;
					cantidadGenerada += cant;
					listaProductos.get(i).setCantidadStock(cant);
					System.out.println("Cantidad Stock generada[" + i + "]= " + listaProductos.get(i).getCantidadStock());
				}
			}else{
				System.out.println("Error generando cantidad de stock. El valor Stock no tiene valor 'Si' ni 'No'");
				listaProductos.get(i).setCantidadStock(10);
			}
			
		}
		System.out.println("Cantidad total generada de productos: " + cantidadGenerada);
	}
	
	/*****************************LOTES RECIBIDOS
	 * @throws IOException 
	 * @throws NumberFormatException *******************************************************************/
	
	public Integer obtenerTotalCantidadRecibida(){
		
		Integer suma=0;
		int numLotes = listaLotesRecibidos.size();
		
		for(int i=0; i<numLotes; i++)
			suma += listaLotesRecibidos.get(i).getCantidadRecibida();
		
		return suma;
	}
	
	public Integer obtenerTotalCantidadVendida(){
		
		Integer suma=0;
		int numVentas = listaVentas.size();
		
		for(int i=0; i<numVentas; i++)
			suma += listaVentas.get(i).getCantidadVendida();
		
		return suma;
	}
	
	public void GenerarLotesRecibidos(String ruta) throws NumberFormatException, IOException{
	
		if(ruta==null)
			ruta = rutaDefectoEntrada+"lotes_recibidos_entrada.csv";
		
		if(listaProductos==null){
			leerProductos(null);
		}
		Date fechaHoy = new Date(0);
		Calendar calendario = Calendar.getInstance();
		calendario.set(2013, 9, 05);
		System.out.println("**** LOTES RECIBIDOS ****");
		System.out.println(calendario.YEAR + "-" + calendario.MONTH + "-" + calendario.DAY_OF_MONTH);
	}
	
	/****************************************************************************************************************/
	public void leerMarcas(String ruta) throws IOException{
		
		if(ruta==null)
			ruta=rutaDefectoEntrada+"marcas_entrada.csv";
		
		File file = new File(ruta);
		FileInputStream fent = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fent);
		BufferedReader r = new BufferedReader(isr);
		String linea;
		while((linea = r.readLine()) != null){
			String[] campos = linea.split(";");
			if(campos[0].equals("999"))
				break;
			Marca m = new Marca();
			m.setIdMarca(Integer.parseInt(campos[0]));
			m.setNombre(campos[1]);
			listaMarcas.add(m);
			
		}
		r.close();
	}
	public void leerCategorias(String ruta) throws IOException{
		
		if(ruta==null)
			ruta=rutaDefectoEntrada+"categorias_entrada.csv";
		
		File file = new File(ruta);
		FileInputStream fent = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fent);
		BufferedReader r = new BufferedReader(isr);
		String linea;
		while((linea = r.readLine()) != null){
			String[] campos = linea.split(";");
			if(campos[0].equals("999"))
				break;
			Categoria cat = new Categoria();
			cat.setIdCategoria(Integer.parseInt(campos[0]));
			cat.setNombre(campos[1]);
			listaCategorias.add(cat);
			
		}
		r.close();
	}
	/*******************NO SE USA******************/
	public void NombrePorIdCategoria(String ruta) throws IOException{
//		if(ruta==null)
//			ruta=rutaDefecto;
		ruta="/home/carlos/";
//		leerProductos(rutaDefecto+"pfc/csv/productos-bueno-5.csv");
//		leerCategorias(rutaDefecto+"pfc/csv/categorias-sin-cabecera.csv");
		File file = new File(ruta+"prueba_categorias_producto.csv");
		FileOutputStream fsal = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fsal);
		Writer w = new BufferedWriter(osw);
		int numProd = listaProductos.size();
		int numCat = listaCategorias.size();
		for(int i=0; i<numProd;i++){
			for(int j=0; j<numCat;j++){
				System.out.println("NomCat(producto): " + listaProductos.get(i).getNombreCategoria() + " ; " +
									"NomCat(categoria): " + listaCategorias.get(j).getNombre());
			if(listaProductos.get(i).getNombreCategoria().equals(listaCategorias.get(j).getNombre())){
				listaProductos.get(i).setIdCategoria(listaCategorias.get(j).getIdCategoria());
				w.write(listaProductos.get(i).getIdProducto() + ";" + listaProductos.get(i).getIdCategoria()+"\n");
				break;
			}
			
			}
		}
		w.close();
	}
	/*******************NO SE USA******************/
	public void NombrePorIdMarca(String ruta) throws IOException{
		ruta="/home/carlos/";
//		leerProductos(rutaDefecto+"pfc/csv/productos-bueno-5.csv");
//		leerMarcas(rutaDefecto+"pfc/csv/marcas-sin-cabecera.csv");
		File file = new File(ruta+"prueba_marcas_producto.csv");
		FileOutputStream fsal = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fsal);
		Writer w = new BufferedWriter(osw);
		int numProd = listaProductos.size();
		int numMar = listaMarcas.size();
		for(int i=0; i<numProd;i++){
			for(int j=0; j<numMar;j++){
				System.out.println("NomMarca(producto): " + listaProductos.get(i).getNombreMarca() + " ; " +
									"NomMarca(Marca): " + listaMarcas.get(j).getNombre());
			if(listaProductos.get(i).getNombreMarca().equals(listaMarcas.get(j).getNombre())){
				listaProductos.get(i).setIdMarca(listaMarcas.get(j).getIdMarca());
				w.write(listaProductos.get(i).getIdProducto() + ";" + listaProductos.get(i).getIdMarca()+"\n");
				break;
			}
			
			}
		}
		w.close();
	}
	/*******************NO SE USA******************/
	public void leerProductosConNombreEnCategoriaYMarca(String ruta) throws IOException{
		
		if(ruta==null)
			ruta=rutaDefectoEntrada+"productos_entrada.csv";//esta linea esta mal la ruta
		
		File file = new File(ruta);
		FileInputStream fent = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fent);
		BufferedReader r = new BufferedReader(isr);
		String linea;
		
		while((linea = r.readLine()) != null){
			String[] campos = linea.split(";");
			if(campos[0].equals("999"))
				break;
			System.out.println("Campo[0]: " + campos[0] + "; Campo[1]: " + campos[1]);
			Producto p = new Producto();
			p.setIdProducto(Integer.valueOf(campos[0]));
			p.setNombreCategoria(campos[1]);			
			p.setNombreMarca(campos[2]);	
			listaProductos.add(p);
			
		}
		r.close();
		
	}

	
}
