package es.studium.SieteYMedia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

//Clase en Progreso. (Borrar este comentario al acabar)
public class Modelo
{
	// DATOS necesarios para el acceso
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/ranking";
	String login = "adminJuego";
	String password = "123456!";
	String sentencia = "";
	// CLASES de objeto para las conexiones
	Connection connection = null;
	Statement statement = null;
	ResultSet resultset = null;
	
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

		try
		{
			sentencia = "SELECT * FROM jugadores ORDER BY puntosJugador DESC , nombreJugador;";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			while (resultset.next())
			{
				contenidoTextarea = contenidoTextarea + resultset.getInt("idJugador") + " -- "
						+ resultset.getString("nombreJugador") + " -- " + resultset.getFloat("puntosJugador") + "\n";
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
	
//	public void actualizarTop10(Connection connection)
//	{
//		En progreso
//	}
}
