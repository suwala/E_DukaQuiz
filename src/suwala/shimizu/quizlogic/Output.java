package suwala.shimizu.quizlogic;

import android.graphics.Bitmap;

public interface Output {
	
	public StringBuilder getQuestion();
	public String getAnswer();
	public String[] getDummy();
	public Bitmap getImage();
}
