package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ClienteDTO;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.FormatoInvalidoException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class ListarClientes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private IApi api;


	public ListarClientes(IApi api) {
		this.api=api;
		setTitle("Clientes Registrados");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 539, 376);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 503, 178);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
			},
			new String[] {
				"Nombre", "Apellido", "DNI", "Contacto"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		scrollPane.setViewportView(table);
		
		JLabel lblBuscar = new JLabel("Buscar por DNI:");
		lblBuscar.setBounds(28, 25, 88, 14);
		contentPane.add(lblBuscar);
		
		textField = new JTextField();
		textField.setBounds(126, 22, 119, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					int dni = Integer.parseInt(textField.getText());
					ClienteDTO cliente = api.obtenerCliente(dni);
				
					DefaultTableModel modelo = (DefaultTableModel) table.getModel();
					modelo.setRowCount(0);
					modelo.addRow(new Object[] {
						cliente.getNombre(),
						cliente.getApellido(),
						cliente.getDni(),
						cliente.getContacto()
					});
					
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "El DNI a buscar debe ser numérico.", "Error de formato", JOptionPane.ERROR_MESSAGE);
				} catch (FormatoInvalidoException ex) {
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "El formato no es correcto", JOptionPane.ERROR_MESSAGE);
				}
				catch (EntidadNoEncontradaException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "No encontrado", JOptionPane.WARNING_MESSAGE);
					cargarTablaCompleta();
				}
			}
			
		});
		btnBuscar.setBounds(255, 21, 89, 23);
		contentPane.add(btnBuscar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
              int filaSelec = table.getSelectedRow();
				
				if (filaSelec != -1) {
					int verificacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro que deseas eliminar este cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
					
					if (verificacion == JOptionPane.YES_OPTION) {
						try {
							int dni = Integer.parseInt(table.getValueAt(filaSelec, 2).toString());
							api.eliminarCliente(dni);
							
							DefaultTableModel modelo = (DefaultTableModel) table.getModel();
							modelo.removeRow(filaSelec);
							
							JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
							
						} catch (EntidadNoEncontradaException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, seleccione un cliente de la tabla para eliminar.", "Fila no seleccionada", JOptionPane.WARNING_MESSAGE);
				}
			}
				
			
		});
		btnEliminar.setBounds(424, 239, 89, 23);
		contentPane.add(btnEliminar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
int filaSelec = table.getSelectedRow();
				
				if (filaSelec != -1) {
					try {
						int dni = Integer.parseInt(table.getValueAt(filaSelec, 2).toString());
						ClienteDTO clienteDTO = api.obtenerCliente(dni);
						
						
						RegistrarCliente ventanaModificar = new RegistrarCliente(api,clienteDTO);
						ventanaModificar.setVisible(true);
						
						JOptionPane.showMessageDialog(null, "Cliente seleccionado para modificar: " + clienteDTO.getNombre(), "Modificar", JOptionPane.INFORMATION_MESSAGE);
						
					} catch (EntidadNoEncontradaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error al modificar.", JOptionPane.ERROR_MESSAGE);
					} catch (FormatoInvalidoException e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage(), "Error al modificar.", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, seleccione un cliente de la tabla para modificar.", "Fila no seleccionada", JOptionPane.WARNING_MESSAGE);
				}
			
			}
		});
		btnModificar.setBounds(325, 239, 89, 23);
		contentPane.add(btnModificar);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCerrar.setBounds(27, 295, 89, 23);
		contentPane.add(btnCerrar);

		cargarTablaCompleta();
	}
private void cargarTablaCompleta() {
	DefaultTableModel modelo = (DefaultTableModel) table.getModel();
	modelo.setRowCount(0); 
	if (api!=null) {
	List<ClienteDTO> clientes = api.obtenerListaClientes();
	for (ClienteDTO c : clientes) {
		modelo.addRow(new Object[] {
			c.getNombre(),
			c.getApellido(),
			c.getDni(),
			c.getContacto()
		});
	}
}
}
}
