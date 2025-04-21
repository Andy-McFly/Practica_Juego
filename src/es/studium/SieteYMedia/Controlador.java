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
	
	public Controlador(Modelo m, Vista v) 
	{
		modelo = m;
		vista = v;
		//Ventanas (en progreso)
		v.vPrincipal.addWindowListener(this);
		v.vNuevaPartida.addWindowListener(this);
		v.vPuntos.addWindowListener(this);
		v.vJ2.addWindowListener(this);
		v.dlgNombreVacio.addWindowListener(this);
		v.vProvisional.addWindowListener(this); //Temporal
		v.dlgPasar.addWindowListener(this);
		v.dlgPlantar.addWindowListener(this);
		
		//Botones (en progreso)
		v.btnNueva.addActionListener(this);
		v.btnContinuar.addActionListener(this);
		v.btnIniciar.addActionListener(this);
		v.btnPuntuaciones.addActionListener(this);
		v.btnVolver.addActionListener(this);
		v.btnSalir.addActionListener(this);
		v.btnOk.addActionListener(this);
		v.btnOk2.addActionListener(this);
		
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
			vista.vPrincipal.setVisible(false);
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
		
		//BOTÓN Ayuda (en progreso)
		
		
		//BOTÓN Salir
		else if (e.getSource().equals(vista.btnSalir))
		{
			System.exit(0);
		}
		
	//VENTANA NUEVA PARTIDA
		//BOTÓN Continuar... (Seleccionar número de jugadores)
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
			switch(jugadores) 
			{
			case 2:
				if((!vista.txfnombre1.getText().isBlank()) && (!vista.txfnombre2.getText().isBlank())) 
				{
					connection = modelo.conectar();
					if ((modelo.altaJugador(connection, vista.txfnombre1.getText())) 
							&& (modelo.altaJugador(connection, vista.txfnombre2.getText())))
					{
						vista.vProvisional.setVisible(true);
						vista.vJ2.dispose();
					}
					else 
					{
						vista.lblAviso.setText("El nombre introducido ya existe");
						vista.dlgNombreVacio.setVisible(true);
					}
					
					modelo.desconectar(connection);
					
				}
				else 
				{
					vista.lblAviso.setText("Escribe todos los nombres");
					vista.dlgNombreVacio.setVisible(true);
				}
				break;
			case 3:
				if((!vista.txfnombre1.getText().isBlank()) && (!vista.txfnombre2.getText().isBlank())
						&& (!vista.txfnombre3.getText().isBlank())) 
				{
					connection = modelo.conectar();
					if ((modelo.altaJugador(connection, vista.txfnombre1.getText())) 
							&& (modelo.altaJugador(connection, vista.txfnombre2.getText()))
							&& (modelo.altaJugador(connection, vista.txfnombre3.getText())))
					{
						vista.vProvisional.setVisible(true);
						vista.vJ2.dispose();
					} 
					else 
					{
						vista.lblAviso.setText("El nombre introducido ya existe");
						vista.dlgNombreVacio.setVisible(true);
					}
					
					modelo.desconectar(connection);
					
				}
				else 
				{
					vista.lblAviso.setText("Escribe todos los nombres.");
					vista.dlgNombreVacio.setVisible(true);
				}
				break;
			case 4:
				if((!vista.txfnombre1.getText().isBlank()) && (!vista.txfnombre2.getText().isBlank()) 
						&& (!vista.txfnombre3.getText().isBlank()) && (!vista.txfnombre4.getText().isBlank())) 
				{
					connection = modelo.conectar();
					if ((modelo.altaJugador(connection, vista.txfnombre1.getText())) 
							&& (modelo.altaJugador(connection, vista.txfnombre2.getText()))
							&& (modelo.altaJugador(connection, vista.txfnombre3.getText()))
							&& (modelo.altaJugador(connection, vista.txfnombre4.getText())))
					{
						vista.vProvisional.setVisible(true);
						vista.vJ2.dispose();
					} 
					else 
					{
						vista.lblAviso.setText("El nombre introducido ya existe");
						vista.dlgNombreVacio.setVisible(true);
					}
					
					modelo.desconectar(connection);
					
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
		
	//VENTANA Ayuda (en progreso)
		//BOTÓN Volver2
		
	//VENTANA Partida (en progreso)
		
	//VENTANA Fin de ronda (en progreso)
		
	//VENTANA Fin de partida (en progreso)
		
	//DIÁlOGO Pasarse
		//BOTÓN OK
		else if (e.getSource().equals(vista.btnOk))
		{
			vista.dlgPasar.dispose();
		}
	//DIÁlOGO Plantarse
		//BOTÓN OK
		else if (e.getSource().equals(vista.btnOk2))
		{
			vista.dlgPlantar.dispose();
		}
	//DIÁlOGO Siete y Media (en progreso)
		
	//DIÁlOGO Siete y Media Real (en progreso)
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
		else if(e.getSource().equals(vista.vProvisional)) //Temporal
		{
			vista.vProvisional.dispose();
			vista.vPrincipal.setVisible(true);
		}
		else if(e.getSource().equals(vista.dlgPasar)) 
		{
			vista.dlgPasar.dispose();
		}
		else if(e.getSource().equals(vista.dlgPlantar)) 
		{
			vista.dlgPlantar.dispose();
		}
		
	}

	@Override public void windowActivated(WindowEvent e){}
	@Override public void windowClosed(WindowEvent e){}
	@Override public void windowDeactivated(WindowEvent e){}
	@Override public void windowDeiconified(WindowEvent e){}
	@Override public void windowIconified(WindowEvent e){}
	@Override public void windowOpened(WindowEvent e){}
}
