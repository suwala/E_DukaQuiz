package suwala.shimizu.quiz.activitys;

import sample.postquiz.R;
import suwala.shimizu.quiz.QuizController;
import suwala.shimizu.quiz.QuizManager;
import suwala.shimizu.quiz.QuizManager.Genre;
import suwala.shimizu.quiz.QuizManager.QuizCode;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuizPlayActivity extends Activity {

	private QuizController quizController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question);
		
		//Context,Genre,QuizCodeを渡すことでフィルタをかける
		//フィルタなしだとGenre.Nogenre,nullでおｋ
		quizController = new QuizController(this, Genre.NoGenre, null);		
	}

	@Override
	protected void onPause() {
		super.onPause();
		quizController.onStop();
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		quizController.onStart();		
	}
	
	public void pushAnswerBtn(View v){
		quizController.onEvent(((Button)v).getText().toString());
	}

}
