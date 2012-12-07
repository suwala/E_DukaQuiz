package suwala.shimizu.quiz;

import android.graphics.Bitmap;

public abstract class Questions {
	
	String q;
	String a;
	
	public Questions(String q,String a) {
		
		this.q = q;
		this.a = a;
		
	}
	
	public String getAnswer(){
		return a;
	}
	
	public String getQuestion(){
		return q;
	}

	public abstract Bitmap getImage();
	public abstract String[] getDummys();
}
