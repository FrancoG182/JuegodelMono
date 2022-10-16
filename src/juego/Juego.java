package juego;

//import java.awt.Color;
import java.awt.Image;

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
		mono = new Mono(100, 300); // 420
		
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
		
		arbol.moverAdelante();
		if (arbol.x < -100) {
			arbol.x = 200;
			arbol.arbolRect.x = 200;
		}
		
		colisionEntre(mono, arbol);
		
	}
	public static boolean colisionEntre(Mono mono, Arbol arbol) {
//		if (
//			    mono.x < arbol.x + 30 &&
//			    mono.x + 80 > arbol.x &&
//			    mono.y < arbol.y + 200 &&
//			    80 + mono.y > arbol.y
//			  ) {
//			    // Collision detected!
//			    System.out.println("Collision detected!");
//			    return true;
//			  }
//		return false;
		if (mono.monoRect.intersects(arbol.arbolRect)) {
			System.out.println("Collision detected!");
			return true;
		}
		return false;
	}
	
	public boolean colision(double x1, double y1, double x2, double y2, double dist) {
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) < dist * dist;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
