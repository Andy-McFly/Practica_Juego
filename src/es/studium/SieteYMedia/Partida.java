package es.studium.SieteYMedia;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;

public class Partida extends Frame implements WindowListener, ActionListener, MouseListener
{
	// Serializado
	private static final long serialVersionUID = 1L;
	// Imágenes y herramienta
	Image tapete, cartaReverso, mazo;
	Image carta1, carta2, carta3, carta4, carta5, carta6, carta7, carta8, carta9, carta10, carta11, carta12, carta13,
			carta14; // Cartas máximas que se van a colocar en el tablero
	Toolkit herramienta;
	
	// Conexión
	Connection connection = null;
	
	// Clases
	Vista vista;
	Modelo modelo;
	
	// Fuentes
	Font fntTurno = new Font("Arial", Font.BOLD, 14);
	Font fntPuntos = new Font("Arial", Font.BOLD, 13);
	
	// Botón Plantarse
	Button btnPlantarse = new Button("Plantarse");
	
	// Declaración de variables
	//Cartas
	String[] cartas = { "P1", "P2", "P3", "P4", "P5", "P6", "P7", "PJ", "PQ", "PR", // Picas
						"C1", "C2", "C3", "C4", "C5", "C6", "C7", "CJ", "CQ", "CR", // Corazones
						"T1", "T2", "T3", "T4", "T5", "T6", "T7", "TJ", "TQ", "TR", // Tréboles
						"D1", "D2", "D3", "D4", "D5", "D6", "D7", "DJ", "DQ", "DR" }; // Diamantes
	//Valor de la carta robada
	String valorCarta = "";
	//Nombre de los jugadores
	String nombreJugador1, nombreJugador2, nombreJugador3, nombreJugador4;
	//Coordenadas del ratón
	int clickX, clickY;
	//Turno del jugador y ronda de la partida
	int turno, ronda;
	//Número de cartas en juego y posición que ocupa la siguiente en el tablero
	int colocarCarta = 0;
	//Número de jugadores en la partida
	int jugadores = 0;
	//Puntos de la ronda de cada jugador
	float puntosRondaJ1, puntosRondaJ2, puntosRondaJ3, puntosRondaJ4;
	//Puntos totales de la partida de cada jugador
	float puntosTotalesJ1, puntosTotalesJ2, puntosTotalesJ3, puntosTotalesJ4;
	//Valor máximo de los puntos comparando a cada jugador. Si hay más de un jugador con el mismo valor máximo es un empate.
	float puntosMaximos;
	//Puntos de la ronda actual. Muestra también el valor de las cartas en juego. Al finalizar el turno se asignan al jugador correspondiente.
	float puntosRonda;
	//Muestra o no el efecto al robar una carta del mazo
	boolean efectoMazo = false;
	//Confirma si se ha producido o no un empate de ronda/partida
	boolean empate = false;
	
	//VENTANA Partida en curso
	public Partida(Vista v, Modelo m)
	{
		// Preparar partida
		vista = v;
		modelo = m;
		nombreJugador1 = vista.txfnombre1.getText();
		nombreJugador2 = vista.txfnombre2.getText();
		nombreJugador3 = vista.txfnombre3.getText();
		nombreJugador4 = vista.txfnombre4.getText();
		turno = 1;
		ronda = 1;
		empate = false;
		puntosRondaJ1 = 0;
		puntosRondaJ2 = 0;
		puntosRondaJ3 = 0;
		puntosRondaJ4 = 0;
		puntosTotalesJ1 = 0;
		puntosTotalesJ2 = 0;
		puntosTotalesJ3 = 0;
		puntosTotalesJ4 = 0;
		puntosMaximos = 0;
		puntosRonda = 0;
		
		// Diseño de la ventana
		setLayout(null);
		setSize(1062, 678);
		setTitle("Siete y Media: Partida en curso");
		setBackground(new Color(30, 30, 30));
		setResizable(true);
		setLocationRelativeTo(null);
		addWindowListener(this);
		addMouseListener(this);
		vista.btnOk.addActionListener(this);
		vista.btnOk2.addActionListener(this);
		vista.btnOk3.addActionListener(this);
		vista.btnOk4.addActionListener(this);
		vista.btnAceptar.addActionListener(this);
		vista.btnSigRonda.addActionListener(this);
		vista.btnMenuPrin.addActionListener(this);
		vista.btnReiniciar.addActionListener(this);
		btnPlantarse.addActionListener(this);
		btnPlantarse.setBounds(480, 502, 100, 40);
		btnPlantarse.setFont(new Font("Arial", Font.BOLD, 16));
		add(btnPlantarse);
		
		// Imágenes
		herramienta = getToolkit();
		tapete = herramienta.getImage("img\\tapete.png");
		cartaReverso = herramienta.getImage("img\\cartaReverso.png");
		mazo = herramienta.getImage("img\\mazo.png");
		
		// Funciones
		//Conocer número de jugadores
		jugadores = numeroJugadores();
		//Cargar las cartas al mazo
		cargarCartas();
		
		repaint();
		setVisible(true);
	}

