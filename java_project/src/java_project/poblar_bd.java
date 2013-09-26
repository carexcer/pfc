package java_project;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.JSpinner;

public class poblar_bd {

	private JFrame frmPobladorDeTablas;
	private JTextField textFieldRutaEntrada;
	private String rutaEntrada = "/home/carlos/pfc/pfc/entradas_app/";
	private String rutaSalida = "/home/carlos/pfc/pfc/salidas_app/";
	private JTextField textFieldRutaSalida;
	static JTextArea textArea;

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
		
		final JSpinner spinnerLotes = new JSpinner(new SpinnerNumberModel(40, 1, 90, 1));		
		spinnerLotes.setBounds(256, 54, 40, 26);
		frmPobladorDeTablas.getContentPane().add(spinnerLotes);
		
		
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
					System.out.println("Error al leer/escribir productos");
					e1.printStackTrace();
				}
			}
		});
		btnLeerProductos.setBounds(16, 10, 185, 27);
		frmPobladorDeTablas.getContentPane().add(btnLeerProductos);
		
		JButton btnPruebas = new JButton("Pruebas");
		btnPruebas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
			}


		
	});
		btnPruebas.setBounds(679, 231, 100, 27);
		frmPobladorDeTablas.getContentPane().add(btnPruebas);
		
		JButton btnGenerarLotesRecibidos = new JButton("Generar Lotes Recibidos");
		btnGenerarLotesRecibidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				final int numLotes = (int) spinnerLotes.getValue();
				textArea.append("Lotes por producto/año: " + numLotes + "\n");				

				Thread hilo = new Thread(new Runnable() {

					@Override
					public void run() {
						try{

							GeneradorCSV gen = new GeneradorCSV();
							textArea.append("Generando lotes recibidos...\n" );
							int numLotesGenerados = 0;
							numLotesGenerados = gen.GenerarLotesRecibidos(numLotes);
							textArea.append("Número de lotes generados: " + numLotesGenerados + "\n");
							int numLotesEscritos = 0;
							numLotesEscritos = gen.escribirLotesRecibidos(null);
							textArea.append("Escribiendo lotes recibidos...\n");
							textArea.append("Número de lotes escritos: " + numLotesEscritos + "\n");
							textArea.append("**************Generación de lotes completada.**************\n" );
						}catch(IOException e){
							System.out.println("Error ejecutando en prueba.");
						}
					}
				});			
				hilo.start();
			}

		});
		btnGenerarLotesRecibidos.setBounds(26, 54, 218, 27);
		frmPobladorDeTablas.getContentPane().add(btnGenerarLotesRecibidos);
		
		
		
		
		
	
	}
}
