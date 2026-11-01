package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.dto.ServicioDTO;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;

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
					IApi apiPrueba = new MemoryApi();
					ListaServicios frame = new ListaServicios(apiPrueba);
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
	public ListaServicios(IApi api) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(10, 227, 89, 23);
		contentPane.add(btnSalir);
		
		JButton btnEliminarServicio = new JButton("Eliminar");
		btnEliminarServicio.setBounds(335, 179, 89, 23);
		contentPane.add(btnEliminarServicio);
		
		JButton btnModificarServicio = new JButton("Modificar");
		btnModificarServicio.setBounds(235, 179, 89, 23);
		contentPane.add(btnModificarServicio);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 39, 414, 129);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Precio", "Estado vigencia"
			}
		));
		table.getColumnModel().getColumn(2).setPreferredWidth(92);
		scrollPane.setViewportView(table);
		
		recargarTabla(api);
		
		btnEliminarServicio.addActionListener((actionEvent) -> {
			int filaSeleccionada = table.getSelectedRow();
			
			if (filaSeleccionada == -1) {
				JOptionPane.showMessageDialog(null, "Seleccione un servicio de la tabla para eliminar", "Atencion", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			String nombreServicio = table.getValueAt(filaSeleccionada, 0).toString();
			
			int confirmacion = JOptionPane.showConfirmDialog(null, "Estas seguro que desea eliminar el servicio '" + nombreServicio + "'?", "Confirmar Eleccion", JOptionPane.YES_NO_OPTION);
			
			if (confirmacion == JOptionPane.YES_OPTION) {
				try {
					api.eliminarServicio(nombreServicio);
					
					recargarTabla(api);
					
					JOptionPane.showMessageDialog(null, "Servicio eliminado correctamente");
				} catch (EntidadNoEncontradaException e) {
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnModificarServicio.addActionListener((actionEvent) -> {
			int filaSeleccionada = table.getSelectedRow();
			
			if (filaSeleccionada == -1) {
				JOptionPane.showMessageDialog(null, "Seleccione un servicio de la tabla para modificar", "Atencion", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			String nombre = table.getValueAt(filaSeleccionada, 0).toString();
			
			try {
				ServicioDTO dto = api.obtenerServicio(nombre);
				
				RegistrarServicio ventana = new RegistrarServicio(api, dto);
				
				ventana.addWindowListener(new WindowAdapter () {
					@Override
					public void windowClosed (WindowEvent w) {
						recargarTabla(api);
					}
				});
				
				ventana.setVisible(true);
				
			} catch (EntidadNoEncontradaException e) {
				JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error al registrar el servicio", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		btnSalir.addActionListener((actionEvent) -> {
			dispose();
		});
	}
	
	private void recargarTabla(IApi api) {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
		
		for (ServicioDTO dto : api.obtenerListaServicios()) {
			modelo.addRow(new Object[] {
					dto.getNombre(), "$" + dto.getPrecio(), dto.isEstadoVigente() ? "Activo" : "Inactivo"
			});
		}
	}
}
