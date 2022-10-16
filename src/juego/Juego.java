package juego;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

import javax.swing.Timer;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Image background;
	Mono mono;
	Arbol arbol;

//	double coordPiso;
//	Arbol[] arboles;

	private Timer timer;

	// Variables y métodos propios de cada grupo
	// ...

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Selva Mono Capuchino - Grupo 10 - v1", 800, 600);
		background = Herramientas.cargarImagen("background.jpg");
		mono = new Mono(50, 410);
		arbol = new Arbol(300, 340);
//		arbol = new Arbol(300, 295); // Para la imagen del arbol

		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {
		// TIMER PARA ANIMAR AL MONO. INVESTIGAR.
//		ActionListener taskPerformer = new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				mono.animar();
//			}
//		};
//		timer = new Timer(500, taskPerformer);
//		timer.start();

		entorno.dibujarImagen(background, 400, 300, 0, 1);

//        mono.animar();

		arbol.dibujarse(entorno);
		mono.dibujarse(entorno);
		arbol.moverAdelante();
		if (arbol.x < -100) {
			arbol.x = 820;
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
