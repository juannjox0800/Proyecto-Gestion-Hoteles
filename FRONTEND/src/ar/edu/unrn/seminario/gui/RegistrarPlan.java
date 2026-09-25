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
					ListaPlanes frame = new ListaPlanes();
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombrePlan = new JLabel("Nombre");
		lblNombrePlan.setBounds(10, 35, 75, 14);
		contentPane.add(lblNombrePlan);
		
		JLabel lblCostoPlan = new JLabel("Costo");
		lblCostoPlan.setBounds(10, 60, 75, 14);
		contentPane.add(lblCostoPlan);
		
		JLabel lblServiciosLista = new JLabel("Servicios incluidos");
		lblServiciosLista.setBounds(10, 85, 116, 14);
		contentPane.add(lblServiciosLista);
		
		textField = new JTextField();
		textField.setBounds(136, 32, 276, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(136, 57, 276, 20);
		contentPane.add(textField_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(136, 85, 276, 82);
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
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Agregar Servicio");
		btnNewButton.setBounds(277, 170, 147, 23);
		contentPane.add(btnNewButton);
		
		JButton btnGuardarPlan = new JButton("Guardar");
		btnGuardarPlan.setBounds(10, 227, 89, 23);
		contentPane.add(btnGuardarPlan);
		
		JButton btnCancelarPlan = new JButton("Cancelar");
		btnCancelarPlan.setBounds(108, 227, 89, 23);
		contentPane.add(btnCancelarPlan);
		
		JButton btnEliminarServicio = new JButton("Eliminar Servicio");
		btnEliminarServicio.setBounds(120, 170, 147, 23);
		contentPane.add(btnEliminarServicio);

	}
}
