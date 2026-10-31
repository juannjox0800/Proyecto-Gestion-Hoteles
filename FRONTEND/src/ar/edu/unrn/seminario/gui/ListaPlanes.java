package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

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

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class ListaPlanes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable jtListaPlanes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		IApi api = new MemoryApi();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListaPlanes frame = new ListaPlanes(api);
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
	public ListaPlanes(IApi api) {
		List<PlanDTO> listaTemporal = new ArrayList<>(api.obtenerListaPlan());
		setTitle("Lista de planes ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 23, 414, 118);
		contentPane.add(scrollPane);
		
		jtListaPlanes = new JTable();
		jtListaPlanes.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Nombre", "Estado", "Costo"
			}
		));
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
			            } else {}
			           
				 }else {}
			}
		});
		contentPane.add(btnEliminarPlan);
		
		JButton btnModificarPlan = new JButton("Modificar");
		btnModificarPlan.setBounds(10, 152, 89, 23);
		contentPane.add(btnModificarPlan);
		
		JButton btnAceptarPlan = new JButton("Aceptar");
		btnAceptarPlan.setBounds(236, 202, 89, 23);
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

}
