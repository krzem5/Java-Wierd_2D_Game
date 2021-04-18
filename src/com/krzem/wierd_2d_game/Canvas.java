package com.krzem.wierd_2d_game;



import java.awt.Graphics;
import javax.swing.JComponent;



public class Canvas extends JComponent{
	Engine cls;



	public Canvas(Engine p){
		this.cls=p;
	}



	@Override
	public void paintComponent(Graphics g){
		this.cls.draw(g);
		super.paintComponent(g);
		g.dispose();
	}



	@Override
	public void addNotify(){
		super.addNotify();
		this.requestFocus();
	}
}
