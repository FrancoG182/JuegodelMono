package juego;

import java.awt.Image;
//import java.awt.Paint;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Color;
//import javafx.scene.paint.Color;

public class Arbol {
	double x, y;
	Image img1;

	public Arbol(double x, double y) {
		this.x = x;
		this.y = y;
		img1 = Herramientas.cargarImagen("arbol.png");
	}

	// Tal vez un metodo que construya un array de arboles con tamaños, coordenadas
	// y otras propiedades distintas.

	// Para escalar un arbol, se podria crear un rectangulo que no se dibuje,
	// pero que este en la misma posicion que el tronco. Cuando el mono colisione,
	// se puede escalar y otras cosas.

	public void dibujarse(Entorno entorno) {
//		entorno.dibujarImagen(img1, this.x, this.y, 0, 0.4);
		entorno.dibujarRectangulo(this.x, this.y, 30.0, 200.0, 0.0, Color.black);
//		else
//			entorno.dibujarImagen(img2, this.x, this.y, 0);
	}

	public void moverAdelante() {
		this.x -= 2;
	}
}
