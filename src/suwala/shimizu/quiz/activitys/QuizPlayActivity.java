package suwala.shimizu.quiz.activitys;

import sample.edukaquiz.R;
import suwala.shimizu.quiz.QuizManager;
import suwala.shimizu.quiz.QuizManager.Genre;
import suwala.shimizu.quiz.QuizManager.QuizCode;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class QuizPlayActivity extends Activity {

	private QuizManager quizManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question);
		
		//暫定ノージャンル クイズコード指定なし
		quizManager = new QuizManager(this, Genre.History,QuizCode.FourSelected);
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		quizManager.onPause();
		if(this.isFinishing())
			quizManager.finish();
		
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		quizManager.onResume();
		
		TextView tv = (TextView)findViewById(R.id.quetion);
		Log.d("aaa",quizManager.getQuestion().toString());
		tv.setText(quizManager.getQuestion().toString());
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setText(quizManager.getAnswer());
	}
	
	

}
