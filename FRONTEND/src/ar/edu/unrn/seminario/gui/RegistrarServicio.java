package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class RegistrarServicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNombre;
	private JTextField tfDescripcion;
	private JTextField tfPrecio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrarServicio frame = new RegistrarServicio();
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
	public RegistrarServicio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 232);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(67, 28, 53, 14);
		contentPane.add(lblNombre);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(151, 25, 134, 20);
		contentPane.add(tfNombre);
		tfNombre.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(67, 63, 69, 14);
		contentPane.add(lblDescripcion);
		
		tfDescripcion = new JTextField();
		tfDescripcion.setBounds(151, 60, 134, 20);
		contentPane.add(tfDescripcion);
		tfDescripcion.setColumns(10);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(67, 94, 46, 14);
		contentPane.add(lblPrecio);
		
		tfPrecio = new JTextField();
		tfPrecio.setBounds(151, 91, 134, 20);
		contentPane.add(tfPrecio);
		tfPrecio.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(165, 156, 89, 23);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(268, 156, 89, 23);
		contentPane.add(btnCancelar);

	}

}
