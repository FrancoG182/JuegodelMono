package juego;

//import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import entorno.Entorno;
import entorno.Herramientas;

public class Piedra {
	int x, y;
	Image img1;
	Rectangle piedraRect;

	boolean lanzada;

	public Piedra(int x, int y, boolean lanzada) {
		// Imagen de la piedra.
		img1 = Herramientas.cargarImagen("Piedra.png");
		
		// Ancho y alto de la hitbox de la piedra.
		this.piedraRect = new Rectangle();
		this.piedraRect.width = img1.getWidth(null);
		this.piedraRect.height = img1.getHeight(null);
		
		// Coordenadas X de la piedra y su hitbox.
		this.x = x;
		this.piedraRect.x = x - piedraRect.width / 2;
		
		// Si el booleano pasado como parametro es false, la piedra se situara sobre el suelo. Si es true, se
		// situara donde le indique la Y pasada como parametro.
		this.lanzada = lanzada;
		if (this.lanzada == false) {
			this.y = Juego.apoyarSobrePiso(this.piedraRect.height);			
		} else {
			this.y = y;			
		}
		
		// Coordenada Y de la hitbox de la piedra.
		this.piedraRect.y = this.y - piedraRect.height / 2;
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
		this.piedraRect.x -= Configuracion.VELOCIDAD_OBJETOS;
	}
	
	public void lanzarPiedra() {
		// Avanza la piedra hacia la derecha de la pantalla.
		this.x += Configuracion.VELOCIDAD_PIEDRA_LANZADA;
		this.piedraRect.x += Configuracion.VELOCIDAD_PIEDRA_LANZADA;
	}
}