package igu;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java_project.ProbabilidadEstacional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class MenuTendencias extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public MenuTendencias() {
				
		this.setVisible(true);		
		setTitle("Configuracion de tendencias");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 740, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{600, 0};
		gbl_contentPane.rowHeights = new int[]{0, 22, 60, 23, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblTendenciaAnualEn = new JLabel("Tendencia anual en la recepci\u00F3n de Lotes con Fecha Fija");
		GridBagConstraints gbc_lblTendenciaAnualEn = new GridBagConstraints();
		gbc_lblTendenciaAnualEn.insets = new Insets(0, 0, 5, 0);
		gbc_lblTendenciaAnualEn.gridx = 0;
		gbc_lblTendenciaAnualEn.gridy = 0;
		contentPane.add(lblTendenciaAnualEn, gbc_lblTendenciaAnualEn);
		
		JPanel panelUp = new JPanel();
		GridBagConstraints gbc_panelUp = new GridBagConstraints();
		gbc_panelUp.fill = GridBagConstraints.BOTH;
		gbc_panelUp.insets = new Insets(0, 0, 5, 0);
		gbc_panelUp.gridx = 0;
		gbc_panelUp.gridy = 1;
		contentPane.add(panelUp, gbc_panelUp);
		GridBagLayout gbl_panelUp = new GridBagLayout();
		gbl_panelUp.columnWidths = new int[]{586, 0};
		gbl_panelUp.rowHeights = new int[]{40, 0};
		gbl_panelUp.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelUp.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelUp.setLayout(gbl_panelUp);
		
		final JTextArea textAreaAgregados = new JTextArea();
		textAreaAgregados.setEditable(false);
		GridBagConstraints gbc_textAreaAgregados = new GridBagConstraints();
		gbc_textAreaAgregados.fill = GridBagConstraints.BOTH;
		gbc_textAreaAgregados.gridx = 0;
		gbc_textAreaAgregados.gridy = 0;
		panelUp.add(textAreaAgregados, gbc_textAreaAgregados);
		
		final ModeloTablaProbabilidadEstacional modelo = new ModeloTablaProbabilidadEstacional();
		table = new JTable(modelo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		contentPane.add(scrollPane, gbc_scrollPane);
		scrollPane.add(table);
		scrollPane.setViewportView(table);
		
		JButton btnGuardar = new JButton("Guardar");
		
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.anchor = GridBagConstraints.WEST;
		gbc_btnGuardar.fill = GridBagConstraints.VERTICAL;
		gbc_btnGuardar.gridx = 0;
		gbc_btnGuardar.gridy = 3;
		contentPane.add(btnGuardar, gbc_btnGuardar);
		
		
		//EL CODIGO QUE SE EJECUTA AL ABRIRSE LA VENTANA
		
		final ProbabilidadEstacional probabilidades =poblar_bd.getGen().getProbabilidades(); 
		modelo.cargarDatos(probabilidades);		
		
		textAreaAgregados.setText("La suma de las probabilidades es de " + probabilidades.getSumaProb() + "% ===> "+ ((probabilidades.getSumaProb()==100)?"OK":"ERROR") + "\n");

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textAreaAgregados.setText(null);
				modelo.getProbabilidades();//Pâra que se actualice lo que se va a mostrar, ya que se actualiza en este metodo el array de probabilidades
				textAreaAgregados.append("La suma de las probabilidades es de " + modelo.getProbabilidades().getSumaProb() + 
						"% ===> "+ ((modelo.getProbabilidades().getSumaProb()==100)?"OK":"ERROR") + "\n");
				if(modelo.getProbabilidades().getSumaProb()==100){
					poblar_bd.getGen().setProbabilidades(modelo.getProbabilidades());
					textAreaAgregados.append("Escrito correctamente.\n");
				}else{
					textAreaAgregados.append("ERROR al escribir probabilidades.\n");
					
				}
			}
		});		
		

		
	}

}
