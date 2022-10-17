package juego;

import java.awt.Color;
//import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Image background;
	Mono mono;
	Arbol arbol;

//	double coordPiso;
//	Arbol[] arboles;

	// Variables y métodos propios de cada grupo
	// ...

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Selva Mono Capuchino - Grupo 10 - v1", 800, 600);
		
		background = Herramientas.cargarImagen("background.jpg");
		mono = new Mono(100, 420); // 420
		
		arbol = new Arbol(300, 360); // Para el rectangulo
//		arbol = new Arbol(300, 295); // Para la imagen del arbol

		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {
		entorno.dibujarImagen(background, 400, 300, 0, 1);

		arbol.dibujarse(entorno);
		mono.dibujarse(entorno);
		
//		arbol.moverAdelante();
		if (arbol.x < -100.0) {
			arbol.x = 200;
			arbol.arbolRect.x = 200;
		}
		
		// Linea que delimita el piso. El mono nunca debe traspasarla
		int asd = Configuracion.POSICION_Y_PISO;
		entorno.dibujarRectangulo(400, asd, 800, 1, 0.0, Color.red);

		colisionEntre(mono.monoRect, arbol.arbolRect);	
	}
	
	public static boolean colisionEntre(Rectangle rect1, Rectangle rect2) {
		if (rect1.intersects(rect2)) {
			System.out.println("Collision detected!");
			return true;
		}
		return false;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
