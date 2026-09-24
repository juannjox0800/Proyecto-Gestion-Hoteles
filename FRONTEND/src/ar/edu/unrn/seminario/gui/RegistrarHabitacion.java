package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Font;

public class RegistrarHabitacion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrarHabitacion frame = new RegistrarHabitacion();
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
	public RegistrarHabitacion() {
		setTitle("Registrar Habitacion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("N° Habitación:");
		lblNewLabel.setBounds(24, 23, 90, 25);
		contentPane.add(lblNewLabel);
		
		JLabel numeroCamas = new JLabel("Número de camas:");
		numeroCamas.setBounds(24, 59, 104, 25);
		contentPane.add(numeroCamas);
		
		JLabel tipoDeCamas = new JLabel("Tipo de camas:");
		tipoDeCamas.setBounds(24, 93, 90, 25);
		contentPane.add(tipoDeCamas);
		
		JLabel tipoHabitacion = new JLabel("Tipo Habitacion:");
		tipoHabitacion.setBounds(24, 129, 90, 25);
		contentPane.add(tipoHabitacion);
		
		textField = new JTextField();
		textField.setBounds(145, 25, 183, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(145, 61, 183, 20);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(145, 95, 183, 20);
		contentPane.add(textField_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Estandar", "Superior", "Deluxe", "Suite"}));
		comboBox.setBounds(145, 130, 183, 22);
		contentPane.add(comboBox);
		
		JLabel costoHabitacion = new JLabel("Costo Habitacion:");
		costoHabitacion.setBounds(24, 165, 104, 25);
		contentPane.add(costoHabitacion);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(145, 167, 183, 20);
		contentPane.add(textField_3);
		
		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(338, 227, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Aceptar");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(239, 227, 89, 23);
		contentPane.add(btnNewButton_1);

	}
}
