package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class RegistrarPlan extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrarPlan frame = new RegistrarPlan();
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
	public RegistrarPlan() {
		setTitle("Registrar Plan");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 426, 336);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombrePlan = new JLabel("Nombre");
		lblNombrePlan.setBounds(46, 34, 75, 14);
		contentPane.add(lblNombrePlan);
		
		JLabel lblCostoPlan = new JLabel("Costo");
		lblCostoPlan.setBounds(46, 59, 75, 14);
		contentPane.add(lblCostoPlan);
		
		textField = new JTextField();
		textField.setBounds(127, 31, 195, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(127, 56, 195, 20);
		contentPane.add(textField_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(46, 113, 276, 82);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
			},
			new String[] {
				"Servicios incluidos"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Agregar Servicio");
		btnNewButton.setBounds(191, 203, 131, 23);
		contentPane.add(btnNewButton);
		
		JButton btnGuardarPlan = new JButton("Guardar");
		btnGuardarPlan.setBounds(167, 263, 89, 23);
		contentPane.add(btnGuardarPlan);
		
		JButton btnCancelarPlan = new JButton("Cancelar");
		btnCancelarPlan.setBounds(265, 263, 89, 23);
		contentPane.add(btnCancelarPlan);
		
		JButton btnEliminarServicio = new JButton("Eliminar Servicio");
		btnEliminarServicio.setBounds(46, 203, 135, 23);
		contentPane.add(btnEliminarServicio);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(46, 88, 71, 14);
		contentPane.add(lblEstado);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Activo", "Inactivo"}));
		comboBox.setBounds(127, 84, 195, 22);
		contentPane.add(comboBox);

	}
}
