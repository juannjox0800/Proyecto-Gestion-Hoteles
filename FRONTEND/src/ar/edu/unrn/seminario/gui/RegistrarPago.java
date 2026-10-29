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
		setBounds(100, 100, 349, 241);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tfMontoPagado = new JTextField();
		tfMontoPagado.setBounds(153, 55, 128, 20);
		contentPane.add(tfMontoPagado);
		tfMontoPagado.setColumns(10);
		
		JLabel lblMontoAPagar = new JLabel("Monto a pagar");
		lblMontoAPagar.setBounds(36, 58, 107, 14);
		contentPane.add(lblMontoAPagar);
		
		JLabel lblMonto = new JLabel("Monto Restante");
		lblMonto.setBounds(36, 28, 107, 14);
		contentPane.add(lblMonto);
		
		JLabel lblPagoRestante = new JLabel("-");
		lblPagoRestante.setBounds(153, 28, 128, 14);
		contentPane.add(lblPagoRestante);
		
		JLabel lblMetodoPago = new JLabel("Metodo de pago");
		lblMetodoPago.setBounds(36, 90, 107, 14);
		contentPane.add(lblMetodoPago);
		
		JComboBox cbMetodoDePago = new JComboBox();
		cbMetodoDePago.setModel(new DefaultComboBoxModel(new String[] {"Efectivo", "Tarjeta", "Transferencia"}));
		cbMetodoDePago.setBounds(153, 86, 128, 22);
		contentPane.add(cbMetodoDePago);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(10, 166, 89, 23);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(105, 166, 89, 23);
		contentPane.add(btnCancelar);

	}
}
