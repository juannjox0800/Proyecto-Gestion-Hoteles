package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
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
	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
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
		menuHabitacion.add(enlistarHabitacion);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Registrar Habitacion");
		menuHabitacion.add(mntmNewMenuItem);
		
		JMenu menuReserva = new JMenu("Reserva");
		menuBar.add(menuReserva);
		
		JMenuItem enlistarReserva = new JMenuItem("Enlistar Reserva");
		menuReserva.add(enlistarReserva);
		
		JMenuItem registrarReserva = new JMenuItem("Registrar Reserva");
		menuReserva.add(registrarReserva);
		
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
