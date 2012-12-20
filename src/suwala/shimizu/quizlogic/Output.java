package suwala.shimizu.quizlogic;

import android.graphics.Bitmap;

public interface Output {
	
	public String getViewQuestion();
	public Bitmap getViewImage();
	public String getViewAnswers(int i);
}
