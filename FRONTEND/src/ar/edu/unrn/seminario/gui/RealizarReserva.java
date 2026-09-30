package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RealizarReserva extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RealizarReserva frame = new RealizarReserva();
					
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
	public RealizarReserva() {
		setTitle("Realizar Reserva");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		setBounds(-8, -23, 984, 706);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		
		panel.setPreferredSize(new Dimension(950, 520));
		
		//esto inserta el panel en el scroll principal
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_11 = new JLabel("Agregar cliente:");
		lblNewLabel_11.setBounds(10, 11, 111, 14);
		panel.add(lblNewLabel_11);
		
		JLabel lblNewLabel_5 = new JLabel("Cliente");
		lblNewLabel_5.setBounds(30, 36, 46, 14);
		panel.add(lblNewLabel_5);
		
		textField_3 = new JTextField();
		textField_3.setBounds(145, 33, 117, 20);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Nombre:");
		lblNewLabel_7.setBounds(40, 61, 60, 14);
		panel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_9 = new JLabel("Contacto:");
		lblNewLabel_9.setBounds(40, 86, 60, 14);
		panel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_8 = new JLabel("-");
		lblNewLabel_8.setBounds(110, 61, 133, 14);
		panel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_10 = new JLabel("-");
		lblNewLabel_10.setBounds(110, 86, 133, 14);
		panel.add(lblNewLabel_10);
		
		JButton btnBuscarCliente = new JButton("Buscar");
		btnBuscarCliente.setBounds(307, 32, 89, 23);
		panel.add(btnBuscarCliente);
		
		JButton btnAgregarAReserva = new JButton("Agregar a la Reserva");
		btnAgregarAReserva.setBounds(68, 120, 194, 23);
		panel.add(btnAgregarAReserva);
		
		JLabel lblNewLabel = new JLabel("Fecha de entrada");
		lblNewLabel.setBounds(474, 36, 109, 14);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(593, 33, 117, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("Buscar disponibilidad");
		btnNewButton_3.setBounds(750, 32, 183, 23);
		panel.add(btnNewButton_3);
		
		JLabel lblNewLabel_1 = new JLabel("Fecha de salida");
		lblNewLabel_1.setBounds(474, 61, 104, 14);
		panel.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(593, 58, 117, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Habitación/es disponibles");
		lblNewLabel_2.setBounds(445, 120, 488, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_6 = new JLabel("Plan solicitado");
		lblNewLabel_6.setBounds(30, 324, 86, 14);
		panel.add(lblNewLabel_6);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(145, 320, 117, 22);
		panel.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Estándar", "Premium"}));
		
		JButton btnNewButton_1 = new JButton("Aceptar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(30, 552, 89, 23);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Cancelar");
		btnNewButton_2.setBounds(154, 552, 89, 23);
		panel.add(btnNewButton_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(445, 145, 488, 99);
		panel.add(scrollPane_1);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
			},
			new String[] {
				"N\u00FAmero", "Tipo de habitaci\u00F3n", "Tipo de camas", "Cantidad de camas", "Precio", "Seleccionar"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, true, true, false, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(55);
		table.getColumnModel().getColumn(0).setMinWidth(18);
		table.getColumnModel().getColumn(1).setPreferredWidth(115);
		table.getColumnModel().getColumn(2).setPreferredWidth(115);
		table.getColumnModel().getColumn(3).setPreferredWidth(125);
		table.getColumnModel().getColumn(5).setResizable(false);
		scrollPane_1.setViewportView(table);
		
		JLabel lblNewLabel_12 = new JLabel("Agregar habitación");
		lblNewLabel_12.setBounds(454, 11, 111, 14);
		panel.add(lblNewLabel_12);
		
		JLabel lblNewLabel_14 = new JLabel("Agregar plan");
		lblNewLabel_14.setBounds(10, 299, 90, 14);
		panel.add(lblNewLabel_14);
		
		JButton btnNewButton = new JButton("Registrar Seña de la Reserva");
		btnNewButton.setBounds(68, 446, 214, 23);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_3 = new JLabel("Agregar pago por adelantado");
		lblNewLabel_3.setBounds(10, 404, 175, 14);
		panel.add(lblNewLabel_3);

	}
}
