package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class EnlistarHabitación extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tFbuscar;
	private JTable table;

	private IApi api;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnlistarHabitación frame = new EnlistarHabitación(null);
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
	public EnlistarHabitación(IApi api) {
		
		this.api = api;
		setTitle("Lista de Habitaciones ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 499, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 36, 483, 178);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
			},
			new String[] {
				"Numero", "Cant Camas", "Tipo", "Ocupada", "Estado", "Cant Huesped"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(103);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(5).setPreferredWidth(88);
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
							dtoH.getCosto(),	
							dtoH.getEstado()
						});
					}
				
			}catch(DatoVacioException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Faltan datos", JOptionPane.WARNING_MESSAGE);
				
			}catch(NumberFormatException e2) {
				JOptionPane.showMessageDialog(null,"Error al ingresar el número de la habitación o el costo.", "Datos inválidos.", JOptionPane.ERROR_MESSAGE);
				
			}
				
			
			
			}});
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(10, 225, 89, 23);
		contentPane.add(btnModificar);
		
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSelec = table.getSelectedRow();
				
				if(filaSelec != -1) {
						
						int numHabitacion = (int) table.getValueAt(filaSelec, 0);
						HabitacionDTO dtoH = api.obtenerHabitacion(numHabitacion);
						RegistrarHabitacion ventanaModificar = new RegistrarHabitacion(api, dtoH);
						ventanaModificar.setVisible(true);
				}
			}
			
		});
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(109, 225, 89, 23);
		contentPane.add(btnEliminar);
		
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSelec = table.getSelectedRow();
				
				if(filaSelec != -1) {
					
                    int numeroHabitacion = (int) table.getValueAt(filaSelec, 0);
                    
                    boolean eliminado = api.eliminarHabitacion(numeroHabitacion);
                    
                    if (eliminado) {
                        
                        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
                        modelo.removeRow(filaSelec);
                        
                        JOptionPane.showMessageDialog(null, "Habitación eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar la habitación del sistema.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
				}
			}
			
		});
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(285, 276, 89, 23);
		contentPane.add(btnAceptar);
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
			}
		});
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(384, 276, 89, 23);
		contentPane.add(btnCancelar);
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
			}
		});

	}
}