	public void paint(Graphics g)
	{
		// Dibujar estilo
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, 1062, 70);
		g.fillRect(0, 491, 1062, 60);
		g.fillRect(462, 584, 142, 33);
		g.drawRect(480, 502, 100, 40);
		g.drawImage(tapete, 0, 70, this);
		g.drawImage(mazo, 470, 320, this);
		g.setColor(Color.white);
		g.setFont(fntPuntos);
		g.drawString("Roba una carta:", 468, 310);
		
		// Cambiar estilo según turno del jugador
		if (turno == 1)
		{
			g.drawString("Puntos Ronda "+ronda+":  " + puntosRondaJ1, 38, 631);
			g.setColor(new Color(243, 87, 87));
			g.setFont(fntTurno);
			g.drawString("Turno "+turno+":  " + nombreJugador1, 460, 57);
			g.drawString("Valor Cartas: " + puntosRonda, 470, 606);
			btnPlantarse.setBackground(new Color(243, 87, 87));
		} 
		else if (turno == 2)
		{
			g.setColor(Color.white);
			g.drawString("Puntos Ronda "+ronda+":  " + puntosRondaJ2, 38, 631);
			g.setColor(new Color(113, 240, 126));
			g.setFont(fntTurno);
			g.drawString("Turno "+turno+":  " + nombreJugador2, 460, 57);
			g.drawString("Valor Cartas: " + puntosRonda, 470, 606);
			btnPlantarse.setBackground(new Color(113, 240, 126));
		} 
		else if (turno == 3)
		{
			g.setColor(Color.white);
			g.drawString("Puntos Ronda "+ronda+":  " + puntosRondaJ3, 38, 631);
			g.setColor(new Color(158, 167, 248));
			g.setFont(fntTurno);
			g.drawString("Turno "+turno+":  " + nombreJugador3, 460, 57);
			g.drawString("Valor Cartas: " + puntosRonda, 470, 606);
			btnPlantarse.setBackground(new Color(158, 167, 248));
		} 
		else if (turno == 4)
		{
			g.setColor(Color.white);
			g.drawString("Puntos Ronda "+ronda+":  " + puntosRondaJ4, 38, 631);
			g.setColor(new Color(249, 251, 109));
			g.setFont(fntTurno);
			g.drawString("Turno "+turno+":  " + nombreJugador4, 460, 57);
			g.drawString("Valor Cartas: " + puntosRonda, 470, 606);
			btnPlantarse.setBackground(new Color(249, 251, 109));
		}
		
		// Efecto al pulsar el mazo, centrando la imagen creada a la posición aproximada del ratón(-40, -60)
		if (efectoMazo)
		{
			g.drawImage(cartaReverso, clickX - 40, clickY - 60, this);
		}
		
