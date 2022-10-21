package juego;

import java.awt.Image;
import java.awt.Color;
import java.awt.Rectangle;
import entorno.Entorno;
import entorno.Herramientas;

public class Rama {
	int x, y;
	Image img1;
	Rectangle ramaRect;

	// PODRIA TOMAR COMO PARAMETRO UN ARBOL EN EL QUE DIBUJARSE.
	public Rama(int x, int y) {
		this.img1 = Herramientas.cargarImagen("rama"); // Poner img cuando este lista.
		this.x = x;
		this.y = y;
		
		this.ramaRect = new Rectangle();
		this.ramaRect.width = img1.getWidth(null);		// Para cuando sea una imagen
		this.ramaRect.height = img1.getHeight(null);	// Para cuando sea una imagen

//		this.ramaRect.width = 100;
//		this.ramaRect.height = 12;
		
		this.ramaRect.x = this.x - ramaRect.width / 2;
		this.ramaRect.y = this.y - ramaRect.height / 2;
	}
	
	public void dibujarse(Entorno entorno) {
//		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
		
		entorno.dibujarRectangulo(this.x, this.y, this.ramaRect.width, this.ramaRect.height, 0.0, Color.gray);
	}
	
	public void moverAdelante() {
		this.x -= Configuracion.VELOCIDAD;
		this.ramaRect.x -= Configuracion.VELOCIDAD;
	}
	
	public void subir() { // METODO PARA TESTEAR
		this.y -= Configuracion.VELOCIDAD;
		this.ramaRect.y -= Configuracion.VELOCIDAD; 
	}
}
