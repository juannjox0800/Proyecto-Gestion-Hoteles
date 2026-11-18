package ar.edu.unrn.seminario.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ClienteDTO;
import ar.edu.unrn.seminario.dto.HabitacionDTO;
import ar.edu.unrn.seminario.dto.PagoDTO;
import ar.edu.unrn.seminario.dto.PlanDTO;
import ar.edu.unrn.seminario.dto.ReservaDTO;
import ar.edu.unrn.seminario.exception.AccesoDatosException;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.FechaInvalidaException;
import ar.edu.unrn.seminario.exception.FormatoInvalidoException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Font;

public class RealizarReserva extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfFechaEntrada;
	private JTextField tfFechaSalida;
	private JTextField tfCliente;
	private JTable table;
	private JLabel lblNombreCliente;
	private JLabel lblContactoCliente;
	private JComboBox<String> cbPlan;
	private JLabel lbSeña;
	private JLabel lbCostoTotal;
	private JComboBox<String> cbMetodoPago;

	private List<HabitacionDTO> habitacionesSeleccionadas = new ArrayList<>();
	private ClienteDTO clienteSeleccionado;
	private LocalDate fechaEntrada;
	private LocalDate fechaSalida;
	private List<HabitacionDTO> habitacionesDisponibles = new ArrayList<>();
	private float costoReserva;
	private float precioSeña;
	private PlanDTO plan;
	private ReservaDTO reserva;
	private IApi api;
	private boolean enCurso;

	public RealizarReserva(IApi api, ReservaDTO reserva) {
		this.reserva = reserva;
		this.api = api;

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 735, 531);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnAceptar = new JButton("Guardar");
		btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnAceptar.setBounds(593, 456, 102, 23);
		contentPane.add(btnAceptar);

		JButton btnCancelar = new JButton("Salir");
		btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnCancelar.setBounds(479, 456, 102, 23);
		contentPane.add(btnCancelar);

		JPanel panelCliente = new JPanel();
		panelCliente.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Datos del Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCliente.setBounds(10, 11, 357, 107);
		contentPane.add(panelCliente);
		panelCliente.setLayout(null);

		JLabel lblNewLabel_5 = new JLabel("DNI:");
		lblNewLabel_5.setBounds(10, 23, 46, 20);
		panelCliente.add(lblNewLabel_5);

		JLabel lblNewLabel_7 = new JLabel("Nombre:");
		lblNewLabel_7.setBounds(10, 54, 60, 14);
		panelCliente.add(lblNewLabel_7);

		lblNombreCliente = new JLabel("-");
		lblNombreCliente.setBounds(80, 54, 133, 14);
		panelCliente.add(lblNombreCliente);

		lblContactoCliente = new JLabel("-");
		lblContactoCliente.setBounds(80, 79, 133, 14);
		panelCliente.add(lblContactoCliente);

		JLabel lblNewLabel_9 = new JLabel("Contacto:");
		lblNewLabel_9.setBounds(10, 79, 60, 14);
		panelCliente.add(lblNewLabel_9);

		tfCliente = new JTextField();
		tfCliente.setBounds(80, 24, 147, 20);
		panelCliente.add(tfCliente);
		tfCliente.setColumns(10);

		JButton btnBuscarCliente = new JButton("Buscar");
		btnBuscarCliente.setBounds(237, 23, 89, 23);
		panelCliente.add(btnBuscarCliente);

		JPanel panelPlan = new JPanel();
		panelPlan.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Plan de Estadia", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelPlan.setBounds(377, 11, 332, 107);
		contentPane.add(panelPlan);
		panelPlan.setLayout(null);

		cbPlan = new JComboBox<>();
		cbPlan.setBounds(117, 44, 183, 22);
		panelPlan.add(cbPlan);

		JLabel lblNewLabel_6 = new JLabel("Plan solicitado:");
		lblNewLabel_6.setBounds(21, 48, 86, 14);
		panelPlan.add(lblNewLabel_6);

		JPanel panelHabitaciones = new JPanel();
		panelHabitaciones.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Seleccion de Habitaciones", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelHabitaciones.setBounds(10, 122, 699, 235);
		contentPane.add(panelHabitaciones);
		panelHabitaciones.setLayout(null);

		JLabel lblNewLabel = new JLabel("Fecha de Entrada:");
		lblNewLabel.setBounds(10, 24, 136, 20);
		panelHabitaciones.add(lblNewLabel);

		tfFechaEntrada = new JTextField();
		tfFechaEntrada.setBounds(156, 24, 117, 20);
		panelHabitaciones.add(tfFechaEntrada);
		tfFechaEntrada.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Fecha de Salida:");
		lblNewLabel_1.setBounds(283, 24, 110, 20);
		panelHabitaciones.add(lblNewLabel_1);

		tfFechaSalida = new JTextField();
		tfFechaSalida.setBounds(403, 24, 117, 20);
		panelHabitaciones.add(tfFechaSalida);
		tfFechaSalida.setColumns(10);

		JButton btnBuscarDisponibilidad = new JButton("Buscar disponibilidad");
		btnBuscarDisponibilidad.setBounds(530, 23, 159, 23);
		panelHabitaciones.add(btnBuscarDisponibilidad);

		JLabel lblNewLabel_2 = new JLabel("Habitación/es disponibles:");
		lblNewLabel_2.setBounds(10, 55, 488, 14);
		panelHabitaciones.add(lblNewLabel_2);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 80, 679, 144);
		panelHabitaciones.add(scrollPane_1);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "N.\u00B0", "Tipo", "Tipo de camas", "Cantidad de camas", "Precio", " Seleccion" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, true };
			
			@Override
			public boolean isCellEditable(int row, int column) {
				if (enCurso) {
					return false;
				}
				return columnEditables[column];
			}
			
			@Override
			public Class getColumnClass (int column){
				if (column == 5) {
					return Boolean.class;
				}
				return Object.class;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(55);
		table.getColumnModel().getColumn(0).setMinWidth(18);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(144);
		table.getColumnModel().getColumn(3).setPreferredWidth(111);
		table.getColumnModel().getColumn(4).setPreferredWidth(63);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(5).setPreferredWidth(67);
		scrollPane_1.setViewportView(table);

		JPanel panelPago = new JPanel();
		panelPago.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Resumen y Pago de Se\u00F1a", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelPago.setBounds(10, 357, 699, 88);
		contentPane.add(panelPago);
		panelPago.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("Se debe abonar un 50% del costo total para realizar una seña");
		lblNewLabel_4.setBounds(10, 21, 504, 18);
		panelPago.add(lblNewLabel_4);

		lbCostoTotal = new JLabel("-");
		lbCostoTotal.setBounds(92, 48, 98, 23);
		panelPago.add(lbCostoTotal);

		JLabel lblNewLabel_8_1 = new JLabel("Costo total:");
		lblNewLabel_8_1.setBounds(10, 48, 72, 23);
		panelPago.add(lblNewLabel_8_1);

		JLabel lblNewLabel_8 = new JLabel("Costo de la seña:");
		lblNewLabel_8.setBounds(200, 50, 112, 23);
		panelPago.add(lblNewLabel_8);

		lbSeña = new JLabel("-");
		lbSeña.setBounds(313, 50, 98, 23);
		panelPago.add(lbSeña);

		JLabel lblNewLabel_3 = new JLabel("Metodo de pago:");
		lblNewLabel_3.setBounds(457, 49, 98, 21);
		panelPago.add(lblNewLabel_3);

		cbMetodoPago = new JComboBox();
		cbMetodoPago.setBounds(567, 49, 112, 22);
		panelPago.add(cbMetodoPago);
		cbMetodoPago.setModel(new DefaultComboBoxModel(new String[] { "Efectivo", "Tarjeta", "Transferencia" }));

		table.getModel().addTableModelListener(e -> {
			if (e.getColumn() == 5) {
				recalcularCosto();
			}
		});

		btnBuscarDisponibilidad.addActionListener(e -> {

			try {
				fechaEntrada = LocalDate.parse(tfFechaEntrada.getText(), DateTimeFormatter.ofPattern("dd/MM/yy"));
				fechaSalida = LocalDate.parse(tfFechaSalida.getText(), DateTimeFormatter.ofPattern("dd/MM/yy"));
			} catch (DateTimeParseException e2) {
				JOptionPane.showMessageDialog(RealizarReserva.this, "Ingrese las fechas con el formato dd/MM/yy",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			List<Integer> numerosTildados = habitacionesSeleccionadas.stream().map(HabitacionDTO::getNumero)
					.collect(Collectors.toList());

			try {
				habitacionesDisponibles = api.buscarDisponibilidad(fechaEntrada, fechaSalida);

				if (reserva != null) {
					List<HabitacionDTO> libresAntes = fechaEntrada.isBefore(reserva.getFechaEntrada())
							? api.buscarDisponibilidad(fechaEntrada, reserva.getFechaEntrada())
							: null;

					List<HabitacionDTO> libresDespues = fechaSalida.isAfter(reserva.getFechaSalida())
							? api.buscarDisponibilidad(reserva.getFechaSalida(), fechaSalida)
							: null;

					for (HabitacionDTO habOriginal : reserva.getHabitaciones()) {
						int num = habOriginal.getNumero();

						boolean yaEnLista = habitacionesDisponibles.stream()
								.anyMatch(h -> h.getNumero() == habOriginal.getNumero());
						boolean okAntes = (libresAntes == null)
								|| libresAntes.stream().anyMatch(h -> h.getNumero() == num);
						boolean okDespues = (libresDespues == null)
								|| libresDespues.stream().anyMatch(h -> h.getNumero() == num);

						if (!yaEnLista && okAntes && okDespues) {
							habitacionesDisponibles.add(habOriginal);
						}
					}
				}
			} catch (FechaInvalidaException | AccesoDatosException e2) {
				JOptionPane.showMessageDialog(RealizarReserva.this, e2.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			DefaultTableModel modelo = (DefaultTableModel) table.getModel();
			modelo.setRowCount(0); // para limpiar la tabla

			for (HabitacionDTO habitacion : habitacionesDisponibles) {
				modelo.addRow(new Object[] { habitacion.getNumero(), habitacion.getTipoHabitacion(),

						habitacion.getCamas().stream().map(cama -> cama.getTipo()).collect(Collectors.joining(", ")),

						habitacion.getCamas().size(), habitacion.getCosto(),
						numerosTildados.contains(habitacion.getNumero()) });
			}
			recalcularCosto();
		});
		cbPlan.removeAllItems();

		cbPlan.addActionListener(e -> {
			recalcularCosto();
		});

		btnBuscarCliente.addActionListener(e -> {

			try {
				int dni = Integer.parseInt(tfCliente.getText());
				clienteSeleccionado = api.obtenerCliente(dni);

				// si no tira ninguna excepcion, carga el nombre y el apellido del cliente en
				// los lbl
				if (clienteSeleccionado != null) {
					lblNombreCliente.setText(clienteSeleccionado.getNombre());
					lblContactoCliente.setText(clienteSeleccionado.getContacto());
				}
			}

			catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(RealizarReserva.this, "El DNI debe ser correcto", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

			catch (FormatoInvalidoException | EntidadNoEncontradaException e1) {
				JOptionPane.showMessageDialog(RealizarReserva.this, e1.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		List<PlanDTO> planes = api.obtenerListaPlan();
		for (PlanDTO plan : planes) {
			cbPlan.addItem(plan.getNombre());
		}

		// como se muestra la ventana

		if (reserva != null) {
			setTitle("Modificar Reserva");

			tfCliente.setText(String.valueOf(reserva.getCliente().getDni()));
			tfCliente.setEnabled(false);
			lblNombreCliente.setText(reserva.getCliente().getNombre());
			lblContactoCliente.setText(reserva.getCliente().getContacto());
			btnBuscarCliente.setEnabled(false);

			cbPlan.setSelectedItem(reserva.getPlan().getNombre());
			cbMetodoPago.setEnabled(false);
			cbMetodoPago.setSelectedItem(reserva.getPagoSeña().getTipoPago());

			fechaEntrada = reserva.getFechaEntrada();
			fechaSalida = reserva.getFechaSalida();

			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yy");
			tfFechaEntrada.setText(fechaEntrada.format(formato));
			tfFechaSalida.setText(fechaSalida.format(formato));

			lbSeña.setText("$" + String.valueOf(reserva.getPagoSeña().getMonto()));
			lbCostoTotal.setText("$" + String.valueOf(reserva.getCostoTotal()));

			DefaultTableModel modelo = (DefaultTableModel) table.getModel();

			costoReserva = reserva.getCostoTotal();
			plan = reserva.getPlan();

			// recargar la tabla con las habitaciones del dto reserva
			habitacionesDisponibles = new ArrayList<HabitacionDTO>(reserva.getHabitaciones());
			habitacionesSeleccionadas = new ArrayList<HabitacionDTO>(reserva.getHabitaciones());

			for (HabitacionDTO habitacion : habitacionesDisponibles) {
				modelo.addRow(new Object[] { habitacion.getNumero(), habitacion.getTipoHabitacion(),
						habitacion.getCamas().stream().map(cama -> cama.getTipo()).collect(Collectors.joining(", ")),
						habitacion.getCamas().size(), habitacion.getCosto(), true });
			}

			LocalDate hoy = LocalDate.now();

			if (!hoy.isBefore(fechaEntrada) && hoy.isBefore(fechaSalida)) {
				// transcurre solo plan
				enCurso = true;
				tfFechaEntrada.setEnabled(false);
				tfFechaSalida.setEnabled(false);
				btnBuscarDisponibilidad.setEnabled(false);

				cbPlan.removeAllItems();
				float costoActual = reserva.getPlan().getCosto();
				for (PlanDTO p : planes) {
					if (p.getNombre().equals(reserva.getPlan().getNombre()) || p.getCosto() > costoActual) {
						cbPlan.addItem(p.getNombre());
					}
				}
				cbPlan.setSelectedItem(reserva.getPlan().getNombre());

			} else if (!hoy.isBefore(fechaSalida)) {
				// ya paso
				JOptionPane.showMessageDialog(this, "No se puede modificar una reserva que ya finalizo.",
						"Reserva no modificable", JOptionPane.WARNING_MESSAGE);
				dispose();
			}
		} else {
			setTitle("Realizar Reserva");
		}

		btnAceptar.addActionListener(e -> {
			String nombrePlan = (String) cbPlan.getSelectedItem();

			try {
				PlanDTO planElegido;

				if (reserva != null && reserva.getPlan().getNombre().equals(nombrePlan)) {
					planElegido = reserva.getPlan();
				} else {
					planElegido = api.obtenerPlan(nombrePlan);
				}

				if (reserva != null) {
					LocalDate hoy = LocalDate.now();

					if (hoy.isBefore(reserva.getFechaEntrada())) {
						if (fechaEntrada == null || fechaSalida == null || habitacionesSeleccionadas.isEmpty()) {
							JOptionPane.showMessageDialog(RealizarReserva.this, "Debe completar todos los campos",
									"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						reserva.setPlan(planElegido);
						reserva.setHabitaciones(habitacionesSeleccionadas);
						reserva.setFechaEntrada(fechaEntrada);
						reserva.setFechaSalida(fechaSalida);
						reserva.setCostoTotal(costoReserva);

					} else if (hoy.isBefore(reserva.getFechaSalida())) {
						// reserva en curso
						reserva.setPlan(planElegido);
						reserva.setCostoTotal(costoReserva);

					} else {
						JOptionPane.showMessageDialog(RealizarReserva.this,
								"No se puede modificar una reserva finalizada.", "Reserva no modificable",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					api.modificarReserva(reserva);

				} else {

					if (clienteSeleccionado == null || fechaEntrada == null || fechaSalida == null
							|| habitacionesSeleccionadas.isEmpty()) {
						JOptionPane.showMessageDialog(RealizarReserva.this, "Debe completar todos los campos", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					String tipoPago = cbMetodoPago.getSelectedItem().toString();
					LocalDate fechaPago = LocalDate.now();

					PagoDTO pagoSeña = new PagoDTO(tipoPago, precioSeña, fechaPago);

					api.realizarReserva(habitacionesSeleccionadas, planElegido, clienteSeleccionado, fechaEntrada,
							fechaSalida, pagoSeña, costoReserva);
				}

				JOptionPane.showMessageDialog(RealizarReserva.this,
						reserva != null ? "Reserva modificada correctamente" : "Reserva registrada correctamente",
						"Éxito", JOptionPane.INFORMATION_MESSAGE);

				limpiarCampos();
				dispose();

			} catch (EntidadNoEncontradaException | DatoVacioException | ValorInvalidoException
					| EntidadDuplicadaException e1) {

				JOptionPane.showMessageDialog(RealizarReserva.this, e1.getMessage(), "Advertencia",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		btnCancelar.addActionListener(e -> {
			dispose();

		});

	}

	private void recalcularCosto() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();

		habitacionesSeleccionadas = new ArrayList<>();

		for (int i = 0; i < modelo.getRowCount(); i++) {
			if (Boolean.TRUE.equals(modelo.getValueAt(i, 5))) {
				habitacionesSeleccionadas.add(habitacionesDisponibles.get(i));
			}
		}

		if (habitacionesSeleccionadas.isEmpty() || fechaEntrada == null || fechaSalida == null
				|| cbPlan.getSelectedItem() == null) {
			costoReserva = 0;
			lbCostoTotal.setText("$0");
			if (reserva == null) {
				precioSeña = 0;
				lbSeña.setText("$0");
			}
			return;
		}

		try {
			String nombrePlan = cbPlan.getSelectedItem().toString();

			if (reserva != null && reserva.getPlan().getNombre().equals(nombrePlan)) {
				plan = reserva.getPlan();
			} else {
				plan = api.obtenerPlan(nombrePlan);
			}

			costoReserva = api.calcularCostoReserva(habitacionesSeleccionadas, fechaEntrada, fechaSalida, plan);
			lbCostoTotal.setText("$" + String.valueOf(costoReserva));
			if (reserva == null) {
				precioSeña = costoReserva / 2;
				lbSeña.setText("$" + String.valueOf(precioSeña));
			}
		} catch (EntidadNoEncontradaException e) {
			costoReserva = 0;
		}
	}

	private void limpiarCampos() {

		// Campos de texto
		tfCliente.setText("");
		tfFechaEntrada.setText("");
		tfFechaSalida.setText("");

		// Datos del cliente
		clienteSeleccionado = null;
		lblNombreCliente.setText("-");
		lblContactoCliente.setText("-");

		// Fechas
		fechaEntrada = null;
		fechaSalida = null;

		// Habitaciones
		habitacionesSeleccionadas.clear();
		habitacionesDisponibles.clear();

		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);

		// Plan y método de pago
		if (cbPlan.getItemCount() > 0) {
			cbPlan.setSelectedIndex(0);
		}
		cbMetodoPago.setSelectedIndex(0);

		// Seña
		precioSeña = 0;
		lbSeña.setText("-");

		lbCostoTotal.setText("-");
	}
}
