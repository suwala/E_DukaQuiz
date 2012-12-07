package suwala.shimizu.quiz.activitys;

import sample.postquiz.R;
import suwala.shimizu.quiz.QuizController;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizPlayActivity extends Activity {

	private QuizController quizController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question);
		
		quizController = new QuizController(this);
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		quizController.onStop();
		if(this.isFinishing())
			;
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		quizController.onStart();
		
		TextView tv = (TextView)findViewById(R.id.quetion);
		tv.setText(quizController.getQuestion().toString());
		
		String[] answers = quizController.getAnswers();
		
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setText(answers[0]);
		btn = (Button)findViewById(R.id.button2);
		btn.setText(answers[1]);
		btn = (Button)findViewById(R.id.button3);
		btn.setText(answers[2]);
		btn = (Button)findViewById(R.id.button4);
		btn.setText(answers[3]);
		
	}
	
	public void pushAnswerBtn(View v){
		int i = quizController.setAnswer(((Button)v).getText().toString());
		if(i == 0)
			Toast.makeText(this, "せいかい", Toast.LENGTH_SHORT).show();
		else if(i == 1)
			Toast.makeText(this, "まちがい", Toast.LENGTH_SHORT).show();
	}
	

}
