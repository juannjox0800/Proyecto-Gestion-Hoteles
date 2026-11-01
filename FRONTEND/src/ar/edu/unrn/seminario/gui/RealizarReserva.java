package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.dto.ClienteDTO;
import ar.edu.unrn.seminario.dto.HabitacionDTO;
import ar.edu.unrn.seminario.dto.PagoDTO;
import ar.edu.unrn.seminario.dto.PlanDTO;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.FechaInvalidaException;
import ar.edu.unrn.seminario.exception.FormatoInvalidoException;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.awt.event.ActionEvent;

public class RealizarReserva extends JFrame {
	private IApi api;
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
	
	boolean señaCalculada;
	
	private List<HabitacionDTO> habitacionesSeleccionadas = new ArrayList<>();
	// plaDTO esta dentro del aceptar
	private ClienteDTO clienteSeleccionado;
	private LocalDate fechaEntrada;
	private LocalDate fechaSalida;
	// pagoDTO pagoSeña esta dentro del aceptar
	
	private List<HabitacionDTO> habitacionesDisponibles = new ArrayList<>();
	
	private int precioSeña;
	
		
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new MemoryApi();
					RealizarReserva frame = new RealizarReserva(api);
					
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
	public RealizarReserva(IApi api) {
		this.api = api;
		setTitle("Realizar Reserva");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
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
		btnBuscarCliente.addActionListener(new ActionListener() {
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
		
		btnBuscarCliente.setBounds(307, 32, 89, 23);
		panel.add(btnBuscarCliente);
		
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
		
		JButton btnNewButton_3 = new JButton("Buscar disponibilidad");
		btnNewButton_3.addActionListener(new ActionListener() {
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
			    		
			   }

			});
		
		btnNewButton_3.setBounds(750, 32, 183, 23);
		panel.add(btnNewButton_3);
		
		JLabel lblNewLabel_2 = new JLabel("Habitación/es disponibles");
		lblNewLabel_2.setBounds(445, 120, 488, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_6 = new JLabel("Plan solicitado");
		lblNewLabel_6.setBounds(30, 290, 86, 14);
		panel.add(lblNewLabel_6);
		
		cbPlan = new JComboBox();
		cbPlan.setBounds(145, 286, 117, 22);
		panel.add(cbPlan);
		cbPlan.setModel(new DefaultComboBoxModel(new String[] {"Estándar", "Premium"}));
		
		JButton btnAceptar = new JButton("Aceptar");    
		btnAceptar.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				
				if (clienteSeleccionado == null || fechaEntrada == null || fechaSalida == null || habitacionesSeleccionadas.isEmpty() ||
				    cbPlan.getSelectedItem() == null || cbMetodoPago.getSelectedItem() == null || !señaCalculada) {
					
					JOptionPane.showMessageDialog(RealizarReserva.this, "Debe completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
					return;
					}
				
				String tipoPago = cbMetodoPago.getSelectedItem().toString();
				LocalDate fechaPago = LocalDate.now();

				PagoDTO pagoSeña = new PagoDTO (tipoPago, precioSeña, fechaPago, 5);    //crea el pago, verificar ID
				
				String nombrePlan = cbPlan.getSelectedItem().toString();              //obtiene el nombre del plan
				
				try {
				PlanDTO plan = api.buscarPlan(nombrePlan);    // memoryApi busca el PlanDTO con el String del nombre
				int costoTotal = precioSeña * 2;
				api.realizarReserva(habitacionesSeleccionadas, plan, clienteSeleccionado, fechaEntrada, fechaSalida, pagoSeña, costoTotal);
				} catch (EntidadNoEncontradaException e3) {
					 JOptionPane.showMessageDialog (RealizarReserva.this, e3.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
					 }
				
				limpiarCampos();
				dispose(); // cierra la ventana 
			}
		});
		
		btnAceptar.setBounds(32, 387, 89, 23);
		panel.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				dispose();
				
			}
		});
		btnCancelar.setBounds(154, 387, 89, 23);
		panel.add(btnCancelar);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(445, 145, 488, 144);
		panel.add(scrollPane_1);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
				"N\u00FAmero", "Tipo de habitaci\u00F3n", "Tipo de camas", "Cantidad de camas", "Precio", "Seleccionar"
			}
		) {
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, true 
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
			
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
		
		JButton btnCalcular = new JButton("Calcular");   
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel modelo = (DefaultTableModel) table.getModel();
				señaCalculada = false;
				
				//recupero las habitaciones seleccionadas
				habitacionesSeleccionadas = IntStream.range(0, modelo.getRowCount())
			            .filter(i -> Boolean.TRUE.equals(modelo.getValueAt(i, 5)))
			            .mapToObj(i -> habitacionesDisponibles.get(i))
			            .collect(Collectors.toList());
				
				// verifico que se haya seleccionado habitacion/es
				if (habitacionesSeleccionadas.isEmpty()) {
					 JOptionPane.showMessageDialog(RealizarReserva.this,"Debe seleccionar una habitación", "Advertencia", JOptionPane.WARNING_MESSAGE);
					 return;
				 }
				
				// sumo el precio de todas las habitaciones
				int precioHabitaciones = habitacionesSeleccionadas.stream() .mapToInt (habitacion -> habitacion.getCosto()) .sum();
				precioSeña = api.calcularSeña(precioHabitaciones, fechaEntrada, fechaSalida);
				
				señaCalculada = true;
				lbSeña.setText(String.valueOf(precioSeña));
			}
		});
		btnCalcular.setBounds(746, 329, 89, 23);
		panel.add(btnCalcular);

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
    señaCalculada = false;
    lbSeña.setText("");

}
}
