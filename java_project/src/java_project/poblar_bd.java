package java_project;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Time;
import java.util.Timer;

import javax.swing.ButtonGroup;
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
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

public class poblar_bd {

	private JFrame frmPobladorDeTablas;
	private JTextField textFieldRutaEntrada;
	private String rutaEntrada = "/home/carlos/pfc/pfc/entradas_app/";
	private String rutaSalida = "/home/carlos/pfc/pfc/salidas_app/";
	private JTextField textFieldRutaSalida;
	static JTextArea textArea;
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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPobladorDeTablas = new JFrame();
		frmPobladorDeTablas.setTitle("Poblador de Base de Datos");
		frmPobladorDeTablas.setBounds(100, 100, 980, 539);
		frmPobladorDeTablas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPobladorDeTablas.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 270, 785, 234);
		frmPobladorDeTablas.getContentPane().add(scrollPane);
		
		final JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setAutoscrolls(true);
		
		JLabel lblRutaEnt = new JLabel("Ruta entrada");
		lblRutaEnt.setBounds(714, 10, 100, 15);
		frmPobladorDeTablas.getContentPane().add(lblRutaEnt);
		
		textFieldRutaEntrada = new JTextField();
		textFieldRutaEntrada.setBounds(714, 25, 243, 27);
		frmPobladorDeTablas.getContentPane().add(textFieldRutaEntrada);
		textFieldRutaEntrada.setColumns(10);
		textFieldRutaEntrada.setText(rutaEntrada);
		
		JLabel lblRutaSal = new JLabel("Ruta salida");
		lblRutaSal.setBounds(714, 54, 100, 15);
		frmPobladorDeTablas.getContentPane().add(lblRutaSal);
		
		textFieldRutaSalida = new JTextField();
		textFieldRutaSalida.setBounds(714, 74, 243, 27);
		frmPobladorDeTablas.getContentPane().add(textFieldRutaSalida);
		textFieldRutaSalida.setColumns(10);
		textFieldRutaSalida.setText(rutaSalida);
		
		final JSpinner spinnerCantMin = new JSpinner();
		spinnerCantMin.setModel(new SpinnerNumberModel(10, 10, 50, 10));
		spinnerCantMin.setBounds(643, 134, 54, 26);
		frmPobladorDeTablas.getContentPane().add(spinnerCantMin);
		
		final JSpinner spinnerCantMax = new JSpinner();
		spinnerCantMax.setModel(new SpinnerNumberModel(30, 10, 50, 10));
		spinnerCantMax.setBounds(643, 96, 54, 26);
		frmPobladorDeTablas.getContentPane().add(spinnerCantMax);

		final JSpinner spinnerMaxCantVenta = new JSpinner();
		spinnerMaxCantVenta.setModel(new SpinnerNumberModel(4, 1, 20, 1));
		spinnerMaxCantVenta.setBounds(293, 185, 54, 26);
		frmPobladorDeTablas.getContentPane().add(spinnerMaxCantVenta);
		
		JLabel lblMaxDasVendindose = new JLabel("Max días en venta");
		lblMaxDasVendindose.setBounds(28, 224, 151, 15);
		frmPobladorDeTablas.getContentPane().add(lblMaxDasVendindose);
		
		final JSpinner spinnerMaxDiasVendiendose = new JSpinner();
		spinnerMaxDiasVendiendose.setModel(new SpinnerNumberModel(90, 30, 180, 1));
		spinnerMaxDiasVendiendose.setBounds(176, 218, 54, 26);
		frmPobladorDeTablas.getContentPane().add(spinnerMaxDiasVendiendose);
		
		final JCheckBox chckbxVentas1Ud = new JCheckBox("70% de ventas 1 ud.");
		chckbxVentas1Ud.setBounds(359, 189, 186, 18);
		frmPobladorDeTablas.getContentPane().add(chckbxVentas1Ud);
		
		JLabel lblMaxCantVenta = new JLabel("Max Cant/Venta");
		lblMaxCantVenta.setBounds(176, 191, 151, 15);
		frmPobladorDeTablas.getContentPane().add(lblMaxCantVenta);
		
		JLabel lblCantMin = new JLabel("Cant. Min");
		lblCantMin.setBounds(570, 140, 89, 15);
		frmPobladorDeTablas.getContentPane().add(lblCantMin);
		
		JLabel lblCantMax = new JLabel("Cant. Max");
		lblCantMax.setBounds(570, 102, 93, 15);
		frmPobladorDeTablas.getContentPane().add(lblCantMax);
		
		final JSpinner spinnerLotes = new JSpinner(new SpinnerNumberModel(30, 1, 90, 1));		
		spinnerLotes.setBounds(498, 129, 54, 26);
		frmPobladorDeTablas.getContentPane().add(spinnerLotes);
		
		final JSpinner spinnerNumLotesFijas = new JSpinner();
		spinnerNumLotesFijas.setModel(new SpinnerNumberModel(15, 15, 30, 15));
		spinnerNumLotesFijas.setBounds(336, 129, 53, 26);
		frmPobladorDeTablas.getContentPane().add(spinnerNumLotesFijas);
		
		final JRadioButton rdbtnFechasFijas = new JRadioButton("Fechas fijas");
		rdbtnFechasFijas.setSelected(true);		//por defecto, fechas fijas
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
		rdbtnFechasFijas.setBounds(259, 100, 130, 18);
		frmPobladorDeTablas.getContentPane().add(rdbtnFechasFijas);
		
		JRadioButton rdbtnFechasVariables = new JRadioButton("Fechas variables");
		rdbtnFechasVariables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spinnerLotes.setEnabled(true);	//activo el spinner de lotes anuales variable
				spinnerNumLotesFijas.setEnabled(false); //desactivo el spinner de lotes anuales de fechas fijas 
			}
		});
		rdbtnFechasVariables.setBounds(401, 100, 157, 18);
		frmPobladorDeTablas.getContentPane().add(rdbtnFechasVariables);
		
		ButtonGroup grupoRadioFechas = new ButtonGroup();
		grupoRadioFechas.add(rdbtnFechasFijas);
		grupoRadioFechas.add(rdbtnFechasVariables);
		
		JButton btnGuardarRuta = new JButton("Guardar Ruta");
		btnGuardarRuta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rutaEntrada = textFieldRutaEntrada.getText();
				rutaSalida = textFieldRutaSalida.getText();
				textArea.append("Guardadas nuevas rutas:\n" + "Entrada: " + rutaEntrada + "\n" + "Salida: " + rutaSalida + "\n");
			}
		});
		btnGuardarRuta.setBounds(806, 113, 151, 27);
		frmPobladorDeTablas.getContentPane().add(btnGuardarRuta);
		
		final JSpinner spinnerStockMedioProducto = new JSpinner();
		spinnerStockMedioProducto.setModel(new SpinnerNumberModel(12, 0, 40, 1));
		spinnerStockMedioProducto.setBounds(190, 48, 56, 26);
		frmPobladorDeTablas.getContentPane().add(spinnerStockMedioProducto);
		
		JLabel lblStockMedioInicial = new JLabel("Stock Medio Inicial ");
		lblStockMedioInicial.setBounds(39, 54, 157, 15);
		frmPobladorDeTablas.getContentPane().add(lblStockMedioInicial);
		
		
		JButton btnLeerProductos = new JButton("Generar productos");
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
		btnLeerProductos.setBounds(18, 10, 218, 27);
		frmPobladorDeTablas.getContentPane().add(btnLeerProductos);
		
		JButton btnPruebas = new JButton("Pruebas");
		btnPruebas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					Temporizador.iniciarTemporizador();
					textArea.append("Leyendo ubicaciones-producto...\n");
					gen.leerUbicaciones(null);
					textArea.append("Generando ubicaciones-producto...\n");
					textArea.append("Numero de ubicaciones-producto generadas: " + gen.generarUbicacionProducto() +"\n");
					textArea.append("Numero de ubicaciones-producto escritas: " + gen.escribirUbicacionProducto(null) + "\n");
					textArea.append("Generando movimientos...\n");
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
		btnPruebas.setBounds(857, 219, 100, 27);
		frmPobladorDeTablas.getContentPane().add(btnPruebas);
		
		JButton btnGenerarLotesRecibidos = new JButton("Generar Lotes Recibidos");
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
								textArea.append("[Fechas fijas] Número de lotes generados: " + numLotesGenerados + "\n");
								int numLotesEscritos = 0;
								numLotesEscritos = gen.escribirLotesRecibidos(null);
								textArea.append("[Fechas fijas] Escribiendo lotes recibidos...\n");
								textArea.append("[Fechas fijas] Número de lotes escritos: " + numLotesEscritos + "\n");
							}
							else{
								textArea.append("[Fechas variables] Generando lotes recibidos...\n" );
								textArea.append("[Fechas variables] Lotes por producto/año: " + numLotesFechaVariable + "\n");
								int numLotesGenerados = 0;
								numLotesGenerados = gen.GenerarLotesRecibidosFechaVariableCantidadVariable(numLotesFechaVariable, (int)spinnerCantMin.getValue(), (int)spinnerCantMax.getValue());
								textArea.append("[Fechas variables] Número de lotes generados: " + numLotesGenerados + "\n");
								int numLotesEscritos = 0;
								numLotesEscritos = gen.escribirLotesRecibidos(null);
								textArea.append("[Fechas variables] Escribiendo lotes recibidos...\n");
								textArea.append("[Fechas variables] Número de lotes escritos: " + numLotesEscritos + "\n");
							}
