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
import ar.edu.unrn.seminario.dto.ServicioDTO;
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
		for (PlanDTO a : api.obtenerListaPlan()) {
			String nombre = a.getNombre();
			float costo = a.getCosto();
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
			}
		});
		contentPane.add(btnEliminarPlan);
		
		JButton btnModificarPlan = new JButton("Modificar");
		btnModificarPlan.setBounds(10, 152, 89, 23);
		btnModificarPlan.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    }
		});
		contentPane.add(btnModificarPlan);
		
		JButton btnAceptarPlan = new JButton("Aceptar");
		btnAceptarPlan.setBounds(236, 202, 89, 23);
		btnAceptarPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){}
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
	/*	private void recargarTabla(IApi api) {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
		
		for (ServicioDTO dto : api.obtenerListaServicios()) {
			modelo.addRow(new Object[] {
					dto.getNombre(), "$" + dto.getPrecio(), dto.isEstadoVigente() ? "Activo" : "Inactivo"
			});
		}
	}*/
}
