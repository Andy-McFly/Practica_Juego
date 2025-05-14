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
	// Cargar imágenes y herramienta
	Image tapete, cartaReverso, mazo;
	Image carta1, carta2, carta3, carta4, carta5, carta6, carta7, carta8, carta9, carta10, carta11, carta12, carta13,
			carta14; // Cartas máximas que se van a colocar en el tablero
	Toolkit herramienta;
	// Clases
	Connection connection = null;
	Vista vista;
	Modelo modelo;
	Font fntTurno = new Font("Arial", Font.BOLD, 14);
	Font fntPuntos = new Font("Arial", Font.BOLD, 13);
	Button btnPlantarse = new Button("Plantarse");
	// Variables
	String[] cartas = { "P1", "P2", "P3", "P4", "P5", "P6", "P7", "PJ", "PQ", "PR", // Picas
						"C1", "C2", "C3", "C4", "C5", "C6", "C7", "CJ", "CQ", "CR", // Corazones
						"T1", "T2", "T3", "T4", "T5", "T6", "T7", "TJ", "TQ", "TR", // Tréboles
						"D1", "D2", "D3", "D4", "D5", "D6", "D7", "DJ", "DQ", "DR" }; // Diamantes
	String valorCarta = "";
	String nombreJugador1, nombreJugador2, nombreJugador3, nombreJugador4;
	int clickX, clickY;
	int turno, ronda;
	int colocarCarta = 0;
	int jugadores = 0;
	float puntosRondaJ1, puntosRondaJ2, puntosRondaJ3, puntosRondaJ4;
	float puntosTotalesJ1, puntosTotalesJ2, puntosTotalesJ3, puntosTotalesJ4;
	float puntosMaximos, puntosRonda;
	boolean efectoMazo = false;
	boolean empate = false;
	
	//VENTANA Partida en curso
	public Partida(Vista v, Modelo m)
	{
		vista = v;
		modelo = m;
		//Preparar partida
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
		// Diseño
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
		btnPlantarse.addActionListener(this);
		btnPlantarse.setBounds(480, 502, 100, 40);
		btnPlantarse.setFont(new Font("Arial", Font.BOLD, 16));
		add(btnPlantarse);
		// Imágenes
		herramienta = getToolkit();
		tapete = herramienta.getImage("img\\tapete.png");
		cartaReverso = herramienta.getImage("img\\cartaReverso.png");
		mazo = herramienta.getImage("img\\mazo.png");
		jugadores = numeroJugadores();
		cargarCartas();
		repaint();
		setVisible(true);
	}

	public void paint(Graphics g)
	{
		// Diseño gráficos
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
		// Cambiar diseño según turno del jugador
		if (turno == 1)
		{
			g.drawString("Puntos Ronda "+ronda+":  " + puntosRondaJ1, 38, 631);
			g.setColor(new Color(243, 87, 87));
			g.setFont(fntTurno);
			g.drawString("Turno "+turno+":  " + nombreJugador1, 460, 57);
			g.drawString("Valor Ronda: " + puntosRonda, 470, 606);
			btnPlantarse.setBackground(new Color(243, 87, 87));
		} 
		else if (turno == 2)
		{
			g.setColor(Color.white);
			g.drawString("Puntos Ronda "+ronda+":  " + puntosRondaJ2, 38, 631);
			g.setColor(new Color(113, 240, 126));
			g.setFont(fntTurno);
			g.drawString("Turno "+turno+":  " + nombreJugador2, 460, 57);
			g.drawString("Valor Ronda: " + puntosRonda, 470, 606);
			btnPlantarse.setBackground(new Color(113, 240, 126));
		} 
		else if (turno == 3)
		{
			g.setColor(Color.white);
			g.drawString("Puntos Ronda "+ronda+":  " + puntosRondaJ3, 38, 631);
			g.setColor(new Color(158, 167, 248));
			g.setFont(fntTurno);
			g.drawString("Turno "+turno+":  " + nombreJugador3, 460, 57);
			g.drawString("Valor Ronda: " + puntosRonda, 470, 606);
			btnPlantarse.setBackground(new Color(158, 167, 248));
		} 
		else if (turno == 4)
		{
			g.setColor(Color.white);
			g.drawString("Puntos Ronda "+ronda+":  " + puntosRondaJ4, 38, 631);
			g.setColor(new Color(249, 251, 109));
			g.setFont(fntTurno);
			g.drawString("Turno "+turno+":  " + nombreJugador4, 460, 57);
			g.drawString("Valor Ronda: " + puntosRonda, 470, 606);
			btnPlantarse.setBackground(new Color(249, 251, 109));
		}
		// Efecto al pulsar el mazo, centrando imagen a la posición aproximada del ratón(-40, -60)
		if (efectoMazo)
		{
			g.drawImage(cartaReverso, clickX - 40, clickY - 60, this);
		}
		// Colocar cartas en el tablero
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
		//Confirmar Salir
		if (e.getSource().equals(vista.btnAceptar))
		{
			turno = 1;
			ronda = 1;
			jugadores = 0;
			dispose();
		}
		
		//Fin de Ronda
		else if(e.getSource().equals(vista.btnSigRonda)) 
		{
			puntosRonda = 0;
			colocarCarta = 0;
			cargarCartas();
			turno = 1;
			repaint();
			vista.vFinRonda.setVisible(false);
		}
		
		//Fin de Partida
		else if (e.getSource().equals(vista.btnMenuPrin)) 
		{
			turno = 1;
			ronda = 1;
			jugadores = 0;
			
			if (empate) 
			{
				dispose();
				vista.vFinPartida.setVisible(false);
			}
			else 
			{
				connection = modelo.conectar();
				modelo.altaJugador(connection, vista.lblGanador.getText(), Float.parseFloat(vista.lblPuntos.getText()));
//				modelo.actualizarTop10(connection);
				modelo.desconectar(connection);
				dispose();
				vista.vFinPartida.setVisible(false);
			}
			
			
		}
		
		//Plantarse Botón
		else if (e.getSource().equals(btnPlantarse))
		{
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
			repaint();
			modelo.SonidoPlantarse();
			vista.dlgPlantar.setVisible(true);
		}
		//Pasarse
		else if (e.getSource().equals(vista.btnOk))
		{
			puntosRonda = 0;
			colocarCarta = 0;
			cargarCartas();
			estadoPartida();
			repaint();
			vista.dlgPasar.dispose();
		}
		//Plantarse (Información)
		else if (e.getSource().equals(vista.btnOk2))
		{
			puntosRonda = 0;
			colocarCarta = 0;
			cargarCartas();
			estadoPartida();
			repaint();
			vista.dlgPlantar.dispose();
		}
		//Siete y Media
		else if (e.getSource().equals(vista.btnOk3))
		{
			puntosRonda = 0;
			colocarCarta = 0;
			cargarCartas();
			estadoPartida();
			repaint();
			vista.dlgSieteMedio.dispose();
		}
		//Siete y Media Real
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
		// Establecer que APAREZCA la imagen al pulsar el mazo
		clickX = me.getX();
		clickY = me.getY();
		//Posición del mazo
		if (clickX >= 470 && clickX <= 582 && clickY >= 320 && clickY <= 470)
		{
			efectoMazo = true;
			repaint();
		}
	}

	public void mouseReleased(MouseEvent me)
	{
		char valor1;
		char valor2;
		boolean siete = false;
		boolean figura = false;
		efectoMazo = false;
		//Guarda el valor (nombre) de la carta que se coloca en el tablero
		valorCarta = cartas[colocarCarta];
		if (clickX >= 470 && clickX <= 582 && clickY >= 320 && clickY <= 470)
		{
			colocarCarta++;
			repaint();
			modelo.SonidoCarta();
			// Se aplica el valor de las cartas en el tablero a los puntos de la ronda
			puntosRonda = puntosRonda + obtenerPuntosRonda(valorCarta);
			// Identificamos el valor de las dos primeras cartas con el segundo carácter
			valor1 = cartas[0].charAt(1);
			valor2 = cartas[1].charAt(1);
			// Si hay 7 en la primera o segunda carta
			if ((valor1 == '7') || (valor2 == '7'))
			{
				siete = true;
			}
			// Si hay figura en la primera o segunda carta
			if ((valor1 == 'J') || (valor1 == 'Q') || (valor1 == 'R') || (valor2 == 'J') || (valor2 == 'Q')
					|| (valor2 == 'R'))
			{
				figura = true;
			}
			/* Siete y Media Real. Si en las dos primeras cartas (única oportunidad de
			 * conseguir Siete y Media Real sin pasarse) hay una figura y un 7.*/
			if (puntosRonda==7.5 && siete && figura)
			{
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
				modelo.SonidoPlantarse();
				vista.dlgSieteReal.setVisible(true);
			}
			// Siete y Media normal
			else if (puntosRonda == 7.5 && colocarCarta != 2)
			{
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
				modelo.SonidoPlantarse();
				vista.dlgSieteMedio.setVisible(true);
			}
			// Si el jugador se pasa del 7.5
			else if (puntosRonda > 7.5)
			{
				modelo.SonidoPasarse();
				vista.dlgPasar.setVisible(true);
			}
			repaint();
		}
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		if (e.getSource().equals(this))
		{
			vista.dlgConfirmacion.setVisible(true);
		}
	}
	
	//Obtener el número de jugadores en la partida
	public int numeroJugadores()
	{
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
		return jugadores;
	}
	
	//Comprueba los turnos y las rondas. Guarda los puntos Totales de cada jugador.
	public void estadoPartida() 
	{
		if(turno==jugadores) 
		{
			if(ronda==3) 
			{
				puntosTotalesJ1 = puntosTotalesJ1 + puntosRondaJ1;
				puntosTotalesJ2 = puntosTotalesJ2 + puntosRondaJ2;
				puntosTotalesJ3 = puntosTotalesJ3 + puntosRondaJ3;
				puntosTotalesJ4 = puntosTotalesJ4 + puntosRondaJ4;
				vista.lblGanador.setText(calcularGanadorPartida());
				puntosRondaJ1 = 0;
				puntosRondaJ2 = 0;
				puntosRondaJ3 = 0;
				puntosRondaJ4 = 0;
				modelo.SonidoGanador();
				vista.vFinPartida.setVisible(true);
				//Ayuda para el desarrollo. No influye en la partida
				System.out.println(puntosTotalesJ1+ " " + puntosTotalesJ2 + " " + puntosTotalesJ3 + " " + puntosTotalesJ4);
			}
			else 
			{
				puntosTotalesJ1 = puntosTotalesJ1 + puntosRondaJ1;
				puntosTotalesJ2 = puntosTotalesJ2 + puntosRondaJ2;
				puntosTotalesJ3 = puntosTotalesJ3 + puntosRondaJ3;
				puntosTotalesJ4 = puntosTotalesJ4 + puntosRondaJ4;
				vista.lblGanadorR.setText(calcularGanadorRonda());
				puntosRondaJ1 = 0;
				puntosRondaJ2 = 0;
				puntosRondaJ3 = 0;
				puntosRondaJ4 = 0;
				modelo.SonidoRonda();
				vista.vFinRonda.setVisible(true);
				ronda++;
				//Ayuda para el desarrollo. No influye en la partida
				System.out.println(puntosTotalesJ1+ " " + puntosTotalesJ2 + " " + puntosTotalesJ3 + " " + puntosTotalesJ4);
			}
		}
		else 
		{
			turno++;
		}
	}
	
	public String calcularGanadorRonda() 
	{
		String ganador = "";
		float puntosRondaMaximos = 0;
		int contador = 0;
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
		//Empate
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
		if (contador>1) 
		{
			ganador = "Empate";
			vista.lblGanadorR.setForeground(Color.gray);
		}
		
		return ganador;
	}
	
	public String calcularGanadorPartida() 
	{
		String ganador = "";
		int contador = 0;
		puntosMaximos = 0;
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
	
	//Barajar y cargar las imágenes según el orden de la mezcla
	public void cargarCartas() 
	{
		modelo.barajar(cartas);
		// 14 es el máximo número de cartas que habrá en juego para cada jugador. Es la jugada más baja.
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
	}
	
	// Obtiene los puntos correspondientes al valor de cada carta (segundo carácter de P1, P2, CJ...)
	public float obtenerPuntosRonda(String valorCarta)
	{
		float puntos = 0;
		switch (valorCarta.charAt(1))
		{
		case '1':
			puntos = puntos + 1;
			break;
		case '2':
			puntos = puntos + 2;
			break;
		case '3':
			puntos = puntos + 3;
			break;
		case '4':
			puntos = puntos + 4;
			break;
		case '5':
			puntos = puntos + 5;
			break;
		case '6':
			puntos = puntos + 6;
			break;
		case '7':
			puntos = puntos + 7;
			break;
		case 'J':
			puntos = puntos + 0.5f;
			break;
		case 'Q':
			puntos = puntos + 0.5f;
			break;
		case 'R':
			puntos = puntos + 0.5f;
			break;
		}
		return puntos;
	}

	public void mouseEntered(MouseEvent me){}public void mouseExited(MouseEvent me){}
	@Override public void windowActivated(WindowEvent e){}@Override public void windowClosed(WindowEvent e){}
	@Override public void windowDeactivated(WindowEvent e){}@Override public void windowDeiconified(WindowEvent e){}
	@Override public void windowIconified(WindowEvent e){}@Override public void windowOpened(WindowEvent e){}
}
