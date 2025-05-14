package es.studium.SieteYMedia;

import java.awt.BorderLayout;
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

public class Vista
{
	Color clrFondo = new Color(35, 35, 35);
	Color clrBotones = new Color(240, 240, 209);
	
	//VENTANA Menú Principal
	Frame vPrincipal = new Frame("Siete y Media");
	Label lblTitulo = new Label("Menú Principal");
	Button btnNueva = new Button("Nueva Partida");
	Button btnPuntuaciones = new Button("Puntuaciones");
	Button btnAyuda = new Button("Ayuda");
	Button btnSalir = new Button("Salir");
	Panel panel1 = new Panel();
	Panel panel2 = new Panel();
	
	//VENTANA Nueva Partida (Número de jugadores)
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
	Frame vPuntos = new Frame("Top 10");
	TextArea lista = new TextArea(10, 20);
	Button btnVolver = new Button("Volver");
	Panel panel6 = new Panel();
	Modelo modelo = new Modelo();
	
	//VENTANA Nueva Partida (Nombre de jugadores)
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
		//DIÁLOGO Auxiliar
	Dialog dlgNombreVacio = new Dialog(vPrincipal, "Aviso", true);
	Label lblAviso = new Label("Escribe todos los nombres");
	
	//VENTANA Partida
		//DIÁLOGO Confirmar Salir
	Dialog dlgConfirmacion = new Dialog(vPrincipal, "Aviso", true);
	Label lblAvisoSalir = new Label("¿Cancelar partida y volver al menú principal?");
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar = new Button("Cancelar");
	
		//DIÁLOGO Fin de ronda
	Dialog vFinRonda = new Dialog(vPrincipal, "Fin de la Ronda", true);
	Label lblFRonda = new Label("Todos los jugadores han finalizado su turno");
	Label lblInfGanadorR = new Label("Ganador de la Ronda: ");
	Label lblGanadorR = new Label("Ganador"); //Se modifica al finalizar la ronda
	Button btnSigRonda = new Button("Siguiente Ronda");
	Panel panelRonda = new Panel();
	Panel panelRonda2 = new Panel();
	
		//DIÁLOGO Fin de partida
	Dialog vFinPartida = new Dialog(vPrincipal, "Fin de la Partida", true);
	Label lblFPartida = new Label("Todas las rondas han fnalizado");
	Label lblInfGanador = new Label("El ganador de la partida es:");
	Label lblGanador = new Label("Ganador"); //Se modifica al finalizar la partida
	Label lblInfPuntos = new Label("Enhorabuena! Has ganado con una puntuación de: ");
	Label lblPuntos = new Label("Puntos"); //Se modifica al finalizar la partida
	Button btnMenuPrin = new Button("Menú Principal");
	Panel panel7 = new Panel();
	Panel panel8 = new Panel();
	
		//DIÁLOGO Pasarse
	Dialog dlgPasar = new Dialog(vPrincipal, "Información", true);
	Label lblAvisoPasar = new Label("Oh no, te has pasado! :(");
	Label lblInfoPasar = new Label("El valor de tus cartas ahora es 0");
	Label lblInfoPasar2 = new Label("Tu turno ha finalizado");
	Button btnOk = new Button("OK");
	Panel panelPasar = new Panel();
	
		//DIÁLOGO Plantarse
	Dialog dlgPlantar = new Dialog(vPrincipal, "Información", true);
	Label lblAvisoPlantar = new Label("Te has plantado!");
	Label lblAvisoPlantar2 = new Label("Ganas XXX puntos");
	Label lblInfoPlantar = new Label("Tu turno ha finalizado.");
	Button btnOk2 = new Button("OK");
	Panel panelPlantar = new Panel();
	
		//DIÁLOGO Siete y Media
	Dialog dlgSieteMedio = new Dialog(vPrincipal, "Información", true);
	Label lblAvisoSieteMedio = new Label("Siete y Media!");
	Label lblAvisoSieteMedio2 = new Label("Ganas 10 puntos");
	Label lblInfoSieteMedio = new Label("Tu turno ha finalizado");
	Button btnOk3 = new Button("OK");
	Panel panelSMedia = new Panel();
	
