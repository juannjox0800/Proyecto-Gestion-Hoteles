package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.dto.PlanDTO;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;

import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class ListaPlanes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable jtListaPlanes;


	public ListaPlanes(IApi api) {
		List<PlanDTO> listaTemporal = new ArrayList<>(api.obtenerListaPlan());
		setTitle("Lista de planes ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 23, 414, 118);
		contentPane.add(scrollPane);
		
		jtListaPlanes = new JTable();
		String [] columnas =  {
				"Nombre", "Estado", "Costo"
			};
		DefaultTableModel modelo = new DefaultTableModel(null, columnas);
		List <PlanDTO>ListaPlanes = api.obtenerListaPlan();
		for (PlanDTO a : ListaPlanes) {
			String nombre = a.getNombre();
			int costo = a.getCosto();
			String estado = "Activo";
			if (a.isEstadoActivo()== false) estado="Inactivo";
			modelo.addRow(new Object[] {nombre, estado, costo});
		}
		jtListaPlanes.setModel(modelo);
		jtListaPlanes.getColumnModel().getColumn(0).setPreferredWidth(120);
		jtListaPlanes.getColumnModel().getColumn(1).setPreferredWidth(120);
		jtListaPlanes.getColumnModel().getColumn(2).setPreferredWidth(120);
		scrollPane.setViewportView(jtListaPlanes);
		
		JButton btnEliminarPlan = new JButton("Eliminar");
		btnEliminarPlan.setBounds(109, 152, 89, 23);
		btnEliminarPlan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				 int filaSeleccionada = jtListaPlanes.getSelectedRow();
				 if (filaSeleccionada!=-1) {
					 String nombrePlanSeleccionado = (String) jtListaPlanes.getValueAt(filaSeleccionada, 0);
			            
			            PlanDTO planAEliminar = null;
			            for (PlanDTO plan : listaTemporal) {
			                if (plan.getNombre().equals(nombrePlanSeleccionado)) {
			                    planAEliminar = plan;
			                }
			            }
			            if (planAEliminar != null) {
			                listaTemporal.remove(planAEliminar);
			                
			                DefaultTableModel modelo = (DefaultTableModel) jtListaPlanes.getModel();
			                JOptionPane.showMessageDialog(null, "Plan marcado para eliminar. Pulse Aceptar para guardar cambios.");
			                modelo.removeRow(filaSeleccionada);
			            } else {
			            	JOptionPane.showMessageDialog(null, "el plan seleccionado no existe");
			            }
				 }else {
		                JOptionPane.showMessageDialog(null, "No has seleccionado plan. Selecciona uno.");

				 }
			}
		});
		contentPane.add(btnEliminarPlan);
		
		JButton btnModificarPlan = new JButton("Modificar");
		btnModificarPlan.setBounds(10, 152, 89, 23);
		btnModificarPlan.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		        int filaSeleccionada = jtListaPlanes.getSelectedRow();
		        if (filaSeleccionada == -1) {
		            JOptionPane.showMessageDialog(null, "No has seleccionado plan. Selecciona uno.");
		            return;
		        }
		        String nombrePlanSeleccionado = (String) jtListaPlanes.getValueAt(filaSeleccionada, 0);
		        PlanDTO planAModificar = null;
		        for (PlanDTO plan : listaTemporal) {
		            if (plan.getNombre().equals(nombrePlanSeleccionado)) {
		                planAModificar = plan;
		            }
		        }

		        if (planAModificar == null) {
		            JOptionPane.showMessageDialog(null, "El plan seleccionado no existe en la lista.");
		        } else {
		            RegistrarPlan ventana = new RegistrarPlan(api, planAModificar);
		            ventana.addWindowListener(new WindowAdapter() {
		                @Override
		                public void windowClosed(WindowEvent we) {
		                    actualizarPlanEnLista(api, listaTemporal, nombrePlanSeleccionado);
		                    refrescarTabla(listaTemporal);
		                }
		            });
		            ventana.setVisible(true);
		        }
		    }
		});
		contentPane.add(btnModificarPlan);
		
		JButton btnAceptarPlan = new JButton("Aceptar");
		btnAceptarPlan.setBounds(236, 202, 89, 23);
		btnAceptarPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {
					api.guardarListaPLan(listaTemporal);
					JOptionPane.showMessageDialog(null,"Lista Modificada exitosamente" );
					dispose();
				} catch (DatoVacioException e1) {
					JOptionPane.showMessageDialog(null, "Error al guardar: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (ValorInvalidoException e2) {
					JOptionPane.showMessageDialog(null, "Error al guardar: " + e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		contentPane.add(btnAceptarPlan);
		
		JButton btnCancelarPlan = new JButton("Cancelar");
		btnCancelarPlan.setSize(89, 23);
		btnCancelarPlan.setLocation(335, 202);
		btnCancelarPlan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
		}
		});
		contentPane.add(btnCancelarPlan);

	}
	private void refrescarTabla(List<PlanDTO>listaTemporal) {
	    DefaultTableModel modelo = (DefaultTableModel) jtListaPlanes.getModel();
	    modelo.setRowCount(0); 	    
	    for (PlanDTO plan : listaTemporal) {
	        modelo.addRow(new Object[] {
	            plan.getNombre(),
	            plan.isEstadoActivo() ? "Activo" : "Inactivo",
	            plan.getCosto()
	        });
	    }
	    
	}
	private void actualizarPlanEnLista(IApi api, List<PlanDTO> listaTemporal, String nombre) {
	    for (PlanDTO actualizado : api.obtenerListaPlan()) {
	        if (actualizado.getNombre().equals(nombre)) {
	            for (int i = 0; i < listaTemporal.size(); i++) {
	                if (listaTemporal.get(i).getNombre().equals(nombre)) {
	                    listaTemporal.set(i, actualizado);
	                }
	            }
	        }
	    }
	}
}
