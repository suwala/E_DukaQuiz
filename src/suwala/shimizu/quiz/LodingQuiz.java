package suwala.shimizu.quiz;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class LodingQuiz {

	Context context;
	
	public LodingQuiz(Context context){
		this.context = context;
	}
	
	protected abstract Questions read(SQLiteDatabase db);
	
	public Questions readDataBase(int index) {
		
		DBHelper dbh = new DBHelper(context);
		SQLiteDatabase db = dbh.getReadableDatabase();
		
		return this.read(db);
	}

	public String readFile(String fileName) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public String readAssets(String fileName) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
