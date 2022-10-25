package juego;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Fruta {
	int x, y;
	Image img1;
	Rectangle frutaRect;

	public Fruta(int x, int y) {
		this.img1 = Herramientas.cargarImagen("Manzanita.png");
		
		this.frutaRect = new Rectangle();
		this.frutaRect.width = img1.getWidth(null);
		this.frutaRect.height = img1.getHeight(null);
		
		this.x = x;
		this.y = y - this.frutaRect.height / 2;

		this.frutaRect.x = this.x - frutaRect.width / 2;
		this.frutaRect.y = this.y - frutaRect.height / 2;
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarRectangulo(this.frutaRect.x + frutaRect.width / 2, this.frutaRect.y + frutaRect.height / 2,
				this.frutaRect.width, this.frutaRect.height, 0.0, Color.gray);

		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
	}

	public void moverAdelante() {
		this.x -= Configuracion.VELOCIDAD_OBJETOS;
		this.frutaRect.x -= Configuracion.VELOCIDAD_OBJETOS;
	}

}