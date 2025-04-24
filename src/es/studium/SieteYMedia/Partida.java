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

public class Partida extends Frame implements WindowListener, ActionListener, MouseListener
{
	//Serializado
	private static final long serialVersionUID = 1L;
	//Cargar imágenes y herramienta
	Image tapete, cartaReverso, mazo;
	Image P1, P2, P3, P4, P5, P6, P7, PJ, PQ, PR; //Picas
	Image C1, C2, C3, C4, C5, C6, C7, CJ, CQ, CR; //Corazones
	Image T1, T2, T3, T4, T5, T6, T7, TJ, TQ, TR; //Tréboles
	Image D1, D2, D3, D4, D5, D6, D7, DJ, DQ, DR; //Diamantes
	Toolkit herramienta;
	//Clases
	Vista vista;
	//Diseño
	Font fntTurno = new Font("Arial", Font.BOLD, 14);
	Color clrFondo = new Color(147, 142, 240);
	Button btnPlantarse = new Button("Plantarse");
	//Variables
	int clickX, clickY;
	int turno = 1;
	int ronda = 1;
	int puntosTotales = 0;
	float puntosRonda = 0f;
	boolean ratonMazo = false;
	boolean finRonda = false;
	
	public Partida(Vista v) 
	{
		vista = v;
	//VENTANA Partida en curso
		//Diseño
		setLayout(null);
		setLocation(238, 110);
		setSize(1062, 678);
		setTitle("Siete y Media: Partida en curso");
		setBackground(clrFondo);
		setResizable(true);
		addWindowListener(this);
		addMouseListener(this);
		vista.btnAceptar.addActionListener(this);
		btnPlantarse.addActionListener(this);
		btnPlantarse.setBounds(480, 502, 100, 40);
		btnPlantarse.setFont(new Font("Arial", Font.BOLD, 16));
		add(btnPlantarse);
		setVisible(true);
		//Imágenes
		herramienta = getToolkit();
		tapete = herramienta.getImage("img\\tapete.png");
		cartaReverso = herramienta.getImage("img\\cartaReverso.png");
		mazo = herramienta.getImage("img\\mazo.png");
			//Picas
		P1 = herramienta.getImage("img\\P1.png");
		P2 = herramienta.getImage("img\\P2.png");
		P3 = herramienta.getImage("img\\P3.png");
		P4 = herramienta.getImage("img\\P4.png");
		P5 = herramienta.getImage("img\\P5.png");
		P6 = herramienta.getImage("img\\P6.png");
		P7 = herramienta.getImage("img\\P7.png");
		PJ = herramienta.getImage("img\\PJ.png");
		PQ = herramienta.getImage("img\\PQ.png");
		PR = herramienta.getImage("img\\PR.png");
			//Corazones
		C1 = herramienta.getImage("img\\C1.png");
		C2 = herramienta.getImage("img\\C2.png");
		C3 = herramienta.getImage("img\\C3.png");
		C4 = herramienta.getImage("img\\C4.png");
		C5 = herramienta.getImage("img\\C5.png");
		C6 = herramienta.getImage("img\\C6.png");
		C7 = herramienta.getImage("img\\C7.png");
		CJ = herramienta.getImage("img\\CJ.png");
		CQ = herramienta.getImage("img\\CQ.png");
		CR = herramienta.getImage("img\\CR.png");
			//Tréboles
		T1 = herramienta.getImage("img\\T1.png");
		T2 = herramienta.getImage("img\\T2.png");
		T3 = herramienta.getImage("img\\T3.png");
		T4 = herramienta.getImage("img\\T4.png");
		T5 = herramienta.getImage("img\\T5.png");
		T6 = herramienta.getImage("img\\T6.png");
		T7 = herramienta.getImage("img\\T7.png");
		TJ = herramienta.getImage("img\\TJ.png");
		TQ = herramienta.getImage("img\\TQ.png");
		TR = herramienta.getImage("img\\TR.png");
			//Diamantes
		D1 = herramienta.getImage("img\\D1.png");
		D2 = herramienta.getImage("img\\D2.png");
		D3 = herramienta.getImage("img\\D3.png");
		D4 = herramienta.getImage("img\\D4.png");
		D5 = herramienta.getImage("img\\D5.png");
		D6 = herramienta.getImage("img\\D6.png");
		D7 = herramienta.getImage("img\\D7.png");
		DJ = herramienta.getImage("img\\DJ.png");
		DQ = herramienta.getImage("img\\DQ.png");
		DR = herramienta.getImage("img\\DR.png");
		repaint();
	}
	
	public void paint(Graphics g) 
	{
		//Diseño gráficos
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, 1062, 70);
		g.fillRect(0, 491, 1062, 60);
		g.setColor(Color.black);
		g.drawRect(462, 584, 142, 33);
		g.setFont(new Font("Arial", Font.PLAIN, 13));
		g.drawString("Puntos Totales: " + puntosTotales, 38, 631);
		g.setFont(fntTurno);
		g.drawImage(tapete, 0, 70, this);
		g.drawImage(mazo, 470, 320, this);
		g.drawString("Puntos Ronda: " + puntosRonda, 470, 606);
		g.setColor(Color.white);
		g.drawString("Roba una carta:", 468, 310);
		//Cambiar diseño según turno del jugador
		if(turno==1) 
		{
			g.setColor(Color.red);
			g.drawString("Turno de: " + vista.txfnombre1.getText(), 460, 57);
			btnPlantarse.setBackground(Color.red);
		}
		if(turno==2) 
		{
			g.setColor(Color.green);
			g.drawString("Turno de: " + vista.txfnombre2.getText(), 460, 57);
			btnPlantarse.setBackground(Color.green);
		}
		if(turno==3) 
		{
			g.drawString("Turno de: " + vista.txfnombre1.getText(), 20, 57);
		}
		if(turno==4) 
		{
			g.drawString("Turno de: " + vista.txfnombre1.getText(), 20, 57);
		}
		//Efecto al pulsar el mazo, centrando imagen a la posición aproximada del ratón(-40, -60)
		if(ratonMazo) 
		{
			g.drawImage(cartaReverso, clickX - 40, clickY - 60, this);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//Cerrar Ventana
		if (e.getSource().equals(vista.btnAceptar)) 
		{
			dispose();
		}
	}
	
	public void mouseClicked(MouseEvent me)
	{
		//Coordenadas del ratón
		clickX = me.getX();
		clickY = me.getY();
		//Ayuda para el diseño
		System.out.println("Clic en X: " + clickX + ", Y: " + clickY);
	}

	public void mousePressed(MouseEvent me)
	{
		//Establecer que APAREZCA la imagen al pulsar el mazo
		clickX = me.getX();
		clickY = me.getY();
		if(clickX >= 470 && clickX <= 582 && clickY >= 320 && clickY <= 470) 
		{
			ratonMazo = true;
			repaint();
		}
	}

	public void mouseReleased(MouseEvent me)
	{
		//Establecer que DESAPAREZCA la imagen al pulsar el mazo
		if(ratonMazo) 
		{
			ratonMazo = false;
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
	
	public void mouseEntered(MouseEvent me){}public void mouseExited(MouseEvent me){}
	@Override public void windowActivated(WindowEvent e){}@Override public void windowClosed(WindowEvent e){}
	@Override public void windowDeactivated(WindowEvent e){}@Override public void windowDeiconified(WindowEvent e){}
	@Override public void windowIconified(WindowEvent e){}@Override public void windowOpened(WindowEvent e){}
}
