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
import ar.edu.unrn.seminario.dto.CamaDTO;
import ar.edu.unrn.seminario.dto.HabitacionDTO;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;

public class RegistrarHabitacion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNumHabitacion;
	private JTextField tfCostoHabitacion;
	private JTable table;

	private List<String> listadoCamasTemporal = new ArrayList<>();
	private IApi api;
	private Integer numeroOriginal = null; // si es nulo, se registra una habitacion nueva, sino, modifica una habitacion
	

	public RegistrarHabitacion(IApi api, HabitacionDTO dtoH) {
		setTitle("Registrar Habitacion");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 460);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.api = api;
		if(dtoH == null) {
			this.numeroOriginal = null;
		}else {
			this.numeroOriginal = dtoH.getNumero();
		}
		
		
		JLabel lblNewLabel = new JLabel("(*) N° Habitación:");
		lblNewLabel.setBounds(24, 40, 120, 25);
		contentPane.add(lblNewLabel);
		
		JLabel tipoHabitacion = new JLabel("(*) Tipo Habitacion:");
		tipoHabitacion.setBounds(24, 214, 90, 25);
		contentPane.add(tipoHabitacion);
		
		tfNumHabitacion = new JTextField();
		tfNumHabitacion.setBounds(191, 43, 248, 20);
		contentPane.add(tfNumHabitacion);
		tfNumHabitacion.setColumns(10);
		
		JComboBox cbTipoHabitacion = new JComboBox();
		cbTipoHabitacion.setModel(new DefaultComboBoxModel(new String[] {"Estandar", "Superior", "Deluxe", "Suite"}));
		cbTipoHabitacion.setBounds(191, 215, 248, 22);
		contentPane.add(cbTipoHabitacion);
		
		JLabel costoHabitacion = new JLabel("(*) Costo Habitacion:");
		costoHabitacion.setBounds(24, 257, 120, 19);
		contentPane.add(costoHabitacion);
		
		tfCostoHabitacion = new JTextField();
		tfCostoHabitacion.setColumns(10);
		tfCostoHabitacion.setBounds(191, 257, 248, 20);
		contentPane.add(tfCostoHabitacion);
		
		JButton btnCancelar = new JButton("Salir");
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancelar.setBounds(317, 360, 89, 23);
		contentPane.add(btnCancelar);
		
		JButton btnAceptar = new JButton("Guardar");
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAceptar.setBounds(444, 360, 89, 23);
		contentPane.add(btnAceptar);
		
		JLabel lblNewLabel_1 = new JLabel("$-");
		lblNewLabel_1.setBounds(162, 257, 19, 19);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(191, 81, 248, 116);
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
		btnEliminarCama.setBounds(24, 170, 120, 23);
		contentPane.add(btnEliminarCama);
		
		JLabel lblNewLabel_2 = new JLabel("Seleccionar Tipo:");
		lblNewLabel_2.setBounds(24, 81, 104, 14);
		contentPane.add(lblNewLabel_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Simple", "Matrimonial"}));
		comboBox.setBounds(24, 105, 147, 22);
		contentPane.add(comboBox);
		
		
		JButton btnAgregarCama = new JButton("Agregar Cama");
		btnAgregarCama.setBounds(24, 137, 120, 23);
		contentPane.add(btnAgregarCama);
		
		JLabel lblNewLabel_3 = new JLabel("Estado:");
		lblNewLabel_3.setBounds(24, 303, 70, 12);
		contentPane.add(lblNewLabel_3);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Disponible", "No disponible"}));
		comboBox_1.setBounds(191, 299, 248, 20);
		contentPane.add(comboBox_1);
		
		JLabel lblNewLabel_4 = new JLabel("Datos obligatorios (*)");
		lblNewLabel_4.setBounds(24, 18, 133, 25);
		contentPane.add(lblNewLabel_4);
		
		btnAgregarCama.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
					
				String tipo = comboBox.getSelectedItem().toString();
				listadoCamasTemporal.add(tipo);
				DefaultTableModel modelo = (DefaultTableModel) table.getModel();
				modelo.addRow(new Object[] {tipo});
					
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
				String estado = comboBox_1.getSelectedItem().toString();
				int costo = Integer.parseInt(tfCostoHabitacion.getText());
				
				if(numeroOriginal == null) {
					api.registrarHabitacion(numero, tipoHabitacion, estado, costo, listadoCamasTemporal);
					
					JOptionPane.showMessageDialog(null, "La habitación se registró correctamente.", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
					
				}else {
					api.modificarHabitacion(numeroOriginal, numero, tipoHabitacion, estado, costo, listadoCamasTemporal);
					
					JOptionPane.showMessageDialog(null, "La habitación se modificó correctamente.", "Modificación Exitosa", JOptionPane.INFORMATION_MESSAGE);
				}
				
				}catch(DatoVacioException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Faltan datos", JOptionPane.WARNING_MESSAGE);
				
				}catch(ValorInvalidoException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Datos erroneos.", JOptionPane.ERROR_MESSAGE);	
				
				}catch(NumberFormatException e3) {
					JOptionPane.showMessageDialog(null,"Error al ingresar el número de la habitación o el costo.", "Datos inválidos.", JOptionPane.ERROR_MESSAGE);
				}catch(EntidadNoEncontradaException e4) {
					JOptionPane.showMessageDialog(null, e4.getMessage(), "Habitacion no encontrada.", JOptionPane.ERROR_MESSAGE);	
				}catch(EntidadDuplicadaException e5) {
					JOptionPane.showMessageDialog(null, e5.getMessage(), "Esta habitacion ya se encuentra en el sistema.", JOptionPane.WARNING_MESSAGE);	
				}
				
			}
			
		});
		
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});
		
		if(dtoH != null) {
			
			//autocompleto campos de texto
			tfNumHabitacion.setText(String.valueOf(dtoH.getNumero()));
			tfCostoHabitacion.setText(String.valueOf(dtoH.getCosto()));
			tfNumHabitacion.setEditable(false);
			
			String tipoGuardado = dtoH.getTipoHabitacion();
			if(tipoGuardado != null) {
				for(int i = 0; i < cbTipoHabitacion.getItemCount(); i++) {
					if(cbTipoHabitacion.getItemAt(i).toString().trim().equalsIgnoreCase(tipoGuardado.trim())){
						cbTipoHabitacion.setSelectedIndex(i);
						break;
					}
				}
			}
			
			if(dtoH.getCamas() != null) {
				DefaultTableModel modelo = (DefaultTableModel) table.getModel();
				for(CamaDTO cama : dtoH.getCamas()) {
					listadoCamasTemporal.add(cama.getTipo());
					modelo.addRow(new Object[] {cama.getTipo()});
				}
			}
		}
		
	}
}
