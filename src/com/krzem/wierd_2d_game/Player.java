package com.krzem.wierd_2d_game;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.Math;



public class Player{
	public Engine engine;
	public static final double SPEED=8*Engine.MAX_FPS;
	public static final double JUMP=20;
	public static final double JUMP_END=0.9*Engine.MAX_FPS;
	public static final double JUMP_COOLDOWN=0.25*Engine.MAX_FPS;
	public static final double GRAVITY=0.7;
	public static final double VEL_LERP=0.9;
	public static final double SMALL=1e-10;
	public int WIDTH;
	public int B_WIDTH;
	public int HEIGHT;
	public int B_HEIGHT;
	public String name;
	public Vec2 pos;
	public Vec2 screen_pos;
	public Vec2 vel;
	public Vec2 dvel;
	public int mr_id=0;
	public int jump_tm=-1;
	public boolean on_ground=true;



	public Player(Engine engine,String name,float px,float py){
		this.engine=engine;
		this.name=name;
		this.pos=new Vec2(px*this.engine.WORLD.BLOCK_WIDTH,py*this.engine.WORLD.BLOCK_HEIGHT);
		this.screen_pos=new Vec2(this.engine.WINDOW_SIZE.width/2,this.engine.WINDOW_SIZE.height/2);
		this.vel=new Vec2(0,0);
		this.dvel=new Vec2(0,0);
		BufferedImage i=this.engine.IMAGES.get("player/default.png");
		this.WIDTH=i.getWidth()*this.engine.SCALE*2;
		this.B_WIDTH=i.getWidth()*7/15*this.engine.SCALE*2;
		this.HEIGHT=i.getHeight()*this.engine.SCALE*2;
		this.B_HEIGHT=i.getHeight()*15/15*this.engine.SCALE*2;
	}



	public Rect get_rect(){
		return new Rect((int)this.screen_pos.x-(int)this.WIDTH/2+1,this.engine.WINDOW_SIZE.height-((int)this.screen_pos.y),(int)this.screen_pos.x+(int)this.WIDTH/2-1,this.engine.WINDOW_SIZE.height-((int)this.screen_pos.y-this.HEIGHT));
	}



	public Rect get_b_rect(){
		return this.get_b_rect(this.pos.x,this.pos.y);
	}
	public Rect get_b_rect(double x,double y){
		return new Rect(x-this.B_WIDTH/2,y-this.B_HEIGHT,x+this.B_WIDTH/2,y);
	}



	public double[][] get_b_poly(){
		Rect r=this.get_b_rect();
		Rect nr=this.get_b_rect(this.pos.x+(this.dvel.x*(1-this.VEL_LERP)+this.vel.x*this.VEL_LERP),this.pos.y+(this.dvel.y*(1-this.VEL_LERP)+this.vel.y*this.VEL_LERP));
		if (this.vel.x==0&&this.vel.y==0){
			double[][] p={{r.A.x,r.A.y},{r.A.x,r.B.y},{r.B.x,r.B.y},{r.B.x,r.A.y}};
			return p;
		}
		if (this.vel.x==0&&this.vel.y>0){
			double[][] p={{nr.A.x,nr.A.y},{r.A.x,r.B.y},{r.B.x,r.B.y},{nr.B.x,nr.A.y}};
			return p;
		}
		if (this.vel.x==0&&this.vel.y<0){
			double[][] p={{r.A.x,r.A.y},{nr.A.x,nr.B.y},{nr.B.x,nr.B.y},{r.B.x,r.A.y}};
			return p;
		}
		if (this.vel.x<0&&this.vel.y==0){
			double[][] p={{nr.A.x,nr.A.y},{nr.A.x,nr.B.y},{r.B.x,r.B.y},{r.B.x,r.A.y}};
			return p;
		}
		if (this.vel.x<0&&this.vel.y<0){
			double[][] p={{r.A.x,r.A.y},{nr.A.x,nr.A.y},{nr.A.x,nr.B.y},{nr.B.x,nr.B.y},{r.B.x,r.B.y},{r.B.x,r.A.y}};
			return p;
		}
		if (this.vel.x<0&&this.vel.y>0){
			double[][] p={{nr.A.x,nr.A.y},{nr.A.x,nr.B.y},{r.A.x,r.B.y},{r.B.x,r.B.y},{r.B.x,r.A.y},{nr.B.x,nr.A.y}};
			return p;
		}
		if (this.vel.x>0&&this.vel.y==0){
			double[][] p={{r.A.x,r.A.y},{r.A.x,r.B.y},{nr.B.x,nr.B.y},{nr.B.x,nr.A.y}};
			return p;
		}
		if (this.vel.x>0&&this.vel.y<0){
			double[][] p={{r.A.x,r.A.y},{r.A.x,r.B.y},{nr.A.x,nr.B.y},{nr.B.x,nr.B.y},{nr.B.x,nr.A.y},{r.B.x,r.A.y}};
			return p;
		}
		if (this.vel.x>0&&this.vel.y>0){
			double[][] p={{r.A.x,r.A.y},{r.A.x,r.B.y},{r.B.x,r.B.y},{nr.B.x,nr.B.y},{nr.B.x,nr.A.y},{nr.A.x,nr.A.y}};
			return p;
		}
		return null;
	}



