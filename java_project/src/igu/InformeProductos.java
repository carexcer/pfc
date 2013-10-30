package igu;

import java.awt.Color;
import java.util.ArrayList;

import java_project.GeneradorCSV;
import java_project.Producto;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

public class InformeProductos extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public InformeProductos(GeneradorCSV gen) {
				
		this.setVisible(true);		
		setTitle("Informe de productos");
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
		
		ModeloTablaProducto modelo = new ModeloTablaProducto();
		table = new JTable(modelo);
		scrollPane.add(table);
		scrollPane.setViewportView(table);
		
		//EL CODIGO QUE SE EJECUTA AL ABRIRSE LA VENTANA
		ArrayList<Producto> lista = gen.getListaProductos();
		textAreaAgregados.setText("Obteniendo informacion...");
		
		int numElementos=lista.size();
		float pesoTotal=0;
		int cantidadStockTotal=0;
		float valorCompraTotal=0;
		float valorVentaTotal=0;
		
		for(int i=0; i<numElementos; i++){
			pesoTotal += lista.get(i).getPeso() * lista.get(i).getCantidadStock();
			cantidadStockTotal += lista.get(i).getCantidadStock();
			valorCompraTotal += lista.get(i).getPrecioMedioCompraUnitario() + lista.get(i).getCantidadStock();
			valorVentaTotal += lista.get(i).getPrecioMedioVentaUnitario() + lista.get(i).getCantidadStock();			
		}
		textAreaAgregados.setText("======== INFORME DE PRODUCTOS ======\n");	
		textAreaAgregados.append("Numero de productos: " + numElementos + ".\n");
		textAreaAgregados.append("Cantidad Total en Stock: " + cantidadStockTotal + " unidades.\n");
		textAreaAgregados.append("Valor Total de compra: " + valorCompraTotal + " euros.\n");
		textAreaAgregados.append("Valor Total de venta: " +  valorVentaTotal + " euros.\n");
		textAreaAgregados.append("Peso total: " +  pesoTotal + " kg.\n");

		modelo.cargarDatos(lista);
		
	}

}
