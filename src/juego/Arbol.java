package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

//import java.awt.Rectangle;
//import java.awt.Color;

public class Arbol {
	private int x, y;
	private int ancho;
	private Image img1;
	private Rama rama;

	public Arbol(int x, boolean alto) {
		// Si el booleano pasado como parametro es true, la imagen sera la de un arbol
		// alto, al que el mono no podra subir saltando desde el suelo. Si es false, se
		// crea una bajo al que el mono podra subir saltando.
		if (alto) {
			this.img1 = Herramientas.cargarImagen("Arbol_alto.png");
		} else {
			this.img1 = Herramientas.cargarImagen("Arbol.png");
		}

		// Coordenadas del arbol en si.
		this.x = x;
		this.y = Juego.apoyarSobrePiso(img1.getHeight(null));

		this.ancho = img1.getWidth(null);
		
		// Se inicializa su rama
		this.rama = new Rama(this.x, this.y - 10);
	}

	public void dibujarse(Entorno entorno) {
		// Se dibuja el arbol.
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);

		// Se dibuja la rama.
		if (this.rama != null) {
			this.rama.dibujarse(entorno);
		}
		// Hitbox del arbol
//		entorno.dibujarRectangulo(this.x + ancho / 2 + 1, 300, 1, 600, 0.0, Color.yellow);
	}

	public int getX() {
		return x;
	}
	
	public void moverAdelante() {
		// Avanza el arbol junto con su rama.
		this.x -= Configuracion.VELOCIDAD_OBJETOS;
		this.rama.moverAdelante();
	}

	public int getAncho() {
		return ancho;
	}

	public Rama getRama() {
		return rama;
	}
}
