package juego;

import java.awt.Image;
import java.awt.Rectangle;
import entorno.Entorno;
import entorno.Herramientas;
//import java.awt.Color;

public class Arbol {
	int x, y;
	Image img1;
	Rama rama;
	
//	Rectangle arbolRect;

	public Arbol(int x, int y) {
		this.img1 = Herramientas.cargarImagen("arbol.png");
		
		// Coordenadas del arbol en si.
		this.x = x;
		this.y = Juego.apoyarSobrePiso(img1.getHeight(null));
		
		this.rama = new Rama(this.x, this.y);
	}

	// Tal vez un metodo que construya un array de arboles con tamanios, coordenadas
	// y otras propiedades distintas.
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
		this.rama.dibujarse(entorno);
		
//		entorno.dibujarRectangulo(this.x, this.y, this.img1.getWidth(null), this.img1.getHeight(null), 0.0, Color.black);
//		entorno.dibujarRectangulo(this.arbolRect.x + this.arbolRect.width / 2, this.arbolRect.y + this.arbolRect.height / 2, this.arbolRect.width, this.arbolRect.height, 0.0, Color.black);

//		entorno.dibujarRectangulo(this.x, this.y, 30.0, 200.0, 0.0, Color.black);
//		else
//			entorno.dibujarImagen(img2, this.x, this.y, 0);
	}
	
	public void moverAdelante() {
		this.x -= Configuracion.VELOCIDAD;
		this.rama.moverAdelante();
//		this.rama.x -= Configuracion.VELOCIDAD; 
//		this.rama.ramaRect.x -= Configuracion.VELOCIDAD; 
		
//		this.arbolRect.x -= Configuracion.VELOCIDAD; 
	}
}
