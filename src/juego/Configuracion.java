package juego;

public class Configuracion {
	static int ANCHO_PANTALLA = 800;
	static int ALTO_PANTALLA = 600;
	
	static int POSICION_Y_PISO = 460;
	static int VELOCIDAD_OBJETOS = 5; // Si se llega a hacer los puntos extra del tp, agregar mas velocidades.

	static int MIN_DIST_DIBUJADO_ENTRE_ARBOLES = 210;
	static int MAX_DIST_DIBUJADO_ENTRE_ARBOLES = 160 + VELOCIDAD_OBJETOS * 100;
	static int COORD_X_DE_PRIMER_ARBOL = 600 - MIN_DIST_DIBUJADO_ENTRE_ARBOLES;

	static int CHANCE_DE_SERPIENTE_EN_ARBOL = 5;

	static int VELOCIDAD_PUMAS = 3;
	static int MIN_DIST_DIBUJADO_ENTRE_PUMAS = 300;
	static int MAX_DIST_DIBUJADO_ENTRE_PUMAS = 300 + VELOCIDAD_PUMAS * 100;
	static int COORD_X_DE_PRIMER_PUMA = 800;

	static int VELOCIDAD_PIEDRA_LANZADA = 2;
	
	static int MIN_DIST_DIBUJADO_ENTRE_PIEDRAS = 300;
	static int MAX_DIST_DIBUJADO_ENTRE_PIEDRAS = 500 + VELOCIDAD_OBJETOS * 100;
	static int COORD_X_DE_PRIMER_PIEDRA = 0;

	static int PUNTOS_GANADOS_POR_PARARSE_EN_RAMA = 5;
	
	static int GRAVEDAD = 3; // Velocidad a la que cae el mono.
	static int FUERZA_SALTO = 3; // Velocidad a la que salta el mono.
	static int LIMITE_SALTO = 130 / FUERZA_SALTO; // Altura limite a la que puede saltar el mono. Se calcula a partir de
													// la fuerza de salto para que, a mayores fuerzas de salto, la
													// altura limite siga siendo la misma( funciona siempre que la
													// fuerza de salto menor a LIMITE_SALTO).
	static int CANT_PUMAS = 3;
	static int CANT_ARBOLES = 10;
	static int CANT_PIEDRAS = 5;
	static int CANT_PIEDRAS_INICIALES_DEL_MONO = 1;
	static int CANT_PIEDRAS_QUE_PUEDE_TENER_EL_MONO = 2;
	static int CANT_PROYECTILES = 10; // Cantidad de piedras que pueden estar volando al mismo tiempo porque el mono las arrojo. 
	
	
	static boolean MONO_DESPLAZAR = true;
	static boolean AVANZAR_ARBOL = true;
	static boolean AVANZAR_DEPREDADOR = true;
	static boolean AVANZAR_PIEDRA = true;
}
