package java_project;

import igu.informeProductos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class poblar_bd {

	private JFrame frmPobladorDeTablas;
	private JTextField textFieldRutaEntrada;
	private String rutaEntrada = "/home/carlos/pfc/pfc/entradas_app/";
	private String rutaSalida = "/home/carlos/pfc/pfc/salidas_app/";
	private JTextField textFieldRutaSalida;
	static JTextArea textArea;
	JTextArea textAreaEstado;
	static public JProgressBar progressBar;
	GeneradorCSV gen = new GeneradorCSV();

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
		System.out.println("Sistema Operativo: " + System.getProperty("os.name"));
		if(System.getProperty("os.name").contains("indows")){
			rutaSalida= "C:\\Users\\Carlos\\Documents\\pfc\\pfc\\salidas_app\\" ;
			rutaEntrada = "C:\\Users\\Carlos\\Documents\\pfc\\pfc\\entradas_app\\" ;
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPobladorDeTablas = new JFrame();
		frmPobladorDeTablas.setTitle("Poblador de Base de Datos");
		frmPobladorDeTablas.setBounds(100, 100, 1000, 600);
		frmPobladorDeTablas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPobladorDeTablas.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setBounds(6, 334, 945, 200);
		frmPobladorDeTablas.getContentPane().add(scrollPane);

		final JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setAutoscrolls(true);

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 968, 320);
		frmPobladorDeTablas.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {200, 200, 200, 200, 200};
		gbl_panel.rowHeights = new int[] {94, 93, 82};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0};
		panel.setLayout(gbl_panel);

		////////////////////////////////////////////////////////////////////////// PANEL PRODUCTOS

		JPanel panelProductos = new JPanel();
		panelProductos.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Productos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelProductos = new GridBagConstraints();
		gbc_panelProductos.fill = GridBagConstraints.BOTH;
		gbc_panelProductos.gridwidth = 2;
		gbc_panelProductos.insets = new Insets(0, 0, 5, 5);
		gbc_panelProductos.gridx = 0;
		gbc_panelProductos.gridy = 0;
		panel.add(panelProductos, gbc_panelProductos);
		GridBagLayout gbl_panelProductos = new GridBagLayout();
		gbl_panelProductos.columnWidths = new int[]{38, 123, 38, 9, 51, 69, 0};
		gbl_panelProductos.rowHeights = new int[]{23, 26, 0};
		gbl_panelProductos.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelProductos.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelProductos.setLayout(gbl_panelProductos);

		final JSpinner spinnerStockMedioProducto = new JSpinner();
		GridBagConstraints gbc_spinnerStockMedioProducto = new GridBagConstraints();
		gbc_spinnerStockMedioProducto.insets = new Insets(0, 0, 0, 5);
		gbc_spinnerStockMedioProducto.fill = GridBagConstraints.BOTH;
		gbc_spinnerStockMedioProducto.gridx = 2;
		gbc_spinnerStockMedioProducto.gridy = 1;
		panelProductos.add(spinnerStockMedioProducto, gbc_spinnerStockMedioProducto);
		spinnerStockMedioProducto.setModel(new SpinnerNumberModel(12, 0, 40, 1));

		JButton btnLeerProductos = new JButton("Generar productos");
		GridBagConstraints gbc_btnLeerProductos = new GridBagConstraints();
		gbc_btnLeerProductos.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnLeerProductos.insets = new Insets(0, 0, 5, 5);
		gbc_btnLeerProductos.gridx = 1;
		gbc_btnLeerProductos.gridy = 0;
		panelProductos.add(btnLeerProductos, gbc_btnLeerProductos);
		btnLeerProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					GeneradorCSV.stockMedioInicial = (int) spinnerStockMedioProducto.getValue();
					Temporizador.iniciarTemporizador();
					textArea.append("Leyendo productos...\n");
					int numProdLeidos = gen.leerProductos(null);
					textArea.append("Leidos " + numProdLeidos + " productos.\n");
					textArea.append("Generando precios de venta...\n");
					int numPreciosVentaGenerados = gen.generarPreciosVentaProducto();					
					textArea.append("Numero de precios generados: " + numPreciosVentaGenerados + " (1 por producto).\n");
					//					Temporizador.iniciarTemporizador();
					//					textArea.append("Generando cantidades de stock...\n");
					//					int cantidadTotalStock = gen.generarCantidadStock();
					//					Temporizador.pararTemporizador("Tiempo que me ahorro al no generar cantidades en producto");
					//					textArea.append("Cantidad total de stock generada: " + cantidadTotalStock + "\n");
					textArea.append("Escribiendo productos...\n");
					int numProdEscritos = gen.escribirProductos(null);
					textArea.append("Escritos " + numProdEscritos + " productos.\n");
					textArea.append("Tiempo ejecucion: " + Temporizador.pararTemporizador("Generacion de productos") + " segundos.\n");
					textArea.append("********************** GENERACION DE PRODUCTOS TERMINADA *******************\n");
					//					gen.flushAll();
				}
				catch (IOException e1) {
					System.out.println("Error al leer/escribir productos");
					e1.printStackTrace();
				}
			}
		});

		JButton btnConsultarProductos = new JButton("Consultar Productos");
		GridBagConstraints gbc_btnConsultarProductos = new GridBagConstraints();
		gbc_btnConsultarProductos.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnConsultarProductos.insets = new Insets(0, 0, 5, 5);
		gbc_btnConsultarProductos.gridwidth = 3;
		gbc_btnConsultarProductos.gridx = 2;
		gbc_btnConsultarProductos.gridy = 0;
		panelProductos.add(btnConsultarProductos, gbc_btnConsultarProductos);
		btnConsultarProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new informeProductos(gen);
			}
		});

		JLabel lblStockMedioInicial = new JLabel("Stock Medio Inicial ");
		GridBagConstraints gbc_lblStockMedioInicial = new GridBagConstraints();
		gbc_lblStockMedioInicial.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblStockMedioInicial.insets = new Insets(0, 0, 0, 5);
		gbc_lblStockMedioInicial.gridx = 1;
		gbc_lblStockMedioInicial.gridy = 1;
		panelProductos.add(lblStockMedioInicial, gbc_lblStockMedioInicial);
				
				JPanel panelUbicacionProducto = new JPanel();
				panelUbicacionProducto.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ubicacion-producto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				GridBagConstraints gbc_panelUbicacionProducto = new GridBagConstraints();
				gbc_panelUbicacionProducto.fill = GridBagConstraints.BOTH;
				gbc_panelUbicacionProducto.insets = new Insets(0, 0, 5, 5);
				gbc_panelUbicacionProducto.gridx = 3;
				gbc_panelUbicacionProducto.gridy = 0;
				panel.add(panelUbicacionProducto, gbc_panelUbicacionProducto);
				GridBagLayout gbl_panelUbicacionProducto = new GridBagLayout();
				gbl_panelUbicacionProducto.columnWidths = new int[]{0, 0};
				gbl_panelUbicacionProducto.rowHeights = new int[]{87, 0};
				gbl_panelUbicacionProducto.columnWeights = new double[]{1.0, Double.MIN_VALUE};
				gbl_panelUbicacionProducto.rowWeights = new double[]{1.0, Double.MIN_VALUE};
				panelUbicacionProducto.setLayout(gbl_panelUbicacionProducto);
				
				JButton btnGenerarUbicacionproducto = new JButton("Generar ubicacion-producto");
				GridBagConstraints gbc_btnGenerarUbicacionproducto = new GridBagConstraints();
				gbc_btnGenerarUbicacionproducto.gridx = 0;
				gbc_btnGenerarUbicacionproducto.gridy = 0;
				panelUbicacionProducto.add(btnGenerarUbicacionproducto, gbc_btnGenerarUbicacionproducto);
		
				JPanel panelFicheros = new JPanel();
				panelFicheros.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ficheros", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				GridBagConstraints gbc_panelFicheros = new GridBagConstraints();
				gbc_panelFicheros.insets = new Insets(0, 0, 5, 5);
				gbc_panelFicheros.fill = GridBagConstraints.HORIZONTAL;
				gbc_panelFicheros.gridheight = 2;
				gbc_panelFicheros.gridx = 4;
				gbc_panelFicheros.gridy = 0;
				panel.add(panelFicheros, gbc_panelFicheros);
				GridBagLayout gbl_panelFicheros = new GridBagLayout();
				gbl_panelFicheros.columnWidths = new int[]{243, 0};
				gbl_panelFicheros.rowHeights = new int[]{15, 27, 15, 27, 23, 0};
				gbl_panelFicheros.columnWeights = new double[]{1.0, Double.MIN_VALUE};
				gbl_panelFicheros.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				panelFicheros.setLayout(gbl_panelFicheros);
				
						JLabel lblRutaEnt = new JLabel("Ruta entrada");
						GridBagConstraints gbc_lblRutaEnt = new GridBagConstraints();
						gbc_lblRutaEnt.anchor = GridBagConstraints.WEST;
						gbc_lblRutaEnt.fill = GridBagConstraints.VERTICAL;
						gbc_lblRutaEnt.insets = new Insets(0, 0, 5, 0);
						gbc_lblRutaEnt.gridx = 0;
						gbc_lblRutaEnt.gridy = 0;
						panelFicheros.add(lblRutaEnt, gbc_lblRutaEnt);
						
								textFieldRutaEntrada = new JTextField();
								GridBagConstraints gbc_textFieldRutaEntrada = new GridBagConstraints();
								gbc_textFieldRutaEntrada.fill = GridBagConstraints.BOTH;
								gbc_textFieldRutaEntrada.insets = new Insets(0, 0, 5, 0);
								gbc_textFieldRutaEntrada.gridx = 0;
								gbc_textFieldRutaEntrada.gridy = 1;
								panelFicheros.add(textFieldRutaEntrada, gbc_textFieldRutaEntrada);
								textFieldRutaEntrada.setColumns(10);
								textFieldRutaEntrada.setText(rutaEntrada);
								
										JLabel lblRutaSal = new JLabel("Ruta salida");
										GridBagConstraints gbc_lblRutaSal = new GridBagConstraints();
										gbc_lblRutaSal.anchor = GridBagConstraints.WEST;
										gbc_lblRutaSal.fill = GridBagConstraints.VERTICAL;
										gbc_lblRutaSal.insets = new Insets(0, 0, 5, 0);
										gbc_lblRutaSal.gridx = 0;
										gbc_lblRutaSal.gridy = 2;
										panelFicheros.add(lblRutaSal, gbc_lblRutaSal);
										
												textFieldRutaSalida = new JTextField();
												GridBagConstraints gbc_textFieldRutaSalida = new GridBagConstraints();
												gbc_textFieldRutaSalida.fill = GridBagConstraints.BOTH;
												gbc_textFieldRutaSalida.insets = new Insets(0, 0, 5, 0);
												gbc_textFieldRutaSalida.gridx = 0;
												gbc_textFieldRutaSalida.gridy = 3;
												panelFicheros.add(textFieldRutaSalida, gbc_textFieldRutaSalida);
												textFieldRutaSalida.setColumns(10);
												textFieldRutaSalida.setText(rutaSalida);
												
														JButton btnGuardarRuta = new JButton("Guardar Ruta");
														GridBagConstraints gbc_btnGuardarRuta = new GridBagConstraints();
														gbc_btnGuardarRuta.anchor = GridBagConstraints.NORTHWEST;
														gbc_btnGuardarRuta.gridx = 0;
														gbc_btnGuardarRuta.gridy = 4;
														panelFicheros.add(btnGuardarRuta, gbc_btnGuardarRuta);
														btnGuardarRuta.addActionListener(new ActionListener() {
															public void actionPerformed(ActionEvent e) {
																rutaEntrada = textFieldRutaEntrada.getText();
																rutaSalida = textFieldRutaSalida.getText();
																textArea.append("Guardadas nuevas rutas:\n" + "Entrada: " + rutaEntrada + "\n" + "Salida: " + rutaSalida + "\n");
															}
														});

		////////////////////////////////////////////////////////////////////////////// PANEL LOTES

		JPanel panelLotes = new JPanel();
		panelLotes.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Lotes recibidos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelLotes = new GridBagConstraints();
		gbc_panelLotes.fill = GridBagConstraints.BOTH;
		gbc_panelLotes.gridwidth = 3;
		gbc_panelLotes.insets = new Insets(0, 0, 5, 5);
		gbc_panelLotes.gridx = 0;
		gbc_panelLotes.gridy = 1;
		panel.add(panelLotes, gbc_panelLotes);
		GridBagLayout gbl_panelLotes = new GridBagLayout();
		gbl_panelLotes.columnWidths = new int[]{56, 47, 50, 47, 63, 39, 63, 39, 0};
		gbl_panelLotes.rowHeights = new int[]{35, 21, 0};
		gbl_panelLotes.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelLotes.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelLotes.setLayout(gbl_panelLotes);

		final JSpinner spinnerNumLotesFijas = new JSpinner();
		GridBagConstraints gbc_spinnerNumLotesFijas = new GridBagConstraints();
		gbc_spinnerNumLotesFijas.anchor = GridBagConstraints.NORTHWEST;
		gbc_spinnerNumLotesFijas.insets = new Insets(0, 0, 0, 5);
		gbc_spinnerNumLotesFijas.gridx = 5;
		gbc_spinnerNumLotesFijas.gridy = 1;
		panelLotes.add(spinnerNumLotesFijas, gbc_spinnerNumLotesFijas);
		spinnerNumLotesFijas.setModel(new SpinnerNumberModel(15, 15, 30, 15));

		JLabel lblNumLotesAnuales = new JLabel("Lotes/a\u00F1o");
		GridBagConstraints gbc_lblNumLotesAnuales = new GridBagConstraints();
		gbc_lblNumLotesAnuales.anchor = GridBagConstraints.EAST;
		gbc_lblNumLotesAnuales.insets = new Insets(0, 0, 0, 5);
		gbc_lblNumLotesAnuales.gridx = 6;
		gbc_lblNumLotesAnuales.gridy = 1;
		panelLotes.add(lblNumLotesAnuales, gbc_lblNumLotesAnuales);

		final JSpinner spinnerLotes = new JSpinner(new SpinnerNumberModel(30, 1, 90, 1));
		GridBagConstraints gbc_spinnerLotes = new GridBagConstraints();
		gbc_spinnerLotes.anchor = GridBagConstraints.NORTHWEST;
		gbc_spinnerLotes.gridx = 7;
		gbc_spinnerLotes.gridy = 1;
		panelLotes.add(spinnerLotes, gbc_spinnerLotes);

		final JRadioButton rdbtnFechasFijas = new JRadioButton("Fechas fijas");
		GridBagConstraints gbc_rdbtnFechasFijas = new GridBagConstraints();
		gbc_rdbtnFechasFijas.anchor = GridBagConstraints.WEST;
		gbc_rdbtnFechasFijas.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnFechasFijas.gridwidth = 2;
		gbc_rdbtnFechasFijas.gridx = 4;
		gbc_rdbtnFechasFijas.gridy = 0;
		panelLotes.add(rdbtnFechasFijas, gbc_rdbtnFechasFijas);
		rdbtnFechasFijas.setSelected(true);		//por defecto, fechas fijas
		rdbtnFechasFijas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spinnerLotes.setEnabled(false);		//desactivo el checkbox de lotes anuales
				spinnerNumLotesFijas.setEnabled(true); //activo el spinner de lotes anuales de fechas fijas
			}
		});
		ButtonGroup grupoRadioFechas = new ButtonGroup();
		if(rdbtnFechasFijas.isSelected()){
			spinnerLotes.setEnabled(false);		
			spinnerNumLotesFijas.setEnabled(true);
		}

		grupoRadioFechas.add(rdbtnFechasFijas);

		JRadioButton rdbtnFechasVariables = new JRadioButton("Fechas variables");
		GridBagConstraints gbc_rdbtnFechasVariables = new GridBagConstraints();
		gbc_rdbtnFechasVariables.anchor = GridBagConstraints.SOUTHWEST;
		gbc_rdbtnFechasVariables.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnFechasVariables.gridwidth = 2;
		gbc_rdbtnFechasVariables.gridx = 6;
		gbc_rdbtnFechasVariables.gridy = 0;
		panelLotes.add(rdbtnFechasVariables, gbc_rdbtnFechasVariables);
		rdbtnFechasVariables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spinnerLotes.setEnabled(true);	//activo el spinner de lotes anuales variable
				spinnerNumLotesFijas.setEnabled(false); //desactivo el spinner de lotes anuales de fechas fijas 
			}
		});
		grupoRadioFechas.add(rdbtnFechasVariables);

		JLabel lblCantMin = new JLabel("Cant. Min");
		GridBagConstraints gbc_lblCantMin = new GridBagConstraints();
		gbc_lblCantMin.anchor = GridBagConstraints.EAST;
		gbc_lblCantMin.insets = new Insets(0, 0, 0, 5);
		gbc_lblCantMin.gridx = 0;
		gbc_lblCantMin.gridy = 1;
		panelLotes.add(lblCantMin, gbc_lblCantMin);

		final JSpinner spinnerCantMin = new JSpinner();
		GridBagConstraints gbc_spinnerCantMin = new GridBagConstraints();
		gbc_spinnerCantMin.anchor = GridBagConstraints.SOUTHWEST;
		gbc_spinnerCantMin.insets = new Insets(0, 0, 0, 5);
		gbc_spinnerCantMin.gridx = 1;
		gbc_spinnerCantMin.gridy = 1;
		panelLotes.add(spinnerCantMin, gbc_spinnerCantMin);
		spinnerCantMin.setModel(new SpinnerNumberModel(10, 1, 100, 1));

		JLabel lblCantMax = new JLabel("Cant. Max");
		GridBagConstraints gbc_lblCantMax = new GridBagConstraints();
		gbc_lblCantMax.anchor = GridBagConstraints.WEST;
		gbc_lblCantMax.insets = new Insets(0, 0, 0, 5);
		gbc_lblCantMax.gridx = 2;
		gbc_lblCantMax.gridy = 1;
		panelLotes.add(lblCantMax, gbc_lblCantMax);

		final JSpinner spinnerCantMax = new JSpinner();
		GridBagConstraints gbc_spinnerCantMax = new GridBagConstraints();
		gbc_spinnerCantMax.anchor = GridBagConstraints.SOUTHWEST;
		gbc_spinnerCantMax.insets = new Insets(0, 0, 0, 5);
		gbc_spinnerCantMax.gridx = 3;
		gbc_spinnerCantMax.gridy = 1;
		panelLotes.add(spinnerCantMax, gbc_spinnerCantMax);
		spinnerCantMax.setModel(new SpinnerNumberModel(30, 1, 100, 1));

		JLabel labelLotesAnyoFijas = new JLabel("Lotes/a\u00F1o");
		GridBagConstraints gbc_labelLotesAnyoFijas = new GridBagConstraints();
		gbc_labelLotesAnyoFijas.anchor = GridBagConstraints.EAST;
		gbc_labelLotesAnyoFijas.insets = new Insets(0, 0, 0, 5);
		gbc_labelLotesAnyoFijas.gridx = 4;
		gbc_labelLotesAnyoFijas.gridy = 1;
		panelLotes.add(labelLotesAnyoFijas, gbc_labelLotesAnyoFijas);

		JButton btnGenerarLotesRecibidos = new JButton("Generar Lotes Recibidos");
		GridBagConstraints gbc_btnGenerarLotesRecibidos = new GridBagConstraints();
		gbc_btnGenerarLotesRecibidos.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnGenerarLotesRecibidos.insets = new Insets(0, 0, 5, 5);
		gbc_btnGenerarLotesRecibidos.gridwidth = 3;
		gbc_btnGenerarLotesRecibidos.gridx = 0;
		gbc_btnGenerarLotesRecibidos.gridy = 0;
		panelLotes.add(btnGenerarLotesRecibidos, gbc_btnGenerarLotesRecibidos);
		btnGenerarLotesRecibidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


				final int numLotesFechaVariable = (int) spinnerLotes.getValue();
				final int numLotesFechaFija = (int) spinnerNumLotesFijas.getValue();

				Thread hilo = new Thread(new Runnable() {

					@Override
					public void run() {
						try{

							Temporizador.iniciarTemporizador();							
							if(rdbtnFechasFijas.isSelected()){
								textArea.append("[Fechas fijas] Generando lotes recibidos...\n" );
								textArea.append("[Fechas fijas] Lotes por producto/año: " + numLotesFechaFija+ "\n");
								int numLotesGenerados = 0;
								numLotesGenerados = gen.GenerarLotesRecibidosFechaFijaCantidadVariable(numLotesFechaFija, (int)spinnerCantMin.getValue(), (int)spinnerCantMax.getValue());
								textArea.append("[Fechas fijas] Numero de lotes generados: " + numLotesGenerados + "\n");
								int numLotesEscritos = 0;
								numLotesEscritos = gen.escribirLotesRecibidos(null);
								textArea.append("[Fechas fijas] Escribiendo lotes recibidos...\n");
								textArea.append("[Fechas fijas] Numero de lotes escritos: " + numLotesEscritos + "\n");
							}
							else{
								progressBar.setMaximum(3);
								textArea.append("[Fechas variables] Generando lotes recibidos...\n" );
								textArea.append("[Fechas variables] Lotes por producto/año: " + numLotesFechaVariable + "\n");
								int numLotesGenerados = 0;
								numLotesGenerados = gen.GenerarLotesRecibidosFechaVariableCantidadVariable(numLotesFechaVariable, (int)spinnerCantMin.getValue(), (int)spinnerCantMax.getValue());
								textArea.append("[Fechas variables] Numero de lotes generados: " + numLotesGenerados + "\n");
								progressBar.setValue(2);
								int numLotesEscritos = 0;
								numLotesEscritos = gen.escribirLotesRecibidos(null);
								textArea.append("[Fechas variables] Escribiendo lotes recibidos...\n");
								textArea.append("[Fechas variables] Numero de lotes escritos: " + numLotesEscritos + "\n");
								progressBar.setValue(3);
							}
							//							gen.flushAll();
							textArea.append("Tiempo ejecucion: " + Temporizador.pararTemporizador("GeneraciÃ³n de lotes") + " segundos.\n");
							textArea.append("*********************** GENERACION DE LOTES COMPLETADA *******************\n" );

						}catch(IOException e){
							System.out.println("Error ejecutando en generacion lotes.");
						}
					}
				});			
				hilo.start();
			}

		});
		
		
				////////////////////////////////////////////////////////////////////////////// PANEL OTROS
				JPanel panelOtros = new JPanel();
				panelOtros.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Otros", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				GridBagConstraints gbc_panelOtros = new GridBagConstraints();
				gbc_panelOtros.fill = GridBagConstraints.BOTH;
				gbc_panelOtros.insets = new Insets(0, 0, 5, 5);
				gbc_panelOtros.gridx = 3;
				gbc_panelOtros.gridy = 1;
				panel.add(panelOtros, gbc_panelOtros);
				GridBagLayout gbl_panelOtros = new GridBagLayout();
				gbl_panelOtros.columnWidths = new int[]{83, 0};
				gbl_panelOtros.rowHeights = new int[]{23, 23, 0};
				gbl_panelOtros.columnWeights = new double[]{0.0, Double.MIN_VALUE};
				gbl_panelOtros.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
				panelOtros.setLayout(gbl_panelOtros);
				
						JButton btnFlushAll = new JButton("FLUSH ALL");
						GridBagConstraints gbc_btnFlushAll = new GridBagConstraints();
						gbc_btnFlushAll.anchor = GridBagConstraints.NORTHWEST;
						gbc_btnFlushAll.insets = new Insets(0, 0, 5, 0);
						gbc_btnFlushAll.gridx = 0;
						gbc_btnFlushAll.gridy = 0;
						panelOtros.add(btnFlushAll, gbc_btnFlushAll);
						
								//////////////////////////////////////////////////// LISTENERS ///////////////////////////////////////////////////////////////////////
								btnFlushAll.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										gen.flushAll();
										textArea.append("===========================BORRADOS TODOS LOS DATOS EN MEMORIA=======================");
									}
								});
								
										JButton btnPruebas = new JButton("Pruebas");
										GridBagConstraints gbc_btnPruebas = new GridBagConstraints();
										gbc_btnPruebas.anchor = GridBagConstraints.NORTHWEST;
										gbc_btnPruebas.gridx = 0;
										gbc_btnPruebas.gridy = 1;
										panelOtros.add(btnPruebas, gbc_btnPruebas);
										btnPruebas.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) {

												try {
													progressBar.setMaximum(4);
													progressBar.setValue(0);
													Temporizador.iniciarTemporizador();
													textArea.append("Leyendo ubicaciones-producto...\n");
													gen.leerUbicaciones(null);
													textArea.append("Generando ubicaciones-producto...\n");
													textArea.append("Numero de ubicaciones-producto generadas: " + gen.generarUbicacionProducto() +"\n");
													progressBar.setValue(1);
													textArea.append("Numero de ubicaciones-producto escritas: " + gen.escribirUbicacionProducto(null) + "\n");
													textArea.append("Generando movimientos...\n");
													progressBar.setValue(2);
													textArea.append("Número de movimientos generados: " + gen.generarMovimientos() + "\n");
													textArea.append("Escribiendo movimientos...\n");

													textArea.append("Número de movimientos escritos: " + gen.escribirMovimientos(null) + "\n");
													textArea.append("Tiempo ejecucion: " + Temporizador.pararTemporizador("Generacion de movimientos") + "\n");

													textArea.append("******************* GENERACION DE MOVIMIENTOS TERMINADA **********************\n");
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												gen.flushAll();
											}



										});


		///////////////////////////////////////////////////////////////////// PANEL VENTAS


		JPanel panelVentas = new JPanel();
		panelVentas.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ventas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelVentas = new GridBagConstraints();
		gbc_panelVentas.fill = GridBagConstraints.BOTH;
		gbc_panelVentas.gridwidth = 3;
		gbc_panelVentas.insets = new Insets(0, 0, 0, 5);
		gbc_panelVentas.gridx = 0;
		gbc_panelVentas.gridy = 2;
		panel.add(panelVentas, gbc_panelVentas);
		GridBagLayout gbl_panelVentas = new GridBagLayout();
		gbl_panelVentas.columnWidths = new int[]{117, 47, 78, 39, 127, 0};
		gbl_panelVentas.rowHeights = new int[]{23, 24, 0};
		gbl_panelVentas.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelVentas.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelVentas.setLayout(gbl_panelVentas);
		
		final JSpinner spinnerPorcent1Ud = new JSpinner();
		spinnerPorcent1Ud.setEnabled(false);
		spinnerPorcent1Ud.setModel(new SpinnerNumberModel(70, 0, 100, 1));
		GridBagConstraints gbc_spinnerPorcent1Ud = new GridBagConstraints();
		gbc_spinnerPorcent1Ud.gridx = 4;
		gbc_spinnerPorcent1Ud.gridy = 1;
		panelVentas.add(spinnerPorcent1Ud, gbc_spinnerPorcent1Ud);

		final JCheckBox chckbxVentas1Ud = new JCheckBox("% ventas 1 Ud.");
		chckbxVentas1Ud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxVentas1Ud.isSelected()==true)
					spinnerPorcent1Ud.setEnabled(true);
				else spinnerPorcent1Ud.setEnabled(false);
			}
		});
		GridBagConstraints gbc_chckbxVentas1Ud = new GridBagConstraints();
		gbc_chckbxVentas1Ud.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxVentas1Ud.anchor = GridBagConstraints.SOUTH;
		gbc_chckbxVentas1Ud.gridx = 4;
		gbc_chckbxVentas1Ud.gridy = 0;
		panelVentas.add(chckbxVentas1Ud, gbc_chckbxVentas1Ud);

		JLabel lblMaxDasVendindose = new JLabel("Max dias en venta");
		GridBagConstraints gbc_lblMaxDasVendindose = new GridBagConstraints();
		gbc_lblMaxDasVendindose.insets = new Insets(0, 0, 0, 5);
		gbc_lblMaxDasVendindose.gridx = 0;
		gbc_lblMaxDasVendindose.gridy = 1;
		panelVentas.add(lblMaxDasVendindose, gbc_lblMaxDasVendindose);

		final JSpinner spinnerMaxDiasVendiendose = new JSpinner();
		GridBagConstraints gbc_spinnerMaxDiasVendiendose = new GridBagConstraints();
		gbc_spinnerMaxDiasVendiendose.anchor = GridBagConstraints.NORTHWEST;
		gbc_spinnerMaxDiasVendiendose.insets = new Insets(0, 0, 0, 5);
		gbc_spinnerMaxDiasVendiendose.gridx = 1;
		gbc_spinnerMaxDiasVendiendose.gridy = 1;
		panelVentas.add(spinnerMaxDiasVendiendose, gbc_spinnerMaxDiasVendiendose);
		spinnerMaxDiasVendiendose.setModel(new SpinnerNumberModel(45, 30, 180, 1));

		JLabel lblMaxCantVenta = new JLabel("Max Cant/Venta");
		GridBagConstraints gbc_lblMaxCantVenta = new GridBagConstraints();
		gbc_lblMaxCantVenta.insets = new Insets(0, 0, 0, 5);
		gbc_lblMaxCantVenta.gridx = 2;
		gbc_lblMaxCantVenta.gridy = 1;
		panelVentas.add(lblMaxCantVenta, gbc_lblMaxCantVenta);

		final JSpinner spinnerMaxCantVenta = new JSpinner();
		GridBagConstraints gbc_spinnerMaxCantVenta = new GridBagConstraints();
		gbc_spinnerMaxCantVenta.anchor = GridBagConstraints.NORTHWEST;
		gbc_spinnerMaxCantVenta.insets = new Insets(0, 0, 0, 5);
		gbc_spinnerMaxCantVenta.gridx = 3;
		gbc_spinnerMaxCantVenta.gridy = 1;
		panelVentas.add(spinnerMaxCantVenta, gbc_spinnerMaxCantVenta);
		spinnerMaxCantVenta.setModel(new SpinnerNumberModel(6, 1, 50, 1));

		JButton btnGenerarVentas = new JButton("Generar Ventas");
		GridBagConstraints gbc_btnGenerarVentas = new GridBagConstraints();
		gbc_btnGenerarVentas.anchor = GridBagConstraints.NORTH;
		gbc_btnGenerarVentas.insets = new Insets(0, 0, 5, 5);
		gbc_btnGenerarVentas.gridx = 0;
		gbc_btnGenerarVentas.gridy = 0;
		panelVentas.add(btnGenerarVentas, gbc_btnGenerarVentas);
		
	
		
				///////////////////////////////////////////////////////////////////////////PANEL ESTADO
		
				JPanel panelEstado = new JPanel();
				panelEstado.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				GridBagConstraints gbc_panelEstado = new GridBagConstraints();
				gbc_panelEstado.fill = GridBagConstraints.BOTH;
				gbc_panelEstado.gridwidth = 2;
				gbc_panelEstado.gridx = 4;
				gbc_panelEstado.gridy = 2;
				panel.add(panelEstado, gbc_panelEstado);
				panelEstado.setLayout(new BorderLayout(0, 0));

				progressBar = new JProgressBar(0, 100);					
				progressBar.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						progressBar.repaint();
					}
				});
				panelEstado.add(progressBar);
				progressBar.setValue(0);
				progressBar.setStringPainted(true);
				progressBar.setString("Nada en ejecución");
				progressBar.setVisible(true);

				textAreaEstado = new JTextArea();
				textAreaEstado.setText("En memoria: ");
				textAreaEstado.setEditable(false);
				textAreaEstado.setBackground(Color.LIGHT_GRAY);
				panelEstado.add(textAreaEstado, BorderLayout.SOUTH);


				btnGenerarVentas.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

				final int maxCantVenta = (int) spinnerMaxCantVenta.getValue();
				textArea.append("Maxima cantidad por venta: " + maxCantVenta + "\n");

				final int maxDiasVendiendose = (int) spinnerMaxDiasVendiendose.getValue();
				textArea.append("Max. dias vendiendose: " + maxDiasVendiendose + "\n");	

				final boolean ventas1Ud = chckbxVentas1Ud.isSelected();
				final int porcentVentas1Ud = (int) spinnerPorcent1Ud.getValue();
				
				Thread hilo = new Thread(new Runnable() {

					@Override
					public void run() {
						try{

							Temporizador.iniciarTemporizador();
							textArea.append("Generando ventas... este proceso puede tardar unos minutos...\n" );
							int numVentasGeneradas = 0;
							numVentasGeneradas = gen.generarVentas(maxCantVenta,maxDiasVendiendose, ventas1Ud, porcentVentas1Ud);
							textArea.append("NÃºmero de ventas generadas: " + numVentasGeneradas + "\n");
							int numVentasEscritas = 0;
							numVentasEscritas = gen.escribirVentas(null);
							textArea.append("Escribiendo ventas...\n");
							textArea.append("NÃºmero de ventas escritas: " + numVentasEscritas + "\n");
							textArea.append("Tiempo ejecucion: " + Temporizador.pararTemporizador("Generacion de ventas") + " segundos.\n");
							int numProdEscritos = gen.escribirProductos(null);
							textArea.append("Se ha modificado y escrito en fichero la cantidad de stock (tabla producto).\n");
							textArea.append("NÃºmero de productos escritos: " + numProdEscritos + "\n");
							textArea.append("*********************** GENERACION DE VENTAS COMPLETADA *******************\n" );
							//							gen.flushAll();
						}catch(IOException e){
							System.out.println("Error ejecutando generar ventas.");
						}
					}
				});			
				hilo.start();

			}
		});





	}
}
