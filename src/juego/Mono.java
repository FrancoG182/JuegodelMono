package juego;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Mono {
	int x, y;
	Image img1, img2;
	Rectangle monoRect;
	boolean monoCayendo;

	int cantPiedras;
	boolean tienePiedras;

	boolean muerto;

	int puntos;

	public Mono(int x, int y) {
		this.img1 = Herramientas.cargarImagen("Mono.png");
//		this.img1 = Herramientas.cargarImagen("Rect.png");
//		this.img2 = Herramientas.cargarImagen("Mono2.png");

		this.muerto = false;

		this.monoCayendo = false;

		cantPiedras = Configuracion.CANT_PIEDRAS_INICIALES_DEL_MONO;
		puntos = 0;

		this.x = x;
		this.y = Juego.apoyarSobrePiso(img1);

		// Coordenadas y tamanio del rectangulo que va a hacer de hitbox del mono.
		this.monoRect = new Rectangle();

		this.monoRect.width = img1.getWidth(null); // Esta asignacion toma el ancho de la imagen que representa
													// al mono para armar el rectangulo.

		this.monoRect.height = img1.getHeight(null); // Esta asignacion toma el alto de la imagen que representa
														// al mono para armar el rectangulo.

		/*
		 * Teniendo en cuenta que los metodos preintegrados del TP dibujan las imgs con
		 * su centro en las coordenadas pasadas como parametro, estas asignaciones
		 * calculan la posicion del rectangulo con respecto a la posicion de la img.
		 */
		this.monoRect.x = this.x - monoRect.width / 2;
		this.monoRect.y = this.y - monoRect.height / 2;
	}

	public void dibujarse(Entorno entorno) {
		// Hitbox
//		entorno.dibujarRectangulo(this.monoRect.x + monoRect.width / 2, this.monoRect.y + monoRect.height / 2,
//				this.monoRect.width, this.monoRect.height, 0.0, Color.gray);

		// Imagen
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
	}

	public boolean sobreRama(Rama rama) {
		int topeDeRama = rama.ramaRect.y;
		int baseMono = this.monoRect.y + this.monoRect.height;
		int proximaPosicion = baseMono + Configuracion.GRAVEDAD;

		boolean derMonoSobreIzqRama = this.monoRect.x + this.monoRect.width >= rama.ramaRect.x
				&& this.monoRect.x + this.monoRect.width <= rama.ramaRect.x + rama.ramaRect.width;

		boolean izqMonoSobreDerRama = this.monoRect.x >= rama.ramaRect.x
				&& this.monoRect.x <= rama.ramaRect.x + rama.ramaRect.width;

		boolean centroMonoSobreCentroRama = this.monoRect.x >= rama.ramaRect.x
				&& this.monoRect.x + this.monoRect.width <= rama.ramaRect.x + rama.ramaRect.width;

		if (derMonoSobreIzqRama || centroMonoSobreCentroRama || izqMonoSobreDerRama) {

			if (proximaPosicion >= topeDeRama && this.monoCayendo && baseMono <= topeDeRama + 1) {
				this.y = topeDeRama - this.monoRect.height / 2; // El mono se coloca por encima de la rama.
				this.monoRect.y = this.y - this.monoRect.height / 2; // Lo mismo para su hitbox.
				if (!rama.yaDioPuntos) {
					this.ganarPuntos(Configuracion.PUNTOS_GANADOS_POR_PARARSE_EN_RAMA);
					rama.yaDioPuntos = true;
				}
				return true;
			}
		}
		return false;
	}

	public void gravedad(Rama[] ramas) {
		int coordPiso = Configuracion.POSICION_Y_PISO;
		int limitePiso = Juego.apoyarSobrePiso(this.img1);

		if (this.y < limitePiso) { // Si el mono esta por encima del piso:
			this.monoCayendo = true;
			int proximaPosicion = this.y + Configuracion.GRAVEDAD; // Su Y crece tanto como diga GRAVEDAD (esto es la
																	// velocidad de caida)-
			for (Rama rama : ramas) { // Por cada rama que exista, se fija si el mono esta encima de ella. Si esta
										// sobre una rama setea monoCayendo en false y termina el metodo.
				
				if (rama != null && this.sobreRama(rama)) {
					this.monoCayendo = false;
					return;
				}
			}
			if (proximaPosicion < limitePiso) { // Si la posicion a la que va a ser dibujado va a seguir por encima del
												// piso:
				this.y = proximaPosicion; // El mono sigue cayendo.
				this.monoRect.y = this.y - this.monoRect.height / 2; // Lo mismo para la hitbox.

			} else { // Si la posicion a la que va a ser dibujado no va a estar por encima del piso
						// (es decir, toca el piso o lo traspasa):
				
				this.y = coordPiso - this.monoRect.height / 2; // El mono se coloca por encima del piso.
				this.monoRect.y = this.y - this.monoRect.height / 2; // Lo mismo para la hitbox.
				this.monoCayendo = false;
			}
		}
	}

	public void agarrarPiedra() {
		if (cantPiedras < Configuracion.CANT_PIEDRAS_QUE_PUEDE_TENER_EL_MONO)
			this.cantPiedras++;
	}

	public Piedra arrojarPiedra() {
		this.cantPiedras--;
		int frenteMono = this.monoRect.x + this.monoRect.width;
		int mitadMono = this.monoRect.y + this.monoRect.height / 2;

		Piedra proyectil = new Piedra(frenteMono, mitadMono, true);

		return proyectil;
	}

	public void ganarPuntos(int puntosGanados) {
		int limite = 99999999; // El valor mas grande que puede tomar un entero.

		if (this.puntos + puntosGanados <= limite) { // Si la puntuacion no va a ser mayor a limite:
			this.puntos += puntosGanados; // Sumar puntos ganados.
		} else if (this.puntos + puntosGanados > limite) {// Si se va a pasar del limite,
			this.puntos += limite - this.puntos; // Le sumo lo que le falta para llegar a limite
		}
	}

	public void morirse() {
		if (!Configuracion.MONO_INMORTAL) {
			this.muerto = true;
		}
	}

	public void saltar() {
		this.monoCayendo = false;
		this.y -= Configuracion.FUERZA_SALTO;
		this.monoRect.y -= Configuracion.FUERZA_SALTO;
	}

	public void avanzar() { // METODO PARA TESTEO. HACE QUE EL MONO SE PUEDA DESPLAZAR EN CUALQUIER
							// DIRECCION.
		this.x += Configuracion.FUERZA_SALTO;
		this.monoRect.x += Configuracion.FUERZA_SALTO;
	}
}