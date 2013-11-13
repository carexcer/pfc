package igu;

import java.util.Vector;

import java_project.ProbabilidadEstacional;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaProbabilidadEstacional extends DefaultTableModel{
	
	static String[] cols={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ModeloTablaProbabilidadEstacional() {
		super(null,cols);
	}

	
	
	@Override
	public void fireTableCellUpdated(int row, int column) {
		if(getValueAt(row, column)==null)
			setValueAt(0, row, column);
		super.fireTableCellUpdated(row, column);
	}



	@Override
	public void addRow(Vector rowData) {
		super.addRow(rowData);
	}

	public void cargarDatos(ProbabilidadEstacional prob){
			Vector fila = new Vector();
			fila.add(prob.getpEnero());
			fila.add(prob.getpFebrero());
			fila.add(prob.getpMarzo());
			fila.add(prob.getpAbril());
			fila.add(prob.getpMayo());
			fila.add(prob.getpJunio());
			fila.add(prob.getpJulio());
			fila.add(prob.getpAgosto());
			fila.add(prob.getpSeptiembre());
			fila.add(prob.getpOctubre());
			fila.add(prob.getpNoviembre());
			fila.add(prob.getpDiciembre());
			addRow(fila);
		
	}

	//La tabla solo acepta enteros
	@Override
	public Class<?> getColumnClass(int arg0) {
		return Integer.class;
	}
	
	/**Devuelve un objeto ProbabilidadEstacional actualizado con las probabilidades de la tabla
	 * @return ProbabilidadEstacional con los datos de la tabla actualizado.*/
	public ProbabilidadEstacional getProbabilidades(){
		
		ProbabilidadEstacional probabilidades = new ProbabilidadEstacional();
		probabilidades.setpEnero((int) getValueAt(0, 0));
		probabilidades.setpFebrero((int) getValueAt(0, 1));
		probabilidades.setpMarzo((int) getValueAt(0, 2));
		probabilidades.setpAbril((int) getValueAt(0, 3));
		probabilidades.setpMayo((int) getValueAt(0, 4));
		probabilidades.setpJunio((int) getValueAt(0, 5));
		probabilidades.setpJulio((int) getValueAt(0, 6));
		probabilidades.setpAgosto((int) getValueAt(0, 7));
		probabilidades.setpSeptiembre((int) getValueAt(0, 8));
		probabilidades.setpOctubre((int) getValueAt(0, 9));
		probabilidades.setpNoviembre((int) getValueAt(0, 10));
		probabilidades.setpDiciembre(((int) getValueAt(0, 11)));
		probabilidades.actualizar();
		return probabilidades;
		
	}
	
}
