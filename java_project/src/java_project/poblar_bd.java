package java_project;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class poblar_bd {

	private JFrame frmPobladorDeTablas;

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
		frmPobladorDeTablas.setBounds(100, 100, 450, 300);
		frmPobladorDeTablas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPobladorDeTablas.getContentPane().setLayout(null);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(23, 118, 402, 137);
		frmPobladorDeTablas.getContentPane().add(textArea);
		
		JButton btnGenerarMarcas = new JButton("Generar Marcas");
		btnGenerarMarcas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneradorSQL generador = new GeneradorSQL();
				textArea.append("Se han generado las marcas correctamente.");
				try {
					generador.generarMarcas(300);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnGenerarMarcas.setBounds(23, 6, 174, 27);
		frmPobladorDeTablas.getContentPane().add(btnGenerarMarcas);
		
		JButton btnGenerarProveedores = new JButton("Generar Proveedores");
		btnGenerarProveedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneradorSQL generador = new GeneradorSQL();
				try {
					generador.generarProveedores(150);
					textArea.setText("Se han generado los proveedores correctamente.");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGenerarProveedores.setBounds(23, 45, 174, 27);
		frmPobladorDeTablas.getContentPane().add(btnGenerarProveedores);
		
		JButton btnGenerarProductos = new JButton("Generar Productos");
		btnGenerarProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGenerarProductos.setBounds(23, 84, 174, 27);
		frmPobladorDeTablas.getContentPane().add(btnGenerarProductos);
	}
}
