package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.FechaInvalidaException;
import ar.edu.unrn.seminario.exception.FormatoInvalidoException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;

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
	private JComboBox<String> cbMetodoPago;

	private List<HabitacionDTO> habitacionesSeleccionadas = new ArrayList<>();
	private ClienteDTO clienteSeleccionado;
	private LocalDate fechaEntrada;
	private LocalDate fechaSalida;
	private List<HabitacionDTO> habitacionesDisponibles = new ArrayList<>();
	private int costoReserva;
	private int precioSeña;
	private PlanDTO plan;
	private ReservaDTO reserva;
	private IApi api;
	private boolean enCurso;



	public RealizarReserva(IApi api, ReservaDTO reserva) {
		this.reserva = reserva;
		this.api = api;

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 1000, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();

		panel.setPreferredSize(new Dimension(950, 520));

		//esto inserta el panel en el scroll principal
		scrollPane.setViewportView(panel);
		panel.setLayout(null);


		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(445, 145, 488, 144);
		panel.add(scrollPane_1);

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
				"N.°", "Tipo", "Tipo de camas", "Cantidad de camas", "Precio", " "
			}
		) {
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, true
			};
			@Override
			public boolean isCellEditable(int row, int column) {
			    if (enCurso) {
			        return false;
			    }

			    return columnEditables[column];
			}

			@Override
			public Class getColumnClass(int column) {   // recibe una columna si es la 5 (sleccionar) devuelve boolean.class, porque puede ser true o false
			    if (column == 5) {
			        return Boolean.class;
			    }
			    return Object.class;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(55);
		table.getColumnModel().getColumn(0).setMinWidth(18);
		table.getColumnModel().getColumn(1).setPreferredWidth(115);
		table.getColumnModel().getColumn(2).setPreferredWidth(115);
		table.getColumnModel().getColumn(3).setPreferredWidth(125);
		table.getColumnModel().getColumn(5).setResizable(false);
		scrollPane_1.setViewportView(table);

		JLabel lblNewLabel_12 = new JLabel("Agregar habitación");
		lblNewLabel_12.setBounds(454, 11, 111, 14);
		panel.add(lblNewLabel_12);

		JLabel lblNewLabel_14 = new JLabel("Agregar plan");
		lblNewLabel_14.setBounds(10, 265, 90, 14);
		panel.add(lblNewLabel_14);

		JLabel lblNewLabel_3 = new JLabel("Metodo de pago ");
		lblNewLabel_3.setBounds(511, 376, 111, 14);
		panel.add(lblNewLabel_3);

		cbMetodoPago = new JComboBox();
		cbMetodoPago.setModel(new DefaultComboBoxModel(new String[] {"Efectivo", "Tarjeta", "Transferencia"}));
		cbMetodoPago.setBounds(632, 372, 111, 22);
		panel.add(cbMetodoPago);

		JLabel lblNewLabel_4 = new JLabel("Se debe abonar un 50% del costo total para realizar una seña");
		lblNewLabel_4.setBounds(511, 300, 338, 18);
		panel.add(lblNewLabel_4);

		JLabel lblNewLabel_8 = new JLabel("- $");
		lblNewLabel_8.setBounds(567, 329, 25, 23);
		panel.add(lblNewLabel_8);

		lbSeña = new JLabel("");
		lbSeña.setBounds(593, 333, 98, 19);
		panel.add(lbSeña);

		JLabel lblNewLabel_11 = new JLabel("Agregar cliente:");
		lblNewLabel_11.setBounds(10, 11, 111, 14);
		panel.add(lblNewLabel_11);

		JLabel lblNewLabel_5 = new JLabel("Cliente");
		lblNewLabel_5.setBounds(30, 36, 46, 14);
		panel.add(lblNewLabel_5);

		tfCliente = new JTextField();
		tfCliente.setBounds(145, 33, 117, 20);
		panel.add(tfCliente);
		tfCliente.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("Nombre:");
		lblNewLabel_7.setBounds(40, 61, 60, 14);
		panel.add(lblNewLabel_7);

		JLabel lblNewLabel_9 = new JLabel("Contacto:");
		lblNewLabel_9.setBounds(40, 86, 60, 14);
		panel.add(lblNewLabel_9);

		lblNombreCliente = new JLabel("-");
		lblNombreCliente.setBounds(110, 61, 133, 14);
		panel.add(lblNombreCliente);

		lblContactoCliente = new JLabel("-");
		lblContactoCliente.setBounds(110, 86, 133, 14);
		panel.add(lblContactoCliente);

		JButton btnBuscarCliente = new JButton("Buscar");
		btnBuscarCliente.setBounds(307, 32, 89, 23);
		panel.add(btnBuscarCliente);

		JButton btnBuscarDisponibilidad = new JButton("Buscar disponibilidad");
		btnBuscarDisponibilidad.setBounds(750, 32, 183, 23);
		panel.add(btnBuscarDisponibilidad);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(32, 387, 89, 23);
		panel.add(btnAceptar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(154, 387, 89, 23);
		panel.add(btnCancelar);

		JLabel lblNewLabel = new JLabel("Fecha de entrada");
		lblNewLabel.setBounds(474, 36, 109, 14);
		panel.add(lblNewLabel);

		tfFechaEntrada = new JTextField();
		tfFechaEntrada.setBounds(593, 33, 117, 20);
		panel.add(tfFechaEntrada);
		tfFechaEntrada.setColumns(10);



		JLabel lblNewLabel_1 = new JLabel("Fecha de salida");
		lblNewLabel_1.setBounds(474, 61, 104, 14);
		panel.add(lblNewLabel_1);

		tfFechaSalida = new JTextField();
		tfFechaSalida.setBounds(593, 58, 117, 20);
		panel.add(tfFechaSalida);
		tfFechaSalida.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Habitación/es disponibles");
		lblNewLabel_2.setBounds(445, 120, 488, 14);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_6 = new JLabel("Plan solicitado");
		lblNewLabel_6.setBounds(30, 290, 86, 14);
		panel.add(lblNewLabel_6);

		cbPlan = new JComboBox<>();
		cbPlan.setBounds(145, 286, 117, 22);
		panel.add(cbPlan);

		List<PlanDTO> planes = api.obtenerListaPlan();
		cbPlan.removeAllItems();
		for (PlanDTO plan : planes) {
		    cbPlan.addItem(plan.getNombre());
		}

		//como se muestra la ventana

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
			tfFechaEntrada.setEnabled(true);
			tfFechaSalida.setEnabled(true);

			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yy");
			tfFechaEntrada.setText(fechaEntrada.format(formato));
			tfFechaSalida.setText(fechaSalida.format(formato));

			lbSeña.setText(String.valueOf(reserva.getPagoSeña().getMonto()));

			DefaultTableModel modelo = (DefaultTableModel) table.getModel();
			costoReserva = reserva.getCostoTotal();		
			plan = reserva.getPlan();
			
			//recargar la tabla con las habitaciones del dto reserva

			habitacionesDisponibles = new ArrayList<HabitacionDTO>(reserva.getHabitaciones());
			habitacionesSeleccionadas = new ArrayList<HabitacionDTO>(reserva.getHabitaciones());
			
			for (HabitacionDTO habitacion : habitacionesDisponibles) {
				modelo.addRow(new Object[] {
						habitacion.getNumero(),
						habitacion.getTipoHabitacion(),

						habitacion.getCamas().stream() .map (cama -> cama.getTipo())
						.collect(Collectors.joining(", ")),

						habitacion.getCamas().size(),
						habitacion.getCosto(),
						true

						});
			}
			
			LocalDate hoy = LocalDate.now();
			// no empezo
			if (!hoy.isBefore(fechaEntrada) && hoy.isBefore(fechaSalida)) {
				// transcurre solo plan
				enCurso = true;
				tfFechaEntrada.setEnabled(false);
				tfFechaSalida.setEnabled(false);
				btnBuscarDisponibilidad.setEnabled(false);
				
				cbPlan.removeAllItems();
				int costoActual = reserva.getPlan().getCosto();
				for (PlanDTO p : planes) {
					if (p.getNombre().equals(reserva.getPlan().getNombre()) || p.getCosto() > costoActual) {
						cbPlan.addItem(p.getNombre());
						cbPlan.setSelectedItem(reserva.getPlan().getNombre());
					}
				}
				
			} else if (!hoy.isBefore(fechaSalida)) {
				// ya paso
				JOptionPane.showMessageDialog(this, "No se puede modificar una reserva que ya finalizo.", "Reserva no modificable", JOptionPane.WARNING_MESSAGE);
				dispose();
			}
		} else {
			setTitle("Realizar Reserva");
		}

		table.getModel().addTableModelListener(e -> {
			if (e.getColumn() == 5) {
				recalcularCosto();
			}
		});
		
		cbPlan.addActionListener(e -> {
			recalcularCosto();
		});
		
		btnBuscarCliente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					int dni = Integer.parseInt(tfCliente.getText());
					clienteSeleccionado = api.obtenerCliente(dni);

					//si no tira ninguna excepcion, carga el nombre y el apellido del cliente en los lbl
					if (clienteSeleccionado != null) {
						lblNombreCliente.setText(clienteSeleccionado.getNombre());
						lblContactoCliente.setText(clienteSeleccionado.getContacto());
						}
					}

				catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(RealizarReserva.this, "El DNI debe ser correcto", "Error", JOptionPane.ERROR_MESSAGE);
					}

				catch (FormatoInvalidoException e1) {
					JOptionPane.showMessageDialog(RealizarReserva.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}

				catch (EntidadNoEncontradaException e1) {
					JOptionPane.showMessageDialog(RealizarReserva.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				    }

				}
		});



		btnBuscarDisponibilidad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			try {
			fechaEntrada = LocalDate.parse(tfFechaEntrada.getText(), DateTimeFormatter.ofPattern("dd/MM/yy"));
			fechaSalida = LocalDate.parse(tfFechaSalida.getText(),  DateTimeFormatter.ofPattern("dd/MM/yy"));
			}
			catch (DateTimeParseException e2) {
				JOptionPane.showMessageDialog(RealizarReserva.this, "Ingrese las fechas con el formato dd/MM/yy", "Error", JOptionPane.ERROR_MESSAGE);
				return;
				}

			try {
			habitacionesDisponibles =  api.buscarDisponibilidad(fechaEntrada, fechaSalida);
			}
			catch (FechaInvalidaException e2) {
				JOptionPane.showMessageDialog(RealizarReserva.this, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
				}

			DefaultTableModel modelo = (DefaultTableModel) table.getModel();
			modelo.setRowCount(0);                      // para limpiar la tabla

			for (HabitacionDTO habitacion : habitacionesDisponibles) {
				modelo.addRow(new Object[] {
						habitacion.getNumero(),
						habitacion.getTipoHabitacion(),

						habitacion.getCamas().stream() .map (cama -> cama.getTipo())
						.collect(Collectors.joining(", ")),

						habitacion.getCamas().size(),
						habitacion.getCosto(),
						false                           // la columna 5 (seleccionar) comienza en false
					});
				}
			recalcularCosto();
			}
		});





	btnAceptar.addActionListener(new ActionListener() {
	    @Override
		public void actionPerformed(ActionEvent e) {
	
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
	                	if ( fechaEntrada == null || fechaSalida == null || habitacionesSeleccionadas.isEmpty()) {
	                    	JOptionPane.showMessageDialog(RealizarReserva.this, "Debe completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
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
	                    JOptionPane.showMessageDialog(RealizarReserva.this, "No se puede modificar una reserva finalizada.", "Reserva no modificable", JOptionPane.WARNING_MESSAGE);
	                    return;
	                }
	
	                api.modificarReserva(reserva);
	
	            } else {
	
	            	if (clienteSeleccionado == null || fechaEntrada == null || fechaSalida == null || habitacionesSeleccionadas.isEmpty()) {
	                	JOptionPane.showMessageDialog(RealizarReserva.this, "Debe completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	
	                String tipoPago = cbMetodoPago.getSelectedItem().toString();
	                LocalDate fechaPago = LocalDate.now();
	
	                PagoDTO pagoSeña = new PagoDTO(tipoPago, precioSeña, fechaPago);
	
	                api.realizarReserva(habitacionesSeleccionadas, planElegido, clienteSeleccionado, fechaEntrada, fechaSalida, pagoSeña, costoReserva);
	            }
	
	
	            JOptionPane.showMessageDialog(RealizarReserva.this, reserva != null ? "Reserva modificada correctamente" : "Reserva registrada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	
	            limpiarCampos();
	            dispose();
	
	        } catch (EntidadNoEncontradaException | DatoVacioException  | ValorInvalidoException e1) {
	
	            JOptionPane.showMessageDialog(RealizarReserva.this, e1.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
	        }
	    }
	});



		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				dispose();

			}
		});

		}


