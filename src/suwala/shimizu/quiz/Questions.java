package suwala.shimizu.quiz;

public abstract class Questions {
	
	String q;
	String a;
	
	public Questions(String q,String a) {
		
		this.q = q;
		this.a = a;
		
		setup();
	}
	
	abstract void setup();

}
