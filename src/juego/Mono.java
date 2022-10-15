package juego;

import java.awt.Color;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Mono {
	double x, y;
	Image img1, img2;

	public Mono(double x, double y) {
		this.x = x;
		this.y = y;
		img1 = Herramientas.cargarImagen("Mono1.png");
		img2 = Herramientas.cargarImagen("Mono2.png");
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
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
