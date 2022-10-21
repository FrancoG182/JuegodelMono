package juego;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import entorno.Entorno;
import entorno.Herramientas;

public class PiedraEstatica {
	int x, y;
	Image img1, img2;
	Rectangle piedraestRect;
	
	public PiedraEstatica(int x, int y){
		
		//Coordenadas de la piedra
		this.x = x;
		this.y = y;
		//Hitbox de la piedra que agarra el mono
		this.piedraestRect = new Rectangle();
		this.piedraestRect.width = 15;
		this.piedraestRect.height = 20;
		this.piedraestRect.x = x - piedraestRect.width / 2;
		this.piedraestRect.y = y - piedraestRect.height / 2;
		
		img1 = Herramientas.cargarImagen("Rect.png");
		img2 = Herramientas.cargarImagen("Piedra1.png");
		
		
		
	}
	
	public void dibujarse(Entorno entorno) {
		
		entorno.dibujarImagen(img2,this.x,this.y,0,1);
		entorno.dibujarRectangulo(this.x, this.y, this.piedraestRect.width, this.piedraestRect.height, 0.0, Color.black);
		
	}
		
		//Velocidad con la que se acerca la piedra al mono
	public void MoverAdelante(){
		this.x -= 1;
		this.piedraestRect.x -= 1;
		}
	
}



