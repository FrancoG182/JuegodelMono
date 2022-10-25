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

	Image background; // El fondo de pantalla.

	Mono mono;

	static int puntuacionFinal;

	int limiteSalto; // Altura maxima a la que puede llegar el mono.

	Arbol[] arboles; // Arreglo que va a guardar a los arboles generados con sus ramas y las
						// serpientes de sus ramas.

	Rama[] ramas; // *
	Serpiente[] serpientes; // *
	static Fruta[] frutas; // *

	// (*) Tanto las ramas como las serpientes y las frutas se generan con los
	// mismos arboles. Estos arrays sirven para llevar registro de la posicion
	// actual de las frutas, las serpientes y las ramas para que el mono interactue
	// con ellas.

	Puma[] pumas;

	static Arbol ultimoArbolGenerado; // A partir de la coordenada x de este arbol se calculara la x del siguiente
										// arbol a generarse.

	static int arbolesSeguidosSinSerpiente; // Cantidad de arboles seguidos con sus serpientes == null.

	static Puma ultimoPumaGenerado;// A partir de la coordenada x de este puma se calculara la x del siguiente
									// puma a generarse.

	Piedra[] piedras; // Piedras del suelo.

	static Piedra ultimaPiedraGenerada;// A partir de la coordenada x de esta piedra se calculara la x de la siguiente
										// piedra a generarse.

	static Piedra[] piedrasArrojadas; // Piedras que estan siendo lanzadas. Tendra elementos cuando el mono lance una
										// piedra y este elemento se eliminara cuando la piedra toque un depredador o se
										// salga de la pantalla.

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Selva Mono Capuchino - Grupo 10 - v1", Configuracion.ANCHO_PANTALLA,
				Configuracion.ALTO_PANTALLA);

		// Inicializar lo que haga falta para el juego

		background = Herramientas.cargarImagen("background.jpg");

		mono = new Mono(100, 0);
		puntuacionFinal = 0;

		limiteSalto = 0;

		arboles = new Arbol[Configuracion.CANT_ARBOLES];
		ramas = new Rama[arboles.length];
		frutas = new Fruta[arboles.length];
		serpientes = new Serpiente[arboles.length];

		arbolesSeguidosSinSerpiente = 1;

		pumas = new Puma[Configuracion.CANT_PUMAS];

		piedras = new Piedra[Configuracion.CANT_PIEDRAS];

		piedrasArrojadas = new Piedra[Configuracion.CANT_PROYECTILES];

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el metodo tick() sera ejecutado en cada instante y por lo
	 * tanto es el metodo más importante de esta clase. Aqui se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {
		if (!mono.muerto) {
			entorno.dibujarImagen(background, 400, 300, 0, 1); // Se dibuja el fondo de pantalla.

			mostrarPuntos(mono, entorno);
			mostrarPiedras(mono, entorno);

			generarPumas(pumas);
			generarArboles(arboles);

			asignarRamasEnArreglos(arboles, ramas); // Se asignan las ramas ya creadas en
			// un arreglo para pasar este arreglo al metodo gravedad().

			generarPiedras(piedras);

			for (Arbol arbol : arboles) { // Se dibuja cada arbol que no sea null con su rama y su serpiente, si es que
											// tiene una.
				if (arbol != null) {
					arbol.dibujarse(entorno);
					Serpiente serpiente = arbol.rama.serpiente;
					Fruta fruta = arbol.rama.fruta;

					if (serpiente != null) { // Ya que estamos recorriendo los arboles, vemos si la serpiente del arbol
												// no es null.
						comerAlMono(mono, serpiente); // Si no es null, vemos si esta colisionando con el mono.
					}
					if (fruta != null) {// Ya que estamos recorriendo los arboles, vemos si la serpiente del arbol
										// no es null.
						agarrarFruta(mono, fruta, arboles);// Si no es null, vemos si esta colisionando con el mono.
					}
				}
			}

			for (Puma puma : pumas) { // Se dibuja cada puma que no sea null y vemos si esta colisionando con el mono.
				if (puma != null) {
					puma.dibujarse(entorno);
					comerAlMono(mono, puma);
				}
			}

			for (Piedra piedra : piedras) { // Se dibuja cada piedra DEL SUELO que no sea null.
				if (piedra != null) {
					piedra.dibujarse(entorno);
				}
			}

			if (entorno.sePresiono(entorno.TECLA_ESPACIO)) { //
				arrojarPiedras(mono, piedrasArrojadas);
			}

			for (Piedra proyectil : piedrasArrojadas)
				if (proyectil != null) {
					proyectil.dibujarse(entorno);
					proyectil.lanzarPiedra();
					espantarDepredador(proyectil, pumas, arboles, mono);
				}

			mono.dibujarse(entorno);

			if (Configuracion.AVANZAR_ARBOL)
				avanzarArboles(arboles);
			if (Configuracion.AVANZAR_DEPREDADOR)
				avanzarPumas(pumas);
			if (Configuracion.AVANZAR_PIEDRA)
				avanzarPiedras(piedras, mono);

			avanzarProyectiles(piedrasArrojadas);

			if (!mono.monoCayendo && entorno.estaPresionada(entorno.TECLA_ARRIBA)
					&& limiteSalto < Configuracion.LIMITE_SALTO) {
				mono.saltar();
				limiteSalto++;
			} else {
				limiteSalto = 0;
				mono.gravedad(ramas);
			}

			desplazarMono(mono); // PARA TESTEO

		} else {
			entorno.cambiarFont("Consolas", 30, Color.white);
			int largoPuntaje = ("Puntos: " + puntuacionFinal).length();
			int xDePuntaje = Configuracion.ANCHO_PANTALLA / 2 - (18 * largoPuntaje) / 2;

			String mensaje = "Presione la tecla ESPACIO para salir";
			int xDeMensaje = Configuracion.ANCHO_PANTALLA / 2 - (18 * mensaje.length()) / 2;

			entorno.escribirTexto("Puntos: " + puntuacionFinal, xDePuntaje, 300);
			entorno.escribirTexto(mensaje, xDeMensaje, 500);

			if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
				quitar();
			}
		}
	}

	public static void generarArboles(Arbol[] arboles) {
		int x = Configuracion.COORD_X_DE_PRIMER_ARBOL;
		int chanceArbolAlto = Configuracion.CHANCE_DE_ARBOL_ALTO;
		int chanceSerpiente = Configuracion.CHANCE_DE_SERPIENTE_EN_ARBOL;
		int chanceFruta = Configuracion.CHANCE_DE_FRUTA_EN_ARBOL;

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

				if (x % chanceArbolAlto == 0) { // Si x es divisible por chanceArbolAlto:
					arboles[i] = new Arbol(x, true); // Se crea un nuevo arbol alto,
				} else {
					arboles[i] = new Arbol(x, false); // si no, se crea un nuevo arbol bajo.
				}

				if (x % chanceSerpiente == 0 || arbolesSeguidosSinSerpiente == 5) {
					// Si x es divisible por chanceSerpiente o si ya se eliminaron las serpientes de
					// 5 arboles, no se le elimina la serpiente al arbol actual y se reinicia la
					// cuenta de arboles sin serpiente.
					arbolesSeguidosSinSerpiente = 0;
				} else {
					arboles[i].rama.serpiente = null;
					arbolesSeguidosSinSerpiente++;
				}
				if (x % chanceFruta != 0) {
					// Si x no es divisible por chanceFruta, se le elimina la fruta al arbol actual.
					arboles[i].rama.fruta = null;
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

	public static void asignarRamasEnArreglos(Arbol[] arboles, Rama[] ramas) {
		for (int i = 0; i < arboles.length; i++) {
			if (arboles[i] != null) {
				ramas[i] = arboles[i].rama;
			}
		}
	}

	public static void avanzarArboles(Arbol[] arboles) {
		for (int i = 0; i < arboles.length; i++) {
			if (arboles[i] == null) {
				continue;
			}
			arboles[i].moverAdelante();

			// Apenas el arbol desaparezca de la pantalla, el arbol, su rama y la serpiente
			// de su rama van a volverse null.
			if (arboles[i].x < -arboles[i].ancho / 2)
				arboles[i] = null;
		}
	}

	public static void avanzarPumas(Puma[] pumas) {
		for (int i = 0; i < pumas.length; i++) {
			if (pumas[i] == null) {
				continue;
			}
			pumas[i].moverAdelante();
			if (pumas[i].pumaRect.x < -pumas[i].pumaRect.width * 2)
				pumas[i] = null;
		}
	}

	public static void avanzarPiedras(Piedra[] piedras, Mono mono) {
		for (int i = 0; i < piedras.length; i++) {
			if (piedras[i] == null) {
				continue;
			}

			piedras[i].moverAdelante();

			if (piedraAgarrada(mono, piedras[i])) {
				mono.agarrarPiedra();
				piedras[i] = null;

			} else if (piedras[i].piedraRect.x < -piedras[i].piedraRect.width) {
				piedras[i] = null;
			}
		}
	}

	public static void avanzarProyectiles(Piedra[] proyectiles) {
		for (int i = 0; i < proyectiles.length; i++) {
			if (proyectiles[i] == null) {
				continue;
			}
			proyectiles[i].lanzarPiedra();

			if (proyectiles[i].piedraRect.x > Configuracion.ANCHO_PANTALLA) {
				proyectiles[i] = null;
			}
		}
	}

	public static boolean piedraAgarrada(Mono mono, Piedra piedra) {
		return (colisionEntre(mono.monoRect, piedra.piedraRect)
				&& mono.cantPiedras < Configuracion.CANT_PIEDRAS_QUE_PUEDE_TENER_EL_MONO);
	}

	public static void arrojarPiedras(Mono mono, Piedra[] piedrasArrojadas) {
		if (mono.cantPiedras > 0) { // Si el mono tiene piedras,
			Piedra proyectil = mono.arrojarPiedra(); // Se crea una nueva piedra.

			for (int i = 0; i < piedrasArrojadas.length; i++) { // Se carga la piedra en un arreglo de piedras que estan
																// siendo arrojadas.
				if (piedrasArrojadas[i] == null) {
					piedrasArrojadas[i] = proyectil;
					break; // Si encuentra espacio, carga la piedra y sale del ciclo y termina el metodo.
				}
			}
		}
	}

	public static void espantarDepredador(Piedra proyectil, Puma[] pumas, Arbol[] arboles, Mono mono) {
		if (proyectil != null) {
			for (int i = 0; i < pumas.length; i++) {
				if (pumas[i] != null) {
					if (colisionEntre(proyectil.piedraRect, pumas[i].pumaRect)) {
						pumas[i] = null;
						eliminarProyectil(proyectil);
						mono.ganarPuntos(Configuracion.PUNTOS_GANADOS_POR_ESPANTAR_DEPREDADOR);

						return;
					}
				}
			}

			for (int i = 0; i < arboles.length; i++) {
				if (arboles[i] != null && arboles[i].rama.serpiente != null) {
					if (colisionEntre(proyectil.piedraRect, arboles[i].rama.serpiente.serpRect)) {
						arboles[i].rama.serpiente = null;
						eliminarProyectil(proyectil);
						mono.ganarPuntos(Configuracion.PUNTOS_GANADOS_POR_ESPANTAR_DEPREDADOR);

						return;
					}
				}
			}
		}
	}

	public static void eliminarProyectil(Piedra proyectil) {
		for (int i = 0; i < piedrasArrojadas.length; i++) {
			if (proyectil == piedrasArrojadas[i]) {
				piedrasArrojadas[i] = null;
				return;
			}
		}
	}

	public static void comerAlMono(Mono mono, Puma puma) {
		if (colisionEntre(mono.monoRect, puma.pumaRect)) {
			mono.morirse();
			System.out.println("colision");
			puntuacionFinal = mono.puntos;
		}
	}

	public static void comerAlMono(Mono mono, Serpiente serpiente) {
		if (colisionEntre(mono.monoRect, serpiente.serpRect)) {
			mono.morirse();
			puntuacionFinal = mono.puntos;
		}
	}

	public static void agarrarFruta(Mono mono, Fruta fruta, Arbol[] arboles) {
		if (colisionEntre(mono.monoRect, fruta.frutaRect)) {
			mono.ganarPuntos(Configuracion.PUNTOS_GANADOS_POR_COMER_FRUTA);
			eliminarFruta(fruta, arboles);
		}
	}

	public static void eliminarFruta(Fruta fruta, Arbol[] arboles) {
		for (int i = 0; i < arboles.length; i++) {
			if (fruta == arboles[i].rama.fruta) {
				arboles[i].rama.fruta = null;
				return;
			}
		}
	}

	public void mostrarPuntos(Mono mono, Entorno entorno) {
		int largoPuntaje = ("PUNTOS: " + mono.puntos).length(); // Calcula el tamanio del String de la puntuacion.

		int xDePuntaje = Configuracion.ANCHO_PANTALLA - 18 * largoPuntaje; // Segun el largo del puntaje, se calcula el
		// x. La puntuacion se va a mostrar del lado
		// derecho de la pantalla.
		// Se muestra el puntaje en pantalla:
		entorno.cambiarFont("Consolas", 30, Color.black);
		entorno.escribirTexto("PUNTOS: " + mono.puntos, xDePuntaje, 30);
	}

	public void mostrarPiedras(Mono mono, Entorno entorno) {
		Image img1 = Herramientas.cargarImagen("Piedra.png");

		// Se calcula la posicion de la piedra en la esquina superior izquierda de la
		// pantalla.
		int anchoPiedra = img1.getWidth(null);
		int altoPiedra = img1.getHeight(null);

		int xDePiedra = 10 + anchoPiedra / 2;
		int yDePiedra = 12 + altoPiedra / 2;

		// Se dibuja una piedra para hacer de icono de la cantidad de piedras que tiene
		// el mono.
		entorno.dibujarImagen(img1, xDePiedra, yDePiedra, 0, 1);

		// Se muestra la cantidad de piedras en pantalla:
		entorno.cambiarFont("Consolas", 25, Color.black);
		entorno.escribirTexto("PIEDRAS: " + mono.cantPiedras, xDePiedra + anchoPiedra, yDePiedra + altoPiedra / 2 - 2);
	}

	public static boolean colisionEntre(Rectangle rect1, Rectangle rect2) {
		return rect1.intersects(rect2);
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

	public void quitar() {
		System.exit(0);
	}

	public void desplazarMono(Mono mono) { // METODO PARA TESTEAR. Hace que el mono se mueva en cualquier direccion.
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

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
