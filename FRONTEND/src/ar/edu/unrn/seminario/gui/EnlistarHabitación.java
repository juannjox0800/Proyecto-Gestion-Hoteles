package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.HabitacionDTO;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;


public class EnlistarHabitación extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tFbuscar;
	private JTable table;

	private IApi api;
	

	public EnlistarHabitación(IApi api) {
		
		this.api = api;
		setTitle("Lista de Habitaciones ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 645, 351);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 36, 631, 178);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
			},
			new String[] {
				"Numero", "Cantidad camas", "Tipo", "Estado", "Cantidad huespedes", "Costo"
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel lblBuscar = new JLabel("Buscar Habitacion:");
		lblBuscar.setBounds(32, 11, 112, 14);
		contentPane.add(lblBuscar);
		
		tFbuscar = new JTextField();
		tFbuscar.setBounds(154, 8, 143, 20);
		contentPane.add(tFbuscar);
		tFbuscar.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(307, 7, 89, 23);
		contentPane.add(btnBuscar);
		
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int numero = Integer.parseInt(tFbuscar.getText());
					
					HabitacionDTO dtoH = api.obtenerHabitacion(numero);
					
					DefaultTableModel modelo = (DefaultTableModel) table.getModel(); 
					modelo.setRowCount(0);
					
					if(dtoH != null) {
						
						modelo.addRow(new Object[] {
							dtoH.getNumero(),
							dtoH.cantCamas(),
							dtoH.getTipoHabitacion(),
							dtoH.getEstado(),
							dtoH.cantHuespedes(),
							dtoH.getCosto()
							
						});
					}
				
			}catch(NumberFormatException e2) {
				JOptionPane.showMessageDialog(null,"Error al ingresar el número de la habitación o el costo.", "Datos inválidos.", JOptionPane.ERROR_MESSAGE);
				
			}catch(EntidadNoEncontradaException e3) {
				JOptionPane.showMessageDialog(null,"Esta habitacion no se encuentra en el sistema. Intente de nuevo", "Datos inválidos.", JOptionPane.ERROR_MESSAGE);
			}
				
			
			
			}});
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(188, 225, 89, 23);
		contentPane.add(btnModificar);
		
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSelec = table.getSelectedRow();
				
				if(filaSelec != -1) {
						
						try {
							
							int numHabitacion = (int) table.getValueAt(filaSelec, 0);
							HabitacionDTO dtoH = api.obtenerHabitacion(numHabitacion);
							RegistrarHabitacion ventanaModificar = new RegistrarHabitacion(api, dtoH);
							ventanaModificar.setVisible(true);
							
							
						} catch (EntidadNoEncontradaException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error al modificar.", JOptionPane.ERROR_MESSAGE);
							
						}
					
				}else {
					JOptionPane.showMessageDialog(null, "Por favor, seleccione una habitación de la tabla para modificar.", "Fila no seleccionada", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		});
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(307, 225, 89, 23);
		contentPane.add(btnEliminar);
		
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSelec = table.getSelectedRow();
				
				if(filaSelec != -1) {
					
					
					int verificacion = JOptionPane.showConfirmDialog(null, "¿Estás segura que deseas eliminar esta habitación?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
					
                    if(verificacion == JOptionPane.YES_OPTION) {
                    	
                    	try {
                    		
                    		int numeroHabitacion = (int) table.getValueAt(filaSelec, 0);
                            boolean eliminado = api.eliminarHabitacion(numeroHabitacion);
                            
                            DefaultTableModel modelo = (DefaultTableModel) table.getModel();
                            modelo.removeRow(filaSelec);
                            
                            JOptionPane.showMessageDialog(null, "Habitación eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            
                    	}catch(EntidadNoEncontradaException e1) {
                    		JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    	}
                      }
					
				}else {
					JOptionPane.showMessageDialog(null, "Por favor, seleccione una habitación de la tabla para eliminar.", "Fila no seleccionada", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		});
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(517, 269, 89, 23);
		contentPane.add(btnCerrar);
		
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
			}
		});

		List<HabitacionDTO> listadoCompleto = api.obtenerListaHabitacion();
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
		
		//recorro lista y agrego filas
		for(HabitacionDTO dtoH : listadoCompleto) {
			modelo.addRow( new Object[] {
				dtoH.getNumero(),
				dtoH.cantCamas(),
				dtoH.getTipoHabitacion(),
				dtoH.getEstado(),
				dtoH.cantHuespedes(),
				dtoH.getCosto()
				
			});
		}
		
		
	}
}
