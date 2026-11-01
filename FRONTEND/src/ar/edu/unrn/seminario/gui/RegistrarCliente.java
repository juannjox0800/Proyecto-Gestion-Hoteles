package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistrarCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNombre;
	private JTextField tfApellido;
	private JTextField tfContacto;
	private JTextField tfDNI;
	private IApi api;



	/**
	 * Create the frame.
	 */
	public RegistrarCliente(IApi api) {
	this.api=api;	
	
		setTitle("Registrar Cliente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 482, 326);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNombre.setBounds(59, 44, 59, 20);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblApellido.setBounds(59, 82, 59, 20);
		contentPane.add(lblApellido);
		
		JLabel lblContacto = new JLabel("Contacto:");
		lblContacto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblContacto.setBounds(59, 123, 72, 20);
		contentPane.add(lblContacto);
		
		JLabel lblDNI = new JLabel("DNI:");
		lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDNI.setBounds(59, 161, 46, 17);
		contentPane.add(lblDNI);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(147, 45, 220, 20);
		contentPane.add(tfNombre);
		tfNombre.setColumns(10);
		
		tfApellido = new JTextField();
		tfApellido.setBounds(147, 83, 220, 20);
		contentPane.add(tfApellido);
		tfApellido.setColumns(10);
		
		tfContacto = new JTextField();
		tfContacto.setBounds(147, 124, 220, 20);
		contentPane.add(tfContacto);
		tfContacto.setColumns(10);
		
		tfDNI = new JTextField();
		tfDNI.setBounds(147, 161, 220, 20);
		contentPane.add(tfDNI);
		tfDNI.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nombre = tfNombre.getText();
					String apellido = tfApellido.getText();
					String contacto = tfContacto.getText();
					int dni = Integer.parseInt(tfDNI.getText());
					
					api.registrarCliente(nombre, apellido, contacto, dni);

					JOptionPane.showMessageDialog(null, "¡Cliente registrado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				
				} catch (DatoVacioException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Faltan datos", JOptionPane.WARNING_MESSAGE);
					
				} catch (EntidadDuplicadaException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Cliente duplicado", JOptionPane.ERROR_MESSAGE);
					
				} catch (NumberFormatException e3) {
					JOptionPane.showMessageDialog(null, "El DNI debe ser un número entero válido.", "Datos inválidos", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAceptar.setBounds(234, 246, 101, 23);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	

		btnCancelar.setBounds(355, 246, 101, 23);
		contentPane.add(btnCancelar);
		
		
		}
	}
	

	
	

