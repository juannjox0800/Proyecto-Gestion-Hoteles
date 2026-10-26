package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ReportarIncidente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfHabitacion;
	private JTextField tfDescripcion;
	private JTextField tfFecha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReportarIncidente frame = new ReportarIncidente();
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
	public ReportarIncidente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNumHabitacion = new JLabel("Habitación afectada");
		lblNumHabitacion.setBounds(22, 99, 129, 26);
		contentPane.add(lblNumHabitacion);
		
		JLabel lblDescripcin = new JLabel("Descripción");
		lblDescripcin.setBounds(23, 135, 87, 26);
		contentPane.add(lblDescripcin);
		
		JLabel lblFechaDelIncidente = new JLabel("Fecha del incidente");
		lblFechaDelIncidente.setBounds(22, 171, 129, 26);
		contentPane.add(lblFechaDelIncidente);
		
		tfHabitacion = new JTextField();
		tfHabitacion.setBounds(161, 103, 129, 19);
		contentPane.add(tfHabitacion);
		tfHabitacion.setColumns(10);
		
		tfDescripcion = new JTextField();
		tfDescripcion.setColumns(10);
		tfDescripcion.setBounds(161, 139, 129, 19);
		contentPane.add(tfDescripcion);
		
		tfFecha = new JTextField();
		tfFecha.setColumns(10);
		tfFecha.setBounds(161, 178, 129, 19);
		contentPane.add(tfFecha);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAceptar.setBounds(26, 216, 84, 20);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(121, 216, 84, 20);
		contentPane.add(btnCancelar);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Servicio", "Habitación"}));
		comboBox.setBounds(161, 27, 129, 20);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Lugar del incidente");
		lblNewLabel.setBounds(22, 29, 104, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tipo de servicio");
		lblNewLabel_1.setBounds(22, 68, 85, 16);
		contentPane.add(lblNewLabel_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Piscina", "Gimnasio", "Cocina"}));
		comboBox_1.setBounds(163, 66, 127, 20);
		contentPane.add(comboBox_1);

	}
}