		// Colocar las cartas en el tablero según la posición que le corresponda
		if (colocarCarta == 1)
		{
			g.drawImage(carta1, 84, 114, this);
		} 
		else if (colocarCarta == 2)
		{
			g.drawImage(carta1, 84, 114, this);
			g.drawImage(carta2, 134, 114, this);
		} 
		else if (colocarCarta == 3)
		{
			g.drawImage(carta1, 84, 114, this);
			g.drawImage(carta2, 134, 114, this);
			g.drawImage(carta3, 184, 114, this);
		} 
		else if (colocarCarta == 4)
		{
			g.drawImage(carta1, 84, 114, this);
			g.drawImage(carta2, 134, 114, this);
			g.drawImage(carta3, 184, 114, this);
			g.drawImage(carta4, 234, 114, this);
		} 
		else if (colocarCarta == 5)
		{
			g.drawImage(carta1, 84, 114, this);
			g.drawImage(carta2, 134, 114, this);
			g.drawImage(carta3, 184, 114, this);
			g.drawImage(carta4, 234, 114, this);
			g.drawImage(carta5, 284, 114, this);
		} 
		else if (colocarCarta == 6)
		{
			g.drawImage(carta1, 84, 114, this);
			g.drawImage(carta2, 134, 114, this);
			g.drawImage(carta3, 184, 114, this);
			g.drawImage(carta4, 234, 114, this);
			g.drawImage(carta5, 284, 114, this);
			g.drawImage(carta6, 334, 114, this);
		} 
		else if (colocarCarta == 7)
		{
			g.drawImage(carta1, 84, 114, this);
			g.drawImage(carta2, 134, 114, this);
			g.drawImage(carta3, 184, 114, this);
			g.drawImage(carta4, 234, 114, this);
			g.drawImage(carta5, 284, 114, this);
			g.drawImage(carta6, 334, 114, this);
			g.drawImage(carta7, 384, 114, this);
		} 
		else if (colocarCarta == 8)
		{
			g.drawImage(carta1, 84, 114, this);
			g.drawImage(carta2, 134, 114, this);
			g.drawImage(carta3, 184, 114, this);
			g.drawImage(carta4, 234, 114, this);
			g.drawImage(carta5, 284, 114, this);
			g.drawImage(carta6, 334, 114, this);
			g.drawImage(carta7, 384, 114, this);
			g.drawImage(carta8, 434, 114, this);
		} 
		else if (colocarCarta == 9)
		{
			g.drawImage(carta1, 84, 114, this);
			g.drawImage(carta2, 134, 114, this);
			g.drawImage(carta3, 184, 114, this);
			g.drawImage(carta4, 234, 114, this);
			g.drawImage(carta5, 284, 114, this);
			g.drawImage(carta6, 334, 114, this);
			g.drawImage(carta7, 384, 114, this);
			g.drawImage(carta8, 434, 114, this);
			g.drawImage(carta9, 484, 114, this);
		} 
		else if (colocarCarta == 10)
		{
			g.drawImage(carta1, 84, 114, this);
			g.drawImage(carta2, 134, 114, this);
			g.drawImage(carta3, 184, 114, this);
			g.drawImage(carta4, 234, 114, this);
			g.drawImage(carta5, 284, 114, this);
			g.drawImage(carta6, 334, 114, this);
			g.drawImage(carta7, 384, 114, this);
			g.drawImage(carta8, 434, 114, this);
			g.drawImage(carta9, 484, 114, this);
			g.drawImage(carta10, 534, 114, this);
		} 
		else if (colocarCarta == 11)
		{
			g.drawImage(carta1, 84, 114, this);
			g.drawImage(carta2, 134, 114, this);
			g.drawImage(carta3, 184, 114, this);
			g.drawImage(carta4, 234, 114, this);
			g.drawImage(carta5, 284, 114, this);
			g.drawImage(carta6, 334, 114, this);
			g.drawImage(carta7, 384, 114, this);
			g.drawImage(carta8, 434, 114, this);
			g.drawImage(carta9, 484, 114, this);
			g.drawImage(carta10, 534, 114, this);
			g.drawImage(carta11, 584, 114, this);
		} 
		else if (colocarCarta == 12)
		{
			g.drawImage(carta1, 84, 114, this);
			g.drawImage(carta2, 134, 114, this);
			g.drawImage(carta3, 184, 114, this);
			g.drawImage(carta4, 234, 114, this);
			g.drawImage(carta5, 284, 114, this);
			g.drawImage(carta6, 334, 114, this);
			g.drawImage(carta7, 384, 114, this);
			g.drawImage(carta8, 434, 114, this);
			g.drawImage(carta9, 484, 114, this);
			g.drawImage(carta10, 534, 114, this);
			g.drawImage(carta11, 584, 114, this);
			g.drawImage(carta12, 634, 114, this);
		} 
		else if (colocarCarta == 13)
		{
			g.drawImage(carta1, 84, 114, this);
			g.drawImage(carta2, 134, 114, this);
			g.drawImage(carta3, 184, 114, this);
			g.drawImage(carta4, 234, 114, this);
			g.drawImage(carta5, 284, 114, this);
			g.drawImage(carta6, 334, 114, this);
			g.drawImage(carta7, 384, 114, this);
			g.drawImage(carta8, 434, 114, this);
			g.drawImage(carta9, 484, 114, this);
			g.drawImage(carta10, 534, 114, this);
			g.drawImage(carta11, 584, 114, this);
			g.drawImage(carta12, 634, 114, this);
			g.drawImage(carta13, 684, 114, this);
		} 
		else if (colocarCarta == 14)
		{
			g.drawImage(carta1, 84, 114, this);
			g.drawImage(carta2, 134, 114, this);
			g.drawImage(carta3, 184, 114, this);
			g.drawImage(carta4, 234, 114, this);
			g.drawImage(carta5, 284, 114, this);
			g.drawImage(carta6, 334, 114, this);
			g.drawImage(carta7, 384, 114, this);
			g.drawImage(carta8, 434, 114, this);
			g.drawImage(carta9, 484, 114, this);
			g.drawImage(carta10, 534, 114, this);
			g.drawImage(carta11, 584, 114, this);
			g.drawImage(carta12, 634, 114, this);
			g.drawImage(carta13, 684, 114, this);
			g.drawImage(carta14, 734, 114, this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		//Confirmar Salir (Botón Aceptar)
		if (e.getSource().equals(vista.btnAceptar))
		{
			//Reinicia parámetros y cierra la ventana
			turno = 1;
			ronda = 1;
			jugadores = 0;
			dispose();
		}
		
		//Fin de Ronda (Botón Siguiente Ronda)
		else if(e.getSource().equals(vista.btnSigRonda)) 
		{
			//Reinicia los puntos de la ronda, la posición de las cartas y el turno. Carga un nuevo mazo barajado.
			puntosRonda = 0;
			colocarCarta = 0;
			cargarCartas();
			turno = 1;
			repaint();
			vista.vFinRonda.setVisible(false);
		}
		
		//Fin de Partida
		//(Botón Menú Principal)
		else if (e.getSource().equals(vista.btnMenuPrin)) 
		{
			//Reinicia parámetros
			turno = 1;
			ronda = 1;
			jugadores = 0;
			//Si hay empate las ventanas se cierran
			if (empate) 
			{
				dispose();
				vista.vFinPartida.setVisible(false);
			}
			//Si no hay empate, el ganador se registra en la BD y las ventanas se cierran
			else 
			{
				connection = modelo.conectar();
				modelo.altaJugador(connection, vista.lblGanador.getText(), Float.parseFloat(vista.lblPuntos.getText()));
				modelo.desconectar(connection);
				dispose();
				vista.vFinPartida.setVisible(false);
			}
		}
		//(Botón Reiniciar)
		else if (e.getSource().equals(vista.btnReiniciar)) 
		{
			//Reinicia parámetros
			turno = 1;
			ronda = 1;
			jugadores = 0;
			vista.txfnombre1.setText("");
			vista.txfnombre2.setText("");
			vista.txfnombre3.setText("");
			vista.txfnombre4.setText("");
			
			if (empate) 
			{
				//Si hay empate las ventanas se cierran
				dispose();
				vista.vFinPartida.setVisible(false);
				//Muestra la ventana de selección de jugadores
				vista.vNuevaPartida.setVisible(true);
			}
			else 
			{
				//Si no hay empate, el ganador se registra en la BD y las ventanas se cierran
				connection = modelo.conectar();
				modelo.altaJugador(connection, vista.lblGanador.getText(), Float.parseFloat(vista.lblPuntos.getText()));
				modelo.desconectar(connection);
				dispose();
				vista.vFinPartida.setVisible(false);
				//Muestra la ventana de selección de jugadores
				vista.vNuevaPartida.setVisible(true);
			}
		}
		
		//Botón Plantarse 
		else if (e.getSource().equals(btnPlantarse))
		{
			//Averigua el jugador que ha pulsado el botón para ajustar la información y el estilo del mensaje
			switch (turno)
			{
			case 1:
				puntosRondaJ1 = puntosRondaJ1 + puntosRonda;
				vista.lblAvisoPlantar2.setText("Ganas "+puntosRondaJ1+" puntos");
				vista.lblAvisoPlantar2.setForeground(new Color(243, 87, 87));
				break;
			case 2:
				puntosRondaJ2 = puntosRondaJ2 + puntosRonda;
				vista.lblAvisoPlantar2.setText("Ganas "+puntosRondaJ2+" puntos");
				vista.lblAvisoPlantar2.setForeground(new Color(113, 240, 126));
				break;
			case 3:
				puntosRondaJ3 = puntosRondaJ3 + puntosRonda;
				vista.lblAvisoPlantar2.setText("Ganas "+puntosRondaJ3+" puntos");
				vista.lblAvisoPlantar2.setForeground(new Color(158, 167, 248));
				break;
			case 4:
				puntosRondaJ4 = puntosRondaJ4 + puntosRonda;
				vista.lblAvisoPlantar2.setText("Ganas "+puntosRondaJ4+" puntos");
				vista.lblAvisoPlantar2.setForeground(new Color(249, 251, 109));
				break;
			}
			//Actualiza los puntos de la ronda dibujados en la partida
			repaint();
			//Reproduce un sonido al pulsar el botón y muestra el mensaje de información
			modelo.SonidoPlantarse();
			vista.dlgPlantar.setVisible(true);
		}
		
		//Pasarse (Botón OK)
		else if (e.getSource().equals(vista.btnOk))
		{
			//Reinicia los puntos de la ronda y la posición de las cartas para el siguiente jugador
			puntosRonda = 0;
			colocarCarta = 0;
			//Carga las cartas barajadas
			cargarCartas();
			//Identifica el turno y la ronda actual de la partida
			estadoPartida();
			//Actualiza los puntos de la ronda dibujados en la partida
			repaint();
			//Se cierra la ventana
			vista.dlgPasar.dispose();
		}
		
		//Plantarse (Botón OK)
		else if (e.getSource().equals(vista.btnOk2))
		{
			//Reinicia los puntos de la ronda y la posición de las cartas para el siguiente jugador
			puntosRonda = 0;
			colocarCarta = 0;
			//Carga las cartas barajadas
			cargarCartas();
			//Identifica el turno y la ronda actual de la partida
			estadoPartida();
			//Actualiza los puntos de la ronda dibujados en la partida
			repaint();
			//Se cierra la ventana
			vista.dlgPlantar.dispose();
		}
		
		//Siete y Media (Botón OK)
		else if (e.getSource().equals(vista.btnOk3))
		{
			//Reinicia los puntos de la ronda y la posición de las cartas para el siguiente jugador
			puntosRonda = 0;
			colocarCarta = 0;
			//Carga las cartas barajadas
			cargarCartas();
			//Identifica el turno y la ronda actual de la partida
			estadoPartida();
			//Actualiza los puntos de la ronda dibujados en la partida
			repaint();
			//Se cierra la ventana
			vista.dlgSieteMedio.dispose();
		}
		
		//Siete y Media Real (Botón OK)
		else if (e.getSource().equals(vista.btnOk4))
		{
			puntosRonda = 0;
			colocarCarta = 0;
			cargarCartas();
			estadoPartida();
			repaint();
			vista.dlgSieteReal.dispose();
		} 
	}

	public void mouseClicked(MouseEvent me)
	{
		// Ayuda para el diseño. No influye en la partida.
		clickX = me.getX();
		clickY = me.getY();
		System.out.println("Clic en X: " + clickX + ", Y: " + clickY);
	}

	public void mousePressed(MouseEvent me)
	{
		// Obtener coordenadas del ratón
		clickX = me.getX();
		clickY = me.getY();
		//Si el ratón pulsa en las coordenadas de la posición del mazo
		if (clickX >= 470 && clickX <= 582 && clickY >= 320 && clickY <= 470)
		{
			//Muestra el efecto de robar carta
			efectoMazo = true;
			repaint();
		}
	}

	public void mouseReleased(MouseEvent me)
	{
		//Valor de la primera carta colocada enel tablero
		char valor1;
		//Valor de la segunda carta colocada enel tablero
		char valor2;
		//Confirma si hay o no un 7 entre las dos primeras cartas
		boolean siete = false;
		//Confirma si hay o no una figura entre las dos primeras cartas
		boolean figura = false;
		//Desaparece el efecto al robar
		efectoMazo = false;
		//Guarda el valor (nombre) de la carta que se coloque en el tablero
		valorCarta = cartas[colocarCarta];
		//Posición de la próxima carta que sea robada
		colocarCarta++;
		//Actualiza las cartas dibujadas antes de mostrar algún mensaje
		repaint();
		//Reproduce un sonido al colocar una carta en el tablero
		modelo.SonidoCarta();
		//Se suma el valor de la carta colocada en el tablero a los puntos de la ronda
		puntosRonda = puntosRonda + cartaColocada(valorCarta);
		//Identifica el valor de las dos primeras cartas usando el segundo carácter (segundo carácter de P1, P2, CJ...)
		valor1 = cartas[0].charAt(1);
		valor2 = cartas[1].charAt(1);
		// Si hay 7 en la primera o segunda carta
		if ((valor1 == '7') || (valor2 == '7'))
		{
			siete = true;
		}
		// Si hay figura en la primera o segunda carta
		if ((valor1 == 'J') || (valor1 == 'Q') || (valor1 == 'R') || (valor2 == 'J') || (valor2 == 'Q') || (valor2 == 'R'))
		{
			figura = true;
		}
		//Siete y Media Real
		/* Si en las dos primeras cartas (única oportunidad de conseguir Siete y Media Real sin pasarse) 
		 * hay una figura y un 7 (sumando 7.5).*/
		if (puntosRonda==7.5 && siete && figura)
		{
			//Identifica al jugador que ha tenido la jugada para asignarle los puntos correspondientes
			switch (turno)
			{
			case 1:
				puntosRondaJ1 = puntosRondaJ1 + 30;
				break;
			case 2:
				puntosRondaJ2 = puntosRondaJ2 + 30;
				break;
			case 3:
				puntosRondaJ3 = puntosRondaJ3 + 30;
				break;
			case 4:
				puntosRondaJ4 = puntosRondaJ4 + 30;
				break;
			}
			//Reproduce un sonido al sacar la jugada
			modelo.SonidoPlantarse();
			//Informa sobre la jugada
			vista.dlgSieteReal.setVisible(true);
		}
		// Siete y Media normal
		//Si suma 7.5 y no es la segunda carta colocada (evita que se ejecute cuando Siete y Media Real)
		else if (puntosRonda == 7.5 && colocarCarta != 2)
		{
			//Identifica al jugador que ha tenido la jugada para asignarle los puntos correspondientes
			switch (turno)
			{
			case 1:
				puntosRondaJ1 = puntosRondaJ1 + 10;
				break;
			case 2:
				puntosRondaJ2 = puntosRondaJ2 + 10;
				break;
			case 3:
				puntosRondaJ3 = puntosRondaJ3 + 10;
				break;
			case 4:
				puntosRondaJ4 = puntosRondaJ4 + 10;
				break;
			}
			//Reproduce un sonido al sacar la jugada
			modelo.SonidoPlantarse();
			//Informa sobre la jugada
			vista.dlgSieteMedio.setVisible(true);
		}
		// Si el jugador se pasa del 7.5
		else if (puntosRonda > 7.5)
		{
			//Reproduce un sonido al pasarse
			modelo.SonidoPasarse();
			//Informa sobre la jugada
			vista.dlgPasar.setVisible(true);
		}
		repaint();
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		//Muestra el mensaje de confirmar salir
		if (e.getSource().equals(this))
		{
			vista.dlgConfirmacion.setVisible(true);
		}
	}
	
	//Obtener el número de jugadores en la partida
	public int numeroJugadores()
	{
		//Analiza los checkboxes de la ventana de selección de jugadores para obtener la opción escogida
		int jugadores = 0;
		if(vista.chk2.getState()==true) 
		{
			jugadores = 2;
		}
		if(vista.chk3.getState()==true) 
		{
			jugadores = 3;
		}
		if(vista.chk4.getState()==true) 
		{
			jugadores = 4;
		}
		//Devuelve el número de jugadores seleccionado
		return jugadores;
	}
	
	//Al finalizar cada turno y cada ronda, comprueba el estado de la partida. Guarda los puntos Totales de cada jugador.
	public void estadoPartida() 
	{
		//Si todos los jugadores han realizado su turno
		if(turno==jugadores) 
		{
			//Si se han jugado todas las rondas
			if(ronda==3) 
			{
				//Se suman los puntos obtenidos en la ronda a los puntos totales de cada jugador
				puntosTotalesJ1 = puntosTotalesJ1 + puntosRondaJ1;
				puntosTotalesJ2 = puntosTotalesJ2 + puntosRondaJ2;
				puntosTotalesJ3 = puntosTotalesJ3 + puntosRondaJ3;
				puntosTotalesJ4 = puntosTotalesJ4 + puntosRondaJ4;
				//Calcula el ganador de la partida para mostrar la información
				vista.lblGanador.setText(calcularGanadorPartida());
				//Reinicia los puntos de la ronda de cada jugador
				puntosRondaJ1 = 0;
				puntosRondaJ2 = 0;
				puntosRondaJ3 = 0;
				puntosRondaJ4 = 0;
				//Reproduce un sonido al finalizar la partida
				modelo.SonidoGanador();
				//Muestra la información del ganador de la partida
				vista.vFinPartida.setVisible(true);
				
				//Ayuda para el desarrollo. No influye en la partida
				System.out.println(puntosTotalesJ1+ " " + puntosTotalesJ2 + " " + puntosTotalesJ3 + " " + puntosTotalesJ4);
			}
			//Si aún faltan rondas por jugar
			else 
			{
				//Se suman los puntos obtenidos en la ronda a los puntos totales de cada jugador
				puntosTotalesJ1 = puntosTotalesJ1 + puntosRondaJ1;
				puntosTotalesJ2 = puntosTotalesJ2 + puntosRondaJ2;
				puntosTotalesJ3 = puntosTotalesJ3 + puntosRondaJ3;
				puntosTotalesJ4 = puntosTotalesJ4 + puntosRondaJ4;
				//Calcula el ganador de la ronda para mostrar la información
				vista.lblGanadorR.setText(calcularGanadorRonda());
				//Reinicia los puntos de la ronda de cada jugador
				puntosRondaJ1 = 0;
				puntosRondaJ2 = 0;
				puntosRondaJ3 = 0;
				puntosRondaJ4 = 0;
				//Reproduce un sonido al finalizar la ronda
				modelo.SonidoRonda();
				//Muestra la información con el ganador de la ronda
				vista.vFinRonda.setVisible(true);
				//Pasa a la siguiente ronda
				ronda++;
				
				//Ayuda para el desarrollo. No influye en la partida
				System.out.println(puntosTotalesJ1+ " " + puntosTotalesJ2 + " " + puntosTotalesJ3 + " " + puntosTotalesJ4);
			}
		}
		//Si el turno no ha llegado al máximo de jugadores
		else 
		{
			//Pasa al siguiente turno
			turno++;
		}
	}
	
	//Calcula el ganador de la ronda comparando los puntos de cada jugador
	public String calcularGanadorRonda() 
	{
		//Almacena el nombre del jugador con más puntos
		String ganador = "";
		//Puntos máximos al finalizar la ronda
		float puntosRondaMaximos = 0;
		//Número de jugadores con el mismo valor de puntos
		int contador = 0;
		
		/*Compara los puntos de cada jugador con los puntos máximos actuales de la ronda 
		 * y establece como ganador al que guarde el valor más alto.
		 * Cambia el estilo y la información según el jugador que gane.*/
		if(puntosRondaJ1 > puntosRondaMaximos) 
		{
			puntosRondaMaximos = puntosRondaJ1;
			ganador = nombreJugador1;
			vista.lblGanadorR.setForeground(new Color(243, 87, 87));
		}
		if(puntosRondaJ2 > puntosRondaMaximos) 
		{
			puntosRondaMaximos = puntosRondaJ2;
			ganador = nombreJugador2;
			vista.lblGanadorR.setForeground(new Color(113, 240, 126));
		}
		if(puntosRondaJ3 > puntosRondaMaximos) 
		{
			puntosRondaMaximos = puntosRondaJ3;
			ganador = nombreJugador3;
			vista.lblGanadorR.setForeground(new Color(158, 167, 248));
		}
		if(puntosRondaJ4 > puntosRondaMaximos) 
		{
			puntosRondaMaximos = puntosRondaJ4;
			ganador = nombreJugador4;
			vista.lblGanadorR.setForeground(new Color(249, 251, 109));
		}
		
		//Analiza si hay empate
		if (puntosRondaJ1==puntosRondaMaximos) 
		{
			contador++;
		}
		if (puntosRondaJ2==puntosRondaMaximos) 
		{
			contador++;
		}
		if (puntosRondaJ3==puntosRondaMaximos) 
		{
			contador++;
		}
		if (puntosRondaJ4==puntosRondaMaximos) 
		{
			contador++;
		}
		//Si hay más de un jugador con el mismo valor de puntos máximos, habrá empate.
		if (contador>1) 
		{
			ganador = "Empate";
			vista.lblGanadorR.setForeground(Color.gray);
		}
		
		//Devuelve el nombre del ganador o el empate
		return ganador;
	}
	
	//Calcula el ganador de la partida comparando los puntos totales de cada jugador
	public String calcularGanadorPartida() 
	{
		String ganador = "";
		int contador = 0;
		puntosMaximos = 0;
		
		/*Compara los puntos totales de cada jugador con los puntos máximos actuales de la partida 
		 * y establece como ganador al que guarde el valor más alto.
		 * Cambia el estilo y la información según el jugador que gane.*/
		if(puntosTotalesJ1 > puntosMaximos) 
		{
			puntosMaximos = puntosTotalesJ1;
			ganador = nombreJugador1;
			vista.lblGanador.setForeground(new Color(243, 87, 87));
			vista.lblPuntos.setText(puntosTotalesJ1+"");
			vista.lblPuntos.setForeground(new Color(243, 87, 87));
		}
		if(puntosTotalesJ2 > puntosMaximos) 
		{
			puntosMaximos = puntosTotalesJ2;
			ganador = nombreJugador2;
			vista.lblGanador.setForeground(new Color(113, 240, 126));
			vista.lblPuntos.setText(puntosTotalesJ2+"");
			vista.lblPuntos.setForeground(new Color(113, 240, 126));
		}
		if(puntosTotalesJ3 > puntosMaximos) 
		{
			puntosMaximos = puntosTotalesJ3;
			ganador = nombreJugador3;
			vista.lblGanador.setForeground(new Color(158, 167, 248));
			vista.lblPuntos.setText(puntosTotalesJ3+"");
			vista.lblPuntos.setForeground(new Color(158, 167, 248));
		}
		if(puntosTotalesJ4 > puntosMaximos) 
		{
			puntosMaximos = puntosTotalesJ4;
			ganador = nombreJugador4;
			vista.lblGanador.setForeground(new Color(249, 251, 109));
			vista.lblPuntos.setText(puntosTotalesJ4+"");
			vista.lblPuntos.setForeground(new Color(249, 251, 109));
		}
		
		//Empate
		if (puntosTotalesJ1==puntosMaximos) 
		{
			contador++;
		}
		if (puntosTotalesJ2==puntosMaximos) 
		{
			contador++;
		}
		if (puntosTotalesJ3==puntosMaximos) 
		{
			contador++;
		}
		if (puntosTotalesJ4==puntosMaximos) 
		{
			contador++;
		}
		if (contador>1) 
		{
			ganador = "Empate!";
			vista.lblInfPuntos.setText("Han empatado con una puntuación de :");
			vista.lblGanador.setForeground(Color.gray);
			vista.lblPuntos.setText(puntosMaximos+"");
			vista.lblPuntos.setForeground(Color.gray);
			empate = true;
		}
		
		return ganador;
	}
	
	//Barajar las cartas y cargar sus imágenes según el orden de la mezcla recibida
	public void cargarCartas() 
	{
		//Barajar las cartas
		modelo.barajar(cartas);
		//Cargar las cartas barajadas al mazo
		carta1 = herramienta.getImage("img\\" + cartas[0] + ".png");
		carta2 = herramienta.getImage("img\\" + cartas[1] + ".png");
		carta3 = herramienta.getImage("img\\" + cartas[2] + ".png");
		carta4 = herramienta.getImage("img\\" + cartas[3] + ".png");
		carta5 = herramienta.getImage("img\\" + cartas[4] + ".png");
		carta6 = herramienta.getImage("img\\" + cartas[5] + ".png");
		carta7 = herramienta.getImage("img\\" + cartas[6] + ".png");
		carta8 = herramienta.getImage("img\\" + cartas[7] + ".png");
		carta9 = herramienta.getImage("img\\" + cartas[8] + ".png");
		carta10 = herramienta.getImage("img\\" + cartas[9] + ".png");
		carta11 = herramienta.getImage("img\\" + cartas[10] + ".png");
		carta12 = herramienta.getImage("img\\" + cartas[11] + ".png");
		carta13 = herramienta.getImage("img\\" + cartas[12] + ".png");
		carta14 = herramienta.getImage("img\\" + cartas[13] + ".png");
		// 14 es el máximo número de cartas que habrá en juego para cada jugador. Es la jugada más baja.
	}
	
	// Obtiene el valor de cada carta que se coloque en el tablero (segundo carácter de P1, P2, CJ...)
	public float cartaColocada(String valorCarta)
	{
		//Valor de la carta
		float puntos = 0;
		//Según el segundo carácter de cada nombre de carta se obtiene un valor en puntos
		switch (valorCarta.charAt(1))
		{
		case '1':
			puntos = 1;
			break;
		case '2':
			puntos = 2;
			break;
		case '3':
			puntos = 3;
			break;
		case '4':
			puntos = 4;
			break;
		case '5':
			puntos = 5;
			break;
		case '6':
			puntos = 6;
			break;
		case '7':
			puntos = 7;
			break;
		case 'J':
			puntos = 0.5f;
			break;
		case 'Q':
			puntos = 0.5f;
			break;
		case 'R':
			puntos = 0.5f;
			break;
		}
		//Devuelve el valor en puntos de la carta colocada en el tablero
		return puntos;
	}

	public void mouseEntered(MouseEvent me){}public void mouseExited(MouseEvent me){}
	@Override public void windowActivated(WindowEvent e){}@Override public void windowClosed(WindowEvent e){}
	@Override public void windowDeactivated(WindowEvent e){}@Override public void windowDeiconified(WindowEvent e){}
	@Override public void windowIconified(WindowEvent e){}@Override public void windowOpened(WindowEvent e){}
}
