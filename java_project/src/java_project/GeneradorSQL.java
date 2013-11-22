package java_project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class GeneradorSQL {
	
	public void generarMarcas(int cantidad, String ruta) throws IOException{
		
		ArrayList<Marca> listaMarcas = new ArrayList<Marca>();
		for(int i=0; i<cantidad; i++){			
			Marca m1 = new Marca();
			m1.setIdMarca(i);
			m1.setNombre("Nombre"+i);
			listaMarcas.add(m1);
		}
		File file = new File(ruta+"poblar_marcas.sql");
		FileOutputStream fsal = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fsal);
		Writer w = new BufferedWriter(osw);
		for(int j=0; j<cantidad; j++)
			w.write(listaMarcas.get(j).getInsertSQL());
		w.close();
	}
	public void generarProveedores(int cantidad, String ruta) throws IOException{
		
		ArrayList<Proveedor> listaProveedores = new ArrayList<Proveedor>();
		for(int i=0; i<cantidad; i++){			
			Proveedor p1 = new Proveedor();
			p1.setIdProveedor(i);
			p1.setNif(i+i+i+i+"E");
			p1.setNombreProveedor("nomproveedor"+i);
			p1.setTelefono("telfproveedor"+i);
			p1.setEmail("info" + "@" + "nomproveedir" + i + ".com");
			p1.setDireccion("DirecProveedor num. "+i);
			listaProveedores.add(p1);
		}
		File file = new File(ruta+"poblar_proveedores.sql");
		FileOutputStream fsal = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fsal);
		Writer w = new BufferedWriter(osw);
		for(int j=0; j<cantidad; j++)
			w.write(listaProveedores.get(j).getInsertSQL());
		w.close();
	}
	public void generarProductos(int cantidad, String ruta) throws IOException{
		
		ArrayList<Marca> listaMarcas = new ArrayList<Marca>();
		Marca m1 = new Marca();
		for(int i=0; i<cantidad; i++){			
			m1.setIdMarca(i);
			m1.setNombre("Nombre"+i);
			listaMarcas.add(m1);
		}
		File file = new File(ruta+"poblar_marcas.sql");
		FileOutputStream fsal = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fsal);
		Writer w = new BufferedWriter(osw);
		for(int j=0; j<cantidad; j++)
			w.write(listaMarcas.get(j).getInsertSQL());
		w.close();
	}
}


	


