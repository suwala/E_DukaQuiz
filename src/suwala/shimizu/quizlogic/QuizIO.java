package suwala.shimizu.quizlogic;

public interface QuizIO {
	
	//onStart,onStopはコントーラではないか
	public void onStart();
	public void onStop();
		
	public void onEvent(String userAnswer);
	public void setAnswer(String answer);
	public boolean getJudge();
	

}
