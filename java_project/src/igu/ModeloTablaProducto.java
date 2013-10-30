package igu;

import java.util.ArrayList;
import java.util.Vector;

import java_project.Producto;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaProducto extends DefaultTableModel{
	
	static String[] cols={"Id_Producto","Id_Categoria","Id_Marca","Nombre","Precio_compra","Precio_venta","Cantidad_Stock","Peso"};	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ModeloTablaProducto() {
		super(null,cols);
	}
	
	public void addRow(Producto p){

		Vector fila = new Vector();
		fila.add(p.getIdProducto());
		fila.add(p.getNombreCategoria());
		fila.add(p.getNombreMarca());
		fila.add(p.getNombreProducto());
		fila.add(p.getPrecioMedioCompraUnitario());
		fila.add(p.getPrecioMedioVentaUnitario());
		fila.add(p.getCantidadStock());
		fila.add(p.getPeso());
		
		addRow(fila);
	}
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public void cargarDatos(ArrayList<Producto> lista){
		int tamLista = lista.size();
		for(int i=0; i<tamLista; i++){
			
			Vector fila = new Vector();
			fila.add(lista.get(i).getIdProducto());
			fila.add(lista.get(i).getIdCategoria());
			fila.add(lista.get(i).getIdMarca());
			fila.add(lista.get(i).getNombreProducto());
			fila.add(lista.get(i).getPrecioMedioCompraUnitario());
			fila.add(lista.get(i).getPrecioMedioVentaUnitario());
			fila.add(lista.get(i).getCantidadStock());
			fila.add(lista.get(i).getPeso());

			addRow(fila);
		}
	}

}
