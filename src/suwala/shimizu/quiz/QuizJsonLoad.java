package suwala.shimizu.quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import sample.postquiz.R;
import suwala.shimizu.quizlogic.JsonLoad;

//waitでアクセスしに行ってデータをMAPに格納
//その後keyを元に該当データを返す

public class QuizJsonLoad implements JsonLoad{

	final String strUrl = "http://wacha2.herokuapp.com/api/";
	Map<String,String> quizMap = new HashMap<String,String>();
	int recordCount=1;
	Context context;
	
	public QuizJsonLoad(Context context) {
		this.context = context;
		//accessURL();
	}
	
	public void accessURL(){
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(strUrl);
		HttpResponse res;
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mode","get"));
		params.add(new BasicNameValuePair("count",null));
		
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
			JSONArray arrya = json.getJSONArray("data");
			json = arrya.getJSONObject(0);
			Iterator it = json.keys();

			while(it.hasNext()){
				String key = (String)it.next();
				quizMap.put(key, json.getString(key));					
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
	}

	private class HttpJoin extends AsyncTask<String, Integer, Map>
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
		protected Map doInBackground(String... params2) {

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(strUrl);
			HttpResponse res;
			Map<String,String> quizMap = new HashMap<String,String>();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("mode","get"));
			params.add(new BasicNameValuePair("count",null));

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
				JSONArray arrya = json.getJSONArray("data");
				json = arrya.getJSONObject(0);
				Iterator it = json.keys();

				while(it.hasNext()){
					String key = (String)it.next();
					quizMap.put(key, json.getString(key));					
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

			return quizMap;

		}

		@Override
		public void onCancel(DialogInterface arg0) {
			dialog.cancel();
		}

		@Override
		protected void onPostExecute(Map result) {
			// TODO 自動生成されたメソッド・スタブ
			super.onPostExecute(result);
			dialog.dismiss();
			quizMap = result;
		}
	}

	private void accessURL(String url) {

		HttpJoin join = new HttpJoin(context);
		join.execute(url);
	}

	@Override
	public String readStringData(String key) {
		// TODO 自動生成されたメソッド・スタブ
		return quizMap.get(key);
	}

	@Override
	public int readIntData(String key) {
		// TODO 自動生成されたメソッド・スタブ
		return Integer.valueOf(quizMap.get(key));
	}

	@Override
	public String readStringData(String key, int index) {
		// TODO 自動生成されたメソッド・スタブ
		return quizMap.get(key);
	}

	@Override
	public int readIntData(String key, int index) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int getRecordCount() {
		// TODO 自動生成されたメソッド・スタブ
		return recordCount;
	}
}
