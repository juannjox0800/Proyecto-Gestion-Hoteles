package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.exception.DatoVacioException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class RegistrarServicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNombre;
	private JTextField tfPrecio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi apiPrueba = new MemoryApi();
					RegistrarServicio frame = new RegistrarServicio(apiPrueba);
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
	public RegistrarServicio(IApi api) {
		setTitle("Registrar nuevo Servicio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 415, 326);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("* Nombre:");
		lblNombre.setBounds(19, 26, 66, 14);
		contentPane.add(lblNombre);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(141, 26, 195, 20);
		contentPane.add(tfNombre);
		tfNombre.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(19, 61, 85, 14);
		contentPane.add(lblDescripcion);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(19, 169, 46, 14);
		contentPane.add(lblPrecio);
		
		tfPrecio = new JTextField();
		tfPrecio.setBounds(141, 169, 195, 20);
		contentPane.add(tfPrecio);
		tfPrecio.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(197, 253, 89, 23);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancelar.setBounds(300, 253, 89, 23);
		contentPane.add(btnCancelar);
		
		JLabel lblNewLabel = new JLabel("$-");
		lblNewLabel.setBounds(119, 172, 23, 14);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(141, 57, 195, 101);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		
		JLabel lblNewLabel_1 = new JLabel("Estado de vigencia: ");
		lblNewLabel_1.setBounds(19, 211, 112, 14);
		contentPane.add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Activo", "Inactivo"}));
		comboBox.setBounds(141, 207, 195, 22);
		contentPane.add(comboBox);
		
		btnAceptar.addActionListener((actionEvent) -> {
			try {
				String nombre = tfNombre.getText();
				String descripcion = textArea.getText();
				int precio = Integer.parseInt(tfPrecio.getText());
				boolean estadoVigente = comboBox.getSelectedItem().toString().equals("Activo");
				
				api.registrarServicio(nombre, descripcion, precio, estadoVigente);
				
				JOptionPane.showMessageDialog(null, "Servicio Registrado Correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
				
				tfNombre.setText("");
				textArea.setText("");
				tfPrecio.setText("");
				
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "El precio debe ser un numero entero", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (RuntimeException e) {
				JOptionPane.showMessageDialog(null, "Error al ingresar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		btnCancelar.addActionListener((actionEvent) -> {
			dispose();
		});

	}
}
