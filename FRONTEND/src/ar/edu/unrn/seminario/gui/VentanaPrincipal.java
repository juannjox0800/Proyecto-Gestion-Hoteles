package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private IApi api;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new MemoryApi();
					VentanaPrincipal frame = new VentanaPrincipal(api);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaPrincipal(IApi api) {
		this.api = api;
		
		setTitle("Sistema de Gestión de Hotel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 350);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuCliente = new JMenu("Cliente");
		menuBar.add(menuCliente);
		
		JMenuItem enlistarClientes = new JMenuItem("Enlistar Clientes");
		enlistarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarClientes ventana = new ListarClientes();
				ventana.setVisible(true);
			}
		});
		menuCliente.add(enlistarClientes);
		
		JMenuItem altaCliente = new JMenuItem("Registrar Cliente");
		altaCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarCliente ventana = new RegistrarCliente();
				ventana.setVisible(true);
			}
		});
		menuCliente.add(altaCliente);
		
		JMenu menuHabitacion = new JMenu("Habitacion");
		menuBar.add(menuHabitacion);
		
		JMenuItem enlistarHabitacion = new JMenuItem("Enlistar Habitacion");
		enlistarHabitacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnlistarHabitación ventana = new EnlistarHabitación();
				ventana.setVisible(true);
			}
		});
		menuHabitacion.add(enlistarHabitacion);
		
		JMenuItem registrarHabitacion = new JMenuItem("Registrar Habitacion");
		registrarHabitacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarHabitacion ventana = new RegistrarHabitacion();
				ventana.setVisible(true);
			}
		});
		menuHabitacion.add(registrarHabitacion);
		
		JMenu menuReserva = new JMenu("Reserva");
		menuBar.add(menuReserva);
		JMenuItem enlistarReserva = new JMenuItem("Enlistar Reserva");
		enlistarReserva.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				VentanaListadoDeReservas ventana= new VentanaListadoDeReservas();
				ventana.setVisible(true);
			}
		});
		menuReserva.add(enlistarReserva);
		JMenuItem registrarReserva = new JMenuItem("Registrar Reserva");
		registrarReserva.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						RealizarReserva ventana= new RealizarReserva();
						ventana.setVisible(true);
					}
				});
		menuReserva.add(registrarReserva);
		
		
		
		JMenuItem hacerCheckIn = new JMenuItem("Hacer Check-In");
		hacerCheckIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CheckIn ventana = new CheckIn();
				ventana.setVisible(true);
			}
		});
		menuReserva.add(hacerCheckIn);
		
		JMenuItem hacerCheckOut = new JMenuItem("Hacer Check-Out");
		hacerCheckOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CheckOut ventana = new CheckOut();
				ventana.setVisible(true);
			}
		});
		menuReserva.add(hacerCheckOut);
		
		JMenuItem pagarServicioExtra = new JMenuItem("Pagar Servicio Extra");
		pagarServicioExtra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarPago ventana = new RegistrarPago();
				ventana.setVisible(true);
			}
		});
		menuReserva.add(pagarServicioExtra);
		
		JMenuItem solicitarServicioExtra = new JMenuItem("Solicitar servicio extra");
		solicitarServicioExtra.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				SolicitarServicio ventana = new SolicitarServicio();
				ventana.setVisible(true);
			}
		});
		menuReserva.add(solicitarServicioExtra);
		
		JMenu menuPlan = new JMenu("Plan");
		menuBar.add(menuPlan);
		
		JMenuItem enlistarPlan = new JMenuItem("Enlistar Plan");
		enlistarPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListaPlanes ventana = new ListaPlanes();
				ventana.setVisible(true);
			}
		});
		menuPlan.add(enlistarPlan);
		
		JMenuItem registrarPlan = new JMenuItem("Registrar Plan");
		registrarPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarPlan ventana = new RegistrarPlan(api);
				ventana.setVisible(true);
			}
		});
		menuPlan.add(registrarPlan);
		
		JMenu menuServicio = new JMenu("Servicio");
		menuBar.add(menuServicio);
		
		JMenuItem enlistarServicio = new JMenuItem("Enlistar Servicio");
		enlistarServicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListaServicios ventana = new ListaServicios();
				ventana.setVisible(true);
			}
		});
		menuServicio.add(enlistarServicio);
		
		JMenuItem registrarServicio = new JMenuItem("Registrar Servicio");
		registrarServicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarServicio ventana = new RegistrarServicio();
				ventana.setVisible(true);
			}
		});
		menuServicio.add(registrarServicio);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
}