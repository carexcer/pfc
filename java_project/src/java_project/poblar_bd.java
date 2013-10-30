package java_project;

import igu.ConfiguracionEntradasSalidas;
import igu.InformeLotes;
import igu.InformeMovimientos;
import igu.InformeProductos;
import igu.InformeUbicacionProducto;
import igu.InformeVentas;

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
import javax.swing.JOptionPane;
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
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.BoxLayout;
import net.miginfocom.swing.MigLayout;

public class poblar_bd {

	private JFrame frmPobladorDeTablas;
	static public String rutaEntrada = "/home/carlos/pfc/pfc/entradas_app/";
	static public String rutaSalida = "/home/carlos/pfc/pfc/salidas_app/";
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
		gbl_panel.columnWidths = new int[] {95, 83, 77};
		gbl_panel.rowHeights = new int[] {94, 93, 82};
		gbl_panel.columnWeights = new double[]{1.0};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0};
		panel.setLayout(gbl_panel);

		////////////////////////////////////////////////////////////////////////// PANEL PRODUCTOS

		JPanel panelProductos = new JPanel();
		panelProductos.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Productos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelProductos = new GridBagConstraints();
		gbc_panelProductos.fill = GridBagConstraints.BOTH;
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
					actualizarEstado();
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
				new InformeProductos(gen);
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
		gbc_panelUbicacionProducto.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelUbicacionProducto.insets = new Insets(0, 0, 5, 5);
		gbc_panelUbicacionProducto.gridx = 1;
		gbc_panelUbicacionProducto.gridy = 0;
		panel.add(panelUbicacionProducto, gbc_panelUbicacionProducto);
		GridBagLayout gbl_panelUbicacionProducto = new GridBagLayout();
		gbl_panelUbicacionProducto.columnWidths = new int[] {20, 0};
		gbl_panelUbicacionProducto.rowHeights = new int[]{87, 0};
		gbl_panelUbicacionProducto.columnWeights = new double[]{1.0, 0.0};
		gbl_panelUbicacionProducto.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelUbicacionProducto.setLayout(gbl_panelUbicacionProducto);

		JButton btnGenerarUbicacionproducto = new JButton("Generar ubicacion-producto");
		btnGenerarUbicacionproducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				Temporizador.iniciarTemporizador();
				textArea.append("Leyendo ubicaciones-producto...\n");
				try {
					gen.leerUbicaciones(null);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error leyendo ubicaciones-producto. Compruebe que el fichero de entrada existe.", null, JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				textArea.append("Generando ubicaciones-producto...\n");
				try {
					textArea.append("Numero de ubicaciones-producto generadas: " + gen.generarUbicacionProducto() +"\n");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error generando ubicaciones-producto.", null, JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				try {
					textArea.append("Numero de ubicaciones-producto escritas: " + gen.escribirUbicacionProducto(null) + "\n");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error escribiendo ubicaciones-producto.", null, JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				textArea.append("Tiempo ejecucion: " + Temporizador.pararTemporizador("Generacion de ubicacion-producto") + "\n");
				textArea.append("******************* GENERACION DE UBICACION-PRODUCTO TERMINADA **********************\n");
				actualizarEstado();

			}
		});
		GridBagConstraints gbc_btnGenerarUbicacionproducto = new GridBagConstraints();
		gbc_btnGenerarUbicacionproducto.insets = new Insets(0, 0, 0, 5);
		gbc_btnGenerarUbicacionproducto.gridx = 0;
		gbc_btnGenerarUbicacionproducto.gridy = 0;
		panelUbicacionProducto.add(btnGenerarUbicacionproducto, gbc_btnGenerarUbicacionproducto);

		JButton btnConsultarUbicacionproducto = new JButton("Consultar Ubicacion-producto");
		btnConsultarUbicacionproducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InformeUbicacionProducto(gen);
			}
		});
		GridBagConstraints gbc_btnConsultarUbicacionproducto = new GridBagConstraints();
		gbc_btnConsultarUbicacionproducto.gridx = 1;
		gbc_btnConsultarUbicacionproducto.gridy = 0;
		panelUbicacionProducto.add(btnConsultarUbicacionproducto, gbc_btnConsultarUbicacionproducto);



		///////////////////////////////////////////////////////////////////////////PANEL ESTADO

		JPanel panelEstado = new JPanel();
		panelEstado.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelEstado = new GridBagConstraints();
		gbc_panelEstado.insets = new Insets(0, 0, 5, 0);
		gbc_panelEstado.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelEstado.gridx = 2;
		gbc_panelEstado.gridy = 0;
		panel.add(panelEstado, gbc_panelEstado);
		panelEstado.setLayout(null);
		GridBagLayout gbl_panelEstado = new GridBagLayout();
		gbl_panelEstado.columnWidths = new int[]{174, 0};
		gbl_panelEstado.rowHeights = new int[]{22, 35, 22, 0};
		gbl_panelEstado.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelEstado.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelEstado.setLayout(gbl_panelEstado);


		progressBar = new JProgressBar(0, 100);
		progressBar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				progressBar.repaint();
			}
		});
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setString("Nada en ejecución");
		progressBar.setVisible(true);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.BOTH;
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 0;
		panelEstado.add(progressBar, gbc_progressBar);

		textAreaEstado = new JTextArea();
		textAreaEstado.setText("En memoria: ");
		textAreaEstado.setEditable(false);
		textAreaEstado.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_textAreaEstado = new GridBagConstraints();
		gbc_textAreaEstado.gridheight = 2;
		gbc_textAreaEstado.insets = new Insets(0, 0, 5, 0);
		gbc_textAreaEstado.fill = GridBagConstraints.BOTH;
		gbc_textAreaEstado.gridx = 0;
		gbc_textAreaEstado.gridy = 1;
		panelEstado.add(textAreaEstado, gbc_textAreaEstado);

		////////////////////////////////////////////////////////////////////////////// PANEL LOTES

		JPanel panelLotes = new JPanel();
		panelLotes.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Lotes recibidos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelLotes = new GridBagConstraints();
		gbc_panelLotes.fill = GridBagConstraints.VERTICAL;
		gbc_panelLotes.gridwidth = 2;
		gbc_panelLotes.insets = new Insets(0, 0, 5, 5);
		gbc_panelLotes.gridx = 0;
		gbc_panelLotes.gridy = 1;
		panel.add(panelLotes, gbc_panelLotes);
		GridBagLayout gbl_panelLotes = new GridBagLayout();
		gbl_panelLotes.columnWidths = new int[] {100, 100, 100, 100, 0, 100, 100, 100, 100, 100, 100};
		gbl_panelLotes.rowHeights = new int[]{35, 0, 21, 0};
		gbl_panelLotes.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gbl_panelLotes.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelLotes.setLayout(gbl_panelLotes);

		JButton btnGenerarLotesRecibidos = new JButton("Generar Lotes Recibidos");
		GridBagConstraints gbc_btnGenerarLotesRecibidos = new GridBagConstraints();
		gbc_btnGenerarLotesRecibidos.insets = new Insets(0, 0, 5, 5);
		gbc_btnGenerarLotesRecibidos.gridwidth = 3;
		gbc_btnGenerarLotesRecibidos.gridx = 0;
		gbc_btnGenerarLotesRecibidos.gridy = 0;
		panelLotes.add(btnGenerarLotesRecibidos, gbc_btnGenerarLotesRecibidos);

		JButton btnConsultarLotes = new JButton("Consultar Lotes");
		btnConsultarLotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new InformeLotes(gen);
			}
		});
		GridBagConstraints gbc_btnConsultarLotes = new GridBagConstraints();
		gbc_btnConsultarLotes.gridwidth = 3;
		gbc_btnConsultarLotes.insets = new Insets(0, 0, 5, 5);
		gbc_btnConsultarLotes.gridx = 3;
		gbc_btnConsultarLotes.gridy = 0;
		panelLotes.add(btnConsultarLotes, gbc_btnConsultarLotes);

		final JSpinner spinnerNumLotesFijas = new JSpinner();
		GridBagConstraints gbc_spinnerNumLotesFijas = new GridBagConstraints();
		gbc_spinnerNumLotesFijas.anchor = GridBagConstraints.NORTHEAST;
		gbc_spinnerNumLotesFijas.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerNumLotesFijas.gridx = 7;
		gbc_spinnerNumLotesFijas.gridy = 1;
		panelLotes.add(spinnerNumLotesFijas, gbc_spinnerNumLotesFijas);
		spinnerNumLotesFijas.setModel(new SpinnerNumberModel(15, 15, 30, 15));

		final JSpinner spinnerLotes = new JSpinner(new SpinnerNumberModel(30, 1, 90, 1));
		GridBagConstraints gbc_spinnerLotes = new GridBagConstraints();
		gbc_spinnerLotes.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerLotes.anchor = GridBagConstraints.NORTHEAST;
		gbc_spinnerLotes.gridx = 9;
		gbc_spinnerLotes.gridy = 1;
		panelLotes.add(spinnerLotes, gbc_spinnerLotes);


		final JRadioButton rdbtnFechasFijas = new JRadioButton("Fechas fijas");
		GridBagConstraints gbc_rdbtnFechasFijas = new GridBagConstraints();
		gbc_rdbtnFechasFijas.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnFechasFijas.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnFechasFijas.gridwidth = 2;
		gbc_rdbtnFechasFijas.gridx = 6;
		gbc_rdbtnFechasFijas.gridy = 0;
		panelLotes.add(rdbtnFechasFijas, gbc_rdbtnFechasFijas);
		rdbtnFechasFijas.setSelected(true);		//por defecto, fechas fijas

		ButtonGroup grupoRadioFechas = new ButtonGroup();
		if(rdbtnFechasFijas.isSelected()){
			spinnerLotes.setEnabled(false);		
			spinnerNumLotesFijas.setEnabled(true);
		}		

		rdbtnFechasFijas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spinnerLotes.setEnabled(false);		//desactivo el checkbox de lotes anuales
				spinnerNumLotesFijas.setEnabled(true); //activo el spinner de lotes anuales de fechas fijas
			}
		});
		grupoRadioFechas.add(rdbtnFechasFijas);

		JRadioButton rdbtnFechasVariables = new JRadioButton("Fechas variables");
		GridBagConstraints gbc_rdbtnFechasVariables = new GridBagConstraints();
		gbc_rdbtnFechasVariables.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnFechasVariables.anchor = GridBagConstraints.SOUTH;
		gbc_rdbtnFechasVariables.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnFechasVariables.gridwidth = 2;
		gbc_rdbtnFechasVariables.gridx = 8;
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
		gbc_lblCantMin.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCantMin.insets = new Insets(0, 0, 5, 5);
		gbc_lblCantMin.gridx = 1;
		gbc_lblCantMin.gridy = 1;
		panelLotes.add(lblCantMin, gbc_lblCantMin);

		final JSpinner spinnerCantMin = new JSpinner();
		GridBagConstraints gbc_spinnerCantMin = new GridBagConstraints();
		gbc_spinnerCantMin.anchor = GridBagConstraints.SOUTH;
		gbc_spinnerCantMin.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerCantMin.gridx = 2;
		gbc_spinnerCantMin.gridy = 1;
		panelLotes.add(spinnerCantMin, gbc_spinnerCantMin);
		spinnerCantMin.setModel(new SpinnerNumberModel(10, 1, 100, 1));

		JLabel lblCantMax = new JLabel("Cant. Max");
		GridBagConstraints gbc_lblCantMax = new GridBagConstraints();
		gbc_lblCantMax.gridwidth = 2;
		gbc_lblCantMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCantMax.insets = new Insets(0, 0, 5, 5);
		gbc_lblCantMax.gridx = 3;
		gbc_lblCantMax.gridy = 1;
		panelLotes.add(lblCantMax, gbc_lblCantMax);

		final JSpinner spinnerCantMax = new JSpinner();
		GridBagConstraints gbc_spinnerCantMax = new GridBagConstraints();
		gbc_spinnerCantMax.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerCantMax.gridx = 5;
		gbc_spinnerCantMax.gridy = 1;
		panelLotes.add(spinnerCantMax, gbc_spinnerCantMax);
		spinnerCantMax.setModel(new SpinnerNumberModel(30, 1, 100, 1));

		JLabel labelLotesAnyoFijas = new JLabel("Lotes/a\u00F1o");
		GridBagConstraints gbc_labelLotesAnyoFijas = new GridBagConstraints();
		gbc_labelLotesAnyoFijas.fill = GridBagConstraints.HORIZONTAL;
		gbc_labelLotesAnyoFijas.insets = new Insets(0, 0, 5, 5);
		gbc_labelLotesAnyoFijas.gridx = 6;
		gbc_labelLotesAnyoFijas.gridy = 1;
		panelLotes.add(labelLotesAnyoFijas, gbc_labelLotesAnyoFijas);


		JLabel lblNumLotesAnuales = new JLabel("Lotes/a\u00F1o");
		GridBagConstraints gbc_lblNumLotesAnuales = new GridBagConstraints();
		gbc_lblNumLotesAnuales.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNumLotesAnuales.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumLotesAnuales.gridx = 8;
		gbc_lblNumLotesAnuales.gridy = 1;
		panelLotes.add(lblNumLotesAnuales, gbc_lblNumLotesAnuales);



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
							actualizarEstado();

						}catch(IOException e){
							System.out.println("Error ejecutando en generacion lotes.");
						}
					}
				});			
				hilo.start();
			}

		});

		JPanel panelMovimientos = new JPanel();
		panelMovimientos.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Movimientos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelMovimientos = new GridBagConstraints();
		gbc_panelMovimientos.fill = GridBagConstraints.BOTH;
		gbc_panelMovimientos.insets = new Insets(0, 0, 5, 0);
		gbc_panelMovimientos.gridx = 2;
		gbc_panelMovimientos.gridy = 1;
		panel.add(panelMovimientos, gbc_panelMovimientos);
		GridBagLayout gbl_panelMovimientos = new GridBagLayout();
		gbl_panelMovimientos.columnWidths = new int[]{77, 71, 0};
		gbl_panelMovimientos.rowHeights = new int[]{93, 0};
		gbl_panelMovimientos.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelMovimientos.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelMovimientos.setLayout(gbl_panelMovimientos);

		JButton btnGenerarMovimientos = new JButton("Generar Movimientos");
		btnGenerarMovimientos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Thread hilo = new Thread(new Runnable() {

					@Override
					public void run() {
						Temporizador.iniciarTemporizador();
						textArea.append("Leyendo ubicaciones-producto...\n");
						textArea.append("Generando movimientos...\n");
						textArea.append("Número de movimientos generados: " + gen.generarMovimientos() + "\n");
						textArea.append("Escribiendo movimientos...\n");
						try {
							textArea.append("Número de movimientos escritos: " + gen.escribirMovimientos(null) + "\n");
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "Error escribiendo movimientos.", null, JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
						textArea.append("Tiempo ejecucion: " + Temporizador.pararTemporizador("Generacion de movimientos") + "\n");

						textArea.append("******************* GENERACION DE MOVIMIENTOS TERMINADA **********************\n");
						actualizarEstado();
					}
				});			
				hilo.start();

			}
		});
		GridBagConstraints gbc_btnGenerarMovimientos = new GridBagConstraints();
		gbc_btnGenerarMovimientos.insets = new Insets(0, 0, 0, 5);
		gbc_btnGenerarMovimientos.gridx = 0;
		gbc_btnGenerarMovimientos.gridy = 0;
		panelMovimientos.add(btnGenerarMovimientos, gbc_btnGenerarMovimientos);

		JButton btnConsultarMovimientos = new JButton("Consultar Movimientos");
		btnConsultarMovimientos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InformeMovimientos(gen);
			}
		});
		GridBagConstraints gbc_btnConsultarMovimientos = new GridBagConstraints();
		gbc_btnConsultarMovimientos.gridx = 1;
		gbc_btnConsultarMovimientos.gridy = 0;
		panelMovimientos.add(btnConsultarMovimientos, gbc_btnConsultarMovimientos);

		///////////////////////////////////////////////////////////////////// PANEL VENTAS


		JPanel panelVentas = new JPanel();
		panelVentas.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ventas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelVentas = new GridBagConstraints();
		gbc_panelVentas.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelVentas.gridwidth = 2;
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

		JButton btnConsultarVentas = new JButton("Consultar Ventas");
		btnConsultarVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InformeVentas(gen);
			}
		});
		GridBagConstraints gbc_btnConsultarVentas = new GridBagConstraints();
		gbc_btnConsultarVentas.insets = new Insets(0, 0, 5, 5);
		gbc_btnConsultarVentas.gridx = 1;
		gbc_btnConsultarVentas.gridy = 0;
		panelVentas.add(btnConsultarVentas, gbc_btnConsultarVentas);

		final JSpinner spinnerPorcent1Ud = new JSpinner();
		spinnerPorcent1Ud.setEnabled(false);
		spinnerPorcent1Ud.setModel(new SpinnerNumberModel(70, 0, 100, 1));
		GridBagConstraints gbc_spinnerPorcent1Ud = new GridBagConstraints();
		gbc_spinnerPorcent1Ud.anchor = GridBagConstraints.WEST;
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
		gbc_chckbxVentas1Ud.anchor = GridBagConstraints.SOUTHWEST;
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


		////////////////////////////////////////////////////////////////////////////// PANEL OTROS
		JPanel panelOtros = new JPanel();
		panelOtros.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Otros", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelOtros = new GridBagConstraints();
		gbc_panelOtros.fill = GridBagConstraints.BOTH;
		gbc_panelOtros.gridx = 2;
		gbc_panelOtros.gridy = 2;
		panel.add(panelOtros, gbc_panelOtros);
		GridBagLayout gbl_panelOtros = new GridBagLayout();
		gbl_panelOtros.columnWidths = new int[]{83, 0, 0};
		gbl_panelOtros.rowHeights = new int[]{23, 23, 0};
		gbl_panelOtros.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelOtros.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelOtros.setLayout(gbl_panelOtros);

		JButton btnFlushAll = new JButton("FLUSH ALL");
		GridBagConstraints gbc_btnFlushAll = new GridBagConstraints();
		gbc_btnFlushAll.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnFlushAll.insets = new Insets(0, 0, 5, 5);
		gbc_btnFlushAll.gridx = 0;
		gbc_btnFlushAll.gridy = 0;
		panelOtros.add(btnFlushAll, gbc_btnFlushAll);

		btnFlushAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gen.flushAll();
				textArea.append("===========================BORRADOS TODOS LOS DATOS EN MEMORIA=======================\n");
				actualizarEstado();
			}
		});

		JButton btnBorrarLog = new JButton("Borrar LOG");
		btnBorrarLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);
			}
		});
		GridBagConstraints gbc_btnBorrarLog = new GridBagConstraints();
		gbc_btnBorrarLog.insets = new Insets(0, 0, 5, 0);
		gbc_btnBorrarLog.gridx = 1;
		gbc_btnBorrarLog.gridy = 0;
		panelOtros.add(btnBorrarLog, gbc_btnBorrarLog);

		JButton btnPruebas = new JButton("Pruebas");
		GridBagConstraints gbc_btnPruebas = new GridBagConstraints();
		gbc_btnPruebas.insets = new Insets(0, 0, 0, 5);
		gbc_btnPruebas.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnPruebas.gridx = 0;
		gbc_btnPruebas.gridy = 1;
		panelOtros.add(btnPruebas, gbc_btnPruebas);
		btnPruebas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				
			}



		});

		JMenuBar menuBar = new JMenuBar();
		frmPobladorDeTablas.setJMenuBar(menuBar);

		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);

		JMenu mnConfiguracion = new JMenu("Configuracion");
		menuBar.add(mnConfiguracion);

		JMenuItem mntmEntradasalida = new JMenuItem("Entrada/Salida");
		mntmEntradasalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConfiguracionEntradasSalidas configES = new ConfiguracionEntradasSalidas();
			}
		});
		mnConfiguracion.add(mntmEntradasalida);

		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);


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
							actualizarEstado();
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

	/**Actualiza el textAreaEstado con las listas y el numero de elementos de cada una que estan en memoria*/
	public void actualizarEstado(){
		textAreaEstado.setText(null);
		if(gen.listaProductos.size()>0){
			textAreaEstado.append("[Productos - " + gen.listaProductos.size() + "]\n");
		}
		if(gen.listaLotesRecibidos.size()>0){
			textAreaEstado.append("[Lotes recibidos - " + gen.listaLotesRecibidos.size() + "]\n");
		}
		if(gen.listaVentas.size()>0){
			textAreaEstado.append("[Ventas - " + gen.listaVentas.size() + "]\n");
		}
		if(gen.listaUbicacionProducto.size()>0){
			textAreaEstado.append("[Ubicacion-producto - " + gen.listaUbicacionProducto.size() + "]\n");
		}
		if(gen.listaMovimientos.size()>0){
			textAreaEstado.append("[Movimientos - " + gen.listaMovimientos.size() + "]\n");
		}

	}

	public static JTextArea getTextArea() {
		return textArea;
	}

	public static void setTextArea(JTextArea textArea) {
		poblar_bd.textArea = textArea;
	}

	public static String getRutaEntrada() {
		return rutaEntrada;
	}

	public static void setRutaEntrada(String rutaEntrada) {
		poblar_bd.rutaEntrada = rutaEntrada;
	}

	public static String getRutaSalida() {
		return rutaSalida;
	}

	public static void setRutaSalida(String rutaSalida) {
		poblar_bd.rutaSalida = rutaSalida;
	}
}
