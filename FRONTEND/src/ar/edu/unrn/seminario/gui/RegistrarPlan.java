package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JTextField tFNombre;
	private JTextField tFCosto;
	private JTable TableServiciosIncluidos;

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
		
		tFNombre = new JTextField();
		tFNombre.setBounds(127, 31, 195, 20);
		contentPane.add(tFNombre);
		tFNombre.setColumns(10);
		
		tFCosto = new JTextField();
		tFCosto.setColumns(10);
		tFCosto.setBounds(127, 56, 195, 20);
		contentPane.add(tFCosto);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(46, 113, 276, 82);
		contentPane.add(scrollPane);
		
		TableServiciosIncluidos = new JTable();
		TableServiciosIncluidos.setModel(new DefaultTableModel(
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
		TableServiciosIncluidos.getColumnModel().getColumn(0).setResizable(false);
		TableServiciosIncluidos.getColumnModel().getColumn(0).setPreferredWidth(150);
		scrollPane.setViewportView(TableServiciosIncluidos);
		
		JButton btnAgregarServicio = new JButton("Agregar Servicio");
		btnAgregarServicio.setBounds(191, 203, 131, 23);
		contentPane.add(btnAgregarServicio);
		
		btnAgregarServicio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			
		}
		});
		JButton btnGuardarPlan = new JButton("Guardar");
		btnGuardarPlan.setBounds(167, 263, 89, 23);
		contentPane.add(btnGuardarPlan);
		btnGuardarPlan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			
		}
		});
		
		JButton btnCancelarPlan = new JButton("Cancelar");
		btnCancelarPlan.setBounds(265, 263, 89, 23);
		contentPane.add(btnCancelarPlan);
		btnCancelarPlan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			
		}
		});
		
		JButton btnEliminarServicio = new JButton("Eliminar Servicio");
		btnEliminarServicio.setBounds(46, 203, 135, 23);
		contentPane.add(btnEliminarServicio);
		btnEliminarServicio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			
		}
		});
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(46, 88, 71, 14);
		contentPane.add(lblEstado);
		
		JComboBox cBActivo = new JComboBox<>();
		cBActivo.setModel(new DefaultComboBoxModel(new String[] {"Activo", "Inactivo"}));
		cBActivo.setBounds(127, 84, 195, 22);
		contentPane.add(cBActivo);

	}
}
