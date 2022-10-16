package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Color;

public class Arbol {
	int x, y, ancho, alto;
	Image img1;

	public Arbol(int x, int y) {
		this.x = x;
		this.y = y;
		img1 = Herramientas.cargarImagen("arbol.png");
	}

//	public Arbol(int x, int y, int ancho, int alto) {
//		this.x = x - ancho / 2;
//		this.y = y - alto / 2;
//		this.ancho = ancho;
//		this.alto = alto;
//		Rectangle arbolR = new Rectangle(this.x, this.y, this.ancho, this.alto);
//	}

	// Tal vez un metodo que construya un array de arboles con tamaños, coordenadas
	// y otras propiedades distintas.

	// Para escalar un arbol, se podria crear un rectangulo que no se dibuje,
	// pero que este en la misma posicion que el tronco. Cuando el mono colisione,
	// se puede escalar y otras cosas.

	
	public void dibujarse(Entorno entorno) {
//		entorno.dibujarImagen(img1, this.x, this.y, 0, 0.4);
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0.0, Color.black);
//		entorno.dibujarRectangulo(this.x, this.y, 30.0, 200.0, 0.0, Color.black);
//		else
//			entorno.dibujarImagen(img2, this.x, this.y, 0);
	}

	public Rectangle rectangulo(int x, int y, int ancho, int alto) {
		this.x = x - ancho / 2;
		this.y = y - alto / 2;
		this.ancho = ancho;
		this.alto = alto;
		Rectangle arbolR = new Rectangle(this.x, this.y, this.ancho, this.alto);
		return arbolR;
	}
	
	public void moverAdelante() {
		this.x -= 1;
	}
}
