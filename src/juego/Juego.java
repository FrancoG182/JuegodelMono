package juego;

import java.awt.Color;
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
	Rama rama;

	static int piso = Configuracion.POSICION_Y_PISO;
//	Arbol[] arboles;

	// Variables y métodos propios de cada grupo
	// ...

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Selva Mono Capuchino - Grupo 10 - v1", 800, 600);

		// Inicializar lo que haga falta para el juego

		background = Herramientas.cargarImagen("background.jpg");

		mono = new Mono(100, 300); // 420

		arbol = new Arbol(100, 360); // Para el rectangulo

		rama = new Rama(100, 400); // Para el rectangulo

//		arbol = new Arbol(300, 295); // Para la imagen del arbol

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

//		int posYPiso = Configuracion.POSICION_Y_PISO;

		// Linea que delimita el piso. Ni el mono ni los enemigos deben traspasarla al
		// hacer efecto la gravedad.
		entorno.dibujarRectangulo(400, piso, 800, 1, 0.0, Color.red);
		
//		entorno.dibujarRectangulo(400, 394, 800, 1, 0.0, Color.red);
///
//		arbol.dibujarse(entorno);
		rama.dibujarse(entorno);
		mono.dibujarse(entorno);
		
		if (!mono.sobreRama(rama)) {
			mono.gravedad();
		}
		
//		rama.moverAdelante();
		rama.subir();
		
		arbol.moverAdelante();
		if (arbol.x < -100.0) {
			arbol.x = 200;
			arbol.arbolRect.x = arbol.x - arbol.arbolRect.width / 2;
		}

		colisionEntre(mono.monoRect, arbol.arbolRect);
	}

	/**
	 * Los elementos del juego son imagenes (rectangulos). Este metodo ayudaria a
	 * posicionarlos correctamente sobre el piso. Calcula la coordenada Y que se
	 * debe pasar como parametro al constructor del objeto en cuestion.
	 */
	public static int apoyarSobrePiso(Image img) {
		return piso - img.getHeight(null) / 2;
	}
	
//	public static int apoyarSobrePiso(Mono mono) {
//		return piso - mono.monoRect.height / 2;
//	}

	public static boolean colisionEntre(Rectangle rect1, Rectangle rect2) {
		if (rect1.intersects(rect2)) {
//			System.out.println("Collision detected!");
			return true;
		}
		return false;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
