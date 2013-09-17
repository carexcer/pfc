package java_project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;	
import java.io.Writer;
import java.util.ArrayList;

public class GeneradorCSV {

	String rutaDefectoEntrada = "/home/carlos/pfc/pfc/entrada_app/";
	String rutaDefectoSalida = "/home/carlos/pfc/pfc/salida_app/";
	ArrayList<Marca> listaMarcas ;
	ArrayList<Categoria> listaCategorias ;
	ArrayList<Producto> listaProductos ;
	
	public GeneradorCSV(){
		this.listaCategorias = new ArrayList<Categoria>();
		this.listaMarcas = new ArrayList<Marca>();
		this.listaProductos = new ArrayList<Producto>();
	}
	public void leerProductos(String ruta) throws IOException{
	
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
			p.setNombreCategoria(campos[1]);			
			p.setNombreMarca(campos[2]);	
			listaProductos.add(p);
			
		}
		r.close();
		
	}
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
}
