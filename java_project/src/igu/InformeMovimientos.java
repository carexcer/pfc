package igu;

import java.awt.Color;
import java.util.ArrayList;

import java_project.GeneradorCSV;
import java_project.Movimiento;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

public class InformeMovimientos extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public InformeMovimientos(GeneradorCSV gen) {
				
		this.setVisible(true);		
		setTitle("Informe de Movimientos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow][grow]"));
		
		JPanel panelUp = new JPanel();
		contentPane.add(panelUp, "cell 0 0,grow");
		panelUp.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JTextArea textAreaAgregados = new JTextArea();
		textAreaAgregados.setEditable(false);
		panelUp.add(textAreaAgregados, "cell 0 0,grow");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		scrollPane.setAutoscrolls(true);
		contentPane.add(scrollPane, "cell 0 1,grow");
		
		ModeloTablaMovimientos modelo = new ModeloTablaMovimientos();
		table = new JTable(modelo);
		scrollPane.add(table);
		scrollPane.setViewportView(table);
		
		//EL CODIGO QUE SE EJECUTA AL ABRIRSE LA VENTANA
		ArrayList<Movimiento> lista = gen.getListaMovimientos();
		textAreaAgregados.setText("Obteniendo informacion...");
		modelo.cargarDatos(lista);
		
		int numElementos=lista.size();
		int numVentas=0;
		int numLotes=0;
		int cantidadTotalVendida=0;
		int cantidadTotalRecibida=0;
		int diferencia=0;
		int stock=0;
		
		
		for(int i=0; i<numElementos; i++){
			if(lista.get(i).getES().equals("E")){
				cantidadTotalRecibida += lista.get(i).getCantidad();
				numLotes++;
			}else{
				cantidadTotalVendida += lista.get(i).getCantidad();
				numVentas++;
			}
		}
		diferencia = numElementos - numLotes - numVentas;
		stock = cantidadTotalRecibida - cantidadTotalVendida;
		
		textAreaAgregados.setText("======== INFORME DE MOVIMIENTOS ======\n");	
		textAreaAgregados.append("Numero de movimientos: " + numElementos + ".\n");
		textAreaAgregados.append("Numero de lotes recibidos: " + numLotes + ".\n");
		textAreaAgregados.append("Numero de ventas: " + numVentas + ".\n");
		textAreaAgregados.append("Movimientos - Lotes - Ventas = " + diferencia + " ===> " + ((diferencia==0)?" OK":" ERROR") + ".\n");
		
		textAreaAgregados.append("Cantidad total recibida: " + cantidadTotalRecibida + " unidades.\n");
		textAreaAgregados.append("Cantidad total vendida: " + cantidadTotalVendida + " unidades.\n");
		textAreaAgregados.append("Cantidad extra en Stock: " + stock + " (Cantidad recibida - vendida). Esta cantidad es el incremento respecto a la cantidad de stock inicial.\n");
		textAreaAgregados.append("Para consultar la CANTIDAD EN STOCK REAL en este momento CONSULTE LOS PRODUCTOS.\n");
		
		
	}

}