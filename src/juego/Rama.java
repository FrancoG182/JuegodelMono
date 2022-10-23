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

	// PODRIA TOMAR COMO PARAMETRO UN ARBOL EN EL QUE DIBUJARSE.
	public Rama(int x, int y) {
		this.img1 = Herramientas.cargarImagen("rama.png"); // Hacer la rama menos larga.
		this.x = x;
		this.y = y;
		
		this.ramaRect = new Rectangle();
		this.ramaRect.width = img1.getWidth(null);
		this.ramaRect.height = img1.getHeight(null);
		
		this.ramaRect.x = this.x - ramaRect.width / 2;
		this.ramaRect.y = this.y - ramaRect.height / 2;
		
		this.serpiente = new Serpiente(this.ramaRect.x, this.ramaRect.y);
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
		
		if (this.serpiente != null) {
			this.serpiente.dibujarse(entorno);
		}
//		entorno.dibujarRectangulo(this.ramaRect.x + ramaRect.width / 2, this.ramaRect.y + ramaRect.height / 2, this.ramaRect.width, this.ramaRect.height, 0.0, Color.gray);
	}
	
	public void moverAdelante() {
		this.x -= Configuracion.VELOCIDAD;
		this.ramaRect.x -= Configuracion.VELOCIDAD;
	}
	
	public void subir() { // METODO PARA TESTEAR
		this.y -= Configuracion.VELOCIDAD;
		this.ramaRect.y -= Configuracion.VELOCIDAD; 
	}
}
