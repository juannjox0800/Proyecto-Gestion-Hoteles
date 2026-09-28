package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SolicitarServicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfBuscaDNI;
	private JTextField tfDiasConsumo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SolicitarServicio frame = new SolicitarServicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SolicitarServicio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Buscar cliente");
		lblNewLabel.setBounds(10, 11, 121, 22);
		contentPane.add(lblNewLabel);
		
		tfBuscaDNI = new JTextField();
		tfBuscaDNI.setBounds(166, 12, 100, 20);
		contentPane.add(tfBuscaDNI);
		tfBuscaDNI.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Seleccionar servicio");
		lblNewLabel_1.setBounds(10, 54, 146, 14);
		contentPane.add(lblNewLabel_1);
		
		JComboBox cbListaServicio = new JComboBox();
		cbListaServicio.setModel(new DefaultComboBoxModel(new String[] {"Piscina ", "Gym"}));
		cbListaServicio.setBounds(166, 50, 190, 22);
		contentPane.add(cbListaServicio);
		
		JLabel lblNewLabel_2 = new JLabel("Dias de consumo");
		lblNewLabel_2.setBounds(10, 89, 100, 14);
		contentPane.add(lblNewLabel_2);
		
		tfDiasConsumo = new JTextField();
		tfDiasConsumo.setBounds(166, 83, 86, 20);
		contentPane.add(tfDiasConsumo);
		tfDiasConsumo.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(10, 227, 89, 23);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(114, 227, 89, 23);
		contentPane.add(btnCancelar);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBuscar.setBounds(267, 11, 89, 23);
		contentPane.add(btnBuscar);

	}
}
