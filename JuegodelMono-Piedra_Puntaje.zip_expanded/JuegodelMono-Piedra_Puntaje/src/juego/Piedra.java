package juego;

import java.awt.Image;
import java.awt.Rectangle;
import entorno.Entorno;
import entorno.Herramientas;

public class Piedra {
	int x, y;
	Image img1, img2;
	Rectangle piedraRect;
	
	public Piedra(int x, int y){
		//Coordenadas de la píedra que lanza el mono.
		this.x = x;
		this.y = y;
		
		//Hitbox de la piedra
		this.piedraRect = new Rectangle();
		this.piedraRect.width = 15;
		this.piedraRect.height = 20;
		this.piedraRect.x = x - piedraRect.width / 2;
		this.piedraRect.y = y - piedraRect.height / 2;
		
		
		img1 = Herramientas.cargarImagen("Rect.png");
		img2 = Herramientas.cargarImagen("Piedra1.png");
		
		
		
	}
	
	public void dibujarse(Entorno entorno) {
		
		entorno.dibujarImagen(img2,this.x,this.y,0,1);
		entorno.dibujarRectangulo(this.x, this.y, this.piedraRect.width, this.piedraRect.height, 0.0, null);
		
	}
		
	//Velocidad de la piedra lanzada
	public void Lanzamiento() {
		
		this.x += 5;
		this.piedraRect.x += 5;
		
      }
}
