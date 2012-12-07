package suwala.shimizu.quiz;

import android.content.Context;
import android.util.Log;
import suwala.shimizu.quiz.QuizManager.Genre;
import suwala.shimizu.quiz.QuizManager.QuizCode;
import suwala.shimizu.quiz.QuizManager.State;
import suwala.shimizu.quizlogic.QuizIO;

public class QuizController implements QuizIO{
	
	QuizManager quizManager;
	
	public QuizController(Context context){
		//暫定ノージャンル クイズコード指定なし
				quizManager = new QuizManager(context, Genre.History,QuizCode.FourSelected);
	}
	
	@Override
	public void onStart(){
		quizManager.onResume();
	}	

	@Override
	public void onStop() {
		quizManager.onPause();
	}

	@Override
	public String[] getAnswers() {
		
		String[] r = new String[]{quizManager.getAnswer(),quizManager.getDummy()[0],
				quizManager.getDummy()[1],quizManager.getDummy()[2]};
		
		QuizManager.ShuffleBox.shuffle(r);
		return r;
	}

	@Override
	public StringBuilder getQuestion() {
		return quizManager.getQuestion();
	}

	@Override
	public int setAnswer(String answer) {
		
		if(quizManager.getQuizManagerState() == State.PLAY){
			quizManager.setAnswer(answer);
			if(quizManager.judge())
				return 0;
			else
				return 1;
		}
		quizManager.onEvents();
		
		return -1;
		
	}

	@Override
	public boolean getJudge() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void stepEvent() {
		
		quizManager.onEvents();
		
	}

}
