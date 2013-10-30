package igu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import java_project.Movimiento;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaMovimientos extends DefaultTableModel{
	
	static String[] cols={"Id Movimiento","Id Producto","Id Ubicacion","Id Lote Recibido","Id Venta","E/S","Cantidad","Fecha movimiento"};	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ModeloTablaMovimientos() {
		super(null,cols);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public void cargarDatos(ArrayList<Movimiento> lista){
		int tamLista = lista.size();
		for(int i=0; i<tamLista; i++){
			
			Vector fila = new Vector();
			fila.add(lista.get(i).getIdMovimiento());
			fila.add(lista.get(i).getIdProducto());
			fila.add(lista.get(i).getIdUbicacion());
			fila.add(lista.get(i).getIdLoteRecibido());
			fila.add(lista.get(i).getIdVenta());
			fila.add(lista.get(i).getES());
			fila.add(lista.get(i).getCantidad());
			String fecha = lista.get(i).getFechaMovimiento().get(Calendar.DAY_OF_MONTH) + "-" +
							(lista.get(i).getFechaMovimiento().get(Calendar.MONTH)+1) + "-" +
							lista.get(i).getFechaMovimiento().get(Calendar.YEAR); 
			fila.add(fecha);

			addRow(fila);
		}
	}

}
