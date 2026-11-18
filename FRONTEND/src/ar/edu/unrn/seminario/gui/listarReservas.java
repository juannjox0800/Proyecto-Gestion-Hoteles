package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.dto.ReservaDTO;
import ar.edu.unrn.seminario.exception.AccesoDatosException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.FormatoInvalidoException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;

public class listarReservas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					IApi api = new MemoryApi();
					listarReservas frame = new listarReservas(api);
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
	public listarReservas(IApi api) {
		setTitle("Lista de Reservas");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 711, 362);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 24, 695, 184);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"N\u00B0 Reserva", "Cliente", "Habitaciones", "Plan", "Entrada", "Salida", "Costo total"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(6).setResizable(false);

			recargarTabla(api);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(228, 219, 89, 23);
		contentPane.add(btnModificar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(340, 219, 89, 23);
		contentPane.add(btnEliminar);

		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(596, 289, 89, 23);
		contentPane.add(btnSalir);



		btnModificar.addActionListener(new ActionListener() { // MODIFICAR
			@Override
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = table.getSelectedRow();

				if (filaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione una reserva de la tabla para modificar", "Atencion", JOptionPane.WARNING_MESSAGE);
					return;
				}

				int numero =  Integer.parseInt(table.getValueAt(filaSeleccionada, 0).toString());
				try {
				ReservaDTO reserva = api.obtenerReserva(numero);
				RealizarReserva ventana = new RealizarReserva(api, reserva);

				ventana.addWindowListener(new WindowAdapter () {
					@Override
					public void windowClosed (WindowEvent w) {
						recargarTabla(api);
					}
				});
				ventana.setVisible(true);

				} catch (EntidadNoEncontradaException | FormatoInvalidoException | AccesoDatosException e1) {
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error al registrar la reserva", JOptionPane.ERROR_MESSAGE);
			}
			}
		});


		btnEliminar.addActionListener(e -> {
			
			try {
				int filaSeleccionada = table.getSelectedRow();

				if (filaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione una reserva de la tabla para eliminar", "Atencion", JOptionPane.WARNING_MESSAGE);
					return;
				}

				int numero =  Integer.parseInt(table.getValueAt(filaSeleccionada, 0).toString());


				int confirmacion = JOptionPane.showConfirmDialog(null, "Esta seguro de que desea eliminar la reserva N.° " + numero + "?", "Confirmar Eleccion", JOptionPane.YES_NO_OPTION);
				if (confirmacion == JOptionPane.YES_OPTION) {
					api.eliminarReserva(numero);

					JOptionPane.showMessageDialog(null, "Reserva eliminada correctamente");
					recargarTabla(api);
				}
			} catch (EntidadNoEncontradaException | ValorInvalidoException e2) {
				JOptionPane.showMessageDialog(null, "Error: " + e2.getMessage(), "Error al registrar la reserva", JOptionPane.ERROR_MESSAGE);
			}
		});



		btnSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});



	}
	private void recargarTabla(IApi api) {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yy");
		
		List<ReservaDTO> reservasDisponibles = api.obtenerListaReserva();
		for (ReservaDTO reserva : reservasDisponibles) {
			if(!reserva.isEstadoCancelacion()) {
				modelo.addRow(new Object[] {
						reserva.getNumeroReserva(), 
						reserva.getCliente().getNombre() + " " + reserva.getCliente().getApellido(),
						reserva.getHabitaciones().stream()
											.map(habitacion -> String.valueOf(habitacion.getNumero()))
											.collect(Collectors.joining(" , ")),
						reserva.getPlan().getNombre(),
						reserva.getFechaEntrada().format(formato), reserva.getFechaSalida().format(formato),
						"$" + reserva.getCostoTotal()
				});
			}
			}

		}
}
