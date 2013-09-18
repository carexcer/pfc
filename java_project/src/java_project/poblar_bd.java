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
	private JTextField textFieldRuta;
	private String ruta = "/home/carlos/pfc/pfc/sql/";

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
		frmPobladorDeTablas.setBounds(100, 100, 600, 500);
		frmPobladorDeTablas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPobladorDeTablas.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 248, 553, 220);
		frmPobladorDeTablas.getContentPane().add(scrollPane);
		
		final JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setAutoscrolls(true);
		
		JLabel lblRuta = new JLabel("Ruta");
		lblRuta.setBounds(414, 12, 60, 15);
		frmPobladorDeTablas.getContentPane().add(lblRuta);
		
		textFieldRuta = new JTextField();
		textFieldRuta.setBounds(322, 39, 243, 27);
		frmPobladorDeTablas.getContentPane().add(textFieldRuta);
		textFieldRuta.setColumns(10);
		textFieldRuta.setText(ruta);
		
		JButton btnGuardarRuta = new JButton("Guardar Ruta");
		btnGuardarRuta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ruta = textFieldRuta.getText();
				textArea.append("Guardada nueva ruta: " + ruta + "\n");
			}
		});
		btnGuardarRuta.setBounds(414, 66, 151, 27);
		frmPobladorDeTablas.getContentPane().add(btnGuardarRuta);
		
		JButton btnLeerProductos = new JButton("GENERAR PRODUCTOS");
		btnLeerProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneradorCSV gen = new GeneradorCSV();
				try {
					textArea.append("Leyendo productos...\n");
					gen.leerProductos(null);
					textArea.append("Generando precios de venta...\n");
					gen.generarPreciosVenta();
					textArea.append("Generando cantidades de stock...\n");
					gen.generarCantidadStock();
					textArea.append("Escribiendo productos...\n");
					gen.escribirProductos(null);
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
		btnLeerProductos.setBounds(23, 22, 185, 27);
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
		btnPruebas.setBounds(414, 105, 100, 27);
		frmPobladorDeTablas.getContentPane().add(btnPruebas);
		
	
	}
}
