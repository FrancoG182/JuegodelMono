package juego;

public class Configuracion {
	static int POSICION_Y_PISO = 460;
	static int VELOCIDAD = 3; // Si se llega a hacer los puntos extra del tp, agregar mas velocidades.
	static int MIN_DIST_DIBUJADO_ENTRE_ARBOLES = 210;
	static int MAX_DIST_DIBUJADO_ENTRE_ARBOLES = 160 + VELOCIDAD * 100;
	static int COORD_X_DE_PRIMER_ARBOL = 600 - MIN_DIST_DIBUJADO_ENTRE_ARBOLES;
	
	static int CHANCE_DE_SERPIENTE_EN_ARBOL = 5;
	
	static int MIN_DIST_DIBUJADO_ENTRE_PUMAS = 300;
	static int MAX_DIST_DIBUJADO_ENTRE_PUMAS = 250 + VELOCIDAD * 100;
	
	static int COORD_X_DE_PRIMER_PUMA = 800;
	
	static int GRAVEDAD = 3; // Velocidad a la que cae el mono.
	static int FUERZA_SALTO = 3; // Velocidad a la que salta el mono.
	static int LIMITE_SALTO = 130 / FUERZA_SALTO; // Altura limite a la que puede saltar el mono. Se calcula a partir de
													// la fuerza de salto para que, a mayores fuerzas de salto, la
													// altura limite siga siendo la misma( funciona siqempre que la
													// fuerza de salto menor a 100).
	static int CANT_PUMAS = 3;
	static int CANT_ARBOLES = 10;
	static boolean MONO_DESPLAZAR = false;
	static boolean AVANZAR_ARBOL = true;
	static boolean AVANZAR_DEPREDADOR= true;
}
