package es.studium.SieteYMedia;

public class Principal
{
	public static void main(String[] args)
	{
		Vista vista = new Vista();
		Modelo modelo = new Modelo();
		Titulo titulo = new Titulo();
		new Controlador(modelo, vista, titulo);
	}
}
