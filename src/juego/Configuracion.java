package juego;

public class Configuracion {
	static int POSICION_Y_PISO = 460;
	static int VELOCIDAD = 2; // Si se llega a hacer los puntos extra del tp, agregar mas velocidades.

	static int MIN_DIST_DIBUJADO_ENTRE_ARBOLES = 210;
	static int MAX_DIST_DIBUJADO_ENTRE_ARBOLES = 160 + VELOCIDAD * 100;
	
	static int GRAVEDAD = 3; // Velocidad a la que cae el mono.
	static int FUERZA_SALTO = 3; // Velocidad a la que salta el mono.
	static int LIMITE_SALTO = 120 / FUERZA_SALTO; // Altura limite a la que puede saltar el mono. Se calcula a partir de
													// la fuerza de salto para que, a mayores fuerzas de salto, la
													// altura limite siga siendo la misma( funciona siqempre que la
													// fuerza de salto menor a 100).
	
	
	static int CANT_ARBOLES = 40;
	static boolean MONO_DESPLAZAR = false;
	static boolean AVANZAR_ARBOL = true;
}
