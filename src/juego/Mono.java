package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Mono {
	int x, y;
	Image img1, img2;
	Rectangle monoRect;
	boolean monoCayendo;

	public Mono(int x, int y) {
//		img1 = Herramientas.cargarImagen("Mono1.png");
		img1 = Herramientas.cargarImagen("Rect.png");
		img2 = Herramientas.cargarImagen("Mono2.png");
		monoCayendo = false;

		this.x = x;
		this.y = y;
//		this.y = Juego.apoyarSobrePiso(img1);

		// Coordenadas y tamanio del rectangulo que va a hacer de hitbox del mono.
		this.monoRect = new Rectangle();

//		this.monoRect.width = 80;	// Esta asignacion es para cuando se esta dibujando un rectangulo.
		this.monoRect.width = img1.getWidth(null); // Esta asignacion toma el ancho de la imagen que representa
													// al mono para armar el rectangulo.

//		this.monoRect.height = 80;	// Esta asignacion es para cuando se esta dibujando un rectangulo.
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
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);

//		entorno.dibujarRectangulo(this.x, this.y, this.monoRect.width, this.monoRect.height, 0.0, Color.gray);
	}

	// ESTA MAL ESTE METODO.
	// PREGUNTA POR UNA COORDENADA DE MANERA INEFICIENTE. ARREGLAR CUANTO ANTES.
	public void animar() {
		if (this.y == 410) {
			this.y = 415;
		} else {
			this.y = 410;
		}
	}

	public boolean sobreRama(Rama rama) {
		int topeDeRama = rama.ramaRect.y;
		int baseMono = this.monoRect.y + this.monoRect.height / 2;
		int proximaPosicion = baseMono + Configuracion.GRAVEDAD;

		boolean derMonoSobreIzqRama = this.monoRect.x + this.monoRect.width >= rama.ramaRect.x
				&& this.monoRect.x + this.monoRect.width <= rama.ramaRect.x + rama.ramaRect.width;

		boolean izqMonoSobreDerRama = this.monoRect.x >= rama.ramaRect.x
				&& this.monoRect.x <= rama.ramaRect.x + rama.ramaRect.width;

		boolean centroMonoSobreCentroRama = this.monoRect.x >= rama.ramaRect.x
				&& this.monoRect.x + this.monoRect.width <= rama.ramaRect.x + rama.ramaRect.width;
				
		if (derMonoSobreIzqRama || centroMonoSobreCentroRama || izqMonoSobreDerRama) {
			
			if (proximaPosicion >= topeDeRama && this.monoCayendo && baseMono <= topeDeRama) {
				this.y = topeDeRama - this.monoRect.height / 2; // El mono se coloca por encima de la rama.
				this.monoRect.y = topeDeRama - this.monoRect.height / 2; // Lo mismo para su hitbox.
				return true;
			}
		}
		return false;
	}

	public void gravedad(Rama rama, Arbol arbol) {
		int coordPiso = Configuracion.POSICION_Y_PISO;
		int limitePiso = Juego.apoyarSobrePiso(this.img1);

		if (this.y < limitePiso) { // Si el mono esta por encima del piso:
			this.monoCayendo = true;
			int proximaPosicion = this.y + Configuracion.GRAVEDAD; // Su Y crece tanto como diga GRAVEDAD (esto es la velocidad
															// de caida)
			if (this.sobreRama(rama)) {
				this.monoCayendo = false;
				
			} else if (proximaPosicion < limitePiso) { // Si la posicion a la que va a ser dibujado va a seguir por encima del piso:
				this.y = proximaPosicion; // El mono sigue cayendo.
				this.monoRect.y = proximaPosicion; // Lo mismo para la hitbox.
				
			} else { // Si la posicion a la que va a ser dibujado no va a estar por encima del piso
				// (es decir, toca el piso o lo traspasa):
				this.y = coordPiso - monoRect.height / 2; // El mono se coloca por encima del piso.
				this.monoRect.y = coordPiso - monoRect.height / 2; // Lo mismo para la hitbox.
				this.monoCayendo = false;
			}
		}
	}

	public void saltar() {
		this.monoCayendo = false;
		this.y -= Configuracion.FUERZA_SALTO;
		this.monoRect.y -= Configuracion.FUERZA_SALTO;
	}

	public void avanzar() {
		this.x += Configuracion.FUERZA_SALTO;
		this.monoRect.x += Configuracion.FUERZA_SALTO;
	}
}