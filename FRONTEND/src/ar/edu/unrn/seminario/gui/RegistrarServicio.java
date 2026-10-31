package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.dto.ServicioDTO;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;

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
	private String nombreOriginal;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi apiPrueba = new MemoryApi();
					RegistrarServicio frame = new RegistrarServicio(apiPrueba, null);
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
	public RegistrarServicio(IApi api, ServicioDTO dto) {
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
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSalir.setBounds(300, 253, 89, 23);
		contentPane.add(btnSalir);
		
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
		
		if (dto == null) {
			this.nombreOriginal = null;
			setTitle ("Registrar nuevo Servicio");
		} else {
			this.nombreOriginal = dto.getNombre();
			setTitle ("Modificar Servicio");
			
			tfNombre.setText(dto.getNombre());
			tfNombre.setEnabled(false);
			textArea.setText(dto.getDescripcion());
			tfPrecio.setText(String.valueOf(dto.getPrecio()));
			
			if (dto.isEstadoVigente()) {
				comboBox.setSelectedItem("Activo");
			} else {
				comboBox.setSelectedItem("Inactivo");
			}
			
		}
		
		btnAceptar.addActionListener((actionEvent) -> {
			try {
				String nombre = tfNombre.getText();
				String descripcion = textArea.getText();
				int precio = Integer.parseInt(tfPrecio.getText());
				boolean estadoVigente = comboBox.getSelectedItem().toString().equals("Activo");
				
				if (nombreOriginal == null) {
					api.registrarServicio(nombre, descripcion, precio, estadoVigente);
					JOptionPane.showMessageDialog(null, "Servicio Registrado Correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
				} else {
					api.modificarServicio(nombreOriginal, descripcion, precio, estadoVigente);
					JOptionPane.showMessageDialog(null, "Servicio Modificado Correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				
				tfNombre.setText("");
				textArea.setText("");
				tfPrecio.setText("");
				
				
			} catch (DatoVacioException e1) {
				JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error al ingresar el nombre", JOptionPane.ERROR_MESSAGE);
			} catch (ValorInvalidoException e2) {
				JOptionPane.showMessageDialog(null, "Error: " + e2.getMessage(), "Error al ingresar el precio", JOptionPane.ERROR_MESSAGE);
			} catch (EntidadDuplicadaException e3) {
				JOptionPane.showMessageDialog(null, "Error: " + e3.getMessage(), "Error al registrar el servicio", JOptionPane.ERROR_MESSAGE);
			} catch (EntidadNoEncontradaException e4) {
				JOptionPane.showMessageDialog(null, "Error: " + e4.getMessage(), "Error intentar modificar el servicio", JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException e5) {
				JOptionPane.showMessageDialog(null, "El precio debe ser un número válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		btnSalir.addActionListener((actionEvent) -> {
			dispose();
		});
		
		

	}
}
