package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class RegistrarPago extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfMontoPagado;
	private JTextField tfFechaDePago;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrarPago frame = new RegistrarPago();
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
	public RegistrarPago() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tfMontoPagado = new JTextField();
		tfMontoPagado.setBounds(127, 53, 235, 20);
		contentPane.add(tfMontoPagado);
		tfMontoPagado.setColumns(10);
		
		JLabel lblMontoAPagar = new JLabel("Monto a pagar");
		lblMontoAPagar.setBounds(10, 56, 107, 14);
		contentPane.add(lblMontoAPagar);
		
		JLabel lblMonto = new JLabel("Monto Restante");
		lblMonto.setBounds(10, 26, 107, 14);
		contentPane.add(lblMonto);
		
		JLabel lblPagoRestante = new JLabel("XXXXXXXXXX");
		lblPagoRestante.setBounds(127, 26, 235, 14);
		contentPane.add(lblPagoRestante);
		
		JLabel lblMetodoPago = new JLabel("Metodo de pago");
		lblMetodoPago.setBounds(10, 88, 107, 14);
		contentPane.add(lblMetodoPago);
		
		JComboBox cbMetodoDePago = new JComboBox();
		cbMetodoDePago.setModel(new DefaultComboBoxModel(new String[] {"Efectivo", "Tarjeta", "Transferencia"}));
		cbMetodoDePago.setBounds(127, 84, 128, 22);
		contentPane.add(cbMetodoDePago);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(0, 227, 89, 23);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(95, 227, 89, 23);
		contentPane.add(btnCancelar);
		
		JLabel lblFechaDePago = new JLabel("Fecha");
		lblFechaDePago.setBounds(10, 120, 79, 14);
		contentPane.add(lblFechaDePago);
		
		tfFechaDePago = new JTextField();
		tfFechaDePago.setColumns(10);
		tfFechaDePago.setBounds(127, 117, 235, 20);
		contentPane.add(tfFechaDePago);

	}
}
