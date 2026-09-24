package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModificarPlan extends JFrame {

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
					ModificarPlan frame = new ModificarPlan();
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
	public ModificarPlan() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setBounds(49, 22, 89, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblCosto = new JLabel("Costo");
		lblCosto.setBounds(49, 47, 71, 14);
		contentPane.add(lblCosto);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(49, 72, 71, 14);
		contentPane.add(lblEstado);
		
		JLabel lblServicios = new JLabel("Servicios");
		lblServicios.setBounds(49, 97, 71, 14);
		contentPane.add(lblServicios);
		
		textField = new JTextField();
		textField.setBounds(130, 19, 186, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(130, 47, 186, 20);
		contentPane.add(textField_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Activo", "Inactivo"}));
		comboBox.setBounds(130, 68, 186, 22);
		contentPane.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(130, 97, 236, 81);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				""
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
		scrollPane.setViewportView(table);
		
		JButton btnAñadirServicio = new JButton("Añadir ");
		btnAñadirServicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAñadirServicio.setBounds(277, 189, 89, 23);
		contentPane.add(btnAñadirServicio);
		
		JButton btnEliminarServicio = new JButton("Eliminar ");
		btnEliminarServicio.setBounds(189, 189, 89, 23);
		contentPane.add(btnEliminarServicio);
		
		JButton btnGuardarMod = new JButton("Guardar");
		btnGuardarMod.setBounds(10, 227, 89, 23);
		contentPane.add(btnGuardarMod);
		
		JButton btnCancelarMod = new JButton("Cancelar");
		btnCancelarMod.setBounds(109, 227, 89, 23);
		contentPane.add(btnCancelarMod);

	}
}
