package es.studium.SieteYMedia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;

public class Controlador implements WindowListener, ActionListener
{
	Modelo modelo;
	Vista vista;
	Connection connection = null;
	int jugadores;
	String jugador1, jugador2, jugador3, jugador4;
	
	public Controlador(Modelo m, Vista v) 
	{
		modelo = m;
		vista = v;
		//Ventanas
		v.vPrincipal.addWindowListener(this);
		v.vNuevaPartida.addWindowListener(this);
		v.vPuntos.addWindowListener(this);
		v.vJ2.addWindowListener(this);
		v.dlgNombreVacio.addWindowListener(this);
		
		//Botones
		v.btnNueva.addActionListener(this);
		v.btnContinuar.addActionListener(this);
		v.btnIniciar.addActionListener(this);
		v.btnPuntuaciones.addActionListener(this);
		v.btnVolver.addActionListener(this);
		v.btnSalir.addActionListener(this);
		v.btnAceptar.addActionListener(this);
		v.btnCancelar.addActionListener(this);
		v.btnAyuda.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
	//VENTANA MENÚ PRINCIPAL
		//BOTÓN Nueva Partida
		if (e.getSource().equals(vista.btnNueva))
		{
			vista.txfnombre1.setText("");
			vista.txfnombre2.setText("");
			vista.txfnombre3.setText("");
			vista.txfnombre4.setText("");
			vista.vNuevaPartida.setVisible(true);
		}
		
		//BOTÓN Puntuaciones
		else if (e.getSource().equals(vista.btnPuntuaciones))
		{
			vista.lista.setText("");
			connection = modelo.conectar();
			vista.lista.append(modelo.obtenerRanking(connection));
			modelo.desconectar(connection);
			vista.vPrincipal.setVisible(false);
			vista.vPuntos.setVisible(true);
		}
		
		//BOTÓN Ayuda
		else if (e.getSource().equals(vista.btnAyuda))
		{
			modelo.webAyuda();
		}
		
		//BOTÓN Salir
		else if (e.getSource().equals(vista.btnSalir))
		{
			System.exit(0);
		}
		
	//VENTANA NUEVA PARTIDA (Seleccionar número de jugadores)
		//BOTÓN Continuar... 
		else if (e.getSource().equals(vista.btnContinuar))
		{
			if(vista.chk2.getState()==true)
			  {
				jugadores = 2;
				vista.txfnombre3.setVisible(false);
				vista.lblJugador3.setVisible(false);
				vista.txfnombre4.setVisible(false);
				vista.lblJugador4.setVisible(false);
				vista.vJ2.setTitle("2 Jugadores");
				vista.vJ2.setVisible(true);
				vista.vNuevaPartida.setVisible(false);
			  }
			else if(vista.chk3.getState()==true)
			  {
				jugadores = 3;
				vista.txfnombre3.setVisible(true);
				vista.lblJugador3.setVisible(true);
				vista.txfnombre4.setVisible(false);
				vista.lblJugador4.setVisible(false);
				vista.vJ2.setTitle("3 Jugadores");
				vista.vJ2.setVisible(true);
				vista.vNuevaPartida.setVisible(false);
			  }
			else if(vista.chk4.getState()==true)
			  {
				jugadores = 4;
				vista.txfnombre3.setVisible(true);
				vista.lblJugador3.setVisible(true);
				vista.txfnombre4.setVisible(true);
				vista.lblJugador4.setVisible(true);
				vista.vJ2.setTitle("4 Jugadores");
				vista.vJ2.setVisible(true);
				vista.vNuevaPartida.setVisible(false);
			  }
		}
		
		// BOTÓN Iniciar Partida (Escribir nombre de jugadores)
		else if (e.getSource().equals(vista.btnIniciar))
		{
			jugador1 = vista.txfnombre1.getText();
			jugador2 = vista.txfnombre2.getText();
			jugador3 = vista.txfnombre3.getText();
			jugador4 = vista.txfnombre4.getText();
			
			switch(jugadores) 
			{
			case 2:
				//Si los nombres no están en blanco
				if((!jugador1.isBlank()) && (!jugador2.isBlank())) 
				{
					//Si los nombres no son iguales
					if(!jugador1.equals(jugador2)) 
					{
						//Comprobar que los nombres no esén ya registrados en el Top 10
						connection = modelo.conectar();
						if ((modelo.comprobarNombre(connection, jugador1)) || (modelo.comprobarNombre(connection, jugador2))) 
						{
							vista.lblAviso.setText("El nombre introducido ya existe");
							vista.dlgNombreVacio.setVisible(true);
						}
						else 
						{
							new Partida(vista, modelo);
							vista.vJ2.dispose();
						}
						modelo.desconectar(connection);
					}
					
					else 
					{
						vista.lblAviso.setText("Los nombres deben ser distintos");
						vista.dlgNombreVacio.setVisible(true);
					}
				}
				else 
				{
					vista.lblAviso.setText("Escribe todos los nombres");
					vista.dlgNombreVacio.setVisible(true);
				}
				break;
				
			case 3:
				if((!jugador1.isBlank()) && (!jugador2.isBlank()) && (!jugador3.isBlank())) 
				{
					if((!jugador1.equals(jugador2)) && (!jugador1.equals(jugador3)) && (!jugador2.equals(jugador3))) 
					{
						connection = modelo.conectar();
						if ((modelo.comprobarNombre(connection, jugador1)) || (modelo.comprobarNombre(connection, jugador2)) 
								|| (modelo.comprobarNombre(connection, jugador3))) 
						{
							vista.lblAviso.setText("El nombre introducido ya existe");
							vista.dlgNombreVacio.setVisible(true);
						}
						else 
						{
							new Partida(vista, modelo);
							vista.vJ2.dispose();
						}
						modelo.desconectar(connection);
					}
					
					else 
					{
						vista.lblAviso.setText("Los nombres deben ser distintos");
						vista.dlgNombreVacio.setVisible(true);
					}
				}
				else 
				{
					vista.lblAviso.setText("Escribe todos los nombres");
					vista.dlgNombreVacio.setVisible(true);
				}
				break;
				
			case 4:
				if((!jugador1.isBlank()) && (!jugador2.isBlank()) && (!jugador3.isBlank()) && (!jugador4.isBlank())) 
				{
					if((!jugador1.equals(jugador2)) && (!jugador1.equals(jugador3)) && (!jugador1.equals(jugador4)) 
							&& (!jugador2.equals(jugador3)) && (!jugador2.equals(jugador4)) && (!jugador3.equals(jugador4))) 
					{
						connection = modelo.conectar();
						if ((modelo.comprobarNombre(connection, jugador1)) || (modelo.comprobarNombre(connection, jugador2)) 
								|| (modelo.comprobarNombre(connection, jugador3)) || (modelo.comprobarNombre(connection, jugador4))) 
						{
							vista.lblAviso.setText("El nombre introducido ya existe");
							vista.dlgNombreVacio.setVisible(true);
						}
						else 
						{
							new Partida(vista, modelo);
							vista.vJ2.dispose();
						}
						modelo.desconectar(connection);
					}
					
					
					else 
					{
						vista.lblAviso.setText("Los nombres deben ser distintos");
						vista.dlgNombreVacio.setVisible(true);
					}
				}
				else 
				{
					vista.lblAviso.setText("Escribe todos los nombres");
					vista.dlgNombreVacio.setVisible(true);
				}
				break;
			}
		}
		
	//VENTANA PUNTUACIONES
		//BOTÓN Volver
		else if (e.getSource().equals(vista.btnVolver))
		{
			vista.vPuntos.dispose();
			vista.vPrincipal.setVisible(true);
		}
		
	//VENTANA Partida
		//DIÁLOGO Confirmar Salir
		else if (e.getSource().equals(vista.btnAceptar)) 
		{
			vista.dlgConfirmacion.dispose();
		}
		else if (e.getSource().equals(vista.btnCancelar)) 
		{
			vista.dlgConfirmacion.dispose();
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e)
	{
		if (e.getSource().equals(vista.vPrincipal))
		{
			System.exit(0);
		}
		else if(e.getSource().equals(vista.vNuevaPartida)) 
		{
			vista.vNuevaPartida.dispose();
			vista.vPrincipal.setVisible(true);
		}
		else if(e.getSource().equals(vista.vPuntos)) 
		{
			vista.vPuntos.dispose();
			vista.vPrincipal.setVisible(true);
		}
		else if(e.getSource().equals(vista.dlgNombreVacio)) 
		{
			vista.dlgNombreVacio.dispose();
		}
		else if(e.getSource().equals(vista.vJ2)) 
		{
			vista.vJ2.dispose();
			vista.vPrincipal.setVisible(true);
		}
	}

	@Override public void windowActivated(WindowEvent e){}@Override public void windowClosed(WindowEvent e){}
	@Override public void windowDeactivated(WindowEvent e){}@Override public void windowDeiconified(WindowEvent e){}
	@Override public void windowIconified(WindowEvent e){}@Override public void windowOpened(WindowEvent e){}
}
