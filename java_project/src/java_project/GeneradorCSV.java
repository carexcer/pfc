package java_project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.JTextArea;

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
	
	/**Aplica el principio de pareto o regla del 20-80. El 20% de las veces devuelve true y el 80% false*/
	public boolean aplicarPareto(){
		if(Math.random() <= 0.2)
			return true;
		else return false;
	}
	/******************************PRODUCTOS************************************************************************/
	
	public void GenerarProductos() throws IOException{
		leerProductos(null);
		generarPreciosVentaProducto();
		generarCantidadStock();
		escribirProductos(null);
	}
	
	/**@param ruta del fichero de entrada que se abrirá para lectura
	 * @return número de productos leidos del fichero de entrada*/
	public int leerProductos(String ruta) throws NumberFormatException, IOException{
		
		if(ruta==null)
			ruta=rutaDefectoEntrada+"productos_entrada.csv";
		
		int numProdLeidos=0;
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
			p.setPrecioMedioCompraUnitario(Float.valueOf(campos[4]));
			p.setPrecioMedioVentaUnitario(Float.valueOf(campos[5]));
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
			p.setPrimario(aplicarPareto());
			listaProductos.add(p);			
			numProdLeidos++;
		}
		r.close();
		return numProdLeidos;
	}
	
	/**@param ruta del fichero de salida que se abrirá para escritura
	 * @return numero de productos que se han escrito en el fichero de salida*/
	public int escribirProductos(String ruta) throws IOException{
		
		if(ruta==null)
			ruta = rutaDefectoSalida+"productos_salida.csv" ;
		int numProdEscritos=0;
		generarPreciosVentaProducto();
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
					+ listaProductos.get(i).getPrecioMedioCompraUnitario() + ";"
					+ listaProductos.get(i).getPrecioMedioVentaUnitario() + ";"
					+ listaProductos.get(i).getStock() + ";"
					+ listaProductos.get(i).getCantidadStock() + ";"
					+ listaProductos.get(i).getPeso() + "\n"
			);
			numProdEscritos++;
		}
		w.close();
		return numProdEscritos;
		
	}
	
	/**@return devuelve el número de precios generados, es decir, uno por cada producto*/
	public int generarPreciosVentaProducto(){

		int numPreciosGenerados = 0;
		int numProd = listaProductos.size();
		for(int i=0; i<numProd; i++){
			float precioCompra = listaProductos.get(i).getPrecioMedioCompraUnitario();
			if(precioCompra <= 5){
				listaProductos.get(i).setPrecioMedioVentaUnitario((float) java.lang.Math.round(precioCompra*1.40));
			}else if(precioCompra > 5 && precioCompra <= 10 ){
				listaProductos.get(i).setPrecioMedioVentaUnitario((float) java.lang.Math.round(precioCompra*1.35));
			}else if(precioCompra > 10 && precioCompra <= 50){
				listaProductos.get(i).setPrecioMedioVentaUnitario((float) java.lang.Math.round(precioCompra*1.30));
			}else if(precioCompra > 50 && precioCompra <= 150){
				listaProductos.get(i).setPrecioMedioVentaUnitario((float) java.lang.Math.round(precioCompra*1.25));
			}else if(precioCompra > 150){
				listaProductos.get(i).setPrecioMedioVentaUnitario((float) java.lang.Math.round(precioCompra*1.20));
			}
			numPreciosGenerados++;
		}
		return numPreciosGenerados;
		
	}
	/**Genera una cantidad de stock aleatoria para cada producto.
	 * @return devuelve la cantidad total de productos que se ha generado*/
	public int generarCantidadStock(){
		
		Integer cantidadGenerada = 0;
		int numProd = listaProductos.size();
		for(int i=0; i < numProd; i++){
			String stock = listaProductos.get(i).getStock();
			Random rand = new Random();
			if(stock.equals("No")){//Productos que no tienen stock
				listaProductos.get(i).setCantidadStock(0);
			}else if(stock.equals("Si")){ //Productos que sí tienen stock
				
				if(listaProductos.get(i).getPrecioMedioCompraUnitario() <= 200){ //Productos <= 200€
					int cant = rand.nextInt(25)+1;								//Cantidad del producto en stock en el almacén
					cantidadGenerada += cant;
					listaProductos.get(i).setCantidadStock(cant);
					System.out.println("Cantidad Stock generada[" + i + "]= " + listaProductos.get(i).getCantidadStock());
				}else{															//Productos > 200€
					int cant = rand.nextInt(15)+1;       						//Cantidad del producto en stock en el almacén
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
		return cantidadGenerada;
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
	
	/**Genera los lotes_recibidos (no los escribe en ningún fichero)
	 * @param lotesPorProductoAlAño Recibe el numero de lotes recibidos al año por cada producto
	 * @param cantidadMinima es la cantidad minima recibida en cada lote
	 * @param cantidadMinima es la cantidad máxima recibida en cada lote
	 * @return Devuelve el numero de lotes_recibidos generados en total (para todos los productos y años)*/
	public int GenerarLotesRecibidos(Integer lotesPorProductoAlAño, Integer cantidadMinima, Integer cantidadMaxima) throws NumberFormatException, IOException{
		
		
		if(listaProductos.size()==0){
			leerProductos(null);
		}
		if(listaProveedores.size()==0)
			leerProveedores(null);
		if(cantidadMinima==null || cantidadMinima <=0 || cantidadMaxima==null || cantidadMaxima <=0  ||cantidadMinima > cantidadMaxima){//Cantidad minima por defecto
			cantidadMinima=10;
			cantidadMaxima=20;
		}
		System.out.println("Intervalo de cantidades: [" + cantidadMinima + ", " + cantidadMaxima + "]\n");
		int numProductos = listaProductos.size();
		int numLotesPorProducto;
		if(lotesPorProductoAlAño==null)
			numLotesPorProducto = 30;	//Numero de lotes por producto/año por defecto
		else numLotesPorProducto = lotesPorProductoAlAño;
		
		int numLotesPorProductoPrimario = (int) Math.round(numLotesPorProducto*0.8);
		int numLotesPorProductoSecundario = numLotesPorProducto - numLotesPorProductoPrimario;
		int minDiasEntrePedidosPrimarios = 365/numLotesPorProductoPrimario; //Numero de dias minimo entre la fecha de recepcion de 2 lotes de productos primarios
		int minDiasEntrePedidosSecundarios = 365/numLotesPorProductoSecundario; //Numero de dias minimo entre la fecha de recepcion de 2 lotes de productos secundarios
		int numLotesTotalPrimario = 0;
		int numLotesTotalSecundario = 0;
		
		int numProveedores = listaProveedores.size();
		int numLotes = 0;	//se actualiza luego cuando se han añadido todos los lotes
		Fecha fecha = new Fecha();			
		
		Random randCantidad = new Random();
		Random randPorcentaje = new Random();
		Random randProveedor = new Random();

		int numPrimarios = 0;
		int numSecundarios = 0;
		
		for(int i=0; i < numProductos; i++){ //Para cada producto			
			
			if(listaProductos.get(i).isPrimario()){ /////SI EL PRODUCTO ES PRIMARIO, ES DECIR, ES DEL 20% DE LOS IMPORTANTES
				
				numPrimarios++;
				numLotesTotalPrimario+=numLotesPorProductoPrimario;
				for(int j=0; j < numLotesPorProductoPrimario; j++){ //PARA CADA LOTE DE UN DETERMINADO PRODUCTO PRIMARIO
					
					int cantidad=0;
					do{//Genero una cantidad entre cantidadMinima y cantidadMaxima para cada lote, siendo cantidad multiplo de 10
						int max = cantidadMaxima/10;
						cantidad = randCantidad.nextInt(max)+1;
						cantidad = cantidad*10; //cantidad estará entre 10 y 50						
					}while(cantidad < cantidadMinima || cantidad > cantidadMaxima);
					
					LoteRecibido lote = new LoteRecibido();
					float ajuste = randPorcentaje.nextInt(11)+1;
					ajuste = (float) (ajuste * 0.01);
					float porcent = (float) (0.94 + ajuste); // +/- 5% de variabilidad en el precio de compra unitario

					//el idLoteRecibido se generará luego
					int indexProveedor = randProveedor.nextInt(numProveedores);
					lote.setIdProveedor(listaProveedores.get(indexProveedor).getIdProveedor());
					lote.setIdProducto(listaProductos.get(i).getIdProducto());
					Calendar fechaRecepcion = Calendar.getInstance();

					int anyo;
					double probabilidadAnyo = Math.random();
					if(probabilidadAnyo <0.39){	//38% de probabilidad de que sea un lote del año 2011  
						anyo=2011;
						fechaRecepcion.set(anyo, fecha.obtenerMesAleatorioPonderado()-1, fecha.obtenerDiaAleatorio(anyo)); //OJO, EL MES EN CALENDAR VA DE 0 A 11
					}else if(probabilidadAnyo >= 0.39 && probabilidadAnyo < 0.79){ //40% de probabilidad de que el lote sea de 2012
						anyo=2012;
						fechaRecepcion.set(anyo, fecha.obtenerMesAleatorioPonderado()-1, fecha.obtenerDiaAleatorio(anyo)); //OJO, EL MES EN CALENDAR VA DE 0 A 11
					}else{//22% de probabilidad de que sea un lote del año 2013
						anyo=2013;
						fechaRecepcion.set(anyo, fecha.obtenerMesAleatorioPonderado(6)-1, fecha.obtenerDiaAleatorio(anyo)); //OJO, EL MES EN CALENDAR VA DE 0 A 11
					}

					/*TODO*/
					System.out.println("Antes de sumar: " + fechaRecepcion.get(Calendar.DAY_OF_MONTH)+ "-" + fechaRecepcion.get(Calendar.MONTH) + "-" + fechaRecepcion.get(Calendar.YEAR));
					fechaRecepcion.add(Calendar.DAY_OF_MONTH, minDiasEntrePedidosPrimarios);
					System.out.println("Despues de sumar: " + fechaRecepcion.get(Calendar.DAY_OF_MONTH)+ "-" + fechaRecepcion.get(Calendar.MONTH) + "-" + fechaRecepcion.get(Calendar.YEAR));
					lote.setFechaRecepcion(fechaRecepcion);
					lote.setCantidadRecibida(cantidad); //Guardo la cantidad generada para el lote
					float precioCompraAux = listaProductos.get(i).getPrecioMedioCompraUnitario()*porcent; //Aplica el porcentaje de variabilidad al precio
					precioCompraAux = (float) (Math.rint(precioCompraAux*100)/100); //Formatea el precio a 2 decimales
					lote.setPrecioCompraUnitario(precioCompraAux);
					listaLotesRecibidos.add(lote);
				}			
			}else{////////////////SI EL PRODUCTO NO ES PRIMARIO, ES DECIR, ES DEL 80% DE LOS NO-IMPORTANTES
				
				numSecundarios++;
				numLotesTotalSecundario+=numLotesPorProductoSecundario;
				for(int j=0; j < numLotesPorProductoSecundario; j++){ //PARA CADA LOTE DE UN DETERMINADO PRODUCTO SECUNDARIO

					int cantidad=0;
					do{//Genero una cantidad entre cantidadMinima y cantidadMaxima para cada lote, siendo cantidad multiplo de 10
						int max = cantidadMaxima/10;
						cantidad = randCantidad.nextInt(max)+1;
						cantidad = cantidad*10; //cantidad estará entre 10 y 50						
					}while(cantidad < cantidadMinima || cantidad > cantidadMaxima);
					
					LoteRecibido lote = new LoteRecibido();
					float ajuste = randPorcentaje.nextInt(11)+1;
					ajuste = (float) (ajuste * 0.01);
					float porcent = (float) (0.94 + ajuste); // +/- 5% de variabilidad en el precio de compra unitario

					//el idLoteRecibido se generará luego
					int indexProveedor = randProveedor.nextInt(numProveedores);
					lote.setIdProveedor(listaProveedores.get(indexProveedor).getIdProveedor());
					lote.setIdProducto(listaProductos.get(i).getIdProducto());
					Calendar fechaRecepcion = Calendar.getInstance();

					int anyo;
					double probabilidadAnyo = Math.random();
					if(probabilidadAnyo <0.39){	//38% de probabilidad de que sea un lote del año 2011  
						anyo=2011;
						fechaRecepcion.set(anyo, fecha.obtenerMesAleatorioPonderado()-1, fecha.obtenerDiaAleatorio(anyo)); //OJO, EL MES EN CALENDAR VA DE 0 A 11
					}else if(probabilidadAnyo >= 0.39 && probabilidadAnyo < 0.79){ //40% de probabilidad de que el lote sea de 2012
						anyo=2012;
						fechaRecepcion.set(anyo, fecha.obtenerMesAleatorioPonderado()-1, fecha.obtenerDiaAleatorio(anyo)); //OJO, EL MES EN CALENDAR VA DE 0 A 11
					}else{//22% de probabilidad de que sea un lote del año 2013
						anyo=2013;
						fechaRecepcion.set(anyo, fecha.obtenerMesAleatorioPonderado(6)-1, fecha.obtenerDiaAleatorio(anyo)); //OJO, EL MES EN CALENDAR VA DE 0 A 11
					}

					/*TODO*/
					fechaRecepcion.add(Calendar.DAY_OF_MONTH, minDiasEntrePedidosSecundarios);
					lote.setFechaRecepcion(fechaRecepcion);
					lote.setCantidadRecibida(cantidad); //Guardo la cantidad generada para el lote
					float precioCompraAux = listaProductos.get(i).getPrecioMedioCompraUnitario()*porcent; //Aplica el porcentaje de variabilidad al precio
					precioCompraAux = (float) (Math.rint(precioCompraAux*100)/100); //Formatea el precio a 2 decimales
					lote.setPrecioCompraUnitario(precioCompraAux);
					listaLotesRecibidos.add(lote);
				}	
			}
		}
		System.out.println("========LOTES RECIBIDOS=========");
		numLotes = listaLotesRecibidos.size();
		for(int i=0; i < numLotes; i++){
			listaLotesRecibidos.get(i).setIdLoteRecibido(i);
			System.out.println("idLote: " + listaLotesRecibidos.get(i).getIdLoteRecibido() + " ; " +
								"idProveedor: " + listaLotesRecibidos.get(i).getIdProveedor() + " ; " +
								"idProducto: " + listaLotesRecibidos.get(i).getIdProducto() + " ; " +
								"CantidadRecibida: " + listaLotesRecibidos.get(i).getCantidadRecibida() + " ; " +						
								"PrecioCompraUnitario: " + listaLotesRecibidos.get(i).getPrecioCompraUnitario() + " ; "
								);
		}
		System.out.println("Numero de productos primarios: " + numPrimarios);
		System.out.println("Numero de productos secundarios: " + numSecundarios);
		System.out.println("Numero de lotes primarios: " + numLotesTotalPrimario);
		System.out.println("Numero de lotes secundarios: " + numLotesTotalSecundario);
		return numLotes;		
		/*-------------- ATENCIÓN: EL MES EN CALENDAR VA DEL 0=ENERO AL 11=DICIEMBRE -----------------*/
	}
	
	/**@param ruta del fichero de salida que se abrirá para escritura
	 * @return numero de lotes que se han escrito en el fichero de salida*/
	public int escribirLotesRecibidos(String ruta) throws IOException{
		
		if(ruta==null)
			ruta = rutaDefectoSalida+"lotes_recibidos_salida.csv" ;
		int numLotesEscritos=0;
		File file = new File(ruta);
		FileOutputStream fsal = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fsal);
		Writer w = new BufferedWriter(osw);
		int numLotes = listaLotesRecibidos.size();
		for(int i=0; i<numLotes;i++){
			Calendar fechaAux = listaLotesRecibidos.get(i).getFechaRecepcion();
			int dia = fechaAux.get(Calendar.DAY_OF_MONTH);
			int mes = fechaAux.get(Calendar.MONTH)+1;
			int año = fechaAux.get(Calendar.YEAR);
			w.write(listaLotesRecibidos.get(i).getIdLoteRecibido() + ";"
					+ listaLotesRecibidos.get(i).getIdProveedor() + ";"
					+ listaLotesRecibidos.get(i).getIdProducto() + ";" 
					+ dia + "-" + mes +"-" + año + ";"
					+ listaLotesRecibidos.get(i).getCantidadRecibida() + ";"
					+ listaLotesRecibidos.get(i).getPrecioCompraUnitario() + "\n"
			);
			numLotesEscritos++;
		}
		w.close();
		return numLotesEscritos;
		
	}
	
	
	/*---------------------------------------PROVEEDORES------------------------------------------------------------*/
	/**@return numero de proveedores que se han leido del fichero de entrada*/
	public int leerProveedores(String ruta) throws IOException{
		
		if(ruta==null)
			ruta=rutaDefectoEntrada+"proveedores_entrada.csv";
		
		int numProvLeidos=0;
		File file = new File(ruta);
		FileInputStream fent = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fent);
		BufferedReader r = new BufferedReader(isr);
		String linea;
		
		while((linea = r.readLine()) != null){			
			String[] campos = linea.split(";");			
			System.out.println("Campo[0]: " + campos[0] + "; Campo[1]: " + campos[1]);
			Proveedor p = new Proveedor();
			p.setIdProveedor(Integer.valueOf(campos[0]));
			p.setNif(campos[1]);			
			p.setNombreProveedor(campos[2]);
			p.setTelefono(campos[3]);
			p.setEmail(campos[4]);
			p.setContacto(campos[5]);
			p.setDireccion(campos[6]);
			listaProveedores.add(p);			
			numProvLeidos++;
		}
		r.close();
		return numProvLeidos;
		
	}
	
	/*------------------------------------------------VENTAS--------------------------------------------------------*/
	public int leerLotesRecibidos(String ruta) throws IOException{
		
		if(ruta==null)
			ruta=rutaDefectoSalida+"lotes_recibidos_salida.csv"; //Leemos de la ruta por defecto de salida
		
		int numLotesLeidos=0;
		File file = new File(ruta);
		FileInputStream fent = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fent);
		BufferedReader r = new BufferedReader(isr);
		String linea;
		
		while((linea = r.readLine()) != null){			
			String[] campos = linea.split(";");			
			System.out.println("Campo[0]: " + campos[0] + "; Campo[1]: " + campos[1]);
			LoteRecibido lote = new LoteRecibido();
			lote.setIdLoteRecibido(Integer.valueOf(campos[0]));
			lote.setIdProveedor(Integer.valueOf(campos[1]));			
			lote.setIdProducto(Integer.valueOf(campos[2]));
			lote.setFechaRecepcion(Fecha.stringToCalendar(campos[3]));
			lote.setCantidadRecibida(Integer.valueOf(campos[4]));
			lote.setPrecioCompraUnitario(Float.valueOf(campos[5]));
			listaLotesRecibidos.add(lote);			
			numLotesLeidos++;
		}
		r.close();
		return numLotesLeidos;
				
	}
	
	public int generarCantidadesVendidas(){
		
		int numCantidadesGeneradas=0;
		
		
		return numCantidadesGeneradas;		
	}
	
	public int generarPreciosVenta(){
	
		int numPreciosGenerados=0;
		
		return numPreciosGenerados;
	}
	
	public int generarFechasVenta(){
	
		int numFechasGeneradas=0;
		
		return numFechasGeneradas;
	}
	/**Genera las ventas a partir de los lotes recibidos
	 * @param maxCantPorVenta - número máximo de cantidad vendida en cada venta
	 * @return numero de ventas generadas*/
	public int generarVentas(int maxCantPorVenta, int maxDiasVendiendose) throws IOException{
		
		if(listaLotesRecibidos==null || listaLotesRecibidos.size()==0)
			leerLotesRecibidos(null);
		
		int numVentasGeneradas = 0;
		int numLotes = listaLotesRecibidos.size();
		int indiceVenta=0;
		Random randCant = new Random();
		Random randDia = new Random();
		
		for(int i=0; i<numLotes; i++){						

			int cantidadLote = listaLotesRecibidos.get(i).getCantidadRecibida(); //Cantidad a repartir entre las ventas del producto
				
				do{

					Venta v = new Venta();
					int cantidadVendida;
					
					do{
						cantidadVendida = randCant.nextInt(maxCantPorVenta)+1;						
					}while(cantidadVendida > cantidadLote);		//Mientras la cantidadVendida generada sea superior a la cantidad que queda, genero otra cantidadVendida
					cantidadLote -= cantidadVendida;
					v.setCantidadVendida(cantidadVendida);

					v.setIdProducto(listaLotesRecibidos.get(i).getIdProducto()); //Id del producto vendido en la venta

					float precioCompra = listaLotesRecibidos.get(i).getPrecioCompraUnitario();
					float precioVenta = 0;
					if(precioCompra <= 5){
						precioVenta = (float) java.lang.Math.round(precioCompra*1.40);
					}else if(precioCompra > 5 && precioCompra <= 10 ){
						precioVenta = (float) java.lang.Math.round(precioCompra*1.35);
					}else if(precioCompra > 10 && precioCompra <= 50){
						precioVenta = (float) java.lang.Math.round(precioCompra*1.30);
					}else if(precioCompra > 50 && precioCompra <= 150){
						precioVenta = (float) java.lang.Math.round(precioCompra*1.25);
					}else if(precioCompra > 150){
						precioVenta = (float) java.lang.Math.round(precioCompra*1.20);
					}
					precioVenta -= 0.05;
					v.setPrecioVentaUnitario(precioVenta);								//Precio de la venta

					Calendar fechaLote = listaLotesRecibidos.get(i).getFechaRecepcion();
					Calendar fechaVenta = Calendar.getInstance();
					//Las ventas de un lote recibido se venden en los siguientes 45 días después de la fecha de recepción del lote
					fechaVenta.set(fechaLote.get(Calendar.YEAR), fechaLote.get(Calendar.MONTH), fechaLote.get(Calendar.DAY_OF_MONTH)+randDia.nextInt(maxDiasVendiendose+1));
					v.setFechaVenta(fechaVenta);										//fecha de la venta

					v.setIdVenta(indiceVenta);									//id de la venta
					
					listaVentas.add(v);
					indiceVenta++;
					numVentasGeneradas++;
				
				}while(cantidadLote!=0);		//Cuando la suma de las cantidades vendidas sea igual a la cantidad del lote recibido, entonces paro.
			
		}
		
		return numVentasGeneradas;
	} 
	
	public Integer obtenerTotalCantidadVendida(){
		
		Integer suma=0;
		int numVentas = listaVentas.size();
		
		for(int i=0; i<numVentas; i++)
			suma += listaVentas.get(i).getCantidadVendida();
		return suma;
	}
	/**Escribe las ventas en un fichero de salida en la ruta dada
	 * @param ruta del fichero de salida
	 * @return numero de ventas escritas en el fichero de salida*/
	public int escribirVentas(String ruta) throws IOException{
		
		if(ruta==null)
			ruta = rutaDefectoSalida+"ventas_salida.csv" ;
		int numVentasEscritas=0;
		File file = new File(ruta);
		FileOutputStream fsal = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fsal);
		Writer w = new BufferedWriter(osw);
		int numVentas = listaVentas.size();
		for(int i=0; i<numVentas;i++){
			Calendar fechaAux = listaVentas.get(i).getFechaVenta();
			int dia = fechaAux.get(Calendar.DAY_OF_MONTH);
			int mes = fechaAux.get(Calendar.MONTH)+1;
			int año = fechaAux.get(Calendar.YEAR);
			w.write(listaVentas.get(i).getIdVenta() + ";"
					+ listaVentas.get(i).getIdProducto() + ";"
					+ listaVentas.get(i).getCantidadVendida() + ";" 
					+ listaVentas.get(i).getPrecioVentaUnitario() + ";"
					+ dia + "-" + mes +"-" + año + "\n"
			);
			numVentasEscritas++;
		}
		w.close();
		return numVentasEscritas;
		
		
	}
	/*------------------------------------------------OTROS----------------------------------------------------------*/
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
