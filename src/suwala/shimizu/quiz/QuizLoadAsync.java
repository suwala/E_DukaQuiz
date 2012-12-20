package suwala.shimizu.quiz;

import suwala.shimizu.quizlogic.DataLoding;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

public class QuizLoadAsync extends AsyncTask<QuizFactory, Integer, Questions>
	implements OnCancelListener{

	ProgressDialog dialog;
	Context context;
	CallBackTask callBackTask;
	DataLoding dl;
	int index;
	Questions quizz;
	
	public QuizLoadAsync(Context context,int index,DataLoding dl){
		this.context = context;
		this.index = index;
		this.dl = dl;
	}
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		
		dialog = new ProgressDialog(context);
		dialog.setMessage("クイズのロード中");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setCancelable(true);
		dialog.setOnCancelListener(this);
		dialog.show();

	}
	
	@Override
	public void onCancel(DialogInterface dialog) {
		dialog.cancel();
		((Activity)context).finish();
	}

	@Override
	protected Questions doInBackground(QuizFactory... params) {
		if(dl instanceof QuizJsonLoad)
			((QuizJsonLoad)dl).accessURL();
		return params[0].createQuiz(index, dl);
	}
	
	@Override
	protected void onPostExecute(Questions result) {
		dialog.dismiss();
		callBackTask.CallBack(result);
	}

	public void setOnCallBack(CallBackTask _obj){
		callBackTask = _obj;
	}

	public static class CallBackTask {
		Questions quizz;
		public void CallBack(Questions result) {
			quizz=result;
		}
	}

}
