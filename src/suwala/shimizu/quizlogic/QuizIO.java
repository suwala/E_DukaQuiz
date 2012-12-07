package suwala.shimizu.quizlogic;

public interface QuizIO {
	
	//onStart,onStopはコントーラではないか
	public void onStart();
	public void onStop();
	
	public String[] getAnswers();
	public StringBuilder getQuestion();
	
	public int setAnswer(String answer);
	public boolean getJudge();
	
	public void stepEvent();

}
