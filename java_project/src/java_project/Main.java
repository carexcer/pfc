package java_project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String ruta = "/home/carlos/pfc/pfc/sql/";
		String bienvenida;
		bienvenida = new String("=== Bienvenido al generador de tablas ===");
		System.out.println(bienvenida);
		System.out.println("1 - LLENAR TABLA MARCA");
		System.out.println("2 - LLENAR TABLA PROVEEDOR");
		System.out.println("3 - LLENAR TABLA PRODUCTO");
		System.out.println("0 - SALIR");

		System.out.println("Selecciona la opcion que desees:");
		Integer option = 1;
		while(option!=0){

			BufferedReader in= new BufferedReader(new InputStreamReader(System.in));
			option = Integer.parseInt(in.readLine());
			System.out.println("opcion selec" +option);
			switch(option){
			case (int) '1'://///////////////////MARCA
			{
				ArrayList<Marca> listaMarcas = new ArrayList<Marca>();
				Marca m1 = new Marca();
				for(int i=0; i<20; i++){			
					m1.setIdMarca(i);
					m1.setNombreComercial("NombreComercial"+i);
					m1.setNombreCompleto("nombreCompleto"+i);
					m1.setDescripcion("Descripcion"+i);
					listaMarcas.add(m1);
				}
				File file = new File(ruta+"poblar_marcas.sql");
				FileOutputStream fsal = new FileOutputStream(file);
				OutputStreamWriter osw = new OutputStreamWriter(fsal);
				Writer w = new BufferedWriter(osw);
				for(int i=0; i<20; i++)
					w.write(listaMarcas.get(i).getInsertSQL());

				w.close();
				break;
			}
			case (int) '2':///////////////////PROVEEDOR
			{
				break;
			}
			case (int) '3':///////////////////PRODUCTO
			{

				break;
			}
			default:
			{
				System.out.println("No has seleccionado ninguna opcion valida.");
				break;
			}
			}

		}
	}

}
