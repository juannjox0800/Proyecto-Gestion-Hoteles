package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.dto.PlanDTO;
import ar.edu.unrn.seminario.dto.ServicioDTO;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class RegistrarPlan extends JFrame {
	private PlanDTO planAEditar=null;
	private ArrayList<ServicioDTO> serviciosSeleccionados = new ArrayList<>();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tFNombre;
	private JTextField tFCosto;
	private JTable TableServiciosIncluidos;
	private JTextField tfABuscar;
	private float precioRecomendado;
	private JLabel lblCostoRecomendado;


	public RegistrarPlan(IApi api)throws DatoVacioException {this(api,null);}
	
	
	public RegistrarPlan(IApi api, PlanDTO planExistente) throws DatoVacioException {
		this.planAEditar=planExistente;
		if (api.obtenerListaServicios().isEmpty()) {
		    throw new DatoVacioException( "No hay ningún servicio cargado");
		}
		setTitle("Registrar Plan");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 527, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombrePlan = new JLabel("Nombre(*)");
		lblNombrePlan.setBounds(10, 42, 75, 14);
		contentPane.add(lblNombrePlan);
		
		JLabel lblCostoPlan = new JLabel("Costo(*)");
		lblCostoPlan.setBounds(10, 63, 75, 14);
		contentPane.add(lblCostoPlan);
		
		tFNombre = new JTextField();
		tFNombre.setBounds(127, 36, 195, 20);
		contentPane.add(tFNombre);
		tFNombre.setColumns(10);
		
		tFCosto = new JTextField();
		tFCosto.setColumns(10);
		tFCosto.setBounds(127, 60, 195, 20);
		contentPane.add(tFCosto);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(155, 167, 278, 90);
		contentPane.add(scrollPane);
		
		TableServiciosIncluidos = new JTable();
		String[] columna ={"Servicios incluidos"};
		DefaultTableModel modelo = new DefaultTableModel(null, columna);
				TableServiciosIncluidos.setModel(modelo);
				TableServiciosIncluidos.getColumnModel().getColumn(0).setResizable(false);
				TableServiciosIncluidos.getColumnModel().getColumn(0).setPreferredWidth(150);
				scrollPane.setViewportView(TableServiciosIncluidos);
		
		TableServiciosIncluidos.setModel(modelo);
		TableServiciosIncluidos.getColumnModel().getColumn(0).setResizable(false);
		TableServiciosIncluidos.getColumnModel().getColumn(0).setPreferredWidth(150);
		scrollPane.setViewportView(TableServiciosIncluidos);
		
		JComboBox cbServicios = new JComboBox();
		String[] nombres = api.obtenerListaServicios().stream()
		        .filter(ServicioDTO::isEstadoVigente)
		        .map(ServicioDTO::getNombre)
		        .toArray(String[]::new);
		cbServicios.setModel(new DefaultComboBoxModel<>(nombres));
		cbServicios.setBounds(10, 167, 135, 22);
		contentPane.add(cbServicios);
		
		JButton btnAgregarServicio = new JButton("Agregar Servicio");
		btnAgregarServicio.setBounds(10, 200, 135, 23);
		btnAgregarServicio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			    try {
			        if (cbServicios.getSelectedIndex() == -1) {
			            throw new ValorInvalidoException("Selecciona un servicio a agregar");
			        }

			        ServicioDTO servicioSelec = api.obtenerServicio(cbServicios.getSelectedItem().toString());

			        boolean yaAgregado = serviciosSeleccionados.stream()
			                .anyMatch(s -> s.getNombre().equalsIgnoreCase(servicioSelec.getNombre()));
			        if (yaAgregado) {
			            throw new EntidadDuplicadaException("El servicio ya fue agregado al plan");
			        }

			        serviciosSeleccionados.add(servicioSelec);
			        ((DefaultTableModel) TableServiciosIncluidos.getModel())
			                .addRow(new Object[] { servicioSelec.getNombre() });
			     actualizarPrecio(api);

			    } catch (ValorInvalidoException | EntidadNoEncontradaException | EntidadDuplicadaException ex) {
			        JOptionPane.showMessageDialog(null, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
			    }
			}});
		
		JComboBox cBActivo = new JComboBox<>();
		cBActivo.setModel(new DefaultComboBoxModel(new String[] {"Activo", "Inactivo"}));
		cBActivo.setBounds(127, 109, 195, 22);
		contentPane.add(cBActivo);
		contentPane.add(btnAgregarServicio);

		if(planAEditar!=null) {
			tFNombre.setText(planAEditar.getNombre());
	        tFNombre.setEditable(false);
	        tFCosto.setText(String.valueOf(planAEditar.getCosto()));
	        cBActivo.setSelectedItem(planAEditar.isEstadoActivo() ? "Activo" : "Inactivo");
	        if (planAEditar.getServicios() != null) {
	            for (ServicioDTO servicio : planAEditar.getServicios()) {
	                serviciosSeleccionados.add(servicio);
	                DefaultTableModel modeloTabla = (DefaultTableModel) TableServiciosIncluidos.getModel();
	                modeloTabla.addRow(new Object[] { servicio.getNombre() });
	            }
	        }
		}
		JButton btnGuardarPlan = new JButton("Guardar");
		btnGuardarPlan.setBounds(395, 352, 89, 23);
		btnGuardarPlan.addActionListener(ev -> {
		    try {
		        String nombre = tFNombre.getText().trim();
		        float costo = Float.parseFloat(tFCosto.getText().trim());
		        boolean activo = "Activo".equals(cBActivo.getSelectedItem());
		        if (planAEditar == null) {
		            api.registrarPlan(serviciosSeleccionados, costo, nombre, activo);
		        } else {
		            api.modificarPlan(serviciosSeleccionados, costo, nombre, activo);
		        }
		        JOptionPane.showMessageDialog(null, "Plan guardado correctamente");

		    } catch (NumberFormatException ex) {
		        JOptionPane.showMessageDialog(null, "El costo debe ser un número válido", "Aviso", JOptionPane.WARNING_MESSAGE);
		    } catch (DatoVacioException | ValorInvalidoException | EntidadDuplicadaException | EntidadNoEncontradaException ex) {
		        JOptionPane.showMessageDialog(null, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
		    }
		});
		contentPane.add(btnGuardarPlan);
		JButton btnSalirPlan = new JButton("Salir");
		btnSalirPlan.setBounds(299, 352, 89, 23);
		btnSalirPlan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
		}
		});
		contentPane.add(btnSalirPlan);
		
		JButton btnEliminarServicio = new JButton("Eliminar Servicio");
		btnEliminarServicio.setBounds(10, 234, 135, 23);
		btnEliminarServicio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
		        int filaSeleccionada = TableServiciosIncluidos.getSelectedRow();
		        if (filaSeleccionada == -1) {JOptionPane.showMessageDialog(null, "Por favor, seleccione un servicio de la tabla para eliminar.", "Aviso",JOptionPane.WARNING_MESSAGE);
		        return;}
		            DefaultTableModel modelo = (DefaultTableModel) TableServiciosIncluidos.getModel();
		            modelo.removeRow(filaSeleccionada);
		            serviciosSeleccionados.remove(filaSeleccionada);
				     actualizarPrecio(api);
		    }
		});
		contentPane.add(btnEliminarServicio);

		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(10, 113, 71, 14);
		contentPane.add(lblEstado);
		
		 lblCostoRecomendado = new JLabel("Costo recomendado: 0.0");
		lblCostoRecomendado.setBounds(10, 88, 195, 14);
		contentPane.add(lblCostoRecomendado);
		
		JLabel lblDatosObligatorios = new JLabel("Datos obligatorios (*)");
		lblDatosObligatorios.setBounds(10, 14, 107, 14);
		contentPane.add(lblDatosObligatorios);

		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(ev -> {
		    String texto = tfABuscar.getText().trim().toLowerCase();
		    if (texto.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Ingrese un nombre a buscar", "Aviso", JOptionPane.WARNING_MESSAGE);
		        return;
		    }

		    int fila = IntStream.range(0, serviciosSeleccionados.size())
		            .filter(i -> serviciosSeleccionados.get(i).getNombre().toLowerCase().contains(texto))
		            .findFirst()
		            .orElse(-1);

		    if (fila == -1) {
		        JOptionPane.showMessageDialog(null, "No se encontró el servicio", "Aviso", JOptionPane.INFORMATION_MESSAGE);
		        return;
		    }
		    TableServiciosIncluidos.setRowSelectionInterval(fila, fila);
		});
		btnBuscar.setBounds(344, 264, 89, 23);
		contentPane.add(btnBuscar);
		
		tfABuscar = new JTextField();
		tfABuscar.setBounds(155, 265, 182, 22);
		contentPane.add(tfABuscar);
		tfABuscar.setColumns(10);
		
		}
	private void actualizarPrecio(IApi api) {
	    precioRecomendado = api.calcPrecioServicios(serviciosSeleccionados);
	    lblCostoRecomendado.setText("Costo recomendado: " + precioRecomendado);
	}
}