//							gen.flushAll();
							textArea.append("Tiempo ejecucion: " + Temporizador.pararTemporizador("Generación de lotes") + " segundos.\n");
							textArea.append("*********************** GENERACION DE LOTES COMPLETADA *******************\n" );
					
						}catch(IOException e){
							System.out.println("Error ejecutando en generacion lotes.");
						}
					}
				});			
				hilo.start();
			}

		});
		btnGenerarLotesRecibidos.setBounds(18, 90, 228, 27);
		frmPobladorDeTablas.getContentPane().add(btnGenerarLotesRecibidos);
		
		JLabel lblNumLotesAnuales = new JLabel("Lotes/año");
		lblNumLotesAnuales.setBounds(423, 134, 114, 15);
		frmPobladorDeTablas.getContentPane().add(lblNumLotesAnuales);
		
		JSeparator separatorProductos = new JSeparator();
		separatorProductos.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		separatorProductos.setBounds(6, 6, 306, 75);
		frmPobladorDeTablas.getContentPane().add(separatorProductos);		
		
		JButton btnGenerarVentas = new JButton("Generar Ventas");
		btnGenerarVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				final int maxCantVenta = (int) spinnerMaxCantVenta.getValue();
				textArea.append("Máxima cantidad por venta: " + maxCantVenta + "\n");
				
				final int maxDiasVendiendose = (int) spinnerMaxDiasVendiendose.getValue();
				textArea.append("Máx. días vendiéndose: " + maxDiasVendiendose + "\n");	
				
				final boolean ventas1Ud = chckbxVentas1Ud.isSelected();
				
				Thread hilo = new Thread(new Runnable() {

					@Override
					public void run() {
						try{

							Temporizador.iniciarTemporizador();
							textArea.append("Generando ventas... este proceso puede tardar unos minutos...\n" );
							int numVentasGeneradas = 0;
							numVentasGeneradas = gen.generarVentas(maxCantVenta,maxDiasVendiendose, ventas1Ud);
							textArea.append("Número de ventas generadas: " + numVentasGeneradas + "\n");
							int numVentasEscritas = 0;
							numVentasEscritas = gen.escribirVentas(null);
							textArea.append("Escribiendo ventas...\n");
							textArea.append("Número de ventas escritas: " + numVentasEscritas + "\n");
							textArea.append("Tiempo ejecucion: " + Temporizador.pararTemporizador("Generacion de ventas") + " segundos.\n");
							int numProdEscritos = gen.escribirProductos(null);
							textArea.append("Se ha modificado y escrito en fichero la cantidad de stock (tabla producto).\n");
							textArea.append("Número de productos escritos: " + numProdEscritos + "\n");
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
		btnGenerarVentas.setBounds(18, 185, 152, 27);
		frmPobladorDeTablas.getContentPane().add(btnGenerarVentas);
		
		JSeparator separatorVentas = new JSeparator();
		separatorVentas.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		separatorVentas.setBounds(6, 179, 696, 81);
		frmPobladorDeTablas.getContentPane().add(separatorVentas);		
		
		JSeparator separatorLotes = new JSeparator();
		separatorLotes.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		separatorLotes.setBounds(6, 86, 696, 81);
		frmPobladorDeTablas.getContentPane().add(separatorLotes);
		
		JLabel labelLotesAñoFijas = new JLabel("Lotes/año");
		labelLotesAñoFijas.setBounds(259, 135, 114, 15);
		frmPobladorDeTablas.getContentPane().add(labelLotesAñoFijas);
		
		JButton btnFlushAll = new JButton("FLUSH ALL");
		btnFlushAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gen.flushAll();
				textArea.append("===========================BORRADOS TODOS LOS DATOS EN MEMORIA=======================");
			}
		});
		btnFlushAll.setBounds(806, 152, 100, 27);
		frmPobladorDeTablas.getContentPane().add(btnFlushAll);
		
	
		
		
		
		
	
	}
}
