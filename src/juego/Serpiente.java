package juego;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Serpiente {
	int x, y;
	Image img1;
	Rectangle serpRect;

	public Serpiente(int x, int y) {
		this.img1 = Herramientas.cargarImagen("serpiente.png");
		
		this.serpRect = new Rectangle();
		this.serpRect.width = img1.getWidth(null);
		this.serpRect.height = img1.getHeight(null);
		
		this.x = x;
		this.y = y - this.serpRect.height / 2;

		this.serpRect.x = this.x - serpRect.width / 2;
		this.serpRect.y = this.y - serpRect.height / 2;
	}

	public void dibujarse(Entorno entorno) {
		// Hitbox
//		entorno.dibujarRectangulo(this.serpRect.x + serpRect.width / 2, this.serpRect.y + serpRect.height / 2,
//				this.serpRect.width, this.serpRect.height, 0.0, Color.gray);

		// Imagen
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
	}

	public void moverAdelante() {
		this.x -= Configuracion.VELOCIDAD_OBJETOS;
		this.serpRect.x -= Configuracion.VELOCIDAD_OBJETOS;
	}

}
