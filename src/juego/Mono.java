package juego;

//import java.awt.Color;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Mono {
	int x, y;
	Image img1, img2;
	Rectangle monoRect;

	public Mono(int x, int y) {
//		img1 = Herramientas.cargarImagen("Mono1.png");
		img1 = Herramientas.cargarImagen("Rect.png");
		img2 = Herramientas.cargarImagen("Mono2.png");
		
		this.x = x;
		this.y = y;
//		this.y = Juego.apoyarSobrePiso(img1);
		
		// Coordenadas y tamaño del rectangulo que va a hacer de hitbox del mono.
		this.monoRect = new Rectangle();
		
//		this.monoRect.width = 80;	// Esta asignacion es para cuando se esta dibujando un rectangulo.
		this.monoRect.width = img1.getWidth(null);	// Esta asignacion toma el ancho de la imagen que representa 
													//	al mono para armar el rectangulo.
		
//		this.monoRect.height = 80;	// Esta asignacion es para cuando se esta dibujando un rectangulo.
		this.monoRect.height = img1.getHeight(null);	// Esta asignacion toma el alto de la imagen que representa 
														//	al mono para armar el rectangulo.
		
		/* Teniendo en cuenta que los metodos preintegrados del TP dibujan las imgs con su centro en las
			coordenadas pasadas como parametro, estas asignaciones calculan la posicion del rectangulo con
			respecto a la posicion de la img. */
		this.monoRect.x = x - monoRect.width / 2;
		this.monoRect.y = y - monoRect.height / 2;
		
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
		
//		entorno.dibujarRectangulo(this.x, this.y, this.monoRect.width, this.monoRect.height, 0.0, Color.gray);
	}

	// ESTA MAL ESTE METODO.
	// PREGUNTA POR UNA COORDENADA DE MANERA INEFICIENTE. ARREGLAR CUANTO ANTES.
	public void animar() {
		if (this.y == 410) {
			this.y = 415;
		} else {
			this.y = 410;
		}
	}
	
	public void gravedad() {
		if (this.y < Juego.apoyarSobrePiso(img1)) {
			this.y += 1;
			this.monoRect.y += 1;
		}
	}
}
