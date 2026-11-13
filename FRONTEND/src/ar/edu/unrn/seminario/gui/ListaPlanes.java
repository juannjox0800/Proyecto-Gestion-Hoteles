package ar.edu.unrn.seminario.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.PlanDTO;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;

public class ListaPlanes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable jtListaPlanes;

	public ListaPlanes(IApi api) {
		setTitle("Lista de Planes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 23, 414, 118);
		contentPane.add(scrollPane);

		jtListaPlanes = new JTable();
		jtListaPlanes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Costo", "Estado vigencia"
			}
		) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		jtListaPlanes.getColumnModel().getColumn(0).setResizable(false);
		jtListaPlanes.getColumnModel().getColumn(1).setResizable(false);
		jtListaPlanes.getColumnModel().getColumn(2).setResizable(false);
		jtListaPlanes.getColumnModel().getColumn(0).setPreferredWidth(120);
		jtListaPlanes.getColumnModel().getColumn(1).setPreferredWidth(120);
		jtListaPlanes.getColumnModel().getColumn(2).setPreferredWidth(120);
		scrollPane.setViewportView(jtListaPlanes);

		recargarTabla(api);

		JButton btnModificarPlan = new JButton("Modificar");
		btnModificarPlan.setBounds(10, 152, 89, 23);
		contentPane.add(btnModificarPlan);

		JButton btnEliminarPlan = new JButton("Eliminar");
		btnEliminarPlan.setBounds(109, 152, 89, 23);
		contentPane.add(btnEliminarPlan);

		JButton btnSalirPlan = new JButton("Salir");
		btnSalirPlan.setBounds(335, 202, 89, 23);
		contentPane.add(btnSalirPlan);

		btnEliminarPlan.addActionListener((actionEvent) -> {
			int filaSeleccionada = jtListaPlanes.getSelectedRow();

			if (filaSeleccionada == -1) {
				JOptionPane.showMessageDialog(null, "Seleccione un plan de la tabla para eliminar", "Atencion", JOptionPane.WARNING_MESSAGE);
				return;
			}

			String nombrePlan = jtListaPlanes.getValueAt(filaSeleccionada, 0).toString();

			int confirmacion = JOptionPane.showConfirmDialog(null, "Estas seguro que desea eliminar el plan '" + nombrePlan + "'?", "Confirmar Eleccion", JOptionPane.YES_NO_OPTION);

			if (confirmacion == JOptionPane.YES_OPTION) {
				try {
					api.eliminarPlan(nombrePlan);

					recargarTabla(api);

					JOptionPane.showMessageDialog(null, "Plan eliminado correctamente");
				} catch (EntidadNoEncontradaException e) {
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnModificarPlan.addActionListener((actionEvent) -> {
			int filaSeleccionada = jtListaPlanes.getSelectedRow();

			if (filaSeleccionada == -1) {
				JOptionPane.showMessageDialog(null, "Seleccione un plan de la tabla para modificar", "Atencion", JOptionPane.WARNING_MESSAGE);
				return;
			}

			String nombre = jtListaPlanes.getValueAt(filaSeleccionada, 0).toString();

			try {
				PlanDTO dto = api.obtenerPlan(nombre);

				RegistrarPlan ventana = new RegistrarPlan(api, dto);

				ventana.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent w) {
						recargarTabla(api);
					}
				});

				ventana.setVisible(true);

			} catch (EntidadNoEncontradaException e) {
				JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error al modificar el plan", JOptionPane.ERROR_MESSAGE);
			} catch (DatoVacioException e) {
				JOptionPane.showMessageDialog(null ,"Error"+ e.getMessage(),  "Error al modificar el plan", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnSalirPlan.addActionListener((actionEvent) -> {
			dispose();
		});
	}

	private void recargarTabla(IApi api) {
		DefaultTableModel modelo = (DefaultTableModel) jtListaPlanes.getModel();
		modelo.setRowCount(0);

		for (PlanDTO dto : api.obtenerListaPlan()) {
			modelo.addRow(new Object[] {
					dto.getNombre(), "$" + dto.getCosto(), dto.isEstadoActivo() ? "Activo" : "Inactivo"
			});
		}
	}
}