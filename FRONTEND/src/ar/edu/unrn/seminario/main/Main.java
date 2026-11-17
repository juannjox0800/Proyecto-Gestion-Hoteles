package ar.edu.unrn.seminario.main;

import java.awt.EventQueue;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.gui.VentanaPrincipal;

public class Main {
	public static void main(String[] args) {
		//IApi api = new MemoryApi();
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
