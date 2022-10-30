package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Rectangle;

//import java.awt.Color;

public class Puma {
	private int x, y;
	private Image img1;
	private Rectangle pumaRect;

	public Puma(int x) {
		// Imagen del puma.
		this.img1 = Herramientas.cargarImagen("tigre.png");

		// Coordenadas del puma.
		this.x = x;
		this.y = Juego.apoyarSobrePiso(img1.getHeight(null));

		// Coordenadas de la hitbox del puma. El ancho se divide a la mitad para que, si
		// el mono colisiona con la parte trasera, el puma no lo mate.
		this.pumaRect = new Rectangle();
		this.pumaRect.width = img1.getWidth(null) / 2;
		this.pumaRect.height = img1.getHeight(null);

		this.pumaRect.x = this.x - pumaRect.width;
		this.pumaRect.y = this.y - pumaRect.height / 2;
	}

	public void dibujarse(Entorno entorno) {
		// Hitbox
//		entorno.dibujarRectangulo(this.pumaRect.x + pumaRect.width / 2, this.pumaRect.y + pumaRect.height / 2,
//				this.pumaRect.width, this.pumaRect.height, 0.0, Color.gray);

		// Imagen
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
	}

	public void moverAdelante() {
		this.x -= Configuracion.VELOCIDAD_PUMAS;
		this.pumaRect.x -= Configuracion.VELOCIDAD_PUMAS;
	}

	public int getX() {
		return x;
	}

	public Rectangle getPumaRect() {
		return pumaRect;
	}

}
