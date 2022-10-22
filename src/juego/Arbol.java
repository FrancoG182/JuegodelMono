package juego;

import java.awt.Image;
//import java.awt.Rectangle;
import entorno.Entorno;
import entorno.Herramientas;
//import java.awt.Color;

public class Arbol {
	int x, y;
	Image img1;
	Rama rama;
	
//	Rectangle arbolRect;

	public Arbol(int x) {
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
		
	}
	
	public void moverAdelante() {
		this.x -= Configuracion.VELOCIDAD;
		this.rama.moverAdelante();
//		if (this.x < 0) {
//			this.x = 300;
//			this.rama.x = this.x;
//			this.rama.ramaRect.x = this.rama.x - this.rama.ramaRect.width / 2;
//		}
	}
}
