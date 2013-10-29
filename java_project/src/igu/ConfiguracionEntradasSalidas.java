package igu;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java_project.poblar_bd;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ConfiguracionEntradasSalidas extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldRutaEntrada;
	private JTextField textFieldRutaSalida;

	/**
	 * Create the frame.
	 */
	public ConfiguracionEntradasSalidas() {
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JPanel panelFicheros = new JPanel();
		panelFicheros.setBounds(5, 5, 424, 251);
		panelFicheros.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ficheros", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelFicheros = new GridBagConstraints();
		gbc_panelFicheros.insets = new Insets(0, 0, 5, 5);
		gbc_panelFicheros.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelFicheros.gridheight = 2;
		gbc_panelFicheros.gridx = 4;
		gbc_panelFicheros.gridy = 0;
		panelFicheros.setLayout(null);
		contentPane.add(panelFicheros);

		JLabel lblRutaEnt = new JLabel("Ruta entrada");
		lblRutaEnt.setBounds(12, 31, 143, 16);
		panelFicheros.add(lblRutaEnt);

		JLabel lblRutaSal = new JLabel("Ruta salida");
		lblRutaSal.setBounds(12, 95, 143, 16);
		panelFicheros.add(lblRutaSal);

		final JButton btnGuardarRuta = new JButton("Guardar Ruta");
		btnGuardarRuta.setBounds(12, 172, 143, 25);
		panelFicheros.add(btnGuardarRuta);
		btnGuardarRuta.setEnabled(false);

		textFieldRutaEntrada = new JTextField();
		textFieldRutaEntrada.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				btnGuardarRuta.setEnabled(true);
			}
		});
		textFieldRutaEntrada.setBounds(12, 49, 301, 22);
		panelFicheros.add(textFieldRutaEntrada);
		textFieldRutaEntrada.setColumns(10);
		textFieldRutaEntrada.setText(poblar_bd.getRutaEntrada());
		
		textFieldRutaSalida = new JTextField();
		textFieldRutaSalida.setBounds(12, 115, 301, 22);
		panelFicheros.add(textFieldRutaSalida);
		textFieldRutaSalida.setColumns(10);
		textFieldRutaSalida.setText(poblar_bd.getRutaSalida());
		textFieldRutaSalida.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				btnGuardarRuta.setEnabled(true);
			}
		});
		
		btnGuardarRuta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rutaE =textFieldRutaEntrada.getText(); 
				poblar_bd.setRutaEntrada(rutaE);
				String rutaS = textFieldRutaSalida.getText();
				poblar_bd.setRutaSalida(rutaS);
				String msj = "Nueva ruta entrada: \n" + textFieldRutaEntrada.getText() + "\nNueva ruta salida: \n" + textFieldRutaSalida.getText();
				JOptionPane.showMessageDialog(contentPane,msj);
				btnGuardarRuta.setEnabled(false);
//				poblar_bd.getTextArea().append("Guardadas nuevas rutas:\n" + "Entrada: " + textFieldRutaEntrada.getText() + "\n" + "Salida: " + textFieldRutaSalida.getText() + "\n");
			}
		});

	}
}
