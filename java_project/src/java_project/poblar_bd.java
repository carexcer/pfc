package java_project;

import java.awt.Dialog;
import java.awt.EventQueue;

import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class poblar_bd {

	private JFrame frmPobladorDeTablas;
	private JTextField textFieldRutaEntrada;
	private String rutaEntrada = "/home/carlos/pfc/pfc/entradas_app/";
	private String rutaSalida = "/home/carlos/pfc/pfc/salidas_app/";
	private JTextField textFieldRutaSalida;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					poblar_bd window = new poblar_bd();
					window.frmPobladorDeTablas.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public poblar_bd() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPobladorDeTablas = new JFrame();
		frmPobladorDeTablas.setTitle("Poblador de Base de Datos");
		frmPobladorDeTablas.setBounds(100, 100, 801, 536);
		frmPobladorDeTablas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPobladorDeTablas.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 270, 785, 234);
		frmPobladorDeTablas.getContentPane().add(scrollPane);
		
		final JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setAutoscrolls(true);
		
		JLabel lblRutaEnt = new JLabel("Ruta entrada");
		lblRutaEnt.setBounds(536, 22, 100, 15);
		frmPobladorDeTablas.getContentPane().add(lblRutaEnt);
		
		textFieldRutaEntrada = new JTextField();
		textFieldRutaEntrada.setBounds(536, 37, 243, 27);
		frmPobladorDeTablas.getContentPane().add(textFieldRutaEntrada);
		textFieldRutaEntrada.setColumns(10);
		textFieldRutaEntrada.setText(rutaEntrada);
		
		JLabel lblRutaSal = new JLabel("Ruta salida");
		lblRutaSal.setBounds(536, 66, 100, 15);
		frmPobladorDeTablas.getContentPane().add(lblRutaSal);
		
		textFieldRutaSalida = new JTextField();
		textFieldRutaSalida.setBounds(536, 86, 243, 27);
		frmPobladorDeTablas.getContentPane().add(textFieldRutaSalida);
		textFieldRutaSalida.setColumns(10);
		textFieldRutaSalida.setText(rutaSalida);
		
		JButton btnGuardarRuta = new JButton("Guardar Ruta");
		btnGuardarRuta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rutaEntrada = textFieldRutaEntrada.getText();
				rutaSalida = textFieldRutaSalida.getText();
				textArea.append("Guardadas nuevas rutas:\n" + "Entrada: " + rutaEntrada + "\n" + "Salida: " + rutaSalida + "\n");
			}
		});
		btnGuardarRuta.setBounds(628, 125, 151, 27);
		frmPobladorDeTablas.getContentPane().add(btnGuardarRuta);
		
		JButton btnLeerProductos = new JButton("GENERAR PRODUCTOS");
		btnLeerProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneradorCSV gen = new GeneradorCSV();
				try {
					textArea.append("Leyendo productos...\n");
					int numProdLeidos = gen.leerProductos(null);
					textArea.append("Leidos " + numProdLeidos + " productos.\n");
					textArea.append("Generando precios de venta...\n");
					int numPreciosVentaGenerados = gen.generarPreciosVenta();					
					textArea.append("Numero de precios generados: " + numPreciosVentaGenerados + " (1 por producto).\n");
					textArea.append("Generando cantidades de stock...\n");
					int cantidadTotalStock = gen.generarCantidadStock();
					textArea.append("Cantidad total de stock generada: " + cantidadTotalStock + "\n");
					textArea.append("Escribiendo productos...\n");
					int numProdEscritos = gen.escribirProductos(null);
					textArea.append("Escritos " + numProdEscritos + " productos.\n");
					textArea.append("****** Terminado. Productos generados. ******\n");
					
				}
				catch (IOException e1) {
					System.out.println("Error al leer productos");
					e1.printStackTrace();
				}
				try {
					
					gen.escribirProductos(null);
				} catch (IOException e) {
					System.out.println("Error al escribir productos");
					e.printStackTrace();
				}
			}
		});
		btnLeerProductos.setBounds(16, 10, 185, 27);
		frmPobladorDeTablas.getContentPane().add(btnLeerProductos);
		
		JButton btnPruebas = new JButton("Pruebas");
		btnPruebas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneradorCSV gen = new GeneradorCSV();
				try {
					gen.GenerarLotesRecibidos(null);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnPruebas.setBounds(679, 231, 100, 27);
		frmPobladorDeTablas.getContentPane().add(btnPruebas);
		
		
		
	
	}
}
