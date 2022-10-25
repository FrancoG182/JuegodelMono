package juego;

//import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Mono {
	int x, y;
	Image img1;
	Rectangle monoRect;
	boolean monoCayendo;

	int cantPiedras;
	int puntos;

	boolean muerto;

	public Mono(int x, int y) {
		this.img1 = Herramientas.cargarImagen("Mono.png");
//		this.img1 = Herramientas.cargarImagen("Mono1.png");

		// Si el mono se muere, el juego termina.
		this.muerto = false;

		// Si el mono no esta cayendo, puede saltar.
		this.monoCayendo = false;

		// Cantidad de piedras actuales del mono.
		cantPiedras = Configuracion.CANT_PIEDRAS_INICIALES_DEL_MONO;

		// Cantidad de puntos actuales del mono.
		puntos = 0;

		// Cordenadas del mono-
		this.x = x;
		this.y = Juego.apoyarSobrePiso(img1);

		// Coordenadas y tamanio de la hitbox del mono.
		this.monoRect = new Rectangle();

		// Esta asignaciones toman el ancho y el alto de la imagen que representa al
		// mono para armar
		// el rectangulo.
		this.monoRect.width = img1.getWidth(null);
		this.monoRect.height = img1.getHeight(null);

//		 Teniendo en cuenta que los metodos preintegrados del TP dibujan las imagenes con
//		 su centro en las coordenadas pasadas como parametro, estas asignaciones
//		 calculan la posicion del rectangulo con respecto a la posicion de la imagen del mono.		
		this.monoRect.x = this.x - monoRect.width / 2;
		this.monoRect.y = this.y - monoRect.height / 2;
	}

	public void dibujarse(Entorno entorno) {
		// Hitbox
//		entorno.dibujarRectangulo(this.monoRect.x + monoRect.width / 2, this.monoRect.y + monoRect.height / 2,
//				this.monoRect.width, this.monoRect.height, 0.0, Color.gray);

		// Se dibuja el mono.
		entorno.dibujarImagen(img1, this.x, this.y, 0, 1);
	}

	/**
	 * Verifica si el mono esta parado sobre una rama. Ya de paso, si esta parado
	 * sobre una rama, el mono gana puntos.
	 * 
	 * @param rama Un arreglo de ramas.
	 * @return true si el mono esta sobre una ramma y falso en caso contrario.
	 */
	public boolean sobreRama(Rama rama) {
		// La parte de arriba de la rama donde se pararia el mono.
		int topeDeRama = rama.ramaRect.y;

		int baseMono = this.monoRect.y + this.monoRect.height;

		// La posicion en Y que va a tener el mono en el proximo instante.
		int proximaPosicion = baseMono + Configuracion.GRAVEDAD;

		// Verifica si el mono y la rama se corresponden con respecto al eje X.
		boolean derMonoSobreIzqRama = this.monoRect.x + this.monoRect.width >= rama.ramaRect.x
				&& this.monoRect.x + this.monoRect.width <= rama.ramaRect.x + rama.ramaRect.width;
		boolean izqMonoSobreDerRama = this.monoRect.x >= rama.ramaRect.x
				&& this.monoRect.x <= rama.ramaRect.x + rama.ramaRect.width;
		boolean centroMonoSobreCentroRama = this.monoRect.x >= rama.ramaRect.x
				&& this.monoRect.x + this.monoRect.width <= rama.ramaRect.x + rama.ramaRect.width;

		// Si se corresponden en el eje X:
		if (derMonoSobreIzqRama || centroMonoSobreCentroRama || izqMonoSobreDerRama) {

			// Si el mono esta cayendo, si la base del mono se encuentra por encima de la
			// rama y si en el siguiente instante el mono va a estar por debajo del tope de
			// la rama, quiere decir que se corresponden en el eje Y.
			if (proximaPosicion >= topeDeRama && this.monoCayendo && baseMono <= topeDeRama + 1) {
				// El mono se coloca por encima de la rama.
				this.y = topeDeRama - this.monoRect.height / 2;

				// Lo mismo para su hitbox.
				this.monoRect.y = this.y - this.monoRect.height / 2;

				// Y la rama da puntos, si es que todavia no los dio.
				if (!rama.yaDioPuntos) {
					this.ganarPuntos(Configuracion.PUNTOS_GANADOS_POR_PARARSE_EN_RAMA);
					rama.yaDioPuntos = true;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Hace que el mono caiga cuando no esta parado sobre una superficie.
	 * 
	 * @param ramas Un arreglo de ramas que se va a utilizar para que el mono
	 *              interactue con ellas.
	 */
	public void gravedad(Rama[] ramas) {
		// Coordenada Y del piso.
		int coordPiso = Configuracion.POSICION_Y_PISO;

		// Coordenada Y que toma el mono cuando toca el piso.
		int coorDeMonoSobrePiso = Juego.apoyarSobrePiso(this.img1);

		// Si el mono esta por encima del piso:
		if (this.y < coorDeMonoSobrePiso) {
			// El mono esta cayendo.
			this.monoCayendo = true;

			// Su Y crece tanto como diga GRAVEDAD (esto es la velocidad de caida).
			int proximaPosicion = this.y + Configuracion.GRAVEDAD;

			// Por cada rama que exista:
			for (Rama rama : ramas) {
				// se fija si el mono esta encima de ella. Si esta sobre una rama setea
				// monoCayendo en false y termina el metodo.
				if (rama != null && this.sobreRama(rama)) {
					this.monoCayendo = false;
					return;
				}
			}
			// Si la posicion a la que el mono va a ser dibujado va a seguir por encima del
			// piso:
			if (proximaPosicion < coorDeMonoSobrePiso) {
				// El mono sigue cayendo.
				this.y = proximaPosicion;
				// Lo mismo para su hitbox.
				this.monoRect.y = this.y - this.monoRect.height / 2;

			} else {
				// Si la posicion a la que va a ser dibujado no va a estar por encima del piso
				// (es decir, toca el piso o lo traspasa):

				// el mono se coloca por encima del piso
				this.y = coordPiso - this.monoRect.height / 2;
				// y lo mismo para la hitbox.
				this.monoRect.y = this.y - this.monoRect.height / 2;

				// Y deja de caer.
				this.monoCayendo = false;
			}
		}
	}

	public void agarrarPiedra() {
		if (cantPiedras < Configuracion.CANT_PIEDRAS_QUE_PUEDE_TENER_EL_MONO)
			this.cantPiedras++;
	}

	/**
	 * Crea una piedra que va a ser lanzada.
	 * 
	 * @return Una piedra que va a aparecer delante del mono y va a servir como
	 *         proyectil para defenderse de los depredadores.
	 */
	public Piedra arrojarPiedra() {
		this.cantPiedras--;
		int frenteMono = this.monoRect.x + this.monoRect.width;
		int mitadMono = this.monoRect.y + this.monoRect.height / 2;

		Piedra proyectil = new Piedra(frenteMono, mitadMono, true);

		return proyectil;
	}

	public void ganarPuntos(int puntosGanados) {
		// El valor mas grande que puede tomar la puntuacion.
		int limite = 99999999;

		// Si la puntuacion no va a ser mayor a limite:
		if (this.puntos + puntosGanados <= limite) {
			// suma los puntos ganados.
			this.puntos += puntosGanados;

		} else if (this.puntos + puntosGanados > limite) {
			// Si se va a pasar del limite le sumo lo que le falta para llegar a limite.
			this.puntos += limite - this.puntos;
		}
	}

	public void morirse() {
		// Si el mono no esta seteado para ser inmortal, se cambia su variable muerto a
		// true.
		if (!Configuracion.MONO_INMORTAL) {
			this.muerto = true;
		}
	}

	public void saltar() {
		this.monoCayendo = false;
		this.y -= Configuracion.FUERZA_SALTO;
		this.monoRect.y -= Configuracion.FUERZA_SALTO;
	}

	/**
	 * METODO PARA TESTEO. HACE QUE EL MONO SE PUEDA DESPLAZAR EN CUALQUIER
	 * DIRECCION.
	 */
	public void avanzar() {
		this.x += Configuracion.FUERZA_SALTO;
		this.monoRect.x += Configuracion.FUERZA_SALTO;
	}
}