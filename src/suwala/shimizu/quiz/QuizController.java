package suwala.shimizu.quiz;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;

import sample.postquiz.R;
import suwala.shimizu.quiz.QuizManager.Genre;
import suwala.shimizu.quiz.QuizManager.QuizCode;
import suwala.shimizu.quiz.QuizManager.State;
import suwala.shimizu.quizlogic.QuizIO;
/*
 * コントローラだけどメインループも兼ねている
 */
public class QuizController implements QuizIO,Runnable{
	
	private QuizManager quizManager;
	private Thread thread;
	private boolean running;
	private TextView q;
	private Button a1;
	private Button a2;
	private Button a3;
	private Button a4;
	private ImageView qImage;
	private Handler handler;
	
	public QuizController(Context context,Genre genre ,QuizCode quizCode){
		//暫定ノージャンル クイズコード指定なし
		quizManager = new QuizManager(context, genre,quizCode);
		running = false;
		q = (TextView)((Activity)context).findViewById(R.id.quetion);
		a1 = (Button)((Activity)context).findViewById(R.id.button1);
		a2 = (Button)((Activity)context).findViewById(R.id.button2);
		a3 = (Button)((Activity)context).findViewById(R.id.button3);
		a4 = (Button)((Activity)context).findViewById(R.id.button4);
		qImage = (ImageView)((Activity)context).findViewById(R.id.mosaic);
		handler = new Handler();
	}
	
	@Override
	public void onStart(){
		running = true;
		thread = new Thread(this);
		thread.start();
	}	

	@Override
	public void onStop() {
		running = false;
		while(true){
			try {
				thread.join();
				break;
			} catch (InterruptedException e) {
				//想定内なのでエラー無視
			}			
		}
	}

	@Override
	public void setAnswer(String answer) {
		
		quizManager.setAnswer(answer);
		
	}
	
	@Override
	public void onEvent(String userAnswer){
		quizManager.onEvents(userAnswer);
	}

	@Override
	public boolean getJudge() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}


	@Override
	public void run() {
		long runTime = System.currentTimeMillis();
		while(running){

			if(System.currentTimeMillis()-runTime<1000/30 || quizManager.state == State.LOAD)
				continue;
			runTime = System.currentTimeMillis();
			quizManager.setMillisTime(System.currentTimeMillis());
			handler.post(new Runnable() {

				@Override
				public void run() {
					
					q.setText(quizManager.getViewQuestion());
					a1.setText(quizManager.getViewAnswers(0));
					a2.setText(quizManager.getViewAnswers(1));
					a3.setText(quizManager.getViewAnswers(2));
					a4.setText(quizManager.getViewAnswers(3));
					qImage.setImageBitmap(quizManager.getViewImage());
				}
			});		

		}

	}

}
