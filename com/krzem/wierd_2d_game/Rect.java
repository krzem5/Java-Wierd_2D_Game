package com.krzem.wierd_2d_game;



public class Rect{
	public Vec2 A;
	public Vec2 B;



	public Rect(double a,double b,double c,double d){
		this.A=new Vec2(a,b);
		this.B=new Vec2(c,d);
	}



	public Rect copy(){
		return new Rect(this.A.x,this.A.y,this.B.x,this.B.y);
	}
}