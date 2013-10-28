package igu;

import java.util.ArrayList;

import java_project.GeneradorCSV;
import java_project.ListaProductos;
import java_project.Producto;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class informeProductos extends JFrame {

	private JPanel contentPane;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					informeProductos frame = new informeProductos();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public informeProductos(GeneradorCSV gen) {
				
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
		scrollPane.setAutoscrolls(true);		
		contentPane.add(scrollPane, "cell 0 1,grow");
		
		JTextArea textAreaDatos = new JTextArea();
		textAreaDatos.setEditable(false);
		scrollPane.setViewportView(textAreaDatos);
		
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
		textAreaAgregados.setText("======== INFORME ======\n");	
		textAreaAgregados.append("Numero de productos: " + numElementos + ".\n");
		textAreaAgregados.append("Cantidad Total en Stock: " + cantidadStockTotal + " unidades.\n");
		textAreaAgregados.append("Valor Total de compra: " + valorCompraTotal + " euros.\n");
		textAreaAgregados.append("Valor Total de venta: " +  valorVentaTotal + " euros.\n");
		textAreaAgregados.append("Peso total: " +  pesoTotal + " kg.\n");
		
		for(int i=0; i<1000; i++){
			textAreaDatos.append(lista.get(i).getIdProducto() + " - " 
									+ lista.get(i).getNombreProducto() + " - "
									+ lista.get(i).getCantidadStock() + " - "
									+ lista.get(i).getPrecioMedioCompraUnitario() + "\n ");
		}
		
	}

}
