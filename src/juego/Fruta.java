package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Rectangle;

//import java.awt.Color;

public class Fruta {
	 private int x, y;
	 private Image img1;
	 private Rectangle frutaRect;

	public Fruta(int x, int y) {
		// Imagen de la fruta.
		this.img1 = Herramientas.cargarImagen("Manzanita.png");
		
		// Ancho y alto de la hitbox de la fruta.
		this.frutaRect = new Rectangle();
		this.frutaRect.width = img1.getWidth(null);
		this.frutaRect.height = img1.getHeight(null);
		
		// Coordenadas de la fruta en si.
		this.x = x;
		this.y = y - this.frutaRect.height / 2;

		// Coordenadas de la hitbox de la fruta.
		this.frutaRect.x = this.x - frutaRect.width / 2;
		this.frutaRect.y = this.y - frutaRect.height / 2;
	}

	public void dibujarse(Entorno entorno) {
		// Hitbox
//		entorno.dibujarRectangulo(this.frutaRect.x + frutaRect.width / 2, this.frutaRect.y + frutaRect.height / 2,
//				this.frutaRect.width, this.frutaRect.height, 0.0, Color.gray);

		// Imagen
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
	}

	public void moverAdelante() {
		// Avanza la fruta.
		this.x -= Configuracion.VELOCIDAD_OBJETOS;
		this.frutaRect.x -= Configuracion.VELOCIDAD_OBJETOS;
	}

	public Rectangle getFrutaRect() {
		return frutaRect;
	}

}