package igu;

import java.awt.Color;
import java.util.ArrayList;

import java_project.GeneradorCSV;
import java_project.UbicacionProducto;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

public class InformeUbicacionProducto extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public InformeUbicacionProducto (GeneradorCSV gen) {
				
		this.setVisible(true);		
		setTitle("Informe de Ubicaciones-Producto");
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
		
		ModeloTablaUbicacionProducto modelo = new ModeloTablaUbicacionProducto();
		table = new JTable(modelo);
		scrollPane.add(table);
		scrollPane.setViewportView(table);
		
		//EL CODIGO QUE SE EJECUTA AL ABRIRSE LA VENTANA
		ArrayList<UbicacionProducto> lista = gen.getListaUbicacionProducto();
		textAreaAgregados.setText("Obteniendo informacion...");
		modelo.cargarDatos(lista);
		
		int numElementos=lista.size();
		int cantidadTotal=0;
		
		for(int i=0; i<numElementos; i++){
			cantidadTotal += lista.get(i).getCantidad();
		}
		textAreaAgregados.setText("======== INFORME DE UBICACIONES-PRODUCTO ======\n");	
		textAreaAgregados.append("Numero de ubicaciones-producto: " + numElementos + ".\n");
		textAreaAgregados.append("Cantidad total (equivalente a Stock Total): " + cantidadTotal+ " unidades.\n");

		
	}

}