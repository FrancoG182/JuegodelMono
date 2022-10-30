package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Rectangle;

//import java.awt.Color;

public class Piedra {
	private int x, y;
	private Image img1;
	private Rectangle piedraRect;

	private boolean lanzada;

	public Piedra(int x, int y, boolean lanzada) {
		// Imagen de la piedra.
		img1 = Herramientas.cargarImagen("Piedra.png");
		
		// Ancho y alto de la hitbox de la piedra.
		this.piedraRect = new Rectangle();
		this.getPiedraRect().width = img1.getWidth(null);
		this.getPiedraRect().height = img1.getHeight(null);
		
		// Coordenadas X de la piedra y su hitbox.
		this.x = x;
		this.getPiedraRect().x = x - getPiedraRect().width / 2;
		
		// Si el booleano pasado como parametro es false, la piedra se situara sobre el suelo. Si es true, se
		// situara donde le indique la Y pasada como parametro.
		this.lanzada = lanzada;
		if (this.lanzada == false) {
			this.y = Juego.apoyarSobrePiso(this.getPiedraRect().height);			
		} else {
			this.y = y;			
		}
		
		// Coordenada Y de la hitbox de la piedra.
		this.getPiedraRect().y = this.y - getPiedraRect().height / 2;
	}

	public void dibujarse(Entorno entorno) {
		// Hitbox
//		entorno.dibujarRectangulo(this.piedraRect.x + piedraRect.width / 2, this.piedraRect.y + piedraRect.height / 2, this.piedraRect.width, this.piedraRect.height, 0.0, Color.black);
		
		// Imagen
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
	}

	public void moverAdelante() {
		// Avanza la piedra hacia la izquierda de la pantalla.
		this.x -= Configuracion.VELOCIDAD_OBJETOS;
		this.getPiedraRect().x -= Configuracion.VELOCIDAD_OBJETOS;
	}
	
	public void lanzarPiedra() {
		// Avanza la piedra hacia la derecha de la pantalla.
		this.x += Configuracion.VELOCIDAD_PIEDRA_LANZADA;
		this.getPiedraRect().x += Configuracion.VELOCIDAD_PIEDRA_LANZADA;
	}

	public int getX() {
		return x;
	}

	public Rectangle getPiedraRect() {
		return piedraRect;
	}
}