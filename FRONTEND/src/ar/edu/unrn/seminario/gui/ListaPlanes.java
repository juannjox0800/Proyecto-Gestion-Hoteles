package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class ListaPlanes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
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
	public ListaPlanes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 414, 118);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Nombre", "Estado", "Costo"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		scrollPane.setViewportView(table);
		
		JButton btnEliminarPlan = new JButton("Eliminar");
		btnEliminarPlan.setBounds(335, 139, 89, 23);
		contentPane.add(btnEliminarPlan);
		
		JButton btnModificarPlan = new JButton("Modificar");
		btnModificarPlan.setBounds(236, 139, 89, 23);
		contentPane.add(btnModificarPlan);
		
		JButton btnAceptarPlan = new JButton("Aceptar");
		btnAceptarPlan.setBounds(10, 227, 89, 23);
		contentPane.add(btnAceptarPlan);
		
		JButton btnCancelarPlan = new JButton("Cancelar");
		btnCancelarPlan.setBounds(109, 227, 89, 23);
		contentPane.add(btnCancelarPlan);

	}

}
