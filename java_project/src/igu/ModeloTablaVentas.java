package igu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import java_project.Venta;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaVentas extends DefaultTableModel{
	
	static String[] cols={"Id Venta","Id Producto","Cantidad vendida","Precio venta unitario","Fecha venta"};	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ModeloTablaVentas() {
		super(null,cols);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public void cargarDatos(ArrayList<Venta> lista){
		int tamLista = lista.size();
		for(int i=0; i<tamLista; i++){
			
			Vector fila = new Vector();
			fila.add(lista.get(i).getIdVenta());
			fila.add(lista.get(i).getIdProducto());
			fila.add(lista.get(i).getCantidadVendida());
			fila.add(lista.get(i).getPrecioVentaUnitario());
			String fecha = lista.get(i).getFechaVenta().get(Calendar.DAY_OF_MONTH) + "-" +
							(lista.get(i).getFechaVenta().get(Calendar.MONTH)+1) + "-" +
							lista.get(i).getFechaVenta().get(Calendar.YEAR); 
			fila.add(fecha);

			addRow(fila);
		}
	}

}
