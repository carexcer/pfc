package java_project;

import igu.BarraProgreso;
import igu.poblar_bd;

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
import java.util.HashMap;
import java.util.Random;

import javax.swing.JOptionPane;

public class GeneradorCSV {

	ArrayList<Marca> listaMarcas ;
	ArrayList<Categoria> listaCategorias ;
	ArrayList<Producto> listaProductos ;
	ArrayList<LoteRecibido> listaLotesRecibidos;
	ArrayList<Proveedor> listaProveedores;
	ArrayList<Almacen> listaAlmacenes;	
	ArrayList<Venta> listaVentas;
	ArrayList<Ubicacion> listaUbicaciones;
	ArrayList<UbicacionProducto> listaUbicacionProducto;
	ArrayList<Movimiento> listaMovimientos;
	static public Calendar fechaLimite = Calendar.getInstance();		//Fecha limite para lotes y ventas
	static public int stockMaximoInicial = 12;											//Cantidad de Stock Medio Inicial de producto (osea en la tabla producto)
	static public int stockMinimo = 0;
	public static int maxUbicacionesDistintas=3;
	static public BarraProgreso progreso = new BarraProgreso();
	int numVentasParaProgressBar=0;
	int numLotesPrimarios=0;
	int numLotesSecundarios=0;
	ProbabilidadEstacional probabilidades = new ProbabilidadEstacional();
	

	public GeneradorCSV(){
		this.listaCategorias = new ArrayList<Categoria>();
		this.listaMarcas = new ArrayList<Marca>();
		this.listaProductos = new ArrayList<Producto>();
		this.listaLotesRecibidos = new ArrayList<LoteRecibido>();
		this.listaProveedores = new ArrayList<Proveedor>();
		this.listaAlmacenes= new ArrayList<Almacen>();
		this.listaVentas = new ArrayList<Venta>();
		this.listaUbicaciones = new ArrayList<Ubicacion>();
		this.listaUbicacionProducto = new ArrayList<UbicacionProducto>();
		this.listaMovimientos = new ArrayList<Movimiento>();
		fechaLimite.set(2013, Calendar.JUNE, 30); //Fecha limite por defecto. NINGUNA FECHA NI DE RECEPCION NI DE VENTA SUPERARÁ ESTA FECHA LÍMITE
		//		if(System.getProperty("os.name").contains("indows")){
		//			rutaDefectoSalida= "C:\\Users\\Carlos\\Documents\\pfc\\pfc\\salidas_app\\" ;
		//			rutaDefectoEntrada = "C:\\Users\\Carlos\\Documents\\pfc\\pfc\\entradas_app\\" ;
		//		}
	}

	public void flushAll(){

		this.listaCategorias = new ArrayList<Categoria>();
		this.listaMarcas = new ArrayList<Marca>();
		this.listaProductos = new ArrayList<Producto>();
		this.listaLotesRecibidos = new ArrayList<LoteRecibido>();
		this.listaProveedores = new ArrayList<Proveedor>();
		this.listaAlmacenes= new ArrayList<Almacen>();
		this.listaVentas = new ArrayList<Venta>();
		this.listaUbicaciones = new ArrayList<Ubicacion>();
		this.listaUbicacionProducto = new ArrayList<UbicacionProducto>();
		this.listaMovimientos = new ArrayList<Movimiento>();
	}
	public void flushCategorias(){
		this.listaCategorias = new ArrayList<Categoria>();
	}
	public void flushMarcas(){
		this.listaMarcas = new ArrayList<Marca>();
	}
	public void flushProveedores(){
		this.listaProveedores = new ArrayList<Proveedor>();
	}
	public void flushAlmacenes(){
		this.listaAlmacenes= new ArrayList<Almacen>();
	}
	public void flushLotesRecibidos(){
		this.listaLotesRecibidos = new ArrayList<LoteRecibido>();
	}
	public void flushProductos(){
		this.listaProductos = new ArrayList<Producto>();
	}
	public void flushVentas(){
		this.listaVentas = new ArrayList<Venta>();
	}
	public void flushUbicaciones(){
		this.listaUbicaciones = new ArrayList<Ubicacion>();
	}
	public void flushUbicacionProducto(){
		this.listaUbicacionProducto = new ArrayList<UbicacionProducto>();
	}
	public void flushMovimientos(){
		this.listaMovimientos = new ArrayList<Movimiento>();
	}

	/**Aplica el principio de pareto o regla del 20-80. El 20% de las veces devuelve true y el 80% false
	 * @return true el 20% de las veces y false el 80% de las veces*/
	public boolean aplicarPareto(){
		if(Math.random() <= 0.2)
			return true;
		else return false;
	}
	/**Aplica una probabilidad dada
	 * @param probabilidad de devolver true
	 * @return devuelve true con una probabilidad prob y false con una probabilidad 1-prob*/
	public boolean aplicarProbabilidad(float prob){
		if(Math.random() <= prob)
			return true;
		else return false;
	}

	/**Devuelve el anyo en funcion del vector de probabilidades de anyos que se le pasa. Se utiliza para establecer la tendencia de crecimiento anual. 
	 * @param recibe como parametro un vector con probabilidades para cada anyo. Por ejempo, con 3 anyos, podría recibir el vector [0.2, 0.3, 0.5]
	 * @return Devuelve un anyo entre 2011 y el anyo de la fecha limite.*/
	public int anyoPorProbabilidad(ArrayList<Float> probabilidadesDeAnyo){
		double rand = Math.random();
		double sum=0;
		for(int i=0; i<probabilidadesDeAnyo.size(); i++){
			sum+=probabilidadesDeAnyo.get(i);
			if(rand <= sum)
				return (i+2011);
		}
		return -1;
	}
	/******************************PRODUCTOS************************************************************************/

	//	public void GenerarProductos() throws IOException{
	//		leerProductos(null);
	//		generarPreciosVentaProducto();
	//		generarCantidadStock();
	//		escribirProductos(null);
	//	}

