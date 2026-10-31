package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.dto.ServicioDTO;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class RegistrarPlan extends JFrame {
	private ArrayList<ServicioDTO> serviciosSeleccionados = new ArrayList<>();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tFNombre;
	private JTextField tFCosto;
	private JTable TableServiciosIncluidos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MemoryApi api = new MemoryApi();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrarPlan frame = new RegistrarPlan(api);
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
	public RegistrarPlan(IApi api) {
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
		btnAgregarServicio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				List<ServicioDTO> lista = api.obtenerListaServicios();
		        if (lista == null || lista.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "No hay servicios registrados en el sistema. Debe dar de alta al menos uno.","Aviso", JOptionPane.WARNING_MESSAGE);
		        } else {
		            VentanaSeleccionarServicio ventanaSeleccion = new VentanaSeleccionarServicio(api, RegistrarPlan.this);
		            ventanaSeleccion.setVisible(true);
		        }
		}
		});
		
		JComboBox cBActivo = new JComboBox<>();
		cBActivo.setModel(new DefaultComboBoxModel(new String[] {"Activo", "Inactivo"}));
		cBActivo.setBounds(127, 84, 195, 22);
		contentPane.add(cBActivo);
		contentPane.add(btnAgregarServicio);

		JButton btnGuardarPlan = new JButton("Guardar");
		btnGuardarPlan.setBounds(167, 263, 89, 23);
		btnGuardarPlan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					if (tFCosto.getText().trim().isEmpty()) {
		                throw new DatoVacioException("El campo costo no puede estar vacío.");
		            }
					String nombre = tFNombre.getText();
					int costo = Integer.parseInt(tFCosto.getText());
				
					String estadoSeleccionado = (String) cBActivo.getSelectedItem();
					boolean esActivo = "Activo".equals(estadoSeleccionado); 
					api.registrarPlan(serviciosSeleccionados, costo, nombre, esActivo);
					JOptionPane.showMessageDialog(null, "Plan registrado con éxito");
					dispose();
				} catch (ValorInvalidoException a) {
					JOptionPane.showMessageDialog(null, a.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (EntidadDuplicadaException b) {
					JOptionPane.showMessageDialog(null, b.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (DatoVacioException c) {
					JOptionPane.showMessageDialog(null, c.getMessage(), "rellenar todos los datos exigidos", JOptionPane.ERROR_MESSAGE);
				}
			
			}
		});
		contentPane.add(btnGuardarPlan);
		
		JButton btnCancelarPlan = new JButton("Cancelar");
		btnCancelarPlan.setBounds(265, 263, 89, 23);
		btnCancelarPlan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
		}
		});
		contentPane.add(btnCancelarPlan);
		
		JButton btnEliminarServicio = new JButton("Eliminar Servicio");
		btnEliminarServicio.setBounds(46, 203, 135, 23);
		btnEliminarServicio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
		        int filaSeleccionada = TableServiciosIncluidos.getSelectedRow();
		        if (filaSeleccionada != -1) {
		            DefaultTableModel modelo = (DefaultTableModel) TableServiciosIncluidos.getModel();
		            modelo.removeRow(filaSeleccionada);
		            
		            serviciosSeleccionados.remove(filaSeleccionada);
		        } else {
		            JOptionPane.showMessageDialog(null, 
		                "Por favor, seleccione un servicio de la tabla para eliminar.", 
		                "Aviso", 
		                JOptionPane.WARNING_MESSAGE);
		        }
		    }
		});
		contentPane.add(btnEliminarServicio);
	
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(46, 88, 71, 14);
		contentPane.add(lblEstado);
		

	}
	public void agregarServicioATabla(ServicioDTO servicio) {
		serviciosSeleccionados.add(servicio);
		DefaultTableModel modeloTabla = (DefaultTableModel) TableServiciosIncluidos.getModel();
		modeloTabla.addRow(new Object[] { servicio.getNombre() });
	}
}
