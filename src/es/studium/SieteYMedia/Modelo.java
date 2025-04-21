package es.studium.SieteYMedia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			sentencia = "SELECT * FROM jugadores ORDER BY puntosJugador DESC , nombreJugador";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			while (resultset.next())
			{
				contenidoTextarea = contenidoTextarea + resultset.getInt("idJugador") + " -- "
						+ resultset.getString("nombreJugador") + " -- " + resultset.getInt("puntosJugador") + "\n";
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return contenidoTextarea;
	}
	
	//Introducir nombres de jugadores
	public boolean altaJugador(Connection connection, String nombre)
	{
		boolean resultado = false;
		try
		{
			statement = connection.createStatement();
			sentencia = "INSERT INTO jugadores VALUES (null,'" + nombre + "', "+ 0 +");";
			statement.executeUpdate(sentencia);
			resultado = true;
		} 
		catch (SQLException sqle)
		{
			resultado = false;
		}
		return resultado;
	}
}
