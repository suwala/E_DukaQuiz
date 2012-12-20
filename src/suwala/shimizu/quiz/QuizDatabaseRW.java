package suwala.shimizu.quiz;

import sample.postquiz.DBHelper;
import suwala.shimizu.quiz.QuizManager.Genre;
import suwala.shimizu.quiz.QuizManager.QuizCode;
import suwala.shimizu.quizlogic.DataBaseRW;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QuizDatabaseRW implements DataBaseRW{

	private Context context;
	private String filter="";
	
	public QuizDatabaseRW(Context context,Genre genre,QuizCode quizCode){
		this.context = context;
		
		if(genre != Genre.NoGenre && quizCode != null)
			filter = " WHERE genre = \""+genre.getGenre()+"\" and quizcode="+quizCode.getQuizCode();
		else if (genre != Genre.NoGenre)
			filter=" WHERE genre='"+genre.getGenre()+"'";
		else if(quizCode != null)
			filter=" WHERE quizcode="+quizCode.getQuizCode();		
	}
	
	@Override
	public int getRecordCount() {
		
		DBHelper dbh = new DBHelper(context);
		SQLiteDatabase db = dbh.getReadableDatabase();
		
		String sql;
		if(filter == null)
			sql = "SELECT COUNT(*) from "+DBHelper.getTableName();
		else
			sql = "SELECT COUNT(*) from "+DBHelper.getTableName()+filter;
		
		Cursor cursor = db.rawQuery(sql,null);
		((Activity)context).startManagingCursor(cursor);
		cursor.moveToFirst();
		int total = cursor.getInt(0);
		dbh.close();
        return total;
	}

	@Override
	public String readStringData(String column,int index) {
		
		DBHelper dbh = new DBHelper(context);
		SQLiteDatabase db = dbh.getReadableDatabase();
		
		Cursor cursor;
		String str=null;
		String sql = "SELECT "+ column+ " from "+DBHelper.getTableName()+filter;
		
		cursor = db.rawQuery(sql, null);
		((Activity)context).startManagingCursor(cursor);
		int clmIndex = cursor.getColumnIndex(column);
		boolean isEof = cursor.moveToFirst();
		if(isEof){
			cursor.move(index);
			str = cursor.getString(clmIndex);
		}
		
		dbh.close();
		
		return str;
	}

	@Override
	public int readIntData(String column,int index) {
		DBHelper dbh = new DBHelper(context);
		SQLiteDatabase db = dbh.getReadableDatabase();
		int i=0 ;
		
		Cursor cursor;
		String sql = "SELECT "+ column+ " from "+DBHelper.getTableName()+filter;
		
		cursor = db.rawQuery(sql, null);
		((Activity)context).startManagingCursor(cursor);
				
		int clmIndex = cursor.getColumnIndex(column);
		boolean isEof = cursor.moveToFirst();
		if(isEof){
			cursor.move(index);
			i = cursor.getInt(clmIndex);
		}
		dbh.close();
		
		return i;
	}

	@Override
	public String readStringData(String key) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int readIntData(String key) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

}
