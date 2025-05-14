package es.studium.SieteYMedia;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Modelo
{
	// Datos para el acceso
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/ranking";
	String login = "adminJuego";
	String password = "123456!";
	String sentencia = "";
	// Conexiones
	Connection connection = null;
	Statement statement = null;
	ResultSet resultset = null;
	//Sonido
	File sf;
	AudioFileFormat aff;
	AudioInputStream ais;
	
	Random random = new Random();


	// Conectar a la base de datos
	public Connection conectar()
	{
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
		} 
		catch (ClassNotFoundException cnfe)
		{
			return null;
		} 
		catch (SQLException sqle)
		{
			return null;
		}
		return connection;
	}

	// Desconectar de la base de datos
	public void desconectar(Connection conexion)
	{
		try
		{
			if (conexion != null)
			{
				conexion.close();
			}
		} 
		catch (SQLException sqle)
		{
		}
	}
	
	// Consultar Ranking
	public String obtenerRanking(Connection connection)
	{
		String contenidoTextarea = "";
		int posicion = 1;
		int top = 0;
		try
		{
			sentencia = "SELECT * FROM jugadores ORDER BY puntosJugador DESC , nombreJugador;";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			while ((resultset.next()) && (top<10))
			{
				contenidoTextarea = contenidoTextarea + posicion + " -- " + resultset.getString("nombreJugador") + " -- " + resultset.getFloat("puntosJugador") + "\n";
				top++;
				posicion++;
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return contenidoTextarea;
	}
	
	//Introducir nombres de ganadores
	public boolean altaJugador(Connection connection, String nombre, float puntos)
	{
		boolean resultado = false;
		try
		{
			statement = connection.createStatement();
			sentencia = "INSERT INTO jugadores VALUES (null,'" + nombre + "', "+ puntos +");";
			statement.executeUpdate(sentencia);
			resultado = true;
		} 
		catch (SQLException sqle)
		{
			resultado = false;
		}
		return resultado;
	}
	//Comprobar si algún nombre introducido existe ya en el ranking
	public boolean comprobarNombre(Connection connection, String nombreJugador)
	{
		boolean resultado = false;
		try
		{
			sentencia = "SELECT nombreJugador FROM jugadores WHERE nombreJugador = '" + nombreJugador +"';";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			
			if (resultset.next()) 
			{
				resultado = true;
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return resultado;
	}
	//Barajar cartas
	public String[] barajar(String[] cartas) 
	{
		for(int i = 0; i<cartas.length; i++) 
		{
			int mezcla = random.nextInt(cartas.length);
			String cambio = cartas[mezcla];
			cartas[mezcla] = cartas[i];
			cartas[i] = cambio;
		}
		return cartas;
	}
	//SONIDO al colocar carta en el tablero	
	public void SonidoCarta() 
	{
		File sf = new File("sonido\\Cartas.wav");
		try
		{
			aff = AudioSystem.getAudioFileFormat(sf);
			ais = AudioSystem.getAudioInputStream(sf);
			AudioFormat af = aff.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat(),
					((int) ais.getFrameLength() * af.getFrameSize()));
			Clip ol = (Clip) AudioSystem.getLine(info);
			ol.open(ais);
			ol.loop(Clip.LOOP_CONTINUOUSLY);
			Thread.sleep(150);
			ol.close();
		} catch (UnsupportedAudioFileException ee)
		{
			System.out.println(ee.getMessage());
		} catch (IOException ea)
		{
			System.out.println(ea.getMessage());
		} catch (LineUnavailableException LUE)
		{
			System.out.println(LUE.getMessage());
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	//SONIDO al ganar
	public void SonidoGanador() 
	{
		File sf = new File("sonido\\Ganar.wav");
		try
		{
			aff = AudioSystem.getAudioFileFormat(sf);
			ais = AudioSystem.getAudioInputStream(sf);
			AudioFormat af = aff.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat(),
					((int) ais.getFrameLength() * af.getFrameSize()));
			Clip ol = (Clip) AudioSystem.getLine(info);
			ol.open(ais);
			ol.loop(Clip.LOOP_CONTINUOUSLY);
			Thread.sleep(1245);
			ol.close();
		} catch (UnsupportedAudioFileException ee)
		{
			System.out.println(ee.getMessage());
		} catch (IOException ea)
		{
			System.out.println(ea.getMessage());
		} catch (LineUnavailableException LUE)
		{
			System.out.println(LUE.getMessage());
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	//SONIDO Ronda
	public void SonidoRonda() 
	{
		File sf = new File("sonido\\Ronda.wav");
		try
		{
			aff = AudioSystem.getAudioFileFormat(sf);
			ais = AudioSystem.getAudioInputStream(sf);
			AudioFormat af = aff.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat(),
					((int) ais.getFrameLength() * af.getFrameSize()));
			Clip ol = (Clip) AudioSystem.getLine(info);
			ol.open(ais);
			ol.loop(Clip.LOOP_CONTINUOUSLY);
			Thread.sleep(700);
			ol.close();
		} catch (UnsupportedAudioFileException ee)
		{
			System.out.println(ee.getMessage());
		} catch (IOException ea)
		{
			System.out.println(ea.getMessage());
		} catch (LineUnavailableException LUE)
		{
			System.out.println(LUE.getMessage());
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	//SONIDO al plantarse
	public void SonidoPlantarse() 
	{
		File sf = new File("sonido\\Plantarse.wav");
		try
		{
			aff = AudioSystem.getAudioFileFormat(sf);
			ais = AudioSystem.getAudioInputStream(sf);
			AudioFormat af = aff.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat(),
					((int) ais.getFrameLength() * af.getFrameSize()));
			Clip ol = (Clip) AudioSystem.getLine(info);
			ol.open(ais);
			ol.loop(Clip.LOOP_CONTINUOUSLY);
			Thread.sleep(660);
			ol.close();
		} catch (UnsupportedAudioFileException ee)
		{
			System.out.println(ee.getMessage());
		} catch (IOException ea)
		{
			System.out.println(ea.getMessage());
		} catch (LineUnavailableException LUE)
		{
			System.out.println(LUE.getMessage());
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	//SONIDO Pasarse
	public void SonidoPasarse() 
	{
		File sf = new File("sonido\\Pasarse.wav");
		try
		{
			aff = AudioSystem.getAudioFileFormat(sf);
			ais = AudioSystem.getAudioInputStream(sf);
			AudioFormat af = aff.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat(),
					((int) ais.getFrameLength() * af.getFrameSize()));
			Clip ol = (Clip) AudioSystem.getLine(info);
			ol.open(ais);
			ol.loop(Clip.LOOP_CONTINUOUSLY);
			Thread.sleep(900);
			ol.close();
		} catch (UnsupportedAudioFileException ee)
		{
			System.out.println(ee.getMessage());
		} catch (IOException ea)
		{
			System.out.println(ea.getMessage());
		} catch (LineUnavailableException LUE)
		{
			System.out.println(LUE.getMessage());
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	//Manual de usuario
	public void webAyuda() 
	{
		String URL = "index.html";
		
		if (java.awt.Desktop.isDesktopSupported())
		{
			java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
			if (desktop.isSupported(java.awt.Desktop.Action.BROWSE))
			{
				try
				{
					java.net.URI uri = new java.net.URI(URL);
					desktop.browse(uri);
				} catch (URISyntaxException | IOException ex)
				{
					System.out.println(ex.getMessage());
				}
			}
		}
	}
}