	/**@param ruta del fichero de entrada que se abrira para lectura
	 * @return numero de productos leidos del fichero de entrada*/
	public int leerProductos(String ruta, int prodPrimarios) throws NumberFormatException, IOException{

		Float porcentProdPrimarios = Float.valueOf(prodPrimarios);
		porcentProdPrimarios = porcentProdPrimarios/100;

		if(ruta==null)
			ruta=poblar_bd.getRutaEntrada()+"productos_entrada.csv";

		int numProdLeidos=0;
		File file = new File(ruta);
		FileInputStream fent = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fent);
		BufferedReader r = new BufferedReader(isr);
		String linea;
		Random rand = new Random();

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
			p.setCantidadStock(Math.max(stockMinimo, rand.nextInt(stockMaximoInicial+1)));										//Ponemos una cantidad de stock aleatoria "inicial"
			if(p.getCantidadStock()==0)
				p.setStock("No");
			else p.setStock("Si");
			//p.setStock(campos[6]);
//			p.setCantidadStock(rand.nextInt(stockMedioInicial));										//Ponemos una cantidad de stock aleatoria "inicial"
			//			if(campos[6].equals("No"))
			//				p.setCantidadStock(0);
			//			else if(campos[6].equals("Si")){
			//				Random rand = new Random();
			//				p.setCantidadStock(rand.nextInt(20)+1);
			//			}else{
			//				p.setCantidadStock(999);
			//			}
			p.setPeso(Float.valueOf(campos[8]));
			p.setPrimario(aplicarProbabilidad(porcentProdPrimarios));	//Aplicar probabilidad de que sea primario. True primario. False secundario.
			listaProductos.add(p);			
			numProdLeidos++;
			progreso.incrementar();
		}
		r.close();
		return numProdLeidos;
	}

	/**@param ruta del fichero de salida que se abrira para escritura
	 * @return numero de productos que se han escrito en el fichero de salida*/
	public int escribirProductos(String ruta) throws IOException{

		if(ruta==null)
			ruta = poblar_bd.getRutaSalida()+"productos_salida.csv" ;
		int numProdEscritos=0;
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
					+ listaProductos.get(i).isPrimario() + ";"
					+ listaProductos.get(i).getStock() + ";"
					+ listaProductos.get(i).getCantidadStock() + ";"
					+ listaProductos.get(i).getPeso() + "\n"
					);
			numProdEscritos++;

		}
		w.close();
		escribirProductosPrimarios(null);

		progreso.setTextStopped();
		return numProdEscritos;

	}
	/**@param ruta del fichero de salida que se abrirÃ¡ para escritura
	 * @return numero de productos PRIMARIOS que se han escrito en el fichero de salida*/
	public int escribirProductosPrimarios(String ruta) throws IOException{

		if(ruta==null)
			ruta = poblar_bd.getRutaSalida()+"productos_primarios_salida.csv" ;
		int numProdEscritos=0;
		//generarPreciosVentaProducto();
		File file = new File(ruta);
		FileOutputStream fsal = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fsal);
		Writer w = new BufferedWriter(osw);
		int numProd = listaProductos.size();
		for(int i=0; i<numProd;i++){
			if(listaProductos.get(i).isPrimario()){
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
		}
		w.close();
		return numProdEscritos;

	}
	
	/**@return devuelve el numero de precios generados, es decir, uno por cada producto*/
	public int generarPreciosVentaProducto(){

		int numPreciosGenerados = 0;
		int numProd = listaProductos.size();
		progreso.setMaximo(5972*2);
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
			progreso.incrementar();
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
			}else if(stock.equals("Si")){ //Productos que sÃ­ tienen stock

				if(listaProductos.get(i).getPrecioMedioCompraUnitario() <= 200){ //Productos <= 200â‚¬
					int cant = rand.nextInt(25)+1;								//Cantidad del producto en stock en el almacÃ©n
					cantidadGenerada += cant;
					listaProductos.get(i).setCantidadStock(cant);
					System.out.println("Cantidad Stock generada[" + i + "]= " + listaProductos.get(i).getCantidadStock());
				}else{															//Productos > 200â‚¬
					int cant = rand.nextInt(15)+1;       						//Cantidad del producto en stock en el almacÃ©n
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

	public Integer obtenerTotalCantidadRecibidaPorProducto(Integer idProducto){

		Integer suma=0;
		int numLotes = listaLotesRecibidos.size();

		for(int i=0; i<numLotes; i++){
			if(listaLotesRecibidos.get(i).getIdProducto().equals(idProducto))
				suma += listaLotesRecibidos.get(i).getCantidadRecibida();
		}
		return suma;
	}
	/**Genera los lotes_recibidos con fechas fijas y cantidades variables (no los escribe en ningun fichero)
	 * @param lotesPorProductoAlAnyo Recibe el numero de lotes recibidos al anyo por cada producto
	 * @param cantidadMinima es la cantidad minima recibida en cada lote
	 * @param cantidadMinima es la cantidad maxima recibida en cada lote
	 * @return Devuelve el numero de lotes_recibidos generados en total (para todos los productos y anyos)*/
	public int GenerarLotesRecibidosFechaFijaCantidadVariable(int numLotesPorProductoPrimarioAlMes, int numLotesSecundariosPorAnyo, Integer cantidadMinima, Integer cantidadMaxima, int movPrimarios) throws NumberFormatException, IOException{
		/*-------------- ATENCION: EL MES EN CALENDAR VA DEL 0=ENERO AL 11=DICIEMBRE -----------------*/

		//		Float porcentMovimientosPrimarios = Float.valueOf(movPrimarios);
		//		porcentMovimientosPrimarios = porcentMovimientosPrimarios/100;

		if(listaProductos.size()==0){
			throw new IOException();
		}
		if(listaProveedores.size()==0){
			leerProveedores(null);
		}
		if(cantidadMinima==null || cantidadMinima <=0 || cantidadMaxima==null || cantidadMaxima <=0  ||cantidadMinima > cantidadMaxima){//Cantidad minima por defecto
			JOptionPane.showMessageDialog(poblar_bd.getFrame(), "Error: Las cantidades minima y/o maxima no puede tomar ese valor.", null, JOptionPane.ERROR_MESSAGE);
			return -1;
		}
		System.out.println("Intervalo de cantidades: [" + cantidadMinima + ", " + cantidadMaxima + "]\n");
		int numProductos = listaProductos.size();
		int numLotesPorProducto = numLotesPorProductoPrimarioAlMes*12 + numLotesSecundariosPorAnyo;

		poblar_bd.getTextArea().append("Numero de lotes por producto primario (AL MES): " + numLotesPorProductoPrimarioAlMes + "\n");
		poblar_bd.getTextArea().append("Numero de lotes por producto secundario (AL AÑO): " + numLotesSecundariosPorAnyo + "\n");
		//TODO
		//		do{
		//			numLotesPorProductoPrimario = (int) Math.round(numLotesPorProducto*porcentMovimientosPrimarios);
		//			numLotesPorProductoSecundario = numLotesPorProducto - numLotesPorProductoPrimario;
		//			intentos--;
		//			if(intentos==0){
		//				JOptionPane.showMessageDialog(poblar_bd.getFrame(), "Excedido el numero de intentos para generar lotes con un " + movPrimarios + "% de movimientos. Por favor, elija otro porcentaje.");
		//			}
		//		}while((numLotesPorProductoPrimario!=15 || numLotesPorProductoPrimario!=30) && 
		//				((numLotesPorProductoSecundario%12!=1) || (numLotesPorProductoSecundario%12!=2) || (numLotesPorProductoSecundario%12!=3) || 
		//						(numLotesPorProductoSecundario%12!=4) || (numLotesPorProductoSecundario%12!=6) || (numLotesPorProductoSecundario%12!=0) ));

		int numLotesTotalPrimario = 0;
		int numLotesTotalSecundario = 0;
		int indiceLote = 0;

		int numProveedores = listaProveedores.size();
		int numLotes = 0;	

		Random randCantidad = new Random();
		Random randPorcentaje = new Random();
		Random randProveedor = new Random();

		int numPrimarios = 0;
		int numSecundarios = 0;

		progreso.setMaximo(numProductos * numLotesPorProducto);

		for(int i=0; i < numProductos; i++){ //Para cada producto			

			Integer cantidadTotalRecibida=0;//estos dos campos se usan para calcular la cantidad total recibida y el valor total comprado de cada producto, para sacar luego la media
			float valorTotalComprado= 0;
			
			if(listaProductos.get(i).isPrimario()){ /////SI EL PRODUCTO ES PRIMARIO, ES DECIR, ES DE LOS IMPORTANTES

				numPrimarios++;

				for(int anyoActual=2011; anyoActual <= getFechaLimite().get(Calendar.YEAR); anyoActual++){			//PARA CADA ANYO

					for(int mesActual=0; mesActual <=11; mesActual++){			//PARA CADA MES

						if(numLotesPorProductoPrimarioAlMes==2){
							LoteRecibido lote1 = new LoteRecibido();
							LoteRecibido lote2 = new LoteRecibido();

							Calendar fecha1 = Calendar.getInstance();
							Calendar fecha2 = Calendar.getInstance();

							fecha1.set(anyoActual, mesActual, 1);						//ESTABLEZCO LA FECHA DE LOS LOTES
							fecha2.set(anyoActual, mesActual, 15);

							lote1.setFechaRecepcion(fecha1);
							lote2.setFechaRecepcion(fecha2);

							int cantidad1=0;											//ESTABLEZCO LA CANTIDAD DE LOS LOTES
							do{//Genero una cantidad entre cantidadMinima y cantidadMaxima para cada lote, siendo cantidad multiplo de 10
								cantidad1 = randCantidad.nextInt(cantidadMaxima)+1;
								//								int max = cantidadMaxima/10;
								//								cantidad1 = randCantidad.nextInt(max)+1;
								//								cantidad1 = cantidad1*10; //cantidad estara entre 10 y 50						
							}while(cantidad1 < cantidadMinima || cantidad1 > cantidadMaxima);
							lote1.setCantidadRecibida(cantidad1); //Guardo la cantidad generada para el lote

							int cantidad2=0;
							do{//Genero una cantidad entre cantidadMinima y cantidadMaxima para cada lote, siendo cantidad multiplo de 10
								cantidad2 = randCantidad.nextInt(cantidadMaxima)+1;
								//								int max = cantidadMaxima/10;
								//								cantidad2 = randCantidad.nextInt(max)+1;
								//								cantidad2 = cantidad2*10; //cantidad estara entre 10 y cantidadMaxima						
							}while(cantidad2 < cantidadMinima || cantidad2 > cantidadMaxima);
							lote2.setCantidadRecibida(cantidad2); //Guardo la cantidad generada para el lote

							float ajuste1 = randPorcentaje.nextInt(11)+1;				//ESTABLEZCO LOS PRECIOS DE COMPRA
							float ajuste2 = randPorcentaje.nextInt(11)+1;
							ajuste1 = (float) (ajuste1 * 0.01);
							ajuste2 = (float) (ajuste2 * 0.01);
							float porcent1 = (float) (0.94 + ajuste1); // +/- 5% de variabilidad en el precio de compra unitario
							float porcent2 = (float) (0.94 + ajuste2); 

							float precioCompraAux1 = listaProductos.get(i).getPrecioMedioCompraUnitario()*porcent1; //Aplica el porcentaje de variabilidad al precio
							precioCompraAux1 = (float) (Math.rint(precioCompraAux1*100)/100); //Formatea el precio a 2 decimales
							lote1.setPrecioCompraUnitario(precioCompraAux1);

							float precioCompraAux2 = listaProductos.get(i).getPrecioMedioCompraUnitario()*porcent2; //Aplica el porcentaje de variabilidad al precio
							precioCompraAux2 = (float) (Math.rint(precioCompraAux2*100)/100); //Formatea el precio a 2 decimales
							lote2.setPrecioCompraUnitario(precioCompraAux2);

							int indexProveedor1 = randProveedor.nextInt(numProveedores);					//ESTABLEZO ID_PROVEEDOR E ID_PRODUCTO
							lote1.setIdProveedor(listaProveedores.get(indexProveedor1).getIdProveedor());
							lote1.setIdProducto(listaProductos.get(i).getIdProducto());

							int indexProveedor2 = randProveedor.nextInt(numProveedores);
							lote2.setIdProveedor(listaProveedores.get(indexProveedor2).getIdProveedor());
							lote2.setIdProducto(listaProductos.get(i).getIdProducto());

							lote1.setIdLoteRecibido(indiceLote);
							lote2.setIdLoteRecibido(indiceLote);

							lote1.setStockProductoAux(listaProductos.get(i).getCantidadStock());	//ESTABLEZCO EL ATRIBUTO AUXILIAR stockProductoAux, que utilizara para generar las ventas
							lote2.setStockProductoAux(listaProductos.get(i).getCantidadStock());

							if(lote1.getFechaRecepcion().before(fechaLimite)){
								int cantRecib = lote1.getCantidadRecibida();				//Acumulo en cantidad_stock del producto la cantidad recibida
								int cantAux = listaProductos.get(i).getCantidadStock();
								cantAux += cantRecib;
								listaProductos.get(i).setCantidadStock(cantAux);
								listaLotesRecibidos.add(lote1);								//ANYADO LOS LOTES A LA LISTA SI TIENEN FECHA ANTERIOR A LA FECHA LIMITE
								cantidadTotalRecibida += cantRecib;//Acumulamos para despues calcular la cantidad
								float auxValorTotalComprado =(cantRecib* precioCompraAux1); 
								valorTotalComprado += auxValorTotalComprado;
								numLotesTotalPrimario++;
								indiceLote++;
								numLotes++;	
							}
							if(lote2.getFechaRecepcion().before(fechaLimite)){
								int cantRecib = lote2.getCantidadRecibida();				//Acumulo en cantidad_stock del producto la cantidad recibida
								int cantAux = listaProductos.get(i).getCantidadStock();
								cantAux += cantRecib;
								listaProductos.get(i).setCantidadStock(cantAux);
								listaLotesRecibidos.add(lote2);				
								cantidadTotalRecibida += cantRecib;//Acumulamos para despues calcular la cantidad
								float auxValorTotalComprado =(cantRecib* precioCompraAux2); 
								valorTotalComprado += auxValorTotalComprado;
								numLotesTotalPrimario++;
								indiceLote++;
								numLotes++;
							}

							progreso.incrementar();

						}else if(numLotesPorProductoPrimarioAlMes==1){			//////////////////////// 1 LOTE/MES

							LoteRecibido lote1 = new LoteRecibido();

							Calendar fecha1 = Calendar.getInstance();

							fecha1.set(anyoActual, mesActual, 1);						//ESTABLEZCO LA FECHA DE LOS LOTES
							lote1.setFechaRecepcion(fecha1);

							int cantidad1=0;											//ESTABLEZCO LA CANTIDAD DE LOS LOTES
							do{//Genero una cantidad entre cantidadMinima y cantidadMaxima para cada lote, siendo cantidad multiplo de 10
								cantidad1 = randCantidad.nextInt(cantidadMaxima)+1;
								//								int max = cantidadMaxima/10;
								//								cantidad1 = randCantidad.nextInt(max)+1;
								//								cantidad1 = cantidad1*10; //cantidad estara entre 10 y 50						
							}while(cantidad1 < cantidadMinima || cantidad1 > cantidadMaxima);
							lote1.setCantidadRecibida(cantidad1); //Guardo la cantidad generada para el lote

							float ajuste1 = randPorcentaje.nextInt(11)+1;				//ESTABLEZCO LOS PRECIOS DE COMPRA
							ajuste1 = (float) (ajuste1 * 0.01);
							float porcent1 = (float) (0.94 + ajuste1); // +/- 5% de variabilidad en el precio de compra unitario

							float precioCompraAux1 = listaProductos.get(i).getPrecioMedioCompraUnitario()*porcent1; //Aplica el porcentaje de variabilidad al precio
							precioCompraAux1 = (float) (Math.rint(precioCompraAux1*100)/100); //Formatea el precio a 2 decimales
							lote1.setPrecioCompraUnitario(precioCompraAux1);

							int indexProveedor1 = randProveedor.nextInt(numProveedores);					//ESTABLEZO ID_PROVEEDOR E ID_PRODUCTO
							lote1.setIdProveedor(listaProveedores.get(indexProveedor1).getIdProveedor());
							lote1.setIdProducto(listaProductos.get(i).getIdProducto());

							lote1.setIdLoteRecibido(indiceLote);

							lote1.setStockProductoAux(listaProductos.get(i).getCantidadStock());	//ESTABLEZCO EL ATRIBUTO AUXILIAR stockProductoAux, que utilizara para generar las ventas

							if(lote1.getFechaRecepcion().before(fechaLimite)){
								int cantRecib = lote1.getCantidadRecibida();				//Acumulo en cantidad_stock del producto la cantidad recibida
								int cantAux = listaProductos.get(i).getCantidadStock();
								cantAux += cantRecib;
								listaProductos.get(i).setCantidadStock(cantAux);
								listaLotesRecibidos.add(lote1);								//ANYADO LOTE A LA LISTA SI TIENE FECHA ANTERIOR A FECHA_LIMITE
								cantidadTotalRecibida += cantRecib;//Acumulamos para despues calcular la cantidad
								float auxValorTotalComprado =(cantRecib * precioCompraAux1); 
								valorTotalComprado += auxValorTotalComprado;
								numLotesTotalPrimario++;
								indiceLote++;
								numLotes++;	
							}
							progreso.incrementar();
						}else{return -1;}		//ERROR
					}						
				}
			}else{////////////////SI EL PRODUCTO NO ES PRIMARIO, ES DECIR, ES DE LOS NO-IMPORTANTES

				numSecundarios++;

				for(int anyoActual=2011; anyoActual <= getFechaLimite().get(Calendar.YEAR); anyoActual++){			//PARA CADA ANYO

					int mesesLote = 12/numLotesSecundariosPorAnyo;

					for(int mesActual=0; mesActual <=11; mesActual+=mesesLote){			//PARA CADA MES

						LoteRecibido lote = new LoteRecibido();

						Calendar fecha = Calendar.getInstance();
						fecha.set(anyoActual, mesActual, 1);						//ESTABLEZCO LA FECHA DEL LOTE
						lote.setFechaRecepcion(fecha);

						int cantidad=0;
						do{//Genero una cantidad entre cantidadMinima y cantidadMaxima para cada lote, siendo cantidad multiplo de 10
							cantidad = randCantidad.nextInt(cantidadMaxima)+1;
							//							int max = cantidadMaxima/10;
							//							cantidad = randCantidad.nextInt(max)+1;
							//							cantidad = cantidad*10; //cantidad estarÃ¡ entre 10 y 50						
						}while(cantidad < cantidadMinima || cantidad > cantidadMaxima);

						float ajuste = randPorcentaje.nextInt(11)+1;
						ajuste = (float) (ajuste * 0.01);
						float porcent = (float) (0.94 + ajuste); // +/- 5% de variabilidad en el precio de compra unitario

						//el idLoteRecibido se generara luego
						int indexProveedor = randProveedor.nextInt(numProveedores);
						lote.setIdProveedor(listaProveedores.get(indexProveedor).getIdProveedor());
						lote.setIdProducto(listaProductos.get(i).getIdProducto());

						lote.setCantidadRecibida(cantidad); //Guardo la cantidad generada para el lote
						float precioCompraAux = listaProductos.get(i).getPrecioMedioCompraUnitario()*porcent; //Aplica el porcentaje de variabilidad al precio
						precioCompraAux = (float) (Math.rint(precioCompraAux*100)/100); //Formatea el precio a 2 decimales
						lote.setPrecioCompraUnitario(precioCompraAux);

						lote.setIdLoteRecibido(indiceLote);

						lote.setStockProductoAux(listaProductos.get(i).getCantidadStock());	//ESTABLEZCO EL ATRIBUTO AUXILIAR stockProductoAux, que utilizara para generar las ventas

						if(lote.getFechaRecepcion().before(fechaLimite)){
							int cantRecib = lote.getCantidadRecibida();				//Acumulo en cantidad_stock del producto la cantidad recibida
							int cantAux = listaProductos.get(i).getCantidadStock();
							cantAux += cantRecib;
							listaProductos.get(i).setCantidadStock(cantAux);
							listaLotesRecibidos.add(lote);								//ANYADO LOTE A LA LISTA SI TIENE FECHA ANTERIOR A FECHA_LIMITE
							cantidadTotalRecibida += cantidad;//Acumulamos para despues calcular la cantidad
							float auxValorTotalComprado =(cantidad * precioCompraAux); 
							valorTotalComprado += auxValorTotalComprado;
							numLotesTotalSecundario++;
							indiceLote++;
							numLotes++;	
							progreso.incrementar();
						}

					}


				}
			}
			
			Float media_precio_compra = valorTotalComprado/cantidadTotalRecibida;
			System.out.println("Precio medio compra antiguo: " + listaProductos.get(i).getPrecioMedioCompraUnitario() +
					" --- Precio medio compra nuevo: " + media_precio_compra + 
					" -> Diferencia: " + (Math.abs(listaProductos.get(i).getPrecioMedioCompraUnitario() - media_precio_compra)));
			listaProductos.get(i).setPrecioMedioCompraUnitario(media_precio_compra);
		}//fin bucle productos

		System.out.println("Numero de productos primarios: " + numPrimarios);
		System.out.println("Numero de productos secundarios: " + numSecundarios);
		System.out.println("Numero de lotes primarios: " + numLotesTotalPrimario);
		System.out.println("Numero de lotes secundarios: " + numLotesTotalSecundario);
		
		setNumLotesPrimarios(numLotesTotalPrimario);
		setNumLotesSecundarios(numLotesTotalSecundario);
		return numLotes;	
	}

	/**Genera los lotes_recibidos con fechas y cantidades variables(no los escribe en ningun fichero)
	 * @param lotesPorProductoAlAnyo Recibe el numero de lotes recibidos al anyo por cada producto
	 * @param cantidadMinima es la cantidad minima recibida en cada lote
	 * @param cantidadMinima es la cantidad maxima recibida en cada lote
	 * @return Devuelve el numero de lotes_recibidos generados en total (para todos los productos y anyos)*/
	public int GenerarLotesRecibidosFechaVariableCantidadVariable(Integer lotesPorProductoAlAnyo, Integer cantidadMinima, Integer cantidadMaxima, int movPrimarios, int crecimiento) throws NumberFormatException, IOException{

		Float porcentMovimientosPrimarios = Float.valueOf(movPrimarios);
		porcentMovimientosPrimarios = porcentMovimientosPrimarios/100;

		//		double porcentCrecimiento = (crecimiento==0)?0:crecimiento/100;	//Convierto el crecimiento en un double, es decir, en un factor (por ejempo, 0.03)
		double porcentCrecimiento = crecimiento*0.01;	//Convierto el crecimiento en un double, es decir, en un factor (por ejempo, 0.03)

		if(listaProductos.size()==0){
			throw new IOException();
		}
		if(listaProveedores.size()==0)
			leerProveedores(null);
		if(cantidadMinima==null || cantidadMinima <=0 || cantidadMaxima==null || cantidadMaxima <=0  ||cantidadMinima > cantidadMaxima){//Cantidad minima por defecto
			JOptionPane.showMessageDialog(poblar_bd.getFrame(), "Error: Las cantidades minima y/o maxima no puede tomar ese valor.", null, JOptionPane.ERROR_MESSAGE);
			return -1;
		}
		System.out.println("Intervalo de cantidades: [" + cantidadMinima + ", " + cantidadMaxima + "]\n");
		int numProductos = listaProductos.size();
		int numLotesPorProducto;
		if(lotesPorProductoAlAnyo==null)
			numLotesPorProducto = 30;	//Numero de lotes por producto/anyo por defecto
		else numLotesPorProducto = lotesPorProductoAlAnyo;

		///////////////////////////////////////////////////////////////////////////////////////////////////////CRECIMIENTO
		ArrayList<Float> cantidadesAnyos = new ArrayList<>();
		ArrayList<Float> probabilidadesAnyos = new ArrayList<Float>();
		int numAnyos= getFechaLimite().get(Calendar.YEAR)-2011+1;
		double factorCrecimientoTotal = 1;
		double acumulado=0;
		cantidadesAnyos.add((float)numLotesPorProducto);
		for(int i=1; i <= numAnyos; i++){
			cantidadesAnyos.add((float) (cantidadesAnyos.get(i-1)* (1+porcentCrecimiento)));
			acumulado += cantidadesAnyos.get(i-1);
			System.out.println("Acumulado en paso " + i + " = " +  acumulado + "\n");
			System.out.println("Cantidad en año " + (i-1) + " = " +  cantidadesAnyos.get(i) + "\n");
			factorCrecimientoTotal = factorCrecimientoTotal * (1+porcentCrecimiento);
		}
		for(int i=0; i<numAnyos; i++){
			float prob = (float) (cantidadesAnyos.get((i))/acumulado);
			probabilidadesAnyos.add(prob);
			System.out.println("Probabilidades de que un lote sea del año " + i + ": " + probabilidadesAnyos.get((i)) + "\n");
			System.out.println("Factor crecimiento total = " + factorCrecimientoTotal + ". Acumulado lotes: " + acumulado + "\n");
		}
		////////////////////AQUI TENGO UN VECTOR DE LONGITUD NUMANYOS CON LA PROBABILIDAD DE QUE UN LOTE SEA DE CIERTO ANYO
		int numLotesPorProductoConCrecimiento = (int) Math.rint(numLotesPorProducto * factorCrecimientoTotal); //En total, en todos los anyos

		int numLotesPorProductoPrimario = (int) Math.round(numLotesPorProductoConCrecimiento*porcentMovimientosPrimarios);
		int numLotesPorProductoSecundario = numLotesPorProductoConCrecimiento - numLotesPorProductoPrimario;

		int numLotesTotalPrimario = 0;
		int numLotesTotalSecundario = 0;

		int numProveedores = listaProveedores.size();
		int numLotes = 0;	//se actualiza luego cuando se han anyadido todos los lotes
		Fecha fecha = new Fecha(); 			

		Random randCantidad = new Random();
		Random randPorcentaje = new Random();
		Random randProveedor = new Random();

		int numPrimarios = 0;	//numero de PRODUCTOS primarios
		int numSecundarios = 0;	//numero de PRODUCTOS secundarios

		progreso.setMaximo(numProductos * numLotesPorProducto);

		for(int i=0; i < numProductos; i++){ //Para cada producto			

			Integer cantidadTotalRecibida=0;//estos dos campos se usan para calcular la cantidad total recibida y el valor total comprado de cada producto, para sacar luego la media
			float valorTotalComprado= 0;
			
			progreso.incrementar();
			if(listaProductos.get(i).isPrimario()==true){ /////SI EL PRODUCTO ES PRIMARIO, ES DECIR, ES DE LOS IMPORTANTES

				numPrimarios++;

				for(int j=0; j < numLotesPorProductoPrimario; j++){ //PARA CADA LOTE DE UN DETERMINADO PRODUCTO PRIMARIO

					int cantidad=0;
					do{//Genero una cantidad entre cantidadMinima y cantidadMaxima para cada lote, siendo cantidad multiplo de 10
						cantidad = randCantidad.nextInt(cantidadMaxima)+1;
						//							int max = cantidadMaxima/10;
						//							cantidad = randCantidad.nextInt(max)+1;
						//							cantidad = cantidad*10; //cantidad estara entre 10 y cantidadMinima y cantidadMaxima						
					}while(cantidad < cantidadMinima || cantidad > cantidadMaxima);

					LoteRecibido lote = new LoteRecibido();
					float ajuste = randPorcentaje.nextInt(11)+1;
					ajuste = (float) (ajuste * 0.01);
					float porcent = (float) (0.94 + ajuste); // +/- 5% de variabilidad en el precio de compra unitario

					//el idLoteRecibido se generara luego
					int indexProveedor = randProveedor.nextInt(numProveedores);
					lote.setIdProveedor(listaProveedores.get(indexProveedor).getIdProveedor());
					lote.setIdProducto(listaProductos.get(i).getIdProducto());
					Calendar fechaRecepcion = Calendar.getInstance();

					int anyoActual = anyoPorProbabilidad(probabilidadesAnyos);
					//						if(Math.random()<=0.1)
					//							fechaRecepcion.set(anyoActual+1, probabilidades.obtenerMesAleatorioPonderado()-1, fecha.obtenerDiaAleatorio(anyoActual)); //OJO, EL MES EN CALENDAR VA DE 0 A 11
					//						else fechaRecepcion.set(anyoActual, probabilidades.obtenerMesAleatorioPonderado()-1, fecha.obtenerDiaAleatorio(anyoActual)); //OJO, EL MES EN CALENDAR VA DE 0 A 11
					fechaRecepcion.set(anyoActual, probabilidades.obtenerMesAleatorioPonderado()-1, fecha.obtenerDiaAleatorio(anyoActual)); //OJO, EL MES EN CALENDAR VA DE 0 A 11

					/*TODO*/
					lote.setFechaRecepcion(fechaRecepcion);
					lote.setCantidadRecibida(cantidad); //Guardo la cantidad generada para el lote
					float precioCompraAux = listaProductos.get(i).getPrecioMedioCompraUnitario()*porcent; //Aplica el porcentaje de variabilidad al precio
					precioCompraAux = (float) (Math.rint(precioCompraAux*100)/100); //Formatea el precio a 2 decimales
					lote.setPrecioCompraUnitario(precioCompraAux);
					
					lote.setIdLoteRecibido(numLotes);
					lote.setStockProductoAux(listaProductos.get(i).getCantidadStock());	//ESTABLEZCO EL ATRIBUTO AUXILIAR stockProductoAux, que utilizara para generar las ventas

					if(lote.getFechaRecepcion().before(fechaLimite)){
						int cantRecib = lote.getCantidadRecibida();				//Acumulo en cantidad_stock del producto la cantidad recibida
						int cantAux = listaProductos.get(i).getCantidadStock();
						cantAux += cantRecib;
						listaProductos.get(i).setCantidadStock(cantAux);
						listaLotesRecibidos.add(lote);								//ANYADO LOTE A LA LISTA SI TIENE FECHA ANTERIOR A FECHA_LIMITE
						cantidadTotalRecibida += cantidad;//Acumulamos para despues calcular la cantidad
						float auxValorTotalComprado =(cantidad * precioCompraAux); 
						valorTotalComprado += auxValorTotalComprado;
						numLotesTotalPrimario++;
						numLotes++;	
						progreso.incrementar();
					}
				}			
			}else if(listaProductos.get(i).isPrimario()==false){////////////////SI EL PRODUCTO NO ES PRIMARIO, ES DECIR, ES DE LOS NO-IMPORTANTES

				numSecundarios++;
				for(int j=0; j < numLotesPorProductoSecundario; j++){ //PARA CADA LOTE DE UN DETERMINADO PRODUCTO SECUNDARIO

					int cantidad=0;
					do{//Genero una cantidad entre cantidadMinima y cantidadMaxima para cada lote, siendo cantidad multiplo de 10
						cantidad = randCantidad.nextInt(cantidadMaxima)+1;
						//							int max = cantidadMaxima/10;
						//							cantidad = randCantidad.nextInt(max)+1;
						//							cantidad = cantidad*10; //cantidad estara entre 10 y 50						
					}while(cantidad < cantidadMinima || cantidad > cantidadMaxima);

					LoteRecibido lote = new LoteRecibido();
					float ajuste = randPorcentaje.nextInt(11)+1;
					ajuste = (float) (ajuste * 0.01);
					float porcent = (float) (0.94 + ajuste); // +/- 5% de variabilidad en el precio de compra unitario

					//el idLoteRecibido se generara luego
					int indexProveedor = randProveedor.nextInt(numProveedores);
					lote.setIdProveedor(listaProveedores.get(indexProveedor).getIdProveedor());
					lote.setIdProducto(listaProductos.get(i).getIdProducto());
					Calendar fechaRecepcion = Calendar.getInstance();

					int anyoActual = anyoPorProbabilidad(probabilidadesAnyos);
					//						if(Math.random()<=0.1)
					//							fechaRecepcion.set(anyoActual+1, probabilidades.obtenerMesAleatorioPonderado()-1, fecha.obtenerDiaAleatorio(anyoActual)); //OJO, EL MES EN CALENDAR VA DE 0 A 11
					//						else fechaRecepcion.set(anyoActual, probabilidades.obtenerMesAleatorioPonderado()-1, fecha.obtenerDiaAleatorio(anyoActual)); //OJO, EL MES EN CALENDAR VA DE 0 A 11
					fechaRecepcion.set(anyoActual, probabilidades.obtenerMesAleatorioPonderado()-1, fecha.obtenerDiaAleatorio(anyoActual)); //OJO, EL MES EN CALENDAR VA DE 0 A 11

					/*TODO*/
					lote.setFechaRecepcion(fechaRecepcion);
					lote.setCantidadRecibida(cantidad); //Guardo la cantidad generada para el lote
					float precioCompraAux = listaProductos.get(i).getPrecioMedioCompraUnitario()*porcent; //Aplica el porcentaje de variabilidad al precio
					precioCompraAux = (float) (Math.rint(precioCompraAux*100)/100); //Formatea el precio a 2 decimales
					lote.setPrecioCompraUnitario(precioCompraAux);

					lote.setIdLoteRecibido(numLotes);

					lote.setStockProductoAux(listaProductos.get(i).getCantidadStock());	//ESTABLEZCO EL ATRIBUTO AUXILIAR stockProductoAux, que utilizara para generar las ventas

					if(lote.getFechaRecepcion().before(fechaLimite)){
						int cantRecib = lote.getCantidadRecibida();				//Acumulo en cantidad_stock del producto la cantidad recibida
						int cantAux = listaProductos.get(i).getCantidadStock();
						cantAux += cantRecib;
						listaProductos.get(i).setCantidadStock(cantAux);
						listaLotesRecibidos.add(lote);								//ANYADO LOTE A LA LISTA SI TIENE FECHA ANTERIOR A FECHA_LIMITE
						cantidadTotalRecibida += cantidad;//Acumulamos para despues calcular la cantidad
						float auxValorTotalComprado =(cantidad * precioCompraAux); 
						valorTotalComprado += auxValorTotalComprado;
						numLotesTotalSecundario++;
						numLotes++;	
						progreso.incrementar();
					}
				}
			}else{
				JOptionPane.showMessageDialog(poblar_bd.getFrame(), "Se ha producido un error generando los lotes. Algun producto no es primario ni secundario.", null, JOptionPane.ERROR_MESSAGE);
				return -1;
			}
			Float media_precio_compra = valorTotalComprado/cantidadTotalRecibida;
			System.out.println("Precio medio compra antiguo: " + listaProductos.get(i).getPrecioMedioCompraUnitario() +
					" --- Precio medio compra nuevo: " + media_precio_compra + 
					" -> Diferencia: " + (Math.abs(listaProductos.get(i).getPrecioMedioCompraUnitario() - media_precio_compra)));
			listaProductos.get(i).setPrecioMedioCompraUnitario(media_precio_compra);
		}//fin bucle productos

		System.out.println("Numero de productos primarios: " + numPrimarios);
		System.out.println("Numero de productos secundarios: " + numSecundarios);
		System.out.println("Numero de lotes primarios: " + numLotesTotalPrimario);
		System.out.println("Numero de lotes secundarios: " + numLotesTotalSecundario);
		setNumLotesPrimarios(numLotesTotalPrimario);
		setNumLotesSecundarios(numLotesTotalSecundario);
		return numLotes;	
	}




	/**@param ruta del fichero de salida que se abrirÃ¡ para escritura
	 * @return numero de lotes que se han escrito en el fichero de salida*/
	public int escribirLotesRecibidos(String ruta) throws IOException{

		if(ruta==null)
			ruta = poblar_bd.getRutaSalida()+"lotes_recibidos_salida.csv" ;
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
			int anyo = fechaAux.get(Calendar.YEAR);
			w.write(listaLotesRecibidos.get(i).getIdLoteRecibido() + ";"
					+ listaLotesRecibidos.get(i).getIdProveedor() + ";"
					+ listaLotesRecibidos.get(i).getIdProducto() + ";" 
					+ dia + "-" + mes +"-" + anyo + ";"
					+ listaLotesRecibidos.get(i).getCantidadRecibida() + ";"
					+ listaLotesRecibidos.get(i).getPrecioCompraUnitario() + "\n"
					);
			numLotesEscritos++;
		}
		w.close();
		progreso.setTextStopped();
		return numLotesEscritos;

	}


	/*---------------------------------------PROVEEDORES------------------------------------------------------------*/
	/**@return numero de proveedores que se han leido del fichero de entrada*/
	public int leerProveedores(String ruta) throws IOException{

		if(ruta==null)
			ruta=poblar_bd.getRutaEntrada()+"proveedores_entrada.csv";

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
			ruta=poblar_bd.getRutaSalida()+"lotes_recibidos_salida.csv"; //Leemos de la ruta por defecto de salida

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
	 * @param maxCantPorVenta - nÃºmero mÃ¡ximo de cantidad vendida en cada venta
	 * @param minimizacionMemoria - true si el metodo se ejecuta con minimizacion de memoria. Esto implica que se irá escribiendo en el fichero a medida
	 * que se van generando las ventas de cada lote, por lo que LUEGO NO HABRÁ QUE INVOCAR EL METODO ESCRIBIRVENTAS()
	 * @return numero de ventas generadas*/
	public int generarVentas(int maxCantPorVenta, int maxDiasVendiendose, boolean boolVentas1Ud, int ventas1Ud, boolean minimizacionMemoria) throws IOException{

		int numVentasEscritas=0;//se usa solo con minimizacion de memoria
		String ruta = poblar_bd.getRutaSalida()+"ventas_salida.csv" ;
		File file = new File(ruta);
		FileOutputStream fsal = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fsal);
		Writer w = new BufferedWriter(osw);
		//hasta aqui es de minimizacionMemoria=true

		Float porcentVentas1Ud = Float.valueOf(ventas1Ud);
		porcentVentas1Ud = porcentVentas1Ud/100;
		if(listaLotesRecibidos==null || listaLotesRecibidos.size()==0){
			w.close();
			throw new IOException();
			//			leerLotesRecibidos(null);
		}
		if(listaProductos==null){
			w.close();
			throw new IOException();
			//			leerProductos(null);
		}
		progreso.setMaximo(listaLotesRecibidos.size());

		int numVentasGeneradas = 0;
		int numLotes = listaLotesRecibidos.size();
		int indiceVenta=0;
		Random randCant = new Random();
		Random randDia = new Random();
		//		int cantStock=0;
		//		ArrayList listaAuxiliarProductos = new ArrayList<>();
		HashMap<Integer, Integer> mapAuxCantidadProductos = new HashMap<Integer, Integer>();//HashMap para calcular la cantidad total vendida de cada producto
		HashMap<Integer, Float> mapAuxCantidadPorPrecioProductos = new HashMap<Integer, Float>();//HashMap para calcular cantidadVendidad*PrecioVenta de cada producto, para luego calcular la media del precio de venta del producto

		for(int i=0; i<numLotes; i++){		

			progreso.incrementar();
			int acumuladoVenta = 0;
			//			cantStock = listaLotesRecibidos.get(i).getStockProductoAux();				//cantidad de stock del producto asociado al lote
			//			int cantidadLote = listaLotesRecibidos.get(i).getCantidadRecibida() - cantStock; //Cantidad a repartir entre las ventas del producto
			int cantidadLote = listaLotesRecibidos.get(i).getCantidadRecibida(); //Cantidad a repartir entre las ventas del producto
			//Asi, despues de generar los lotes y las ventas, la cantidad de producto sin vender sera la suma(cantidad_recibida) - suma(cantidad_vendida) = cantStock

			do{

				Venta v = new Venta();
				int cantidadVendida;

				Calendar fechaLote = listaLotesRecibidos.get(i).getFechaRecepcion();
				Calendar fechaVenta = Calendar.getInstance();

				//Las ventas de un lote recibido se venden en los siguientes 'maxDiasVendiendose' dias despues de la fecha de recepcion del lote				
				fechaVenta.set(fechaLote.get(Calendar.YEAR), fechaLote.get(Calendar.MONTH), fechaLote.get(Calendar.DAY_OF_MONTH)+randDia.nextInt(maxDiasVendiendose+1));

				v.setFechaVenta(fechaVenta);										//fecha de la venta

				v.setIdVenta(indiceVenta);									//id de la venta

				do{
					if(boolVentas1Ud==false){//La cantidad de las ventas estara entre 1 y maxCantPorVenta
						cantidadVendida = randCant.nextInt(maxCantPorVenta)+1;
					}else{
						if(porcentVentas1Ud==1){//Si todas las ventas tienen que ser de 1 unidad. SOLO PARA OPTIMIZAR EL CASO DEL 100% DE VENTAS DE 1 UD.
							cantidadVendida=1;
						}else{
							if(aplicarProbabilidad(porcentVentas1Ud)==true){//El porcentVentas1Ud% de las ventas deben ser de cantidad 1 unidad
								cantidadVendida=1;
							}else{//El resto de las ventas sera de cantidad > 1 y <= maxCantPorVenta
								cantidadVendida = randCant.nextInt(maxCantPorVenta-1)+2;
							}
						}
					}
				}while(cantidadVendida > cantidadLote);		//Mientras la cantidadVendida generada sea superior a la cantidad que queda, genero otra cantidadVendida

				/*Poniendo esta linea aqui conseguimos hacer como si no existieran las ventas que deberian haber sido despues de la fecha limite
				con lo que obtenemos un stock del producto > 0 (CUANDO EL TIEMPO MAXIMO EN VENTA ES MAYOR QUE EL QUE HAY ENTRE RECEPCION Y RECEPCION), 
				lo cual ocurre normalmente en los productos secundarios y tambien en los primarios cuando solo se recibe 1 al mes y el tiempo de venta son mas de 30 dias.*/										
				cantidadLote -= cantidadVendida;		

				if(v.getFechaVenta().before(fechaLimite)){

					v.setCantidadVendida(cantidadVendida);
					int idProd = listaLotesRecibidos.get(i).getIdProducto();
					if(mapAuxCantidadProductos.containsKey(idProd)){			//Si el producto ya estaba en la lista auxiliar, actualizo el valor de la cantidad vendida
						int cantVendidaAux = mapAuxCantidadProductos.get(idProd);
						cantVendidaAux += cantidadVendida;
						mapAuxCantidadProductos.put(idProd, cantVendidaAux);	//Anyado el par (id_producto, acumuladoVenta) a una lista auxiliar
					}else{												//Si el producto no estaba en la lista, lo meto con el valor de cantidad directamente
						mapAuxCantidadProductos.put(idProd, cantidadVendida);	//Anyado el par (id_producto, acumuladoVenta) a una lista auxiliar
					}

					v.setIdProducto(idProd); //Id del producto vendido en la venta

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
					v.setPrecioVentaUnitario(precioVenta);

					//Para calcular el precio medio de venta, acumulamos el precioVenta*CantidadVendida de cada producto
					Float auxCantidadPorPrecio = cantidadVendida * precioVenta;
					if(mapAuxCantidadPorPrecioProductos.containsKey(idProd)){
						Float acumuladoCantidadPorPrecio = mapAuxCantidadPorPrecioProductos.get(idProd);
						acumuladoCantidadPorPrecio += auxCantidadPorPrecio;
						mapAuxCantidadPorPrecioProductos.put(idProd, acumuladoCantidadPorPrecio);
					}else{
						mapAuxCantidadPorPrecioProductos.put(idProd, auxCantidadPorPrecio);
					}
					
					listaVentas.add(v);
					indiceVenta++;
					numVentasGeneradas++;

				}
				//Precio de la venta
				/*TODO*/
			}while(cantidadLote!=0);		//Cuando la suma de las cantidades vendidas sea igual a la cantidad del lote recibido, entonces paro.

			//			mapAuxProductos.put(listaLotesRecibidos.get(i).getIdProducto(), acumuladoVenta);	//Anyado el par (id_producto, acumuladoVenta) a una lista auxiliar

			if(minimizacionMemoria==true){ //Si hay que minimizar el uso de memoria, abro el fichero para escritura y voy escribiendo las ventas de cada lote
				while(listaVentas.size()!=0){
					Calendar fechaAux = listaVentas.get(0).getFechaVenta();
					int dia = fechaAux.get(Calendar.DAY_OF_MONTH);
					int mes = fechaAux.get(Calendar.MONTH)+1;
					int anyo = fechaAux.get(Calendar.YEAR);
					w.write(listaVentas.get(0).getIdVenta() + ";"
							+ listaVentas.get(0).getIdProducto() + ";"
							+ listaVentas.get(0).getCantidadVendida() + ";" 
							+ listaVentas.get(0).getPrecioVentaUnitario() + ";"
							+ dia + "-" + mes +"-" + anyo + "\n"
							);
					numVentasEscritas++;
					listaVentas.remove(0);
				}

			}

		}//fin bucle lotes
		w.close();//cierro el fichero de ventas que he abierto en caso de minimizacion de memoria
		if(minimizacionMemoria==true){
			System.out.println("NUMERO DE VENTAS ESCRITAS CON MINIMIZACION DE MEMORIA: " + numVentasEscritas);
			poblar_bd.getTextArea().append("Numero de ventas escritas (CON MINIMIZACION DE MEMORIA): " + numVentasEscritas +"\n");
			numVentasParaProgressBar = numVentasEscritas;
		}
		/*Recorro los productos para restar a su cantidad de stock (que era la cantidad recibida)
		 * la cantidad vendida (acumuladoVenta) que tengo guardada en un hashmap para cada id del producto*/
		int numProd = listaProductos.size();
		for(int i=0; i<numProd; i++){

			Integer cantVend = mapAuxCantidadProductos.get(listaProductos.get(i).getIdProducto());
			if(cantVend!=null){
				int cantStock = listaProductos.get(i).getCantidadStock();
				cantStock -= cantVend;
				listaProductos.get(i).setCantidadStock(cantStock);
				if(cantStock==0)
					listaProductos.get(i).setStock("No");
				else listaProductos.get(i).setStock("Si");
			}
			//Actualizamos el precioMedioVenta unitario...
			Float cantPorPrecio = mapAuxCantidadPorPrecioProductos.get(listaProductos.get(i).getIdProducto());
			if(cantPorPrecio!=null){
				Float mediaPrecioVenta = cantPorPrecio/cantVend;
				System.out.println("Precio medio venta antiguo: " + listaProductos.get(i).getPrecioMedioVentaUnitario() + 
						" --- Precio medio venta nuevo: " + mediaPrecioVenta + 
						" --- Diferencia: " + (listaProductos.get(i).getPrecioMedioVentaUnitario()-mediaPrecioVenta));
				
				listaProductos.get(i).setPrecioMedioVentaUnitario(mediaPrecioVenta);
			}
		}//fin bucle productos

		if(minimizacionMemoria==true)
			progreso.setTextStopped();
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
			ruta = poblar_bd.getRutaSalida()+"ventas_salida.csv" ;
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
			int anyo = fechaAux.get(Calendar.YEAR);
			w.write(listaVentas.get(i).getIdVenta() + ";"
					+ listaVentas.get(i).getIdProducto() + ";"
					+ listaVentas.get(i).getCantidadVendida() + ";" 
					+ listaVentas.get(i).getPrecioVentaUnitario() + ";"
					+ dia + "-" + mes +"-" + anyo + "\n"
					);
			numVentasEscritas++;
		}
		w.close();
		progreso.setTextStopped();
		return numVentasEscritas;


	}
	/*------------------------------------------------UBICACION-PRODUCTO----------------------------------------------------------*/

	public int leerAlmacenes(String ruta) throws IOException{

		if(ruta==null)
			ruta=poblar_bd.getRutaEntrada()+"almacenes_entrada.csv"; //Leemos de la ruta por defecto de salida

		int numAlmacenes=0;
		File file = new File(ruta);
		FileInputStream fent = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fent);
		BufferedReader r = new BufferedReader(isr);
		String linea;

		while((linea = r.readLine()) != null){			
			String[] campos = linea.split(";");			
			System.out.println("Campo[0]: " + campos[0] + "; Campo[1]: " + campos[1]);
			Almacen a = new Almacen();
			a.setIdAlmacen(Integer.valueOf(campos[0]));
			a.setNombreAlmacen(campos[1]);
			a.setCiudad(campos[2]);
			a.setDireccion(campos[3]);
			a.setDetalles(campos[4]);
			listaAlmacenes.add(a);			
			numAlmacenes++;
		}
		r.close();

		return numAlmacenes;
	}

	public int leerUbicaciones(String ruta) throws IOException{

		if(ruta==null)
			ruta=poblar_bd.getRutaEntrada()+"ubicaciones_entrada.csv"; //Leemos de la ruta por defecto de salida

		int numUbicaciones=0;
		File file = new File(ruta);
		FileInputStream fent = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fent);
		BufferedReader r = new BufferedReader(isr);
		String linea;

		while((linea = r.readLine()) != null){			
			String[] campos = linea.split(";");			
			System.out.println("Campo[0]: " + campos[0] + "; Campo[1]: " + campos[1] + "; Campo[2]: " + campos[2]);
			Ubicacion u = new Ubicacion();
			u.setIdUbicacion(Integer.valueOf(campos[0]));
			u.setIdAlmacen(Integer.valueOf(campos[1]));
			u.setZona(campos[2]);
			u.setEstante(Integer.valueOf(campos[3]));
			listaUbicaciones.add(u);			
			numUbicaciones++;
		}
		r.close();

		return numUbicaciones;
	}

	/**Genera una ubicacion para cada producto. Tal y como esta, un producto solo tiene una ubicacion y esa ubicacion tiene toda la cantidad del producto.
	 * @return numero de producto-ubicacion generados
	 * @throws IOException */
	public int generarUbicacionProducto() throws IOException{

		if(listaUbicaciones==null || listaUbicaciones.size()==0){
			leerUbicaciones(null);
		}
		if(listaProductos==null || listaProductos.size()==0){
			System.out.println("ERROR. NO HAY LISTA DE PRODUCTOS.");
			return -1;
		}
		int numProd = listaProductos.size();
		int numUbi = listaUbicaciones.size();
		int numUbiProdGeneradas = 0;
		Random randAlm = new Random();

		for(int i=0; i<numProd; i++){

			ArrayList<Integer> integridadUbicaciones = new ArrayList<Integer>(); /*Array con los idUbicacion del producto para que no haya dos ubicaciones iguales 
			para el mismo producto, ya que no cumpliria la restriccion de integridad porque en la BD la clave primara es el par (idProd, idUbi)*/
			Integer idProd = listaProductos.get(i).getIdProducto();
			Integer cantProd = listaProductos.get(i).getCantidadStock();
			Random randUbi = new Random();
			Random randCant = new Random();
			int prodEnAlm = Math.min(maxUbicacionesDistintas,randAlm.nextInt(maxUbicacionesDistintas));//establece el numero de ubicaciones en las que esta un producto. Si un producto esta en 3 ubicaciones, prodEnAlm=3. Como minimo esta en 1 ubicacion
			int auxProdEnAlm = prodEnAlm;
			Integer auxCantProd = new Integer(cantProd);//para crear una copia del valor de cantProd
			ArrayList<Integer> cantidades = new ArrayList<Integer>();//para luego distribuir las cantidades en las ubicaciones
			int k=0;
			while(k!=auxProdEnAlm){//En el numero de ubicaciones (prodEnAlm) -1, donde añadiré el resto
				if(auxCantProd<=1){//Si ya no queda cantidad que repartir, dejo de repartir y en la ultima ubicacion pongo la cantidad que queda, osea, 1.
					prodEnAlm=k;//Actualizo el numero de ubicaciones con las que finalmente han sido, es decir, el numero de ubicaciones real en las que esta el producto (+1 por la de fuera del bucle)
					break;
				}
				//				int auxSeed= (int) Math.rint(auxCantProd/2)+1;
				int cant = randCant.nextInt(auxCantProd)+1;//genero una cantidad aleatoria en función de la cantidad que queda
				cantidades.add(cant);
				auxCantProd-=cant;//resto la cantidad añadida al auxiliar de cantidad de producto total
				k++;
			}

			if(cantProd==0){//Si no hay stock del producto, hay que asignarle igualmente alguna ubicacion.
				prodEnAlm=1;//Por lo que ponemos en prodEnAlm, que es el numero diferente de ubicaciones en las que está (o ha estado en este caso) el producto a 1
			}

			if(auxCantProd>0){
				cantidades.add(auxCantProd);//El resto de la cantidad de producto lo añado en la ultima ubicacion
				prodEnAlm++;
			}
			if(prodEnAlm==0 ){
				System.out.println("ERROR: prodEnAlm == 0.");
			}
			if(cantProd==0){
				System.out.println("ERROR: cantProd == 0.");
			}
			int j=0;
			while(j!=prodEnAlm){
				UbicacionProducto up = new UbicacionProducto();
				up.setIdProducto(idProd);
				Integer idUbi = randUbi.nextInt(numUbi)+1;
				while(integridadUbicaciones.contains(idUbi)==true){ //Comprobamos que el idUbi no se haya generado ya para este producto
					idUbi = randUbi.nextInt(numUbi)+1;
				}
				integridadUbicaciones.add(idUbi);
				up.setIdUbicacion(idUbi); //Asi, un producto solo va a estar en una ubicacion, o sea que no todos los almacenes tendran de todo
				if(cantProd==0)//Si es un producto que no tiene stock, se pone el stock a 0, pero tiene ubicacion asignada
					up.setCantidad(0); 
				else up.setCantidad(cantidades.get(j));

				listaUbicacionProducto.add(up);
				numUbiProdGeneradas++;
				j++;
			}
		}
		return numUbiProdGeneradas;
	}

	public int escribirUbicacionProducto(String ruta) throws IOException{

		if(ruta==null)
			ruta = poblar_bd.getRutaSalida()+"ubicacion_producto_salida.csv" ;
		int numUbiProdEscritas=0;
		File file = new File(ruta);
		FileOutputStream fsal = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fsal);
		Writer w = new BufferedWriter(osw);
		int numUbiProd= listaUbicacionProducto.size();
		for(int i=0; i<numUbiProd;i++){
			w.write(listaUbicacionProducto.get(i).getIdUbicacion() + ";"
					+ listaUbicacionProducto.get(i).getIdProducto() + ";"
					+ listaUbicacionProducto.get(i).getCantidad() + "\n"
					);
			numUbiProdEscritas++;
		}
		w.close();
		return numUbiProdEscritas;

	}

	/*------------------------------------------------MOVIMIENTOS----------------------------------------------------------*/
	/**Genera los movimientos de entrada y salida a partir de las listas de lotesRecibidos y ventas. Éstas listas deben estar en memoria.
	 * @return numero de movimientos generados*/
	public int generarMovimientos() throws IOException{

		int numMovimientos=0;

		if(listaLotesRecibidos==null || listaLotesRecibidos.size()==0){
			//			leerLotesRecibidos(null);
			System.out.println("No hay lotes generados");
			throw new IOException();
		}
		if(listaVentas==null || listaVentas.size()==0){			
			System.out.println("No hay ventas generadas");
			throw new IOException();
		}
		if(listaUbicacionProducto==null || listaUbicacionProducto.size()==0){
			System.out.println("No hay ubicacion-producto generadas");
			throw new IOException();
		}
		progreso.setMaximo(listaLotesRecibidos.size()*2 + listaVentas.size()*2);
		//Movimientos de entrada
		int numLotes = listaLotesRecibidos.size();
		for(int i=0; i<numLotes; i++){

			progreso.incrementar();
			Movimiento mov = new Movimiento();
			LoteRecibido lote = listaLotesRecibidos.get(i);

			mov.setIdMovimiento(numMovimientos);
			mov.setIdProducto(lote.getIdProducto());

			//TODO
			int numUbiProd = listaUbicacionProducto.size();
			for(int j=0; j<numUbiProd; j++){
				if(listaUbicacionProducto.get(j).getIdProducto()==lote.getIdProducto()){
					mov.setIdUbicacion(listaUbicacionProducto.get(j).getIdUbicacion());
					break;
				}
			}
			mov.setIdLoteRecibido(lote.getIdLoteRecibido());
			mov.setIdVenta(null);
			mov.setES("E");
			mov.setCantidad(lote.getCantidadRecibida());
			mov.setFechaMovimiento(lote.getFechaRecepcion());

			numMovimientos++;
			listaMovimientos.add(mov);
		}

		//Movimientos de salida
		int numVentas = listaVentas.size();
		Venta v;
		for(int i=0; i<numVentas; i++){

			progreso.incrementar();
			Movimiento mov = new Movimiento();
			v = listaVentas.get(i);

			mov.setIdMovimiento(numMovimientos);
			mov.setIdProducto(v.getIdProducto());

			int numUbiProd = listaUbicacionProducto.size();
			for(int j=0; j<numUbiProd; j++){
				if(listaUbicacionProducto.get(j).getIdProducto().equals(v.getIdProducto())){
					mov.setIdUbicacion(listaUbicacionProducto.get(j).getIdUbicacion());
					break;
				}
			}
			mov.setIdLoteRecibido(null);
			mov.setIdVenta(v.getIdVenta());
			mov.setES("S");
			mov.setCantidad(v.getCantidadVendida());
			mov.setFechaMovimiento(v.getFechaVenta());

			numMovimientos++;
			listaMovimientos.add(mov);
		}

		return numMovimientos;

	}

	/**Genera los movimientos de entrada y salida a partir de las listas de lotesRecibidos y ventas. Éstas listas deben estar en memoria.
	 * @param minimizacionMemoria: booleano que indica si los movimientos se deben generar con minimizacion de memoria o no.
	 * @return numero de movimientos generados*/

	public int generarMovimientosConMinimizacionMemoria() throws IOException{

		int tamBloque = 1000; //Tamanyo de los bloques de ventas en memoria
		//Con minimizacionMemoria
		String rutaVentas = poblar_bd.getRutaSalida()+"ventas_salida.csv" ;
		File fileVentas=null;
		FileInputStream fentVentas=null;
		InputStreamReader isrVentas=null;
		BufferedReader rVentas=null;
		int numVentasLeidas=0;
		String rutaMov = poblar_bd.getRutaSalida()+"movimientos_salida.csv" ;
		File fileMov = null;
		FileOutputStream fsalMov = null;
		OutputStreamWriter oswMov = null;
		BufferedWriter wMov = null;
		int numMovEscritos=0;
		//hasta aqui con minimizacionMemoria

		int numMovimientos=0;
		listaMovimientos = new ArrayList<Movimiento>();

		if(listaLotesRecibidos==null || listaLotesRecibidos.size()==0){
			//			leerLotesRecibidos(null);
			System.out.println("No hay lotes generados");
			throw new IOException();
		}
		fileVentas = new File(rutaVentas);
		fentVentas = new FileInputStream(fileVentas);
		isrVentas = new InputStreamReader(fentVentas);
		rVentas = new BufferedReader(isrVentas);						
		fileMov = new File(rutaMov);
		fsalMov = new FileOutputStream(fileMov);
		oswMov = new OutputStreamWriter(fsalMov);
		wMov = new BufferedWriter(oswMov);		

		if(listaUbicacionProducto==null || listaUbicacionProducto.size()==0){
			System.out.println("No hay ubicacion-producto generadas");
			throw new IOException();
		}
		progreso.setMaximo(listaLotesRecibidos.size()*2 + numVentasParaProgressBar*2);//aproximado a grosso modo
		//Movimientos de entrada
		while(listaLotesRecibidos.size()!=0){			//vamos leyendo lotes, borrandolos y añadiendo movimientos a listaMovimientos
			progreso.incrementar();
			Movimiento mov = new Movimiento();
			LoteRecibido lote = listaLotesRecibidos.get(0);

			mov.setIdMovimiento(numMovimientos);
			mov.setIdProducto(lote.getIdProducto());

			int numUbiProd = listaUbicacionProducto.size();
			for(int j=0; j<numUbiProd; j++){
				if(listaUbicacionProducto.get(j).getIdProducto()==lote.getIdProducto()){
					mov.setIdUbicacion(listaUbicacionProducto.get(j).getIdUbicacion());
					break;
				}
			}
			mov.setIdLoteRecibido(lote.getIdLoteRecibido());
			mov.setIdVenta(null);
			mov.setES("E");
			mov.setCantidad(lote.getCantidadRecibida());
			mov.setFechaMovimiento(lote.getFechaRecepcion());

			numMovimientos++;
			listaLotesRecibidos.remove(0);
			listaMovimientos.add(mov);
		}
		while(listaMovimientos.size()!=0){				//escribimos los movimientos que hay hasta ahora y los eliminamos de la lista para ahorra memoria
			progreso.incrementar();
			Calendar fechaAux = listaMovimientos.get(0).getFechaMovimiento();
			int dia = fechaAux.get(Calendar.DAY_OF_MONTH);
			int mes = fechaAux.get(Calendar.MONTH)+1;
			int anyo = fechaAux.get(Calendar.YEAR);
			wMov.write(listaMovimientos.get(0).getIdMovimiento() + ";"
					+ listaMovimientos.get(0).getIdProducto() + ";"
					+ listaMovimientos.get(0).getIdUbicacion() + ";"
					+ listaMovimientos.get(0).getIdLoteRecibido() + ";"
					+ listaMovimientos.get(0).getIdVenta() + ";"
					+ listaMovimientos.get(0).getES() + ";"
					+ listaMovimientos.get(0).getCantidad()+ ";"
					+ dia + "-" + mes +"-" + anyo + "\n"
					);
			numMovEscritos++;
			listaMovimientos.remove(0);
		}


		////////////////////////////Movimientos de salida

		Venta v;
		String linea;
		while((linea=rVentas.readLine())!=null){//MIENTRAS HAYAN VENTAS QUE LEER
			String[] campos = linea.split(";");			
			Venta venta = new Venta();
			venta.setIdVenta((Integer.valueOf(campos[0])));
			venta.setIdProducto(Integer.valueOf(campos[1]));
			venta.setCantidadVendida(Integer.valueOf(campos[2]));
			venta.setPrecioVentaUnitario(Float.valueOf(campos[3]));
			venta.setFechaVenta(Fecha.stringToCalendar(campos[4]));
			listaVentas.add(venta);			
			numVentasLeidas++;
			if(listaVentas.size()==tamBloque){			//Vamos leyendo hasta tener 20000 ventas en listaVentas
				//en este punto tenemos 20000 ventas en la listaVentas
				while(listaVentas.size()!=0){
					progreso.incrementar();
					Movimiento mov = new Movimiento();
					v = listaVentas.get(0);

					mov.setIdMovimiento(numMovimientos);
					mov.setIdProducto(v.getIdProducto());

					int numUbiProd = listaUbicacionProducto.size();
					for(int j=0; j<numUbiProd; j++){
						if(listaUbicacionProducto.get(j).getIdProducto().equals(v.getIdProducto())){
							mov.setIdUbicacion(listaUbicacionProducto.get(j).getIdUbicacion());
							break;
						}
					}
					mov.setIdLoteRecibido(null);
					mov.setIdVenta(v.getIdVenta());
					mov.setES("S");
					mov.setCantidad(v.getCantidadVendida());
					mov.setFechaMovimiento(v.getFechaVenta());

					numMovimientos++;
					listaVentas.remove(0);
					listaMovimientos.add(mov);
				}
				System.out.println("Tamaño lista movimientos (deben ser " + tamBloque + ": " + listaMovimientos.size());
				while(listaMovimientos.size()!=0){				//escribimos los movimientos que hay hasta ahora y los eliminamos de la lista para ahorra memoria
					progreso.incrementar();
					Calendar fechaAux = listaMovimientos.get(0).getFechaMovimiento();
					int dia = fechaAux.get(Calendar.DAY_OF_MONTH);
					int mes = fechaAux.get(Calendar.MONTH)+1;
					int anyo = fechaAux.get(Calendar.YEAR);
					wMov.write(listaMovimientos.get(0).getIdMovimiento() + ";"
							+ listaMovimientos.get(0).getIdProducto() + ";"
							+ listaMovimientos.get(0).getIdUbicacion() + ";"
							+ listaMovimientos.get(0).getIdLoteRecibido() + ";"
							+ listaMovimientos.get(0).getIdVenta() + ";"
							+ listaMovimientos.get(0).getES() + ";"
							+ listaMovimientos.get(0).getCantidad()+ ";"
							+ dia + "-" + mes +"-" + anyo + "\n"
							);
					numMovEscritos++;
					listaMovimientos.remove(0);
				}
				//TODO
			
			}
		}
		//ESTO LO HAGO ESCRIBIR EN EL FICHERO EL ULTIMO BLOQUE DE VENTAS
		while(listaVentas.size()!=0){
			progreso.incrementar();
			Movimiento mov = new Movimiento();
			v = listaVentas.get(0);

			mov.setIdMovimiento(numMovimientos);
			mov.setIdProducto(v.getIdProducto());

			int numUbiProd = listaUbicacionProducto.size();
			for(int j=0; j<numUbiProd; j++){
				if(listaUbicacionProducto.get(j).getIdProducto().equals(v.getIdProducto())){
					mov.setIdUbicacion(listaUbicacionProducto.get(j).getIdUbicacion());
					break;
				}
			}
			mov.setIdLoteRecibido(null);
			mov.setIdVenta(v.getIdVenta());
			mov.setES("S");
			mov.setCantidad(v.getCantidadVendida());
			mov.setFechaMovimiento(v.getFechaVenta());

			numMovimientos++;
			listaVentas.remove(0);
			listaMovimientos.add(mov);
		}
		System.out.println("Tamaño lista movimientos (deben ser < " + tamBloque + " : " + listaMovimientos.size());
		while(listaMovimientos.size()!=0){				//escribimos los movimientos que hay hasta ahora y los eliminamos de la lista para ahorra memoria
			progreso.incrementar();
			Calendar fechaAux = listaMovimientos.get(0).getFechaMovimiento();
			int dia = fechaAux.get(Calendar.DAY_OF_MONTH);
			int mes = fechaAux.get(Calendar.MONTH)+1;
			int anyo = fechaAux.get(Calendar.YEAR);
			wMov.write(listaMovimientos.get(0).getIdMovimiento() + ";"
					+ listaMovimientos.get(0).getIdProducto() + ";"
					+ listaMovimientos.get(0).getIdUbicacion() + ";"
					+ listaMovimientos.get(0).getIdLoteRecibido() + ";"
					+ listaMovimientos.get(0).getIdVenta() + ";"
					+ listaMovimientos.get(0).getES() + ";"
					+ listaMovimientos.get(0).getCantidad()+ ";"
					+ dia + "-" + mes +"-" + anyo + "\n"
					);
			numMovEscritos++;
			listaMovimientos.remove(0);
		}
		//HASTA AQUI ES PARA ESCRIBIR EN EL FICHERO EL ULTIMO BLOQUE DE VENTAS 



		//		if(minimizacionMemoria==true){
		progreso.setTextStopped();
		rVentas.close();
		wMov.close();
		//		}
		poblar_bd.getTextArea().append("[Con minimizacion memoria] Numero de ventas leidas de fichero: " + numVentasLeidas+ "\n");
		poblar_bd.getTextArea().append("[Con minimizacion memoria] Numero de movimientos generados: " + numMovimientos + "\n");
		poblar_bd.getTextArea().append("[Con minimizacion memoria] Numero de movimientos escritos: " + numMovEscritos + "\n");
		return numMovimientos;

	}

	/**Escribir movimientos
	 * @param ruta Ruta del fichero de salida donde se escribiran los movimientos
	 * @return numero de movimientos escritos en fichero de salida*/
	public int escribirMovimientos(String ruta) throws IOException{

		int numMovEscritos=0;

		if(ruta==null)
			ruta = poblar_bd.getRutaSalida()+"movimientos_salida.csv" ;
		File file = new File(ruta);
		FileOutputStream fsal = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fsal);
		Writer w = new BufferedWriter(osw);
		int numMov= listaMovimientos.size();
		for(int i=0; i<numMov;i++){
			progreso.incrementar();

			Calendar fechaAux = listaMovimientos.get(i).getFechaMovimiento();
			int dia = fechaAux.get(Calendar.DAY_OF_MONTH);
			int mes = fechaAux.get(Calendar.MONTH)+1;
			int anyo = fechaAux.get(Calendar.YEAR);
			//			System.out.println("Fecha: " + dia + "-" + mes + "-" + anyo +" Elemento[" + i + "]; " + " IdMovimiento: " + listaMovimientos.get(i).getIdMovimiento() 
			//																				+ " E/S: " + listaMovimientos.get(i).getES()
			//																				+ " IdUbicacion: " + listaMovimientos.get(i).getIdUbicacion()
			//																				+ " IdLote: " + listaMovimientos.get(i).getIdLoteRecibido()
			//																				+ " IdVenta: " + listaMovimientos.get(i).getIdVenta());
			w.write(listaMovimientos.get(i).getIdMovimiento() + ";"
					+ listaMovimientos.get(i).getIdProducto() + ";"
					+ listaMovimientos.get(i).getIdUbicacion() + ";"
					+ listaMovimientos.get(i).getIdLoteRecibido() + ";"
					+ listaMovimientos.get(i).getIdVenta() + ";"
					+ listaMovimientos.get(i).getES() + ";"
					+ listaMovimientos.get(i).getCantidad()+ ";"
					+ dia + "-" + mes +"-" + anyo + "\n"
					);
			numMovEscritos++;
		}
		w.close();
		progreso.setTextStopped();
		return numMovEscritos;
	}

	
	public boolean guardarProyecto(String rutaProyecto){
		
		progreso.setTextRunning("Guardando...");
		ArrayList proyecto = new ArrayList<>();
			proyecto.add(listaProductos.size());
		proyecto.addAll(listaProductos);
			proyecto.add(listaLotesRecibidos.size());
		proyecto.addAll(listaLotesRecibidos);
			proyecto.add(listaVentas.size());
		proyecto.addAll(listaVentas);
			proyecto.add(listaUbicacionProducto.size());
		proyecto.addAll(listaUbicacionProducto);
			proyecto.add(listaMovimientos.size());
		proyecto.addAll(listaMovimientos);
		DatosProyecto datosProyecto = new DatosProyecto();
		try {
			datosProyecto.escribirDatosXML(proyecto, rutaProyecto);
			progreso.setTextStopped();
		} catch (FileNotFoundException e) {
			return false;
		}
		
		return true;
		
	}
