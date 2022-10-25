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

	// El fondo de pantalla.
	Image background;

	Mono mono;

	// Altura maxima a la que puede llegar el mono.
	int limiteSalto;

	// Arreglo que va a guardar a los arboles generados con sus ramas y las
	// serpientes y frutas de sus ramas.
	Arbol[] arboles;

	Rama[] ramas; // *
	static Fruta[] frutas; // *

	// (*) Tanto las ramas como las serpientes y las frutas se generan con los
	// mismos arboles. Estos arrays sirven para llevar registro de la posicion
	// actual de las frutas, las serpientes y las ramas para que el mono interactue
	// con ellas.

	Puma[] pumas;

	// A partir de la coordenada x de este arbol se calculara la x del siguiente
	// arbol a generarse.
	static Arbol ultimoArbolGenerado;

	// Cantidad de arboles seguidos con sus serpientes == null.
	static int arbolesSeguidosSinSerpiente;

	// A partir de la coordenada x de este puma se calculara la x del siguiente puma
	// a generarse.
	static Puma ultimoPumaGenerado;

	// Piedras del suelo.
	Piedra[] piedras;

	// A partir de la coordenada x de esta piedra se calculara la x de la siguiente
	// piedra a generarse.
	static Piedra ultimaPiedraGenerada;

	// Piedras que estan siendo lanzadas. Tendra elementos cuando el mono lance una
	// piedra y este elemento se eliminara cuando la piedra toque un depredador o se
	// salga de la pantalla.
	static Piedra[] piedrasArrojadas;

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Selva Mono Capuchino - Grupo 10 - v1", Configuracion.ANCHO_PANTALLA,
				Configuracion.ALTO_PANTALLA);

		// Inicializar lo que haga falta para el juego

		background = Herramientas.cargarImagen("background.jpg");

		mono = new Mono(100, 0);

		// El contador que va a contar la cantidad de ticks que el mono estuvo saltando
		// en el salto actual.
		limiteSalto = 0;

		// Arreglos de arboles, ramas y frutas.
		arboles = new Arbol[Configuracion.CANT_ARBOLES];
		ramas = new Rama[arboles.length];
		frutas = new Fruta[arboles.length];

		// Ayudara a que, cada cierta cantidad de arboles sin serpiente, se genere una
		// serpiente obligatoriamente.
		arbolesSeguidosSinSerpiente = 0;

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
		// Si el mono no esta muerto, iniciamos el juego.
		if (!mono.muerto) {
			entorno.dibujarImagen(background, 400, 300, 0, 1); // Se dibuja el fondo de pantalla.

			// Mostramos los puntos y la cantidad de piedras que tiene el mono.
			mostrarPuntos(mono, entorno);
			mostrarPiedras(mono, entorno);

			// Generamos los pumas.
			generarPumas(pumas);

			// Generamos lo arboles. Con ellos se generan las ramas, las serpientes y las
			// frutas.
			generarArboles(arboles);

			// Se asignan las ramas ya creadas en un arreglo para pasar este arreglo al
			// metodo gravedad().
			asignarRamasEnArreglos(arboles, ramas);

			// Generamos las piedras del suelo que el mono puede agarrar.
			generarPiedras(piedras);

			// Se dibuja cada arbol que no sea null con su rama. Si no son null, se dibujan
			// su fruta y su serpiente.
			for (Arbol arbol : arboles) {
				if (arbol != null) {
					arbol.dibujarse(entorno);

					// Ya que estamos recorriendo los arboles, vemos si la serpiente del arbol no es
					// null.
					Serpiente serpiente = arbol.rama.serpiente;
					if (serpiente != null) {
						comerAlMono(mono, serpiente); // Si no es null, vemos si esta colisionando con el mono.
					}

					// Ya que estamos recorriendo los arboles, vemos si la fruta del arbol no es
					// null.
					Fruta fruta = arbol.rama.fruta;
					if (fruta != null) {
						// Si no es null, vemos si esta colisionando con el mono.
						agarrarFruta(mono, fruta, arboles);
					}
				}
			}

			// Se dibuja cada puma que no sea null y vemos si esta colisionando con el mono.
			for (Puma puma : pumas) {
				if (puma != null) {
					puma.dibujarse(entorno);
					comerAlMono(mono, puma);
				}
			}

			// Se dibuja cada piedra DEL SUELO que no sea null.
			for (Piedra piedra : piedras) {
				if (piedra != null) {
					piedra.dibujarse(entorno);
				}
			}

			// Si se presiona la tecla espacio, el mono tira una piedra.
			if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
				arrojarPiedras(mono, piedrasArrojadas);
			}

			// Se dibuja cada piedra que no sea null QUE ESTE SIENDO ARROJADA.
			for (Piedra proyectil : piedrasArrojadas)
				if (proyectil != null) {
					proyectil.dibujarse(entorno);
					proyectil.lanzarPiedra();

					// Vemos si el proyectil esta colisionando con un depredador. Se pasa el arreglo
					// de arboles porque las serpientes pertenecen a los arboles.
					espantarDepredador(proyectil, pumas, arboles, mono);
				}

			// Se dibuja al mono.
			mono.dibujarse(entorno);

			// Se hacen avanzar los arboles con sus ramas, serpientes y frutas
			if (Configuracion.AVANZAR_ARBOL)
				avanzarArboles(arboles);

			// Se hacen avanzar los pumas.
			if (Configuracion.AVANZAR_PUMAS)
				avanzarPumas(pumas);

			// Se hacen avanzar las piedras.
			if (Configuracion.AVANZAR_PIEDRA)
				avanzarPiedras(piedras, mono);

			// Se hacen avanzar los proyectiles.
			avanzarProyectiles(piedrasArrojadas);

			// Si el mono no esta cayendo, se esta apretando la tecla arriba y no se llego
			// al limite del salto:
			if (!mono.monoCayendo && entorno.estaPresionada(entorno.TECLA_ARRIBA)
					&& limiteSalto < Configuracion.LIMITE_SALTO) {
				// el mono salta
				mono.saltar();

				// y hacemos crecer la variable que nos ayuda a llevar registro de cuanto esta
				// saltando el mono.
				limiteSalto++;
			} else {
				// Si no se entra en la rama afirmativa, se reinicia el limite del salto
				limiteSalto = 0;

				// y el mono cae.
				mono.gravedad(ramas);
			}

			 // PARA TESTEO. Permite al mono moverse en cualquier direccion.
			desplazarMono(mono);

		} else {
			// Si el mono esta muerto:
			entorno.cambiarFont("Consolas", 30, Color.white);

			// Calculamos el largo del String del puntaje final para situarlo en el centro
			// de la pantalla.
			int largoPuntaje = ("Puntos: " + mono.puntos).length();
			int xDePuntaje = Configuracion.ANCHO_PANTALLA / 2 - (18 * largoPuntaje) / 2;

			// Calculamos el largo del mensaje de la pantalla final para situarlo en el
			// centro de la pantalla, abajo del puntaje.
			String mensaje = "Presione la tecla ESPACIO para salir";
			int xDeMensaje = Configuracion.ANCHO_PANTALLA / 2 - (18 * mensaje.length()) / 2;

			// Mostramos la puntuacion y el mensaje final en pantalla.
			entorno.escribirTexto("Puntos: " + mono.puntos, xDePuntaje, 300);
			entorno.escribirTexto(mensaje, xDeMensaje, 500);

			// Si se aprieta la tecla espacios, se quita el juego.
			if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
				quitar();
			}
		}
	}

	/**
	 * Recibe como parametro un arreglo de arboles e inicializa cada elemento que
	 * sea null.
	 * 
	 * @param arboles Un arreglo de arboles.
	 */
	public static void generarArboles(Arbol[] arboles) {
		// El valor en x a partir del cual se va a calcular el x del primer arbol.
		int x = Configuracion.COORD_X_DE_PRIMER_ARBOL;

		// La chance de que el arbol sea alto.
		int chanceArbolAlto = Configuracion.CHANCE_DE_ARBOL_ALTO;
		// La chance de que el arbol tenga una serpiente.
		int chanceSerpiente = Configuracion.CHANCE_DE_SERPIENTE_EN_ARBOL;
		// La chance de que el arbol tenga una fruta.
		int chanceFruta = Configuracion.CHANCE_DE_FRUTA_EN_ARBOL;

		// Distancias minima y maxima que puede haber entre cada arbol.
		int distMin = Configuracion.MIN_DIST_DIBUJADO_ENTRE_ARBOLES;
		int distMax = Configuracion.MAX_DIST_DIBUJADO_ENTRE_ARBOLES;

		// Si el ultimo arbol que se creo no es null, x toma el valor de la coordenada
		// actual del ultimo arbol generado (aliasing util).
		if (ultimoArbolGenerado != null) {
			x = ultimoArbolGenerado.x;
		}

		// Para cada arbol:
		for (int i = 0; i < arboles.length; i++) {
			// Si el arbol es null, se lo va a generar. Un arbol puede ser null porque
			// recien empieza el juego o porque se salio de la pantalla.
			if (arboles[i] == null) {

				// A partir de la x inicial o la del ultimo arbol creado, se obtiene un numero
				// random que va
				// a ser la distancia entre el ultimo arbol y el siguiente.
				x = enteroAleatorio(x + distMin, x + distMax);

				// Si x es divisible por chanceArbolAlto:
				if (x % chanceArbolAlto == 0) {
					// Se crea un nuevo arbol alto,
					arboles[i] = new Arbol(x, true);
				} else {
					// si no, se crea un nuevo arbol bajo.
					arboles[i] = new Arbol(x, false);
				}

				// Si x es divisible por chanceSerpiente o si ya se eliminaron las serpientes de
				// 5 arboles seguidos:
				if (x % chanceSerpiente == 0 || arbolesSeguidosSinSerpiente == 5) {
					// no se le elimina la serpiente al arbol actual y se reinicia la
					// cuenta de arboles sin serpiente.
					arbolesSeguidosSinSerpiente = 0;
				} else {
					// En caso contrario, se elimina la serpiente del arbol actual y se va llevando
					// la cuenta de los arboles seguidos sin serpiente.
					arboles[i].rama.serpiente = null;
					arbolesSeguidosSinSerpiente++;
				}

				// Si x no es divisible por chanceFruta, se le elimina la fruta al arbol actual.
				if (x % chanceFruta != 0) {
					arboles[i].rama.fruta = null;
				}

				// Se registra el ultimo arbol creado. Aca se utiliza aliasing.
				ultimoArbolGenerado = arboles[i];
			}
		}
	}

	/**
	 * Recibe como parametro un arreglo de pumas e inicializa cada elemento que sea
	 * null.
	 * 
	 * @param pumas Un arreglo de pumas.
	 */
	public static void generarPumas(Puma[] pumas) {
		// El valor en x a partir del cual se va a calcular el x del primer puma.
		int x = Configuracion.COORD_X_DE_PRIMER_PUMA;

		// Distancias minima y maxima que puede haber entre cada puma.
		int distMin = Configuracion.MIN_DIST_DIBUJADO_ENTRE_PUMAS;
		int distMax = Configuracion.MAX_DIST_DIBUJADO_ENTRE_PUMAS;

		// Si el ultimo puma que se creo no es null, x toma el valor de la coordenada
		// actual del ultimo puma generado.
		if (ultimoPumaGenerado != null) {
			x = ultimoPumaGenerado.x;
		}

		// Para cada puma
		for (int i = 0; i < pumas.length; i++) {
			// Si el puma es null, se lo va a generar. Un puma puede ser null porque
			// recien empieza el juego o porque se salio de la pantalla.
			if (pumas[i] == null) {

				// A partir de la x inicial o la del ultimo puma creado, se obtiene un numero
				// random que va a ser la distancia entre el ultimo puma y el siguiente creado.
				x = enteroAleatorio(x + distMin, x + distMax);

				// Se crea el nuevo puma.
				pumas[i] = new Puma(x);
				// Se registra el ultimo puma creado. Aca se utiliza aliasing.
				ultimoPumaGenerado = pumas[i];
			}
		}
	}

	/**
	 * Recibe como parametro un arreglo de piedras e inicializa cada elemento que
	 * sea null.
	 * 
	 * @param piedras Un arreglo de piedras.
	 */
	public static void generarPiedras(Piedra[] piedras) {
		// El valor en x a partir del cual se va a calcular el x de la primera piedra.
		int x = Configuracion.COORD_X_DE_PRIMER_PIEDRA;

		// Distancias minima y maxima que puede haber entre cada piedra.
		int distMin = Configuracion.MIN_DIST_DIBUJADO_ENTRE_PIEDRAS;
		int distMax = Configuracion.MAX_DIST_DIBUJADO_ENTRE_PIEDRAS;

		// Si la ultima piedra que se creo no es null,
		// x toma el valor de la coordenada actual de la ultima piedra generada.
		if (ultimaPiedraGenerada != null) {
			x = ultimaPiedraGenerada.x;
		}

		// Para cada piedra
		for (int i = 0; i < piedras.length; i++) {
			// Si la piedra es null, se lo va a generar. Una piedra puede ser null porque
			// recien empieza el juego o porque se salio de la pantalla.
			if (piedras[i] == null) {

				// A partir de la x inicial o la de la ultima piedra creada, se obtiene un
				// numero random que va
				// a ser la distancia entre la ultima piedra y la siguiente.
				x = enteroAleatorio(x + distMin, x + distMax);

				// Se crea la nueva piedra. La Y no importa porque el constructor
				// la apoya sobre el piso.
				piedras[i] = new Piedra(x, 0, false);

				// Se registra la ultima piedra creada. Aca se utiliza aliasing.
				ultimaPiedraGenerada = piedras[i];
			}
		}
	}

	/**
	 * Asigna las ramas ya creadas en otro arreglo de ramas que se va a utilizar
	 * para que el mono interactue con ellas.
	 * 
	 * @param arboles Un arreglo de arboles ya inicializados con sus ramas.
	 * @param ramas   Un arreglo de ramas vacio.
	 */
	public static void asignarRamasEnArreglos(Arbol[] arboles, Rama[] ramas) {
		for (int i = 0; i < arboles.length; i++) {
			if (arboles[i] != null) {
				ramas[i] = arboles[i].rama;
			}
		}
	}

	/**
	 * Hace que los arboles avancen hacia la izquierda de la pantalla.
	 * 
	 * @param arboles
	 */
	public static void avanzarArboles(Arbol[] arboles) {
		for (int i = 0; i < arboles.length; i++) {
			// Si el arbol actual es null, salta a la siguiente iteracion.
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

	/**
	 * Hace que los pumas avancen hacia la izquierda de la pantalla.
	 * 
	 * @param pumas
	 */
	public static void avanzarPumas(Puma[] pumas) {
		for (int i = 0; i < pumas.length; i++) {
			// Si el puma actual es null, salta a la siguiente iteracion.
			if (pumas[i] == null) {
				continue;
			}

			// El puma se hace null si se sale de la pantalla.
			pumas[i].moverAdelante();
			if (pumas[i].pumaRect.x < -pumas[i].pumaRect.width * 2)
				pumas[i] = null;
		}
	}

	/**
	 * Hace que las piedras avancen hacia la izquierda de la pantalla y verifica si
	 * el mono esta colisionando con alguna.
	 * 
	 * @param piedras
	 * @param mono
	 */
	public static void avanzarPiedras(Piedra[] piedras, Mono mono) {
		for (int i = 0; i < piedras.length; i++) {
			// Si la piedra actual es null, salta a la siguiente iteracion.
			if (piedras[i] == null) {
				continue;
			}

			piedras[i].moverAdelante();

			// Si el mono agarra la piedra, esta se hace null.
			if (piedraAgarrada(mono, piedras[i])) {
				mono.agarrarPiedra();
				piedras[i] = null;

				// La piedra se hace null si se sale de la pantalla.
			} else if (piedras[i].piedraRect.x < -piedras[i].piedraRect.width) {
				piedras[i] = null;
			}
		}
	}

	/**
	 * Hace que los proyectile avancen hacia la derecha de la pantalla.
	 * 
	 * @param proyectiles Un arreglo de piedras con su
	 */
	public static void avanzarProyectiles(Piedra[] proyectiles) {
		for (int i = 0; i < proyectiles.length; i++) {
			// Si el proyectil actual es null, salta a la siguiente iteracion.
			if (proyectiles[i] == null) {
				continue;
			}

			// El proyectil avanza.
			proyectiles[i].lanzarPiedra();

			// El proyectil se hace null si se sale de la pantalla.
			if (proyectiles[i].piedraRect.x > Configuracion.ANCHO_PANTALLA) {
				proyectiles[i] = null;
			}
		}
	}

	/**
	 * Verifica que el mono colisiono con una piedra y si tiene espacio para
	 * agarrarla.
	 * 
	 * @param mono
	 * @param piedra
	 * @return Verdadero si el mono agarro la piedra y falso en caso contrario.
	 */
	public static boolean piedraAgarrada(Mono mono, Piedra piedra) {
		return (colisionEntre(mono.monoRect, piedra.piedraRect)
				&& mono.cantPiedras < Configuracion.CANT_PIEDRAS_QUE_PUEDE_TENER_EL_MONO);
	}

	/**
	 * Carga las piedras lanzadas en un arreglo para que luego se las pueda dibujar
	 * y que interactuen con depredadores.
	 * 
	 * @param mono
	 * @param piedrasArrojadas El arreglo que va a contener las piedras que estan
	 *                         siendo arrojadas en este momento.
	 */
	public static void arrojarPiedras(Mono mono, Piedra[] piedrasArrojadas) {
		// Si el mono tiene piedras:
		if (mono.cantPiedras > 0) {
			// se crea una nueva piedra.
			Piedra proyectil = mono.arrojarPiedra();

			// Se carga la piedra en un arreglo de piedras que estan
			// siendo arrojadas.
			for (int i = 0; i < piedrasArrojadas.length; i++) {
				// Si encuentra espacio, carga la piedra y termina el metodo.
				if (piedrasArrojadas[i] == null) {
					piedrasArrojadas[i] = proyectil;
					break;
				}
			}
		}
	}

	/**
	 * Verifica si el proyectil pasado como parametro colisiona con un depredador.
	 * Si es asi, el depredador y el proyectil pasan a ser null.
	 * 
	 * @param proyectil Una piedra del arreglo piedrasArrojadas.
	 * @param pumas     Un arreglo de pumas.
	 * @param arboles   Un arreglo de arboles. Se utiliza el arreglo de arboles para
	 *                  acceder a sus ramas y, de esta manera, a las serpientes.
	 * @param mono
	 */
	public static void espantarDepredador(Piedra proyectil, Puma[] pumas, Arbol[] arboles, Mono mono) {
		// Si el proyectil no es null:
		if (proyectil != null) {
			// Por cada puma:
			for (int i = 0; i < pumas.length; i++) {

				// si el puma actual no es null,
				if (pumas[i] != null) {

					// y si el proyectil colisiona con el puma:
					if (colisionEntre(proyectil.piedraRect, pumas[i].pumaRect)) {

						// el puma y el proyectil se eliminan,
						pumas[i] = null;
						eliminarProyectil(proyectil);

						// el mono gana puntos y termina el metodo.
						mono.ganarPuntos(Configuracion.PUNTOS_GANADOS_POR_ESPANTAR_DEPREDADOR);
						return;
					}
				}
			}

			// Por cada arbol:
			for (int i = 0; i < arboles.length; i++) {

				// si el arbol actual y la serpiente de su rama no son null
				if (arboles[i] != null && arboles[i].rama.serpiente != null) {

					// y si el proyectil colisiona con la serpiente:
					if (colisionEntre(proyectil.piedraRect, arboles[i].rama.serpiente.serpRect)) {
						// la serpiente y el proyectil se eliminan,
						arboles[i].rama.serpiente = null;
						eliminarProyectil(proyectil);

						// el mono gana puntos y termina el metodo.
						mono.ganarPuntos(Configuracion.PUNTOS_GANADOS_POR_ESPANTAR_DEPREDADOR);
						return;
					}
				}
			}
		}
	}

	/**
	 * Recibe una piedra como parametro y la busca en el arreglo de piedrasArrojadas
	 * para pasarla a null.
	 * 
	 * @param proyectil El proyectil a eliminar.
	 */
	public static void eliminarProyectil(Piedra proyectil) {
		// Por cada elemento en piedrasArrojadas:
		for (int i = 0; i < piedrasArrojadas.length; i++) {
			// Si la piedra pasada como parametro y la piedra actual son el mismo objeto,
			if (proyectil == piedrasArrojadas[i]) {
				// la piedra actual pasa a ser null y termina el metodo.
				piedrasArrojadas[i] = null;
				return;
			}
		}
	}

	/**
	 * Verfica si un puma esta colisionando con el mono y mata al mono en caso
	 * afirmativo.
	 * 
	 * @param mono
	 * @param puma
	 */
	public static void comerAlMono(Mono mono, Puma puma) {
		// Si el puma y el mono colisionan, el mono muere.
		if (colisionEntre(mono.monoRect, puma.pumaRect)) {
			mono.morirse();
		}
	}

	/**
	 * Verfica si una serpiente esta colisionando con el mono y mata al mono en caso
	 * afirmativo.
	 * 
	 * @param mono
	 * @param serpiente
	 */
	public static void comerAlMono(Mono mono, Serpiente serpiente) {
		// Si la serpiente y el mono colisionan, el mono muere.
		if (colisionEntre(mono.monoRect, serpiente.serpRect)) {
			mono.morirse();
		}
	}

	/**
	 * Verfica si una fruta esta colisionando con el mono. En caso afirmativo, se
	 * elimina la fruta y el mono gana puntos.
	 * 
	 * @param mono
	 * @param fruta
	 * @param arboles Un arreglo de arboles. Se utiliza el arreglo de arboles para
	 *                acceder a sus ramas y, de esta manera, a las frutas.
	 */
	public static void agarrarFruta(Mono mono, Fruta fruta, Arbol[] arboles) {
		if (colisionEntre(mono.monoRect, fruta.frutaRect)) {
			mono.ganarPuntos(Configuracion.PUNTOS_GANADOS_POR_COMER_FRUTA);
			eliminarFruta(fruta, arboles);
		}
	}

	/**
	 * Recibe una fruta como parametro y la busca en el arreglo de arboles para
	 * pasarla a null.
	 * 
	 * @param fruta
	 * @param arboles
	 */
	public static void eliminarFruta(Fruta fruta, Arbol[] arboles) {
		// Por cada arbol:
		for (int i = 0; i < arboles.length; i++) {

			// Si la fruta pasada com parametro es el mismo objeto que la fruta del arbol
			// actual,
			if (fruta == arboles[i].rama.fruta) {
				// la fruta del arbol actual pasa a ser null y termina el metodo.
				arboles[i].rama.fruta = null;
				return;
			}
		}
	}

	/**
	 * Muestra la puntuacion actual en el lado superior derecho de la pantalla.
	 * 
	 * @param mono
	 * @param entorno
	 */
	public void mostrarPuntos(Mono mono, Entorno entorno) {
		// Calcula el tamanio del String de la puntuacion.
		int largoPuntaje = ("PUNTOS: " + mono.puntos).length();

		// Segun el largo del puntaje, se calcula el x.
		int xDePuntaje = Configuracion.ANCHO_PANTALLA - 18 * largoPuntaje;

		int yDePuntaje = 30;

		// Se muestra el puntaje en pantalla:
		entorno.cambiarFont("Consolas", 30, Color.black);
		entorno.escribirTexto("PUNTOS: " + mono.puntos, xDePuntaje, yDePuntaje);
	}

	/**
	 * Muestra la cantidad de piedras que tiene el mono en el lado superior
	 * izquierdo de la pantalla.
	 * 
	 * @param mono
	 * @param entorno
	 */
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

	/**
	 * Devuelve verdadero si los rectangulos pasados como parametro estan
	 * colisionando y falso en caso contrario.
	 * 
	 * @param rect1
	 * @param rect2
	 * @return
	 */
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

	/**
	 * Devuelve un entero aleatorio.
	 * 
	 * @param minimo El valor minimo (inclusivo) que puede tomar el numero.
	 * @param maximo El valor maximo (inclusivo) que puede tomar el numero.
	 * @return Un entero aleatorio.
	 */
	public static int enteroAleatorio(int minimo, int maximo) {
		double r = Math.random();
		double res = minimo + (maximo - minimo) * r;
		return (int) Math.round(res);
	}

	/**
	 * Cierra el juego
	 */
	public void quitar() {
		System.exit(0);
	}

	/**
	 * METODO PARA TESTEAR. Hace que el mono se mueva en cualquier direccion.
	 * 
	 * @param mono
	 */
	public void desplazarMono(Mono mono) {
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
