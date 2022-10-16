package juego;

import java.awt.Color;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Mono {
	int x, y;
	Image img1, img2;
	Rectangle monoRect;

	public Mono(int x, int y) {
		this.x = x;
		this.y = y;
		
		// Coordenadas y tamaño del rectangulo que va a hacer de hitbox del mono.
		this.monoRect = new Rectangle();
		this.monoRect.width = 80;
		this.monoRect.height = 80;
		this.monoRect.x = x - monoRect.width / 2;
		this.monoRect.y = y - monoRect.height / 2;
		
		img1 = Herramientas.cargarImagen("Mono1.png");
		img2 = Herramientas.cargarImagen("Mono2.png");
	}

	public void dibujarse(Entorno entorno) {
//		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
		entorno.dibujarRectangulo(this.x, this.y, this.monoRect.width, this.monoRect.height, 0.0, Color.gray);
//		else
//			entorno.dibujarImagen(img2, this.x, this.y, 0);
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

	public void moverAdelante() {
		this.x += Math.cos(this.x) * 20;
		this.y += Math.sin(this.x) * 20;
		if (this.x > 900) {
			this.x = -100;
		}
		if (this.x < -100) {
			this.x = 900;
		}
		if (this.y > 650) {
			this.y = -50;
		}
		if (this.y < -50) {
			this.y = 650;
		}

	}
}