public boolean cargarProyecto(String rutaProyecto){
		
		progreso.setTextRunning("Cargando...");
		ArrayList proyecto = new ArrayList<>();
		DatosProyecto datosProyecto = new DatosProyecto();
		try {
			proyecto = datosProyecto.leerDatosXML(rutaProyecto);
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			progreso.setTextStopped();
			return false;
		}
		int numProd = (int) proyecto.remove(0);
		while(numProd!=0){
			listaProductos.add((Producto) proyecto.remove(0));
			numProd--;
		}
		poblar_bd.actualizarEstado();
		poblar_bd.getTextArea().append("Cargada la lista de productos.\n");
		int numLotes= (int) proyecto.remove(0);
		while(numLotes!=0){
			listaLotesRecibidos.add((LoteRecibido) proyecto.remove(0));
			numLotes--;
		}
		poblar_bd.actualizarEstado();
		poblar_bd.getTextArea().append("Cargada la lista de lotes recibidos.\n");
		int numVentas= (int) proyecto.remove(0);
		while(numVentas!=0){
			listaVentas.add((Venta) proyecto.remove(0));
			numVentas--;
		}
		poblar_bd.actualizarEstado();
		poblar_bd.getTextArea().append("Cargada la lista de ventas.\n");
		int numUbiProd= (int) proyecto.remove(0);
		while(numUbiProd!=0){
			listaUbicacionProducto.add((UbicacionProducto) proyecto.remove(0));
			numUbiProd--;
		}
		poblar_bd.actualizarEstado();
		poblar_bd.getTextArea().append("Cargada la lista de ubicacion-producto.\n");
		int numMov= (int) proyecto.remove(0);
		while(numMov!=0){
			listaMovimientos.add((Movimiento) proyecto.remove(0));
			numMov--;
		}
		poblar_bd.actualizarEstado();
		poblar_bd.getTextArea().append("Cargada la lista de movimientos.\n");
		progreso.setTextStopped();
		
		return true;
		
	}
	
	
	/*------------------------------------------------OTROS----------------------------------------------------------*/

	public void leerMarcas(String ruta) throws IOException{

		if(ruta==null)
			ruta=poblar_bd.getRutaEntrada()+"marcas_entrada.csv";

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
			ruta=poblar_bd.getRutaEntrada()+"categorias_entrada.csv";

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
			ruta=poblar_bd.getRutaEntrada()+"productos_entrada.csv";//esta linea esta mal la ruta

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

	public ArrayList<Marca> getListaMarcas() {
		return listaMarcas;
	}

	public void setListaMarcas(ArrayList<Marca> listaMarcas) {
		this.listaMarcas = listaMarcas;
	}

	public ArrayList<Categoria> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(ArrayList<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public ArrayList<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(ArrayList<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public ArrayList<LoteRecibido> getListaLotesRecibidos() {
		return listaLotesRecibidos;
	}

	public void setListaLotesRecibidos(ArrayList<LoteRecibido> listaLotesRecibidos) {
		this.listaLotesRecibidos = listaLotesRecibidos;
	}

	public ArrayList<Proveedor> getListaProveedores() {
		return listaProveedores;
	}

	public void setListaProveedores(ArrayList<Proveedor> listaProveedores) {
		this.listaProveedores = listaProveedores;
	}

	public ArrayList<Almacen> getListaAlmacenes() {
		return listaAlmacenes;
	}

	public void setListaAlmacenes(ArrayList<Almacen> listaAlmacenes) {
		this.listaAlmacenes = listaAlmacenes;
	}

	public ArrayList<Venta> getListaVentas() {
		return listaVentas;
	}

	public void setListaVentas(ArrayList<Venta> listaVentas) {
		this.listaVentas = listaVentas;
	}

	public ArrayList<Ubicacion> getListaUbicaciones() {
		return listaUbicaciones;
	}

	public void setListaUbicaciones(ArrayList<Ubicacion> listaUbicaciones) {
		this.listaUbicaciones = listaUbicaciones;
	}

	public ArrayList<UbicacionProducto> getListaUbicacionProducto() {
		return listaUbicacionProducto;
	}

	public void setListaUbicacionProducto(
			ArrayList<UbicacionProducto> listaUbicacionProducto) {
		this.listaUbicacionProducto = listaUbicacionProducto;
	}

	public ArrayList<Movimiento> getListaMovimientos() {
		return listaMovimientos;
	}

	public void setListaMovimientos(ArrayList<Movimiento> listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}

	public static Calendar getFechaLimite() {
		return fechaLimite;
	}

	public static void setFechaLimite(Calendar fechaLimite) {
		GeneradorCSV.fechaLimite = fechaLimite;
	}

	public static int getStockMedioInicial() {
		return stockMaximoInicial;
	}

	public static void setStockMedioInicial(int stockMedioInicial) {
		GeneradorCSV.stockMaximoInicial = stockMedioInicial;
	}
	public int getNumLotesPrimarios() {
		return numLotesPrimarios;
	}

	public void setNumLotesPrimarios(int numLotesPrimarios) {
		this.numLotesPrimarios = numLotesPrimarios;
	}
	public int getNumLotesSecundarios() {
		return numLotesSecundarios;
	}

	public void setNumLotesSecundarios(int numLotesSecundarios) {
		this.numLotesSecundarios = numLotesSecundarios;
	}

	public ProbabilidadEstacional getProbabilidades() {
		return probabilidades;
	}

	public void setProbabilidades(ProbabilidadEstacional probabilidades) {
		this.probabilidades = probabilidades;
	}

	public static int getStockMinimo() {
		return stockMinimo;
	}

	public static void setStockMinimo(int stockMinimo) {
		GeneradorCSV.stockMinimo = stockMinimo;
	}

	public static int getMaxUbicacionesDistintas() {
		return maxUbicacionesDistintas;
	}

	public static void setMaxUbicacionesDistintas(int maxUbicacionesDistintas) {
		GeneradorCSV.maxUbicacionesDistintas = maxUbicacionesDistintas;
	}

	
	
}
