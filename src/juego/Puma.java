package juego;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Puma {
	int x, y;
	Image img1;
	Rectangle pumaRect;

	public Puma(int x) {
		this.img1 = Herramientas.cargarImagen("tigre.png"); // Hacer la rama menos larga.
		this.x = x;
		this.y = Juego.apoyarSobrePiso(img1.getHeight(null));
		
		this.pumaRect = new Rectangle();
		this.pumaRect.width = img1.getWidth(null);
		this.pumaRect.height = img1.getHeight(null);
		
		this.pumaRect.x = this.x - pumaRect.width / 2;
		this.pumaRect.y = this.y - pumaRect.height / 2;
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarRectangulo(this.pumaRect.x + pumaRect.width / 2, this.pumaRect.y + pumaRect.height / 2, this.pumaRect.width, this.pumaRect.height, 0.0, Color.gray);
		
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
	}
	
	public void moverAdelante() {
		this.x -= Configuracion.VELOCIDAD;
		this.pumaRect.x -= Configuracion.VELOCIDAD;
	}

}
