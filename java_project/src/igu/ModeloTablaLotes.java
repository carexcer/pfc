package igu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import java_project.LoteRecibido;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaLotes extends DefaultTableModel{
	
	static String[] cols={"Id_Lote Recibido","Id Proveedor","Id Producto","Fecha Recepcion","Cantidad Recibida","Precio Compra Unitario"};	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ModeloTablaLotes() {
		super(null,cols);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public void cargarDatos(ArrayList<LoteRecibido> lista){
		int tamLista = lista.size();
		for(int i=0; i<tamLista; i++){
			
			Vector fila = new Vector();
			fila.add(lista.get(i).getIdLoteRecibido());
			fila.add(lista.get(i).getIdProveedor());
			fila.add(lista.get(i).getIdProducto());
			String fecha = lista.get(i).getFechaRecepcion().get(Calendar.DAY_OF_MONTH) + "-" +
							(lista.get(i).getFechaRecepcion().get(Calendar.MONTH)+1) + "-" +
							lista.get(i).getFechaRecepcion().get(Calendar.YEAR); 
			fila.add(fecha);
			fila.add(lista.get(i).getCantidadRecibida());
			fila.add(lista.get(i).getPrecioCompraUnitario());

			addRow(fila);
		}
	}

}