		//DIÁLOGO Siete y Media Real
	Dialog dlgSieteReal = new Dialog(vPrincipal, "Información", true);
	Label lblAvisoSieteReal = new Label("Siete y Media Real!");
	Label lblAvisoSieteReal2 = new Label("Ganas 30 puntos");
	Label lblInfoSieteReal = new Label("Tu turno ha finalizado.");
	Button btnOk4 = new Button("OK");
	Panel panelSReal = new Panel();
	
	public Vista() 
	{
		//VENTANA Menú Principal
		vPrincipal.setLayout(new GridLayout(3, 1));
		vPrincipal.setSize(305, 150);
		vPrincipal.setBackground(clrFondo);
		vPrincipal.setResizable(false);
		vPrincipal.setLocationRelativeTo(null);
		lblTitulo.setForeground(new Color(255, 250, 209));
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitulo.setAlignment(Label.CENTER);
		vPrincipal.add(lblTitulo);
		btnNueva.setFont(new Font("Arial", Font.BOLD, 12));
		btnNueva.setBackground(clrBotones);
		btnPuntuaciones.setFont(new Font("Arial", Font.BOLD, 12));
		btnPuntuaciones.setBackground(clrBotones);
		btnAyuda.setFont(new Font("Arial", Font.BOLD, 12));
		btnAyuda.setBackground(clrBotones);
		panel1.add(btnNueva);
		panel1.add(btnPuntuaciones);
		panel1.add(btnAyuda);
		vPrincipal.add(panel1);
		btnSalir.setBackground(Color.gray);
		panel2.add(btnSalir);
		vPrincipal.add(panel2);
		
		//VENTANA Nueva Partida (Número Jugadores)
		vNuevaPartida.setLayout(new GridLayout(3, 1));
		vNuevaPartida.setSize(370, 180);
		vNuevaPartida.setBackground(clrFondo);
		vNuevaPartida.setResizable(true);
		vNuevaPartida.setLocationRelativeTo(null);
		lblNumeroJugadores.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNumeroJugadores.setForeground(new Color(255, 250, 209));
		lblNumeroJugadores.setAlignment(Label.CENTER);
		panel3.add(lblNumeroJugadores);
		vNuevaPartida.add(panel3);
		chk2.setForeground(new Color(255, 250, 209));
		chk2.setFont(new Font("Arial", Font.BOLD, 16));
		chk3.setForeground(new Color(255, 250, 209));
		chk3.setFont(new Font("Arial", Font.BOLD, 16));
		chk4.setForeground(new Color(255, 250, 209));
		chk4.setFont(new Font("Arial", Font.BOLD, 16));
		panel4.add(chk2);
		panel4.add(chk3);
		panel4.add(chk4);
		vNuevaPartida.add(panel4);
		btnContinuar.setBackground(clrBotones);
		panel5.add(btnContinuar);
		vNuevaPartida.add(panel5);
		
		//VENTANA Puntuaciones
		vPuntos.setLayout(new BorderLayout());
		vPuntos.setSize(270, 270);
		vPuntos.setResizable(true);
		vPuntos.setBackground(clrFondo);
		vPuntos.setLocationRelativeTo(null);
		lista.setFont(new Font("Arial", Font.BOLD, 13));
		lista.setBackground(clrBotones);
		lista.setFocusable(false);
		vPuntos.add(lista, BorderLayout.CENTER);
		btnVolver.setBackground(clrBotones);
		panel6.add(btnVolver);
		vPuntos.add(panel6, BorderLayout.SOUTH);		
		
		//VENTANA Nueva Partida (Nombre Jugadores)
		vJ2.setLayout(new FlowLayout());
		vJ2.setSize(265, 200);
		vJ2.setBackground(clrFondo);
		vJ2.setResizable(false);
		vJ2.setLocationRelativeTo(null);
		lblJugador1.setForeground(clrBotones);
		lblJugador1.setFont(new Font("Arial", Font.BOLD, 11));
		vJ2.add(lblJugador1);
		txfnombre1.setBackground(new Color(243, 87, 87));
		txfnombre1.setFont(new Font("Arial", Font.BOLD, 12));
		vJ2.add(txfnombre1);
		lblJugador2.setForeground(clrBotones);
		lblJugador2.setFont(new Font("Arial", Font.BOLD, 11));
		vJ2.add(lblJugador2);
		txfnombre2.setBackground(new Color(113, 240, 126));
		txfnombre2.setFont(new Font("Arial", Font.BOLD, 12));
		vJ2.add(txfnombre2);
		lblJugador3.setForeground(clrBotones);
		lblJugador3.setFont(new Font("Arial", Font.BOLD, 11));
		vJ2.add(lblJugador3);
		txfnombre3.setBackground(new Color(158, 167, 248));
		txfnombre3.setFont(new Font("Arial", Font.BOLD, 12));
		vJ2.add(txfnombre3);
		lblJugador4.setForeground(clrBotones);
		lblJugador4.setFont(new Font("Arial", Font.BOLD, 11));
		vJ2.add(lblJugador4);
		txfnombre4.setBackground(new Color(249, 251, 109));
		txfnombre4.setFont(new Font("Arial", Font.BOLD, 12));
		vJ2.add(txfnombre4);
		btnIniciar.setBackground(clrBotones);
		vJ2.add(btnIniciar);
			//Diálogo Auxiliar
		dlgNombreVacio.setSize(200, 100);
		dlgNombreVacio.setBackground(Color.lightGray);
		dlgNombreVacio.setResizable(false);
		dlgNombreVacio.setLocationRelativeTo(null);
		lblAviso.setAlignment(Label.CENTER);
		dlgNombreVacio.add(lblAviso);
		
		//VENTANA Partida
			//DIÁLOGO Confirmar Salir
		dlgConfirmacion.setLayout(new FlowLayout());
		dlgConfirmacion.setSize(300, 130);
		dlgConfirmacion.setLocationRelativeTo(null);
		dlgConfirmacion.setBackground(Color.lightGray);
		dlgConfirmacion.setResizable(false);
		dlgConfirmacion.add(lblAvisoSalir);
		dlgConfirmacion.add(btnAceptar);
		dlgConfirmacion.add(btnCancelar);
		
			//VENTANA Fin de ronda
		vFinRonda.setLayout(new GridLayout(3, 1));
		vFinRonda.setSize(305, 175);
		vFinRonda.setBackground(clrFondo);
		vFinRonda.setResizable(false);
		vFinRonda.setLocationRelativeTo(null);
		lblFRonda.setForeground(clrBotones);
		lblFRonda.setAlignment(Label.CENTER);
		vFinRonda.add(lblFRonda);
		lblInfGanadorR.setForeground(clrBotones);
		lblGanadorR.setFont(new Font("Arial", Font.BOLD, 14));
		panelRonda.add(lblInfGanadorR);
		panelRonda.add(lblGanadorR);
		vFinRonda.add(panelRonda);
		btnSigRonda.setBackground(clrBotones);
		panelRonda2.add(btnSigRonda);
		vFinRonda.add(panelRonda2);
		
			//VENTANA Fin de partida
		vFinPartida.setLayout(new GridLayout(5, 1));
		vFinPartida.setSize(395, 215);
		vFinPartida.setBackground(clrFondo);
		vFinPartida.setResizable(true);
		vFinPartida.setLocationRelativeTo(null);
		lblFPartida.setAlignment(Label.CENTER);
		lblFPartida.setForeground(clrBotones);
		vFinPartida.add(lblFPartida);
		lblInfGanador.setAlignment(Label.CENTER);
		lblInfGanador.setForeground(clrBotones);
		vFinPartida.add(lblInfGanador);
		lblGanador.setFont(new Font("Arial", Font.BOLD, 14));
		lblGanador.setAlignment(Label.CENTER);
		vFinPartida.add(lblGanador);
		lblInfPuntos.setForeground(clrBotones);
		panel7.add(lblInfPuntos);
		lblPuntos.setFont(new Font("Arial", Font.BOLD, 14));
		panel7.add(lblPuntos);
		vFinPartida.add(panel7);
		btnMenuPrin.setBackground(clrBotones);
		panel8.add(btnMenuPrin);
		vFinPartida.add(panel8);
		
			//DIÁLOGO Pasarse
		dlgPasar.setLayout(new GridLayout(4, 1));
		dlgPasar.setSize(350, 225);
		dlgPasar.setBackground(clrFondo);
		dlgPasar.setResizable(false);
		dlgPasar.setLocationRelativeTo(null);
		lblAvisoPasar.setFont(new Font("Arial", Font.BOLD, 12));
		lblAvisoPasar.setAlignment(Label.CENTER);
		lblAvisoPasar.setForeground(clrBotones);
		lblInfoPasar.setAlignment(Label.CENTER);
		lblInfoPasar.setForeground(clrBotones);
		lblInfoPasar2.setAlignment(Label.CENTER);
		lblInfoPasar2.setForeground(clrBotones);
		dlgPasar.add(lblAvisoPasar);
		dlgPasar.add(lblInfoPasar);
		dlgPasar.add(lblInfoPasar2);
		btnOk.setBackground(clrBotones);
		panelPasar.add(btnOk);
		dlgPasar.add(panelPasar);
		
			//DIÁLOGO Plantarse
		dlgPlantar.setLayout(new GridLayout(4, 1));
		dlgPlantar.setSize(305, 210);
		dlgPlantar.setBackground(clrFondo);
		dlgPlantar.setResizable(false);
		dlgPlantar.setLocationRelativeTo(null);
		lblAvisoPlantar.setFont(new Font("Arial", Font.BOLD, 12));
		lblAvisoPlantar.setAlignment(Label.CENTER);
		lblAvisoPlantar.setForeground(clrBotones);
		lblAvisoPlantar2.setFont(new Font("Arial", Font.BOLD, 13));
		lblAvisoPlantar2.setAlignment(Label.CENTER);
		lblAvisoPlantar2.setForeground(clrBotones);
		lblInfoPlantar.setAlignment(Label.CENTER);
		lblInfoPlantar.setForeground(clrBotones);
		dlgPlantar.add(lblAvisoPlantar);
		dlgPlantar.add(lblAvisoPlantar2);
		dlgPlantar.add(lblInfoPlantar);
		btnOk2.setBackground(clrBotones);
		panelPlantar.add(btnOk2);
		dlgPlantar.add(panelPlantar);
		
			//DIÁLOGO Siete y Medio
		dlgSieteMedio.setLayout(new GridLayout(4, 1));
		dlgSieteMedio.setSize(305, 210);
		dlgSieteMedio.setBackground(clrFondo);
		dlgSieteMedio.setResizable(true);
		dlgSieteMedio.setLocationRelativeTo(null);
		lblAvisoSieteMedio.setFont(new Font("Arial", Font.BOLD, 12));
		lblAvisoSieteMedio.setAlignment(Label.CENTER);
		lblAvisoSieteMedio.setForeground(clrBotones);
		lblAvisoSieteMedio2.setFont(new Font("Arial", Font.BOLD, 13));
		lblAvisoSieteMedio2.setForeground(Color.lightGray);
		lblAvisoSieteMedio2.setAlignment(Label.CENTER);
		lblInfoSieteMedio.setAlignment(Label.CENTER);
		lblInfoSieteMedio.setForeground(clrBotones);
		dlgSieteMedio.add(lblAvisoSieteMedio);
		dlgSieteMedio.add(lblAvisoSieteMedio2);
		dlgSieteMedio.add(lblInfoSieteMedio);
		btnOk3.setBackground(clrBotones);
		panelSMedia.add(btnOk3);
		dlgSieteMedio.add(panelSMedia);
		
			//DIÁLOGO Siete y Medio Real
		dlgSieteReal.setLayout(new GridLayout(4, 1));
		dlgSieteReal.setSize(305, 210);
		dlgSieteReal.setBackground(clrFondo);
		dlgSieteReal.setResizable(true);
		dlgSieteReal.setLocationRelativeTo(null);
		lblAvisoSieteReal.setFont(new Font("Arial", Font.BOLD, 12));
		lblAvisoSieteReal.setAlignment(Label.CENTER);
		lblAvisoSieteReal.setForeground(clrBotones);
		lblAvisoSieteReal2.setFont(new Font("Arial", Font.BOLD, 13));
		lblAvisoSieteReal2.setForeground(Color.lightGray);
		lblAvisoSieteReal2.setAlignment(Label.CENTER);
		lblInfoSieteReal.setAlignment(Label.CENTER);
		lblInfoSieteReal.setForeground(clrBotones);
		dlgSieteReal.add(lblAvisoSieteReal);
		dlgSieteReal.add(lblAvisoSieteReal2);
		dlgSieteReal.add(lblInfoSieteReal);
		btnOk4.setBackground(clrBotones);
		panelSReal.add(btnOk4);
		dlgSieteReal.add(panelSReal);
	}
}
