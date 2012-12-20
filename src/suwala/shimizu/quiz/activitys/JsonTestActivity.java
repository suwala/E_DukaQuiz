package suwala.shimizu.quiz.activitys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sample.postquiz.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class JsonTestActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jsontest);
		
		Button btn = (Button)findViewById(R.id.jsonbtn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HttpJoin join = new HttpJoin(JsonTestActivity.this);
				join.execute("");
			}
		});
	}
	
	private class HttpJoin extends AsyncTask<String, Integer, StringBuilder>
		implements OnCancelListener{
		
		ProgressDialog dialog;
		Context context;
		String strUrl = "http://wacha2.herokuapp.com/api/";
		
		public HttpJoin(Context context) {
			this.context = context;
		}
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			
			dialog = new ProgressDialog(context);
			dialog.setMessage("接続中です");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(true);
			dialog.setOnCancelListener(this);
			dialog.show();
			
		}
		@Override
		protected StringBuilder doInBackground(String... params2) {
			
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(strUrl);
			HttpResponse res;
			StringBuilder str=new StringBuilder();
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("mode","get"));
			params.add(new BasicNameValuePair("count","3"));
			
			try{
				post.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
				res = client.execute(post);
				
				InputStream objStream = res.getEntity().getContent();
				InputStreamReader objReader = new InputStreamReader(objStream);
				BufferedReader objBuf = new BufferedReader(objReader);
				
				StringBuilder objJson = new StringBuilder();
				String sLine;
				while((sLine = objBuf.readLine()) != null)
					objJson.append(sLine);
				
				JSONObject json = new JSONObject(objJson.toString());
				Log.d("json",json.toString());
				JSONArray array = json.getJSONArray("data");
				json = array.getJSONObject(0);
				array.length();
				Iterator it = json.keys();
				
				while(it.hasNext()){
					String key = (String)it.next();
					str.append(key+":");
					str.append(json.getString(key)+"\n");
				}
				
			} catch (ClientProtocolException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
			return str;
			
		}

		@Override
		public void onCancel(DialogInterface arg0) {
			dialog.cancel();
		}
		
		@Override
		protected void onPostExecute(StringBuilder result) {
			// TODO 自動生成されたメソッド・スタブ
			super.onPostExecute(result);
			dialog.dismiss();
			TextView tv = (TextView)findViewById(R.id.jsontext);
			tv.setText(result);
		}
	}	

	@Override
	protected void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
	}

}