	private BufferedImage get_image(){
		if (this.jump_tm>-1&&this.jump_tm<=this.JUMP_END){
			switch (this.mr_id){
				case 0:
					return this.engine.IMAGES.get("player/l-jump.png");
				case 1:
					return this.engine.IMAGES.get("player/lm-jump.png");
				case 2:
					return this.engine.IMAGES.get("player/r-jump.png");
				case 3:
					return this.engine.IMAGES.get("player/rm-jump.png");
			}
		}
		if (this.vel.x==0){
			switch (this.mr_id){
				case 0:
					return this.engine.IMAGES.get("player/l-stand.png");
				case 1:
					return this.engine.IMAGES.get("player/lm-stand.png");
				case 2:
					return this.engine.IMAGES.get("player/r-stand.png");
				case 3:
					return this.engine.IMAGES.get("player/rm-stand.png");
			}
		}
		return this.engine.IMAGES.get("player/default.png");
	}



	public void collision(){
		double[][] p=this.get_b_poly();
		for (BaseBlock B:this.engine.WORLD.blocks){
			Rect b=B.get_b_rect();
			if (this.vel.x>0&&b.B.y>this.pos.y-this.B_HEIGHT&&Util.collisionLinePoly(b.A.x,b.A.y,b.A.x,b.B.y,p)){
				this.pos.x=b.A.x-this.B_WIDTH/2;
				this.vel.x=0;
				break;
			}
			if (this.vel.x<0&&b.B.y>this.pos.y-this.B_HEIGHT&&Util.collisionLinePoly(b.B.x,b.A.y,b.B.x,b.B.y,p)){
				this.pos.x=b.B.x+this.B_WIDTH/2;
				this.vel.x=0;
				break;
			}
		}
		this.on_ground=false;
		if (this.vel.y<=0){
			p=this.get_b_poly();
			for (BaseBlock B:this.engine.WORLD.blocks){
				Rect b=B.get_b_rect();
				if ((this.pos.y==b.B.y-this.B_HEIGHT&&this.vel.y==0)||(this.pos.y<=b.B.y+this.B_HEIGHT&&Util.collisionLinePoly(b.A.x+this.SMALL,b.B.y,b.B.x-this.SMALL,b.B.y,p))){
					this.vel.y=0;
					this.pos.y=b.B.y+this.B_HEIGHT;
					this.on_ground=true;
					break;
				}
			}
		}
		else{
			p=this.get_b_poly();
			for (BaseBlock B:this.engine.WORLD.blocks){
				Rect b=B.get_b_rect();
				if (Util.collisionLinePoly(b.A.x+this.SMALL,b.A.y,b.B.x-this.SMALL,b.A.y,p)){
					this.vel.y=0;
					this.pos.y=b.A.y;
					this.jump_tm=(int)this.JUMP_END;
					break;
				}
			}
		}
	}



	public void update(){
		if (this.engine.KEYBOARD.pressed(KeyEvent.VK_A)==true&&this.engine.KEYBOARD.pressed(KeyEvent.VK_D)==false&&(this.on_ground==true||(this.jump_tm>-1&&this.vel.x>=0))){
			this.vel.x=-this.SPEED/this.engine.FPS;
		}
		else if (this.engine.KEYBOARD.pressed(KeyEvent.VK_A)==false&&this.engine.KEYBOARD.pressed(KeyEvent.VK_D)==true&&(this.on_ground==true||(this.jump_tm>-1&&this.vel.x<=0))){
			this.vel.x=this.SPEED/this.engine.FPS;
		}
		else{
			this.vel.x=0;
		}
		if (this.engine.KEYBOARD.pressed(KeyEvent.VK_SPACE)==true&&this.jump_tm==-1&&this.on_ground==true){
			this.vel.y+=this.JUMP;
			this.jump_tm=0;
		}
		if (this.vel.x==0&&(this.jump_tm==-1||this.jump_tm>this.JUMP_END)){
			if (this.engine.MOUSE_POS.x<this.screen_pos.x-Math.ceil(this.WIDTH/2)){
				this.mr_id=0;
			}
			else if (this.engine.MOUSE_POS.x<this.screen_pos.x){
				this.mr_id=1;
			}
			else if (this.engine.MOUSE_POS.x>this.screen_pos.x+Math.ceil(this.WIDTH/2)){
				this.mr_id=2;
			}
			else{
				this.mr_id=3;
			}
		}
		if (this.jump_tm>-1){
			this.jump_tm++;
			if (this.jump_tm>=this.JUMP_END+this.JUMP_COOLDOWN){
				this.jump_tm=-1;
			}
		}
		this.vel.y-=this.GRAVITY;
		this.collision();
		this.dvel.lerp(this.vel,this.VEL_LERP);
		this.pos.add(this.dvel);
		this.engine.WORLD.OFFSET=new Vec2(this.pos.x-(int)this.engine.WINDOW_SIZE.width/2,this.pos.y-(int)this.engine.WINDOW_SIZE.height/2);
	}



	public void draw(Graphics g){
		BufferedImage i=this.get_image();
		Rect r=this.get_rect();
		g.drawImage(i,(int)r.A.x,(int)r.A.y,(int)r.B.x,(int)r.B.y,0,0,i.getWidth(),i.getHeight(),null);
	}
}
