package com.krzem.wierd_2d_game;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;



public class BaseBlock{
	public Engine engine;
	public String name;
	public String path;
	public Vec2 pos;



	public BaseBlock(Engine engine,String name,int bx,int by){
		this.engine=engine;
		this.name=name;
		this.path="blocks/"+this.name+".png";
		this.pos=new Vec2(bx*this.engine.WORLD.BLOCK_WIDTH,by*this.engine.WORLD.BLOCK_HEIGHT);
	}



	public Rect get_rect(){
		return new Rect(this.pos.x-(int)this.engine.WORLD.BLOCK_WIDTH/2-this.engine.WORLD.OFFSET.x,this.engine.WINDOW_SIZE.height-(this.pos.y-this.engine.WORLD.OFFSET.y),this.pos.x+(int)this.engine.WORLD.BLOCK_WIDTH/2-this.engine.WORLD.OFFSET.x,this.engine.WINDOW_SIZE.height-(this.pos.y-this.engine.WORLD.BLOCK_HEIGHT-this.engine.WORLD.OFFSET.y));
	}
	public Rect get_b_rect(){
		return new Rect(this.pos.x-(int)this.engine.WORLD.BLOCK_WIDTH/2,this.pos.y-this.engine.WORLD.BLOCK_HEIGHT,this.pos.x+(int)this.engine.WORLD.BLOCK_WIDTH/2,this.pos.y);
	}



	public void draw(Graphics g){
		BufferedImage i=this.engine.IMAGES.get(this.path);
		Rect r=this.get_rect();
		g.drawImage(i,(int)r.A.x,(int)r.A.y,(int)r.B.x,(int)r.B.y,0,0,i.getWidth(),i.getHeight(),null);
	}
}