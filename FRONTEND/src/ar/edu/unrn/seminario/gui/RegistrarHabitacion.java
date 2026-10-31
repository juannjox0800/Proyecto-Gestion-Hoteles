package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;

public class RegistrarHabitacion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNumHabitacion;
	private JTextField tfCostoHabitacion;
	private JTable table;

	private List<String> listadoCamasTemporal = new ArrayList<>();
	private IApi api;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrarHabitacion frame = new RegistrarHabitacion(null);
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
	public RegistrarHabitacion(IApi api) {
		setTitle("Registrar Habitacion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 469, 328);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.api = api;
		
		JLabel lblNewLabel = new JLabel("N° Habitación:");
		lblNewLabel.setBounds(24, 23, 90, 25);
		contentPane.add(lblNewLabel);
		
		JLabel tipoHabitacion = new JLabel("Tipo Habitacion:");
		tipoHabitacion.setBounds(24, 183, 90, 25);
		contentPane.add(tipoHabitacion);
		
		tfNumHabitacion = new JTextField();
		tfNumHabitacion.setBounds(181, 23, 248, 20);
		contentPane.add(tfNumHabitacion);
		tfNumHabitacion.setColumns(10);
		
		JComboBox cbTipoHabitacion = new JComboBox();
		cbTipoHabitacion.setModel(new DefaultComboBoxModel(new String[] {"Estandar", "Superior", "Deluxe", "Suite"}));
		cbTipoHabitacion.setBounds(181, 184, 248, 22);
		contentPane.add(cbTipoHabitacion);
		
		JLabel costoHabitacion = new JLabel("Costo Habitacion:");
		costoHabitacion.setBounds(24, 219, 104, 19);
		contentPane.add(costoHabitacion);
		
		tfCostoHabitacion = new JTextField();
		tfCostoHabitacion.setColumns(10);
		tfCostoHabitacion.setBounds(181, 217, 248, 20);
		contentPane.add(tfCostoHabitacion);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancelar.setBounds(354, 255, 89, 23);
		contentPane.add(btnCancelar);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAceptar.setBounds(255, 255, 89, 23);
		contentPane.add(btnAceptar);
		
		JLabel lblNewLabel_1 = new JLabel("$-");
		lblNewLabel_1.setBounds(152, 219, 19, 19);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(181, 53, 248, 116);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Cama"
			}
		));
		scrollPane.setViewportView(table);
		
		
		JButton btnEliminarCama = new JButton("Eliminar Cama");
		btnEliminarCama.setBounds(24, 149, 120, 23);
		contentPane.add(btnEliminarCama);
		
		JLabel lblNewLabel_2 = new JLabel("Seleccionar Tipo:");
		lblNewLabel_2.setBounds(24, 60, 104, 14);
		contentPane.add(lblNewLabel_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Simple", "Matrimonial"}));
		comboBox.setBounds(24, 85, 147, 22);
		contentPane.add(comboBox);
		
		
		JButton btnAgregarCama = new JButton("Agregar Cama");
		btnAgregarCama.setBounds(24, 115, 120, 23);
		contentPane.add(btnAgregarCama);
		
		btnAgregarCama.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
					
				try {
					String tipo = comboBox.getSelectedItem().toString();
					listadoCamasTemporal.add(tipo);
					DefaultTableModel modelo = (DefaultTableModel) table.getModel();
					modelo.addRow(new Object[] {tipo});
					
				}catch(DatoVacioException e) {
					JOptionPane.showMessageDialog(null, e, "Error al ingresar datos.", JOptionPane.ERROR_MESSAGE);
				}	
			}	
		});
		
		btnEliminarCama.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				
				int filaSelec = table.getSelectedRow();
				
				if(filaSelec != -1) {
					
					listadoCamasTemporal.remove(filaSelec);
					DefaultTableModel modelo = (DefaultTableModel) table.getModel();
					modelo.removeRow(filaSelec);
				}
			}
		});
		
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				
				int numero = Integer.parseInt(tfNumHabitacion.getText());
				String tipoHabitacion = cbTipoHabitacion.getSelectedItem().toString();
				int costo = Integer.parseInt(tfCostoHabitacion.getText());
				
				api.registrarHabitacion(numero, tipoHabitacion, costo, listadoCamasTemporal);
				
				
				}catch(DatoVacioException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Faltan datos", JOptionPane.WARNING_MESSAGE);
				
				}catch(ValorInvalidoException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Datos erroneos.", JOptionPane.ERROR_MESSAGE);	
				
				}catch(NumberFormatException e3) {
					JOptionPane.showMessageDialog(null,"Error al ingresar el número de la habitación o el costo.", "Datos inválidos.", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);	
			}
			
		});
		
	}
}
