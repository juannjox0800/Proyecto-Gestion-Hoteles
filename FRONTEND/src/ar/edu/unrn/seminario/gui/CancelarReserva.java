package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.dto.ReservaDTO;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.FormatoInvalidoException;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class CancelarReserva extends JFrame {

	private IApi api;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNumeroReserva;          //NO ERAN TEXTFIELD!!!!!!!
	private JLabel lblNumeroHabitaciones;
	private JLabel lblPlan;
	private JLabel lblFechaEntrada;
	private JLabel lblFechaSalida;
	
	private Integer numeroReserva;
	private ReservaDTO reserva;
	private JTextField tfNumeroReserva;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new MemoryApi();
					CancelarReserva frame = new CancelarReserva(api);
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
	public CancelarReserva(IApi api ) {
		this.api = api;
		setTitle("Cancelar Reserva");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(216, 10, 1, 1);
		contentPane.add(panel);
		panel.setLayout(null);
		
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
				numeroReserva = Integer.parseInt(tfNumeroReserva.getText()); 
				reserva = api.obtenerReserva(numeroReserva);
				}
				
				catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(CancelarReserva.this, "El formato no es correcto", "Error", JOptionPane.ERROR_MESSAGE);
					}
				
				catch (FormatoInvalidoException e1) {
					JOptionPane.showMessageDialog(CancelarReserva.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				catch (EntidadNoEncontradaException e1) {
					JOptionPane.showMessageDialog(CancelarReserva.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					
				} finally {
					if (reserva != null) {
						
						//recupero los numeros de cada habitacion de la reserva
						String numeros = reserva.getHabitaciones().stream() .map(habitacion -> String.valueOf(habitacion.getNumero())) .collect(Collectors.joining(", "));
						
						lblNumeroHabitaciones.setText(numeros);
						
						lblPlan.setText(reserva.getPlan().getNombre());
						lblFechaEntrada.setText(reserva.getFechaEntrada().toString());
						lblFechaSalida.setText(reserva.getFechaSalida().toString());
						} 
					// limpio el lblNumeroReserva
					else {
						lblNumeroReserva.setText("");       
						}
				}
				
			}
		});
		btnBuscar.setBounds(319, 21, 89, 21);
		contentPane.add(btnBuscar);
		
		JLabel lblNewLabel = new JLabel("Ingrese el numero de la reserva");
		lblNewLabel.setBounds(10, 19, 182, 20);
		contentPane.add(lblNewLabel);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (numeroReserva == null) {
					JOptionPane.showMessageDialog(CancelarReserva.this, "Debe buscar una reserva", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int opcion = JOptionPane.showConfirmDialog(CancelarReserva.this, "¿Desea cancelar la reserva?", "Confirmarcion", JOptionPane.YES_NO_CANCEL_OPTION);
				
				if (opcion == JOptionPane.YES_OPTION) {
				api.cancelarReserva(numeroReserva);  
				}
				limpiarCampos();
			}
		});
		
		btnAceptar.setBounds(45, 227, 89, 23);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				dispose();
			}
		});
		btnCancelar.setBounds(164, 227, 89, 23);
		contentPane.add(btnCancelar);
		
		JLabel lblNewLabel_1 = new JLabel("Numero de habitaciones ");
		lblNewLabel_1.setBounds(10, 58, 156, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Plan");
		lblNewLabel_2.setBounds(10, 85, 156, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Fecha de entrada");
		lblNewLabel_2_1.setBounds(10, 117, 156, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_3 = new JLabel("Fecha de salida ");
		lblNewLabel_3.setBounds(10, 158, 156, 14);
		contentPane.add(lblNewLabel_3);
		

		
		JLabel lblNumeroHabitaciones = new JLabel("-");
		lblNumeroHabitaciones.setBounds(207, 53, 86, 19);
		contentPane.add(lblNumeroHabitaciones);
		
		lblPlan = new JLabel("-");
		lblPlan.setBounds(207, 85, 86, 19);
		contentPane.add(lblPlan);
		
		lblFechaEntrada = new JLabel("-");
		lblFechaEntrada.setBounds(207, 117, 86, 19);
		contentPane.add(lblFechaEntrada);
		
		lblFechaSalida = new JLabel("-");
		lblFechaSalida.setBounds(207, 158, 86, 19);
		contentPane.add(lblFechaSalida);
		
		tfNumeroReserva = new JTextField();
		tfNumeroReserva.setBounds(207, 22, 86, 20);
		contentPane.add(tfNumeroReserva);
		tfNumeroReserva.setColumns(10);

	}
	
	private void limpiarCampos() {
		lblNumeroReserva.setText("");
		lblNumeroHabitaciones.setText("");
		lblPlan.setText("");
		lblFechaEntrada.setText("");
		lblFechaSalida.setText("");
		
		numeroReserva = null;
		reserva = null;
	}
		}

