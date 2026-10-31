package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.dto.ServicioDTO;

public class VentanaSeleccionarServicio extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList<ServicioDTO> listServiciosDisponibles;
	private IApi api;
	private RegistrarPlan ventanaPadre;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			IApi api = new MemoryApi();
			RegistrarPlan planr= new RegistrarPlan(api);
			public void run() {
				try {
					VentanaSeleccionarServicio frame = new VentanaSeleccionarServicio(api, planr);
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
	public VentanaSeleccionarServicio(IApi api, RegistrarPlan ventanaPadre) {
		
		this.api = api;
		this.ventanaPadre = ventanaPadre;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
        setBounds(150, 150, 300, 350);
        getContentPane().setLayout(null);
        
        List<ServicioDTO> serviciosList = api.obtenerListaServicios();

        
        listServiciosDisponibles = new JList<>(serviciosList.toArray(new ServicioDTO[0]));
        JScrollPane scrollPane = new JScrollPane(listServiciosDisponibles);
		scrollPane.setBounds(30, 30, 240, 180);
		contentPane.add(scrollPane);
		
		JButton btnAceptar = new JButton("Seleccionar");
		btnAceptar.setBounds(95, 230, 120, 25);
		contentPane.add(btnAceptar);
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServicioDTO servicioSeleccionado = listServiciosDisponibles.getSelectedValue();
				
				if (servicioSeleccionado != null) {
					ventanaPadre.agregarServicioATabla(servicioSeleccionado);
					dispose(); 
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, seleccione un servicio de la lista.");
				}
			}
		});
	}

}
