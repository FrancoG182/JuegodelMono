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
	Rama[] ramas;
	Puma[] pumas;

	static Arbol ultimoArbolGenerado;
	static int arbolesSeguidosSinSerpiente;

	static Puma ultimoPumaGenerado;

	Piedra[] piedras;
	static Piedra ultimaPiedraGenerada;
	Piedra[] piedrasArrojadas;

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

		arbolesSeguidosSinSerpiente = 1;

		pumas = new Puma[Configuracion.CANT_PUMAS];

		piedras = new Piedra[Configuracion.CANT_PIEDRAS];

		piedrasArrojadas = new Piedra[Configuracion.CANT_PROYECTILES];

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

		generarPumas(pumas);
		generarArboles(arboles);
		asignarRamasEnUnArreglo(arboles, ramas); // Se asignan las ramas ya creadas en un arreglo para
													// pasar este arreglo al metodo gravedad().
		generarPiedras(piedras);

		for (Arbol arbol : arboles) {
			if (arbol != null) {
				arbol.dibujarse(entorno);
				Serpiente s = arbol.rama.serpiente;

				if (s != null) {
					colisionEntre(mono.monoRect, s.serpRect);
//					System.out.println("Collision detected!");
				}
			}
		}

		for (Puma puma : pumas) {
			if (puma != null) {
				puma.dibujarse(entorno);
				colisionEntre(mono.monoRect, puma.pumaRect);
//				System.out.println("Collision detected!");
			}
		}

		for (Piedra piedra : piedras) {
			if (piedra != null) {
				piedra.dibujarse(entorno);
			}
		}

		if (entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
			arrojarPiedras(mono, piedrasArrojadas);
		}

		for (Piedra proyectil : piedrasArrojadas) {
//			System.out.println(proyectil == null);
			if (proyectil != null) {
				proyectil.dibujarse(entorno);
				proyectil.lanzarPiedra();
			}
		}

		mono.dibujarse(entorno);

		if (Configuracion.AVANZAR_ARBOL)
			avanzarArboles(arboles);
		if (Configuracion.AVANZAR_DEPREDADOR)
			avanzarPumas(pumas);
		if (Configuracion.AVANZAR_PIEDRA)
			avanzarPiedras(piedras, mono);

		if (!mono.monoCayendo && entorno.estaPresionada(entorno.TECLA_ARRIBA)
				&& limiteSalto < Configuracion.LIMITE_SALTO) {
			mono.saltar();
			limiteSalto++;
		} else {
			limiteSalto = 0;
			mono.gravedad(ramas);
		}

		desplazar(mono); // Para testeo
	}

	public void desplazar(Mono mono) {
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
	}

	public static void generarArboles(Arbol[] arboles) {
		int x = Configuracion.COORD_X_DE_PRIMER_ARBOL;
		int chance = Configuracion.CHANCE_DE_SERPIENTE_EN_ARBOL;

		int distMin = Configuracion.MIN_DIST_DIBUJADO_ENTRE_ARBOLES;
		int distMax = Configuracion.MAX_DIST_DIBUJADO_ENTRE_ARBOLES;

		if (ultimoArbolGenerado != null) { // Si el ultimo arbol que se creo no es null,
			x = ultimoArbolGenerado.x; // x toma el valor de la coordenada actual del ultimo arbol generado (aliasing
										// util).
		}

		for (int i = 0; i < arboles.length; i++) { // Para cada arbol
			if (arboles[i] == null) { // Si el arbol es null, se lo va a generar. Un arbol puede ser null porque
										// recien empieza el juego o porque se salio de la pantalla.

				x = enteroAleatorio(x + distMin, x + distMax); // A partir de la x del ultimo arbol creado, se obtiene
																// un numero random que va a ser la distancia entre el
																// ultimo arbol y el siguiente.
				arboles[i] = new Arbol(x); // Se crea el nuevo arbol.

				if (x % chance != 0 && arbolesSeguidosSinSerpiente != 5) {
					// Si x es divisible por chance o si ya se eliminaron las serpientes de
					// 5 arboles, no se le elimina la serpiente al arbol actual.
					arboles[i].rama.serpiente = null;
					arbolesSeguidosSinSerpiente++;
				} else {
					arbolesSeguidosSinSerpiente = 0;
				}

				ultimoArbolGenerado = arboles[i]; // Se registra el ultimo arbol creado. Aca se utiliza aliasing.
			}
		}
	}

	public static void generarPumas(Puma[] pumas) {
		int x = Configuracion.COORD_X_DE_PRIMER_PUMA;

		int distMin = Configuracion.MIN_DIST_DIBUJADO_ENTRE_PUMAS;
		int distMax = Configuracion.MAX_DIST_DIBUJADO_ENTRE_PUMAS;

		if (ultimoPumaGenerado != null) { // Si el ultimo puma que se creo no es null,
			x = ultimoPumaGenerado.x; // x toma el valor de la coordenada actual del ultimo puma generado.
		}

		for (int i = 0; i < pumas.length; i++) { // Para cada puma
			if (pumas[i] == null) {
				x = enteroAleatorio(x + distMin, x + distMax); // X random generado a partir de la x del ultimo puma
				// creado.

				pumas[i] = new Puma(x); // Se crea el nuevo puma.
				ultimoPumaGenerado = pumas[i];
			}
		}
	}

	public static void generarPiedras(Piedra[] piedras) {
		int x = Configuracion.COORD_X_DE_PRIMER_PIEDRA;

		int distMin = Configuracion.MIN_DIST_DIBUJADO_ENTRE_PIEDRAS;
		int distMax = Configuracion.MAX_DIST_DIBUJADO_ENTRE_PIEDRAS;

		if (ultimaPiedraGenerada != null) { // Si la ultima piedra que se creo no es null,
			x = ultimaPiedraGenerada.x; // x toma el valor de la coordenada actual de la ultima piedra generada.
		}

		for (int i = 0; i < piedras.length; i++) { // Para cada piedra
			if (piedras[i] == null) {
				x = enteroAleatorio(x + distMin, x + distMax); // X aleatorio generado a partir de la x de la
																// ultima piedra creada.
				piedras[i] = new Piedra(x, 0, false); // Se crea la nueva piedra. La y no importa porque el constructor
														// la apoya sobre el piso.
				ultimaPiedraGenerada = piedras[i];
			}
		}
	}

	public static void asignarRamasEnUnArreglo(Arbol[] arboles, Rama[] ramas) {
		for (int i = 0; i < arboles.length; i++) {
			if (arboles[i] != null)
				ramas[i] = arboles[i].rama;
		}
	}

	public static void avanzarArboles(Arbol[] arboles) {
		for (int i = 0; i < arboles.length; i++) {
			arboles[i].moverAdelante();

			// Apenas el arbol desaparezca de la pantalla, el arbol, su rama y la serpiente
			// de su
			// rama van a volverse null.
			if (arboles[i].x < -arboles[i].ancho / 2)
				arboles[i] = null;
		}
	}

	public static void avanzarPumas(Puma[] pumas) {
		for (int i = 0; i < pumas.length; i++) {
			pumas[i].moverAdelante();
			if (pumas[i].pumaRect.x < -pumas[i].pumaRect.width)
				pumas[i] = null;
		}
	}

	public static void avanzarPiedras(Piedra[] piedras, Mono mono) {
		for (int i = 0; i < piedras.length; i++) {
			piedras[i].moverAdelante();

			if (piedraAgarrada(mono, piedras[i])) {
				mono.agarrarPiedra();
				piedras[i] = null;
			} else if (piedras[i].piedraRect.x < -piedras[i].piedraRect.width) {
				piedras[i] = null;
//				piedras[i].x = 300;
//				piedras[i].piedraRect.x = piedras[i].x - piedras[i].piedraRect.width / 2;
			}
		}
	}

	public static boolean piedraAgarrada(Mono mono, Piedra piedra) {
//		return colisionEntre(mono.monoRect, piedra.piedraRect); 
		if (colisionEntre(mono.monoRect, piedra.piedraRect)) {
			return true;
		}
		return false;
	}

	public static void arrojarPiedras(Mono mono, Piedra[] piedrasArrojadas) {
		if (mono.cantPiedras > 0) {
			Piedra proyectil = mono.arrojarPiedra();
			
			for (int i = 0; i < piedrasArrojadas.length; i++) {
				if (piedrasArrojadas[i] == null) {
					piedrasArrojadas[i] = proyectil;
					break;
				}
			}
//			proyectil.lanzarPiedra();
		}
	}

	public static boolean colisionEntre(Rectangle rect1, Rectangle rect2) {
//		return rect1.intersects(rect2);

		if (rect1.intersects(rect2)) {
//			System.out.println("Collision detected!");
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
		int piso = Configuracion.POSICION_Y_PISO;
		return piso - img.getHeight(null) / 2;
	}

	public static int apoyarSobrePiso(int altura) {
		int piso = Configuracion.POSICION_Y_PISO;
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
