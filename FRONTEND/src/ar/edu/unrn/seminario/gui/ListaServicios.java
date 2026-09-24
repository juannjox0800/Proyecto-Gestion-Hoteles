package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class ListaServicios extends JFrame {

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
					ListaServicios frame = new ListaServicios();
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
	public ListaServicios() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAceptarServicio = new JButton("Aceptar");
		btnAceptarServicio.setBounds(10, 227, 89, 23);
		contentPane.add(btnAceptarServicio);
		
		JButton btnCancelarServicio = new JButton("Cancelar");
		btnCancelarServicio.setBounds(109, 227, 89, 23);
		contentPane.add(btnCancelarServicio);
		
		JButton btnEliminarServicio = new JButton("Eliminar");
		btnEliminarServicio.setBounds(345, 179, 89, 23);
		contentPane.add(btnEliminarServicio);
		
		JButton btnModificarServicio = new JButton("Modificar");
		btnModificarServicio.setBounds(245, 179, 89, 23);
		contentPane.add(btnModificarServicio);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 39, 414, 129);
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
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Nombre", "Precio", "Estado vigencia"
			}
		));
		table.getColumnModel().getColumn(2).setPreferredWidth(92);
		scrollPane.setViewportView(table);

	}
}
