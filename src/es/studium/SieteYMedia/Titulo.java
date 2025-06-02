package es.studium.SieteYMedia;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Titulo extends Frame
{
	private static final long serialVersionUID = 1L;
	//VENTANA Título
	Button btnJugar = new Button("Jugar");
	Image titulo;
	Toolkit herramienta;
	
	public Titulo() 
	{
		herramienta = getToolkit();
		titulo = herramienta.getImage("img\\Titulo.png");
		setLayout(null);
		setSize(626, 626);
		setLocationRelativeTo(null);
		setTitle("Siete y Media");
		btnJugar.setBounds(400, 500, 100, 40);
		btnJugar.setFont(new Font("Arial", Font.BOLD, 16));
		btnJugar.setBackground(new Color(240, 240, 209));
		add(btnJugar);
		setVisible(true);
	}
	public void paint(Graphics g) 
	{
		g.drawImage(titulo, 0, 0, this);
	}
}
