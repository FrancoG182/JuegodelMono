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
//	Arbol arbol;
	Rama rama;
	int limiteSalto;
	Arbol[] arboles;
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

//		arbol = new Arbol(300); // Para el rectangulo

		arboles = new Arbol[Configuracion.CANT_ARBOLES];
		ramas = new Rama[arboles.length];

//		arboles[0] = new Arbol(100);

		generarArboles(arboles);
		asignarRamasEnArreglo(arboles, ramas);

//		rama = new Rama(arbol.x, arbol.y); // Para el rectangulo

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

		for (Arbol arbol : arboles) {
			System.out.println(arbol.x);
			arbol.dibujarse(entorno);
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

//		rama.moverAdelante();
//		rama.subir();
//		System.out.println(Configuracion.VELOCIDAD);

		if (Configuracion.AVANZAR_ARBOL) {
			avanzarArboles(arboles);
		}
//		if (arbol.x < 20.0) {
//			arbol.x = 300;
//			arbol.rama.x = arbol.x;
//			arbol.rama.ramaRect.x = arbol.rama.x - arbol.rama.ramaRect.width / 2;
//		}

//		colisionEntre(mono.monoRect, arbol.arbolRect);
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

	public static void generarArboles(Arbol[] arboles) {
		int x = 600;
		int disMin = Configuracion.MIN_DIST_DIBUJADO_ENTRE_ARBOLES;
		int disMax = Configuracion.MAX_DIST_DIBUJADO_ENTRE_ARBOLES;

		for (int i = 0; i < arboles.length; i++) {
			arboles[i] = new Arbol(x);
//			System.out.println(arbol.x);
//			x += 180;
			x = enteroAleatorio(x + disMin, x + disMax); // Velocidad 1
//			x = enteroAleatorio(x + 260, x + 360); // Velocidad 2
//			x = enteroAleatorio(x + 330, x + 460); // Velocidad 3
//			x = enteroAleatorio(x + 210, x + 560); // Velocidad 4
		}
	}

	public static void asignarRamasEnArreglo(Arbol[] arboles, Rama[] ramas) {
		for (int i = 0; i < arboles.length; i++) {
			ramas[i] = arboles[i].rama;
		}
	}

	public static boolean colisionEntre(Rectangle rect1, Rectangle rect2) {
		if (rect1.intersects(rect2)) {
			System.out.println("Collision detected!");
			return true;
		}
		return false;
	}

	public static void avanzarArboles(Arbol[] arboles) {
		for (Arbol arbol : arboles) {
			arbol.moverAdelante();			
		}
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
