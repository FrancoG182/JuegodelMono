package juego;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import entorno.Entorno;
import entorno.Herramientas;

public class Piedra {
	int x, y;
	Image img1;
	Rectangle piedraRect;

	boolean agarrada;
	boolean lanzada;

	public Piedra(int x, int y, boolean lanzada) {
		img1 = Herramientas.cargarImagen("Piedra1.png");

		this.lanzada = lanzada;
		
		// Hitbox de la piedra que agarra el mono
		this.piedraRect = new Rectangle();
		this.piedraRect.width = img1.getWidth(null);
		this.piedraRect.height = img1.getHeight(null);
		
		// Coordenadas de la piedra
		this.x = x;
		this.piedraRect.x = x - piedraRect.width / 2;
		
		if (this.lanzada == false) {
			this.y = Juego.apoyarSobrePiso(this.piedraRect.height);			
		} else {
			this.y = y;			
		}
		this.piedraRect.y = this.y - piedraRect.height / 2;



	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarRectangulo(this.piedraRect.x + piedraRect.width / 2, this.piedraRect.y + piedraRect.height / 2, this.piedraRect.width, this.piedraRect.height, 0.0, Color.black);
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
	}

	public void moverAdelante() {
		this.x -= Configuracion.VELOCIDAD_OBJETOS;
		this.piedraRect.x -= Configuracion.VELOCIDAD_OBJETOS;
	}
	
	public void lanzarPiedra() {
		this.x += Configuracion.VELOCIDAD_PIEDRA_LANZADA;
		this.piedraRect.x += Configuracion.VELOCIDAD_PIEDRA_LANZADA;
	}
}