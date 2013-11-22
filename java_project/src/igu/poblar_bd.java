package igu;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Calendar;

import java_project.GeneradorCSV;
import java_project.Temporizador;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

public class poblar_bd {

	private static JFrame frmPobladorDeTablas;
	static public String rutaEntrada = "/home/carlos/pfc/pfc/entradas_app/";
	static public String rutaSalida = "/home/carlos/pfc/pfc/salidas_app/";
	static JTextArea textArea;
	Calendar fechaLimiteDefecto;
	static String comandoAmpliacionMemoria;
	JCheckBox chckbxMinimizarMemoria;
	JTextArea textAreaEstado;
	JSpinner spinnerMaxUbicacionesDistintas;
	static public JProgressBar progressBar;
	static GeneradorCSV gen = new GeneradorCSV();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Process pr;
		comandoAmpliacionMemoria ="java -Xmx2048m -XX:MaxPermSize=256m -jar \""+System.getProperty("user.dir")+"\\PBD.jar \" ";
		//		comandoAmpliacionMemoria ="java -Xms256m -Xmx2048m -XX:PermSize=1024m -XX:MaxPermSize=2048m -jar \""+System.getProperty("user.dir")+"\\PBD.jar \" ";
		System.out.println(comandoAmpliacionMemoria);
		try {
			pr = Runtime.getRuntime().exec(comandoAmpliacionMemoria);
		} catch (IOException e) {
			System.out.println("Error al ejecutar el comando de ampliacion de RAM de la máquina virtual JAVA.\n");
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					poblar_bd window = new poblar_bd();

					window.frmPobladorDeTablas.setVisible(true);				

				} catch (Exception e) {
					JOptionPane.showMessageDialog(frmPobladorDeTablas, "ERROR:" + e.getCause().getMessage(), null, JOptionPane.ERROR_MESSAGE);
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
		frmPobladorDeTablas.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				JOptionPane.showMessageDialog(frmPobladorDeTablas, "Si vas a generar una gran cantidad de datos, ejecuta la aplicacion con el siguiente comando:"+ comandoAmpliacionMemoria);
			}
		});
		frmPobladorDeTablas.setTitle("Poblador de Base de Datos");
		frmPobladorDeTablas.setBounds(100, 100, 1000, 600);
		frmPobladorDeTablas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icono = new ImageIcon("./src/images/pbd_background.png");
		frmPobladorDeTablas.setIconImage(icono.getImage());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{968, 0};
		gridBagLayout.rowHeights = new int[]{320, 200, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		frmPobladorDeTablas.getContentPane().setLayout(gridBagLayout);

			
		fechaLimiteDefecto = Calendar.getInstance();
		fechaLimiteDefecto.set(2013, Calendar.JUNE, 30);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
//		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
//			public void adjustmentValueChanged(AdjustmentEvent e) {  
//				e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
//			}
//		});

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		frmPobladorDeTablas.getContentPane().add(tabbedPane, gbc_tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Principal", null, panel, null);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {310, 145, 77, 20, 0, 20};
		gbl_panel.rowHeights = new int[] {94, 93, 82};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0};
		panel.setLayout(gbl_panel);

		////////////////////////////////////////////////////////////////////////// PANEL PRODUCTOS

		JPanel panelProductos = new JPanel();
		panelProductos.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Productos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelProductos = new GridBagConstraints();
		gbc_panelProductos.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelProductos.insets = new Insets(0, 0, 5, 5);
		gbc_panelProductos.gridx = 0;
		gbc_panelProductos.gridy = 0;
		panel.add(panelProductos, gbc_panelProductos);
		GridBagLayout gbl_panelProductos = new GridBagLayout();
		gbl_panelProductos.columnWidths = new int[] {38, 123, 50};
		gbl_panelProductos.rowHeights = new int[]{23, 0, 26, 0};
		gbl_panelProductos.columnWeights = new double[]{1.0, 1.0, 0.0};
		gbl_panelProductos.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		panelProductos.setLayout(gbl_panelProductos);
		
		JLabel lblStockMinimo = new JLabel("Stock Minimo");
		GridBagConstraints gbc_lblStockMinimo = new GridBagConstraints();
		gbc_lblStockMinimo.insets = new Insets(0, 0, 5, 5);
		gbc_lblStockMinimo.gridx = 1;
		gbc_lblStockMinimo.gridy = 0;
		panelProductos.add(lblStockMinimo, gbc_lblStockMinimo);
		
		final JSpinner spinnerStockMinimo = new JSpinner();
		spinnerStockMinimo.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		GridBagConstraints gbc_spinnerStockMinimo = new GridBagConstraints();
		gbc_spinnerStockMinimo.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerStockMinimo.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerStockMinimo.gridx = 2;
		gbc_spinnerStockMinimo.gridy = 0;
		panelProductos.add(spinnerStockMinimo, gbc_spinnerStockMinimo);

		JButton btnConsultarProductos = new JButton("Consultar Productos");
		btnConsultarProductos.setIcon(new ImageIcon("./src/images/look-for-icon.png"));
		GridBagConstraints gbc_btnConsultarProductos = new GridBagConstraints();
		gbc_btnConsultarProductos.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConsultarProductos.insets = new Insets(0, 0, 5, 5);
		gbc_btnConsultarProductos.gridx = 0;
		gbc_btnConsultarProductos.gridy = 1;
		panelProductos.add(btnConsultarProductos, gbc_btnConsultarProductos);
		btnConsultarProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new InformeProductos(gen);
			}
		});

		JButton btnLeerProductos = new JButton("Generar productos");
		btnLeerProductos.setIcon(new ImageIcon("./src/images/play-icon.png"));
		GridBagConstraints gbc_btnLeerProductos = new GridBagConstraints();
		gbc_btnLeerProductos.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLeerProductos.insets = new Insets(0, 0, 5, 5);
		gbc_btnLeerProductos.gridx = 0;
		gbc_btnLeerProductos.gridy = 0;
		panelProductos.add(btnLeerProductos, gbc_btnLeerProductos);
						
								JLabel lblStockMaximoInicial = new JLabel("Stock Maximo ");
								GridBagConstraints gbc_lblStockMaximoInicial = new GridBagConstraints();
								gbc_lblStockMaximoInicial.insets = new Insets(0, 0, 5, 5);
								gbc_lblStockMaximoInicial.gridx = 1;
								gbc_lblStockMaximoInicial.gridy = 1;
								panelProductos.add(lblStockMaximoInicial, gbc_lblStockMaximoInicial);
				
						final JSpinner spinnerStockMaximoProducto = new JSpinner();
						GridBagConstraints gbc_spinnerStockMaximoProducto = new GridBagConstraints();
						gbc_spinnerStockMaximoProducto.fill = GridBagConstraints.HORIZONTAL;
						gbc_spinnerStockMaximoProducto.insets = new Insets(0, 0, 5, 0);
						gbc_spinnerStockMaximoProducto.gridx = 2;
						gbc_spinnerStockMaximoProducto.gridy = 1;
						panelProductos.add(spinnerStockMaximoProducto, gbc_spinnerStockMaximoProducto);
						spinnerStockMaximoProducto.setModel(new SpinnerNumberModel(12, 1, 100, 1));
		
				JLabel lblPorcentajeProdPrimarios = new JLabel("% Prod. Primarios");
				GridBagConstraints gbc_lblPorcentajeProdPrimarios = new GridBagConstraints();
				gbc_lblPorcentajeProdPrimarios.insets = new Insets(0, 0, 0, 5);
				gbc_lblPorcentajeProdPrimarios.gridx = 1;
				gbc_lblPorcentajeProdPrimarios.gridy = 2;
				panelProductos.add(lblPorcentajeProdPrimarios, gbc_lblPorcentajeProdPrimarios);
				
						final JSpinner spinnerPorcentajePrimarios = new JSpinner();
						spinnerPorcentajePrimarios.setModel(new SpinnerNumberModel(20, 0, 100, 1));
						GridBagConstraints gbc_spinnerPorcentajePrimarios = new GridBagConstraints();
						gbc_spinnerPorcentajePrimarios.fill = GridBagConstraints.HORIZONTAL;
						gbc_spinnerPorcentajePrimarios.gridx = 2;
						gbc_spinnerPorcentajePrimarios.gridy = 2;
						panelProductos.add(spinnerPorcentajePrimarios, gbc_spinnerPorcentajePrimarios);
		btnLeerProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					GeneradorCSV.stockMinimo = (int)  spinnerStockMinimo.getValue();
					GeneradorCSV.stockMaximoInicial = (int) spinnerStockMaximoProducto.getValue();
					Temporizador.iniciarTemporizador();
					textArea.append("Leyendo productos...\n");
					int numProdLeidos = gen.leerProductos(null, (int) spinnerPorcentajePrimarios.getValue());
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

		JPanel panelUbicacionProducto = new JPanel();
		panelUbicacionProducto.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ubicacion-producto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelUbicacionProducto = new GridBagConstraints();
		gbc_panelUbicacionProducto.gridwidth = 2;
		gbc_panelUbicacionProducto.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelUbicacionProducto.insets = new Insets(0, 0, 5, 5);
		gbc_panelUbicacionProducto.gridx = 1;
		gbc_panelUbicacionProducto.gridy = 0;
		panel.add(panelUbicacionProducto, gbc_panelUbicacionProducto);
		GridBagLayout gbl_panelUbicacionProducto = new GridBagLayout();
		gbl_panelUbicacionProducto.columnWidths = new int[]{120, 0, 50, 0};
		gbl_panelUbicacionProducto.rowHeights = new int[]{25, 25, 0};
		gbl_panelUbicacionProducto.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelUbicacionProducto.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelUbicacionProducto.setLayout(gbl_panelUbicacionProducto);

		JButton btnGenerarUbicacionproducto = new JButton("Generar ubicacion-producto");
		btnGenerarUbicacionproducto.setIcon(new ImageIcon("./src/images/play-icon.png"));
		btnGenerarUbicacionproducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				Temporizador.iniciarTemporizador();
				GeneradorCSV.setMaxUbicacionesDistintas((int) spinnerMaxUbicacionesDistintas.getValue());
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
		gbc_btnGenerarUbicacionproducto.anchor = GridBagConstraints.NORTH;
		gbc_btnGenerarUbicacionproducto.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGenerarUbicacionproducto.insets = new Insets(0, 0, 5, 5);
		gbc_btnGenerarUbicacionproducto.gridx = 0;
		gbc_btnGenerarUbicacionproducto.gridy = 0;
		panelUbicacionProducto.add(btnGenerarUbicacionproducto, gbc_btnGenerarUbicacionproducto);

		JButton btnConsultarUbicacionproducto = new JButton("Consultar Ubicacion-producto");
		btnConsultarUbicacionproducto.setIcon(new ImageIcon("./src/images/look-for-icon.png"));
		btnConsultarUbicacionproducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InformeUbicacionProducto(gen);
			}
		});
		
		JLabel lblMaxUbicDistintas = new JLabel("Max. Ubic. Distintas");
		GridBagConstraints gbc_lblMaxUbicDistintas = new GridBagConstraints();
		gbc_lblMaxUbicDistintas.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxUbicDistintas.gridx = 1;
		gbc_lblMaxUbicDistintas.gridy = 0;
		panelUbicacionProducto.add(lblMaxUbicDistintas, gbc_lblMaxUbicDistintas);
		
		spinnerMaxUbicacionesDistintas = new JSpinner();
		spinnerMaxUbicacionesDistintas.setModel(new SpinnerNumberModel(3, 1, 5, 1));
		GridBagConstraints gbc_spinnerMaxUbicacionesDistintas = new GridBagConstraints();
		gbc_spinnerMaxUbicacionesDistintas.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerMaxUbicacionesDistintas.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerMaxUbicacionesDistintas.gridx = 2;
		gbc_spinnerMaxUbicacionesDistintas.gridy = 0;
		panelUbicacionProducto.add(spinnerMaxUbicacionesDistintas, gbc_spinnerMaxUbicacionesDistintas);
		GridBagConstraints gbc_btnConsultarUbicacionproducto = new GridBagConstraints();
		gbc_btnConsultarUbicacionproducto.insets = new Insets(0, 0, 0, 5);
		gbc_btnConsultarUbicacionproducto.anchor = GridBagConstraints.NORTH;
		gbc_btnConsultarUbicacionproducto.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConsultarUbicacionproducto.gridx = 0;
		gbc_btnConsultarUbicacionproducto.gridy = 1;
		panelUbicacionProducto.add(btnConsultarUbicacionproducto, gbc_btnConsultarUbicacionproducto);



		///////////////////////////////////////////////////////////////////////////PANEL ESTADO

		JPanel panelEstado = new JPanel();
		panelEstado.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Estado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelEstado = new GridBagConstraints();
		gbc_panelEstado.gridwidth = 3;
		gbc_panelEstado.fill = GridBagConstraints.BOTH;
		gbc_panelEstado.insets = new Insets(0, 0, 5, 0);
		gbc_panelEstado.gridx = 3;
		gbc_panelEstado.gridy = 0;
		panel.add(panelEstado, gbc_panelEstado);
		panelEstado.setLayout(null);
		GridBagLayout gbl_panelEstado = new GridBagLayout();
		gbl_panelEstado.columnWidths = new int[]{174, 0};
		gbl_panelEstado.rowHeights = new int[]{40, 90, 0};
		gbl_panelEstado.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelEstado.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
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
		gbc_textAreaEstado.fill = GridBagConstraints.BOTH;
		gbc_textAreaEstado.gridx = 0;
		gbc_textAreaEstado.gridy = 1;
		panelEstado.add(textAreaEstado, gbc_textAreaEstado);

		////////////////////////////////////////////////////////////////////////////// PANEL LOTES

		JPanel panelLotes = new JPanel();
		panelLotes.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Lotes recibidos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelLotes = new GridBagConstraints();
		gbc_panelLotes.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelLotes.gridwidth = 5;
		gbc_panelLotes.insets = new Insets(0, 0, 5, 5);
		gbc_panelLotes.gridx = 0;
		gbc_panelLotes.gridy = 1;
		panel.add(panelLotes, gbc_panelLotes);
		GridBagLayout gbl_panelLotes = new GridBagLayout();
		gbl_panelLotes.columnWidths = new int[] {140, 50, 90, 0, 0, 0, 0};
		gbl_panelLotes.rowHeights = new int[]{35, 0, 21, 0, 0};
		gbl_panelLotes.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0};
		gbl_panelLotes.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		panelLotes.setLayout(gbl_panelLotes);

		JButton btnGenerarLotesRecibidos = new JButton("Generar Lotes");
		btnGenerarLotesRecibidos.setIcon(new ImageIcon("./src/images/play-icon.png"));
		GridBagConstraints gbc_btnGenerarLotesRecibidos = new GridBagConstraints();
		gbc_btnGenerarLotesRecibidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGenerarLotesRecibidos.insets = new Insets(0, 0, 5, 5);
		gbc_btnGenerarLotesRecibidos.gridx = 0;
		gbc_btnGenerarLotesRecibidos.gridy = 0;
		panelLotes.add(btnGenerarLotesRecibidos, gbc_btnGenerarLotesRecibidos);

		JButton btnConsultarLotes = new JButton("Consultar Lotes");
		btnConsultarLotes.setIcon(new ImageIcon("./src/images/look-for-icon.png"));
		btnConsultarLotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new InformeLotes(gen);
			}
		});

		JLabel lblCantMin = new JLabel("Cant. Min");
		GridBagConstraints gbc_lblCantMin = new GridBagConstraints();
		gbc_lblCantMin.anchor = GridBagConstraints.EAST;
		gbc_lblCantMin.insets = new Insets(1, 5, 5, 5);
		gbc_lblCantMin.gridx = 1;
		gbc_lblCantMin.gridy = 0;
		panelLotes.add(lblCantMin, gbc_lblCantMin);

		final JSpinner spinnerCantMin = new JSpinner();
		GridBagConstraints gbc_spinnerCantMin = new GridBagConstraints();
		gbc_spinnerCantMin.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerCantMin.gridx = 2;
		gbc_spinnerCantMin.gridy = 0;
		panelLotes.add(spinnerCantMin, gbc_spinnerCantMin);
		spinnerCantMin.setModel(new SpinnerNumberModel(10, 1, 100, 1));

		JLabel labelLotesPrimariosMesFijas = new JLabel("Lotes Primarios/Mes");
		GridBagConstraints gbc_labelLotesPrimariosMesFijas = new GridBagConstraints();
		gbc_labelLotesPrimariosMesFijas.insets = new Insets(0, 0, 5, 5);
		gbc_labelLotesPrimariosMesFijas.gridx = 3;
		gbc_labelLotesPrimariosMesFijas.gridy = 1;
		panelLotes.add(labelLotesPrimariosMesFijas, gbc_labelLotesPrimariosMesFijas);

		final JSpinner spinnerNumLotesPrimariosFijas = new JSpinner();
		GridBagConstraints gbc_spinnerNumLotesPrimariosFijas = new GridBagConstraints();
		gbc_spinnerNumLotesPrimariosFijas.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerNumLotesPrimariosFijas.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerNumLotesPrimariosFijas.gridx = 4;
		gbc_spinnerNumLotesPrimariosFijas.gridy = 1;
		panelLotes.add(spinnerNumLotesPrimariosFijas, gbc_spinnerNumLotesPrimariosFijas);
		spinnerNumLotesPrimariosFijas.setModel(new SpinnerNumberModel(1, 1, 2, 1));

		final JSpinner spinnerLotes = new JSpinner(new SpinnerNumberModel(30, 1, 90, 1));
		GridBagConstraints gbc_spinnerLotes = new GridBagConstraints();
		gbc_spinnerLotes.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerLotes.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerLotes.gridx = 6;
		gbc_spinnerLotes.gridy = 1;
		panelLotes.add(spinnerLotes, gbc_spinnerLotes);

		final JRadioButton rdbtnFechasFijas = new JRadioButton("Fechas fijas");
		GridBagConstraints gbc_rdbtnFechasFijas = new GridBagConstraints();
		gbc_rdbtnFechasFijas.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnFechasFijas.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnFechasFijas.gridx = 3;
		gbc_rdbtnFechasFijas.gridy = 0;
		panelLotes.add(rdbtnFechasFijas, gbc_rdbtnFechasFijas);
		rdbtnFechasFijas.setSelected(true);		//por defecto, fechas fijas

		rdbtnFechasFijas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spinnerLotes.setEnabled(false);		//desactivo el checkbox de lotes anuales
				spinnerNumLotesPrimariosFijas.setEnabled(true); //activo el spinner de lotes anuales de fechas fijas
			}
		});
		ButtonGroup grupoRadioFechas = new ButtonGroup();
		if(rdbtnFechasFijas.isSelected()){
			spinnerLotes.setEnabled(false);		
			spinnerNumLotesPrimariosFijas.setEnabled(true);
		}	
		grupoRadioFechas.add(rdbtnFechasFijas);

		JRadioButton rdbtnFechasVariables = new JRadioButton("Fechas variables");
		GridBagConstraints gbc_rdbtnFechasVariables = new GridBagConstraints();
		gbc_rdbtnFechasVariables.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnFechasVariables.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnFechasVariables.gridx = 5;
		gbc_rdbtnFechasVariables.gridy = 0;
		panelLotes.add(rdbtnFechasVariables, gbc_rdbtnFechasVariables);
		rdbtnFechasVariables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spinnerLotes.setEnabled(true);	//activo el spinner de lotes anuales variable
				spinnerNumLotesPrimariosFijas.setEnabled(false); //desactivo el spinner de lotes anuales de fechas fijas 
			}
		});
		grupoRadioFechas.add(rdbtnFechasVariables);
		GridBagConstraints gbc_btnConsultarLotes = new GridBagConstraints();
		gbc_btnConsultarLotes.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConsultarLotes.insets = new Insets(0, 0, 5, 5);
		gbc_btnConsultarLotes.gridx = 0;
		gbc_btnConsultarLotes.gridy = 1;
		panelLotes.add(btnConsultarLotes, gbc_btnConsultarLotes);

		JLabel lblCantMax = new JLabel("Cant. Max");
		GridBagConstraints gbc_lblCantMax = new GridBagConstraints();
		gbc_lblCantMax.anchor = GridBagConstraints.EAST;
		gbc_lblCantMax.insets = new Insets(0, 0, 5, 5);
		gbc_lblCantMax.gridx = 1;
		gbc_lblCantMax.gridy = 1;
		panelLotes.add(lblCantMax, gbc_lblCantMax);

		final JSpinner spinnerCantMax = new JSpinner();
		GridBagConstraints gbc_spinnerCantMax = new GridBagConstraints();
		gbc_spinnerCantMax.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerCantMax.gridx = 2;
		gbc_spinnerCantMax.gridy = 1;
		panelLotes.add(spinnerCantMax, gbc_spinnerCantMax);
		spinnerCantMax.setModel(new SpinnerNumberModel(30, 10, 100, 1));

		JLabel lblNumLotesAnuales = new JLabel("Lotes/a\u00F1o");
		GridBagConstraints gbc_lblNumLotesAnuales = new GridBagConstraints();
		gbc_lblNumLotesAnuales.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumLotesAnuales.gridx = 5;
		gbc_lblNumLotesAnuales.gridy = 1;
		panelLotes.add(lblNumLotesAnuales, gbc_lblNumLotesAnuales);

		JLabel lblFechaLimite = new JLabel("Fecha Limite");
		GridBagConstraints gbc_lblFechaLimite = new GridBagConstraints();
		gbc_lblFechaLimite.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaLimite.gridx = 1;
		gbc_lblFechaLimite.gridy = 2;
		panelLotes.add(lblFechaLimite, gbc_lblFechaLimite);


		final JDateChooser fechaLimiteChooser = new JDateChooser();
		GridBagConstraints gbc_fechaLimiteChooser = new GridBagConstraints();
		gbc_fechaLimiteChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaLimiteChooser.insets = new Insets(0, 0, 5, 5);
		gbc_fechaLimiteChooser.gridx = 2;
		gbc_fechaLimiteChooser.gridy = 2;
		panelLotes.add(fechaLimiteChooser, gbc_fechaLimiteChooser);
		fechaLimiteChooser.setCalendar(fechaLimiteDefecto); //Fecha limite por defecto. NINGUNA FECHA NI DE RECEPCION NI DE VENTA SUPERARÁ ESTA FECHA LÍMITE)

		JLabel lblLotesSecundariosMes = new JLabel("Lotes Secundarios/A\u00F1o");
		GridBagConstraints gbc_lblLotesSecundariosMes = new GridBagConstraints();
		gbc_lblLotesSecundariosMes.insets = new Insets(0, 0, 5, 5);
		gbc_lblLotesSecundariosMes.gridx = 3;
		gbc_lblLotesSecundariosMes.gridy = 2;
		panelLotes.add(lblLotesSecundariosMes, gbc_lblLotesSecundariosMes);

		final JSpinner spinnerNumLotesSecundariosAnyo = new JSpinner();
		spinnerNumLotesSecundariosAnyo.setModel(new SpinnerNumberModel(4, 2, 6, 2));
		GridBagConstraints gbc_spinnerNumLotesSecundariosAnyo = new GridBagConstraints();
		gbc_spinnerNumLotesSecundariosAnyo.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerNumLotesSecundariosAnyo.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerNumLotesSecundariosAnyo.gridx = 4;
		gbc_spinnerNumLotesSecundariosAnyo.gridy = 2;
		panelLotes.add(spinnerNumLotesSecundariosAnyo, gbc_spinnerNumLotesSecundariosAnyo);

		JLabel lblPorcentMovimientosPrimarios = new JLabel("% Mov. Primarios");
		GridBagConstraints gbc_lblPorcentMovimientosPrimarios = new GridBagConstraints();
		gbc_lblPorcentMovimientosPrimarios.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorcentMovimientosPrimarios.gridx = 5;
		gbc_lblPorcentMovimientosPrimarios.gridy = 2;
		panelLotes.add(lblPorcentMovimientosPrimarios, gbc_lblPorcentMovimientosPrimarios);

		final JSpinner spinnerPorcentMovimientosPrimarios = new JSpinner();
		GridBagConstraints gbc_spinnerPorcentMovimientosPrimarios = new GridBagConstraints();
		gbc_spinnerPorcentMovimientosPrimarios.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerPorcentMovimientosPrimarios.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerPorcentMovimientosPrimarios.gridx = 6;
		gbc_spinnerPorcentMovimientosPrimarios.gridy = 2;
		panelLotes.add(spinnerPorcentMovimientosPrimarios, gbc_spinnerPorcentMovimientosPrimarios);
		spinnerPorcentMovimientosPrimarios.setModel(new SpinnerNumberModel(80, 0, 100, 1));

		JLabel lblCrecimientoAnual = new JLabel("% Crecimiento anual");
		GridBagConstraints gbc_lblCrecimientoAnual = new GridBagConstraints();
		gbc_lblCrecimientoAnual.insets = new Insets(0, 0, 0, 5);
		gbc_lblCrecimientoAnual.gridx = 5;
		gbc_lblCrecimientoAnual.gridy = 3;
		panelLotes.add(lblCrecimientoAnual, gbc_lblCrecimientoAnual);

		final JSpinner spinnerCrecimiento = new JSpinner();
		spinnerCrecimiento.setModel(new SpinnerNumberModel(3, -20, 50, 1));
		GridBagConstraints gbc_spinnerCrecimiento = new GridBagConstraints();
		gbc_spinnerCrecimiento.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerCrecimiento.gridx = 6;
		gbc_spinnerCrecimiento.gridy = 3;
		panelLotes.add(spinnerCrecimiento, gbc_spinnerCrecimiento);
		fechaLimiteChooser.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				GeneradorCSV.setFechaLimite(fechaLimiteChooser.getCalendar());
				System.out.println("Fecha: " + GeneradorCSV.getFechaLimite().get(Calendar.DAY_OF_MONTH) + "-"
						+ GeneradorCSV.getFechaLimite().get(Calendar.MONTH) + "-"
						+ GeneradorCSV.getFechaLimite().get(Calendar.YEAR));
			}
		});



		btnGenerarLotesRecibidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


				final int numLotesFechaVariable = (int) spinnerLotes.getValue();
				final int numLotesPrimariosMesFechaFija = (int) spinnerNumLotesPrimariosFijas.getValue();
				final int numLotesSecundariosAnyoFechaFija = (int) spinnerNumLotesSecundariosAnyo.getValue();

				Thread hilo = new Thread(new Runnable() {

					@Override
					public void run() {
						try{

							Temporizador.iniciarTemporizador();							
							if(rdbtnFechasFijas.isSelected()){
								textArea.append("[Fechas fijas] Generando lotes recibidos...\n" );
								textArea.append("[Fechas fijas] Lotes primarios por producto/mes: " + numLotesPrimariosMesFechaFija + "[" + (numLotesPrimariosMesFechaFija*12) + " lotes/año]"+ "\n");
								textArea.append("[Fechas fijas] Lotes secundarios por producto/año: " + numLotesSecundariosAnyoFechaFija + "\n");
								textArea.append("[Fechas fijas] Lotes totales por producto/año: " + ((numLotesPrimariosMesFechaFija*12)+numLotesSecundariosAnyoFechaFija) + "\n");
								int numLotesGenerados = 0;
								numLotesGenerados = gen.GenerarLotesRecibidosFechaFijaCantidadVariable(numLotesPrimariosMesFechaFija, numLotesSecundariosAnyoFechaFija,(int)spinnerCantMin.getValue(), (int)spinnerCantMax.getValue(), (int)spinnerPorcentMovimientosPrimarios.getValue());
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
								numLotesGenerados = gen.GenerarLotesRecibidosFechaVariableCantidadVariable(numLotesFechaVariable, (int)spinnerCantMin.getValue(), (int)spinnerCantMax.getValue(), (int)spinnerPorcentMovimientosPrimarios.getValue(), (int)spinnerCrecimiento.getValue());
								textArea.append("[Fechas variables] Numero de lotes generados: " + numLotesGenerados + "\n");
								progressBar.setValue(2);
								int numLotesEscritos = 0;
								numLotesEscritos = gen.escribirLotesRecibidos(null);
								textArea.append("[Fechas variables] Escribiendo lotes recibidos...\n");
								textArea.append("[Fechas variables] Numero de lotes escritos: " + numLotesEscritos + "\n");
								progressBar.setValue(3);
							}
							//							gen.flushAll();
							textArea.append("Tiempo ejecucion: " + Temporizador.pararTemporizador("Generacion de lotes") + " segundos.\n");
							textArea.append("*********************** GENERACION DE LOTES COMPLETADA *******************\n" );
							actualizarEstado();

						}catch(IOException e){
							System.out.println("Error ejecutando en generacion lotes.");
							JOptionPane.showMessageDialog(frmPobladorDeTablas, "Error ejecutando generacion de lotes [Compruebe los ficheros de entrada y que se han generado previamente los productos]", null, JOptionPane.ERROR_MESSAGE);
						}
					}
				});			
				hilo.start();
			}

		});

		JPanel panelMovimientos = new JPanel();
		panelMovimientos.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Movimientos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelMovimientos = new GridBagConstraints();
		gbc_panelMovimientos.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelMovimientos.insets = new Insets(0, 0, 5, 0);
		gbc_panelMovimientos.gridx = 5;
		gbc_panelMovimientos.gridy = 1;
		panel.add(panelMovimientos, gbc_panelMovimientos);
		GridBagLayout gbl_panelMovimientos = new GridBagLayout();
		gbl_panelMovimientos.columnWidths = new int[]{77, 0};
		gbl_panelMovimientos.rowHeights = new int[]{0, 0, 0};
		gbl_panelMovimientos.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelMovimientos.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelMovimientos.setLayout(gbl_panelMovimientos);

		JButton btnGenerarMovimientos = new JButton("Generar Movimientos");
		btnGenerarMovimientos.setIcon(new ImageIcon("./src/images/play-icon.png"));
		btnGenerarMovimientos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Thread hilo = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Temporizador.iniciarTemporizador();
							textArea.append("Leyendo ubicaciones-producto...\n");
							textArea.append("Generando movimientos...\n");
							if(chckbxMinimizarMemoria.isSelected())
								textArea.append("Número de movimientos generados: " + gen.generarMovimientosConMinimizacionMemoria() + "\n");							
							else {
								textArea.append("Número de movimientos generados: " + gen.generarMovimientos() + "\n");
								textArea.append("Escribiendo movimientos...\n");
								textArea.append("Número de movimientos escritos: " + gen.escribirMovimientos(null) + "\n");
							}
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "Error generando movimientos. [Compruebe que se han generado correctamente productos, lotes, ubicaciones-producto y ventas.", null, JOptionPane.ERROR_MESSAGE);
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
		gbc_btnGenerarMovimientos.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGenerarMovimientos.insets = new Insets(0, 0, 5, 0);
		gbc_btnGenerarMovimientos.gridx = 0;
		gbc_btnGenerarMovimientos.gridy = 0;
		panelMovimientos.add(btnGenerarMovimientos, gbc_btnGenerarMovimientos);

		JButton btnConsultarMovimientos = new JButton("Consultar Movimientos");
		btnConsultarMovimientos.setIcon(new ImageIcon("./src/images/look-for-icon.png"));
		btnConsultarMovimientos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InformeMovimientos(gen);
			}
		});
		GridBagConstraints gbc_btnConsultarMovimientos = new GridBagConstraints();
		gbc_btnConsultarMovimientos.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConsultarMovimientos.gridx = 0;
		gbc_btnConsultarMovimientos.gridy = 1;
		panelMovimientos.add(btnConsultarMovimientos, gbc_btnConsultarMovimientos);

		///////////////////////////////////////////////////////////////////// PANEL VENTAS


		JPanel panelVentas = new JPanel();
		panelVentas.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ventas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelVentas = new GridBagConstraints();
		gbc_panelVentas.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelVentas.gridwidth = 5;
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
		btnConsultarVentas.setIcon(new ImageIcon("./src/images/look-for-icon.png"));
				btnConsultarVentas.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new InformeVentas(gen);
					}
				});

				JLabel lblMaxDasVendindose = new JLabel("Max dias en venta");
				GridBagConstraints gbc_lblMaxDasVendindose = new GridBagConstraints();
				gbc_lblMaxDasVendindose.insets = new Insets(0, 0, 5, 5);
				gbc_lblMaxDasVendindose.gridx = 1;
				gbc_lblMaxDasVendindose.gridy = 0;
				panelVentas.add(lblMaxDasVendindose, gbc_lblMaxDasVendindose);

				final JSpinner spinnerMaxDiasVendiendose = new JSpinner();
				GridBagConstraints gbc_spinnerMaxDiasVendiendose = new GridBagConstraints();
				gbc_spinnerMaxDiasVendiendose.anchor = GridBagConstraints.WEST;
				gbc_spinnerMaxDiasVendiendose.insets = new Insets(0, 0, 5, 5);
				gbc_spinnerMaxDiasVendiendose.gridx = 2;
				gbc_spinnerMaxDiasVendiendose.gridy = 0;
				panelVentas.add(spinnerMaxDiasVendiendose, gbc_spinnerMaxDiasVendiendose);
				spinnerMaxDiasVendiendose.setModel(new SpinnerNumberModel(45, 30, 180, 1));

				chckbxMinimizarMemoria = new JCheckBox("Minimizar memoria");
				GridBagConstraints gbc_chckbxMinimizarMemoria = new GridBagConstraints();
				gbc_chckbxMinimizarMemoria.insets = new Insets(0, 0, 5, 0);
				gbc_chckbxMinimizarMemoria.gridx = 4;
				gbc_chckbxMinimizarMemoria.gridy = 0;
				panelVentas.add(chckbxMinimizarMemoria, gbc_chckbxMinimizarMemoria);

				final JSpinner spinnerPorcent1Ud = new JSpinner();
				spinnerPorcent1Ud.setEnabled(false);
				spinnerPorcent1Ud.setModel(new SpinnerNumberModel(70, 0, 100, 1));
				GridBagConstraints gbc_spinnerPorcent1Ud = new GridBagConstraints();
				gbc_spinnerPorcent1Ud.insets = new Insets(0, 0, 0, 5);
				gbc_spinnerPorcent1Ud.anchor = GridBagConstraints.WEST;
				gbc_spinnerPorcent1Ud.gridx = 3;
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
				gbc_chckbxVentas1Ud.insets = new Insets(0, 0, 5, 5);
				gbc_chckbxVentas1Ud.anchor = GridBagConstraints.WEST;
				gbc_chckbxVentas1Ud.gridx = 3;
				gbc_chckbxVentas1Ud.gridy = 0;
				panelVentas.add(chckbxVentas1Ud, gbc_chckbxVentas1Ud);
				GridBagConstraints gbc_btnConsultarVentas = new GridBagConstraints();
				gbc_btnConsultarVentas.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnConsultarVentas.insets = new Insets(0, 0, 0, 5);
				gbc_btnConsultarVentas.gridx = 0;
				gbc_btnConsultarVentas.gridy = 1;
				panelVentas.add(btnConsultarVentas, gbc_btnConsultarVentas);

				JLabel lblMaxCantVenta = new JLabel("Max Cant/Venta");
				GridBagConstraints gbc_lblMaxCantVenta = new GridBagConstraints();
				gbc_lblMaxCantVenta.insets = new Insets(0, 0, 0, 5);
				gbc_lblMaxCantVenta.gridx = 1;
				gbc_lblMaxCantVenta.gridy = 1;
				panelVentas.add(lblMaxCantVenta, gbc_lblMaxCantVenta);

				final JSpinner spinnerMaxCantVenta = new JSpinner();
				GridBagConstraints gbc_spinnerMaxCantVenta = new GridBagConstraints();
				gbc_spinnerMaxCantVenta.anchor = GridBagConstraints.WEST;
				gbc_spinnerMaxCantVenta.insets = new Insets(0, 0, 0, 5);
				gbc_spinnerMaxCantVenta.gridx = 2;
				gbc_spinnerMaxCantVenta.gridy = 1;
				panelVentas.add(spinnerMaxCantVenta, gbc_spinnerMaxCantVenta);
				spinnerMaxCantVenta.setModel(new SpinnerNumberModel(6, 1, 50, 1));

				JButton btnGenerarVentas = new JButton("Generar Ventas");
				btnGenerarVentas.setIcon(new ImageIcon("./src/images/play-icon.png"));
				GridBagConstraints gbc_btnGenerarVentas = new GridBagConstraints();
				gbc_btnGenerarVentas.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnGenerarVentas.insets = new Insets(0, 0, 5, 5);
				gbc_btnGenerarVentas.gridx = 0;
				gbc_btnGenerarVentas.gridy = 0;
				panelVentas.add(btnGenerarVentas, gbc_btnGenerarVentas);

				////////////////////////////////////////////////////////////////////////////// PANEL OTROS
				JPanel panelOtros = new JPanel();
				panelOtros.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Otros", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				GridBagConstraints gbc_panelOtros = new GridBagConstraints();
				gbc_panelOtros.fill = GridBagConstraints.HORIZONTAL;
				gbc_panelOtros.gridx = 5;
				gbc_panelOtros.gridy = 2;
				panel.add(panelOtros, gbc_panelOtros);
				GridBagLayout gbl_panelOtros = new GridBagLayout();
				gbl_panelOtros.columnWidths = new int[]{83, 0, 0};
				gbl_panelOtros.rowHeights = new int[]{23, 23, 0};
				gbl_panelOtros.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
				gbl_panelOtros.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
				panelOtros.setLayout(gbl_panelOtros);

				JButton btnFlushAll = new JButton("FLUSH ALL");
				btnFlushAll.setIcon(new ImageIcon("./src/images/flush-all-icon.png"));
				GridBagConstraints gbc_btnFlushAll = new GridBagConstraints();
				gbc_btnFlushAll.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnFlushAll.anchor = GridBagConstraints.NORTH;
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
				btnBorrarLog.setIcon(new ImageIcon("./src/images/delete-log-icon.png"));
				btnBorrarLog.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textArea.setText(null);
					}
				});
				GridBagConstraints gbc_btnBorrarLog = new GridBagConstraints();
				gbc_btnBorrarLog.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnBorrarLog.insets = new Insets(0, 0, 5, 0);
				gbc_btnBorrarLog.gridx = 1;
				gbc_btnBorrarLog.gridy = 0;
				panelOtros.add(btnBorrarLog, gbc_btnBorrarLog);

				JButton btnEjecutarTodo = new JButton("Ejecutar todo");
				btnEjecutarTodo.setIcon((Icon) poblar_bd.class.getResourceAsStream("run-all-icon.png"));
				GridBagConstraints gbc_btnEjecutarTodo = new GridBagConstraints();
				gbc_btnEjecutarTodo.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnEjecutarTodo.insets = new Insets(0, 0, 0, 5);
				gbc_btnEjecutarTodo.anchor = GridBagConstraints.NORTH;
				gbc_btnEjecutarTodo.gridx = 0;
				gbc_btnEjecutarTodo.gridy = 1;
				panelOtros.add(btnEjecutarTodo, gbc_btnEjecutarTodo);
				
				JPanel panel_2 = new JPanel();
				tabbedPane.addTab("Informe", null, panel_2, null);
				btnEjecutarTodo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						try {

							GeneradorCSV.stockMaximoInicial = (int) spinnerStockMaximoProducto.getValue();
							Temporizador.iniciarTemporizador();
							textArea.append("Leyendo productos...\n");
							int numProdLeidos = gen.leerProductos(null, (int) spinnerPorcentajePrimarios.getValue());
							textArea.append("Leidos " + numProdLeidos + " productos.\n");
							textArea.append("Generando precios de venta...\n");
							int numPreciosVentaGenerados = gen.generarPreciosVentaProducto();					
							textArea.append("Numero de precios generados: " + numPreciosVentaGenerados + " (1 por producto).\n");

							textArea.append("Escribiendo productos...\n");
							int numProdEscritos = gen.escribirProductos(null);
							textArea.append("Escritos " + numProdEscritos + " productos.\n");
							textArea.append("Tiempo ejecucion: " + Temporizador.pararTemporizador("Generacion de productos") + " segundos.\n");
							textArea.append("********************** GENERACION DE PRODUCTOS TERMINADA *******************\n");
							actualizarEstado();

							//LOTES


							//VENTAS



							//UBICACION-PRODUCTO



							//MOVIMIENTOS



						}
						catch (IOException e1) {
							System.out.println("Error al leer/escribir productos");
							e1.printStackTrace();
						}

					}

				});

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

									numVentasGeneradas = gen.generarVentas(maxCantVenta,maxDiasVendiendose, ventas1Ud, porcentVentas1Ud,chckbxMinimizarMemoria.isSelected());
									textArea.append("Numero de ventas generadas: "
											+ numVentasGeneradas + "\n");
									int numVentasEscritas = 0;
									if(chckbxMinimizarMemoria.isEnabled()==false){
										numVentasEscritas = gen.escribirVentas(null);
										textArea.append("Escribiendo ventas...\n");
										textArea.append("Numero de ventas escritas: "
												+ numVentasEscritas + "\n");
									}
									textArea.append("Tiempo ejecucion: " + Temporizador.pararTemporizador("Generacion de ventas") + " segundos.\n");
									int numProdEscritos = gen.escribirProductos(null);
									textArea.append("Se ha modificado y escrito en fichero LA CANTIDAD DE STOCK (tabla producto).\n");
									textArea.append("Numero de productos escritos: "
											+ numProdEscritos + "\n");
									textArea.append("*********************** GENERACION DE VENTAS COMPLETADA *******************\n" );
									actualizarEstado();
									//							gen.flushAll();
								}catch(IOException e){
									JOptionPane.showMessageDialog(frmPobladorDeTablas, "Error generando ventas [Compruebe que se han generado productos y lotes previamente", null, JOptionPane.ERROR_MESSAGE);
									System.out.println("Error ejecutando generar ventas.");
								}
							}
						});			
						hilo.start();

					}
				});
				GridBagConstraints gbc_scrollPane = new GridBagConstraints();
				gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
				gbc_scrollPane.fill = GridBagConstraints.BOTH;
				gbc_scrollPane.gridx = 0;
				gbc_scrollPane.gridy = 1;
				frmPobladorDeTablas.getContentPane().add(scrollPane, gbc_scrollPane);

				textArea = new JTextArea();
				scrollPane.setViewportView(textArea);
				textArea.setAutoscrolls(true);

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

				JMenuItem mntmTendencias = new JMenuItem("Tendencias");
				mntmTendencias.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						new MenuTendencias();
					}
				});
				mnConfiguracion.add(mntmTendencias);

				JMenu mnAyuda = new JMenu("Ayuda");
				menuBar.add(mnAyuda);

	}

	/**Actualiza el textAreaEstado con las listas y el numero de elementos de cada una que estan en memoria*/
	public void actualizarEstado(){
		textAreaEstado.setText(null);
		if(gen.getListaProductos().size()>0){
			textAreaEstado.append("[Productos - " + gen.getListaProductos().size() + "]\n");
		}
		if(gen.getListaLotesRecibidos().size()>0){
			textAreaEstado.append("[Lotes recibidos - " + gen.getListaLotesRecibidos().size() + "]\n");
		}
		if(gen.getListaVentas().size()>0){
			textAreaEstado.append("[Ventas - " + gen.getListaVentas().size() + "]\n");
		}
		if(gen.getListaUbicacionProducto().size()>0){
			textAreaEstado.append("[Ubicacion-producto - " + gen.getListaUbicacionProducto().size() + "]\n");
		}
		if(gen.getListaMovimientos().size()>0){
			textAreaEstado.append("[Movimientos - " + gen.getListaMovimientos().size() + "]\n");
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
	static public JFrame getFrame(){
		return frmPobladorDeTablas;
	}

	static public GeneradorCSV getGen() {
		return gen;
	}

	public void setGen(GeneradorCSV gen) {
		this.gen = gen;
	}

}
