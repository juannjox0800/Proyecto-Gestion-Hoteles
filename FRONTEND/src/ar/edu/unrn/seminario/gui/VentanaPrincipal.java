package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private IApi api;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					IApi api = new ar.edu.unrn.seminario.api.MemoryApi();
					VentanaPrincipal frame = new VentanaPrincipal(api);
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
	public VentanaPrincipal(IApi api) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		this.api = api;
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuCliente = new JMenu("Cliente");
		menuBar.add(menuCliente);
		
		JMenuItem EnlistarClientes = new JMenuItem("Enlistar Clientes");
		menuCliente.add(EnlistarClientes);
		
		JMenuItem altaCliente = new JMenuItem("Registrar Cliente");
		menuCliente.add(altaCliente);
		
		
		JMenu menuHabitacion = new JMenu("Habitacion");
		menuBar.add(menuHabitacion);
		
		JMenuItem enlistarHabitacion = new JMenuItem("Enlistar Habitacion");
		enlistarHabitacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnlistarHabitación ventana = new EnlistarHabitación(api);
				ventana.setVisible(true);
			}
		});
		menuHabitacion.add(enlistarHabitacion);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Registrar Habitacion");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarHabitacion ventana = new RegistrarHabitacion(api, null);
				ventana.setVisible(true);
				
			}});
			
		menuHabitacion.add(mntmNewMenuItem);
		
		JMenu menuReserva = new JMenu("Reserva");
		menuBar.add(menuReserva);
		
		JMenuItem enlistarReserva = new JMenuItem("Enlistar Reserva");
		menuReserva.add(enlistarReserva);
		
		JMenuItem registrarReserva = new JMenuItem("Registrar Reserva");
		menuReserva.add(registrarReserva);
		
		JMenuItem hacerCheckIn = new JMenuItem("Hacer Check-In");
		menuReserva.add(hacerCheckIn);
		
		JMenuItem hacerCheckOut = new JMenuItem("Hacer Check-Out");
		menuReserva.add(hacerCheckOut);
		
		JMenuItem pagarServicioExtra = new JMenuItem("Pagar Servicio Extra");
		menuReserva.add(pagarServicioExtra);
		
		JMenuItem SolicitarServicioExtra = new JMenuItem("Solicitar servicio extra");
		menuReserva.add(SolicitarServicioExtra);
		
		JMenu menuPlan = new JMenu("Plan");
		menuBar.add(menuPlan);
		
		JMenuItem enlistarPlan = new JMenuItem("Enlistar Plan");
		menuPlan.add(enlistarPlan);
		
		JMenuItem registrarPlan = new JMenuItem("Registrar Plan");
		menuPlan.add(registrarPlan);
		
		JMenu menuServicio = new JMenu("Servicio");
		menuBar.add(menuServicio);
		
		JMenuItem enlistarServicio = new JMenuItem("Enlistar Servicio");
		menuServicio.add(enlistarServicio);
		
		JMenuItem registrarServicio = new JMenuItem("Registrar Servicio");
		menuServicio.add(registrarServicio);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

	}

}
