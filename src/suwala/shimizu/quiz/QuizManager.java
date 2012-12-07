package suwala.shimizu.quiz;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import suwala.shimizu.quizlogic.DataLoding;
import suwala.shimizu.quizlogic.Output;

public class QuizManager implements Output{

	private Questions q;
	private int[] order;
	private int quizIndex;
	private int totalQ = 3;
	private State state;
	private QuizState qState;
	private QuizFactory quizFactory;
	private long startTime;
	private DataLoding dl;
	private StringBuilder question  = new StringBuilder(255);
	
	private final int WAIT_TIME_LIMIT = 3000;
	private final int PLAY_TIME_LIMIT = 10000;
	private final int JUDGE_TIME_LIMIT = 3000;
	
	public static enum State{
		PLAY,JUDGE,WAIT,FINISH
	}
	
	public static enum QuizState{
		PLAY,PAUSE
	}
	
	public static enum Genre{
		
		NoGenre(""),History("歴史"),Zatsugaku("雑学"),E_duka("飯塚"),Learning("学問"),GeAni("ゲーム＆アニメ"),Geography("地理");
		private String genre;
		
		private Genre(String genre){
			this.genre = genre;
		}
		
		public String getGenre(){
			return genre;
		}
	}
	
	public static enum QuizCode{
		FourSelected(0),Mosaic(1),Scratch(2),marubatu(3),Typing(4),CharaMove(5);
		int code;
		
		private QuizCode(int code){
			this.code = code;
		}
		
		public int getQuizCode(){
			return code;
		}
	}
	
	public void setTotalQ(int i){
		totalQ = i;
	}
	
	public static class ShuffleBox{
		public static void shuffle(Object[] o){
			for(int i=0;i<o.length;i++){
				int dst = (int)Math.floor(Math.random()*(i+1));
				Object j = o[i];
				o[i] = o[dst];
				o[dst] = j;
			}
		}
		
		public static void shuffle(int[] box){

			for(int i=0;i<box.length;i++){
				int dst = (int)Math.floor(Math.random()*(i+1));
				int j = box[i];
				box[i] = box[dst];
				box[dst] = j;
			}
		}
	}
	
	public QuizManager(Context context,Genre genre,QuizCode code){
		
		dl = new QuizDatabaseRW(context,genre,code);
		int total = dl.getRecordCount();
		
		Log.d("Total",String.valueOf(total));
		
		order = new int[total];
		for(int i=0;i<total;i++)
			order[i] = i;
		
		ShuffleBox.shuffle(order);
		quizIndex = 0;
		quizFactory = new QuizFactory();
		state = State.WAIT;
		question = new StringBuilder(255);
		
		Playing playing = new Playing();
		playing.start();
		quizWait();
	}
	
	public void onResume(){
		qState = QuizState.PLAY;
	}
	
	public void onPause(){
		qState = QuizState.PAUSE;
	}
	
	public void finish(){
		qState = QuizState.PAUSE;
	}
	
	public synchronized void onEvents(){
		
		if(state == State.PLAY){
			state = State.JUDGE;
			quizJudge();
			return;
		}
		if(state == State.WAIT){
			state = State.PLAY;
			quizStart();
			return;
		}
		
		//処理をしなければ自動進行のみ
		if(state == State.JUDGE){
			state = State.WAIT;
			quizWait();
		}
	}
	
	private void quizWait(){
		state = State.WAIT;
		question.delete(0, question.length());
		q = quizFactory.createQuiz(order[quizIndex], dl);
		question.append(q.getQuestion());
		startTime = System.currentTimeMillis();
		Log.d("quiz",q.getQuestion()+q.getAnswer());
		
	}

	private void quizStart() {
		
		startTime = System.currentTimeMillis();
		quizPlay();
	}	
	
	private void quizJudge(){
		state = QuizManager.State.JUDGE;
		startTime = System.currentTimeMillis();
		quizIndex++;
	}
	
	private void quizPlay(){
		state = QuizManager.State.PLAY;
	}
	
	//メインループ
	class Playing extends Thread{

		@Override
		public void run() {
			while(qState != QuizState.PAUSE){
				
				Log.d("Thread",state.toString());
				
				if(state == QuizManager.State.PLAY)
					if(System.currentTimeMillis() - startTime > PLAY_TIME_LIMIT)
						quizJudge();
				
				if(state == QuizManager.State.JUDGE)
					if(System.currentTimeMillis() - startTime > JUDGE_TIME_LIMIT)
						quizWait();
				
				if(state == QuizManager.State.WAIT)
					if(System.currentTimeMillis() - startTime > WAIT_TIME_LIMIT)
						quizPlay();
				
				try {
					Thread.sleep(1000/30);
				} catch (InterruptedException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private boolean judge(String answer) {
		
		if(q.getAnswer().equals(answer))
			return true;
		else
			return false;
	}

	public long getElapsedTime(){
		return System.currentTimeMillis() - startTime;
	}

	@Override
	public StringBuilder getQuestion() {
		
		return question;
	}


	@Override
	public String getAnswer() {
		return q.getAnswer();
	}


	@Override
	public Bitmap getImage() {
		return q.getImage();
	}


	@Override
	public String[] getDummy() {
		return q.getDummys();
	}



}
