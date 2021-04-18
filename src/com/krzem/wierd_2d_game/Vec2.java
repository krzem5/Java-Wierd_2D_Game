package com.krzem.wierd_2d_game;



public class Vec2{
	double x,y;



	public Vec2(double x,double y){
		this.x=x;
		this.y=y;
	}



	public boolean eq(double ox,double oy){
		return (this.x==ox&&this.y==oy);
	}



	public void add(Vec2 o){
		this.x+=o.x;
		this.y+=o.y;
	}



	public void add(int ox,int oy){
		this.x+=ox;
		this.y+=oy;
	}



	public void lerp(Vec2 o,double a){
		this.x=this.x*(1-a)+o.x*a;
		this.y=this.y*(1-a)+o.y*a;
	}
}
