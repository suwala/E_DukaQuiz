package suwala.shimizu.quiz;

import android.graphics.Bitmap;

public class FourSelectQuiz extends Questions{

	private String[] dummys;
	
	public FourSelectQuiz(String q, String a,String[] dummys) {
		super(q, a);
		this.dummys = dummys;
	}

	@Override
	public Bitmap getImage() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String[] getDummys() {
		// TODO 自動生成されたメソッド・スタブ
		return dummys;
	}

}
