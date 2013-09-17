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
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(23, 291, 402, 137);
		frmPobladorDeTablas.getContentPane().add(textArea);
		
		JButton btnGenerarMarcas = new JButton("Generar Marcas");
		btnGenerarMarcas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneradorSQL generador = new GeneradorSQL();
				textArea.append("Se han generado las marcas correctamente.\n");
				try {
					generador.generarMarcas(300, ruta);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnGenerarMarcas.setBounds(23, 6, 208, 27);
		frmPobladorDeTablas.getContentPane().add(btnGenerarMarcas);
		
		JButton btnGenerarProveedores = new JButton("Generar Proveedores");
		btnGenerarProveedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneradorSQL generador = new GeneradorSQL();
				try {
					generador.generarProveedores(150, ruta);
					textArea.append("Se han generado los proveedores correctamente.\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGenerarProveedores.setBounds(23, 45, 208, 27);
		frmPobladorDeTablas.getContentPane().add(btnGenerarProveedores);
		
		JButton btnGenerarProductos = new JButton("Generar Productos");
		btnGenerarProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGenerarProductos.setBounds(23, 84, 208, 27);
		frmPobladorDeTablas.getContentPane().add(btnGenerarProductos);
		
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
		
		JButton btnNomcatPorIdcat = new JButton("NomCat por IdCat en Producto");
		btnNomcatPorIdcat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frmPobladorDeTablas, "El producto con id=10 de categoria accesorios LG Optimus L5 II hay que introducirlo manualmente.", "Atencion",0);

				GeneradorCSV gen = new GeneradorCSV();
				try {
					gen.NombrePorIdCategoria(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNomcatPorIdcat.setBounds(27, 154, 251, 27);
		frmPobladorDeTablas.getContentPane().add(btnNomcatPorIdcat);
		
		JButton btnNommarcaPorIdmarca = new JButton("NomMarca por IdMarca en Producto");
		btnNommarcaPorIdmarca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneradorCSV gen = new GeneradorCSV();
				try {
					gen.NombrePorIdMarca(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNommarcaPorIdmarca.setBounds(23, 191, 251, 27);
		frmPobladorDeTablas.getContentPane().add(btnNommarcaPorIdmarca);
	}
}
