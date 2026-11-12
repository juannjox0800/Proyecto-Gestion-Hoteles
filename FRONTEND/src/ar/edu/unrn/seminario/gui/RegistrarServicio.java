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

	public RegistrarServicio(IApi api, ServicioDTO dto) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 415, 334);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("(*) Nombre:");
		lblNombre.setBounds(20, 39, 66, 14);
		contentPane.add(lblNombre);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(142, 39, 195, 20);
		contentPane.add(tfNombre);
		tfNombre.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(20, 74, 85, 14);
		contentPane.add(lblDescripcion);
		
		JLabel lblPrecio = new JLabel("(*) Precio:");
		lblPrecio.setBounds(20, 182, 66, 14);
		contentPane.add(lblPrecio);
		
		tfPrecio = new JTextField();
		tfPrecio.setBounds(142, 182, 195, 20);
		contentPane.add(tfPrecio);
		tfPrecio.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(300, 261, 89, 23);
		contentPane.add(btnGuardar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSalir.setBounds(197, 261, 89, 23);
		contentPane.add(btnSalir);
		
		JLabel lblNewLabel = new JLabel("$-");
		lblNewLabel.setBounds(120, 185, 23, 14);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(142, 70, 195, 101);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		
		JLabel lblNewLabel_1 = new JLabel("Estado: ");
		lblNewLabel_1.setBounds(20, 224, 85, 14);
		contentPane.add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Activo", "Inactivo"}));
		comboBox.setBounds(142, 220, 195, 22);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_2 = new JLabel("Datos Obligatorios (*)");
		lblNewLabel_2.setBounds(20, 11, 169, 14);
		contentPane.add(lblNewLabel_2);
		
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
		
		btnGuardar.addActionListener((actionEvent) -> {
			try {
				String nombre = tfNombre.getText();
				String descripcion = textArea.getText();
				float precio = Float.parseFloat(tfPrecio.getText());
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
