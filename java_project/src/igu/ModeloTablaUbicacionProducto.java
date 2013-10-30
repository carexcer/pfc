package igu;

import java.util.ArrayList;
import java.util.Vector;

import java_project.UbicacionProducto;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaUbicacionProducto extends DefaultTableModel{
	
	static String[] cols={"Id Ubicacion","Id Producto","Cantidad"};	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ModeloTablaUbicacionProducto() {
		super(null,cols);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public void cargarDatos(ArrayList<UbicacionProducto> lista){
		int tamLista = lista.size();
		for(int i=0; i<tamLista; i++){
			
			Vector fila = new Vector();
			fila.add(lista.get(i).getIdUbicacion());
			fila.add(lista.get(i).getIdProducto());
			fila.add(lista.get(i).getCantidad());

			addRow(fila);
		}
	}

}
