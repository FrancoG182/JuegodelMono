package juego;

public class Configuracion {
	static int POSICION_Y_PISO = 460;
	static int VELOCIDAD = 1; // Si se llega a hacer los puntos extra del tp, agregar mas velocidades.

	static int GRAVEDAD = 3; // Velocidad a la que cae el mono.
	static int FUERZA_SALTO = 3; // Velocidad a la que salta el mono.
	static int LIMITE_SALTO = 1200 / FUERZA_SALTO; // Altura limite a la que puede saltar el mono. Se calcula a partir de
													// la fuerza de salto para que, a mayores fuerzas de salto, la
													// altura limite siga siendo la misma( funciona siqempre que la
													// fuerza de salto menor a 100).
//	static int LIMITE_SALTO = 30; 

}
