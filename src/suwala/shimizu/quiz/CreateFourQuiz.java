package suwala.shimizu.quiz;

import suwala.shimizu.quizlogic.DataLoding;

public class CreateFourQuiz implements CreateQuestions{

	@Override
	public Questions createQuiz(int index,DataLoding dl) {
		
		String q = dl.readStringData("question",index);
		String a =dl.readStringData("answer", index);
		
		String d1 = dl.readStringData("dummy1", index);
		String d2 = dl.readStringData("dummy2", index);
		String d3 = dl.readStringData("dummy3", index);
		
		String[] dummys=new String[]{d1,d2,d3};
		
		return new FourSelectQuiz(q, a, dummys);
	}

}
