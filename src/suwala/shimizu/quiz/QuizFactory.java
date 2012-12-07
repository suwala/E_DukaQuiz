package suwala.shimizu.quiz;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import suwala.shimizu.quizlogic.DataLoding;

public class QuizFactory {
	
	private static Map<Integer, CreateQuestions> create = new HashMap<Integer,CreateQuestions>();
	
	
	static{
		create.put(0, new CreateFourQuiz());
		create.put(1, new CreateMosaicQuiz());
	}
	public enum quizCode{
		fourSelect(0),mosaic(1);
		private int code;
		
		private quizCode(int code) {
			this.code = code;
		}
		
		public int getCode(){
			return code;
		}		
	}
		
	public Questions createQuiz(int index,DataLoding dl){
			
		//indexからcodeを取得
		//コードを元に対応するインスタンスを生成
		
		
		int code;
		try {
			code = dl.readIntData("quizcode", index);
			return create.get(code).createQuiz(index,dl);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
				
		
	}
}
