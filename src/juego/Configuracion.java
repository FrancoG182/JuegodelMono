package juego;

public class Configuracion {
	final static int ANCHO_PANTALLA = 800;
	final static int ALTO_PANTALLA = 600;
	
	final static int POSICION_Y_PISO = 460;

	final static int VELOCIDAD_OBJETOS = 3;

	final static int MIN_DIST_DIBUJADO_ENTRE_ARBOLES = 210;
	final static int MAX_DIST_DIBUJADO_ENTRE_ARBOLES = 160 + VELOCIDAD_OBJETOS * 100;
	final static int COORD_X_DE_PRIMER_ARBOL = 600 - MIN_DIST_DIBUJADO_ENTRE_ARBOLES;

	final static int CHANCE_DE_ARBOL_ALTO = 8;
	final static int CHANCE_DE_SERPIENTE_EN_ARBOL = 6;
	final static int CHANCE_DE_FRUTA_EN_ARBOL = 9;

	final static int VELOCIDAD_PUMAS = 4;
	final static int MIN_DIST_DIBUJADO_ENTRE_PUMAS = 350;
	final static int MAX_DIST_DIBUJADO_ENTRE_PUMAS = 350 + VELOCIDAD_PUMAS * 100;
	final static int COORD_X_DE_PRIMER_PUMA = 800;
	
	final static int VELOCIDAD_PIEDRA_LANZADA = 5;
	final static int MIN_DIST_DIBUJADO_ENTRE_PIEDRAS = 280;
	final static int MAX_DIST_DIBUJADO_ENTRE_PIEDRAS = 500 + VELOCIDAD_OBJETOS * 100;
	final static int COORD_X_DE_PRIMER_PIEDRA = 0;

	final static int PUNTOS_GANADOS_POR_PARARSE_EN_RAMA = 5;
	final static int PUNTOS_GANADOS_POR_ESPANTAR_DEPREDADOR = 1;
	final static int PUNTOS_GANADOS_POR_COMER_FRUTA = 10;
	
	final static int GRAVEDAD = 3; // Velocidad a la que cae el mono.
	final static int FUERZA_SALTO = 3; // Velocidad a la que salta el mono.
	final static int LIMITE_SALTO = 130 / FUERZA_SALTO; // Altura limite a la que puede saltar el mono. Se calcula a partir de
													// la fuerza de salto para que, a mayores fuerzas de salto, la
													// altura limite siga siendo la misma( funciona siempre que la
													// fuerza de salto menor a LIMITE_SALTO).
	final static int CANT_PUMAS = 5;
	final static int CANT_ARBOLES = 5;
	final static int CANT_PIEDRAS = 5;
	final static int CANT_PIEDRAS_INICIALES_DEL_MONO = 1;
	final static int CANT_PIEDRAS_QUE_PUEDE_TENER_EL_MONO = 2;
	final static int CANT_PROYECTILES = 10; // Cantidad de piedras que pueden estar volando al mismo tiempo porque el mono las arrojo. 
	
	
	final static boolean MONO_INMORTAL = false;
	final static boolean MONO_DESPLAZAR = false;
	final static boolean AVANZAR_ARBOL = true;
	final static boolean AVANZAR_PUMAS = true;
	final static boolean AVANZAR_PIEDRA = true;
}
