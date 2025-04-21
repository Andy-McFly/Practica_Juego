package es.studium.SieteYMedia;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;

//Clase en Progreso. (Borrar este comentario al acabar)
public class Vista
{
	//VENTANA Menú Principal
	Frame vPrincipal = new Frame("Siete y Media");
	Label lblTitulo = new Label("Menú Principal");
	Button btnNueva = new Button("Nueva Partida");
	Button btnPuntuaciones = new Button("Puntuaciones");
	Button btnAyuda = new Button("Ayuda");
	Button btnSalir = new Button("Salir");
	Panel panel1 = new Panel();
	Panel panel2 = new Panel();
	
	//VENTANA Nueva Partida
	Frame vNuevaPartida = new Frame("Siete y Media: Nueva Partida");
	Label lblNumeroJugadores = new Label("Elegir número de jugadores:");
	CheckboxGroup gpJugadores = new CheckboxGroup();
	Checkbox chk2 = new Checkbox("2", true, gpJugadores);
	Checkbox chk3 = new Checkbox("3", false, gpJugadores);
	Checkbox chk4 = new Checkbox("4", false, gpJugadores);
	Button btnContinuar = new Button("Continuar...");
	Panel panel3 = new Panel();
	Panel panel4 = new Panel();
	Panel panel5 = new Panel();
	
	//VENTANA Puntuaciones
	Frame vPuntos = new Frame("Stiete y Media: Puntuaciones");
	TextArea lista = new TextArea(20, 15);
	Button btnVolver = new Button("Volver");
	Panel panel6 = new Panel();
	Modelo modelo = new Modelo();
	
	//VENTANA Ayuda (en progreso)
	
	
	//VENTANA Nombre Jugadores
	Frame vJ2 = new Frame("Stiete y Media: Nueva Partida");
	Label lblJugador1 = new Label("Jugador 1:");
	TextField txfnombre1 = new TextField(15);
	Label lblJugador2 = new Label("Jugador 2:");
	TextField txfnombre2 = new TextField(15);
	Label lblJugador3 = new Label("Jugador 3:");
	TextField txfnombre3 = new TextField(15);
	Label lblJugador4 = new Label("Jugador 4:");
	TextField txfnombre4 = new TextField(15);
	Button btnIniciar = new Button("Iniciar Partida");
	Dialog dlgNombreVacio = new Dialog(vPrincipal, "Aviso", true);
	Label lblAviso = new Label("Escribe todos los nombres");
	
	//VENTANA Partida (en progreso)
	//Temporal
	Frame vProvisional = new Frame("Provisional");
	
	//VENTANA Fin de ronda (en progreso)
	
	//VENTANA Fin de partida (en progreso)
	
	//DIÁLOGO Pasarse
	Dialog dlgPasar = new Dialog(vPrincipal, "Información", true);
	Label lblAvisoPasar = new Label("Te has pasado! :(");
	Label lblInfoPasar = new Label("El valor de tus cartas ahora es 0. Tu turno ha finalizado.");
	Button btnOk = new Button("OK");
	
	//DIÁLOGO Plantarse
	Dialog dlgPlantar = new Dialog(vPrincipal, "Información", true);
	Label lblAvisoPlantar = new Label("Te has plantado!");
	Label lblInfoPlantar = new Label("Tu turno ha finalizado.");
	Button btnOk2 = new Button("OK");
	
	//DIÁLOGO Siete y Media
	Dialog dlgSieteMedio = new Dialog(vPrincipal, "Información", true);
	Label lblAvisoSieteMedio = new Label("Siete y Media!");
	Label lblInfoSieteMedio = new Label("Tu turno ha finalizado.");
	Button btnOk3 = new Button("OK");
	
	//DIÁLOGO Siete y Media Real
	Dialog dlgSieteReal = new Dialog(vPrincipal, "Información", true);
	Label lblAvisoSieteReal = new Label("Siete y Media Real!");
	Label lblInfoSieteReal = new Label("Tu turno ha finalizado.");
	Button btnOk4 = new Button("OK");
	
