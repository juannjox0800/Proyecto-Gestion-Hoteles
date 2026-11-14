package ar.edu.unrn.seminario.main;

import java.awt.EventQueue;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.*;
import ar.edu.unrn.seminario.gui.VentanaPrincipal;

public class Main {
	public static void main(String[] args) {
		IApi api = new PersistenceApi();
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal ventana = new VentanaPrincipal(api);
					ventana.setLocationRelativeTo(null);
					ventana.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
