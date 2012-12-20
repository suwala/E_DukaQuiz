package suwala.shimizu.quiz;

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
	public State state;
	private QuizFactory quizFactory;
	private long startTime;
	private DataLoding dl;
	private StringBuilder question  = new StringBuilder(255);
	private String userAnswer;
	private String[] answers;
	private Bitmap viewImage;
	private final String WAIT = "問題の準備中";
	private final String MARU = "正解";
	private final String BATSU = "まちがい";
	private volatile boolean judge;
	private final Context context;
	
	private final int WAIT_TIME_LIMIT = 3000;
	private final int PLAY_TIME_LIMIT = 10000;
	private final int JUDGE_TIME_LIMIT = 3000;
	private final int FADE_LENGTH = 5;//QuestionViewに一秒間に表示する文字数
	
	/*
	 * onEventsによってStateが変化＝次のイベントへ進む
	 * 
	 * waitにも同じ事が言える
	 * 問題の読み込みが終了していなくても
	 * 進む事が可能！
	 * 
	 */
	
	public static enum State{
		PLAY,JUDGE,WAIT,FINISH,LOAD
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
		
		//ここでデータのロード先の切り替えjson or 内部DB
//		dl = new QuizJsonLoad(context);
		dl = new QuizDatabaseRW(context,genre,code);
		
		this.context = context;
		
		int total = dl.getRecordCount();
		
		Log.d("Total",String.valueOf(total));
		
		order = new int[total];
		for(int i=0;i<total;i++)
			order[i] = i;
		
		ShuffleBox.shuffle(order);
		quizIndex = 0;
		quizFactory = new QuizFactory();
		question = new StringBuilder(255);
		quizLoad();
	}
	

	public void setTotalQ(int i){
		totalQ = i;
	}
	
	public State getQuizManagerState(){
		return state;
	}
	
	public synchronized void onEvents(String userAnswer){
		
		if(state == State.PLAY){
			this.userAnswer = userAnswer;
			quizJudge();
			return;
		}
		if(state == State.WAIT){
			quizStart();
			return;
		}
		
		if(state == State.JUDGE){
			quizLoad();
			return;
		}
	}
	
	public void setAnswer(String userAnswer){
		this.userAnswer = userAnswer;
	}
	//問題の読み込み Asyncにしたい
	private void quizLoad(){
		Log.d("STATE","LOAD");
		state = State.LOAD;
		question.delete(0, question.length());
		setStartTime();
		//クイズの受け取り
		QuizLoadAsync load = new QuizLoadAsync(context, order[quizIndex], dl);
		load.setOnCallBack(new QuizLoadAsync.CallBackTask(){
			@Override
			public void CallBack(Questions Result) {
				super.CallBack(Result);
				
				q = super.quizz;
//				question.append(q.getQuestion());
//				answers = new String[]{q.getAnswer(),q.getDummys()[0],
//						q.getDummys()[1],q.getDummys()[2]};
//				ShuffleBox.shuffle(answers);
//				userAnswer = null;
//				setStartTime();
				quizWait();
//				Log.d("quiz",q.getQuestion()+getViewAnswers(0)+getViewAnswers(2)+getViewAnswers(1)+getViewAnswers(3));
			};
		});
		load.execute(quizFactory);
	}
	
	//投稿者の表示などが行われる予定 
	private void quizWait(){
		Log.d("STATE","WAIT");
		state = State.WAIT;
		setStartTime();
	}
	
	//クイズ出題中
	private void quizStart() {
		Log.d("STATE","PLAY");
		question.append(q.getQuestion());
		answers = new String[]{q.getAnswer(),q.getDummys()[0],
				q.getDummys()[1],q.getDummys()[2]};
		ShuffleBox.shuffle(answers);
		userAnswer = null;
		setStartTime();
		state = State.PLAY;
	}
	
	//時間切れかボタン押後の判定
	private void quizJudge(){
		Log.d("STATE","JUDGE");
		setStartTime();
		state = QuizManager.State.JUDGE;
		judge = judge();
		quizIndex++;
	}
	
	private void setStartTime(){
		this.startTime = System.currentTimeMillis();
	}
	
	//上のクラスで呼ばれ続けている
	public void setMillisTime(long newTime){
		long time = newTime-startTime;
		if(state == State.PLAY){
			if(time > PLAY_TIME_LIMIT)
				onEvents(null);
		}else if(state == State.JUDGE){
			if(time > JUDGE_TIME_LIMIT)
				onEvents(null);
		}else if(state == State.WAIT)
			if(time > WAIT_TIME_LIMIT)
				onEvents(null);
	}
	
	private boolean judge() {
		
		if(q.getAnswer().equals(userAnswer))
			return true;
		else
			return false;
	}

	@Override
	public String getViewQuestion() {	
		
		if(state == State.PLAY){
			int time = (int)(System.currentTimeMillis() -startTime);
			if(time*FADE_LENGTH/1000 < question.length())
				return question.substring(0, 1+time*FADE_LENGTH/1000);
			else
				return question.toString();
		}
		if(state == State.WAIT)
			return WAIT;
		if(state == State.JUDGE)
			if(judge)
				return MARU;
			else
				return BATSU;
		
		return null;
		
	}

	@Override
	public Bitmap getViewImage() {
		if(state == State.PLAY)
			viewImage = q.getImage();
		return viewImage;
	}
	

	@Override
	public String getViewAnswers(int i){
		if(state == State.PLAY)
			return answers[i];
		return null;
	}	
	
	


}
