package igu;

import java.awt.Color;
import java.util.ArrayList;

import java_project.GeneradorCSV;
import java_project.LoteRecibido;
import java_project.Venta;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

public class InformeVentas extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public InformeVentas (GeneradorCSV gen) {
				
		this.setVisible(true);		
		setTitle("Informe de Ventas");
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
		
		ModeloTablaVentas modelo = new ModeloTablaVentas();
		table = new JTable(modelo);
		scrollPane.add(table);
		scrollPane.setViewportView(table);
		
		//EL CODIGO QUE SE EJECUTA AL ABRIRSE LA VENTANA
		ArrayList<Venta> lista = gen.getListaVentas();
		textAreaAgregados.setText("Obteniendo informacion...");
		
		int numElementos=lista.size();
		int cantidadTotalVendida=0;
		float valorTotalVendido=0;
		
		for(int i=0; i<numElementos; i++){
			cantidadTotalVendida += lista.get(i).getCantidadVendida();
			valorTotalVendido += lista.get(i).getCantidadVendida() * lista.get(i).getPrecioVentaUnitario();
		}
		textAreaAgregados.setText("======== INFORME DE VENTAS ======\n");	
		textAreaAgregados.append("Numero de ventas: " + numElementos + ".\n");
		textAreaAgregados.append("Cantidad total vendida: " + cantidadTotalVendida + " unidades.\n");
		textAreaAgregados.append("Valor total de ventas: " + valorTotalVendido + " euros.\n");

		modelo.cargarDatos(lista);
		
	}

}