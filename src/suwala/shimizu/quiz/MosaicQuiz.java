package suwala.shimizu.quiz;

import android.graphics.Bitmap;

public class MosaicQuiz extends Questions{

	Bitmap mosaicImage;
	String[] dummys;
	
	public MosaicQuiz(String question, String answer,String[] dummys,Bitmap image) {
		super(question, answer);
		this.mosaicImage = image;
		this.dummys = dummys;		
	}

	@Override
	public Bitmap getImage() {
		return mosaicImage;
	}

	@Override
	public String[] getDummys() {
		return dummys;
	}

}
