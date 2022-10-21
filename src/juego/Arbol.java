package juego;

import java.awt.Image;
import java.awt.Rectangle;

import javax.imageio.stream.FileCacheImageInputStream;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Color;

public class Arbol {
	int x, y;
	Image img1;
	Rectangle arbolRect;

	public Arbol(int x, int y) {
		// Coordenadas del arbol en si.
		this.arbolRect = new Rectangle();
		this.arbolRect.width = 30;
		this.arbolRect.height = 300;
		
		this.x = x;
		this.y = Juego.apoyarSobrePiso(this.arbolRect.height);
//		this.y = y;
		
		// Coordenadas y tamaño del rectangulo que va a hacer de hitbox del arbol.
		this.arbolRect.x = x - arbolRect.width / 2;
		this.arbolRect.y = y - arbolRect.height / 2;

		img1 = Herramientas.cargarImagen("arbol.png");
	}

	// Tal vez un metodo que construya un array de arboles con tamaÃ±os, coordenadas
	// y otras propiedades distintas.

	// Para escalar un arbol, se podria crear un rectangulo que no se dibuje,
	// pero que este en la misma posicion que el tronco. Cuando el mono colisione,
	// se puede escalar y otras cosas.

	
	public void dibujarse(Entorno entorno) {
//		entorno.dibujarImagen(img1, this.x, this.y, 0, 0.4);
		
		entorno.dibujarRectangulo(this.x, this.arbolRect.y + arbolRect.height / 2, this.arbolRect.width, this.arbolRect.height, 0.0, Color.black);
		
//		entorno.dibujarRectangulo(this.x, this.y, 30.0, 200.0, 0.0, Color.black);
//		else
//			entorno.dibujarImagen(img2, this.x, this.y, 0);
	}
	
	public void moverAdelante() {
		this.x -= Configuracion.VELOCIDAD;
		this.arbolRect.x -= Configuracion.VELOCIDAD; 
	}
}
