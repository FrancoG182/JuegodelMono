package juego;

//import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import entorno.Entorno;
import entorno.Herramientas;

public class Rama {
	int x, y;
	Image img1;
	Rectangle ramaRect;
	Serpiente serpiente;
	Fruta fruta;
	boolean yaDioPuntos;

	public Rama(int x, int y) {
		// Imagen de la rama.
		this.img1 = Herramientas.cargarImagen("Rama.png");
		
		// Booleano que verifica si el mono ya obtuvo puntos por pararse en esta rama.
		yaDioPuntos = false;
		
		// Coordenadas de la rama.
		this.x = x;
		this.y = y;

		// Coordenadas de la hitbox de la rama.
		this.ramaRect = new Rectangle();
		this.ramaRect.width = img1.getWidth(null);
		this.ramaRect.height = img1.getHeight(null);

		this.ramaRect.x = this.x - ramaRect.width / 2;
		this.ramaRect.y = this.y - ramaRect.height / 2;

		// Se inicializa su serpiente
		this.serpiente = new Serpiente(this.x, this.ramaRect.y);
		// Se inicializa su fruta
		this.fruta = new Fruta(this.x, this.ramaRect.y);
	}

	public void dibujarse(Entorno entorno) {
		// Se dibuja la rama.
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);

		// Se dibuja la serpiente.
		if (this.serpiente != null) {
			this.serpiente.dibujarse(entorno);
		}
		// Se dibuja la manzana.
		if (this.fruta != null) {
			this.fruta.dibujarse(entorno);
		}
		
		// Hitbox de la rama
//		entorno.dibujarRectangulo(this.ramaRect.x + ramaRect.width / 2, this.ramaRect.y + ramaRect.height / 2, this.ramaRect.width, this.ramaRect.height, 0.0, Color.gray);
	}

	public void moverAdelante() {
		// Avanza la rama.
		this.x -= Configuracion.VELOCIDAD_OBJETOS;
		this.ramaRect.x -= Configuracion.VELOCIDAD_OBJETOS;

		// Si no es null, tambien avanza su serpiente. 
		if (this.serpiente != null) {
			this.serpiente.moverAdelante();
		}
		// Si no es null, tambien avanza su fruta. 
		if (this.fruta != null) {
			this.fruta.moverAdelante();
		}
	}
}
