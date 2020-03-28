package com.krzem.wierd_2d_game;



public class Util{
	public static boolean collisionLineLine(double l1sx,double l1sy,double l1ex,double l1ey,double l2sx,double l2sy,double l2ex,double l2ey){
		return (((l2ex-l2sx)*(l1sy-l2sy)-(l2ey-l2sy)*(l1sx-l2sx))/((l2ey-l2sy)*(l1ex-l1sx)-(l2ex-l2sx)*(l1ey-l1sy))>=0&&((l2ex-l2sx)*(l1sy-l2sy)-(l2ey-l2sy)*(l1sx-l2sx))/((l2ey-l2sy)*(l1ex-l1sx)-(l2ex-l2sx)*(l1ey-l1sy))<=1&&((l1ex-l1sx)*(l1sy-l2sy)-(l1ey-l1sy)*(l1sx-l2sx))/((l2ey-l2sy)*(l1ex-l1sx)-(l2ex-l2sx)*(l1ey-l1sy))>=0&&((l1ex-l1sx)*(l1sy-l2sy)-(l1ey-l1sy)*(l1sx-l2sx))/((l2ey-l2sy)*(l1ex-l1sx)-(l2ex-l2sx)*(l1ey-l1sy))<=1);
	}


	
	public static boolean collisionLineRect(double lsx,double lsy,double lex,double ley,double rsx,double rsy,double rex,double rey){
		return (Util.collisionLineLine(lsx,lsy,lex,ley,rsx,rsy,rsx,rey)||Util.collisionLineLine(lsx,lsy,lex,ley,rex,rsy,rex,rey)||Util.collisionLineLine(lsx,lsy,lex,ley,rsx,rsy,rex,rsy)||Util.collisionLineLine(lsx,lsy,lex,ley,rsx,rey,rex,rey)||(rsx<=lsx&&lsx<=rex&&rsy<=lsy&&lsy<=rey)||(rsx<=lex&&lex<=rex&&rsy<=ley&&ley<=rey));
	}



	public static boolean collisionPointPoly(double px,double py,double[][] verts){
		boolean c=false;
		for (int i=0;i<verts.length;i++){
			if (((verts[i][1]>py&&verts[(i+1)%verts.length][1]<py)||(verts[i][1]<py&&verts[(i+1)%verts.length][1]>py))&&(px<(verts[(i+1)%verts.length][0]-verts[i][0])*(py-verts[i][1])/(verts[(i+1)%verts.length][1]-verts[i][1])+verts[i][0])) {
				c=!c;
			}
		}
		return c;
	}



	public static boolean collisionPolyRect(double[][] verts,double rsx,double rsy,double rex,double rey){
		for (int i=0;i<verts.length;i++){
			if (Util.collisionLineRect(verts[i][0],verts[i][1],verts[(i+1)%verts.length][0],verts[(i+1)%verts.length][1],rsx,rsy,rex,rey)||Util.collisionPointPoly(rsx,rsy,verts)){
				return true;
			}
		}
		return false;
	}



	public static boolean collisionLinePoly(double lsx,double lsy,double lex,double ley,double[][] verts){
		for (int i=0;i<verts.length;i++){
			if (Util.collisionLineLine(lsx,lsy,lex,ley,verts[i][0],verts[i][1],verts[(i+1)%verts.length][0],verts[(i+1)%verts.length][1])){
				return true;
			}
		}
		return false;
	}
}