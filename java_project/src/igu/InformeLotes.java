package igu;

import java.awt.Color;
import java.util.ArrayList;

import java_project.GeneradorCSV;
import java_project.LoteRecibido;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

public class InformeLotes extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public InformeLotes(GeneradorCSV gen) {
				
		this.setVisible(true);		
		setTitle("Informe de Lotes recibidos");
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
		
		ModeloTablaLotes modelo = new ModeloTablaLotes();
		table = new JTable(modelo);
		scrollPane.add(table);
		scrollPane.setViewportView(table);
		
		//EL CODIGO QUE SE EJECUTA AL ABRIRSE LA VENTANA
		ArrayList<LoteRecibido> lista = gen.getListaLotesRecibidos();
		textAreaAgregados.setText("Obteniendo informacion...");
		
		int numElementos=lista.size();
		int cantidadTotalRecibida=0;
		float valorTotalRecibido=0;
		int numLotesPrimarios=0;
		int numLotesSecundarios=0;
		
		for(int i=0; i<numElementos; i++){
			cantidadTotalRecibida += lista.get(i).getCantidadRecibida();
			valorTotalRecibido += lista.get(i).getCantidadRecibida() * lista.get(i).getPrecioCompraUnitario();
		}
		Float porcentLotesPrimarios=(float) ((gen.getNumLotesPrimarios()/numElementos)*100);
		textAreaAgregados.setText("======== INFORME DE LOTES RECIBIDOS ======\n");	
		textAreaAgregados.append("Numero de lotes: " + numElementos + ".\n");
		textAreaAgregados.append("Numero de lotes de productos primarios: " + gen.getNumLotesPrimarios() + " [" +  porcentLotesPrimarios + "%].\n");
		textAreaAgregados.append("Numero de lotes de productos secundarios: " + gen.getNumLotesSecundarios() + " [" + ((gen.getNumLotesSecundarios()/numElementos)*100) + "%].\n");
		int diferencia = numElementos - gen.getNumLotesPrimarios() - gen.getNumLotesSecundarios();
		textAreaAgregados.append("Total lotes - Lotes de primarios - Lotes secundarios = " + diferencia + ((diferencia==0)?"==> OK":"==> ERROR") + ".\n");
		textAreaAgregados.append("Cantidad Total Recibida: " + cantidadTotalRecibida + " unidades.\n");
		textAreaAgregados.append("Valor Total de compra: " + valorTotalRecibido + " euros.\n");

		modelo.cargarDatos(lista);
		
	}

}