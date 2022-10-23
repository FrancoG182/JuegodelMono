package juego;

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
	int limiteSalto;

	Arbol[] arboles;
	static Arbol ultimoArbol;
//	static int xDeUltimoArbol;
	Rama[] ramas;

	static int piso = Configuracion.POSICION_Y_PISO;

	// Variables y métodos propios de cada grupo
	// ...

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Selva Mono Capuchino - Grupo 10 - v1", 800, 600);

		// Inicializar lo que haga falta para el juego

		background = Herramientas.cargarImagen("background.jpg");

		mono = new Mono(100, 0);

		limiteSalto = 0;

		arboles = new Arbol[Configuracion.CANT_ARBOLES];
		ramas = new Rama[arboles.length];

//		xDeUltimoArbol = Configuracion.COORD_X_DE_PRIMER_ARBOL;

//		ultimoArbol = arboles[arboles.length - 1];

//		generarArboles(arboles);
//		asignarRamasEnArreglo(arboles, ramas);

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

		generarArboles(arboles);
		asignarRamasEnUnArreglo(arboles, ramas); // Se asignan las ramas ya creadas en un arreglo para pasar este
													// arreglo al metodo gravedad().
//		if (ultimoArbol != null) {
//			xDeUltimoArbol = ultimoArbol.x;
//		}

		for (Arbol arbol : arboles) {
//			System.out.println(arbol.x);
			if (arbol != null) {
				arbol.dibujarse(entorno);
			}
		}

		if (Configuracion.AVANZAR_ARBOL) {
			avanzarArboles(arboles);
		}

		mono.dibujarse(entorno);

		if (!mono.monoCayendo && entorno.estaPresionada(entorno.TECLA_ARRIBA)
				&& limiteSalto < Configuracion.LIMITE_SALTO) {
			mono.saltar();
			limiteSalto++;
		} else {
			limiteSalto = 0;
			mono.gravedad(ramas);
		}

//		System.out.println(mono.monoCayendo);

		if (Configuracion.MONO_DESPLAZAR) {
			if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
				mono.avanzar();
			}
			if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
				mono.x -= Configuracion.FUERZA_SALTO;
				mono.monoRect.x -= Configuracion.FUERZA_SALTO;
			}
			if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
				mono.y += Configuracion.FUERZA_SALTO;
				mono.monoRect.y += Configuracion.FUERZA_SALTO;
			}
		}

//		colisionEntre(mono.monoRect, arbol.arbolRect);
	}

	public static void generarArboles(Arbol[] arboles) {
		int x = Configuracion.COORD_X_DE_PRIMER_ARBOL;

		int distMin = Configuracion.MIN_DIST_DIBUJADO_ENTRE_ARBOLES;
		int distMax = Configuracion.MAX_DIST_DIBUJADO_ENTRE_ARBOLES;

		if (ultimoArbol != null) {
			x = ultimoArbol.x;
		}

		for (int i = 0; i < arboles.length; i++) {
			if (arboles[i] == null) {
				x = enteroAleatorio(x + distMin, x + distMax);
				arboles[i] = new Arbol(x);
				ultimoArbol = arboles[i];
			}
		}
	}

	public static void avanzarArboles(Arbol[] arboles) {
		for (int i = 0; i < arboles.length; i++) {
			arboles[i].moverAdelante();

			if (arboles[i].x < -arboles[i].ancho / 2) { // Apenas desaparezca de la pantalla, el arbol se va a volver
				// null.
				arboles[i].rama = null; // Seteamos a null tanto al arbol como a su rama. La rama no es necesaria, pero
				// creo que es buena practica.
				arboles[i] = null;
			}
		}
	}

	public static void asignarRamasEnUnArreglo(Arbol[] arboles, Rama[] ramas) {
		for (int i = 0; i < arboles.length; i++) {
			if (arboles[i] != null) {
				ramas[i] = arboles[i].rama;
			}
		}
	}

	public static boolean colisionEntre(Rectangle rect1, Rectangle rect2) {
		if (rect1.intersects(rect2)) {
			System.out.println("Collision detected!");
			return true;
		}
		return false;
	}

	/**
	 * Los elementos del juego son imagenes (rectangulos). Este metodo ayudaria a
	 * posicionarlos correctamente sobre el piso. Calcula la coordenada Y que se
	 * debe pasar como parametro al constructor del objeto en cuestion.
	 */
	public static int apoyarSobrePiso(Image img) {
		return piso - img.getHeight(null) / 2;
	}

	public static int apoyarSobrePiso(int altura) {
		return piso - altura / 2;
	}

	public static int enteroAleatorio(int minimo, int maximo) {
		double r = Math.random();
		double res = minimo + (maximo - minimo) * r;
		return (int) Math.round(res);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