	public Vista() 
	{
		//VENTANA Menú Principal
		vPrincipal.setLayout(new FlowLayout());
		vPrincipal.setSize(305, 150);
		vPrincipal.setBackground(new Color(147, 142, 240));
		vPrincipal.setResizable(false);
		vPrincipal.setLocationRelativeTo(null);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitulo.setAlignment(Label.CENTER);
		vPrincipal.add(lblTitulo);
		panel1.add(btnNueva);
		panel1.add(btnPuntuaciones);
		panel1.add(btnAyuda);
		vPrincipal.add(panel1);
		btnSalir.setBackground(Color.lightGray);
		panel2.add(btnSalir);
		vPrincipal.add(panel2);
		vPrincipal.setVisible(true);
		
		//VENTANA Nueva Partida (Número Jugadores)
		vNuevaPartida.setLayout(new GridLayout(3, 1));
		vNuevaPartida.setSize(370, 180);
		vNuevaPartida.setBackground(new Color(147, 142, 240));
		vNuevaPartida.setResizable(true);
		vNuevaPartida.setLocationRelativeTo(null);
		lblNumeroJugadores.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNumeroJugadores.setAlignment(Label.CENTER);
		panel3.add(lblNumeroJugadores);
		vNuevaPartida.add(panel3);
		panel4.add(chk2);
		panel4.add(chk3);
		panel4.add(chk4);
		vNuevaPartida.add(panel4);
		panel5.add(btnContinuar);
		vNuevaPartida.add(panel5);
		
		//VENTANA Puntuaciones
		vPuntos.setLayout(new GridLayout(2, 1));
		vPuntos.setSize(340, 340);
		vPuntos.setResizable(true);
		vPuntos.setBackground(new Color(147, 142, 240));
		vPuntos.setLocationRelativeTo(null);
		lista.setFocusable(false);
		vPuntos.add(lista);
		btnVolver.setBackground(Color.lightGray);
		panel6.add(btnVolver);
		vPuntos.add(panel6);
				
		//VENTANA Ayuda (en progreso)
		
		
		//VENTANA Nueva Partida (Nombre Jugadores)
		vJ2.setLayout(new FlowLayout());
		vJ2.setSize(265, 200);
		vJ2.setBackground(new Color(147, 142, 240));
		vJ2.setResizable(false);
		vJ2.setLocationRelativeTo(null);
		vJ2.add(lblJugador1);
		vJ2.add(txfnombre1);
		vJ2.add(lblJugador2);
		vJ2.add(txfnombre2);
		vJ2.add(lblJugador3);
		vJ2.add(txfnombre3);
		vJ2.add(lblJugador4);
		vJ2.add(txfnombre4);
		vJ2.add(btnIniciar);
		//Diálogo Aviso
		dlgNombreVacio.setSize(200, 100);
		dlgNombreVacio.setBackground(Color.lightGray);
		dlgNombreVacio.setResizable(false);
		dlgNombreVacio.setLocationRelativeTo(null);
		lblAviso.setAlignment(Label.CENTER);
		dlgNombreVacio.add(lblAviso);
		
		//VENTANA Partida (en progreso)
		//Temporal
		vProvisional.setLayout(new FlowLayout());
		vProvisional.setSize(400, 400);
		vProvisional.setBackground(new Color(147, 142, 240));
		vProvisional.setResizable(false);
		vProvisional.setLocationRelativeTo(null);
		
		//VENTANA Fin de ronda (en progreso)
		
		//VENTANA Fin de partida (en progreso)
		
		//DIÁLOGO Pasarse
		dlgPasar.setLayout(new FlowLayout());
		dlgPasar.setSize(350, 140);
		dlgPasar.setBackground(new Color(155, 152, 213));
		dlgPasar.setResizable(false);
		dlgPasar.setLocationRelativeTo(null);
		lblAvisoPasar.setFont(new Font("Arial", Font.BOLD, 12));
		lblAvisoPasar.setAlignment(Label.CENTER);
		lblInfoPasar.setAlignment(Label.CENTER);
		dlgPasar.add(lblAvisoPasar);
		dlgPasar.add(lblInfoPasar);
		btnOk.setBackground(new Color(147, 142, 240));
		dlgPasar.add(btnOk);
		
		//DIÁLOGO Plantarse
		dlgPlantar.setLayout(new FlowLayout());
		dlgPlantar.setSize(180, 140);
		dlgPlantar.setBackground(new Color(155, 152, 213));
		dlgPlantar.setResizable(true);
		dlgPlantar.setLocationRelativeTo(null);
		lblAvisoPlantar.setFont(new Font("Arial", Font.BOLD, 12));
		lblAvisoPlantar.setAlignment(Label.CENTER);
		lblInfoPlantar.setAlignment(Label.CENTER);
		dlgPlantar.add(lblAvisoPlantar);
		dlgPlantar.add(lblInfoPlantar);
		btnOk2.setBackground(new Color(147, 142, 240));
		dlgPlantar.add(btnOk2);
		
		//DIÁLOGO Siete y Medio (en progreso)
		
		//DIÁLOGO Siete y Medio Real (en progreso)
		
	}
	
}