private void recalcularCosto() {
	DefaultTableModel modelo = (DefaultTableModel) table.getModel();
	
	habitacionesSeleccionadas = new ArrayList<>();
	
	for (int i = 0; i<modelo.getRowCount(); i++) {
		if (Boolean.TRUE.equals(modelo.getValueAt(i, 5))) {
			habitacionesSeleccionadas.add(habitacionesDisponibles.get(i));
		}
	}
	
	if (habitacionesSeleccionadas.isEmpty() || fechaEntrada == null || fechaSalida == null || cbPlan.getSelectedItem() == null) {
		costoReserva = 0;
		if (reserva == null) {
			precioSeña = 0;
			lbSeña.setText("0");
		}
		return;
	}
	
	try {
		String nombrePlan = cbPlan.getSelectedItem().toString();
		
		if (reserva != null && reserva.getPlan().getNombre().equals(nombrePlan)) {
			plan = reserva.getPlan();
		} else {
			plan = api.obtenerPlan (nombrePlan);
		}
		
		costoReserva = api.calcularCostoReserva(habitacionesSeleccionadas, fechaEntrada, fechaSalida, plan);
		
		if (reserva == null) {
			precioSeña = costoReserva / 2;
			lbSeña.setText(String.valueOf(precioSeña));
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
    cbPlan.setSelectedIndex(0);
    cbMetodoPago.setSelectedIndex(0);

    // Seña
    precioSeña = 0;
    lbSeña.setText("");

	}
}
