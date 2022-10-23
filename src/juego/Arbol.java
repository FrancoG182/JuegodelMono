package juego;

import java.awt.Image;
//import java.awt.Rectangle;
import entorno.Entorno;
import entorno.Herramientas;
//import java.awt.Color;

public class Arbol {
	int x, y;
	int ancho;
	Image img1;
	Rama rama;

	public Arbol(int x) {
		this.img1 = Herramientas.cargarImagen("Arbol.png");

		// Coordenadas del arbol en si.
		this.x = x;
		this.y = Juego.apoyarSobrePiso(img1.getHeight(null));

		this.ancho = img1.getWidth(null);

		this.rama = new Rama(this.x, this.y - 10);
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
		
		if (this.rama != null) {
			this.rama.dibujarse(entorno);
		}
//		entorno.dibujarRectangulo(this.x + ancho / 2 + 1, 300, 1, 600, 0.0, Color.yellow);
	}

	public void moverAdelante() {
		this.x -= Configuracion.VELOCIDAD_OBJETOS;
		this.rama.moverAdelante();
	}
}
