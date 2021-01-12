package com.krzem.wierd_2d_game;



import java.awt.Graphics;
import javax.swing.JComponent;



public class Canvas extends JComponent{
	Engine parent;



	public Canvas(Engine p){
		this.parent=p;
	}



	public void paintComponent(Graphics g){
		this.parent.draw(g);
		super.paintComponent(g);
		g.dispose();
	}



	public void addNotify(){
		super.addNotify();
		this.requestFocus();
	}
}