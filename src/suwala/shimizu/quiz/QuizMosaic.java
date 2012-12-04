package suwala.shimizu.quiz;

import sample.edukaquiz.DBHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QuizMosaic extends LodingQuiz{

	public QuizMosaic(Context context) {
		super(context);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	protected Questions read(SQLiteDatabase db) {
				
		Cursor c = db.query(DBHelper.getTableName(), new String[] {"question","answer","dummy1","dummy2","dummy3","image"}, null,null,null,null,null);
		int clmIndex= c.getColumnIndex("question");
		
		String q = c.getString(clmIndex);
		
		clmIndex= c.getColumnIndex("answer");
		String a = c.getString(clmIndex);
		
		Questions quiz = new Questions(q, a);
		
		clmIndex = c.getColumnIndex("dummy1");
		quiz.setDammy(c.getString(clmIndex), 0);
		
		clmIndex = c.getColumnIndex("dummy2");
		quiz.setDammy(c.getString(clmIndex), 1);
		
		clmIndex = c.getColumnIndex("dummy3");
		quiz.setDammy(c.getString(clmIndex), 2);
		
		return quiz;
	}

}
