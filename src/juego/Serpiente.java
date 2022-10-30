package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Rectangle;

//import java.awt.Color;

public class Serpiente {
	 private int x, y;
	 private Image img1;
	 private Rectangle serpRect;

	public Serpiente(int x, int y) {
		// Imagen de la serpiente.
		this.img1 = Herramientas.cargarImagen("serpiente.png");
		
		// Ancho y alto de la hitbox de la serpiente.
		this.serpRect = new Rectangle();
		this.serpRect.width = img1.getWidth(null);
		this.serpRect.height = img1.getHeight(null);
		
		// Coordenadas de la serpiente en si.
		this.x = x;
		this.y = y - this.serpRect.height / 2;

		// Coordenadas de la hitbox de la serpiente.
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
		// Avanza la serpiente.
		this.x -= Configuracion.VELOCIDAD_OBJETOS;
		this.serpRect.x -= Configuracion.VELOCIDAD_OBJETOS;
	}

	public Rectangle getSerpRect() {
		return serpRect;
	}
}
